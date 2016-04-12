/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.screen;

import org.csproduction.descendant.GameMain;

/**
 *
 * @author chengsong01px2015
 */
public class MainMenuScreen extends BasicScreen {

    public MainMenuScreen(GameMain game) {
        super(game);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        clear();
        sb.begin();
        sb.setProjectionMatrix(stableCam.combined);
        game.font.draw(sb, "MAIN MENU", GameMain.V_WIDTH/2-30, GameMain.V_HEIGHT/2+10);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
