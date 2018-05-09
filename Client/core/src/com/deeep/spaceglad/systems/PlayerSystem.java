package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.deeep.spaceglad.Assets;

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
	Vector3 rayToLong = new Vector3();
	Vector3 rayToMedium = new Vector3();
	Vector3 rayToShort = new Vector3();
    ClosestRayResultCallback rayTestCB;
	ClosestRayResultCallback rayTestCBLong;
	ClosestRayResultCallback rayTestCBMedium;
	ClosestRayResultCallback rayTestCBShort;								
	private ImmutableArray<Entity> playersList;

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
		if (player == null)
			return;
		updateMovement(delta);
		updateStatus();
		checkGameOver();
	}

	private void updateMovement(float delta) {
		// Updated to mouse controls -Paul
		float deltaX = -Gdx.input.getDeltaX() * 0.5f;
		float deltaY = -Gdx.input.getDeltaY() * 0.5f;
		tmp.set(0, 0, 0);
		camera.rotate(camera.up, deltaX);
		tmp.set(camera.direction).crs(camera.up).nor();
		camera.direction.rotate(tmp, deltaY);
		tmp.set(0, 0, 0);
		characterComponent.characterDirection.set(-1, 0, 0).rot(modelComponent.instance.transform).nor();
		characterComponent.walkDirection.set(0, 0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			characterComponent.walkDirection.add(camera.direction);
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			characterComponent.walkDirection.sub(camera.direction);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			tmp.set(camera.direction).crs(camera.up).scl(-1);
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			tmp.set(camera.direction).crs(camera.up);
		characterComponent.walkDirection.add(tmp);
		characterComponent.walkDirection.scl(10f * delta);
		characterComponent.characterController.setWalkDirection(characterComponent.walkDirection);
		Matrix4 ghost = new Matrix4();
		Vector3 translation = new Vector3();
		characterComponent.ghostObject.getWorldTransform(ghost); // TODO export this
		ghost.getTranslation(translation);

		Quaternion lookAngle = new Quaternion(); //PFM -paul
		float theta = (float) (Math.atan2(camera.direction.x, camera.direction.z));
		Quaternion rot = lookAngle.setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));

		modelComponent.instance.transform.set(translation.x, translation.y-3, translation.z,
				rot.x, rot.y, rot.z, rot.w); //keep avatar rotated where camera rotates -paul
		camera.position.set(translation.x, translation.y, translation.z);
		camera.update(true);
		if (gameWorld.game.client != null && !gameWorld.game.dinoSpawner) {
			gameWorld.game.client.sendMessage("\\move " + gameWorld.game.client.username + " " + translation.x + " "
					+ translation.y + " " + translation.z + " " + (theta) + "\n");  //theta for avatar rotation (radians) -paul
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			characterComponent.characterController.setJumpSpeed(25);
			characterComponent.characterController.jump();
		}

		if (Gdx.input.justTouched()) {
			fire(); // mouse fire -Paul
			//gameUI.messageWidget.addChatMessage(Double.toString(theta+3.1416) + "\n");
		}

		if (Gdx.input.isKeyPressed(Input.Keys.X))
			useDoor(delta); // should we change this? -Paul
	}


	private void updateStatus() {
		gameUI.healthWidget.setValue(playerComponent.health);
		
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && player.getComponent(PlayerComponent.class).weapon != 0){
			Gdx.app.log("Weapon:", "Spear"); // switch to spear
			//gun.getComponent(ModelComponent.class) = new ModelComponent(Assets.spearModel, 2.5f, -1.9f, -4);
			gun.remove(ModelComponent.class);
			ModelComponent modelComponent = new ModelComponent(Assets.spearModel, 2.5f, -1.9f, -4);
			modelComponent.instance.transform.scale(0.01f, 0.01f, 0.01f);
			gun.add(modelComponent); 
			gun.remove(WeaponComponent.class);
			gun.add(new WeaponComponent(0));
			player.getComponent(PlayerComponent.class).weapon = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && player.getComponent(PlayerComponent.class).weapon != 1){
			Gdx.app.log("Weapon:", "Gun"); // switch to gun
			gun.remove(ModelComponent.class);
			
			ModelLoader<?> modelLoader = new G3dModelLoader (new JsonReader());
			ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/GUNMODEL.g3dj"));
			Model model = new Model(modelData, new TextureProvider.FileTextureProvider());

			ModelComponent modelComponent = new ModelComponent(model, 2.5f, -1.9f, -4);
			modelComponent.instance.transform.rotate(0, 1, 0, 180);
			gun.add(modelComponent); 
			gun.remove(WeaponComponent.class);
			gun.remove(AnimationComponent.class);
			gun.add(new WeaponComponent(1));
			gun.add(new AnimationComponent(modelComponent.instance));
			player.getComponent(PlayerComponent.class).weapon = 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) && player.getComponent(PlayerComponent.class).weapon != 2){
			Gdx.app.log("Weapon:", "Shotgun"); // switch to shotgun
			gun.remove(ModelComponent.class);
			ModelComponent modelComponent = new ModelComponent(Assets.shotgunModel, 2.5f, -1.9f, -4);
			modelComponent.instance.transform.scale(0.03f, 0.03f, 0.03f);
			modelComponent.instance.transform.rotate(0, 1, 0, 90);
			gun.add(modelComponent); 
			gun.remove(WeaponComponent.class);
			gun.add(new WeaponComponent(2));
			player.getComponent(PlayerComponent.class).weapon = 2;
		}
	}

	private void fire() {
		Ray ray = camera.getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		rayFrom.set(ray.origin);
		//rayTo.set(ray.direction).scl(50f).add(rayFrom);
		rayToLong.set(ray.direction).scl(gun.getComponent(WeaponComponent.class).longRange).add(rayFrom);
		rayToMedium.set(ray.direction).scl(gun.getComponent(WeaponComponent.class).mediumRange).add(rayFrom);
		rayToShort.set(ray.direction).scl(gun.getComponent(WeaponComponent.class).shortRange).add(rayFrom);			 
		rayTestCB.setCollisionObject(null);
		rayTestCB.setClosestHitFraction(1f);
		rayTestCB.setRayFromWorld(rayFrom);
		//rayTestCB.setRayToWorld(rayTo);
		rayTestCB.setRayToWorld(rayToShort);
		
		boolean confirmedHit = false;
		boolean longHit = false;
		boolean mediumHit = false;
		boolean shortHit = false;
		
        //gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);
		gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayToShort, rayTestCB);
		if(rayTestCB.hasHit() && !confirmedHit){
			Gdx.app.log("hit", "short");
			confirmedHit = true;
			shortHit = true;
		}else{
			rayTestCB.setRayToWorld(rayToMedium);
			gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayToMedium, rayTestCB);
		}
		if(rayTestCB.hasHit() && !confirmedHit){
			Gdx.app.log("hit", "medium");
			confirmedHit = true;
			mediumHit = true;
		}else{
			rayTestCB.setRayToWorld(rayToLong);
			gameWorld.bulletSystem.collisionWorld.rayTest(rayFrom, rayToLong, rayTestCB);
		}
		if(rayTestCB.hasHit() && !confirmedHit){
			Gdx.app.log("hit", "long");
			confirmedHit = true;
			longHit = true;
		}
		
		
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
				//((Entity) obj.userData).getComponent(EnemyComponent.class).health -= 10;
				if(shortHit) ((Entity) obj.userData).getComponent(EnemyComponent.class).health -= gun.getComponent(WeaponComponent.class).shortDamage;
				else if(mediumHit) ((Entity) obj.userData).getComponent(EnemyComponent.class).health -= gun.getComponent(WeaponComponent.class).mediumDamage;
				else if(longHit) ((Entity) obj.userData).getComponent(EnemyComponent.class).health -= gun.getComponent(WeaponComponent.class).longDamage;
				if (gameWorld.game.client != null && !gameWorld.game.dinoSpawner) {
					//The correct command will look something like this but currently dinos don't have an avatar component so use placeholder fire command below
					//gameWorld.game.client
					//		.sendMessage("\\fire " + ((Entity) obj.userData).getComponent(AvatarComponent.class).x + " " + ((Entity) obj.userData).getComponent(AvatarComponent.class).y +
					//				" " + ((Entity) obj.userData).getComponent(AvatarComponent.class).z + " " + ((Entity) obj.userData).getComponent(AvatarComponent.class).username + "\n");
					gameWorld.game.client
							.sendMessage("\\fire " + 0 + " " + 0 +
									" " + 0 + " " + "dino" + "\n");
				}
				if (((Entity) obj.userData).getComponent(EnemyComponent.class).health <= 0)
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
				// rotate door
				if (!((Entity) obj.userData).getComponent(DoorComponent.class).doorInfo.isOpen) {
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform.translate(5f, 0f, 0f);
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.calculateTransforms();
					((Entity) obj.userData).getComponent(BulletComponent.class).motionState = new MotionState(
							((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform);
					((btRigidBody) ((Entity) obj.userData).getComponent(BulletComponent.class).body)
							.setMotionState(((Entity) obj.userData).getComponent(BulletComponent.class).motionState);
				} else {
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform.translate(-5f, 0f,
							0f);
					((Entity) obj.userData).getComponent(ModelComponent.class).instance.calculateTransforms();
					((Entity) obj.userData).getComponent(BulletComponent.class).motionState = new MotionState(
							((Entity) obj.userData).getComponent(ModelComponent.class).instance.transform);
					((btRigidBody) ((Entity) obj.userData).getComponent(BulletComponent.class).body)
							.setMotionState(((Entity) obj.userData).getComponent(BulletComponent.class).motionState);
				}
				((Entity) obj.userData).getComponent(DoorComponent.class).doorInfo.isOpen = !((Entity) obj.userData)
						.getComponent(DoorComponent.class).doorInfo.isOpen;
			}
		}
	}

	public Entity getPlayer() {
		return player;
	}

	private void checkGameOver() {
		if (playerComponent.health <= 0 && !Settings.Paused) {
			Settings.Paused = true;
			gameUI.gameOverWidget.gameOver();
		}
	}

	@Override
	public void entityAdded(Entity entity) {
		if (entity.getComponent(PlayerComponent.class) != null) {
			player = entity;
			playerComponent = entity.getComponent(PlayerComponent.class);
			characterComponent = entity.getComponent(CharacterComponent.class);
			modelComponent = entity.getComponent(ModelComponent.class);
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
	}
}
