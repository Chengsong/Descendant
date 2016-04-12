/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author chengsong01px2015
 */
public class Animation {
    private TextureRegion[] frames;
    private float time;
    private float interval;
    private int currentFrame;
    private int numPlayed;
    
    public Animation(){
        frames = null;
        interval = 0;
        numPlayed = 0;
    }
    
    public Animation(TextureRegion[] frames){
        this(frames, 1/12f);
    }
    
    public Animation(TextureRegion[] frames, float interval){
        this.frames = frames;
        this.interval = interval;
        currentFrame = 0;
        time = 0;
        numPlayed = 0;
    }
    
    public Animation (Texture texture, int width, int height, float interval){
        this(TextureRegion.split(texture, width, height)[0],interval);
    }
    
    public void reset(){
        if(frames == null) return;
        currentFrame = 0;
        time = 0;
        numPlayed=0;
    }
    
    public void update(float dt){
        if(frames ==null || interval <=0) return;
        time +=dt;
        while(time>=interval){
            step();
        }
    }
    
    private void step(){
        time -=interval;
        currentFrame++;
        if(currentFrame == frames.length){
            currentFrame = 0;
            numPlayed++;
        }
    }
    
    public TextureRegion getFrame(){
        if(frames==null) return null;
        
        return frames[currentFrame];
    }
    
    public int getFrameNumber(){
        return currentFrame;
    }
    
    public int getNumPlayed(){
        return numPlayed;
    }
}
