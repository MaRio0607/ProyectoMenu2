package mx.mcd.demodvd.runner;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public PantallaRunner(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        crearFondo();
        crearMario();
    }

    private void crearMario() {
        textureMario=new Texture("runner/MarioSprites.png");
        mario=new Mario(textureMario,ANCHO/2,100);
    }

    private void crearFondo() {
        textureFondo=new Texture("runner/fondoMario_5.jpg");
    }

    @Override
    public void render(float delta) {
        actualizar();
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(textureFondo,xFondo,0);
        batch.draw(textureFondo,xFondo+textureFondo.getWidth(),0);
        //Dibujar mario
        mario.render(batch);
        batch.end();

    }

    private void actualizar() {
        actualizarFondo();
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
