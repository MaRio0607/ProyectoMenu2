package mx.mcd.demodvd.plataformas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import mx.mcd.demodvd.Juego;
import mx.mcd.demodvd.Pantalla;

public class PantallaPlataformas extends Pantalla {
    //Mapa
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    public PantallaPlataformas(Juego juego) {
    }

    @Override
    public void show() {
        AssetManager manager=new AssetManager();
        manager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapas/MapaMario.tmx",TiledMap.class);
        manager.finishLoading();
        mapa=manager.get("mapas/MapaMario.tmx");
        rendererMapa =new OrthogonalTiledMapRenderer(mapa);
        //poner input procesor
        Gdx.input.setInputProcessor(new ProcesadorInput());
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        rendererMapa.setView(camara);
        rendererMapa.render();

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

    private class ProcesadorInput implements InputProcessor {
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
            return false;
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
