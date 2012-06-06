package paths.demo;

import static paths.transform.AffineTransformation.id;
import paths.demo.awt.DemoBase;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.curve.QuadCurve;
import paths.paths.results.project.BestProject;
import paths.points.twod.Vec;


public class PathDeform extends DemoBase{

	public static void main(String[] argv){
//		double x = 800;
//		double y = 600;
//		Vec size = new Vec(x,y);
//		 Path ts = TextFactory.text2Paths("Atze");
//	        double yDiff = ts.getBBox().yInterval.low + ts.getBBox().yInterval.length / 2.0;
//	        ts = ts.transform(id.translate(-ts.getBBox().xInterval.low,-yDiff));
//	        Vec middle = size.div(2);
//	        Vec mid = new Vec(size.x/2.0,y+50);
//		for(double i =1; i < x ; i+=0.5){
//			for(double j = 1 ; j < y; j+=0.5){
//				System.err.printf("%f %f\n",i,j);
//				Vec control = new Vec(i,j);
//				QuadCurve c = PathFactory.createQuad(new Vec(0,size.y/2), control, new Vec(size.x,size.y/2));
//		        Path q = ts.pathDeform(c);
//			}
//		}
		new PathDeform();
	        
	}


	private Path r;
	
	public PathDeform() {
		r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
	}
	
	@Override
	public void draw() {
        Path ts = TextFactory.text2Paths(lastLine);
        double yDiff = ts.getBBox().yInterval.low + ts.getBBox().yInterval.length / 2.0;
        ts = ts.transform(id.translate(-ts.getBBox().xInterval.low,-yDiff));
        Vec middle = size.div(2);
        Vec control = mouse.sub(middle).mul(1).add(middle);
        control = new Vec(
        		Math.max(Math.min(control.x,size.x-1 ),1),
        		Math.max(Math.min(control.y,size.y-1 ),1));
        double y = size.y/2.0;
        Vec mid = new Vec(size.x/2.0,y+50);
//        Path c = PathFactory.createAppends(PathFactory.createLine(new Vec(0,size.y/2), mid),PathFactory.createLine(mid,new Vec(size.x,size.y/2)));

        QuadCurve c = PathFactory.createQuad(new Vec(0,size.y/2), control, new Vec(size.x,size.y/2));
//        try {
//			Path r = PathFactory.createClosedPath(c,
//					PathFactory.createLine(new Vec(size.x,size.y/2), size)
//			, PathFactory.createLine(size,new Vec(0,size.y)),PathFactory.createLine(new Vec(0,size.y),new Vec(0,size.y/2)));
//	        draw(r);
//		} catch (NotClosedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        ts = ts.transform(id.scale((c.length()/ ts.getBBox().xInterval.length)/1));
        Path q = ts.pathDeform(c);
        fill(q);

//        System.out.println(q);
	}

	
}
