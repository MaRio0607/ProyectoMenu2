package mx.mcd.demodvd;

import com.badlogic.gdx.graphics.Texture;

/*
Representa los aliens en la pantalla
 */
public class Alien extends Objeto {
    private Texture texturaArriba;
    private Texture texturaAbajo;
    private Texture Explota;
    private EstadoAlien estado; //ARRIBA; ABAJO; MURRIENDO

    public Alien(Texture texture, float x, float y){
    super(texture, x, y);// el constructor de la super clase
 }
//Constructor(recibe 2 texturas)
    public  Alien(Texture texturaArriba, Texture texturaAbajo, Texture muriendo,float x, float y){
     super(texturaArriba,x,y);
     this.texturaArriba=texturaArriba;
     this.texturaAbajo=texturaAbajo;
     estado=EstadoAlien.ARRIBA;
    }
    public void cambiarEstado(){
        switch (estado){
            case ARRIBA:
                estado=EstadoAlien.ABAJO;
                sprite.setTexture(texturaAbajo);
                break;
            case ABAJO:
                estado=EstadoAlien.ARRIBA;
                sprite.setTexture(texturaArriba);
                break;

        }
    }
public  void  setEstado(EstadoAlien nuevoEstado){
        this.estado=nuevoEstado;
        if (nuevoEstado==EstadoAlien.MURIENDO){
            sprite.setTexture(Explota);
        }
}
 //mover
    public void  moverHorizontal (float dx){
        sprite.setX(sprite.getX()+dx);
    }
  //Dibujar



  //objeto

}
