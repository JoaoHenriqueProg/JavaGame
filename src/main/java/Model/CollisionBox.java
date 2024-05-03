package Model;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.DrawLine;
import static com.raylib.Raylib.DrawRectangleLines;

public class CollisionBox {
    public double x;
    public double y;
    public double w;
    public double h;

    public CollisionBox() {
        this(0., 0., 0., 0.);
    }

    public CollisionBox(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean collides_with_collision_box(CollisionBox other_one) {
        Tuple<Double, Double> x_stuff1 = new Tuple<>(x, w);
        Tuple<Double, Double> x_stuff2 = new Tuple<>(other_one.x, other_one.w);

        Tuple<Double, Double> y_stuff1 = new Tuple<>(y, h);
        Tuple<Double, Double> y_stuff2 = new Tuple<>(other_one.y, other_one.h);

        if (y_stuff1.a > y_stuff2.a) {
            Tuple<Double, Double> tmp = y_stuff1;
            y_stuff1 = y_stuff2;
            y_stuff2 = tmp;
        }
        if (x_stuff1.a > x_stuff2.a) {
            Tuple<Double, Double> tmp = x_stuff1;
            x_stuff1 = x_stuff2;
            x_stuff2 = tmp;
        }

        boolean overlaps_y = (y_stuff2.a > y_stuff1.a && y_stuff2.a < y_stuff1.a + y_stuff1.b);
        boolean overlaps_x = (x_stuff2.a > x_stuff1.a && x_stuff2.a < x_stuff1.a + x_stuff1.b);

        return overlaps_x && overlaps_y;
    }

    public void draw() {
        DrawRectangleLines((int) x, (int) y, (int) w, (int) h, WHITE);
        DrawLine((int) x, (int) y, (int) (x + w), (int) (y + h), WHITE);
    }
}
