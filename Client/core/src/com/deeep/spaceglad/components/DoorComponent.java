package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.deeep.spaceglad.components.Door;
/**
 * Created by Andreas on 8/12/2015.
 */
public class DoorComponent extends Component{
	public Door doorInfo;
	public DoorComponent(Door d) {
		doorInfo = d;
	}
}
