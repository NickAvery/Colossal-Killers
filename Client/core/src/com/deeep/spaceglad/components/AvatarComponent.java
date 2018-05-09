package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class AvatarComponent extends Component {
    public float x;
    public float y;
    public float z;
    public float rotAngle;
    public String username;
    public float health;
    public Music footStep;
    public int color;
    public int type;

    public AvatarComponent() {
        health = 100;
        //default color is red
        color = 11; //colors 11 through 19 are available for teammates (ROYGBIV G B) -Paul
    }

    public void getSound(){
        System.out.print(type);   // "5"
        switch(type) {
            case 1: //ankylo
                footStep = Gdx.audio.newMusic(Gdx.files.internal("data/shortDinoStep.mp3"));
                footStep.setLooping(false);

            break;
            case 2: //raptor
                footStep = Gdx.audio.newMusic(Gdx.files.internal("data/raptor.mp3"));
                footStep.setLooping(true);
            break;
            default:
                //nothing
            break;
        }
    }
}
