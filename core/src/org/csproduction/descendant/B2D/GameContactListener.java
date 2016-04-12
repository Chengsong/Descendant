/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.B2D;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import org.csproduction.descendant.entities.Player;
import org.csproduction.descendant.entities.spell.Spell;

import static org.csproduction.descendant.B2D.B2DVars.Bits.*;

/**
 *
 * @author chengsong01px2015
 */
public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        Vector2 normal = contact.getWorldManifold().getNormal();
        
        switch(fa.getFilterData().categoryBits){
            case BIT_PLAYER:
                Player p = ((Player)fa.getBody().getUserData());
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                        if(normal.y<0){
                            boolean[] groundArray = (boolean[])fb.getUserData();
                            if(!groundArray[0]){
                                p.changeGroundCount(1);
                                groundArray[0] = true;
                            }
                        }
                    break;
                        
                    case BIT_SPELL:
                    case BIT_SPELL2:
                        ((Spell)fb.getBody().getUserData()).land();
                        p.takeDamage(((Spell)fb.getBody().getUserData()).getDamage());
                    break;
                        
                }
            break;
                
            case BIT_PLAYER2:
                p = ((Player)fa.getBody().getUserData());
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                        if(normal.y<0){
                            boolean[] groundArray = (boolean[])fb.getUserData();
                            if(!groundArray[1]){
                                p.changeGroundCount(1);
                                groundArray[1] = true;
                            }
                        }
                    break;
                        
                    case BIT_SPELL:
                    case BIT_SPELL2:
                        ((Spell)fb.getBody().getUserData()).land();
                        p.takeDamage(((Spell)fb.getBody().getUserData()).getDamage());
                    break;
                        
                }
            break;
            
            case BIT_GROUND:
                switch(fb.getFilterData().categoryBits){
                    case BIT_PLAYER: 
                        p = ((Player)fb.getBody().getUserData());
                        if(normal.y<0){
                            boolean[] groundArray = (boolean[])fa.getUserData();
                            if(!groundArray[0]){
                                p.changeGroundCount(1);
                                groundArray[0] = true;
                            }
                        }
                    break;
                        
                    case BIT_PLAYER2:
                        p = ((Player)fb.getBody().getUserData());
                        if(normal.y<0){
                            boolean[] groundArray = (boolean[])fa.getUserData();
                            if(!groundArray[1]){
                                p.changeGroundCount(1);
                                groundArray[1] = true;
                            }
                        }
                    break;
                        
                    case BIT_SPELL:
                    case BIT_SPELL2:
                        ((Spell)fb.getBody().getUserData()).land();
                    break;
                }
                break;
                
            case BIT_SPELL:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND:
                        ((Spell)fa.getBody().getUserData()).land();
                    break;
                    
                    case BIT_PLAYER:
                    case BIT_PLAYER2:
                        ((Spell)fa.getBody().getUserData()).land();
                        ((Player)fb.getBody().getUserData()).takeDamage(((Spell)fa.getBody().getUserData()).getDamage());
                    break;
                        
                    case BIT_SPELL2:
                        ((Spell)fa.getBody().getUserData()).land();
                        ((Spell)fb.getBody().getUserData()).land();
                    break;
                }
            break;
                
            case BIT_SPELL2:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND:
                        ((Spell)fa.getBody().getUserData()).land();
                    break;
                    
                    case BIT_PLAYER:
                    case BIT_PLAYER2:
                        ((Spell)fa.getBody().getUserData()).land();
                        ((Player)fb.getBody().getUserData()).takeDamage(((Spell)fa.getBody().getUserData()).getDamage());
                    break;
                    
                    case BIT_SPELL:
                        ((Spell)fa.getBody().getUserData()).land();
                        ((Spell)fb.getBody().getUserData()).land();
                    break;
                }
            break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        switch(fa.getFilterData().categoryBits){
            case BIT_PLAYER:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                        boolean[] groundArray = (boolean[])fb.getUserData();
                        if(groundArray[0]){
                            Player p = ((Player)fa.getBody().getUserData());
                            p.changeGroundCount(-1);
                            groundArray[0] = false;
                        }
                    break;
                        
                }
            break;
                
            case BIT_PLAYER2:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                            boolean[] groundArray = (boolean[])fb.getUserData();
                            if(groundArray[1]){
                                Player p = ((Player)fa.getBody().getUserData());
                                p.changeGroundCount(-1);
                                groundArray[1] = false;
                            }
                    break;
                }
            break;
            
            case BIT_GROUND:
                switch(fb.getFilterData().categoryBits){
                    case BIT_PLAYER: 
                        boolean[] groundArray = (boolean[])fa.getUserData();
                        if(groundArray[0]){
                            Player p = ((Player)fb.getBody().getUserData());
                            p.changeGroundCount(-1);
                            groundArray[0] = false;
                        }
                        break;
                        
                    case BIT_PLAYER2:
                        groundArray = (boolean[])fa.getUserData();
                        if(groundArray[1]){
                            Player p = ((Player)fb.getBody().getUserData());
                            p.changeGroundCount(-1);
                            groundArray[1] = false;
                        }
                        break;
                }
            break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Vector2 normal = contact.getWorldManifold().getNormal();
        contact.resetFriction();
        if(normal.x!=0) contact.setFriction(0);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        Vector2 normal = contact.getWorldManifold().getNormal();
        
        Player p;
        Vector2 pv;
        switch(fa.getFilterData().categoryBits){
            case BIT_PLAYER:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                        p = ((Player)fa.getBody().getUserData());
                        pv = p.getPrevVelocity();
                        if(normal.x!=0&&pv.x!=0) p.restoreVY();
                        if(normal.y!=0&&pv.y!=0) p.restoreVX();
                    break;
                        
                }
            break;
                
            case BIT_PLAYER2:
                switch(fb.getFilterData().categoryBits){
                    case BIT_GROUND: 
                        p = ((Player)fa.getBody().getUserData());
                        pv = p.getPrevVelocity();
                        if(normal.x!=0&&pv.x!=0) p.restoreVY();
                        if(normal.y!=0&&pv.y!=0) p.restoreVX();
                    break;
                }
            break;
            
            case BIT_GROUND:
                switch(fb.getFilterData().categoryBits){
                    case BIT_PLAYER: 
                    case BIT_PLAYER2:
                        p = ((Player)fb.getBody().getUserData());
                        pv = p.getPrevVelocity();
                        if(normal.x!=0&&pv.x!=0) p.restoreVY();
                        if(normal.y!=0&&pv.y!=0) p.restoreVX();
                        break;
                }
            break;
        }
    }
    
}