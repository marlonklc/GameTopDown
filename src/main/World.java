package main;

import entities.Ammo;
import entities.Enemy;
import entities.Life;
import render.RenderableObject;
import tile.TileFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    private int width, height;
    public static final int TILE_SIZE = 16;
    private TileFactory tileFactory = TileFactory.of();
    private RenderableObject[] tiles = null;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(World.class.getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            width = map.getWidth();
            height = map.getHeight();
            tiles = new RenderableObject[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            for(int xx = 0; xx < map.getWidth(); xx++) {
                for(int yy = 0; yy < map.getHeight(); yy++) {
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];

                    tiles[xx + (yy * width)] = tileFactory.getByColor(pixelAtual).create(xx * TILE_SIZE, yy * TILE_SIZE);

                    //Player
                    if(pixelAtual == 0xFF1407FF) {
                        Main.player.setX(xx*16);
                        Main.player.setY(yy*16);
                    }
                    //Municao
                    else if(pixelAtual == 0xFF4800FF) {
                        Main.entities.add(new Ammo(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    }
                    //Vida
                    else if(pixelAtual == 0xFFFF8800) {
                        Main.entities.add(new Life(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    }
                    //inimigo
                    else if(pixelAtual == 0xFFFF7F7F) {
                        Main.entities.add(new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RenderableObject[] getTiles() {
        return tiles;
    }
}
