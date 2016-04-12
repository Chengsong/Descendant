/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class DynamicEntity extends Entity{
    protected boolean facesRight;
    protected boolean alive;
    
    protected DynamicEntity(World world, float imageWidth, float imageHeight, boolean facesRight) {
        super(world, imageWidth, imageHeight);
        alive = true;
        this.facesRight = facesRight;
    }

    protected DynamicEntity(World world, float imageWidth, float imageHeight, boolean facesRight, Animation animation) {
        super(world, imageWidth, imageHeight, animation);
        alive = true;
        this.facesRight = facesRight;
    }
    
    @Override
    protected BodyDef createBodyDef(float x,float y){
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        return bdef;
    }
    
    /**
     * Removes this entity from the world.
     * DO NOT CALL THIS DURING WORLD UPDATE STEP
     */
    public void die(){
        world.destroyBody(body);
    }
    
    public boolean isAlive() {
        return alive;
    }
}
