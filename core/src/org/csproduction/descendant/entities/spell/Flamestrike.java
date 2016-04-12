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
public class Flamestrike extends FixedSpell {
    public Flamestrike(World world, int playerNum, boolean facesRight) {
        super(world, 64, 128, new Animation(GameMain.res.getTexture("flamestrike"),64,128,1/10f), 8, 60, 30, playerNum,facesRight);
    }

    @Override
    protected void createFixture() {
    }
    
    @Override
    public boolean update(float dt){
        if(!super.update(dt)) return false;
        
        int frame = currentAnim.getFrameNumber();
        if(6<=frame&&frame<9&&body.getFixtureList().size==0) solidify();
        if(frame>=9&&body.getFixtureList().size!=0) destoryFixture();
        
        return true;
    }
    
    private void solidify(){
        FixtureDef fdef = createSpellFixture();
        PolygonShape s = new PolygonShape();
        s.setAsBox(24/PPM, imageHeight/2/PPM);
        fdef.shape = s;
        body.createFixture(fdef);
        s.dispose();
    }
}
