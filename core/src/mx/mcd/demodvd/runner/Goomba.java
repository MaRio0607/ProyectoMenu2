package mx.mcd.demodvd.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.mcd.demodvd.Objeto;
/*
Representa ememigos
 */
public class Goomba extends Objeto {
    private Animation<TextureRegion> animation;
    private float timerAnimacion=0;
    //fisica
    private float velocidadX=-300;    //pixeles/segndo

    public Goomba(Texture texture,float x, float y){
        TextureRegion region= new TextureRegion(texture);
        TextureRegion [][] texturas= region.split(31,20);

        //Animacion
        TextureRegion[] arrFrames={texturas[0][0], texturas[0][1]};
        animation= new Animation<TextureRegion>(0.3f,arrFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        sprite=new Sprite(texturas [0][0]);
        sprite.setPosition(x,y);
    }
    @Override
    public void render(SpriteBatch batch){
        timerAnimacion+= Gdx.graphics.getDeltaTime();
        TextureRegion frame= animation.getKeyFrame(timerAnimacion);
        batch.draw(frame,sprite.getX(),sprite.getY());
    }
    //mover enemigos
    public void moverIzquierda(float delta) {
        float dx =velocidadX*delta;
        sprite.setX(sprite.getX()+dx);

    }
}
