/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import org.csproduction.descendant.GameMain;
import org.csproduction.descendant.entities.Player;

import static org.csproduction.descendant.B2D.B2DVars.*;
import static org.csproduction.descendant.B2D.B2DVars.Bits.*;
import org.csproduction.descendant.B2D.GameContactListener;
import org.csproduction.descendant.entities.spell.Spell;
import org.csproduction.descendant.gamesystem.HUD;
import org.csproduction.descendant.graphics.BoundedCamera;
import org.csproduction.descendant.input.GameInput;

/**
 *
 * @author chengsong01px2015
 */
public class GameScreen extends BasicScreen {
    private static final float STEP = 1/60f;
    private static final float MAX_VX = 4;

    private final World world;
    private float time;
    private float accum;
    
    private final BoundedCamera gameCam;
    
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera debugCam;
    
    private final TiledMap map;
    private OrthogonalTiledMapRenderer tmr;
    
    private final HUD hud;
    
    private Player player, player2;
    private final Array<Spell> spells;
    
    public GameScreen(GameMain game, TiledMap map) {
        super(game);
        this.map = map;
        
        world = new World(new Vector2(0,-9.81f),true);
        world.setContactListener(new GameContactListener());
        
        time = 0;
        
        gameCam = new BoundedCamera();
        gameCam.setToOrtho(false, GameMain.V_WIDTH, GameMain.V_HEIGHT);
        
        debugCam = new OrthographicCamera();
        debugCam.setToOrtho(false, GameMain.V_WIDTH/PPM, GameMain.V_HEIGHT/PPM);
        
        createPlayer(350,400);
        
        hud = new HUD(game.font,player,player2);
        
        spells = new Array<Spell>();
        
        createMap();
    }

    @Override
    public void show() {
        b2dr = new Box2DDebugRenderer();
        tmr = new OrthogonalTiledMapRenderer(map);
        game.switchMusic(GameMain.res.getMusic("chaosf"));
    }

    @Override
    public void render(float dt) {
        //RENDER THE GAME
        long start = System.nanoTime();
        
        if(player!=null){
            gameCam.setPosition(player.getX(), player.getY());
            gameCam.update();
        }
        
        clear(1,1,1);
        tmr.setView(gameCam);
        tmr.render();
        
        sb.setProjectionMatrix(gameCam.combined);
        sb.begin();
        if(player!=null)player.render(sb);
        if(player2!=null)player2.render(sb);
        
        for(Spell s:spells){
            s.render(sb);
        }
        
        sb.setProjectionMatrix(stableCam.combined);
        hud.render(sb);
        
        sb.end();
        
        //b2dr.render(world, debugCam.combined);
        dt += (System.nanoTime()-start)/1000000000f;
        
        //UPDATE THE WORLD
        update(dt);
        
    }
    
    private void update(float dt){
        if(!handleInput()){
            game.setScreen(new GameScreen(game,map));
            return;
        }
        
        stepWorld(dt);
        
        if(player!=null&&!player.update(dt)){
            player.die();
            player = null;
        }
        if(player2!=null&&!player2.update(dt)){
            player2.die();
            player2 = null;
        }
        
        updateSpells(dt);
    }
    
