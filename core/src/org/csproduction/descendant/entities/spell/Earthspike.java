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
import org.csproduction.descendant.B2D.B2DVars;
import static org.csproduction.descendant.B2D.B2DVars.PPM;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.graphics.Animation;

/**
 *
 * @author chengsong01px2015
 */
public class Earthspike extends FixedSpell{
    
    public Earthspike(World world, int playerNum, boolean facesRight){
        super(world, 64, 64, new Animation(GameMain.res.getTexture("earthspike"),64,64,1/12f), 4, 36, -2, playerNum,facesRight);
    }
    
    @Override
    public boolean update(float dt){
        if(!super.update(dt)) return false;
        
        int frame = currentAnim.getFrameNumber();
        if(3<=frame&&frame<6&&body.getFixtureList().size==0) solidify();
        if(frame>=6&&body.getFixtureList().size!=0) destoryFixture();
        
        return true;
    }
    
    @Override
    protected void createFixture() {
        //fixture will be created later
    }
    
    private void solidify(){
        FixtureDef fdef = createSpellFixture();
        fdef.filter.groupIndex = B2DVars.Bits.BIT_SPELL_SHIELD;
        
        PolygonShape tri = new PolygonShape();
        tri.set(new Vector2[]{new Vector2(-26/PPM,-32/PPM),new Vector2(7/PPM,23/PPM),new Vector2(23/PPM,-32/PPM)});
        fdef.shape = tri;
        body.createFixture(fdef);
        tri.dispose();
    }
}
