package com.deeep.spaceglad;

import com.badlogic.gdx.Gdx;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.components.AnimationComponent;
import com.deeep.spaceglad.components.AvatarComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.managers.EntityFactory;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.deeep.spaceglad.systems.*;
import com.deeep.spaceglad.Assets;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.deeep.spaceglad.Logger;
import com.badlogic.gdx.utils.Array;
import com.deeep.spaceglad.components.Door;
/**
 * Created by scanevaro on 31/07/2015.
 */
class Wall {
	String texture;
}
 
class Room {
	String name;
	float x;
	float y;
	float z;
	float w;
	float h;
	float l;
	String texture;
	Wall floor;
	Wall ceiling;
	Array<Door> Doors;
}
 
public class GameWorld {
    private static final float FOV = 67F;
    private ModelBatch modelBatch;
	
	private DebugDrawer debugDrawer;
	private static final boolean debug = false;
	
    private Environment environment;
    private PerspectiveCamera perspectiveCamera;
	private Json json = new Json();
	private JsonValue map;
	private Array<Room> Rooms;
	public Core game;
	//private Array<Door> Doors;
	
	/*
	private static Texture wallTexture;
	private static Material wallMaterial;
	private static Texture floorTexture;
	private static Material floorMaterial;
	private static Texture ceilingTexture;
	private static Material ceilingMaterial;
	
	
    static {
		
		wallTexture = new Texture(Gdx.files.internal("data/wall.png"));
        wallMaterial = new Material(TextureAttribute.createDiffuse(wallTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(4f));
		floorTexture = new Texture(Gdx.files.internal("data/floor.png"));
        floorMaterial = new Material(TextureAttribute.createDiffuse(floorTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(4f));
		ceilingTexture = new Texture(Gdx.files.internal("data/ceiling.png"));
        ceilingMaterial = new Material(TextureAttribute.createDiffuse(ceilingTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(4f));
    }
	*/
	
    private Engine engine;
    private Entity character, gun;
    public BulletSystem bulletSystem;
	public PlayerSystem playerSystem;
	public ShakeSystem shakeSystem;
	private RenderSystem renderSystem;
	public AvatarSystem avatarSystem;
    public ModelBuilder modelBuilder = new ModelBuilder();
	
	/*old chap3 code
    Model wallHorizontal = modelBuilder.createBox(40, 30, 1, wallMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    Model wallVertical = modelBuilder.createBox(1, 30, 40, wallMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    Model groundModel = modelBuilder.createBox(40, 1, 40, floorMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
	Model ceilingModel = modelBuilder.createBox(40, 1, 40, ceilingMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
	*/
	
    public GameWorld(GameUI gameUI, Core game) {
    	this.game = game;
        Bullet.init();
		setDebug();
        //initEnvironment();
        //initModelBatch();
        //initPersCamera();
        addSystems(gameUI);
        addEntities();
    }
	
	private void setDebug()
	{
		if (debug)
		{
			debugDrawer = new DebugDrawer();
			debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
		}
	}

//    private void initEnvironment() {
//        environment = new Environment();
//        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.f));
//    }

//    private void initPersCamera() {
//        perspectiveCamera = new PerspectiveCamera(FOV, Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
//        perspectiveCamera.position.set(30f, 40f, 30f);
//        perspectiveCamera.lookAt(0, 0, 0);
//        perspectiveCamera.near = 1f;
//        perspectiveCamera.far = 300f;
//        perspectiveCamera.update();
//   }

//    private void initModelBatch() {
//        modelBatch = new ModelBatch();
//    }
	public void addPlayer(String username,float x, float y, float z, float angle) {
    	Entity player = (EntityFactory.createAvatar(bulletSystem, x, y, z));
    	player.getComponent(AvatarComponent.class).username = username;
    	player.getComponent(AvatarComponent.class).x = x;
        player.getComponent(AvatarComponent.class).y = y;
        player.getComponent(AvatarComponent.class).z = z;
        player.getComponent(AvatarComponent.class).rotAngle = angle;
        //TODO add avatar color
    	engine.addEntity(player);
	}

    private void addEntities() {
        createGround();
        createPlayer(0, 1, 0);
    }

    private void createPlayer(float x, float y, float z) {
        character = EntityFactory.createPlayer(bulletSystem, x, y, z);
        if (game.client != null) {
        	character.getComponent(PlayerComponent.class).username = game.client.username;
        } else {
        	character.getComponent(PlayerComponent.class).username = "Player";
        }
        engine.addEntity(character);
		engine.addEntity(gun = EntityFactory.loadGun(2.5f, -1.9f, -4));
		playerSystem.gun = gun;
        playerSystem.player = character;
		renderSystem.gun = gun;
        renderSystem.player = character;
    }

