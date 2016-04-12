/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.csproduction.descendant.GameMain;

import static org.csproduction.descendant.GameMain.res;

/**
 *
 * @author chengsong01px2015
 */
public class ResourceLoadingScreen extends BasicScreen{
    private final String[] messages;
    private int frame;
    private final float animRate;
    private float time;

    public ResourceLoadingScreen(GameMain game) {
        super(game);
        messages = new String[] {"Loading","Loading.","Loading..","Loading...","Loading....","Loading.....","Loading......"};
        frame = 0;
        animRate = 1/4f;
        time = 0;
    }

    @Override
    public void show() {
        res.loadGeneralResources();
    }

    @Override
    public void render(float dt) {
        if(res.update()){
            res.finishGeneralLoad();
            game.setScreen(new LevelLoadingScreen(game));
        } else{
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
        dispose();
    }

    @Override
    public void dispose() {
    }
    
}
