package mx.mcd.demodvd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mx.mcd.demodvd.Juego;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//definir el tamaño incial
		config.width=1280/2;
		config.height=720/2;
		new LwjglApplication(new Juego(), config);
	}
}
