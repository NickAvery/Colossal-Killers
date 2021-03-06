package com.deeep.spaceglad.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.ashley.core.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.deeep.spaceglad.components.*;

/**
 * Created by scanevaro on 22/09/2015.
 */
public class BulletSystem extends EntitySystem implements EntityListener {
    public final btCollisionConfiguration collisionConfiguration;
    public final btCollisionDispatcher dispatcher;
    public final btBroadphaseInterface broadphase;
    public final btConstraintSolver solver;
    public final btDiscreteDynamicsWorld collisionWorld;
    private btGhostPairCallback ghostPairCallback;
    public int maxSubSteps = 5;
    public float fixedTimeStep = 1f / 60f;

    public class MyContactListener extends ContactListener {
        @Override
        public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
            if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {
                Entity entity0 = (Entity) colObj0.userData;
                Entity entity1 = (Entity) colObj1.userData;
                if (entity0.getComponent(CharacterComponent.class) != null && entity1.getComponent(CharacterComponent.class) != null) {
                    if (entity0.getComponent(EnemyComponent.class) != null && entity1.getComponent(PlayerComponent.class) != null) { //entity0 is monster
                        if (entity0.getComponent(StatusComponent.class).alive)
						{
							entity1.getComponent(PlayerComponent.class).health -= 10;
						}
						//Nick A for HW#6
						if(entity0.getComponent(AnimationComponent.class) != null && entity0.getComponent(AnimationComponent.class).getController() != null)
						{
							entity0.getComponent(AnimationComponent.class).action("Armature|attack", 1, 1);
						}
						//end
                    } else if (entity1.getComponent(EnemyComponent.class) != null && entity0.getComponent(PlayerComponent.class) != null) {	//entity1 is monster
						if (entity1.getComponent(StatusComponent.class).alive)
						{
							entity0.getComponent(PlayerComponent.class).health -= 10;
						}
						//Nick A for HW#6
						if(entity1.getComponent(AnimationComponent.class) != null && entity1.getComponent(AnimationComponent.class).getController() != null)
						{
							entity1.getComponent(AnimationComponent.class).action("Armature|attack", 1, 1);
						}
						//end
                    }
                }
				
				if(entity0.getComponent(HealthPackComponent.class) != null && entity1.getComponent(CharacterComponent.class) != null){
					if(entity1.getComponent(PlayerComponent.class) != null){
						entity1.getComponent(PlayerComponent.class).health += 10;
						if(entity1.getComponent(PlayerComponent.class).health > 100)
							entity1.getComponent(PlayerComponent.class).health = 100;
						entity0.getComponent(HealthPackComponent.class).state = HealthPackComponent.STATE.RESPAWNING;
						entity0.getComponent(HealthPackComponent.class).respawnDelta = 0f;
						entity0.getComponent(HealthPackComponent.class).despawnFlag = true;
					}
					
					
				}else if(entity1.getComponent(HealthPackComponent.class) != null && entity0.getComponent(CharacterComponent.class) != null){
					if(entity0.getComponent(PlayerComponent.class) != null){
						entity0.getComponent(PlayerComponent.class).health += 10;
						if(entity0.getComponent(PlayerComponent.class).health > 100)
							entity0.getComponent(PlayerComponent.class).health = 100;
						entity1.getComponent(HealthPackComponent.class).state = HealthPackComponent.STATE.RESPAWNING;
						entity1.getComponent(HealthPackComponent.class).respawnDelta = 0f;
						entity1.getComponent(HealthPackComponent.class).despawnFlag = true;
					}
				}
				
				
				if(entity0.getComponent(WeaponComponent.class) != null && entity1.getComponent(CharacterComponent.class) != null){
					if(entity1.getComponent(PlayerComponent.class) != null && entity0.getComponent(WeaponComponent.class).despawnFlag == false){
						
						if(entity0.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.SPEAR){ // spear collision
							if(entity1.getComponent(PlayerComponent.class).ammoHeld[0] == 0){
								entity1.getComponent(PlayerComponent.class).ammoHeld[0] = 1;
							}
						}
						if(entity0.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.GUN){ // gun collision
							entity1.getComponent(PlayerComponent.class).ammoHeld[1] += entity0.getComponent(WeaponComponent.class).ammoAmount;
							Gdx.app.log("Ammo", "picked up");
						}
						if(entity0.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.SHOTGUN){ // shotgun collision
							entity1.getComponent(PlayerComponent.class).ammoHeld[2] += entity0.getComponent(WeaponComponent.class).ammoAmount;
						}
						entity0.getComponent(WeaponComponent.class).state = WeaponComponent.STATE.RESPAWNING;
						entity0.getComponent(WeaponComponent.class).respawnDelta = 0f;
						entity0.getComponent(WeaponComponent.class).despawnFlag = true;

					}
					
					
				}else if(entity1.getComponent(WeaponComponent.class) != null && entity0.getComponent(CharacterComponent.class) != null){
					if(entity0.getComponent(PlayerComponent.class) != null && entity1.getComponent(WeaponComponent.class).despawnFlag == false){
						
						if(entity1.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.SPEAR){ // spear collision
							if(entity0.getComponent(PlayerComponent.class).ammoHeld[0] == 0){
								entity0.getComponent(PlayerComponent.class).ammoHeld[0] = 1;
							}
						}
						if(entity1.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.GUN){ // gun collision
							entity0.getComponent(PlayerComponent.class).ammoHeld[1] += entity1.getComponent(WeaponComponent.class).ammoAmount;
							Gdx.app.log("Ammo", "picked up");
						}
						if(entity1.getComponent(WeaponComponent.class).weapon == WeaponComponent.WEAPON.SHOTGUN){ // shotgun collision
							entity0.getComponent(PlayerComponent.class).ammoHeld[2] += entity1.getComponent(WeaponComponent.class).ammoAmount;
						}
						
						
						entity1.getComponent(WeaponComponent.class).state = WeaponComponent.STATE.RESPAWNING;
						entity1.getComponent(WeaponComponent.class).respawnDelta = 0f;
						entity1.getComponent(WeaponComponent.class).despawnFlag = true;
					}
				}
            }
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(BulletComponent.class).get(), this);
    }

    public BulletSystem() {
        MyContactListener myContactListener = new MyContactListener();
        myContactListener.enable();
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btAxisSweep3(new Vector3(-1000, -1000, -1000), new Vector3(1000, 1000, 1000));
        solver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        ghostPairCallback = new btGhostPairCallback();
        broadphase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);
        this.collisionWorld.setGravity(new Vector3(0, -0.5f, 0));
    }

    @Override
    public void update(float deltaTime) {
        collisionWorld.stepSimulation(deltaTime);
    }

    public void dispose() {
        collisionWorld.dispose();
        if (solver != null) solver.dispose();
        if (broadphase != null) broadphase.dispose();
        if (dispatcher != null) dispatcher.dispose();
        if (collisionConfiguration != null) collisionConfiguration.dispose();
        ghostPairCallback.dispose();
    }

    @Override
    public void entityAdded(Entity entity) {
        BulletComponent bulletComponent = entity.getComponent(BulletComponent.class);
        if (bulletComponent.body != null) {
            collisionWorld.addRigidBody((btRigidBody) bulletComponent.body);
        }
    }

    public void removeBody(Entity entity) {
        BulletComponent comp = entity.getComponent(BulletComponent.class);
        if (comp != null)
            collisionWorld.removeCollisionObject(comp.body);
        CharacterComponent character = entity.getComponent(CharacterComponent.class);
        if (character != null) {
            collisionWorld.removeAction(character.characterController);
            collisionWorld.removeCollisionObject(character.ghostObject);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
