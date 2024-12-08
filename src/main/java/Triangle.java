import processing.core.PApplet;
import processing.core.PVector;

public record Triangle(PVector P1, PVector P2, PVector P3, float hue,
                       float saturation, float brightness) {

    public void draw(PApplet pApplet) {
        pApplet.fill(hue, saturation, brightness);
        pApplet.stroke(hue, saturation, brightness);
        pApplet.triangle(P1.x, P1.y, P2.x, P2.y, P3.x, P3.y);
    }
}
