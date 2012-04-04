package bezier.demos;

import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;
import bezier.paths.Path;
import bezier.paths.compound.CompoundPath;
import bezier.paths.factory.FontFactory;
import bezier.points.Transformation;

public class InsideCheck extends DemoBase{

	private static final long serialVersionUID = -2921876923822407811L;

	public static void main(String[] args) {
        new InsideCheck();
    }
	
	@Override
	public void draw() {
		Path ts = FontFactory.text2Paths(lastLine);
		ts = ts.transform(Transformation.id.rotate(wheel / 100.0 * Math.PI).scale(5).translate(100,300));//.;
		boolean inside = ts.isInside(mouse);
		Sample4 c = inside ?  ColorsAlpha.green : ColorsAlpha.red;
		draw(ts,ColorsAlpha.black,c);
//		g.draw(ts.bbox.toAWT());
	}

}
