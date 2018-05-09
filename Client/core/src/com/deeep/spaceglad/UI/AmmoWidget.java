package com.deeep.spaceglad.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.components.PlayerComponent;

/**
 * Created by scanevaro on 04/08/2015.
 */
public class AmmoWidget extends Actor {
    Label label;

    public AmmoWidget() {
        label = new Label("", Assets.skin);
    }

    @Override
    public void act(float delta) {
        label.act(delta);
		int ammo = PlayerComponent.ammoHeld[PlayerComponent.weapon];
		String output = Integer.toString(ammo);
		//Gdx.app.log("Ammo", output);
        label.setText("Ammo : " + ammo);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        label.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        label.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        label.setSize(width, height);
    }
}