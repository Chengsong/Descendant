/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.math.Vector2;
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
public class Airslash extends FixedSpell{

    public Airslash(World world, int playerNum, boolean facesRight) {
        super(world, 64, 64, new Animation(GameMain.res.getTexture("airslash"),64,64,1/16f), 2, 60, -5, playerNum,facesRight);
    }

    @Override
    protected void createFixture() {
        FixtureDef fdef = createSpellFixture();
        
        PolygonShape s = new PolygonShape();
        s.set(new Vector2[]{new Vector2(10/PPM,-22/PPM),new Vector2(-10/PPM,17/PPM),new Vector2(23/PPM,-9/PPM)});
        fdef.shape = s;
        body.createFixture(fdef);
        s.dispose();
    }
    
}
