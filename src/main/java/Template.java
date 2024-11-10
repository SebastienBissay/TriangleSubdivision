import processing.core.PApplet;

import static parameters.Parameters.SEED;
import static save.SaveUtil.saveSketch;

public class Template extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Template.class);
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
        noLoop();
    }

    @Override
    public void draw() {
        saveSketch(this);
    }
}
