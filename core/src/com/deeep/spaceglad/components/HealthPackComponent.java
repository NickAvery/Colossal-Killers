package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class HealthPackComponent extends Component {

    public enum STATE {
        READY,
		RESPAWNING
    }
    public float health;
    public STATE state = STATE.READY;

     public HealthPackComponent(STATE state, int type){
        this.state = state;
		switch(type) {
			case 1: //BIG health pack
				health = 50;
			break;
			case 2: //OKAY health pack
				health = 25;
			break;
			default: //SMALL health pack
				health = 10;
			break;
		}
    }
}
