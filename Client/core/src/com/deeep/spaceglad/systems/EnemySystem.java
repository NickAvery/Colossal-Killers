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
    
    ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);

    public EnemySystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(EnemyComponent.class, CharacterComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(), this);
        this.engine = e;
    }

    public void update(float delta) {
	//Nick A for HW#6
        if (entities.size() < 2) {
            Random random = new Random();
            engine.addEntity(EntityFactory.createEnemy(gameWorld.bulletSystem, 10, 3, 10,random.nextInt(2)+1));
        }
        for (Entity e : entities) {
            ModelComponent mod = e.getComponent(ModelComponent.class);
            ModelComponent playerModel = player.getComponent(ModelComponent.class);

            Vector3 playerPosition = new Vector3();
            Vector3 enemyPosition = new Vector3();

            playerPosition = playerModel.instance.transform.getTranslation(playerPosition);
            enemyPosition = mod.instance.transform.getTranslation(enemyPosition);

            float dist = (float)Math.sqrt(Math.pow(playerPosition.x - enemyPosition.x, 2) + Math.pow(playerPosition.y - enemyPosition.y, 2) + Math.pow(playerPosition.z - enemyPosition.z, 2));
            if(dist < .01f) 
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

            if (e.getComponent(EnemyComponent.class).health <= 0)
			{
                e.getComponent(EnemyComponent.class).footStep.stop();
                e.getComponent(EnemyComponent.class).footStep.dispose();
				//Nick A for HW#6
					if(e.getComponent(AnimationComponent.class) != null && e.getComponent(AnimationComponent.class).getController() != null)
					{
						if(e.getComponent(StatusComponent.class).alive){
							e.getComponent(AnimationComponent.class).action("Armature|dead", 1, 3);
                        }
					}
					//end
					e.getComponent(StatusComponent.class).alive = false;
			}else{
                if(!e.getComponent(EnemyComponent.class).footStep.isPlaying()){
                    e.getComponent(EnemyComponent.class).footStep.setLooping(true);
                    e.getComponent(EnemyComponent.class).footStep.play();
                }
    
                e.getComponent(EnemyComponent.class).footStep.setVolume(10/dist);
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
