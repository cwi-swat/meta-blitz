package bezier.demos;

import static bezier.points.Transformation.id;
import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.QuadCurve;
import bezier.points.Vec;
import bezier.segment.LengthMap;

public class TextOnPath extends DemoBase{

	private static final long serialVersionUID = -4592695752080318229L;

	public static void main(String[] args) {
        new TextOnPath();
    }

	@Override
	public void draw() {
        Paths ts = FontFactory.text2Paths(lastLine);
        double yDiff = ts.bbox.y + ts.bbox.height / 2.0;
        ts = ts.transform(id.translate(-ts.bbox.x,-yDiff));
        Vec middle = size.div(2);
        Vec control = mouse.sub(middle).mul(2).add(middle);
        QuadCurve c = new QuadCurve(new Vec(0,size.y/2), 
        								control, 
        								new Vec(size.x,size.y/2));
        Path p = new Path(c);
        LengthMap lm = p.getLengthMap();
        ts = ts.transform(id.scale(lm.totalLength()/ts.bbox.width * 0.8));
        ts = ts.transform(id.translate(lm.totalLength()*0.1,0));
        ts = ts.projectOn(p, lm);
        draw(p);
        draw(ts,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));

	}
	

}
