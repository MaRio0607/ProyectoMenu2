package mx.mcd.demodvd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	//Musica
	public Music fondo;

	@Override
	public void create () {
		//Muestre la primer pantlla
		setScreen(new PantallaMenu(this)); //Primera pantalla visible
	}

	public void reporducir(TipoMusica tipo){
		if(fondo.isPlaying()){
			fondo.stop();
		}
		switch (tipo){
			case MENU:
				//fondo= assetManager("musica/menu.mp3");
				break;
			case NIVEL1:
			//	fondo= assetManager("musica/Niver.mp3");
				break;
		}
		fondo.play();
	}
	public void detener(){
		if (fondo.isPlaying()){
			fondo.stop();
		}
	}

	public enum  TipoMusica {
		MENU,
		NIVEL1,
	}
}
