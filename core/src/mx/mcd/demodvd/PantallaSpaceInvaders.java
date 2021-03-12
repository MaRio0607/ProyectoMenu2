package mx.mcd.demodvd;
/*
Representa un clon del juego Space invaders
Autor: Mario
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PantallaSpaceInvaders extends Pantalla {
    private Juego juego;
    //Enemigos
    //private Alien alien;

    private Array<Alien> alienArray; //guarda los aliens
//Pesonaje nave
    private Nave nave;

    public PantallaSpaceInvaders(Juego juego) {
        this.juego=juego;
    }

    //Inicializacion de todos los objetos

    @Override
    public void show() {
        crearEnemigos();
        crearNave();
    }

    private void crearNave() {
        Texture textureNave =new Texture("Space/nave.png");
        nave=new Nave(textureNave,ANCHO/2,0.1f*ALTO);
    }

    private void crearEnemigos() {
        Texture textureAlien = new Texture("Space/enemigoArriba.png");
        //alien= new Alien(textureAlien,ANCHO/2,ALTO/2);
        //crear 55 aliens

        alienArray=new Array<>(11*5);
        for(int renglon=0;renglon<5; renglon++){//recorre los renglones (0->4)
            for (int columna=0;columna<11;columna++){
                Alien alien=new Alien(textureAlien,310+columna*60,ALTO/2 +renglon*60);
                alienArray.add(alien);
            }
        }
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //alien.render(batch);
        //dibujar los 55 alien
        for (Alien alien:alienArray) {
            alien.render(batch);
        }
        //
        nave.render(batch);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
