package Model;

import com.raylib.Jaylib;
import static com.raylib.Raylib.*;
import static com.raylib.Jaylib.*;

public class Sprite {
    public Texture texture;
    public double x;
    public double y;
    public int width;
    public int height;
    private boolean flipH = false;

    public void draw() {
        int internal_width = width;
        if (flipH) {
            internal_width *= -1;
        }
        DrawTextureRec(texture, new Jaylib.Rectangle(0, 0, internal_width, height), new Jaylib.Vector2((float) x, (float)  y), WHITE);
    }

    public void flipH() {
        flipH = !flipH;
    }
    public void flipH(boolean newVal) {
        flipH = newVal;
    }
}
