package com.deeep.spaceglad.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.deeep.spaceglad.bullet.MotionState;
import com.deeep.spaceglad.components.*;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.RenderSystem;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.Assets;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.Random;

/**
 * Created by Elmar on 7-8-2015.
 */
public class EntityFactory {
    private static Model playerModel;
    private static Texture playerTexture;
    private static ModelBuilder modelBuilder;
    private static Model boxModel;
    public static RenderSystem renderSystem;

    static {
        modelBuilder = new ModelBuilder();
        playerTexture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
        Material material = new Material(TextureAttribute.createDiffuse(playerTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
        playerModel = modelBuilder.createCapsule(2f, 4f, 16, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        boxModel = modelBuilder.createBox(1f, 1f, 1f, new Material(ColorAttribute.createDiffuse(Color.WHITE),
                ColorAttribute.createSpecular(Color.WHITE), FloatAttribute.createShininess(64f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }


    public static Entity createStaticEntity(Model model, float x, float y, float z, float rotateDeg) {
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f, boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.rotate(0f, 0f, 1f, rotateDeg);
		modelComponent.instance.calculateTransforms();
        entity.add(modelComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        return entity;
    }
	
	public static Entity createDoorEntity(Model model, float x, float y, float z, float rotateDeg, Door d) {
		final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f, boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.rotate(0f, 1f, 0f, rotateDeg);
		modelComponent.instance.calculateTransforms();
        entity.add(modelComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        entity.add(new DoorComponent(d));
        return entity;
	}

    public static Entity createVisualEntity(Model model, float x, float y, float z, float rotateDeg){
                Entity entity = new Entity();
                ModelComponent modelComponent = new ModelComponent(model, x, y, z);
                modelComponent.instance.transform.rotate(0f, 0f, 1f, rotateDeg);
                modelComponent.instance.calculateTransforms();
                entity.add(modelComponent);
                return entity;
           }

    public static Entity createTreeEntity(Model model, float x, float y, float z, float rotateDeg){
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btCylinderShape(tmpV.set(boundingBox.getWidth() * 0.15f, boundingBox.getHeight() * 0.9f, boundingBox.getDepth() * 0.5f));
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.rotate(0f, 0f, 1f, rotateDeg);
		modelComponent.instance.calculateTransforms();
        entity.add(modelComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        return entity;
       }

       public static Entity createRampEntity(Model model, float x, float y, float z, float rotateDeg){
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btConvexHullShape();
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.rotate(0f, 0f, 1f, rotateDeg);
		modelComponent.instance.calculateTransforms();
        entity.add(modelComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        return entity;
       }

    private static Entity createCharacter(BulletSystem bulletSystem, float x, float y, float z, int type, float dinoScale) {
        Entity entity = new Entity();
	ModelComponent modelComponent = null;
	switch(type) {
		case 0: //player
            switch(Assets.avColor){
                case 1: //red
                    modelComponent = new ModelComponent(Assets.playerModelRed, x, y, z);
                    break;
                case 2: //orange
                    modelComponent = new ModelComponent(Assets.playerModelOrange, x, y, z);
                    break;
                case 3: //yellow
                    modelComponent = new ModelComponent(Assets.playerModelYellow, x, y, z);
                    break;
                case 4: //green
                    modelComponent = new ModelComponent(Assets.playerModelGreen, x, y, z);
                    break;
                case 5: //blue
                    modelComponent = new ModelComponent(Assets.playerModelBlue, x, y, z);
                    break;
                case 6: //indigo
                    modelComponent = new ModelComponent(Assets.playerModelIndigo, x, y, z);
                    break;
                case 7: //violet
                    modelComponent = new ModelComponent(Assets.playerModelViolet, x, y, z);
                    break;
                case 8: //gray
                    modelComponent = new ModelComponent(Assets.playerModelGray, x, y, z);
                    break;
                case 9: //black
                    modelComponent = new ModelComponent(Assets.playerModelBlack, x, y, z);
                    break;

            }
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
			
			//entity.add(new WeaponComponent(1)); // gives player "spear", for testing
			
		break;

		//Nick A for final sprint
			case 1: //anklyo model
				modelComponent = new ModelComponent(Assets.anklyoModel, x, y, z);
				for (Node node : modelComponent.instance.nodes) node.scale.scl(dinoScale); // scale the model -Paul
					modelComponent.instance.transform.rotate(0, 1, 0, 0);
					modelComponent.instance.calculateTransforms();
				entity.add(new AnimationComponent(modelComponent.instance));
			break;
			case 2: //raptor model	
				modelComponent = new ModelComponent(Assets.raptorModel, x, y, z);
				for (Node node : modelComponent.instance.nodes) node.scale.scl(dinoScale); // scale the model -Paul
					modelComponent.instance.transform.rotate(0, 1, 0, 0);
					modelComponent.instance.calculateTransforms();
				entity.add(new AnimationComponent(modelComponent.instance));
			break;
			//end
        case 11:
            modelComponent = new ModelComponent(Assets.playerModelRed, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 12:
            modelComponent = new ModelComponent(Assets.playerModelOrange, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 13:
            modelComponent = new ModelComponent(Assets.playerModelYellow, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 14:
            modelComponent = new ModelComponent(Assets.playerModelGreen, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 15:
            modelComponent = new ModelComponent(Assets.playerModelBlue, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 16:
            modelComponent = new ModelComponent(Assets.playerModelIndigo, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 17:
            modelComponent = new ModelComponent(Assets.playerModelViolet, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 18:
            modelComponent = new ModelComponent(Assets.playerModelGray, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
        case 19:
            modelComponent = new ModelComponent(Assets.playerModelBlack, x, y, z);
            for (Node node : modelComponent.instance.nodes) node.scale.scl(3.8f); // scale the model -Paul
            modelComponent.instance.transform.rotate(0, 1, 0, 0);
            modelComponent.instance.calculateTransforms();
            break;
    }
	if(modelComponent != null)
		entity.add(modelComponent);
        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.ghostObject = new btPairCachingGhostObject();
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        characterComponent.ghostShape = new btCapsuleShape(2f, 2f);
	//Nick A for final sprint
	if(type == 1 | type == 2)
	{
		characterComponent.ghostShape = new btCapsuleShape((2f*dinoScale)-1f, (2f*dinoScale)-1f);
	}
        characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
        //characterComponent.ghostObject.setCollisionShape(Bullet.obtainStaticNodeShape(modelComponent.instance.nodes));
        characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, .35f);
        characterComponent.ghostObject.userData = entity;
        entity.add(characterComponent);
        bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CharacterComponent.class).ghostObject,
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        bulletSystem.collisionWorld.addAction(entity.getComponent(CharacterComponent.class).characterController);

        entity.add(new AnimationComponent(modelComponent.instance)); //Avatar animation -paul

        return entity;
    }

    public static Entity createPlayer(BulletSystem bulletSystem, float x, float y, float z) {
        Entity entity = createCharacter(bulletSystem, x, y, z, 0, 0);
        entity.add(new PlayerComponent());
        return entity;
    }


    public static Entity createAvatar(BulletSystem bulletSystem, float x, float y, float z) {
        Random rand = new Random();
        int color = rand.nextInt((9 - 1) + 1) + 1;

        Entity entity = createCharacter(bulletSystem, x, y, z, color+10, 0);
        entity.add(new AvatarComponent());
        entity.getComponent(AvatarComponent.class).x = x;
        entity.getComponent(AvatarComponent.class).y = y;
        entity.getComponent(AvatarComponent.class).z = z;
        return entity;
    }
	
	// Handles creating health packs
	public static Entity createHealthPack(BulletSystem bulletSystem, float x, float y, float z) {
        Entity entity = new Entity();
		final BoundingBox boundingBox = new BoundingBox();
		Model model = new Model();
		model = Assets.healthPackModel;
		model.calculateBoundingBox(boundingBox);
		
		ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.scale(0.1f, 0.1f, 0.1f);
		modelComponent.instance.calculateTransforms();
		//modelComponent.calculateBoundingBox(boundingBox);
		
		HealthPackComponent healthPackComponent = new HealthPackComponent(HealthPackComponent.STATE.READY, 1);
		
		healthPackComponent.ghostObject = new btPairCachingGhostObject();
        healthPackComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        healthPackComponent.ghostShape = new btCapsuleShape(2f, 2f);
        healthPackComponent.ghostObject.setCollisionShape(healthPackComponent.ghostShape);
        healthPackComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
		
		
		entity.add(healthPackComponent);
		
		
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.3f, boundingBox.getHeight() * 0.3f, boundingBox.getDepth() * 0.3f));
		
		
		BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
		
		
		
		entity.add(modelComponent);
		
		
		
        
        return entity;
    }
	
	public static Entity createWeapon(BulletSystem bulletSystem, float x, float y, float z, int weaponType){
		Entity entity = new Entity();
		final BoundingBox boundingBox = new BoundingBox();
		
		Model model = new Model();
		switch(weaponType){
			case 0://spear
				model = Assets.spearModel;
			break;
			case 1:
				ModelLoader<?> modelLoader = new G3dModelLoader (new JsonReader());
				ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/GUNMODEL.g3dj"));
				model = new Model(modelData, new TextureProvider.FileTextureProvider());
				//gun
			break;
			case 2: //shotgun
				model = Assets.shotgunModel;
			break;
		}
		model.calculateBoundingBox(boundingBox);
		
		ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		switch(weaponType){
			case 0: // spear scale
				modelComponent.instance.transform.scale(0.01f, 0.01f, 0.01f);
			break;
			case 1:
				modelComponent.instance.transform.scale(0.25f, 0.25f, 0.25f);
			break;
			case 2: // shotgun scale
				modelComponent.instance.transform.scale(0.01f, 0.01f, 0.01f);
			break;
		}
		
		modelComponent.instance.calculateTransforms();
		
		WeaponComponent weaponComponent = new WeaponComponent(weaponType);
		
		weaponComponent.ghostObject = new btPairCachingGhostObject();
        weaponComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        weaponComponent.ghostShape = new btCapsuleShape(2f, 2f);
        weaponComponent.ghostObject.setCollisionShape(weaponComponent.ghostShape);
        weaponComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
		
		entity.add(weaponComponent);
		
		Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.3f, boundingBox.getHeight() * 0.3f, boundingBox.getDepth() * 0.3f));
		
		
		BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        
		entity.add(bulletComponent);
		
		entity.add(modelComponent);
		
		return entity;
	}


    public static Entity createEnemy(BulletSystem bulletSystem, float x, float y, float z, int type, float scale) {
        if (type != 1 && type != 2)
		  type = 1;
	Entity entity = createCharacter(bulletSystem, x,y,z, type, scale);
        entity.add(new EnemyComponent(EnemyComponent.STATE.HUNTING,type));
        entity.add(new AvatarComponent());
        entity.add(new StatusComponent());
        entity.add(new DieParticleComponent(renderSystem.particleSystem));
        entity.getComponent(AvatarComponent.class).x = x;
        entity.getComponent(AvatarComponent.class).y = y;
        entity.getComponent(AvatarComponent.class).z = z;
        entity.getComponent(EnemyComponent.class).type = type;
        entity.getComponent(EnemyComponent.class).scale = scale;
	    //Nick A for HW#6
		if(entity.getComponent(AnimationComponent.class) != null && entity.getComponent(AnimationComponent.class).getController() != null)
		{
			if(entity.getComponent(AnimationComponent.class).getController().current == null)
				entity.getComponent(AnimationComponent.class).animate("Armature|walk", -1, 3);
		}
		//end
        return entity;
    }
	
	public static Entity loadGun(float x, float y, float z)
	{
		ModelLoader<?> modelLoader = new G3dModelLoader (new JsonReader());
		ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/GUNMODEL.g3dj"));
		//ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("data/shotgun.g3dj")); // shotgun model
		Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
		ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		modelComponent.instance.transform.rotate(0, 1, 0, 180);
		//modelComponent.instance.transform.rotate(0, 1, 0, 90); // For shotgun model
		//modelComponent.instance.transform.scale(0.03f, 0.03f, 0.03f); // For shotgun model
		WeaponComponent weaponComponent = new WeaponComponent(1);
		Entity gunEntity = new Entity();
		gunEntity.add(modelComponent);
		gunEntity.add(new GunComponent());
		gunEntity.add(new AnimationComponent(modelComponent.instance));
		gunEntity.add(weaponComponent);
		return gunEntity;
	}

//    public static void dispose() {
//        playerModel.dispose();
//        playerTexture.dispose();
//        boxModel.dispose();
//    }
}

