/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.B2D.B2DVars;
import org.csproduction.descendant.entities.DynamicEntity;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class Spell extends DynamicEntity {
    protected int damage;
    protected int playerNum;
    
    protected Spell(World world, float imageWidth, float imageHeight, int damage, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, facesRight);
        this.damage = damage;
        this.playerNum = playerNum;
    }

    protected Spell(World world, float imageWidth, float imageHeight, Animation animation, int damage, int playerNum, boolean facesRight) {
        super(world, imageWidth, imageHeight, facesRight, animation);
        this.damage = damage;
        this.playerNum = playerNum;
    }

    @Override
    public void render(SpriteBatch sb) {
        TextureRegion frame = currentAnim.getFrame();
        int regionX = frame.getRegionX();
        int regionY = frame.getRegionY();
        int regionWidth = frame.getRegionWidth();
        int regionHeight = frame.getRegionHeight();
        
        sb.draw(frame.getTexture(), getX() - imageWidth/2, getY() - imageHeight/2,
                imageWidth, imageHeight, regionX, regionY, 
                regionWidth, regionHeight, !facesRight, false);
    }
    
    @Override
    public boolean update(float dt){
        if(!alive){
            die();
            return false;
        }
        
        super.update(dt);
        
        return true;
    }
    
    /**
     * This method is called when the spell comes in contact with another object.
     */
    public abstract void land();
    
    @Override
    protected BodyDef createBodyDef(float x, float y){
        BodyDef bdef = super.createBodyDef(x, y);
        bdef.gravityScale = 0;
        return bdef;
    }
    
    /**
     * This method wraps createBody() method, all spells should be created with this.
     * @param world current world
     * @param x 
     * @param y 
     */
    public void cast(float x, float y){
        this.createBody(x, y);
    }
    
    public int getDamage(){
        return damage;
    }
    
    /**
     * A little helper method, to create fixture for spells without the shape.
     * @return FixtureDef of a spell with filtering properties
     */
    protected FixtureDef createSpellFixture(){
        FixtureDef fdef = new FixtureDef();
        fdef.density = 1;
        if(playerNum==1){
            fdef.filter.categoryBits = B2DVars.Bits.BIT_SPELL;
            fdef.filter.maskBits = B2DVars.Bits.BIT_PLAYER2;
        }
        if(playerNum==2){
            fdef.filter.categoryBits = B2DVars.Bits.BIT_SPELL2;
            fdef.filter.maskBits = B2DVars.Bits.BIT_PLAYER;
        }
        fdef.friction = 0f;
        fdef.isSensor = true;
        
        return fdef;
    }
}
