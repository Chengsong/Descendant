/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.csproduction.descendant.GameMain;

import static org.csproduction.descendant.GameMain.res;
/**
 *
 * @author chengsong01px2015
 */
public class LevelLoadingScreen extends BasicScreen{
    private final String[] messages;
    private int frame;
    private final float animRate;
    private float time;
    
    private ExecutorService es;
    private Future<GameScreen> future;
    
    private boolean resourceLoaded;
    
    public LevelLoadingScreen(GameMain game) {
        super(game);
        messages = new String[] {"Loading","Loading.","Loading..","Loading...","Loading....","Loading.....","Loading......"};
        frame = 0;
        animRate = 1/6f;
        time = 0;
        
        es = Executors.newSingleThreadExecutor();
        
        resourceLoaded = false;
    }
    
    @Override
    public void show() {
        res.loadLevelResources();
    }

    @Override
    public void render(float dt) {
        if(!resourceLoaded && res.update()){
            res.finishLevelLoad();
            resourceLoaded = true;
            future = es.submit(new CreateWorld());
        }
        
        if(resourceLoaded && future.isDone()){
            try {
                game.setScreen(future.get());
            } catch (InterruptedException ex) {
                Gdx.app.error("FATAL", "Level Loading Interupted", ex);
                Gdx.app.exit();
            } catch (ExecutionException ex){
                Gdx.app.error("FATAL", "Level Loading FAILED", ex);
                Gdx.app.exit();
            }
        } else {
            update(dt);
            
            clear();
            sb.begin();
            sb.setProjectionMatrix(stableCam.combined);
            game.font.draw(sb, messages[frame], 500, 100);
            sb.end();
        }
    }
    
    private void update(float dt){
        time += dt;
        while(time>animRate){
            frame++;
            if(frame>messages.length - 1) frame = 0;
            
            time-=animRate;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if(future!=null&&!future.isDone()){
            future.cancel(true);
        }
    }
    
    
    /////////////////////THE INNER CALLABLE CLASS///////////////////////
    private class CreateWorld implements Callable<GameScreen> {

        @Override
        public GameScreen call() throws Exception {
            //Thread.sleep(3000);
            GameScreen gs = new GameScreen(game,res.getMap());
            return gs;
        }
    }
    
}
