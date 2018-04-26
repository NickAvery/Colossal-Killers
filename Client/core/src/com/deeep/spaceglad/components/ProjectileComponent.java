package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Andreas on 8/5/2015.
 */
public class ProjectileComponent extends Component {
	public Vector3 direction;
	
    public ProjectileComponent(Vector3 direction){
    	this.direction = new Vector3(direction);
   	}
}
