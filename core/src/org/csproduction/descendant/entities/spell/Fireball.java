/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static org.csproduction.descendant.B2D.B2DVars.PPM;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public class Fireball extends ProjectileSpell {
    
    public Fireball(World world, int playerNum, boolean facesRight){
        super(world, 64, 64, new Animation(GameMain.res.getTexture("fireball"),64,64,1/4f), 1, 3,0,playerNum,facesRight);
    }
    
    @Override
    protected void createFixture() {
        FixtureDef fdef = createSpellFixture();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/2/PPM, 5/2/PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
        
        shape.dispose();
    }
    
}
