/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.csproduction.descendant.B2D.B2DVars.Bits;
import static org.csproduction.descendant.B2D.B2DVars.PPM;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.entities.spell.*;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public final class Player extends DynamicEntity{
    private final Animation idleAnim;
    private final Animation moveAnim;
    
    private boolean wasIdle;
    private Vector2 prevVelocity;
    
    private int groundCount;
    private int hp;
    private final float friction;
    
    private final int playerNum;
    
    public Player(World world, int playerNum, boolean facesRight) {
        super(world, 64, 64, facesRight, new Animation(GameMain.res.getTexture("main_idle_L"),64,64,1/4f));
        this.playerNum = playerNum;
        idleAnim = currentAnim;
        moveAnim = new Animation(GameMain.res.getTexture("main_move_L"),64,64,1/6f);
        wasIdle = true;
        prevVelocity = new Vector2(0,0);
        groundCount = 0;
        hp=100;
        friction = 1f;
    }
    
    @Override
    public void render(SpriteBatch sb) {
        TextureRegion frame = currentAnim.getFrame();
        int regionX = frame.getRegionX();
        int regionY = frame.getRegionY();
        int regionWidth = frame.getRegionWidth();
        int regionHeight = frame.getRegionHeight();
        
        sb.draw(frame.getTexture(), getX() - imageWidth/2, getY() - 64/2 -1,
                64, 64, regionX, regionY, 
                regionWidth, regionHeight, !facesRight, false);
    }
    /**
     * THIS METHOD MUST BE CALLED AFTER PLAYER CONSTRUCTION.REPLACES CREATEBODY()
     * Creates body and fixture for this entity
     * @param world world of the 2DBox engine
     * @param x x position of the body
     * @param y y position of the body
     */
    public void spawn(float x, float y){
        createBody(x,y);
    }
    
    @Override
    protected void createFixture() {
        FixtureDef fdef = new FixtureDef();
        fdef.density = 60;
        if(playerNum ==1){
            fdef.filter.categoryBits = Bits.BIT_PLAYER;
            fdef.filter.maskBits = Bits.BIT_GROUND|Bits.BIT_SPELL2;
        }
        else if(playerNum ==2){
            fdef.filter.categoryBits = Bits.BIT_PLAYER2;
            fdef.filter.maskBits = Bits.BIT_GROUND|Bits.BIT_SPELL;
        }
        fdef.friction = friction;
        fdef.isSensor = false;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/2/PPM, 64/2/PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("player_body");
        
        shape.dispose();
    }
    
    @Override
    public boolean update(float dt){
        if(!alive) return false;
            
        Vector2 v = body.getLinearVelocity();
        if(facesRight&&v.x<0||!facesRight&&v.x>0) turn();
        
        super.update(dt);
        if(v.equals(new Vector2(0,0))){
            if(wasIdle && currentAnim!=idleAnim)
                currentAnim = idleAnim;
            wasIdle = true;
        } else {
            wasIdle = false;
        }
        
        prevVelocity = new Vector2(v.x,v.y);
        
        return true;
    }
    
    public void takeDamage(int damage){
        hp-=damage;
        if(hp<=0) alive = false;
    }
    
    public void move(float impulse){
        if(currentAnim!=moveAnim){
            currentAnim = moveAnim;
        }
        if(isOnGround()) body.setLinearVelocity(0, body.getLinearVelocity().y);
        body.applyLinearImpulse(impulse, 0, body.getPosition().x, body.getPosition().y,true);
    }
    
    public void jump(float impulse){
        body.applyLinearImpulse(0, impulse, body.getPosition().x, body.getPosition().y,true);
    }
    
    public void attack(Array<Spell> spells, String name){
        Spell s = null;
        if(name.equals("fireball")) s= new Fireball(world,playerNum,facesRight);
        else if (name.equals("earthspike")) s = new Earthspike(world,playerNum,facesRight);
        else if (name.equals("airslash")) s = new Airslash(world,playerNum,facesRight);
        else if(name.equals("fireblast")) s = new Fireblast(world,playerNum,facesRight);
        else if(name.equals("flamestrike")) s = new Flamestrike(world,playerNum,facesRight);
        else if(name.equals("flamepillar")) s = new Flamepillar(world,playerNum,facesRight);
        else if(name.equals("groundslam")) s = new Groundslam(world,playerNum,facesRight);
        else if(name.equals("hellfireblast")) s = new Hellfireblast(world,playerNum,facesRight);
        else if(name.equals("inferno")) s = new Inferno(world,playerNum,facesRight);
        else if(name.equals("iceshard")) s = new Iceshard(world,playerNum,facesRight);
        s.cast(getX(), getY());
        spells.add(s);
    }

    private void turn(){
        facesRight = !facesRight;
    }
    
    public boolean isOnGround() {
        return groundCount>0;
    }

    public void changeGroundCount(int delta) {
        groundCount+=delta;
    }
    
    public int getHP(){
        return hp;
    }
    
    public int getPlayerID(){
        return playerNum;
    }
    
    public void restoreVX(){
        body.setLinearVelocity(prevVelocity.x, body.getLinearVelocity().y);
    }
    
    public void restoreVY(){
        body.setLinearVelocity(body.getLinearVelocity().x, prevVelocity.y);
    }

    public Vector2 getPrevVelocity() {
        return prevVelocity;
    }
    
    public void toggleFriction(boolean on){
        body.getFixtureList().get(0).setFriction( on?friction:0 );
    }
    
    public void setFriction(float fric){
        body.getFixtureList().get(0).setFriction( fric );
    }
    
    public void resetVelocity(){
        body.setLinearVelocity(0, body.getLinearVelocity().y);
    }
}
