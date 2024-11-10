import processing.core.PApplet;

import static parameters.Parameters.SEED;
import static save.SaveUtil.saveSketch;

public class Arcs extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Arcs.class);
    }

    @Override
    public void settings() {
        size(1000, 1000);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(255);
        stroke(0, 25, 75, 5.1f);
        noFill();
        blendMode(MULTIPLY);
        noLoop();
    }

    @Override
    public void draw() {

        for (float r = 1; r < 0.9f * min(width, height); r += 0.02) {
            float startAngle = 2 * TWO_PI * sq(noise(r / 100.f));
            float endAngle = startAngle + floor(random(1, 13)) * PI / 8;
            arc(width / 2.f, height / 2.f, r, r, startAngle, endAngle);
        }

        saveSketch(this);
    }
}
