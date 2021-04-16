package mx.mcd.demodvd.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import mx.mcd.demodvd.Juego;
import mx.mcd.demodvd.Pantalla;

public class PantallaRunner extends Pantalla {
    private Juego juego;

    //fondo infinito
    private Texture textureFondo;
    private Texture textureMario;
    private float xFondo=0;

    //personaje
    private Mario mario;

    //enemigo
    //private Goomba goomba;
    private Array<Goomba>goombaArray;
    private Texture textureGoomba;
    private float timerGoomba;
    private final float TIEMPO_CREAR_GOOMBA=2;

    public PantallaRunner(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        crearFondo();
        crearMario();
        crearGoombas();
    }

    private void crearGoombas() {
        textureGoomba=new Texture("runner/goomba.png");
        //goomba=new Goomba(textureGoomba,ANCHO-62,64);
        goombaArray=new Array<>(3);
    }

    private void crearMario() {
        textureMario=new Texture("runner/MarioSprites.png");
        mario=new Mario(textureMario,ANCHO/2,54);
    }

    private void crearFondo() {
        textureFondo=new Texture("runner/fondoMario_5.jpg");
    }

    @Override
    public void render(float delta) {
        actualizar(delta);
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(textureFondo,xFondo,0);
        batch.draw(textureFondo,xFondo+textureFondo.getWidth(),0);
        //Dibujar mario
        mario.render(batch);
        //Dibujar goomba
        // goomba.render(batch);
        for (Goomba goomba:goombaArray){
            goomba.render(batch);
        }
        batch.end();

    }

    private void actualizar(float delta) {
        actualizarFondo();
        actualizarGoombas(delta);
    }

    private void actualizarGoombas(float delta) {
        //crear
        timerGoomba +=delta;
        if (timerGoomba>=TIEMPO_CREAR_GOOMBA){
            timerGoomba=0;
            //crear enemigo
            float xGoomba= MathUtils.random(ANCHO,ANCHO*1.5f);
            Goomba goomba=new Goomba(textureGoomba,xGoomba,54);
            //Gdx.app.log("CREAR GOOMBAS","x="+xGoomba);
            goombaArray.add(goomba);
        }

        //mover enemigos
        for (Goomba goomba:goombaArray){
            goomba.moverIzquierda(delta);//fisica
        }
    }

    private void actualizarFondo() {
        xFondo-=2;
        if (xFondo <= -textureFondo.getWidth()){
            xFondo= 0;
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
}
