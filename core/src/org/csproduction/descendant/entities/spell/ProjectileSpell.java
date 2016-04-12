/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.B2D.B2DVars;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class ProjectileSpell extends Spell {
    protected float vx, vy;

    protected ProjectileSpell(World world, float imageWidth, float imageHeight, int damage, float vx, float vy, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, damage, playerNum, facesRight);
        this.vx = vx;
        this.vy = vy;
    }

    protected ProjectileSpell(World world, float imageWidth, float imageHeight, Animation animation, int damage, float vx, float vy, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, animation, damage, playerNum, facesRight);
        this.vx = vx;
        this.vy = vy;
    }
    
    @Override
    protected BodyDef createBodyDef(float x, float y) {
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.linearVelocity.x = facesRight?vx:-vx;
        bdef.linearVelocity.y = vy;
        return bdef;
    }
    
    @Override
    protected FixtureDef createSpellFixture(){
        FixtureDef fdef = super.createSpellFixture();
        short bits = fdef.filter.maskBits;
        bits |= B2DVars.Bits.BIT_GROUND;
        fdef.filter.maskBits = bits;
        fdef.filter.groupIndex = B2DVars.Bits.BIT_SPELL_PROJECTILE;
        
        return fdef;
    }
    
    @Override
    public void land(){
        alive = false;
    }
}
