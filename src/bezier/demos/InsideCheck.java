package bezier.demos;

import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;
import bezier.points.Transformation;

public class InsideCheck extends DemoBase{

	private static final long serialVersionUID = -2921876923822407811L;

	public static void main(String[] args) {
        new InsideCheck();
    }
	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths(lastLine);
		ts = ts.transform(Transformation.id.translate(0,300).scale(5).rotate(wheel / 100.0 * Math.PI)).makeMonotomous();
		boolean inside = ts.isInside(mouse);
		Sample4 c = inside ?  ColorsAlpha.green : ColorsAlpha.red;
		draw(ts,ColorsAlpha.black,c);
		g.draw(ts.bbox.toAWT());
	}

}
