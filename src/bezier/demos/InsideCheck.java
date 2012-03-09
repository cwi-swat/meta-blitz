package bezier.demos;

import bezier.image.Sample;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.points.Matrix;

public class InsideCheck extends DemoBase{

	public static void main(String[] args) {
        new InsideCheck();
    }
	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths(lastLine);
		ts = ts.transform(Matrix.identity.translate(0,300).scale(5).rotate(wheel / 100.0 * Math.PI)).makeMonotomous();
		boolean inside = ts.isInside(mouse);
		Sample c = inside ?  Sample.GREEN : Sample.RED;
		draw(ts,Sample.BLACK,c);
		g.draw(ts.bbox.toAWT());
	}

}
