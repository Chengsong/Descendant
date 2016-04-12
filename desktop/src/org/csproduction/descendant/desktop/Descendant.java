package org.csproduction.descendant.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.csproduction.descendant.GameMain;

public class Descendant {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title=GameMain.TITLE;
                config.width=GameMain.V_WIDTH*GameMain.SCALE;
                config.height=GameMain.V_HEIGHT*GameMain.SCALE;
                config.resizable = false;
		new LwjglApplication(new GameMain(), config);
	}
}
