package mx.mcd.demodvd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import mx.mcd.demodvd.runner.PantallaRunner;
import mx.mcd.demodvd.spaceInvaders.PantallaSpaceInvaders;

public class PantallaMenu extends Pantalla {
    private Juego juego;
    // fondo
    private Texture texturaFondo;
    //escena
    private Stage escenaMenu;

    public PantallaMenu(Juego juego) {

        this.juego=juego;
    }

    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        //Fondo
        texturaFondo=new Texture("Menu/FONDO.png");
        //se crea la escena del menu
        escenaMenu=new Stage(vista);
        //crear el boton
        Button btnJugarSpace = crearBoton("Menu/btn_jugar_space.png", "Menu/invaders2.png");
        btnJugarSpace.setPosition(ANCHO*2/3,ALTO*2/3, Align.center);
        // Agrega el botón a la escena
        escenaMenu.addActor(btnJugarSpace);
        //le dice que hacer cuando se le de click
        btnJugarSpace.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                juego.setScreen(new PantallaSpaceInvaders(juego));
            }
        });

        Button btnMario= crearBoton("Menu/btn_jugar_mario.png","Menu/bross 2.png");
        btnMario.setPosition(ANCHO/3,ALTO*2/3, Align.center);
        escenaMenu.addActor(btnMario);
        btnMario.addListener(new ClickListener(){
           public void clicked(InputEvent event, float x, float y){
               juego.setScreen(new PantallaMario(juego));
           }
        });

        Button btnConfig= crearBoton("Menu/btn_configuracion.png","Menu/confi2.png");
        btnConfig.setPosition(ANCHO*2/3,ALTO/3,Align.center);
        escenaMenu.addActor(btnConfig);
        btnConfig.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                juego.setScreen(new PantallaConfig(juego));
            }
        });

        Button btnRunner= crearBoton("Menu/btn_jugar_runner.png","Menu/runner2.png");
        btnRunner.setPosition(ANCHO/3,ALTO/3,Align.center);
        escenaMenu.addActor(btnRunner);
        btnRunner.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                juego.setScreen(new PantallaRunner(juego));
            }
        });




        //Atiende los eventos de entrada
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String image_path, String inverse_image_path) {
        Texture texturaBoton = new Texture(image_path);
        TextureRegionDrawable trdButton = new TextureRegionDrawable(texturaBoton);
        Texture texturaInverso = new Texture(inverse_image_path);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);

        return new Button(trdButton,trdBtnInverso);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,1,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();

        //Escena despues del fondo
        escenaMenu.draw();

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
