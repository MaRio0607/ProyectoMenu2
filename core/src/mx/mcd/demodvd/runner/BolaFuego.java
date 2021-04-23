package mx.mcd.demodvd.runner;

import com.badlogic.gdx.graphics.Texture;

import mx.mcd.demodvd.Objeto;

/*
Las bolas de fuego que lanza el personaje
 */
public class BolaFuego extends Objeto {
    private float vX=350;

    public BolaFuego(Texture texture, float x, float y){
    super(texture,x,y);
    }

    //Mover a la dercha la bola de fuego
    public void  mover(float delta){
        float dx=vX*delta;
        sprite.setX(sprite.getX()+dx);
    }


    public float getX() {
        return sprite.getX();
    }
}
