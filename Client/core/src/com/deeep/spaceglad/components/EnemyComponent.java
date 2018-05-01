package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
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

    public Music footStep;
    public float health;
    public STATE state = STATE.IDLE;

     public EnemyComponent(STATE state, int type){
        this.state = state;
		switch(type) {
			case 1: //ankylo
				health = 100;
				footStep = Gdx.audio.newMusic(Gdx.files.internal("data/dinoFootstep.mp3"));
			break;
			case 2: //raptor
				footStep = Gdx.audio.newMusic(Gdx.files.internal("data/raptor.mp3"));
				health = 50;
			break;
			default:
				health = 10;
			break;
		}
    }
}