    private void createGround() {
        // Floo
        Texture tempTexture = new Texture(Gdx.files.internal("data/snow_ground.png"));
        Material tempMaterial = new Material(TextureAttribute.createDiffuse(tempTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(4f));
        Model tempModel = modelBuilder.createBox(1800, 2, 1800, tempMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        engine.addEntity(EntityFactory.createStaticEntity(tempModel, 0, 0, 0, 0f));

        // Trees
        engine.addEntity(EntityFactory.createVisualEntity(Assets.level1skymodel, 0f, -50f, 0f, 0f));
        for (int i = 0; i < 50; i++) {
            float x = 5 * i - 20;
            float z = i % 4;
            float y = -i % 8;
            engine.addEntity(EntityFactory.createTreeEntity(Assets.level1treemodel, x, y, z, 0f));
        }

        for (int i = 0; i < 50; i++ ) {
            float x = 5 * i - 20;
            float z = i % 4 - 45;
            float y = -i % 8;
            engine.addEntity(EntityFactory.createTreeEntity(Assets.level1treemodel, x, y, z, 0f));
        }

        for (int i = 0; i < 10; i++ ) {
            float z = -i * 5;
            float y = -i % 8;
            engine.addEntity(EntityFactory.createTreeEntity(Assets.level1treemodel, -20, y, z, 0f));
        }
    }

    private void addSystems(GameUI gameUI) {
        engine = new Engine();
		
		EntityFactory.renderSystem = renderSystem;
		
		engine.addSystem(renderSystem = new RenderSystem());
        engine.addSystem(bulletSystem = new BulletSystem());
        engine.addSystem(shakeSystem = new ShakeSystem(this, renderSystem.perspectiveCamera));
		engine.addSystem(playerSystem = new PlayerSystem(this, gameUI, renderSystem.perspectiveCamera));
		
		//engine.addSystem(new PlayerSystem(this, gameUI, perspectiveCamera));
		
        engine.addSystem(new EnemySystem(this, renderSystem.perspectiveCamera));
        engine.addSystem(new StatusSystem(this));
        engine.addSystem(new HealthPackSystem(this));
        engine.addSystem(avatarSystem = new AvatarSystem(this));
		
		if (debug)
		{
			bulletSystem.collisionWorld.setDebugDrawer(this.debugDrawer);
		}
    }

    public void render(float delta) {
        renderWorld(delta);
        checkPause();
    }

    private void checkPause() {
        if (Settings.Paused) {
            engine.getSystem(PlayerSystem.class).setProcessing(false);
        } else {
            engine.getSystem(PlayerSystem.class).setProcessing(true);
            engine.getSystem(EnemySystem.class).setProcessing(true);
            engine.getSystem(StatusSystem.class).setProcessing(true);
            engine.getSystem(BulletSystem.class).setProcessing(true);
            engine.getSystem(HealthPackSystem.class).setProcessing(true);
            engine.getSystem(AvatarSystem.class).setProcessing(true);
        }
    }

    protected void renderWorld(float delta) {
       //modelBatch.begin(perspectiveCamera);
        //engine.update(delta);
       //modelBatch.end();
	   
	   engine.update(delta);
	   
	   if (debug)
	   {
			debugDrawer.begin(renderSystem.perspectiveCamera);
			bulletSystem.collisionWorld.debugDrawWorld();
			debugDrawer.end();
	   }
    }

    public void resize(int width, int height) {
		renderSystem.resize(width, height);
        //perspectiveCamera.viewportHeight = height;
        //perspectiveCamera.viewportWidth = width;
    }

    public void dispose() {
        bulletSystem.collisionWorld.removeAction(character.getComponent(CharacterComponent.class).characterController);
        bulletSystem.collisionWorld.removeCollisionObject(character.getComponent(CharacterComponent.class).ghostObject);
        bulletSystem.dispose();
        bulletSystem = null;
		renderSystem.dispose();
		
		
		/*
        wallHorizontal.dispose();
        w allVertical.dispose();
        groundModel.dispose();
        modelBatch.dispose();
		*/ 
		
        modelBatch = null;
        character.getComponent(CharacterComponent.class).characterController.dispose();
        character.getComponent(CharacterComponent.class).ghostObject.dispose();
        character.getComponent(CharacterComponent.class).ghostShape.dispose();
    }

    public void remove(Entity entity) {
        engine.removeEntity(entity);
        bulletSystem.removeBody(entity);
    }
}
