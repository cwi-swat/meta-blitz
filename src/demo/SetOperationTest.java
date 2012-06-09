package demo;

import static transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import demo.awt.DemoBase;

import paths.crossing.Crossing;
import paths.crossing.IntersectionsToCrossings;
import paths.paths.factory.TextFactory;
import paths.paths.iterators.ClosedPathIterator;
import paths.paths.iterators.PathIterator;
import paths.paths.iterators.ShapeIterator;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.Append;
import paths.paths.paths.compound.ClosedPath;
import paths.paths.paths.compound.ClosedPathIndex;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.project.BestProject;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.BBox;
import paths.points.twod.Vec;

import textures.old.generated.ColorsAlpha;
import textures.old.generated.SampleInstances.Sample4;

public class SetOperationTest extends DemoBase{
	public static void main(String[] argv){
//		AngularInterval in = AngularIntervalFactory.create180DegreesInterval(new Vec(0,1));
//		System.out.println(in.isInside(new Vec(1,0)));
//		Path<PathIndex>  r = TextFactory.text2Paths("s").transform(id.scale(5).translate(200, 200));
//		Path<PathIndex>  z = TextFactory.text2Paths("s").transform(id.scale(5));
//		for(int x = 000; x < 500 ; x++){
//			for(int y = 0 ; y < 500 ; y++){
//				Path<PathIndex> q = z.transform(id.translate(x,y));
//				IIntersections<PathIndex,PathIndex> ints = r.intersection(q);
//				List<List<Crossing<PathIndex, PathIndex>>> cross = new GroupedIntersections(ints, r,q).getCrossings();
//				int i = 0;
//				for(List<Crossing<PathIndex, PathIndex>> cc : cross){
//					for(Crossing<PathIndex,PathIndex> c : cc){
//						i++;
//					}
//				}
//				if(i % 2 == 1){
//					System.out.printf("%d %d crossings: %s\n", x,y,i);
//				}
//				try{
//					Path p = new SetOperations<PathIndex, PathIndex>(r, q, cross).union();
//				} catch(Error p){
//					System.out.println(p.getMessage());
//				}
//			}
//		}
//		System.out.println("Done!");
		new SetOperationTest();
	}

	Vec location = new Vec(0,0);
	
	Path<ClosedPathIndex> r;
	private Path<ClosedPathIndex> z;
	boolean bla = false;
	public SetOperationTest() {
		r = rectangle().transform(id.scale(200).translate(400,400));
		r = TextFactory.text2Paths("atZe").transform(id.scale(5).translate(200, 200));
		System.out.println("r");
//		r = rectangle().transform(id.scale(30).translate(200, 200));
		z = TextFactory.text2Paths("Jurgen").transform(id.scale(5));
//		z = rectangle().transform(id.scale(200));
	}
	
	public void handleKeyStroke(char key){
		switch(key){
		case 'p' : bla = !bla; break;
		case 'n' : location = new Vec(0,0); break;
		case 'w' : location = location.add(new Vec(0,-1)); break;
		case 's' : location = location.add(new Vec(0,1)); break;
		case 'a' : location = location.add(new Vec(-1,0)); break;
		case 'd' : location = location.add(new Vec(1,0)); break;
		}
	}
	
	public void drawAppendBBoxes(Path z){
		if(z instanceof Append){
			Append q = (Append)z;
			BBox b = z.getBBox();
			drawRect(b.getMiddle(), b.width(), b.height(), ColorsAlpha.green.lerp(0.5, ColorsAlpha.transparent));
			drawAppendBBoxes(q.left);
			drawAppendBBoxes(q.right);
		}
		
	}
	
	@Override
	public void draw() {
		if(!bla){
			location = mouse;
		}
		Path<ClosedPathIndex> q =
		z.transform(
				id.scale(1 + wheel / 100).translate(location));
//		draw(q);
//		draw(r);
//		List<Crossing<ClosedPathIndex, ClosedPathIndex>> cross = r.crossings(q);
//		System.out.println(cross.size());
//		for(Crossing<ClosedPathIndex, ClosedPathIndex> c : cross){
//			fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green.lerp(0.5, ColorsAlpha.transparent) : ColorsAlpha.red.lerp(0.5, ColorsAlpha.transparent) );
//			
//		}
		Path p = null;
		try{
			long start = System.currentTimeMillis();
			p = r.intersectionOp(q);
			System.out.printf("Took %d\n", System.currentTimeMillis() - start);
		} catch(NotClosedException e){

			draw(e.notClosed, ColorsAlpha.red);
			System.out.println("Not closed! " + e.notClosed);
		}
		if(p != null){
			fill(p);
		}
//		System.out.println(wheel);
		if(bla){
//			System.out.println(p.project(mouse).t + " " +  p.project(mouse).distSquared) ;
			drawLine(p.getAt(p.project(mouse).t),mouse);
//			PathIterator<Append> it = new AppendIterator(p);
//			while(it.hasNext()){
//				Append z = it.next();
//				drawAppendBBoxes(z);
//			}
		}
//		Iterator<Path> it = new ClosedPathIterator(p);
//		while(it.hasNext()){
//			Path z = it.next();
//			draw(z);
//			Vec d = 	z.getBBox().getLeftUp().add(new Vec(-10,-10));
//			BestProject<PathIndex> best = z.project(d);
//			Vec v = z.getAt(best.t);
//			List<Vec> vs = z.getTangents(best.t);
//			System.out.println(best.t);
//			drawOval(d,3,ColorsAlpha.blue);
//			drawOval(v,3,ColorsAlpha.blue);
//			if(vs.isEmpty()){
//				System.out.println("Weirrd!!!!!!!!!!!");
//			}
//			for(Vec vv : vs){
//				vv = vv.normalize();
//				vv = vv.mul(40);
//				System.out.print(vv + " ");
//				drawLine(v, v.add(vv), ColorsAlpha.green);
//				drawOval(v.add(vv),3,ColorsAlpha.blue);
//			}
//			drawLine(z.getBBox().getLeftUp(), z.getBBox().getRightUp(), ColorsAlpha.red);
//			drawLine(z.getBBox().getRightUp(), z.getBBox().getRightDown(), ColorsAlpha.red);
//			drawLine(z.getBBox().getRightDown(), z.getBBox().getLeftDown(), ColorsAlpha.red);
//			drawLine(z.getBBox().getLeftDown(), z.getBBox().getLeftUp(), ColorsAlpha.red);
//		}
//		draw(p,bla ? (p.isInside(mouse) ? ColorsAlpha.red : ColorsAlpha.green) : ColorsAlpha.black );
//		while(it.hasNext()){
//			
//			draw(it.next(),c);
//		}
//		System.out.println(p);
//		System.out.println("end");
//		List<Crossing<ClosedPathIndex, ClosedPathIndex>> cross = r.crossings(q);
//		
//			for(Crossing<ClosedPathIndex, ClosedPathIndex> c : cross){
//				
//				fillOval(c.loc, 10, 
//						(c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red).lerp(0.5,ColorsAlpha.transparent));
//			}
//		int i = 0;
//		for(List<Crossing<PathIndex, PathIndex>> cc : cross){
//			for(Crossing<PathIndex,PathIndex> c : cc){
//				fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red);
//				i++;
//			}
//		}
//		Sample4 c;
//		if(i % 2 != 0){
//			c = ColorsAlpha.red;
//			System.err.println(i);
//		} else {
//			c = ColorsAlpha.black;
//		}
//		draw(r,c);
//		draw(q,c);

		
//		for(Crossing<PathIndex, PathIndex> c : cross){
//		
//			fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red);
//		}
//		System.out.println();

	}
}
