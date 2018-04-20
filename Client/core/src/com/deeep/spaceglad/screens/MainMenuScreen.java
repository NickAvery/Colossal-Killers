package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;

/**
 * Created by scanevaro on 05/08/2015.
 */
public class MainMenuScreen implements Screen {
    Core game;
    Stage stage;
    Image backgroundImage, titleImage;
    TextButton playButton, leaderboardsButton, quitButton, avatarButton; //added avatar button -Paul

    public MainMenuScreen(Core game) {
        this.game = game;
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgets(); //fixed typo -Paul
        setListeners();

        Gdx.input.setInputProcessor(stage);
    }

    private void setWidgets() {
        //background image is royalty free -Paul
        //source: https://pixabay.com/en/tyrannosaurus-rex-dinosaur-reptile-284554/
        backgroundImage = new Image(new Texture(Gdx.files.internal("data/splash.jpg")));
        titleImage = new Image(new Texture(Gdx.files.internal("data/title.png")));
        playButton = new TextButton("Play", Assets.skin);
        leaderboardsButton = new TextButton("Leaderboards", Assets.skin);
        quitButton = new TextButton("Quit", Assets.skin);
        avatarButton = new TextButton("Account", Assets.skin); //TODO: @Joe, must have an account with name and avatar data to hit "Play" button -Paul
    }

    private void configureWidgets() {
        backgroundImage.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        titleImage.setSize(1000, 100); // new title image has a 10:1 aspect ratio -Paul
        titleImage.setPosition(Core.VIRTUAL_WIDTH / 2 - titleImage.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 + titleImage.getWidth() / 7);
        playButton.setSize(128, 64);
        playButton.setPosition(Core.VIRTUAL_WIDTH / 2 - playButton.getWidth() * 3.1f, Core.VIRTUAL_HEIGHT / 2 - 145);
        leaderboardsButton.setSize(128, 64);
        leaderboardsButton.setPosition(Core.VIRTUAL_WIDTH / 2 + playButton.getWidth() * 2.1f, Core.VIRTUAL_HEIGHT / 2 - 145);
        quitButton.setSize(128, 64);
        quitButton.setPosition(Core.VIRTUAL_WIDTH / 2 + playButton.getWidth() * 2.1f, Core.VIRTUAL_HEIGHT / 2 - 220);
        avatarButton.setSize(128, 64);
        avatarButton.setPosition(Core.VIRTUAL_WIDTH / 2 - playButton.getWidth() * 3.1f, Core.VIRTUAL_HEIGHT / 2 - 220);


        stage.addActor(backgroundImage);
        stage.addActor(titleImage);
        stage.addActor(playButton);
        stage.addActor(leaderboardsButton);
        stage.addActor(quitButton);
        stage.addActor(avatarButton);
    }

    private void setListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game));
            }
        });
        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardsScreen(game));
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        /** Updates */
        stage.act(delta);
        /** Draw */
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
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