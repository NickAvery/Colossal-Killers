package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.components.*;
import com.deeep.spaceglad.bullet.MotionState;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;

/**
 * Created by Elmar on 8-8-2015.
 */
public class PlayerSystem extends EntitySystem implements EntityListener {
    public Entity player;
    private PlayerComponent playerComponent;
	
	public Entity gun;
	
    private CharacterComponent characterComponent;
    private ModelComponent modelComponent;
    private GameUI gameUI;
    private final Vector3 tmp = new Vector3();
    private final Camera camera;
    private final Vector3 tempVector = new Vector3();
    private GameWorld gameWorld;
    private Sound gunShot;
    Vector3 rayFrom = new Vector3();
    Vector3 rayTo = new Vector3();
    ClosestRayResultCallback rayTestCB;

    public PlayerSystem(GameWorld gameWorld, GameUI gameUI, Camera camera) {
        this.camera = camera;
        this.gameWorld = gameWorld;
        this.gameUI = gameUI;
        rayTestCB = new ClosestRayResultCallback(Vector3.Zero, Vector3.Z);
        gunShot = Gdx.audio.newSound(Gdx.files.internal("data/laser.mp3"));
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (player == null) return;
        updateMovement(delta);
        updateStatus();
        checkGameOver();
    }

    boolean moving = true;

    private void updateMovement(float delta) {
        //Updated to mouse controls -Paul
        float deltaX = -Gdx.input.getDeltaX() * 0.3f;
        float deltaY = -Gdx.input.getDeltaY() * 0.3f;
        tmp.set(0, 0, 0);
        camera.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        tmp.set(0, 0, 0);
        characterComponent.characterDirection.set(-1, 0, 0).rot(modelComponent.instance.transform).nor();
        characterComponent.walkDirection.set(0, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) characterComponent.walkDirection.add(camera.direction);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) characterComponent.walkDirection.sub(camera.direction);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) tmp.set(camera.direction).crs(camera.up).scl(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) tmp.set(camera.direction).crs(camera.up);
        characterComponent.walkDirection.add(tmp);
        characterComponent.walkDirection.scl(10f * delta);
        characterComponent.characterController.setWalkDirection(characterComponent.walkDirection);
        Matrix4 ghost = new Matrix4();
        Vector3 translation = new Vector3();
        characterComponent.ghostObject.getWorldTransform(ghost);   //TODO export this
        ghost.getTranslation(translation);

        Quaternion lookAngle = new Quaternion(); //PFM -paul
        float theta = (float) (Math.atan2(camera.direction.x, camera.direction.z));
        Quaternion rot = lookAngle.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));
        modelComponent.instance.transform.set(translation.x, translation.y-3, translation.z, rot.x, rot.y, rot.z, rot.w); //keep avatar rotated where camera rotates -paul

        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            characterComponent.characterController.setJumpSpeed(25);
            characterComponent.characterController.jump();
        }

        if (Gdx.input.justTouched()) fire();  //mouse fire -Paul

		if (Gdx.input.isKeyPressed(Input.Keys.X)) useDoor(delta);  //should we change this? -Paul


        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.getComponent(AnimationComponent.class).animate("Root|Run_loop", 10000, 2); //animate the avatar
            moving = true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (moving == true) { //only start animation once.... -Paul
            player.getComponent(AnimationComponent.class).animate("Root|Idle", 10000, 1); //animate the avatar
            }
            moving = false;
        }

    }

    private void updateStatus() {
        gameUI.healthWidget.setValue(playerComponent.health);
    }

    private void fire() {
        Ray ray = camera.getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);
        gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);
        if (rayTestCB.hasHit()) {
            final btCollisionObject obj = rayTestCB.getCollisionObject();
            if (((Entity) obj.userData).getComponent(EnemyComponent.class) != null) {
                 ParticleEffect effect = ((Entity) obj.userData).getComponent(DieParticleComponent.class).originalEffect.copy();
                ((RegularEmitter)effect.getControllers().first().emitter).setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
                effect.setTransform(((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform);
                effect.scale(3.25f, 1, 1.5f);
                effect.init();
                effect.start();
                RenderSystem.particleSystem.add(effect);
                ((Entity) obj.userData).getComponent(EnemyComponent.class).health -= 10;
		if(((Entity) obj.userData).getComponent(EnemyComponent.class).health <= 0)
			PlayerComponent.score += 100;
            }
        }
		
		gun.getComponent(AnimationComponent.class).animate("Armature|shoot", 1, 3);
        gunShot.play(.25f);
    }
	
	private void useDoor(float delta) {
		Ray ray = camera.getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(50f).add(rayFrom);
        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);
        gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);
		if (rayTestCB.hasHit()) {
            final btCollisionObject obj = rayTestCB.getCollisionObject();
            if (((Entity) obj.userData).getComponent(DoorComponent.class) != null) {
				//rotate door 
				if(!((Entity) obj.userData).getComponent(DoorComponent.class).doorInfo.isOpen)
				{
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform.translate(5f,0f,0f);
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.calculateTransforms();
					((Entity) obj.userData).getComponent(BulletComponent.class).motionState = new MotionState(((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform);
					((btRigidBody) ((Entity) obj.userData).getComponent(BulletComponent.class).body).setMotionState( ((Entity) obj.userData).getComponent(BulletComponent.class).motionState );
				}
				else
				{
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform.translate(-5f,0f,0f);
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.calculateTransforms();
					((Entity) obj.userData).getComponent(BulletComponent.class).motionState = new MotionState(((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform);
					((btRigidBody) ((Entity) obj.userData).getComponent(BulletComponent.class).body).setMotionState( ((Entity) obj.userData).getComponent(BulletComponent.class).motionState );
				}
				((Entity) obj.userData).getComponent(DoorComponent.class).doorInfo.isOpen = !((Entity) obj.userData).getComponent(DoorComponent.class).doorInfo.isOpen;
            }
        }
	}

    private void checkGameOver() {
        if (playerComponent.health <= 0 && !Settings.Paused) {
            Settings.Paused = true;
            gameUI.gameOverWidget.gameOver();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
        playerComponent = entity.getComponent(PlayerComponent.class);
        characterComponent = entity.getComponent(CharacterComponent.class);
        modelComponent = entity.getComponent(ModelComponent.class);
        //
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}

