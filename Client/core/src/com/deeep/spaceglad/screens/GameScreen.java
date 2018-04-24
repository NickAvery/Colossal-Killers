package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;

/**
 * Created by scanevaro on 31/07/2015.
 */
public class GameScreen implements Screen {
	Core game;
	GameUI gameUI;
	GameWorld gameWorld;

	public GameScreen(Core game) {
		this.game = game;
		gameUI = new GameUI(game);
		gameWorld = new GameWorld(gameUI);
		Settings.Paused = false;
		Gdx.input.setInputProcessor(gameUI.stage);
		Gdx.input.setCursorCatched(true); // capture cursor for aiming -Paul
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		// Handle server messages
		if (game.client != null) {
			// Should only be null if offline
			while (game.client.getQueueLength() > 0) {
				String msg = game.client.getNextMessage();
				if (msg.startsWith("\\say ")) {
					gameUI.messageWidget.addChatMessage(msg.substring(5));
				} else if (msg.startsWith("\\inform ")) {
					gameUI.messageWidget.addChatMessage("Server: " + msg.substring(8));
				} else {
				 // TODO Actually handle commands, don't just push them to the chat window
					gameUI.messageWidget.addChatMessage(msg);
				}
			}
		}

		/** Updates */
		gameUI.update(delta);
		/** Draw */
		gameWorld.render(delta);
		gameUI.render();
	}

	@Override
	public void resize(int width, int height) {
		gameUI.resize(width, height);
		gameWorld.resize(width, height);
	}

	@Override
	public void dispose() {
		gameWorld.dispose();
		gameUI.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
}