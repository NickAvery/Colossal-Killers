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
		if (game.client != null) { // Should only be null if offline
			while (game.client.getQueueLength() > 0) {
				String msg = game.client.getNextMessage();
				String params[] = msg.split(" ");
				switch (params[0]) {
				case "\\say":
					gameUI.messageWidget.addChatMessage(msg.substring(5));
					break;
				case "\\inform":
					gameUI.messageWidget.addChatMessage("Server: " + msg.substring(8));
					break;
				case "\\avatar":
					/*
					 * params[0] = "\\avatar"
					 * params[1] = "" <-- bug on server side
					 * params[2] = username
					 * params[3] = position.x
					 * params[4] = position.y
					 * params[5] = position.z
					 * params[6] = min( rotation.y, 1.5 )
					 * params[7] = "user\n"
					 */
					if (params[7] == "user\n") {
						if (params[2] == game.client.username) {
							// This player
							// TODO
						} else {
							// Other Player
							// TODO
						}
					} else {
						// NPC
						// TODO
					}
					break;
				default:
					// TODO Handle more commands
					// Unhandled commands are pushed to chat window
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