package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.components.*;

/**
 * Created by Andreas on 8/4/2015.
 */
public class RenderSystem extends EntitySystem {
	private static final float FOV = 67f;
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;
	public PerspectiveCamera perspectiveCamera, gunCamera;
	public Entity gun;
	public Entity player;
	public static ParticleSystem particleSystem;


    public RenderSystem() {
        //this.batch = batch;
        //this.environment = environment;
		
		perspectiveCamera = new PerspectiveCamera(FOV, Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
		perspectiveCamera.far = 10000f;
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
		batch = new ModelBatch();
		gunCamera = new PerspectiveCamera(FOV, Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
		gunCamera.far = 100f;

        particleSystem = ParticleSystem.get();
        BillboardParticleBatch billboardParticleBatch = new
        BillboardParticleBatch();
        billboardParticleBatch.setCamera(perspectiveCamera);
        particleSystem.add(billboardParticleBatch);
    }

    // Event called when an entity is added to the engine
    public void addedToEngine(Engine e) {
        // Grabs all entities with desired components
        entities = e.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    public void update(float delta) {
        //for (int i = 0; i < entities.size(); i++) {
        //    ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
        //    batch.render(mod.instance, environment);
        //}
		drawModels();
    }
	
	private void drawModels()
	{
		batch.begin(perspectiveCamera);
		for(int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i).getComponent(GunComponent.class) == null)
			{
				ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
				batch.render(mod.instance, environment);

				if(entities.get(i).getComponent(PlayerComponent.class) != null) //-paul
				{
					player.getComponent(AnimationComponent.class).update(Gdx.graphics.getDeltaTime()); //update animation for avatars -paul
				}
			}
			//Nick A for HW#6
			if (entities.get(i).getComponent(EnemyComponent.class) != null && entities.get(i).getComponent(AnimationComponent.class) != null)
			{
				entities.get(i).getComponent(AnimationComponent.class).update(Gdx.graphics.getDeltaTime());
			}
			//end
		}
		batch.end();
		renderParticleEffects();
		drawGun();
	}

	private void drawGun()
	{
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin(gunCamera);
		batch.render(gun.getComponent(ModelComponent.class).instance);
		gun.getComponent(AnimationComponent.class).update(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	private void renderParticleEffects() {
		batch.begin(perspectiveCamera);
		particleSystem.update(); /* technically not necessary for
		rendering*/
		particleSystem.begin();
		particleSystem.draw();
		particleSystem.end();
		batch.render(particleSystem);
		batch.end();
	}
	
	public void resize(int width, int height)
	{
		perspectiveCamera.viewportHeight = height;
		perspectiveCamera.viewportWidth = width;
		gunCamera.viewportHeight = height;
		gunCamera.viewportWidth = width;
	}
	
	public void dispose()
	{
		batch.dispose();
		batch = null;
	}
}
