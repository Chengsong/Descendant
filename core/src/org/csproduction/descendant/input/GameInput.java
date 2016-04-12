/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author chengsong01px2015
 */
public final class GameInput extends InputAdapter {
    private static final boolean[] keys = new boolean[255];
    private static final boolean[] pkeys = new boolean[255];
    
    /**
     * This method handles the values in previous keys.
     * ONLY CALL THIS METHOD AFTER INPUT HANDLING
     */
    public static final void update(){
        System.arraycopy(keys, 0, pkeys, 0, keys.length);
    }
    
    /**
     * This method checks if the key is currently down, no matter when it was pressed.
     * @param i the numerical value of the key
     * @return true/false
     */
    public static final boolean isHeld(int i){
        return keys[i];
    }
    
    /**
     * This method checks if the key is just pressed last frame.
     * @param i the numerical value of the key
     * @return true/false
     */
    public static final boolean isPressed(int i){
        return keys[i] && !pkeys[i];
    }
    
    /**
     * This method checks if the key has just been released last frame.
     * @param i the numerical value of the key
     * @return true/false
     */
    public static final boolean isReleased(int i){
        return !keys[i] && pkeys[i];
    }
    
    /**
     * This method checks if the key is currently up, regardless of when it was released.
     * @param i the numerical value of the key
     * @return true/false
     */
    public static final boolean isUp(int i){
        return !keys[i];
    }
    
    /////////////////////INPUT ADAPTER METHODS////////////////////
    @Override
    public final boolean keyDown (int k){
        keys[k] = true;
        
        return true;
    }
    
    @Override
    public final boolean keyUp (int k){
        keys[k] = false;
        
        return true;
    }
}
