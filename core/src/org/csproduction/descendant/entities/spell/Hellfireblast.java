/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.entities.spell;

import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author chengsong01px2015
 */
public class Hellfireblast extends SpawnSpell{

    public Hellfireblast(World world, int playerNum, boolean facesRight) {
        super(world, playerNum,facesRight);
    }
    
    @Override
    public void cast(float x, float y){
        super.cast(x, y);
        for(int i=0;i<=2;i++){
            for(int j=0;j<3;j++){
                Spell s = new Fireblast(world,playerNum,facesRight);
                s.cast(x+j*30*(facesRight?1:-1), y+i*64);
                spells.add(s);
            }
        }
    }
}
