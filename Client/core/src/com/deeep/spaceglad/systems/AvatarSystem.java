package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.AnimationComponent;
import com.deeep.spaceglad.components.AvatarComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;

import java.util.HashMap;


public class AvatarSystem extends EntitySystem implements EntityListener{
    private GameWorld gameWorld;
    private Engine engine;
    private HashMap<String, Entity> players;
    private HashMap<String, Entity> enemies;

    public AvatarSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        players = new HashMap<>();
        enemies = new HashMap<>();
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
        for(Entity enemy : enemies.values()) {
            ModelComponent modelComponent = enemy.getComponent(ModelComponent.class);
            AvatarComponent avatarComponent = enemy.getComponent(AvatarComponent.class);
            avatarAngle.setFromAxisRad(yaxis, avatarComponent.rotAngle); //get quaternion of avatar angle -Paul
            modelComponent.instance.transform.set (avatarComponent.x,
                    avatarComponent.y, avatarComponent.z,
                    avatarAngle.x, avatarAngle.y, avatarAngle.z, avatarAngle.w); //model.y-3 to stop floating avatar -Paul
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
