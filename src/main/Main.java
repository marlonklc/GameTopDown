package main;

import entities.Player;
import render.Camera;
import render.RenderableObject;
import render.WalkingObject;
import util.SpriteSheet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    private MainFrame frame;
    private Thread thread;
    private boolean isRunning;

    private final int SCALE = 3;

    public static List<RenderableObject> entities = new ArrayList<>();
    public static SpriteSheet spriteSheet;
    public static Player player;

    private static Camera camera;

    private static Worlds worlds;

    private static World currentWorld;

    public static void next() {
        currentWorld = worlds.next();
        camera = new Camera(currentWorld);
    }

    public static void previous() {
        currentWorld = worlds.previous();
        camera = new Camera(currentWorld);
    }

    private Main() {
        spriteSheet = new SpriteSheet("/SpriteSheet.png");
        addKeyListener(new KeyboardControl());
        setPreferredSize(new Dimension(Camera.WIDTH*SCALE,Camera.HEIGHT*SCALE));
        frame = new MainFrame(this);

        player = new Player(0, 0, 16, 16);
        entities.add(player);
        worlds = new Worlds();
        currentWorld = worlds.next();
        camera = new Camera(currentWorld);
    }

    private synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
        if(isRunning) {
            System.out.println("Está rodando!");
        }
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void move() {
        for(int i = 0; i < entities.size(); i++) {
            RenderableObject renderableObject = entities.get(i);
            if (renderableObject instanceof WalkingObject) {
                WalkingObject walkingObject = (WalkingObject) renderableObject;
                walkingObject.move(currentWorld);
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        BufferedImage image = new BufferedImage(Camera.WIDTH, Camera.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0, Camera.WIDTH, Camera.HEIGHT);

        camera.render(g);

        for(int i = 0; i < entities.size(); i++) {
            RenderableObject renderableObject = entities.get(i);
            renderableObject.render(g);
        }

        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, Camera.WIDTH*SCALE, Camera.HEIGHT*SCALE, null);
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();

        requestFocus();

        while(isRunning) {
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                move();
                camera.move(player.getX(), player.getY());
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer+=1000;
            }

        }
        stop();
    }
}
