package mx.mcd.demodvd;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class PantallaRunner extends Pantalla {
    private Juego juego;

    //fondo infinito
    private Texture textureFondo;
    private float xFondo=0;

    public PantallaRunner(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        crearFondo();

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
