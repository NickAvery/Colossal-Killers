package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

/**
 * Created by Andreas on 8/5/2015.
 */
public class EnemyComponent extends Component {

    public enum STATE {
        IDLE,
        FLEEING,
        HUNTING
    }

    public enum DINOTYPE{
    	RAPTOR,
    	ANKLO
    }

    public Music footStep;
    public float health;
    public STATE state = STATE.IDLE;
    public DINOTYPE dinoType;
    public int type;
    public float scale;
    public String username;
    public Entity target;

     public EnemyComponent(STATE state, int type){
        this.state = state;
		switch(type) {
			case 1: //ankylo
				health = 100;
				footStep = Gdx.audio.newMusic(Gdx.files.internal("data/shortDinoStep.mp3"));
                footStep.setLooping(false);
                dinoType = DINOTYPE.ANKLO;

			break;
			case 2: //raptor
				footStep = Gdx.audio.newMusic(Gdx.files.internal("data/raptor.mp3"));
                footStep.setLooping(true);
				health = 50;
                dinoType = DINOTYPE.RAPTOR;
			break;
			default:
				health = 10;
			break;
		}
    }
}
