package Model;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Game {
    public static void run() {
        int INTERNAL_WIDTH = 1280 / 2;
        int INTERNAL_HEIGHT = 720 / 2;

        CollisionBox[] platforms = new CollisionBox[2];
        platforms[0] = new CollisionBox(10, 270, INTERNAL_WIDTH - 20, 170);
        platforms[1] = new CollisionBox(600, 64, 50, INTERNAL_HEIGHT);

        double gravity = 5000;

        SetTargetFPS(60);
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow(1280, 720, "Procima obra do picaço");
        Player player = new Player();
        RenderTexture rt = LoadRenderTexture(INTERNAL_WIDTH, INTERNAL_HEIGHT);
        RenderTexture YFlipped = LoadRenderTexture(INTERNAL_WIDTH, INTERNAL_HEIGHT);

        while (!WindowShouldClose()) {
            // can't use ray-lib's actual get frame time cause it bugs out when resizing screen
            double delta = 1./60.;

            BeginTextureMode(rt);
            ClearBackground(BLACK);

            player.update(delta, gravity);

            platforms[1].x = GetMouseX() * 0.5;
            platforms[1].y = GetMouseY() * 0.5;
            platforms[1].w = 32;
            platforms[1].h = 32;
            for (int i = 0; i < platforms.length; i++) {
                boolean collides_with_platform = player.collision.collides_with_collision_box(platforms[i]);

                if (collides_with_platform) {
                    player.y = platforms[i].y - 64;
                    player.y_accel = 0;
                    player.y_vel = 0;
                }
            }

            player.handle_input(delta);
            player.draw();

            player.collision.draw();
            for (int i = 0; i < platforms.length; i++) {
                platforms[i].draw();
            }
            EndTextureMode();

            // FLIP RENDER TEXTURE IMAGE
            BeginTextureMode(YFlipped);
            DrawTextureRec(rt.texture(), new Jaylib.Rectangle(0, 0, INTERNAL_WIDTH, INTERNAL_HEIGHT), new Jaylib.Vector2(0, 0), WHITE);
            EndTextureMode();

            // RENDER TEXTURE INTO SCREEN
            BeginDrawing();
            Jaylib.Rectangle imgrec = new Jaylib.Rectangle(0, 0, INTERNAL_WIDTH, INTERNAL_HEIGHT);
            Jaylib.Rectangle destrec = new Jaylib.Rectangle(0, 0, GetScreenWidth(), GetScreenHeight());
            Jaylib.Vector2 origin = new Jaylib.Vector2(0, 0);
            DrawTexturePro(YFlipped.texture(), imgrec, destrec, origin, 0, WHITE);

            DrawText("Fps: " + GetFPS(), 10, 10, 25, WHITE);
            EndDrawing();
        }
    }
}
