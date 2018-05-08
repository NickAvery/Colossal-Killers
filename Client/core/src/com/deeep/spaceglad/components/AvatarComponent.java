package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;

public class AvatarComponent extends Component {
    public float x;
    public float y;
    public float z;
    public float rotAngle;
    public String username;
    public float health;

    public int color;

    public AvatarComponent() {
        health = 100;
        //default color is red
        color = 11; //colors 11 through 19 are available for teammates (ROYGBIV G B) -Paul
    }
}
