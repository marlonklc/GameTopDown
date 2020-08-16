package entities;

import main.Main;
import main.World;
import render.Camera;
import render.CollisionObject;
import render.RenderableObject;
import render.WalkingObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends RenderableObject implements WalkingObject {

    private static final BufferedImage sprite = Main.spriteSheet.getSprite(48, 0,16,16);

    public boolean left, right, up, down;

    private enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private Direction direction = Direction.DOWN;
    private int speed = 1;
    private int index = 0, frames = 0;
    private final int maxIndex = 4, maxFrames = 5;
    private boolean isMoving;
    private BufferedImage[] spriteMoveRight;
    private BufferedImage[] spriteMoveLeft;
    private BufferedImage[] spriteMoveUp;
    private BufferedImage[] spriteMoveDown;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height, sprite);

        spriteMoveRight = new BufferedImage[4];
        spriteMoveLeft = new BufferedImage[4];
        spriteMoveUp = new BufferedImage[4];
        spriteMoveDown = new BufferedImage[4];

        for(int i = 0; i < 4; i++) {
            spriteMoveRight[i] = Main.spriteSheet.getSprite(48 + (i*16), 0, 16, 16);
        }
        for(int i = 0; i < 4; i++) {
            spriteMoveLeft[i] = Main.spriteSheet.getSprite(48 + (i*16), 16, 16, 16);
        }
        for(int i = 0; i < 4; i++) {
            spriteMoveUp[i] = Main.spriteSheet.getSprite(48 + (i*16), 32, 16, 16);
        }
        for(int i = 0; i < 4; i++) {
            spriteMoveDown[i] = Main.spriteSheet.getSprite(48 + (i*16), 48, 16, 16);
        }
    }

    public void moveCima() {
        setY(getY() - speed);
        direction = Direction.UP;
        isMoving = true;
    }

    public void moveBaixo() {
        setY(getY() + speed);
        direction = Direction.DOWN;
        isMoving = true;
    }

    public void moveEsquerda() {
        setX(getX() - speed);
        direction = Direction.LEFT;
        isMoving = true;
    }

    public void moveDireita() {
        setX(getX() + speed);
        direction = Direction.RIGHT;
        isMoving = true;
    }

    @Override
    public void move(World currentWorld) {
        isMoving = false;

        if(up && canMove(getX(), getY()-speed, currentWorld)) moveCima();
        if(down && canMove(getX(), getY()+speed, currentWorld)) moveBaixo();
        if(left && canMove(getX()-speed, getY(), currentWorld)) moveEsquerda();
        if(right && canMove(getX()+speed, getY(), currentWorld)) moveDireita();

        if(isMoving) {
            frames++;
            if (frames >= maxFrames) {
                frames = 0;
                index++;
                if (index >= maxIndex)
                    index = 0;
            }
        }
    }

    private boolean canMove(int xnext, int ynext, World currentWorld) {
        int x1 = xnext / currentWorld.TILE_SIZE;
        int y1 = ynext / currentWorld.TILE_SIZE;

        int x2 = (xnext + currentWorld.TILE_SIZE - 1) / currentWorld.TILE_SIZE;
        int y2 = ynext / currentWorld.TILE_SIZE;

        int x3 = xnext / currentWorld.TILE_SIZE;
        int y3 = (ynext + currentWorld.TILE_SIZE - 1) / currentWorld.TILE_SIZE;

        int x4 = (xnext+ currentWorld.TILE_SIZE - 1) / currentWorld.TILE_SIZE;
        int y4 = (ynext+ currentWorld.TILE_SIZE - 1) / currentWorld.TILE_SIZE;

        try {
            return !(currentWorld.getTiles()[x1 + (y1 * currentWorld.getWidth())] instanceof CollisionObject ||
                    currentWorld.getTiles()[x2 + (y2 * currentWorld.getWidth())] instanceof CollisionObject ||
                    currentWorld.getTiles()[x3 + (y3 * currentWorld.getWidth())] instanceof CollisionObject ||
                    currentWorld.getTiles()[x4 + (y4 * currentWorld.getWidth())] instanceof CollisionObject);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void render(Graphics g) {
        switch (direction) {
            case UP:
                g.drawImage(spriteMoveUp[isMoving ? index : 0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case DOWN:
                g.drawImage(spriteMoveDown[isMoving ? index : 0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case RIGHT:
                g.drawImage(spriteMoveRight[isMoving ? index : 0], this.getX() - Camera.x, this.getY() - Camera.y, null);
                break;
            case LEFT:
                g.drawImage(spriteMoveLeft[isMoving ? index : 0], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }

}
