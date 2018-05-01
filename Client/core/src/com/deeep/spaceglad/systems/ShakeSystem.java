package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.GameWorld;
import java.util.Random;


public class ShakeSystem extends EntitySystem implements EntityListener {
	private float shakeTime = 1f;
	private Vector3 origin;
	private float curTime = 0f;
	private Camera camera;
	private Random rand = new Random();
	private GameWorld gameWorld;

	public ShakeSystem(GameWorld gameWorld, Camera camera) {
        this.camera = camera;
        this.gameWorld = gameWorld;
    }

    public void startShake(Vector3 origin){
    	this.origin = origin.cpy();
    	curTime = 0;
    }

    public void update(float delta){
    	System.out.println(delta);
    	curTime += delta;
    	if(curTime < shakeTime){
    		Vector3 tmp = new Vector3();
    		tmp.set(0, 0, 0);
        	camera.rotate(camera.up, rand.nextFloat() * 1f);
        	tmp.set(camera.direction).crs(camera.up).nor();
        	camera.direction.rotate(tmp, rand.nextFloat() * 1f);
        	tmp.set(0, 0, 0);
    	}else if(curTime - delta < shakeTime){
    		camera.lookAt(origin.x, origin.y, origin.z);
    	}
    }

    @Override
    public void addedToEngine(Engine e) {
    }
    @Override
    public void entityAdded(Entity entity) {
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

}