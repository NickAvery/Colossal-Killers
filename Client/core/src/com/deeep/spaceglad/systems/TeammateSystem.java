package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.AnimationComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.components.StatusComponent;
import com.deeep.spaceglad.components.TeammateComponent;
import com.deeep.spaceglad.managers.EntityFactory;

import java.util.Random;

/**
 * Created by Andreas on 8/5/2015.
 */

public class TeammateSystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> entities;
    private Entity player;
    private Quaternion quat = new Quaternion();
    private Engine engine;
    private GameWorld gameWorld;

    ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);

    public TeammateSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(TeammateComponent.class, CharacterComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }

    public void update(float delta) {
        for (Entity e : entities) {
            ModelComponent mod = e.getComponent(ModelComponent.class);

            /*
            Vector3 enemyPosition = new Vector3();

            playerPosition = playerModel.instance.transform.getTranslation(playerPosition);
            enemyPosition = mod.instance.transform.getTranslation(enemyPosition);

            cm.get(e).characterDirection.set(-1, 0, 0).rot(mod.instance.transform);
            cm.get(e).walkDirection.set(0, 0, 0);
            cm.get(e).walkDirection.add(cm.get(e).characterDirection);
            cm.get(e).walkDirection.scl(3f * delta);
            cm.get(e).characterController.setWalkDirection(cm.get(e).walkDirection);

            Matrix4 ghost = new Matrix4();
            Vector3 translation = new Vector3();
            cm.get(e).ghostObject.getWorldTransform(ghost);
            ghost.getTranslation(translation);
*/
            //TODO For each e, check if character is connected to the server, and get position/orientation and update below
            //mod.instance.transform.set(translation.x, translation.y, translation.z, rot.x, rot.y, rot.z, rot.w);
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
