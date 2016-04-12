/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.csproduction.descendant.B2D.B2DVars;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public abstract class Entity {
    protected final World world;
    
    protected float imageWidth, imageHeight;
    protected Animation currentAnim;
    protected Body body;
    
    protected Entity(World world, float imageWidth, float imageHeight, Animation animation){
        this.world = world;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.currentAnim = animation;
    }
    
    protected Entity(World world, float imageWidth, float imageHeight){
        this.world = world;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        currentAnim = new Animation();
    }
    
    /**
     * Renders the image of this entity.
     * @param sb the sprite batch of the application
     */
    public abstract void render(SpriteBatch sb);
    
    /**
     * THIS METHOD MUST BE CALLED AFTER ENTITY CONSTRUCTION.
     * Creates body and fixture for this entity
     * @param world world of the 2DBox engine
     * @param x x position of the body
     * @param y y position of the body
     */
    protected final void createBody(float x, float y){
        BodyDef bdef = createBodyDef(x,y);
        body = world.createBody(bdef);
        body.setUserData(this);
        createFixture();
    }
    
    protected BodyDef createBodyDef(float x,float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/B2DVars.PPM,y/B2DVars.PPM);
        bdef.allowSleep = true;
        bdef.fixedRotation = true;
        
        return bdef;
    }
    
    protected abstract void createFixture();
    
    /**
     * This method resets the currentAnim time.
     */
    public final void resetAnimation(){
        currentAnim.reset();
    }
    
    /**
     * Updates the entity properties, such as the currentAnim.
     * Must be called every frame update.
     * @param dt delta-time
     */
    public boolean update(float dt){
        currentAnim.update(dt);
        return true;
    }
    
    public Body getBody(){
        return body;
    }
    
    /**
     * Returns x in pixel coordinate.
     * @return pixel coordinate x of the center of the entity
     */
    public float getX(){
        return body.getPosition().x*B2DVars.PPM;
    }
    /**
     * Returns y in pixel coordinate.
     * @return pixel coordinate y of the center of the entity
     */
    public float getY(){
        return body.getPosition().y*B2DVars.PPM;
    }
    
    public float getImageWidth(){
        return imageWidth;
    }
    public float getImageHeight(){
        return imageHeight;
    }
}
