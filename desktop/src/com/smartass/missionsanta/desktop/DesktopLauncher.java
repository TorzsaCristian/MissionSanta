package com.smartass.missionsanta.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smartass.missionsanta.SantaGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SantaGame.WIDTH;
		config.height = SantaGame.HEIGHT;
		config.title = SantaGame.TITLE;
		new LwjglApplication(new SantaGame(), config);
	}
}