    private boolean handleInput(){
        if(GameInput.isHeld(Keys.ENTER)&&(GameInput.isHeld(Keys.SHIFT_LEFT)||GameInput.isHeld(Keys.SHIFT_RIGHT))){
            return false;
        }
        
        if(player!=null){
            player.resetVelocity();
            Body pb = player.getBody();
            if(GameInput.isPressed(Keys.W)){
                if(player.isOnGround()){
                    player.jump(200);
                }
            }

            if(GameInput.isHeld(Keys.D)){
                if(player.isOnGround()){
                    player.move(150);
                    player.toggleFriction(false);
                } else {
                    player.move(Math.abs(Math.max(player.getPrevVelocity().x*pb.getMass(), 75)));
                }
            }else if(GameInput.isReleased(Keys.D)){
                player.toggleFriction(true);
            }

            if(GameInput.isHeld(Keys.A)){
                if(player.isOnGround()){
                    player.move(-150);
                    player.toggleFriction(false);
                } else {
                    player.move(-Math.abs(Math.min(player.getPrevVelocity().x*pb.getMass(), -75)));
                }
            }else if(GameInput.isReleased(Keys.A)){
                player.toggleFriction(true);
            }
            
            if(GameInput.isPressed(Keys.F)){
                player.attack(spells,"fireball");
            }
            
            if(GameInput.isPressed(Keys.V)&&pb.getLinearVelocity().y==0){
                if(player.isOnGround()) player.attack(spells,"earthspike");
            }
            
            if(GameInput.isPressed(Keys.R)){
                player.attack(spells,"airslash");
            }
            
            if(GameInput.isPressed(Keys.C)){
                player.attack(spells,"fireblast");
            }
            
            if(GameInput.isPressed(Keys.B)){
                player.attack(spells,"flamestrike");
            }
            
            if(GameInput.isPressed(Keys.T)){
                player.attack(spells,"iceshard");
            }
            
            if(GameInput.isPressed(Keys.Y)){
                player.attack(spells,"hellfireblast");
            }
            
            if(GameInput.isPressed(Keys.H)){
                player.attack(spells,"inferno");
            }
            
            if(GameInput.isPressed(Keys.G)&&pb.getLinearVelocity().y==0){
                if(player.isOnGround()) player.attack(spells,"groundslam");
            }
        }
        
        if(false){//player2!=null){
            Body pb = player2.getBody();
            if(GameInput.isPressed(Keys.I)){
                if(player2.isOnGround()){
                    player2.jump(240);
                }
            }

            if(GameInput.isHeld(Keys.L)){
                if(player2.isOnGround()){
                    player2.move(500);
                    if(pb.getLinearVelocity().x>MAX_VX) pb.setLinearVelocity(MAX_VX, pb.getLinearVelocity().y);
                } else {
                    player2.move(150);
                    if(pb.getLinearVelocity().x>MAX_VX) pb.setLinearVelocity(MAX_VX, pb.getLinearVelocity().y);
                }
            }

            if(GameInput.isHeld(Keys.J)){
                if(player2.isOnGround()){
                    player2.move(-500);
                    if(pb.getLinearVelocity().x<-MAX_VX) pb.setLinearVelocity(-MAX_VX, pb.getLinearVelocity().y);
                } else {
                    player2.move(-150);
                    if(pb.getLinearVelocity().x<-MAX_VX) pb.setLinearVelocity(-MAX_VX, pb.getLinearVelocity().y);
                }
            }

            if(GameInput.isPressed(Keys.SEMICOLON)){
                player2.attack(spells,"fireball");
            }
            
            if(GameInput.isPressed(Keys.SLASH)&&pb.getLinearVelocity().y==0){
                if(player2.isOnGround()) player2.attack(spells,"earthspike");
            }
            
            if(GameInput.isPressed(Keys.P)){
                player2.attack(spells,"airslash");
            }
            
            if(GameInput.isPressed(Keys.PERIOD)){
                player2.attack(spells,"fireblast");
            }
        }
        
        ///////////////CALL THIS LAST///////////////
        GameInput.update();
        
        return true;
    }
    
    private void updateSpells(float dt){
        Iterator<Spell> i = spells.iterator();
        while(i.hasNext()){
            Spell s = i.next();
            if(!s.update(dt)) i.remove();
        }
    }
    
    private void stepWorld(float dt){
        time+=dt;
        
        while(time>STEP){
            world.step(STEP, 8, 3);
            time-=STEP;
        }
        
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
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        tmr.dispose();
        map.dispose();
    }

    private void createMap() {
        MapLayer layer = map.getLayers().get("collision");
        layer.setVisible(false);
        
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.StaticBody;
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = BIT_GROUND;
        fdef.filter.maskBits = BIT_PLAYER|BIT_PLAYER2|BIT_SPELL|BIT_SPELL2;
        fdef.friction = 1f;
        PolygonShape s = new PolygonShape();
        
        for(MapObject mo:layer.getObjects()){
            boolean[] groundArray= new boolean[2];
            float x = (Float)mo.getProperties().get("x");
            float y = (Float)mo.getProperties().get("y");
            float width = (Float)mo.getProperties().get("width");
            float height = (Float)mo.getProperties().get("height");
            bdef.position.set((x+width/2)/PPM,(y+height/2)/PPM);
            s.setAsBox(width/2/PPM, height/2/PPM);
            fdef.shape = s;
            world.createBody(bdef).createFixture(fdef).setUserData(groundArray);
        }
        s.dispose();
        
        int tileSize = (Integer)map.getProperties().get("tilewidth");
        int mapWidth = (Integer) map.getProperties().get("width")*tileSize;
        int mapHeight = (Integer) map.getProperties().get("height")*tileSize;
        gameCam.setBounds(0, mapWidth, 0, mapHeight);
    }
    
    private void createDebugMap(){
        /*BodyDef bdef = new BodyDef();
        bdef.type = BodyType.StaticBody;
        bdef.position.set(400/PPM, 100/PPM);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(200/PPM, 10/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_GROUND;
        fdef.filter.maskBits = BIT_PLAYER;
        fdef.friction = 0.5f;
        world.createBody(bdef).createFixture(fdef);
        
        bdef.position.set(250/PPM,200/PPM);
        shape.setAsBox(50/PPM, 90/PPM);
        fdef.shape = shape;
        world.createBody(bdef).createFixture(fdef);
        
        PolygonShape tri = new PolygonShape();
        bdef.position.set(550/PPM,110/PPM);
        tri.set(new Vector2[]{new Vector2(-50/PPM,0),new Vector2(50/PPM,0),new Vector2(50/PPM,100/PPM)});
        fdef.shape = tri;
        world.createBody(bdef).createFixture(fdef);
        tri.dispose();
        
        shape.dispose();*/
    }

    private void createPlayer(float x, float y) {
        player = new Player(world,1,true);
        player.spawn(x, y);
        
        player2 = new Player(world,2,false);
        player2.spawn(x+200,y);
    }
 
}
