package com.deeep.spaceglad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;

/**
 * Created by scanevaro on 01/08/2015.
 */
public class Assets {
    public static Skin skin;
	public static Model tvModel;
	public static Model chairModel;
	public static Model enemyModel;
	public static Model playerModel;
	
	
    public Assets() {
        skin = new Skin();
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
		
		FileHandle tvPath = Gdx.files.internal("data/tv.g3db");
		FileHandle chairPath = Gdx.files.internal("data/chair.g3db");
		FileHandle enemyPath = Gdx.files.internal("data/enemy.g3db");
		FileHandle playerPath = Gdx.files.internal("data/avatar.g3db"); //Avatar -Paul
		UBJsonReader jsonReader = new UBJsonReader();
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		
		tvModel = modelLoader.loadModel(tvPath);
		chairModel = modelLoader.loadModel(chairPath);
		enemyModel = modelLoader.loadModel(enemyPath);
		playerModel = modelLoader.loadModel(playerPath);
    }

    public static void dispose() {
        skin.dispose();
    }
}
