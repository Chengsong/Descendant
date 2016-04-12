/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.resource.Resource;

/**
 *
 * @author chengsong01px2015
 */
public abstract class BasicScreen implements Screen{
    protected final GameMain game;
    
    protected final SpriteBatch sb;
    protected final OrthographicCamera stableCam;
    
    public BasicScreen(GameMain game) {
        this.game = game;
        this.sb = game.getSpriteBatch();
        this.stableCam = game.getStableCamera();
    }
    
    protected final void clear(){
        clear(0,0,0,1);
    }
    
    protected final void clear(float r, float g, float b){
        clear(r,g,b,1);
    }
    
    protected final void clear(float r, float g, float b, float a){
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
