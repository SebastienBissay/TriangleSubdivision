import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class TriangleSubdivision extends PApplet {
    public static void main(String[] args) {
        PApplet.main(TriangleSubdivision.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
    }

    @Override
    public void setup() {
        colorMode(COLOR_MODE, 360, 100, 100);
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noLoop();
    }

    @Override
    public void draw() {
        List<Triangle> triangles = new ArrayList<>();
        float x = width / tan(PI / 3);
        float y = sqrt(3) * width / 2;
        triangles.add(new Triangle(new PVector(-x, height),
                new PVector(width + x, height),
                new PVector(width / 2f, -y),
                BASE_HUE, BASE_SAT, BASE_BRI));

        IntStream.range(0, NUMBER_OF_RECURSIONS)
                .forEach(i -> triangles.addAll(divide(triangles.remove(0))));

        triangles.forEach(t -> t.draw(this));

        addGaussianNoise(NOISE_AMOUNT);
        saveSketch(this);
    }

    private List<Triangle> divide(Triangle triangle) {
        List<Triangle> newTriangles = new ArrayList<>();
        if (random(100) < CHANCE_OF_DIVISION) {
            float t = 0.5f + CENTER_VARIANCE * randomGaussian();
            PVector P4 = PVector.add(PVector.mult(triangle.P1(), t), PVector.mult(triangle.P2(), 1 - t));
            t = 0.5f + CENTER_VARIANCE * randomGaussian();
            PVector P5 = PVector.add(PVector.mult(triangle.P1(), t), PVector.mult(triangle.P3(), 1 - t));
            t = 0.5f + CENTER_VARIANCE * randomGaussian();
            PVector P6 = PVector.add(PVector.mult(triangle.P2(), t), PVector.mult(triangle.P3(), 1 - t));

            newTriangles.add(new Triangle(triangle.P1(), P4, P5, triangle.hue() + HUE_VARIANCE * randomGaussian(),
                    constrain(triangle.saturation() + SAT_VARIANCE * randomGaussian(), 0, 100),
                    constrain(triangle.brightness() + BRI_VARIANCE * randomGaussian(), 0, 100)));
            newTriangles.add(new Triangle(triangle.P2(), P4, P6, triangle.hue() + HUE_VARIANCE * randomGaussian(),
                    constrain(triangle.saturation() + SAT_VARIANCE * randomGaussian(), 0, 100),
                    constrain(triangle.brightness() + BRI_VARIANCE * randomGaussian(), 0, 100)));
            newTriangles.add(new Triangle(triangle.P3(), P5, P6, triangle.hue() + HUE_VARIANCE * randomGaussian(),
                    constrain(triangle.saturation() + SAT_VARIANCE * randomGaussian(), 0, 100),
                    constrain(triangle.brightness() + BRI_VARIANCE * randomGaussian(), 0, 100)));
            newTriangles.add(new Triangle(P4, P5, P6, triangle.hue() + HUE_VARIANCE * randomGaussian(),
                    constrain(triangle.saturation() + SAT_VARIANCE * randomGaussian(), 0, 100),
                    constrain(triangle.brightness() + BRI_VARIANCE * randomGaussian(), 0, 100)));

        } else {
            newTriangles.add(triangle);
        }
        return newTriangles;
    }

    private void addGaussianNoise(float value) {
        colorMode(RGB);
        loadPixels();
        for (int i = 0; i < pixels.length; i++) {
            float r = red(pixels[i]);
            float g = green(pixels[i]);
            float b = blue(pixels[i]);

            float n = sqrt(value * 255 / 100) * randomGaussian();
            r += n;
            g += n;
            b += n;
            pixels[i] = color(r, g, b);
        }
        updatePixels();
    }

}
