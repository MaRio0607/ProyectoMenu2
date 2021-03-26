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
    private float timerMover; //contar tiempo para que los aliens den pasos
    private final float LIMITED_PASOS_ALIENS=0.5f;
    private Array<Alien> alienArray; //guarda los aliens
    private float DX_PASOS_ALIENS=10;
    private int contaodrPasos=10;   //Hacen 20 pasos en total

    //Pesonaje nave
    private Nave nave;
    //indican si se mueve en una cierta direccion
    private boolean moviendoIzq;
    private boolean moviendoDer;

    //proyectil
    private Bala bala;
    //textura de la bala
    private Texture texturaBala;
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
        crearTexturaBala();

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearTexturaBala() {
        texturaBala=new Texture("Space/bala.png");
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
        Texture AlienAbajo= new Texture("Space/enemigoAbajo.png");
        Texture AlienExplota= new Texture("Space/enemigoExplota.png");

        //alien= new Alien(textureAlien,ANCHO/2,ALTO/2);
        //crear 55 aliens

        alienArray=new Array<>(11*5);
        for(int renglon=0;renglon<5; renglon++){//recorre los renglones (0->4)
            for (int columna=0;columna<11;columna++){
                Alien alien=new Alien(textureAlien,AlienAbajo,AlienExplota,
                        310+columna*60,ALTO/2 +renglon*60);
                alienArray.add(alien);    //lo guarda para el arreglo
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
        //Mover bala
        if(bala != null){
            bala.mover(5);
            //preguntar si se sale de la pantalla
            if(bala.sprite.getY()>ALTO){
                bala=null; //invalidar
            }
        }
        //Colisiones
        if(bala!=null){
            probarColisiones();
        }

        //mover aliens
        moverAliens();
    }
    //Mueve los aliens de la bala
    private void moverAliens() {
        timerMover += Gdx.graphics.getDeltaTime();
       if (timerMover >= LIMITED_PASOS_ALIENS){
           timerMover=0;
           for (Alien alien:alienArray){
               alien.moverHorizontal(DX_PASOS_ALIENS);
               alien.cambiarEstado();
           }
           contaodrPasos++;
           if(contaodrPasos>=20){
               contaodrPasos=0;
               DX_PASOS_ALIENS=-DX_PASOS_ALIENS;
           }
       }
    }

    /*
    Prueba la colision de la bala contra todos los enemigos
     La bala existe
     */
    private void probarColisiones() {
    //No podemos usar el iterador
        for (int i=alienArray.size-1;i>=0;i--){
            Alien alien= alienArray.get(i);
            if(bala.sprite.getBoundingRectangle().overlaps(alien.sprite.getBoundingRectangle())){
                //le pego!!

                alienArray.removeIndex(i);
                //alien.setEstado(EstadoAlien.MURIENDO);
                //Desaparecer bala
                bala=null;
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
        //cuando el usuario pone el dedo en la pantalla
        ///se movera a la derecha o a la izquierda
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v =new Vector3(screenX,screenY,0);

           camara.unproject(v);
                //v ya tiene coordenadas logicas
            //comprueba el click en el boton
           if(v.x>=ANCHO-2*textureDisparo.getWidth() && v.x<=ANCHO-textureDisparo.getWidth()
           && v.y>=textureDisparo.getHeight()/2 && v.y<=1.5f*textureDisparo.getHeight()){
               //Disparo
               //Gdx.app.log("DISPARO","Dispara");
            //Disparar
               if( bala ==null){
                   bala=new Bala(texturaBala,
                           nave.sprite.getX()+nave.sprite.getWidth()/2-texturaBala.getWidth()/2,
                           nave.sprite.getY()+nave.sprite.getHeight());
               }
           }else {
               //No Dispara
               if(v.x < ANCHO/2) {
                   //primera mitad, mover izq
                   // nave.mover(-DELTA_X);
                   moviendoIzq=true;
               }else {
                   //segunda mitad, mover derecha
                   //nave.mover(DELTA_X);
                   moviendoDer=true;
               }
           }
            return true;
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
