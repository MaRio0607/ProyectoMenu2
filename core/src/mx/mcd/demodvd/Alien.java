package mx.mcd.demodvd;

import com.badlogic.gdx.graphics.Texture;

/*
Representa los aliens en la pantalla
 */
public class Alien extends Objeto{

    //Animacion
    private Texture texturaArriba;
    private Texture texturaAbajo;
    private Texture texturaExplota;
    private EstadoAlien estado; //ARRIBA, ABAJO, MURIENDO

    public Alien(Texture textura, float x, float y){
        super(textura,x,y); //El constructor de la superclase inicializa el sprite
    }

    //Constructor (recibe 2 texturas)
    public Alien (Texture texturaArriba, Texture texturaAbajo, Texture texturaExplota, float x, float y){
        super(texturaArriba,x,y);
        this.texturaArriba = texturaArriba;
        this.texturaAbajo = texturaAbajo;
        this.texturaExplota = texturaExplota;
        estado = EstadoAlien.ARRIBA;
    }

    //Cambiar estado
    public void cambiarEstado() {
        switch (estado) {
            case ARRIBA:
                estado = EstadoAlien.ABAJO;
                sprite.setTexture(texturaAbajo);
                break;
            case ABAJO:
                estado = EstadoAlien.ARRIBA;
                sprite.setTexture(texturaArriba);
                break;
        }
    }

    public EstadoAlien getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlien nuevoEstado){
        this.estado = nuevoEstado;
        if(nuevoEstado == EstadoAlien.EXPLOTA){
            sprite.setTexture(texturaExplota);
        }
    }
    //Mover
    public void moverHorizontal(float dx){
        sprite.setX(sprite.getX() + dx);
    }

    //Dibujar (Ya lo heredó de Objeto)
}
