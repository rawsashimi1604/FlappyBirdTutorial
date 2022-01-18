package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class DemoGame extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "DemoGame";
	private GameStateManager gsm;
	private SpriteBatch batch; // Only have one of in each game, heavy file. Pass it around different states

	private Music music;
	
	@Override
	public void create () {

		// SpriteBatch : open box -> put all the files to render -> close box -> render
		batch = new SpriteBatch();

		gsm = new GameStateManager();

		// Set looping music
		music = Gdx.audio.newMusic(Gdx.files.internal("./audio/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		// Clears color
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// Push menu state to GameStateManager Stack
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
