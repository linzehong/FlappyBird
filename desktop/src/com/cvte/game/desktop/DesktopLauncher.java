package com.cvte.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cvte.game.Flappy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 288;
		config.height = 512;
		new LwjglApplication(new Flappy(), config);
//		new LwjglApplication(new PlaneGame(), config);
	}
}
