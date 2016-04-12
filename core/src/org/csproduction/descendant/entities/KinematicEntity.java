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
public abstract class KinematicEntity extends Entity{

    protected KinematicEntity(World world, float width, float height, Animation animation) {
        super(null, width, height, animation);
    }

    protected KinematicEntity(World world, float width, float height) {
        super(null, width, height);
    }
    
    @Override
    protected BodyDef createBodyDef(float x,float y){
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.type = BodyDef.BodyType.KinematicBody;
        return bdef;
    }
}
