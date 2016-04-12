/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class StaticEntity extends Entity{
    protected StaticEntity(World world, float width, float height) {
        super(null, width, height);
    }
    
    protected StaticEntity(World world, float width, float height, Animation animation) {
        super(null, width, height, animation);
    }
    
    @Override
    protected BodyDef createBodyDef(float x,float y){
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.type = BodyType.StaticBody;
        return bdef;
    }
}
