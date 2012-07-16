package demo;

import static transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.List;

import deform.Vec;

import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import transform.nonlinear.IDeform;
import transform.nonlinear.ffd.CubicFFD;


public class FFDTest extends DemoBase{

	public static void main(String[] argv){
		List<List<Vec>> v = new ArrayList<List<Vec>>();
		List<Vec> vv = new ArrayList<Vec>();
		vv.add(new Vec(0,0));
		vv.add(new Vec(1,0));
		vv.add(new Vec(2,0));
		vv.add(new Vec(3,0));
		v.add(vv);
		vv = new ArrayList<Vec>();
		vv.add(new Vec(0,1));
		vv.add(new Vec(1,1));
		vv.add(new Vec(2,1));
		vv.add(new Vec(3,1));
		v.add(vv);
		vv = new ArrayList<Vec>();
		vv.add(new Vec(0,2));
		vv.add(new Vec(1,2));
		vv.add(new Vec(2,2));
		vv.add(new Vec(3,2));
		v.add(vv);
		vv.add(new Vec(0,3));
		vv.add(new Vec(1,3));
		vv.add(new Vec(2,3));
		vv.add(new Vec(3,3));
		v.add(vv);
		List<Double> gridX = new ArrayList<Double>();
		gridX.add(0.); gridX.add(1.0); gridX.add(2.); gridX.add(3.);
		
		CubicFFD ffd = new CubicFFD(gridX, new ArrayList<Double>(gridX), v);
		for(double d = 0 ; d <= 3 ; d+=0.25){
			for(double e = 0 ; e <= 3 ; e+=0.25){
				System.out.printf("from %s -> %s\n", new Vec(d,e), ffd.to(new Vec(d,e)));
			}
		}
//		System.out.println((new Vec(1.0,0.75),v ));
		new FFDTest();
	        
	}


	private Path r;
	
	public FFDTest() {
		
		r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
	}
	
	@Override
	public void draw() {
        Path ts = TextFactory.text2Paths("Atze!");
        ts = ts.transform(id.scale(7).translate(100,400));
        double rad = Math.sqrt(2) * 50 + wheel / 20;
        double rad3 = rad / 3.0;
        double dia = rad / Math.sqrt(2);
        double dia3 = dia / 3.0;
//        IDeform d= Fisheye.createFishEye(mouse, 200, 1.0 + wheel / 200);
        IDeform d = CubicFFD.makeFFD(90, 
        		new Double[] {mouse.x - dia, mouse.x + dia},
        		new Double[] {mouse.y - dia, mouse.y + dia},
        		new Vec[][]{ new Vec[] { 
        				new Vec(mouse.x - dia, mouse.y - dia), 
						new Vec(mouse.x -dia3, mouse.y - rad3),
						new Vec(mouse.x + dia3, mouse.y - rad3),
						new Vec(mouse.x + dia, mouse.y - dia)},
						new Vec[] { 
        				new Vec(mouse.x - dia, mouse.y - dia3), 
						new Vec(mouse.x -dia3, mouse.y - dia3),
						new Vec(mouse.x + dia3, mouse.y - dia3),
						new Vec(mouse.x + dia, mouse.y - dia3)},
						new Vec[] { 
        				new Vec(mouse.x - dia, mouse.y + dia3), 
						new Vec(mouse.x -dia3, mouse.y + dia3),
						new Vec(mouse.x + dia3, mouse.y + dia3),
						new Vec(mouse.x + dia, mouse.y + dia3)},
						new Vec[] { 
        				new Vec(mouse.x - dia, mouse.y + dia), 
						new Vec(mouse.x -dia3, mouse.y + rad3),
						new Vec(mouse.x + dia3, mouse.y + rad3),
						new Vec(mouse.x + dia, mouse.y + dia)}});				
//										
//        								
       
        fill(ts.deform(d));
	}
	
	
}
