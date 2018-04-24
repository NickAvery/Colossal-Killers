package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Andreas on 8/5/2015.
 */
public class EnemyComponent extends Component {

    public enum STATE {
        IDLE,
        FLEEING,
        HUNTING
    }
    public float health;
    public STATE state = STATE.IDLE;

     public EnemyComponent(STATE state, int type){
        this.state = state;
		switch(type) {
			case 1: //ankylo
				health = 100;
			break;
			case 2: //raptor
				health = 50;
			break;
			default:
				health = 10;
			break;
		}
    }
}