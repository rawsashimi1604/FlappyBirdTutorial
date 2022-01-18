package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DemoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Change config settings
		config.width = DemoGame.WIDTH;
		config.height = DemoGame.HEIGHT;
		config.title = DemoGame.TITLE;


		new LwjglApplication(new DemoGame(), config);

	}
}
