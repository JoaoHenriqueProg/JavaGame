package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Player {
    public double x;
    public double y;
    public double y_accel = 0;
    public double y_vel = 0;

    public Sprite sprite = new Sprite();
    public CollisionBox collision = new CollisionBox();

    public Player() {
        File fi = new File("src/main/stuff/vampire.png");
        try {
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            sprite.texture = LoadTextureFromImage(LoadImageFromMemory(".png", fileContent, fileContent.length));
            sprite.width = 32;
            sprite.height = 64;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(double delta, double gravity) {
        y_accel += gravity * delta;
        if (y_vel > 0) {
            y_accel *= 1.01;
        }
        y_vel += y_accel * delta;
        y_vel = Double.min(y_vel, 64 * 60);
        y += y_vel * delta;

        updateChildren();
    }

    public void handle_input(double delta) {
        if (IsKeyPressed(KEY_SPACE)) {
            y_vel = -700;
        }
        if (IsKeyDown(KEY_A)) {
            sprite.flipH(true);
            x -= 200 * delta;
        }
        if (IsKeyDown(KEY_D)) {
            sprite.flipH(false);
            x += 200 * delta;
        }

        updateChildren();
    }

    public void draw() {
        sprite.draw();
    }

    public void updateChildren() {
        sprite.x = x;
        sprite.y = y;
        collision.x = x;
        collision.y = y;
        collision.w = 32;
        collision.h = 64;
    }

    public String getDebugData() {
        return "Player: \nX: " + x + "\nY: " + y;
    }
}
