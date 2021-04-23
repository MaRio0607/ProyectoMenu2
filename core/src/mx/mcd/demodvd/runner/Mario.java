package mx.mcd.demodvd.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.mcd.demodvd.Objeto;

/*
Representa al pesonaje que esta en el escenario
 */
public class Mario extends Objeto {
    private Animation<TextureRegion> animacionCorrer;
    private float timerAnimation; //sabre el frame que corresponde mostrar
    //Salto
    private final float yBase=54;    //Suelo, piso
    private float tAire;             //Tiempo que lleva en al aire
    private float tVuelo;            //Tiempo de vuelo total
    private final float v0y=200;     //Componete en y de la velocidad
    private final  float g= 150;    //Pixeles sobre segundo al cuadrado
    private  EstadoMario estadoMario; //SAltando,caminado, bajando


    public Mario(Texture texture, float x, float y){
        TextureRegion region=new TextureRegion(texture);
        TextureRegion[][]texturas= region.split(38,76);

        //cuadros para caminar
        TextureRegion[] arrFramesCaminar={ texturas[1][3],texturas[1][4], texturas[2][0]};
        animacionCorrer= new Animation<TextureRegion>(0.2f,arrFramesCaminar);
        animacionCorrer.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;

        //IDLE
        sprite=new Sprite(texturas[1][2]);
        sprite.setPosition(x,y);

        //estado inical
        estadoMario=EstadoMario.CAMINADO;
    }
    //Reescribir el metodo render para mostrar la animacion
    public void render(SpriteBatch batch){
        float delta= Gdx.graphics.getDeltaTime();
        switch (estadoMario){
            case CAMINADO:
                timerAnimation+=delta;
                TextureRegion frame=animacionCorrer.getKeyFrame(timerAnimation);
                batch.draw(frame, sprite.getX(),sprite.getY());
                break;
            case SALTANDO:
                actualizar();  //al saltar calcula la nueva poscion
                super.render(batch);
                break;
        }

    }
    //Calcula el movimient ertical
    private void actualizar() {
       float delta=Gdx.graphics.getDeltaTime();
        tAire+=5*delta;
        float y =yBase+v0y*tAire-0.5f*g*tAire*tAire;
        sprite.setY(y);
        //como saber si ya termino
        if(tAire>=tVuelo||y<=yBase){
            estadoMario=EstadoMario.CAMINADO;
            sprite.setY(yBase);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void saltar() {
        if (estadoMario != EstadoMario.SALTANDO){
            tAire=0; //inicia el salto
            tVuelo=2*v0y/g;
            estadoMario=EstadoMario.SALTANDO;
        }
    }
}
