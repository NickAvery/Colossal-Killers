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
	public static Model anklyoModel;
	public static Model raptorModel;
	public static Model playerModel;
	public static Model level1skymodel;
	public static Model level1groundModel;
	public static Model level1rockmodel1;
	public static Model level1treemodel;
	public static Model ramp1model;
	public static Model healthPackModel;
	public static Model playerModelRed;
	public static Model playerModelOrange;
	public static Model playerModelYellow;
	public static Model playerModelGreen;
	public static Model playerModelBlue;
	public static Model playerModelIndigo;
	public static Model playerModelViolet;
	public static Model playerModelGray;
	public static Model playerModelBlack;
	public static int avColor;
	
	
    public Assets() {
    	avColor = 1; // default to avatar 1, valid selections are 1-9 and correspond to ROYGBIV +gray +black -paul
        skin = new Skin();
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
		
		FileHandle tvPath = Gdx.files.internal("data/tv.g3db");
		FileHandle chairPath = Gdx.files.internal("data/chair.g3db");
		FileHandle anklyoPath = Gdx.files.internal("data/ANKYLO.g3db");//Nick A for HW#6
		FileHandle raptorPath = Gdx.files.internal("data/RAPTOR.g3db");//Nick A for HW#6
		FileHandle playerPath = Gdx.files.internal("data/avatar.g3db"); //Avatar -Paul
		FileHandle level1groundPath = Gdx.files.internal("data/level1ground.g3db");//level 1 groundmodel JT
		FileHandle level1skyPath = Gdx.files.internal("data/level1sky.g3db");	//level1 sky model JT
		FileHandle level1rockPath = Gdx.files.internal("data/rockyoutcrop.g3db");	//levle1 rockyoutcrop JT
		FileHandle level1treePath = Gdx.files.internal("data/tree1.g3db");
		FileHandle level1rampPath = Gdx.files.internal("data/ramp1.g3db");
		//FileHandle playerPath = Gdx.files.internal("data/avatars/red.g3db"); //Red avatar -Paul
		FileHandle redPlayerPath = Gdx.files.internal("data/avatars/red.g3db");
		FileHandle orangePlayerPath = Gdx.files.internal("data/avatars/orange.g3db");
		FileHandle yellowPlayerPath = Gdx.files.internal("data/avatars/yellow.g3db");
		FileHandle greenPlayerPath = Gdx.files.internal("data/avatars/green.g3db");
		FileHandle bluePlayerPath = Gdx.files.internal("data/avatars/blue.g3db");
		FileHandle indigoPlayerPath = Gdx.files.internal("data/avatars/indigo.g3db");
		FileHandle violetPlayerPath = Gdx.files.internal("data/avatars/violet.g3db");
		FileHandle grayPlayerPath = Gdx.files.internal("data/avatars/gray.g3db");
		FileHandle blackPlayerPath = Gdx.files.internal("data/avatars/black.g3db");
		FileHandle healthPackPath = Gdx.files.internal("data/health_pack.g3db");// health pack - James
		UBJsonReader jsonReader = new UBJsonReader();
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		
		tvModel = modelLoader.loadModel(tvPath);
		chairModel = modelLoader.loadModel(chairPath);
		anklyoModel = modelLoader.loadModel(anklyoPath);
		raptorModel = modelLoader.loadModel(raptorPath);
//playerModel = modelLoader.loadModel(playerPath); //avatar -Paul
		level1skymodel = modelLoader.loadModel(level1skyPath);	//level1skyjt
		level1groundModel = modelLoader.loadModel(level1groundPath);	//level1ground JT
		level1rockmodel1 = modelLoader.loadModel(level1rockPath);	//level1rock JT

		level1treemodel = modelLoader.loadModel(level1treePath);
		ramp1model = modelLoader.loadModel(level1rampPath);
		playerModelRed = modelLoader.loadModel(redPlayerPath);
		playerModelOrange = modelLoader.loadModel(orangePlayerPath);
		playerModelYellow = modelLoader.loadModel(yellowPlayerPath);
		playerModelGreen = modelLoader.loadModel(greenPlayerPath);
		playerModelBlue = modelLoader.loadModel(bluePlayerPath);
		playerModelIndigo = modelLoader.loadModel(indigoPlayerPath);
		playerModelViolet = modelLoader.loadModel(violetPlayerPath);
		playerModelGray = modelLoader.loadModel(grayPlayerPath);
		playerModelBlack = modelLoader.loadModel(blackPlayerPath);
		healthPackModel = modelLoader.loadModel(healthPackPath); // health pack - James
    }

    public static void dispose() {
        skin.dispose();
    }
}
