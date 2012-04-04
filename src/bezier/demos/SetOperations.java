package bezier.demos;

import static bezier.points.Transformation.id;

import java.util.List;

import bezier.composite.Paths;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.factory.TextFactory;
import bezier.points.Vec;

public class SetOperations extends DemoBase{

	private static final long serialVersionUID = -8697035763451173868L;
	public static void main(String[] args) {
        new SetOperations();
    }
	
	public static boolean dump = false;

	enum SetOperationChoices{
		UNION,
		SUBTRACT_R,
		SUBTRACT_L,
		INTERSECTION
	}
	
	
	SetOperationChoices currentChoice = SetOperationChoices.UNION;
	@Override	
	public void handleKeyStroke(char key){
		if(key == 'n'){
			currentChoice = SetOperationChoices.values()[( currentChoice.ordinal()+ 1) % SetOperationChoices.values().length];
		}
		if(key == 'r'){
			dump = true;
		}
		if(key == 'w'){
			mouse = mouse.add(new Vec(0,-0.1));
		} 
		if(key == 's'){
			mouse = mouse.add(new Vec(0,0.1));
		} 
		if(key == 'a'){
			mouse = mouse.add(new Vec(-0.1,0));
		}
		if(key == 'd'){
			mouse = mouse.add(new Vec(0.1,0));
		}
	}
	
	@Override
	public void draw() {
		  Paths ts = TextFactory.text2Paths("tz");
		  Paths ts2 = TextFactory.text2Paths(lastLine);
		
		  ts2 = ts2.transform(id.translate(25,400).scale(5));
//		  if(dump){
//			  System.out.println(ts2);
//		  }
		  ts = ts.transform(id.scale(5).rotate(wheel / 100.0 * Math.PI));
		  ts = ts.transform(id.translate(mouse.sub(ts.bbox.middle()))).makeMonotomous();
		  ts2 = ts2.makeMonotomous();
//		  if(dump){
//			  System.out.println(ts2);
//			  dump = false;
//		  }
		  Paths res = null;
		  switch(currentChoice){
		  case UNION: res = ts.union(ts2); break;
		  case SUBTRACT_R: res = ts.substract(ts2); break;
		  case SUBTRACT_L: res = ts2.substract(ts); break;
		  case INTERSECTION: res = ts.intersection(ts2); break;
		  }
//		  Shapes sres = new Shapes(res);
//		  System.out.print(res);
		  draw(res,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		  List<Vec> inters2 = ts.getIntersectionPoints(ts2);
		  for(Vec v : inters2){
			  drawOval(v, 10);
		  }
		  if(dump){
			  System.out.println(res);
			  dump = false;
		  }
//		  Tuple<Intersections, Intersections> inters = ts.getIntersections(ts2);
//	      for(int i = 0 ; i < inters.l.intersectionsPerPath.size();i++){
//	    	  List<Double> rs = inters.l.getFor(i);
//	    	  if(rs.isEmpty()) continue;
//	    	  double prev = rs.get(rs.size()-1);
//	    	  for(Double d: inters.l.getFor(i)){
//	    		  Vec v = ts.paths.get(i).getBetween(prev, d);
////	    		  if(i == 1){
////	    			  System.out.printf("Between %f %f %d -> %f\n", prev,d, ts.paths.get(i).curves.size(), ts.paths.get(i).tBetween(prev, d));
////	    		  }
////	    		  System.out.printf("%f\n",d);
//	    		  Color c = ts2.isInside(v) ? Color.GREEN : Color.RED;
//	    		  drawOval(v,8,c,c);
//	    		  prev = d;
//	    	  }
//	    	  System.out.println();
//	      }
//	      System.out.printf("%d\n",inters.size());
	      
		
	}


	
	
}
