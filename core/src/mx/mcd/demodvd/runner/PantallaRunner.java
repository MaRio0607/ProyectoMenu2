package mx.mcd.demodvd.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
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

    //Disparo del personaje
    private Array<BolaFuego>arrBolas;
    private Texture texturaBola;

    public PantallaRunner(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        crearFondo();
        crearMario();
        crearGoombas();
        crearBolas();
        //poner input procesor
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearBolas() {
        arrBolas=new Array<>();
        texturaBola=new Texture("runner/bolaFuego.png");
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

        //Dibujar bolas de fuego
        for (BolaFuego bolaFuego:arrBolas){
            bolaFuego.render(batch);
        }
        batch.end();

    }

    //se actualizan los objeteos del nivel
    private void actualizar(float delta) {
        actualizarFondo();
        actualizarGoombas(delta);
        actualizarBolas(delta);
    }
//mover todos los proyectiles
    private void actualizarBolas(float delta) {
        for (int i=arrBolas.size-1;i>=0;i--){
            BolaFuego bolaFuego = arrBolas.get(i);
            bolaFuego.mover(delta);
            //Prueba si la bola debe desaparecer
            if(bolaFuego.getX()>ANCHO){
                //borrar el objeto
                arrBolas.removeIndex(i);
            }
        }

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

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v =new Vector3(screenX, screenY,0);
            camara.unproject(v);
            if (v.x<=ANCHO/2){
                //Dispara
                BolaFuego bolaFuego = new BolaFuego(texturaBola,mario.getSprite().getX(),mario.getSprite().getY());
                arrBolas.add(bolaFuego);
            }else {
                mario.saltar();  //Top-Down
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
