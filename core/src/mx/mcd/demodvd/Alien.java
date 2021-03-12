package mx.mcd.demodvd;

import com.badlogic.gdx.graphics.Texture;

/*
Representa los aliens en la pantalla
 */
public class Alien extends Objeto {
 public Alien(Texture texture, float x, float y){
    super(texture, x, y);// el constructor de la super clase
 }

 //mover
    public void  moverHorizontal (float dx){
        sprite.setX(sprite.getX()+dx);
    }
  //Dibujar



  //objeto

}
