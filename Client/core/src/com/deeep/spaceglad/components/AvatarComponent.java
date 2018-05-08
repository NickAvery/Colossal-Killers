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

    public AvatarComponent() {
        health = 100;
    }
}
