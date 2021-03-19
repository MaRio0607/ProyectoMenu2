package mx.mcd.demodvd;

import com.badlogic.gdx.graphics.Texture;
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
