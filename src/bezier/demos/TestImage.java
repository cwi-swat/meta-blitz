package bezier.demos;


import static bezier.points.Transformation.id;

import java.awt.Color;
import java.util.List;

import com.sun.istack.internal.Pool.Impl;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.FromShape;
import bezier.image.Image;
import bezier.image.functions.Clamp;
import bezier.image.functions.Constant;
import bezier.image.functions.Div;
import bezier.image.functions.DivSample;
import bezier.image.functions.GiveX;
import bezier.image.functions.ImageTransformation;
import bezier.image.functions.Lerp;
import bezier.image.functions.Lerp2D;
import bezier.image.functions.Mask;
import bezier.image.functions.MulAlpha;
import bezier.image.functions.ProjectOnPath;
import bezier.image.functions.ProjectOnPaths;
import bezier.image.generated.Colors;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.ImageSampleOpers;
import bezier.image.generated.RasterInstances.Raster1;
import bezier.image.generated.RasterInstances.Raster2;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.image.generated.SampleInstances.Sample2;
import bezier.image.generated.SampleInstances.Sample3;
import bezier.image.generated.SampleInstances.Sample4;
import bezier.paths.leaf.QuadCurve;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.segment.curve.Curve;
public class TestImage extends DemoBase {

	private static final long serialVersionUID = 2620494065723135106L;

	public static void main(String[] args) {
        new TestImage();
    }
	
	boolean drag;
	Vec control = new Vec(0,0);

	@Override
	public void handleKeyStroke(char key){
		drag = !drag;
		System.out.printf("%s \n",drag);
	}
	
	@Override
	public void draw() {
		  Paths ts = FontFactory.text2Paths("blabla");
	        double yDiff = ts.bbox.y + ts.bbox.height / 2.0;
	        ts = ts.transform(id.translate(-ts.bbox.x,-yDiff));
	        Vec middle = size.div(2);
			if(!drag){
				control = mouse.sub(middle).mul(2).add(middle);
			}
//	        List<Curve> c = new QuadCurve(new Vec(0,size.y/2), 
//	        								control, 
//	        								new Vec(size.x,size.y/2)).makeMonotomous();
//	        Path p = new Path(c);
	        
//	        LengthMap lm = p.getLengthMap();
//	        ts = ts.transform(id.scale(lm.totalLength()/ts.bbox.width * 0.8));
//	        ts = ts.transform(id.translate(lm.totalLength()*0.1,0));
			ts = ts.transform(id.scale(8,8).translate(100, 400));
//	        ts = ts.projectOn(p, lm);
//	        draw(p);
//	        drawOval(p.getAt(p.project(mouse)),10);
//		  draw(ts2);
		  Image<Sample1> img1 = FromShape.paths2img(ts);
		   Raster1 imgl = ProjectOnPaths.projectDist(img1, ts);
		  Sample1 max = imgl.getMax();
		  max = new Sample1(Math.max(0,Math.min(1.0, wheel / 100) * max.a));
		  Image<Sample1> normimgl = new Clamp<Sample1>(new DivSample<Sample1>(imgl, max));
//		  
		  Image<Sample4> img2 = new MulAlpha(img1, 
				  new Lerp<Sample4>(normimgl,
						  new Constant<Sample4>(ColorsAlpha.transparent), 
						  new Constant<Sample4>(ColorsAlpha.black)));
//		  Image<Sample4> img3 = ImageSampleOpers.append31(img2,img1);
		  draw(img2);
//		  }
		  

	}

}
