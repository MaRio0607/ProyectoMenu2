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
    }
    //Reescribir el metodo render para mostrar la animacion
    public void render(SpriteBatch batch){
        float delta= Gdx.graphics.getDeltaTime();
        timerAnimation+=delta;
        TextureRegion frame=animacionCorrer.getKeyFrame(timerAnimation);
        batch.draw(frame, sprite.getX(),sprite.getY());
    }


}
