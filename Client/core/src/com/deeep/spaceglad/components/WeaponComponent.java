package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class WeaponComponent extends Component {

	public btPairCachingGhostObject ghostObject;
	public btConvexShape ghostShape;
	//public btCollisionObject body;


    public enum STATE {
        READY,
		RESPAWNING
    }
	
	public enum WEAPON {
		SPEAR,
		GUN,
		SHOTGUN
	}
	
    public STATE state = STATE.READY;
	public WEAPON weapon;
	public float longRange;
	public float mediumRange;
	public float shortRange;
	public float longDamage;
	public float mediumDamage;
	public float shortDamage;
	public float respawnTime = 10f;
	public float respawnDelta = 0f;
	public int ammoAmount;
	public boolean despawnFlag = false;
	public boolean respawnFlag = false;
	
    public WeaponComponent(int type){
		switch(type) {
			case 0: //spear
				weapon = WEAPON.SPEAR;
				longRange = 5f;
				mediumRange = 5f;
				shortRange = 5f;
				longDamage = 10f;
				mediumDamage = 10f;
				shortDamage = 10f;
				ammoAmount = 1;
			break;
			case 1: //Gun
				weapon = WEAPON.GUN;
				longRange = 50f;
				mediumRange = 30f;
				shortRange = 20f;
				longDamage = 5f;
				mediumDamage = 10f;
				shortDamage = 15f;
				ammoAmount = 10;
			break;
			
			case 2: //Shotgun
				weapon = WEAPON.SHOTGUN;
				longRange = 40f;
				mediumRange = 20f;
				shortRange = 10f;
				longDamage = 5f;
				mediumDamage = 10f;
				shortDamage = 25f;
				ammoAmount = 6;
			break;
			
			default:
				weapon = WEAPON.SPEAR;
			break;
		}
    }
}
