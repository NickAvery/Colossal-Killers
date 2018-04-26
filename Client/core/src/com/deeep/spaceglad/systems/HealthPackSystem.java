package com.deeep.spaceglad.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.*;
import com.deeep.spaceglad.managers.EntityFactory;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import java.util.Random;


public class HealthPackSystem extends EntitySystem implements EntityListener {
	private ImmutableArray<Entity> entities;
	private Engine engine;
	private GameWorld gameWorld;
	
	public HealthPackSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
	
	@Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(HealthPackComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }
	
	@Override
	public void update(float delta) {
		
		for(Entity e : entities){
			if(e != null){
				if(e.getComponent(HealthPackComponent.class).state == HealthPackComponent.STATE.RESPAWNING){
					if(e.getComponent(HealthPackComponent.class).despawnFlag == true){
						e.getComponent(ModelComponent.class).instance.transform.trn(0f,0f,-100f);
						e.getComponent(ModelComponent.class).instance.calculateTransforms();
						e.getComponent(HealthPackComponent.class).ghostObject.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						e.getComponent(BulletComponent.class).motionState.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						
						((btRigidBody) e.getComponent(BulletComponent.class).body).setMotionState(e.getComponent(BulletComponent.class).motionState);
						
						Gdx.app.log("HealthPack", "Despawned");
						
						
						e.getComponent(HealthPackComponent.class).despawnFlag = false;
					}
					e.getComponent(HealthPackComponent.class).respawnDelta += delta;
					if(e.getComponent(HealthPackComponent.class).respawnDelta >
					e.getComponent(HealthPackComponent.class).respawnTime){
						e.getComponent(HealthPackComponent.class).state = HealthPackComponent.STATE.READY;
						e.getComponent(HealthPackComponent.class).respawnFlag = true;
					}
				}else if(e.getComponent(HealthPackComponent.class).state == HealthPackComponent.STATE.READY){
					if(e.getComponent(HealthPackComponent.class).respawnFlag == true){
						e.getComponent(ModelComponent.class).instance.transform.trn(0f,0f,100f);
						e.getComponent(ModelComponent.class).instance.calculateTransforms();
						e.getComponent(HealthPackComponent.class).ghostObject.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						e.getComponent(BulletComponent.class).motionState.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						
						((btRigidBody) e.getComponent(BulletComponent.class).body).setMotionState(e.getComponent(BulletComponent.class).motionState);
						
						Gdx.app.log("HealthPack", "Respawned");
						
						
						e.getComponent(HealthPackComponent.class).respawnFlag = false;
						
					}
					
				}
			}
			
			
		}
	
	}
	
	@Override
    public void entityAdded(Entity entity) {
        //player = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
	
}