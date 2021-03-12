package mx.mcd.demodvd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	@Override
	public void create () {
		//Muestre la primer pantlla
		setScreen(new PantallaMenu(this)); //Primera pantalla visible
	}

}
