package entities;

import main.Main;
import render.Camera;
import render.RenderableObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ammo extends RenderableObject {

    private static BufferedImage sprite = Main.spriteSheet.getSprite(128, 0, 16, 16);

    public Ammo(int x, int y, int width, int height) {
        super(x, y, width, height, Main.spriteSheet.getSprite(128, 0, 16, 16));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
    }
}
