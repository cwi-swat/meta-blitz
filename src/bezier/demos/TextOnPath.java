package bezier.demos;

import bezier.colors.Color;
import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.segment.curve.QuadCurve;

public class TextOnPath extends DemoBase{

	public static void main(String[] args) {
        new TextOnPath();
    }

	@Override
	public void draw() {
        Paths ts = FontFactory.text2Paths(lastLine);
        double yDiff = ts.bbox.y + ts.bbox.height / 2.0;
        ts = ts.transform(Matrix.identity.translate(-ts.bbox.x,-yDiff));
        Vec middle = size.div(2);
        Vec control = mouse.sub(middle).mul(2).add(middle);
        QuadCurve c = new QuadCurve(new Vec(0,size.y/2), 
        								control, 
        								new Vec(size.x,size.y/2));
        Path p = new Path(c);
        LengthMap lm = p.getLengthMap();
        ts = ts.transform(Matrix.identity.scale(lm.totalLength()/ts.bbox.width * 0.8));
        ts = ts.transform(Matrix.identity.translate(lm.totalLength()*0.1,0));
        ts = ts.projectOn(p, lm);
        draw(p);
        draw(ts, Color.BLACK,Color.GREEN.interpolate(0.5, Color.BLACK));

	}
	

}
