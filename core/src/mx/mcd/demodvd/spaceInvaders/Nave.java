package mx.mcd.demodvd.spaceInvaders;

import com.badlogic.gdx.graphics.Texture;

import mx.mcd.demodvd.Objeto;

/*
la nave del juegador
 */
public class Nave extends Objeto {

    public Nave(Texture texture, float x, float y){
        super(texture, x, y);// el constructor de la super clase
    }

    //metodo para mover el sprite
    public  void  mover(float dx){
        sprite.setX(sprite.getX()+dx);
    }


}
