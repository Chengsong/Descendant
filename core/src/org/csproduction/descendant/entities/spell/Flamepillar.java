/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author chengsong01px2015
 */
public class Flamepillar extends SpawnSpell{
    private final float rate;
    private final boolean[] casted;
    
    public Flamepillar(World world, int playerNum, boolean facesRight) {
        super(world, playerNum,facesRight);
        rate = 1/8f;
        casted = new boolean[20];
    }
    
    @Override
    public boolean update(float dt){
        if(!super.update(dt)) return false;
        
        for(int i=1;i<casted.length;i++){
            if(time>i*rate&&!casted[i]){
                Spell s = new Flamestrike(world,playerNum,facesRight);
                s.cast(x+(int)(Math.random()*300), y-64+(int)(Math.random()*150));
                spells.add(s);
                casted[i] = true;
            }
        }
        
        return true;
    }
    
    @Override
    public void cast(float x, float y){
        super.cast(x, y);
        Spell s = new Flamestrike(world,playerNum,facesRight);
        s.cast(x, y);
        spells.add(s);
        casted[0] = true;
    }
}
