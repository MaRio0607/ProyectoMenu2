package mx.mcd.demodvd.plataformas;

import com.badlogic.gdx.Screen;
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
    private OrthogonalTiledMapRenderer renderMapa;

    public PantallaPlataformas(Juego juego) {
    }

    @Override
    public void show() {
        AssetManager manager=new AssetManager();
        manager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapa/Mapa_Mario.tmx",TiledMap.class);
        manager.finishLoading();
        mapa=manager.get("mapa/Mapa_Mario.tmx");
        renderMapa=new OrthogonalTiledMapRenderer(mapa);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);

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
