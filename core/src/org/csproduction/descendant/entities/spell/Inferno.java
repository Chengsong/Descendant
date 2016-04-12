package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.World;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chengsong01px2015
 */
public class Inferno extends SpawnSpell{

    public Inferno(World world, int playerNum, boolean facesRight) {
        super(world, playerNum,facesRight);
    }
    
    @Override
    public void cast(float x, float y){
        super.cast(x, y);
        Spell s = new Hellfireblast(world,playerNum,facesRight);
        s.cast(x, y);
        spells.add(s);
        s = new Flamepillar(world,playerNum,facesRight);
        s.cast(x, y);
        spells.add(s);
    }
    
}
