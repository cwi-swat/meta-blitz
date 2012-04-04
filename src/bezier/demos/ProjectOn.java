package bezier.demos;

import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.image.generated.Colors;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.factory.FontFactory;
import bezier.paths.simple.CubicCurve;
import bezier.paths.simple.Line;
import bezier.paths.simple.QuadCurve;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.projectiondeform.LinesCoordinateSystem;
import bezier.segment.LengthMap;
import bezier.segment.curve.Curve;

public class ProjectOn extends DemoBase{

	private static final long serialVersionUID = 5191465336253221274L;
	public static void main(String[] args) {
        new ProjectOn();
    }

	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths("Atze");
//		ts = new Paths(ts.get(0));
		ts = ts.transform(Transformation.id.scale(5).rotate(0 / 100.0 * Math.PI).translate(400,400)).makeMonotomous();
		Path rect = makeRectangle();
//		draw(ts,ColorsAlpha.red);
		List<Path> ps = new ArrayList<Path>();
		for(Path t : ts.paths){

			LinesCoordinateSystem sys = LinesCoordinateSystem.create(t);
			int rep = (int)(sys.totalLength() / 30);
			Path r = rect.transform(Transformation.id.scale( sys.totalLength()/rep, wheel/40 )).makeMonotomous().repeatX(rep);
			 r = sys.deform(r);
//			 System.out.println(r.curves.size());
			 ps.add(r);
			
		}
//		 rect = makeRectangle2();
//		for(Path t : ts.paths){
//
//			LinesCoordinateSystem sys = LinesCoordinateSystem.create(t);
//			Path r = rect.transform(Transformation.id.scale( sys.totalLength(), wheel/40));
//			 r = sys.deform(r);
////			 System.out.println(r.curves.size());
//			 ps.add(r);
//			
//		}
		fill(new Paths(ps));

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
	
	Path makeRectangle(){
		Vec a, b,c ,d,e,f;
		e = new Vec(0.25,2);
		a = new Vec(0,0); b = new Vec(0.25,1);
		c = new Vec(0.5,0); d = new Vec(0.75,-1);
		e = new Vec(1,0);
		f = new Vec(0.5,2);
		List<Curve> curves = new ArrayList<Curve>();
//		curves.add(new Line(a,c));
//		curves.add(new Line(c,e));
//		curves.add(new QuadCurve(a,b, c));
//		curves.add(new QuadCurve(c, d, e));
//		curves.add(new Line(new Vec(0,1),new Vec(1,1)));
		curves.add(new Line(a,f));
		curves.add(new Line(f,e));
//		curves.add(new Line(a,d));
//		curves.add(new Line(c,d));
//		curves.add(new Line(a,d));
//		curves.add(new Path(new Line(a, d)));
		return new Path(curves);
	}
	
//	Path makeRectangle2(){
////		Vec a, b,c ,d;
//		
//		a = new Vec(0,-1); b = new Vec(0,1);
//		c = new Vec(1,1); d = new Vec(1,-1);
//		List<Curve> curves = new ArrayList<Curve>();
////		curves.add(new Line(b, c));
//		curves.add(new Line(a,d));
////		curves.add(new Line(c,d));
////		curves.add(new Line(a,d));
////		curves.add(new Path(new Line(a, d)));
//		return new Path(curves);
//	}

	
	
}
