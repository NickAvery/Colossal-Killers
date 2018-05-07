package com.deeep.spaceglad.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.Settings;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.components.AvatarComponent;
import com.deeep.spaceglad.components.PlayerComponent;


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
		gameWorld = new GameWorld(gameUI, game);
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
				Entity entity;
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
						 * params[1] = username
						 * params[2] = position.x
						 * params[3] = position.y
						 * params[4] = position.z
						 * params[5] = min( rotation.y, 1.5 )
						 * params[6] = "user\n"
						 */
						if (params[2] != "") {
							if (params[1].equals(game.client.username)) {
								// This player
								// TODO
							} else {
								gameWorld.addPlayer(params[1], Float.parseFloat(params[2]),
										Float.parseFloat(params[3]),
										Float.parseFloat(params[4]));
							}
						} else {
							// NPC
							// TODO
						}
						break;
					case "\\damage":
						if ((entity = (Entity) gameWorld.avatarSystem.getPlayersList().get(params[1])) != null)
							entity.getComponent(AvatarComponent.class).health -= Integer.parseInt(params[2]);
						else
							gameWorld.playerSystem.getPlayer().getComponent(PlayerComponent.class).
									health -= Integer.parseInt(params[2]);
						break;
					case "\\move":
						if ((entity = (Entity) gameWorld.avatarSystem.getPlayersList().get(params[1])) != null) {
							entity.getComponent(AvatarComponent.class).x = Float.parseFloat(params[2]);
							entity.getComponent(AvatarComponent.class).y = Float.parseFloat(params[3]);
							entity.getComponent(AvatarComponent.class).z = Float.parseFloat(params[4]);
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