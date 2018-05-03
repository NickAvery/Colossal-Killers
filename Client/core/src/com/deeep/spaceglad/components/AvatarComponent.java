package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class AvatarComponent extends Component {
    public float x;
    public float y;
    public float z;
    public String username;
    public float health;

    public AvatarComponent() {
        health = 100;
    }
}
