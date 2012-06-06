package paths.demo;

import static paths.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.List;

import paths.demo.awt.DemoBase;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.iterators.ConnectedIterator;
import paths.paths.paths.Path;
import paths.paths.paths.simple.curve.QuadCurve;
import paths.points.twod.Vec;


public class PathDeform2 extends DemoBase{

	public static void main(String[] argv){
		new PathDeform2();
	        
	}


	private Path r;
	
	public PathDeform2() {
		r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
	}
	
	@Override
	public void draw() {
        Path ts = TextFactory.text2Paths(lastLine);
        ts = ts.transform(id.scale(7).translate(100,400));
        ConnectedIterator it = new ConnectedIterator(ts);
        double amp = wheel  / 200 ;
        while(it.hasNext()){
        	Path p = it.next();
        	double l = p.length();
        	int nr = (int)Math.round(l / 25);
        	Path q = repeatX(l,nr,sine(amp));
        	draw(q.pathDeform(p));
        	
        }
	}
	
	Path repeatX(double length, int nr, Path p){
		List<Path> res = new ArrayList<Path>();
		p = p.transform(id.translate(-p.getBBox().xInterval.low,0));
		double x = length/nr;
		p = p.transform(id.scale(x/p.getBBox().xInterval.length));
		for(int i = 0 ; i < nr ; i++){
			res.add(p.transform(id.translate(i*x,0)));
		}
		return PathFactory.createAppends(res);
		
	}
	
	Path sine(double amp){
		Path r = bulb(amp);
		Path q = bulb(-amp);
		q = q.transform(id.translate(1.0,0));
		return PathFactory.createAppends(r,q);
	}
	
	Path bulb(double amp){
		Vec a = Vec.ZeroVec;
		Vec b = new Vec(0,1.0/3.0);
		Vec c = new Vec(2.0/6.0, 1);
		Vec d = new Vec(0.5,1);
		Vec e = new Vec(4.0/6.0, 1.0);
		Vec f = new Vec(1.0,1.0/3.0);
		Vec g = new Vec(1.0,0);
		return PathFactory.createAppends(
				PathFactory.createCubic(a, b,c,d),PathFactory.createCubic(d,e,f,g)).transform(id.scale(1.0,amp));
	}
	
	Path spikey(double amp){
		Vec a = Vec.ZeroVec;
		Vec b = new Vec(0.5,amp);
		Vec c = new Vec(1,0);
		return PathFactory.createAppends(PathFactory.createLine(a, b),PathFactory.createLine(b, c));
	}

}
