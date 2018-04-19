package com.deeep.spaceglad.UI;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;
import com.deeep.spaceglad.Assets;

/**
 * Created by scanevaro on 04/08/2015.
 */
public class MessageWidget extends Actor {
	Label label;

	private ArrayList<String> messages;
	private float ttl = 0;
	private final float TTL_MAX = 5;

	public MessageWidget() {
		label = new Label("", Assets.skin);
		messages = new ArrayList<String>();
	}

	@Override
	public void act(float delta) {
		label.act(delta);
		if (messages.size() > 0) {
			ttl += delta;
			while (ttl >= TTL_MAX) {
				ttl -= TTL_MAX;
				messages.remove(0);
			}
			StringBuilder str = new StringBuilder();
			for (String msg : messages) {
				// TODO wrap long messages
				str.append(msg);
			}
			label.setText(str);
		} else {
			ttl = 0;
			label.setText("");
		}
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

	public void addChatMessage(String msg) {
		messages.add(msg);
	}

}