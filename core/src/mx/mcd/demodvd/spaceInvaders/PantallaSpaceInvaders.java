package mx.mcd.demodvd.spaceInvaders;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mx.mcd.demodvd.Juego;
import mx.mcd.demodvd.Pantalla;
import mx.mcd.demodvd.PantallaMenu;
import mx.mcd.demodvd.spaceInvaders.Alien;
import mx.mcd.demodvd.spaceInvaders.Bala;
import mx.mcd.demodvd.spaceInvaders.EstadoAlien;
import mx.mcd.demodvd.spaceInvaders.Nave;
import mx.mcd.demodvd.utilerias.Texto;

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
    private mx.mcd.demodvd.spaceInvaders.Nave nave;

    //Indican si se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;

    //Proyectil (Disparo de la nave)
    private mx.mcd.demodvd.spaceInvaders.Bala bala;
    private Texture texturaBala;
    //Boton de disparo
    private Texture texturaDisparo;

    //Marcador (vidas, niveles, etc)
    private int puntos=0;
    private Texto texto;    //escribe texto en la pantalla

    //regresa al menu principal
    private Texture textureBack;


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
        crearTexto();
        crearBack();
        recuperarMarcador();
        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

        //MUSICA
        juego.reporducir(Juego.TipoMusica.NIVEL1);


    }

    private void recuperarMarcador() {
        Preferences prefs= Gdx.app.getPreferences("PUNTAJE");
        puntos=prefs.getInteger("puntos",0);
    }

    private void crearBack() {
        textureBack=new Texture("Space/back.png");
    }

    private void crearTexto() {
        texto= new Texto("Fonts/space.fnt");
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


        //dibujar marcador
        texto.mostrarMensaje(batch,Integer.toString(puntos),.95f*ANCHO,0.9f*ALTO);
        //dibujar back
        batch.draw(textureBack,textureBack.getWidth()/2,ALTO-3*textureBack.getHeight()/2);

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
                //contar puntos
                puntos+=150;
                //desaparece la bala
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

            float altoBack= textureBack.getHeight();
            float anchoBack=textureBack.getHeight();
            float xBack= textureBack.getHeight()/2;
            float yBack=  ALTO-1.5f*textureBack.getWidth();

            //verificar si se pico al boton de regresar
            Rectangle rectBack=new Rectangle(xBack,yBack,anchoBack,altoBack);
            if (rectBack.contains(v.x,v.y)){
                //guardar marcador
                Preferences preferences=Gdx.app.getPreferences("PUNTAJE");
                preferences.putInteger("puntos", puntos);
                preferences.flush();//Guardar en disco
                //regresar a pantalla menu
                juego.detener();//detiene la musica
                juego.setScreen(new PantallaMenu(juego));

            }else
            //probar si hace click en el boton de dispara
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