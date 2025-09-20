package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;
import nz.ac.wgtn.swen225.lc.renderer.imgs.TileDummy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class dummyTest {

 //Lookup table for the test
 public static Map<String, LoadingImg> tileIdentities = Map.ofEntries(
         Map.entry("Sand", LoadingImg.Sand),
         Map.entry("Rock", LoadingImg.Rock),
         Map.entry("Water", LoadingImg.Water),
         Map.entry("PlayerL", LoadingImg.PlayerLeft),
         Map.entry("PlayerR", LoadingImg.PlayerRight),
         Map.entry("PlayerU", LoadingImg.PlayerUp),
         Map.entry("PlayerD", LoadingImg.PlayerDown),
         Map.entry("orangeKey", LoadingImg.OrangeKey),
         Map.entry("orangeDoor", LoadingImg.OrangeDoor),
         Map.entry("treasure", LoadingImg.Treasure),
         Map.entry("exitLock", LoadingImg.ExitLock),
         Map.entry("exit", LoadingImg.Exit),
         Map.entry("enemyCrab", LoadingImg.enemyCrab)
 );


 /**
  * Testing methods that loads all tiles to show into a list
  */
 public static ArrayList<TileDummy> getTiles(){
     ArrayList<TileDummy> tiles = new ArrayList<>();

     for(int i = 0; i <= 8; i++) {
         for (int j = 0; j <= 8; j++) {
             tiles.add(new TileDummy("Rock", i, j));
         }
     }

     for(int i = 1; i <= 7; i++) {
         for (int j = 1; j <= 7; j++) {
             tiles.add(new TileDummy("Sand", i, j));
         }
     }

     tiles.add(new TileDummy("Water", 6, 7));
     tiles.add(new TileDummy("Water", 7, 7));

     tiles.add(new TileDummy("Water", 4, 5));
     tiles.add(new TileDummy("Water", 4, 4));

     tiles.add(new TileDummy("PlayerU", 4, 4));
     tiles.add(new TileDummy("PlayerD", 2, 4));
     tiles.add(new TileDummy("PlayerR", 6, 4));
     tiles.add(new TileDummy("PlayerL", 7, 4));

     tiles.add(new TileDummy("enemyCrab", 3, 2));

     tiles.add(new TileDummy("exitLock", 4, 7));
     tiles.add(new TileDummy("exit", 4, 8));

     tiles.add(new TileDummy("orangeKey", 6, 2));
     tiles.add(new TileDummy("orangeDoor", 2, 2));
     tiles.add(new TileDummy("treasure", 6, 6));


     return tiles;
 }

 public static void drawTiles(Graphics g, Component c) {
     int COLS = 9;
     int ROWS = 9;
     int SIZE = c.getWidth() / ROWS;

     for (TileDummy tile : getTiles()) {
         int x = tile.x();
         int y = tile.y();
         g.drawImage(dummyTest.tileIdentities.get(tile.name()).loadImage(), x * SIZE, y * SIZE, SIZE, SIZE, null);
     }
 }



}
