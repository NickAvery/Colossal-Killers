package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Elmar on 8-8-2015.
 */
public class PlayerComponent extends Component {

    //    public float energy;
//    public float oxygen;
    public float health;
    public static int score;
    public String username;
    public float x;
    public float y;
    public float z;
		public static int weapon;
	public static int ammoHeld[] = new int [10]; //allows for 10 different ammo types
	
    public PlayerComponent() {
//        energy = 100;
//        oxygen = 100;
        health = 100;
        score = 0;
		weapon = 1;
		ammoHeld[0] = 1;
		ammoHeld[1] = 20;
		ammoHeld[2] = 6;
    }
}