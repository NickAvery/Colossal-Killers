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


public class WeaponSystem extends EntitySystem implements EntityListener {
	private ImmutableArray<Entity> entities;
	private Engine engine;
	private GameWorld gameWorld;
	
	public WeaponSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
	
	@Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(WeaponComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }
	
	public void update(float delta) {
		

		for(Entity e : entities){
			if(e != null){
				if(e.getComponent(WeaponComponent.class).state == WeaponComponent.STATE.RESPAWNING){
					if(e.getComponent(WeaponComponent.class).despawnFlag == true){
						e.getComponent(ModelComponent.class).instance.transform.trn(0f,-1000f,0f);
						e.getComponent(ModelComponent.class).instance.calculateTransforms();
						e.getComponent(WeaponComponent.class).ghostObject.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						e.getComponent(BulletComponent.class).motionState.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						
						((btRigidBody) e.getComponent(BulletComponent.class).body).setMotionState(e.getComponent(BulletComponent.class).motionState);
						
						Gdx.app.log("Weapon", "Despawned");
						
						
						e.getComponent(WeaponComponent.class).despawnFlag = false;
					}
					e.getComponent(WeaponComponent.class).respawnDelta += delta;
					if(e.getComponent(WeaponComponent.class).respawnDelta >
					e.getComponent(WeaponComponent.class).respawnTime){
						e.getComponent(WeaponComponent.class).state = WeaponComponent.STATE.READY;
						e.getComponent(WeaponComponent.class).respawnFlag = true;
					}
				}else if(e.getComponent(WeaponComponent.class).state == WeaponComponent.STATE.READY){
					if(e.getComponent(WeaponComponent.class).respawnFlag == true){
						e.getComponent(ModelComponent.class).instance.transform.trn(0f,1000f,0f);
						e.getComponent(ModelComponent.class).instance.calculateTransforms();
						e.getComponent(WeaponComponent.class).ghostObject.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						e.getComponent(BulletComponent.class).motionState.setWorldTransform(e.getComponent(ModelComponent.class).instance.transform);
						
						((btRigidBody) e.getComponent(BulletComponent.class).body).setMotionState(e.getComponent(BulletComponent.class).motionState);
						
						Gdx.app.log("Weapon", "Respawned");
						
						
						e.getComponent(WeaponComponent.class).respawnFlag = false;
						
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