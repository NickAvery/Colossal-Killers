package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
		playButton = new TextButton("Play(No server)", Assets.skin);
		backButton = new TextButton("Back", Assets.skin);
		usernameLabel = new Label("Username", Assets.skin);
		passwordLabel = new Label("Password", Assets.skin);
		usernameArea = new TextArea("", Assets.skin);
		// TODO disallow newline in usernameArea
		passwordArea = new TextArea("", Assets.skin);
		passwordArea.setPasswordMode(true);
		// TODO disallow newline in passwordArea
		messageLabel = new Label("", Assets.skin);
		newUserButton = new TextButton("New User?", Assets.skin);

	}

	private void configureWidgets() {
		backgroundImage.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
		loginButton.setSize(128, 64);
		loginButton.setPosition(Core.VIRTUAL_WIDTH / 2 - loginButton.getWidth() - 10,
				Core.VIRTUAL_HEIGHT / 2 - 200);
		playButton.setSize(128, 64);
		playButton.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 200);
		backButton.setSize(128, 64);
		backButton.setPosition(Core.VIRTUAL_WIDTH / 2 + backButton.getWidth() + 20, Core.VIRTUAL_HEIGHT / 2 - 200);
		usernameLabel.setSize(128, 32);
		usernameLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - usernameLabel.getWidth() / 2 - 14,
				Core.VIRTUAL_HEIGHT / 2 + 75);
		usernameArea.setSize(150, 28);
		usernameArea.setPosition(Core.VIRTUAL_WIDTH / 2 - usernameArea.getWidth() / 2,
				Core.VIRTUAL_HEIGHT / 2 + 75 - 20);
		passwordLabel.setSize(128, 32);
		passwordLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - passwordLabel.getWidth() / 2 - 14,
				Core.VIRTUAL_HEIGHT / 2 );
		passwordArea.setSize(150, 28);
		passwordArea.setPosition(Core.VIRTUAL_WIDTH / 2 - passwordArea.getWidth() / 2,
				Core.VIRTUAL_HEIGHT / 2 - 20);
		messageLabel.setSize(128, 32);
        messageLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - messageLabel.getWidth() / 2 - 14,
                Core.VIRTUAL_HEIGHT / 2 + 130);
        newUserButton.setSize(128, 64);
        newUserButton.setPosition(Core.VIRTUAL_WIDTH / 2 - newUserButton.getWidth()*2 - 20,
                Core.VIRTUAL_HEIGHT / 2 - 200);

		stage.addActor(backgroundImage);
		stage.addActor(loginButton);
		stage.addActor(playButton);
		stage.addActor(backButton);
		stage.addActor(usernameLabel);
		stage.addActor(usernameArea);
		stage.addActor(passwordLabel);
		stage.addActor(passwordArea);
		stage.addActor(messageLabel);
		stage.addActor(newUserButton);
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
        newUserButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NewUserScreen(game));
            }
        });

	}

	private void login() {
		if (usernameArea.getText().length() != 0 && passwordArea.getText().length() != 0) {

			Client client = new Client(usernameArea.getText(), passwordArea.getText());

			if (client.isRunning()) {

				if (game.client != null) {
					game.client.close();
				}
				game.client = client;

				game.setScreen(new GameScreen(game));
			}
			else
                messageLabel.setText("Invalid username or password!\n If you haven't already create a new account.\n");
		}
		else
		    messageLabel.setText("Please enter a username \nand password.");
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
