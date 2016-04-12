/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/**
 *
 * @author chengsong01px2015
 */
public abstract class SpawnSpell extends Spell {
    protected final Array<Spell> spells;
    protected float x, y;
    protected float time;
    
    public SpawnSpell(World world, int playerNum, boolean facesRight) {
        super(world, 0, 0, 0, playerNum, facesRight);
        x = -1;
        y = -1;
        spells = new Array<Spell>();
        time = 0;
    }
    
    @Override
    public boolean update(float dt){
        if(!alive) return false;
        
        time+=dt;
        
        boolean allDone = true;
        Iterator<Spell> itr = spells.iterator();
        while(itr.hasNext()){
            Spell s = itr.next();
            if(!s.update(dt)) itr.remove();
            else allDone = false;
        }
        
        return !allDone;
    }
    
    @Override
    public void render(SpriteBatch sb){
        for(Spell s:spells){
            s.render(sb);
        }
    }
    
    @Override
    public void cast(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void createFixture(){
        //do nothing
    }
    
    @Override
    public void land(){
        //do nothing
    }
    
    @Override
    public void die(){
        for(Spell s:spells){
            s.die();
        }
    }
    
}
