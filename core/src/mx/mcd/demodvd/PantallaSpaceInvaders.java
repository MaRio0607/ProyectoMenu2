package mx.mcd.demodvd;
/*
Representa un clon del juego Space invaders
Autor: Mario
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PantallaSpaceInvaders extends Pantalla {
    private static final float DELTA_X =10;
    private Juego juego;
    //Enemigos
    //private Alien alien;

    private Array<Alien> alienArray; //guarda los aliens
//Pesonaje nave
    private Nave nave;
    //indican si se mueve en una cierta direccion
    private boolean moviendoIzq;
    private boolean moviendoDer;

    //proyectil
    private Bala bala;
    //boton de disparo
    private Texture textureDisparo;

    public PantallaSpaceInvaders(Juego juego) {
        this.juego=juego;
    }

    //Inicializacion de todos los objetos

    @Override
    public void show() {
        crearEnemigos();
        crearNave();
        crearBotonDisparo();

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearBotonDisparo() {
        textureDisparo=new Texture("Space/disparo.png");
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
//hasta 60 veces
    @Override
    public void render(float delta) {
        actualizar();
        borrarPantalla(0,0,0);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //alien.render(batch);
        //dibujar los 55 alien
        for (Alien alien:alienArray) {
            alien.render(batch);
        }
        //dibujar nave
        nave.render(batch);

        //dibujar bala
        if(bala != null){// existe??
            bala.render(batch);
        }

        //dinujar boton Disparo
        batch.draw(textureDisparo,ANCHO-textureDisparo.getWidth()*2,textureDisparo.getWidth()/2);

        batch.end();
    }
//Ejecitar 60 veces
    private void actualizar() {
        //Mover nave
        if(moviendoDer){
            nave.mover(DELTA_X);
        }
        if (moviendoIzq){
            nave.mover(-DELTA_X);
        }
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

    private class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }
        //cuando el usuario pone el dedo en la pantalla
        ///se movera a la derecha o a la izquierda
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v =new Vector3(screenX,screenY,0);
           camara.unproject(v);
                //v ya tiene coordenadas logicas
            if(v.x < ANCHO/2) {
                //primera mitad, mover izq
                // nave.mover(-DELTA_X);
                moviendoIzq=true;
            }else {
                //segunda mitad, mover derecha
                //nave.mover(DELTA_X);
                moviendoDer=true;
            }
            return false;
        }
        //cuando el usuario quita el dedo en la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Vector3 v =new Vector3(screenX,screenY,0);
            camara.unproject(v);
            //v ya tiene coordenadas logicas
        moviendoDer=false;
        moviendoIzq=false;
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}
