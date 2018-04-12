package com.deeep.spaceglad.screens;

import java.io.BufferedReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Client;
import com.deeep.spaceglad.Core;

public class LoginScreen implements Screen {
	private Core game;
	private Stage stage;
	private Image backgroundImage;
	private TextButton loginButton, playButton, backButton;
	private Label usernameLabel, passwordLabel;
	private TextArea usernameArea, passwordArea;
	private Socket socket;
	private BufferedReader buffer;
	private String temp, tempBuffer;

	public LoginScreen(Core game) {
		this.game = game;
		stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
		setWidgets();
		configureWidgets();
		setListeners();

		Gdx.input.setInputProcessor(stage);

	}

	private void setWidgets() {
		backgroundImage = new Image(new Texture(Gdx.files.internal("data/splash.jpg")));
		loginButton = new TextButton("Login", Assets.skin);
		playButton = new TextButton("Play (use this until login works)", Assets.skin);
		backButton = new TextButton("Back", Assets.skin);
		usernameLabel = new Label("Username", Assets.skin);
		passwordLabel = new Label("Password", Assets.skin);
		usernameArea = new TextArea("", Assets.skin);
		passwordArea = new TextArea("", Assets.skin);

	}

	private void configureWidgets() {
		backgroundImage.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
		loginButton.setSize(128, 64);
		loginButton.setPosition(Core.VIRTUAL_WIDTH / 2 - loginButton.getWidth() / 2 - 250,
				Core.VIRTUAL_HEIGHT / 2 - 200);
		playButton.setSize(256, 64);
		playButton.setPosition(Core.VIRTUAL_WIDTH / 2 - playButton.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 - 200);
		backButton.setSize(128, 64);
		backButton.setPosition(Core.VIRTUAL_WIDTH / 2 - backButton.getWidth() / 2 + 250, Core.VIRTUAL_HEIGHT / 2 - 200);
		usernameLabel.setSize(128, 32);
		usernameLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - usernameLabel.getWidth() / 2 - 14,
				Core.VIRTUAL_HEIGHT / 2 + 150);
		usernameArea.setSize(150, 28);
		usernameArea.setPosition(Core.VIRTUAL_WIDTH / 2 - usernameArea.getWidth() / 2,
				Core.VIRTUAL_HEIGHT / 2 + 150 - 20);
		passwordLabel.setSize(128, 32);
		passwordLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - passwordLabel.getWidth() / 2 - 14,
				Core.VIRTUAL_HEIGHT / 2 + 75);
		passwordArea.setSize(150, 28);
		passwordArea.setPosition(Core.VIRTUAL_WIDTH / 2 - passwordArea.getWidth() / 2,
				Core.VIRTUAL_HEIGHT / 2 + 75 - 20);

		stage.addActor(backgroundImage);
		stage.addActor(loginButton);
		stage.addActor(playButton);
		stage.addActor(backButton);
		stage.addActor(usernameLabel);
		stage.addActor(usernameArea);
		stage.addActor(passwordLabel);
		stage.addActor(passwordArea);
	}

	private void setListeners() {
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
			}
		});
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
			}
		});
		loginButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				login();
			}
		});
	}

	private void login() {
		if (usernameArea.getText().length() != 0 && passwordArea.getText().length() != 0) {

			Client client = new Client(usernameArea.getText(), passwordArea.getText());

			if (client.isRunning()) {

				if (Core.client != null) {
					Core.client.close();
				}
				Core.client = client;

				Core.clientThread = new Thread(client);
				Core.clientThread.start();
				game.setScreen(new GameScreen(game));
			}

		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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

	@Override
	public void dispose() {
		stage.dispose();
	}
}
