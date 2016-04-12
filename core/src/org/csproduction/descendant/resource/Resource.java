/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.csproduction.descendant.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author chengsong01px2015
 */
public class Resource {
    private final AssetManager manager;
    
    private final HashMap<String, String> texturePaths;
    private final HashMap<String, String> musicPaths;
    private final HashMap<String, String> soundPaths;
    
    private final HashMap<String, Texture> textures;
    private final HashMap<String, Music> musics;
    private final HashMap<String, Sound> sounds;
    
    private TiledMap map;
    
    public Resource(){
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        
        texturePaths = new HashMap<String,String>();
        musicPaths = new HashMap<String,String>();
        soundPaths = new HashMap<String,String>();
        
        textures = new HashMap<String, Texture>();
        musics = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();
        
        map = null;
    }
    
    /**
     * loads all general resources.
     */
    public void loadGeneralResources(){
        loadPlayer();
        loadSpells();
        loadHUD();
    }
    
    /**
     * loads all level-specific resources.
     */
    public void loadLevelResources(){
        loadMusics();
        loadMap();
    }

    private void loadPlayer() {
        loadTexture("sprites/main_character/main_idle_L.png");
        loadTexture("sprites/main_character/main_move_L.png");
    }
    
    private void loadSpells(){
        loadTexture("sprites/spells/fireball/fireball.png");
        loadTexture("sprites/spells/earthspike/earthspike.png");
        loadTexture("sprites/spells/airslash/airslash.png");
        loadTexture("sprites/spells/flamestrike/flamestrike.png");
        loadTexture("sprites/spells/fireblast/fireblast.png");
        loadTexture("sprites/spells/iceshard/iceshard.png");
    }
    
    private void loadHUD(){
        loadTexture("sprites/interface/elements/firerune.png");
    }
    
    private void loadMusics(){
        loadMusic("music/chaosf.mp3");
    }
    
    private void loadMap(){
        manager.load("maps/test_map.tmx", TiledMap.class);
    }
    
    public TiledMap getMap(){
        return map;
    }
    
    /////////////////////TEXTURES/////////////////////
    private void loadTexture(String path){
        int slashIndex = path.lastIndexOf("/");
        int dotIndex = path.lastIndexOf(".");
        String key;
        if(slashIndex == -1){
            key = path.substring(0,dotIndex);
        } else {
            key = path.substring(slashIndex+1,dotIndex);
        }
        loadTexture(path,key);
    }
    
    private void loadTexture(String path, String key){
        manager.load(path, Texture.class);
        texturePaths.put(key, path);
    }
    
    public Texture getTexture(String key){
        return textures.get(key);
    }
    
    /////////////////////MUSIC/////////////////////
    private void loadMusic(String path){
        int slashIndex = path.lastIndexOf("/");
        int dotIndex = path.lastIndexOf(".");
        String key;
        if(slashIndex == -1){
            key = path.substring(0,dotIndex);
        } else {
            key = path.substring(slashIndex+1,dotIndex);
        }
        loadMusic(path,key);
    }
    
    private void loadMusic(String path, String key){
        manager.load(path, Music.class);
        musicPaths.put(key, path);
    }
    
    public Music getMusic(String key){
        return musics.get(key);
    }
    
    /////////////////////SOUNDS/////////////////////
    private void loadSound(String path){
        int slashIndex = path.lastIndexOf("/");
        int dotIndex = path.lastIndexOf(".");
        String key;
        if(slashIndex == -1){
            key = path.substring(0,dotIndex);
        } else {
            key = path.substring(slashIndex+1,dotIndex);
        }
        loadSound(path,key);
    }
    
    private void loadSound(String path, String key){
        manager.load(path, Sound.class);
        soundPaths.put(key,path);
    }
    
    public Sound getSound(String key){
        return sounds.get(key);
    }
    
    
    /////////////////////UPDATE///////////////////////
    /**
     * updates the assets manager and keeps loading assets
     * @return whether loading is done or not
     */
    public boolean update(){
        return manager.update();
    }
    
    /////////////////////FINISH LOADING///////////////////////
    /**
     * THIS METHOD MUST BE CALLED AFTER GENERAL LOADING IS DONE
     * this method takes all the loaded assets into correct HashMaps
     */
    public void finishGeneralLoad(){
        Set<String> keys = texturePaths.keySet();
        for(String key:keys){
            String path = texturePaths.get(key);
            textures.put(key, manager.get(path,Texture.class));
        }
        texturePaths.clear();
        
        keys = musicPaths.keySet();
        for(String key:keys){
            String path = musicPaths.get(key);
            musics.put(key, manager.get(path,Music.class));
        }
        musicPaths.clear();
        
        keys = soundPaths.keySet();
        for(String key:keys){
            String path = soundPaths.get(key);
            sounds.put(key, manager.get(path,Sound.class));
        }
        soundPaths.clear();
    }
    
    /**
     * THIS METHOD MUST BE CALLED AFTER LEVEL LOADING IS DONE
     * this method does finishGeneralLoad(), plus map loading
     */
    public void finishLevelLoad(){
        finishGeneralLoad();
        map = manager.get("maps/test_map.tmx");
    }
    
    /////////////////////REMOVE ALL///////////////////////
    public void disposeAll(){
        for(Texture t:textures.values()){
            t.dispose();
        }
        textures.clear();
        for(Music m:musics.values()){
            m.dispose();
        }
        musics.clear();
        for(Sound s:sounds.values()){
            s.dispose();
        }
        sounds.clear();
        
        map.dispose();
        
        manager.dispose();
    }
}
