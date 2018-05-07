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
    private float intensity;
	private Random rand = new Random();
	private GameWorld gameWorld;

	public ShakeSystem(GameWorld gameWorld, Camera camera) {
        this.camera = camera;
        this.gameWorld = gameWorld;
    }

    public void startShake(Vector3 origin, float intensity){
    	this.origin = origin.cpy();
        this.intensity = intensity;
    	curTime = 0;
    }

    public void update(float delta){
    	curTime += delta;
    	if(curTime < shakeTime){
            int isNeg = 1;
            if(rand.nextFloat() > .5f)
                isNeg = -1;
    		Vector3 tmp = new Vector3();
    		tmp.set(0, 0, 0);
        	camera.rotate(camera.up, rand.nextFloat() * intensity * isNeg);
            isNeg = 1;
        	tmp.set(camera.direction).crs(camera.up).nor();
            if(rand.nextFloat() > .5f)
                isNeg = -1;
        	camera.direction.rotate(tmp, rand.nextFloat() * intensity * isNeg);
        	tmp.set(0, 0, 0);
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