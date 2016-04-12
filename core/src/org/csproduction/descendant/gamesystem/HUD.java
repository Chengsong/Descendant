/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.gamesystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.entities.Player;

/**
 *
 * @author chengsong01px2015
 */
public class HUD {
    private Texture fireRune; 
    private BitmapFont font;
    private Player p1, p2;

    public HUD(){
        fireRune = GameMain.res.getTexture("firerune");
    }
    
    public HUD(BitmapFont font, Player p1, Player p2){
        this();
        this.font = font;
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public void render(SpriteBatch sb){
        sb.draw(fireRune, 50, 500);
        font.draw(sb, ""+p1.getHP(), 50, 580);
        font.draw(sb, ""+p2.getHP(), 700, 580);
    }
        
}
