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
import com.deeep.spaceglad.Settings;

/**
 * Created by scanevaro on 06/08/2015.
 */
public class AvatarScreen implements Screen {
    private Core game;
    private Stage stage;
    private Image backgroundImage;
    private TextButton red, orange, yellow, green, blue, indigo, violet, gray, black;
    private Label messageLabel;

    public AvatarScreen(Core game) {
        this.game = game;
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgets();
        setListeners();

        Gdx.input.setInputProcessor(stage);

    }

    private void setWidgets() {
        backgroundImage = new Image(new Texture(Gdx.files.internal("data/splash.jpg")));
        messageLabel = new Label("Please select avatar color", Assets.skin);
        red = new TextButton("RED", Assets.skin);
        orange = new TextButton("ORANGE", Assets.skin);
        yellow = new TextButton("YELLOW", Assets.skin);
        green = new TextButton("GREEN", Assets.skin);
        blue = new TextButton("BLUE", Assets.skin);
        indigo = new TextButton("INDIGO", Assets.skin);
        violet = new TextButton("VIOLET", Assets.skin);
        gray = new TextButton("GRAY", Assets.skin);
        black = new TextButton("BLACK", Assets.skin);
    }

    private void configureWidgets() {
        backgroundImage.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        messageLabel.setSize(128, 32);
        messageLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - messageLabel.getWidth() / 2 -350,
                Core.VIRTUAL_HEIGHT / 2 + 230);
        red.setSize(128, 64);
        red.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 + 160);
        orange.setSize(128, 64);
        orange.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 + 90);
        yellow.setSize(128, 64);
        yellow.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 + 20);
        green.setSize(128, 64);
        green.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 - 50);
        blue.setSize(128, 64);
        blue.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 - 120);
        indigo.setSize(128, 64);
        indigo.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 - 190);
        violet.setSize(128, 64);
        violet.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 3.1f,
                Core.VIRTUAL_HEIGHT / 2 - 260);
        gray.setSize(128, 64);
        gray.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 2.0f,
                Core.VIRTUAL_HEIGHT / 2 - 190);
        black.setSize(128, 64);
        black.setPosition(Core.VIRTUAL_WIDTH / 2 - red.getWidth() * 2.0f,
                Core.VIRTUAL_HEIGHT / 2 - 260);

        stage.addActor(backgroundImage);
        stage.addActor(messageLabel);
        stage.addActor(red);
        stage.addActor(orange);
        stage.addActor(yellow);
        stage.addActor(green);
        stage.addActor(blue);
        stage.addActor(indigo);
        stage.addActor(violet);
        stage.addActor(gray);
        stage.addActor(black);
    }

    private void setListeners() {
        red.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 1;
            }
        });
        orange.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 2;
            }
        });
        yellow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 3;
            }
        });
        green.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 4;
            }
        });
        blue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 5;
            }
        });
        indigo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 6;
            }
        });
        violet.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 7;
            }
        });
        gray.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 8;
            }
        });
        black.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                Assets.avColor = 9;
            }
        });
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