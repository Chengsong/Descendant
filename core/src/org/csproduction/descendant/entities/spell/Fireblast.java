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
public class Fireblast extends FixedSpell {

    public Fireblast(World world, int playerNum, boolean facesRight) {
        super(world, 128, 64, new Animation(GameMain.res.getTexture("fireblast"),128,64,1/10f), 5, 77, -5, playerNum,facesRight);
    }

    @Override
    protected void createFixture() {
    }
    
    @Override
    public boolean update(float dt){
        if(!super.update(dt)) return false;
        
        int frame = currentAnim.getFrameNumber();
        if(7<=frame&&frame<11&&body.getFixtureList().size==0) solidify();
        if(frame>=11&&body.getFixtureList().size!=0) destoryFixture();
        
        return true;
    }
    
    private void solidify(){
        FixtureDef fdef = createSpellFixture();
        PolygonShape s = new PolygonShape();
        s.setAsBox(imageWidth/2/PPM, 22/PPM);
        fdef.shape = s;
        body.createFixture(fdef);
        s.dispose();
    }
}
