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
import java.util.Random;
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
        player.getComponent(AvatarComponent.class).rotAngle = angle;
        //TODO add avatar color
    	engine.addEntity(player);
	}

	public void addEnemy(String username, float x, float y, float z, float angle, int type, float scale) {
        Entity enemy = (EntityFactory.createEnemy(bulletSystem, x, y, z, type, scale));
        enemy.getComponent(AvatarComponent.class).username = username;
        enemy.getComponent(AvatarComponent.class).rotAngle = angle;
        enemy.getComponent(AvatarComponent.class).type = type;
        enemy.getComponent(AvatarComponent.class).getSound();
        engine.addEntity(enemy);
    }

    private void addEntities() {
        createGround();
        Random rand = new Random();
        int spawnPoint = rand.nextInt((9 - 1) + 1) + 1;
        if(!game.dinoSpawner) {
            if(spawnPoint == 1)
                createPlayer(0, 10, 0);
            else if(spawnPoint == 2)
                createPlayer(-10, 10, 0);
            else if(spawnPoint == 3)
                createPlayer(-20, 10, 0);
            else if(spawnPoint == 4)
                createPlayer(10, 10, -10);
            else if(spawnPoint == 5)
                createPlayer(20, 10, 0);
            else if(spawnPoint == 6)
                createPlayer(0, 10, -10);
            else if(spawnPoint == 7)
                createPlayer(0, 10, -20);
            else if(spawnPoint == 8)
                createPlayer(0, 10, 10);
            else //spawnPoint 9
                createPlayer(0, 10, 20);
        }
        else
            createPlayer(0, -1000 , 0);

		engine.addEntity(EntityFactory.createHealthPack(bulletSystem, 0, 2, 30));
		engine.addEntity(EntityFactory.createWeapon(bulletSystem, 0, 3, 40, 2));
		engine.addEntity(EntityFactory.createWeapon(bulletSystem, 0, 3, 50, 0));
		engine.addEntity(EntityFactory.createWeapon(bulletSystem, 0, 3, 60, 1));

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
        
        // Floor
        Texture tempTexture = new Texture(Gdx.files.internal("data/snow_ground.png"));
        Material tempMaterial = new Material(TextureAttribute.createDiffuse(tempTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(4f));
        for (int j = -10; j < 10; j++)
        {
            float z = 180 * j;
        for (int i = -10; i < 10; i++)
        {
            float x = 180 * i;
            Model tempModel = modelBuilder.createBox(180, 2, 180, tempMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
            engine.addEntity(EntityFactory.createStaticEntity(tempModel, x, 0, z, 0f));
        }
        }

        //rock exterior (bounds the level)
        for (int i = 0; i < 20; i++)
        {
            float z = -1000 +85 * i;
            float x = -600;
            engine.addEntity(EntityFactory.createStaticEntity(Assets.level1rockmodel1, x, -20, z, 0f));  
             x = 600;
            engine.addEntity(EntityFactory.createStaticEntity(Assets.level1rockmodel1, x, -20, z, 0f)); 
        }

        //rock map divider
        for (int i = 0; i < 5; i++)
        {
            float z = 150 + 55 * i;
            float x = 150 + 55 * i;
            engine.addEntity((EntityFactory.createStaticEntity((Assets.level1rockmodel1), x, -45, z, 0f)));
            z = -150 - 55 * i;
            x = -150 - 55 * i;
            engine.addEntity((EntityFactory.createStaticEntity((Assets.level1rockmodel1), x, -45, z, 0f)));
        }
        

        for (int i = 0; i < 20; i++)
        {
            float x = -1000 +85 * i;
            float z = -600;
            engine.addEntity(EntityFactory.createStaticEntity(Assets.level1rockmodel1, x, -20, z, 0f)); 
            z = 600;
            engine.addEntity(EntityFactory.createStaticEntity(Assets.level1rockmodel1, x, -20, z, 0f)); 
        }
          
        
        engine.addEntity(EntityFactory.createVisualEntity(Assets.level1skymodel, 0f, -25f, 0f, 0f));
        
        
        //Distribute some Trees throughout the game world     
        for (int i = 0; i < 75; i++) {
            Random rand = new Random();
            float x = rand.nextInt(1200)- 600;
            float z = rand.nextInt(1200)-600;
            float y = -i % 8;
            engine.addEntity(EntityFactory.createTreeEntity(Assets.level1treemodel, x, y, z, 0f));
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
		
        engine.addSystem(new EnemySystem(this));
        engine.addSystem(new StatusSystem(this));
        engine.addSystem(new HealthPackSystem(this));
		engine.addSystem(new WeaponSystem(this));
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
			engine.getSystem(WeaponSystem.class).setProcessing(true);
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
