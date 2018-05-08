package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.AvatarComponent;
import com.deeep.spaceglad.components.ModelComponent;

import java.util.HashMap;


public class AvatarSystem extends EntitySystem implements EntityListener{
    private GameWorld gameWorld;
    private Engine engine;
    private HashMap<String, Entity> players;

    public AvatarSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        players = new HashMap<>();
    }

    public void addedToEngine(Engine e) {
        e.addEntityListener(Family.one(AvatarComponent.class).get(), this);
        this.engine = e;
    }

    public HashMap getPlayersList() {
        return players;
    }

    public void update(float delta) {
        for(Entity player : players.values()) {
            ModelComponent modelComponent = player.getComponent(ModelComponent.class);
            AvatarComponent avatarComponent = player.getComponent(AvatarComponent.class);
            modelComponent.instance.transform.set(avatarComponent.x,
                    avatarComponent.y-3, avatarComponent.z, 0, 0, 0, 0); //model.y-3 to stop floating avatar -Paul
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        if (entity.getComponent(AvatarComponent.class) != null) {
            players.put(entity.getComponent(AvatarComponent.class).username, entity);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
