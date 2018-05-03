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

public class NewUserScreen implements Screen {
	private Core game;
	private Stage stage;
	private Image backgroundImage;
	private TextButton createButton, backButton;
	private Label usernameLabel, passwordLabel, messageLabel, firstNameLabel, lastNameLabel, emailLabel,
			affiliationLabel;
	private TextArea usernameArea, passwordArea, firstNameArea, lastNameArea, emailArea, affiliationArea;

	public NewUserScreen(Core game) {
		this.game = game;
		stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
		this.game.client = new Client("system", "unicron");
		setWidgets();
		configureWidgets();
		setListeners();

		Gdx.input.setInputProcessor(stage);
	}

	private void setWidgets() {
		backgroundImage = new Image(new Texture(Gdx.files.internal("data/splash.jpg")));
		backButton = new TextButton("Back", Assets.skin);
		usernameLabel = new Label("Username", Assets.skin);
		passwordLabel = new Label("Password", Assets.skin);
		usernameArea = new TextArea("", Assets.skin);
		passwordArea = new TextArea("", Assets.skin);
		messageLabel = new Label("", Assets.skin);
		createButton = new TextButton("Create Account", Assets.skin);
		emailArea = new TextArea("", Assets.skin);
		emailLabel = new Label("Email Address", Assets.skin);
		firstNameArea = new TextArea("", Assets.skin);
		firstNameLabel = new Label("First Name", Assets.skin);
		lastNameLabel = new Label("Last Name", Assets.skin);
		lastNameArea = new TextArea("", Assets.skin);
		affiliationArea = new TextArea("", Assets.skin);
		affiliationLabel = new Label("Affiliation", Assets.skin);

	}

	private void configureWidgets() {
		backgroundImage.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
		createButton.setSize(128, 64);
		createButton.setPosition(Core.VIRTUAL_WIDTH / 2 - createButton.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 - 200);
		backButton.setSize(128, 64);
		backButton.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 200);
		backButton.setSize(128, 64);
		backButton.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 200);
		messageLabel.setSize(128, 32);
		messageLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - messageLabel.getWidth() / 2 - 14,
				Core.VIRTUAL_HEIGHT / 2 + 130);
		emailArea.setSize(250, 28);
		emailArea.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 - 75 - 20);
		emailLabel.setSize(128, 32);
		emailLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 - 75);
		usernameLabel.setSize(128, 32);
		usernameLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 + 75);
		usernameArea.setSize(150, 28);
		usernameArea.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 + 75 - 20);
		passwordLabel.setSize(128, 32);
		passwordLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2);
		passwordArea.setSize(150, 28);
		passwordArea.setPosition(Core.VIRTUAL_WIDTH / 2 - emailArea.getWidth() - 10, Core.VIRTUAL_HEIGHT / 2 - 20);
		firstNameLabel.setSize(128, 32);
		firstNameLabel.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 + 75);
		firstNameArea.setSize(150, 28);
		firstNameArea.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 + 75 - 20);
		lastNameLabel.setSize(128, 32);
		lastNameLabel.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2);
		lastNameArea.setSize(150, 28);
		lastNameArea.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 20);
		affiliationLabel.setSize(128, 32);
		affiliationLabel.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 75);
		affiliationArea.setSize(150, 28);
		affiliationArea.setPosition(Core.VIRTUAL_WIDTH / 2 + 10, Core.VIRTUAL_HEIGHT / 2 - 75 - 20);

		stage.addActor(backgroundImage);
		stage.addActor(backButton);
		stage.addActor(usernameLabel);
		stage.addActor(usernameArea);
		stage.addActor(passwordLabel);
		stage.addActor(passwordArea);
		stage.addActor(messageLabel);
		stage.addActor(createButton);
		stage.addActor(emailArea);
		stage.addActor(emailLabel);
		stage.addActor(firstNameArea);
		stage.addActor(firstNameLabel);
		stage.addActor(lastNameArea);
		stage.addActor(lastNameLabel);
		stage.addActor(affiliationArea);
		stage.addActor(affiliationLabel);
	}

	private void setListeners() {
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LoginScreen(game));
				if (game.client.isRunning())
					game.client.sendMessage("\\logout\n");
			}
		});
		createButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				createAccount();
			}
		});
	}

	private void createAccount() {
		if (usernameArea.getText().length() != 0 && passwordArea.getText().length() != 0
				&& emailArea.getText().length() != 0 && firstNameArea.getText().length() != 0
				&& lastNameArea.getText().length() != 0 && affiliationArea.getText().length() != 0) {

			if (game.client.isRunning()) {
				game.client.sendMessage("\\newuser " + usernameArea.getText() + " " + passwordArea.getText() + " "
						+ firstNameArea.getText() + " " + lastNameArea.getText() + " " + emailArea.getText() + " "
						+ affiliationArea.getText() + "\n");
				while (game.client.isRunning()) {
					if (game.client.getQueueLength() > 0) {
						String msg = game.client.getNextMessage();
						if (msg.equals("")) {
						} else if (msg.equals("\\success new user creation succeeded\n")) {
							messageLabel.setText("New account created!");
							break;
						} else if (msg.equals("\\failure new user creation failed\n")) {
							messageLabel.setText("Account creation failed!");
							break;
						}
					}
				}
			}
		} else
			messageLabel.setText("Please make sure all\nfields are not empty.");
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
