package org.csproduction.descendant;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.csproduction.descendant.input.GameInput;
import org.csproduction.descendant.resource.Resource;
import org.csproduction.descendant.screen.LevelLoadingScreen;
import org.csproduction.descendant.screen.ResourceLoadingScreen;

public class GameMain extends Game {
    public static final String TITLE = "Descendant";
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    public static final int SCALE = 1;
    
    
    
    public static final Resource res = new Resource();
    
    private SpriteBatch sb;
    private OrthographicCamera stableCamera;
    
    //for debug
    public BitmapFont font;
    
    private Music currentMusic;

    @Override
    public void create () {
        //set log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        
        sb = new SpriteBatch();
        
        stableCamera = new OrthographicCamera();
        stableCamera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        
        font = new BitmapFont();
        font.scale(2);
        
        Gdx.input.setInputProcessor(new GameInput());
        
        this.setScreen(new ResourceLoadingScreen(this));
    }

    @Override
    public void render () {
        Gdx.graphics.setTitle(TITLE+" FPS: "+Gdx.graphics.getFramesPerSecond());
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        sb.dispose();
        font.dispose();
        res.disposeAll();
    }
    
    public void switchMusic(Music m){
        if(currentMusic!=null) currentMusic.stop();
        currentMusic = m;
        currentMusic.play();
    }

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getStableCamera() {
        return stableCamera;
    }
    
    public Resource getResource(){
        return res;
    }
    
}
