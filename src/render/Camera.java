package render;

import main.Main;
import main.World;

import java.awt.Graphics;

public class Camera {
    public static int x;
    public static int y;

    public static final int WIDTH = 160;
    public static final int HEIGHT = 160;

    private World currentWorld;

    public Camera(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public void move(int playerX, int playerY) {
        int x = playerX - (WIDTH / 2);
        int limitCameraX = currentWorld.getWidth() * currentWorld.TILE_SIZE - WIDTH;
        if (x > 0 && x < limitCameraX) {
            Camera.x = x;
        }

        int y = playerY - (HEIGHT / 2);
        int limitCameraY = currentWorld.getHeight() * currentWorld.TILE_SIZE - WIDTH;
        if (y > 0 && y < limitCameraY) {
            Camera.y = y;
        }
    }

    public void render(Graphics g) {
        int xStart = Camera.x>>4;
        int yStart = Camera.y>>4;

        int xFinal = xStart + (WIDTH >> 4);
        int yFinal = yStart + (HEIGHT >> 4);

        for(int xx = xStart; xx <= xFinal; xx++) {
            for(int yy = yStart; yy <= yFinal; yy++) {
                if(xx < 0 || yy < 0 || xx >= currentWorld.getWidth() || yy >= currentWorld.getHeight())
                    continue;
                RenderableObject renderableObject = currentWorld.getTiles()[xx + (yy * currentWorld.getWidth())];
                renderableObject.render(g);
            }
        }
    }
}
