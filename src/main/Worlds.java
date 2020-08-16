package main;

public class Worlds {

    private World[] worlds;
    private int index = -1;

    public Worlds() {
        worlds = new World[2];
        worlds[0] = new World("/TileMap.png");
        worlds[1] = new World("/TileMap2.png");
    }

    public World next() {
        if (worlds.length == 0) return null;
        if (index + 1 >= worlds.length) return current();
        return worlds[++index];
    }

    public World previous() {
        if (worlds.length == 0) return null;
        if (index - 1 < 0) return current();
        return worlds[--index];
    }

    private World current() {
        return worlds[index];
    }
}
