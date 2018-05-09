package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.AnimationComponent;
import com.deeep.spaceglad.components.AvatarComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;

import java.util.HashMap;


public class AvatarSystem extends EntitySystem implements EntityListener{
    private GameWorld gameWorld;
    private Engine engine;
    private HashMap<String, Entity> players;
    private HashMap<String, Entity> enemies;
    private Vector3 playerLoc;
    ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);

    public AvatarSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        players = new HashMap<>();
        enemies = new HashMap<>();
        playerLoc = new Vector3();
    }

    public void updatePlayerLoc(float x, float y, float z){
        playerLoc.x = x;
        playerLoc.y = y;
        playerLoc.z = z;
    }

    public void addedToEngine(Engine e) {
        e.addEntityListener(Family.one(AvatarComponent.class).get(), this);
        this.engine = e;
    }

    public HashMap getPlayersList() {
        return players;
    }

    public HashMap getEnemiesList() {
        return enemies;
    }

    Quaternion avatarAngle = new Quaternion(); //-quaternions.... -paul
    Vector3 yaxis = new Vector3(0,1,0);

    public void update(float delta) {
        for(Entity player : players.values()) {
            ModelComponent modelComponent = player.getComponent(ModelComponent.class);
            AvatarComponent avatarComponent = player.getComponent(AvatarComponent.class);
            avatarAngle.setFromAxisRad(yaxis, avatarComponent.rotAngle); //get quaternion of avatar angle -Paul
            modelComponent.instance.transform.set (avatarComponent.x,
                    avatarComponent.y-3, avatarComponent.z,
                    avatarAngle.x, avatarAngle.y, avatarAngle.z, avatarAngle.w); //model.y-3 to stop floating avatar -Paul
        }
        if(!gameWorld.game.dinoSpawner) {
            for (Entity enemy : enemies.values()) {
                ModelComponent modelComponent = enemy.getComponent(ModelComponent.class);
                AvatarComponent avatarComponent = enemy.getComponent(AvatarComponent.class);
                cm.get(enemy).characterDirection.set(-1, 0, 0).rot(modelComponent.instance.transform);
                cm.get(enemy).walkDirection.set(0, 0, 0);
                cm.get(enemy).walkDirection.add(cm.get(enemy).characterDirection);
                cm.get(enemy).walkDirection.scl(3f * delta);
                cm.get(enemy).characterController.setWalkDirection(cm.get(enemy).walkDirection);

                Matrix4 ghost = new Matrix4();
                Vector3 translation = new Vector3();
                cm.get(enemy).ghostObject.getWorldTransform(ghost);
                ghost.getTranslation(translation);

                modelComponent.instance.transform.set(avatarComponent.x,
                        avatarComponent.y, avatarComponent.z,
                        avatarAngle.x, avatarAngle.y, avatarAngle.z, avatarAngle.w); //model.y-3 to stop floating avatar -Paul

                Vector3 enemyPosition = new Vector3(avatarComponent.x, avatarComponent.y, avatarComponent.z);

                float dist = (float) Math.sqrt(Math.pow(playerLoc.x - enemyPosition.x, 2) + Math.pow(playerLoc.y - enemyPosition.y, 2) + Math.pow(playerLoc.z - enemyPosition.z, 2));
                if (dist < .01f)
                    dist = .1f;

                if (!enemy.getComponent(AvatarComponent.class).footStep.isPlaying()) {
                    System.out.print("not playing");   // "5"
                    if (enemy.getComponent(AvatarComponent.class).type != 2) {
                        gameWorld.shakeSystem.startShake(10 / dist);
                        dist = dist/10;
                    }
                    enemy.getComponent(AvatarComponent.class).footStep.play();
                }
                enemy.getComponent(AvatarComponent.class).footStep.setVolume(10 / dist);
            }
        }
        if(!gameWorld.game.dinoSpawnerOnline) {
            for(Entity enemy: enemies.values()) {
                engine.removeEntity(enemy);
            }
            enemies.clear();
        }

    }

    @Override
    public void entityAdded(Entity entity) {
        if (entity.getComponent(AvatarComponent.class) != null) {
            if (entity.getComponent(EnemyComponent.class) != null)
                enemies.put(entity.getComponent(AvatarComponent.class).username, entity);
            else
                players.put(entity.getComponent(AvatarComponent.class).username, entity);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
