package inf112.skeleton.app;

import java.awt.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import java.util.random.*;

import javax.xml.catalog.Catalog;

public class View implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private Enemy enemy;
    private Controller controller = new Controller();
    public RectangleMapObject playerRect;
    public RectangleMapObject enemyRect;
    public boolean enemyexists;
    private Random random = new Random();
    private int points = 0;
    private BitmapFont font = new BitmapFont();

    


    @Override
    public void show() {
        map = new TmxMapLoader().load("src/main/java/inf112/skeleton/app/assets/mapet.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        player = new Player(new Sprite(new Texture(PlayerPics.DOWN.source)), 110, 110, ID.Player, controller, map, this);
        playerRect = new RectangleMapObject(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        enemy = new Enemy(150, 110, ID.Enemy, new Sprite(new Texture(PlayerPics.ENEMYDOWN.source)),controller, map, this);
        enemyRect = new RectangleMapObject(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        Gdx.input.setInputProcessor(controller);
        enemyexists = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(120, 120, 0);
        renderer.getBatch().setProjectionMatrix(camera.combined);
        

        
        //setter position på spiller;
        // camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        checkSpriteCollision();
        renderer.getBatch().begin();

        font.draw(renderer.getBatch(), "score: " + points, 50, 180);

        enemyRect.getRectangle().setPosition(enemy.x, enemy.y);
        enemy.draw(renderer.getBatch());
        playerRect.getRectangle().setPosition(player.x, player.y);
        player.draw(renderer.getBatch());
        

        renderer.getBatch().end();
        

    }

    public void checkSpriteCollision() {
        
        if (playerRect.getRectangle().overlaps(enemyRect.getRectangle())) {
            points ++;
            int x = random.nextInt(90, 160), y = random.nextInt(50, 180);
            enemy.x = x;
            enemy.y = y;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
  
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
       map.dispose();
       renderer.dispose();
       player.getTexture().dispose();
    }
    
}
