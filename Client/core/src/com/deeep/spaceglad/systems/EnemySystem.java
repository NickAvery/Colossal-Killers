package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.*;
import com.deeep.spaceglad.managers.EntityFactory;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import java.lang.Math;

import java.util.Random;

/**
 * Created by Andreas on 8/5/2015.
 */

public class EnemySystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> entities;
    private Entity player;
    private Quaternion quat = new Quaternion();
    private Engine engine;
    private GameWorld gameWorld;
    private Camera camera;
    private int dinoNumber = 0;
    
    ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);

    public EnemySystem(GameWorld gameWorld, Camera camera) {
        this.gameWorld = gameWorld;
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(EnemyComponent.class, CharacterComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }

    public void update(float delta) {
        //Nick A for HW#6
        if (gameWorld.game.dinoSpawner && !gameWorld.avatarSystem.getPlayersList().isEmpty()) {
            if (entities.size() < 2) {
                Random random = new Random();
                int type = random.nextInt(2)+1;
			    float scale = 1.0f;
			    if (type == 1)
			    {
				    scale = (float)random.nextInt(5)+3.0f;
			    }   
			    else if(type == 2)
			    {
				    scale = (float)random.nextInt(3)+1.0f;
			    }
			    Entity entity = EntityFactory.createEnemy(gameWorld.bulletSystem, 10, 20, 10, type, scale);
                Object[] values = gameWorld.avatarSystem.getPlayersList().values().toArray();
                Entity target = (Entity) values[random.nextInt(values.length)];
                entity.getComponent(EnemyComponent.class).target = target;
			    entity.getComponent(EnemyComponent.class).username = "dinoava" + dinoNumber++ + '\'' + entity.getComponent(EnemyComponent.class).type + '\'' +
                        entity.getComponent(EnemyComponent.class).scale;
                engine.addEntity(entity);
                if (gameWorld.game.client != null) {
                    gameWorld.game.client.sendMessage("\\avatar" + " " + entity.getComponent(EnemyComponent.class).username + " " +
                            entity.getComponent(AvatarComponent.class).x + " " + entity.getComponent(AvatarComponent.class).y + " " +
                            entity.getComponent(AvatarComponent.class).z + " " + 0 + "\n");
                }
            }
            for (Entity e : entities) {
                ModelComponent mod = e.getComponent(ModelComponent.class);
                if (gameWorld.avatarSystem.getPlayersList().get(e.getComponent(EnemyComponent.class).target.getComponent(AvatarComponent.class).username) == null) {
                    Random random = new Random();
                    Object[] values = gameWorld.avatarSystem.getPlayersList().values().toArray();
                    Entity target = (Entity) values[random.nextInt(values.length)];
                    e.getComponent(EnemyComponent.class).target = target;
                }
                System.out.println(e.getComponent(EnemyComponent.class).username + " " + e.getComponent(EnemyComponent.class).target.getComponent(AvatarComponent.class).username);
                ModelComponent playerModel = e.getComponent(EnemyComponent.class).target.getComponent(ModelComponent.class);

                Vector3 playerPosition = new Vector3();
                Vector3 enemyPosition = new Vector3();

                playerPosition = playerModel.instance.transform.getTranslation(playerPosition);
                enemyPosition = mod.instance.transform.getTranslation(enemyPosition);

                float dist = (float) Math.sqrt(Math.pow(playerPosition.x - enemyPosition.x, 2) + Math.pow(playerPosition.y - enemyPosition.y, 2) + Math.pow(playerPosition.z - enemyPosition.z, 2));
                if (dist < .01f)
                    dist = .1f;

                float dX = playerPosition.x - enemyPosition.x;
                float dZ = playerPosition.z - enemyPosition.z;

                float theta = (float) (Math.atan2(dX, dZ));

                //Calculate the transforms
                Quaternion rot = quat.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);

                cm.get(e).characterDirection.set(-1, 0, 0).rot(mod.instance.transform);
                cm.get(e).walkDirection.set(0, 0, 0);
                cm.get(e).walkDirection.add(cm.get(e).characterDirection);
                cm.get(e).walkDirection.scl(3f * delta);
                cm.get(e).characterController.setWalkDirection(cm.get(e).walkDirection);

                Matrix4 ghost = new Matrix4();
                Vector3 translation = new Vector3();
                cm.get(e).ghostObject.getWorldTransform(ghost);
                ghost.getTranslation(translation);

                mod.instance.transform.set(translation.x, translation.y, translation.z, rot.x, rot.y, rot.z, rot.w);
                if (gameWorld.game.client != null) {
                    gameWorld.game.client.sendMessage("\\move " + e.getComponent(EnemyComponent.class).username + " " + translation.x + " "
                            + translation.y + " " + translation.z + " " + (theta+150) + "\n");
                }

                if (e.getComponent(EnemyComponent.class).health <= 0) {
                    e.getComponent(EnemyComponent.class).footStep.stop();
                    e.getComponent(EnemyComponent.class).footStep.dispose();
                    //Nick A for HW#6
                    if (e.getComponent(AnimationComponent.class) != null && e.getComponent(AnimationComponent.class).getController() != null) {
                        if (e.getComponent(StatusComponent.class).alive) {
                            e.getComponent(AnimationComponent.class).action("Armature|dead", 1, 3);
                        }
                    }
                    //end
                    e.getComponent(StatusComponent.class).alive = false;
                } else {
                    if (!e.getComponent(EnemyComponent.class).footStep.isPlaying()) {
                        if (e.getComponent(EnemyComponent.class).dinoType != EnemyComponent.DINOTYPE.RAPTOR) {
                            gameWorld.shakeSystem.startShake(camera.position, 10 / dist);
                        }
                        e.getComponent(EnemyComponent.class).footStep.play();
                    }
                    e.getComponent(EnemyComponent.class).footStep.setVolume(10 / dist);
                }
            }
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
