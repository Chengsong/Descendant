/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class FixedSpell extends Spell {
    protected float xOffset, yOffset;
    
    protected FixedSpell(World world, float imageWidth, float imageHeight, int damage, float xOffset, float yOffset, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, damage, playerNum, facesRight);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    protected FixedSpell(World world, float imageWidth, float imageHeight, Animation animation, int damage, float xOffset, float yOffset, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, animation, damage, playerNum, facesRight);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    @Override
    protected BodyDef createBodyDef(float x, float y){
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.allowSleep = false;
        return bdef;
    }
    
    @Override
    public boolean update(float dt){
        
        if(!super.update(dt)) return false;
        
        if(currentAnim.getNumPlayed()>0){
            alive = false;
            die();
            return false;
        }
        
        return true;
    }
    
    @Override
    public void cast(float x, float y){
        x+= facesRight ? xOffset:-xOffset;
        y+=yOffset;
        super.cast(x, y);
    }
    
    @Override
    public void land(){
        //do nothing on land
    }
    
    protected void destoryFixture(){
        body.destroyFixture(body.getFixtureList().get(0));
    }
    
}
