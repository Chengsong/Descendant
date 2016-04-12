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
public class Groundslam extends SpawnSpell{
    private final float rate;
    private final boolean[] casted;
    
    public Groundslam(World world, int playerNum, boolean facesRight) {
        super(world, playerNum, facesRight);
        rate = 1/12f;
        casted = new boolean[5];
    }
    
    @Override
    public boolean update(float dt){
        if(!super.update(dt)) return false;
        
        for(int i=1;i<casted.length;i++){
            if(time>i*rate&&!casted[i]){
                Spell s = new Earthspike(world,playerNum,facesRight);
                s.cast(x+i*40*(facesRight?1:-1), y);
                spells.add(s);
                casted[i] = true;
            }
        }
        
        return true;
    }
    
    @Override
    public void cast(float x, float y){
        super.cast(x, y);
        Spell s = new Earthspike(world,playerNum,facesRight);
        s.cast(x, y);
        spells.add(s);
        casted[0] = true;
    }
}
