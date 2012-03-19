package bezier.demos;

import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.generated.Colors;
import bezier.image.generated.ColorsAlpha;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.segment.curve.CubicCurve;
import bezier.segment.curve.Curve;
import bezier.segment.curve.Line;

public class ProjectOn extends DemoBase{

	private static final long serialVersionUID = 5191465336253221274L;
	public static void main(String[] args) {
        new ProjectOn();
    }

	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths("o");
		ts = new Paths(ts.get(0));
		ts = ts.transform(Transformation.id.scale(5).rotate(wheel / 100.0 * Math.PI).translate(400,400)).makeMonotomous();
		Paths rect = makeRectangle();
		draw(ts,ColorsAlpha.red);
		for(Path t : ts.paths){
			LengthMap lm = t.getLengthMap();
			Paths rec2 = rect.transform(Transformation.id.scale(lm.totalLength(),0));
			rec2 = rec2.projectOn(t,lm);
			for(Path p : rec2.paths){
				for(Curve c : p.curves){
					CubicCurve cc= (CubicCurve)c;
					drawOval(cc.p1,7);
					drawOval(cc.p2,7);
				}
			}
			draw(rec2);
			
		}

//		CubicCurve c = new CubicCurve(new Vec(0,100),new Vec(100,200), new Vec(300,100), new Vec(450,150) );
//		List<Double> ds = new ArrayList<Double>();
//		for(int i = 0 ; i < 20 ; i++){
//			ds.add((double)i/20.0);
//		}
//		List<CubicCurve> curves = c.splitsAt(ds);
//		Path p = new Path((List)curves);
//		draw(new Path(c.makeMonotomous()),ColorsAlpha.red);
//		
//		draw(p);
		
//		draw(ts);
		
	}
	
	Paths makeRectangle(){
		Vec a, b,c ,d;
		a = new Vec(0,-1); b = new Vec(0,1);
		c = new Vec(1,1); d = new Vec(1,-1);
		List<Path> curves = new ArrayList<Path>();
		curves.add(new Path(new Line(b, c)));
//		curves.add(new Line(b,c));
//		curves.add(new Line(c,d));
//		curves.add(new Line(a,d));
//		curves.add(new Path(new Line(a, d)));
		return new Paths(curves);
	}

	
	
}
