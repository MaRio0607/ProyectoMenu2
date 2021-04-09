package mx.mcd.demodvd.spaceInvaders;

import com.badlogic.gdx.graphics.Texture;

import mx.mcd.demodvd.Objeto;

//proyectil
//Autor:mario
public class Bala extends Objeto {

    public Bala(Texture texture,float x, float y){
        super(texture, x, y);
    }
    //mover
    public void mover(float dy){
        sprite.setY(sprite.getY()+dy);
    }
}
