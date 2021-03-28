package mx.mcd.demodvd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.xalan.internal.xsltc.dom.AnyNodeCounter;

public class PantallaSpaceInvaders extends Pantalla {

    private static final float DELTA_X = 10;
    private Juego juego;

    //Enemigos
    //private Alien alien; //borrar
    private Array<Alien> arrAliens; //Guarda TODOS los aliens
    private float timerMover = 0; //Contar tiempo para que los aliens den pasos
    private final float LIMITE_PASOS_ALIENS = 0.5f; //Cada segundo los aliens darán un paso
    private float DX_PASO_ALIEN = 10; // +, derecha    -, izquierda
    private int contadorPasos = 10;

    //Personaje (Nave)
    private Nave nave;

    //Indican si se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;

    //Proyectil (Disparo de la nave)
    private Bala bala;
    private Texture texturaBala;
    //Boton de disparo
    private Texture texturaDisparo;

    public PantallaSpaceInvaders(Juego juego) {
        this.juego = juego;
    }

    //Se inicializan todos los objetos aqui
    @Override
    public void show() {
        crearEnemigos();
        crearNave();
        crearBotonDisparo();
        crearTexturaBala();
        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearTexturaBala() {
        texturaBala = new Texture("Space/bala.png");
    }

    private void crearBotonDisparo() {
        texturaDisparo = new Texture("Space/disparo.png");
    }

    private void crearNave() {
        Texture texturaNave = new Texture("Space/nave.png");
        nave = new Nave(texturaNave,ANCHO/2, 0.07f*ALTO);
    }

    private void crearEnemigos() {
        Texture texturaArriba = new Texture("Space/enemigoArriba.png");
        Texture textureAlienAbajo = new Texture("Space/enemigoAbajo.png");
        Texture textureExplota = new Texture("Space/enemigoExplota.png");
        //CREAR 55 aliens (11 columnas x 5 filas segun el juego)
        arrAliens = new Array<>(11*5);
        for(int renglon = 0; renglon < 5; renglon++){ //Recorrer los renglones (0 - 4)
            for (int columna = 0; columna < 11; columna++){
                Alien alien = new Alien(texturaArriba, textureAlienAbajo, textureExplota, 310 + columna*60,ALTO/2 + renglon*60);
                arrAliens.add(alien); //Lo guarda en el arrelo
            }
        }
    }

/*    private void crearMenu() {
        texturaFondo = new Texture("menu/space_invaders_fondo.jpg");
    }*/

    @Override
    public void render(float delta) {
        actualizar(); //Actualizo y luego dibujo
        borrarPantalla(0,0,0); //Borrar con color negro}

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        for (Alien alien: arrAliens) //Visita cada objeto del arreglo
        {
            alien.render(batch);
        }
        //Dibujar nave
        nave.render(batch);

        //Dibujar la bala
        if(bala != null){
            bala.render(batch);
        }

        //Dibujar boton disparo
        batch.draw(texturaDisparo,ANCHO-texturaDisparo.getWidth()*2,texturaDisparo.getHeight()/2);
        batch.end();
    }

    private void actualizar() {
        if(moviendoDerecha){
            nave.mover(DELTA_X);
        }if(moviendoIzquierda)
        {
            nave.mover(-DELTA_X);
        }

        //Actualizar bala
        if(bala != null){
            bala.mover(5);
            //Preguntar si se sale de la pantalla
            if( bala.sprite.getY() > ALTO){
                bala = null; //Invalidar esta bala
            }
        }

        //Probar colisiones
        if(bala != null){
            probarColisiones();
        }

        //Mover Alines
        moverAliens();
    }

    private void depurarAliens() {
        for(int i= arrAliens.size-1; i>=0; i--){
            Alien alien = arrAliens.get(i);
            if(alien.getEstado() == EstadoAlien.EXPLOTA){
                arrAliens.removeIndex(i);
            }
        }
    }

    private void moverAliens() {
        timerMover += Gdx.graphics.getDeltaTime();
        if(timerMover >= LIMITE_PASOS_ALIENS){
            timerMover = 0;
            for (Alien alien: arrAliens
            ) {
                alien.moverHorizontal(DX_PASO_ALIEN);
                alien.cambiarEstado();
            }
            contadorPasos++;
            if(contadorPasos >= 20){
                contadorPasos = 0;
                DX_PASO_ALIEN = -DX_PASO_ALIEN;
            }
            depurarAliens();
        }
    }

    /*
    Prueba la colisión de la bala contra TODOS los enemigos
    La bala EXISTE
     */
    private void probarColisiones() {
        //NO podemos usar el iterador
        for(int i= arrAliens.size-1; i>=0; i--){
            Alien alien = arrAliens.get(i);
            if(bala.sprite.getBoundingRectangle().overlaps(alien.sprite.getBoundingRectangle())){
                //Le pegó
                alien.setEstado(EstadoAlien.EXPLOTA);
                bala = null; //No regresar al for
                break;
            }
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

        //Cuando el usuario toca la pantalla. Pone el dedo sobre la pantalla
        //Mover nave derecha si toco la parte derecha de la pantalla, izquierda del otro caso
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            //pointer = dice qué dedo y que posicion lo puse
            //button = dice si presione el boton izq o derecho
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v); //Convierte de coordenadas FISICAS a LÓGICAS
            //PRIMERO
            if( v.x >= ANCHO-2*texturaDisparo.getWidth() && v.x <= ANCHO - texturaDisparo.getWidth()
                    && v.y >= texturaDisparo.getHeight()/2 && v.y <= 1.5f*texturaDisparo.getHeight()){
                Gdx.app.log("DISPARO", "Dispara");
                if(bala == null){
                    bala = new Bala(texturaBala,
                            nave.sprite.getX() + nave.sprite.getWidth()/2 - texturaBala.getWidth()/2
                            , nave.sprite.getY() + nave.sprite.getHeight());
                }
            }else{
                if(v.x < ANCHO/2){
                    //Primera mitad de la pantalla
                    //nave.mover(-DELTA_X);
                    moviendoIzquierda = true;
                }else{
                    nave.mover(DELTA_X);
                    moviendoDerecha = true;
                }
            }
            return true; //Porque el juego ya proceso el evento
        }

        //Cuando el usuario quita el dedo de la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v); //Convierte de coordenadas FISICAS a LÓGICAS
            moviendoDerecha = false;
            moviendoIzquierda = false;
            return true; //Porque el juego ya proceso el evento
        }

        //Cuando arrastro el dedo sonre la pantalla
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