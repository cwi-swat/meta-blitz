package bezier.demos;


import static bezier.points.Transformation.id;
import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.CircularGradient;
import bezier.image.GaussianBlur;
import bezier.image.Image;
import bezier.image.RasterImage;
import bezier.image.RenderPaths;
import bezier.image.Sample;
import bezier.image.TransformedImage;
import bezier.image.UseAlphaLeft;
public class TestImage extends DemoBase {

	private static final long serialVersionUID = 2620494065723135106L;

	public static void main(String[] args) {
        new TestImage();
    }
	
	@Override
	public void draw() {
		  Paths ts2 = FontFactory.text2Paths("Atze");
			
		  ts2 = ts2.transform(id.translate(-ts2.bbox.x,-ts2.bbox.y).scale(5).translate(mouse)).makeMonotomous();
//		  g.drawImage(RenderPaths.makeImage(ts2,(int)ts2.getBBox().x, (int)ts2.getBBox().y, 500, 500), 0, 0, null);
		 
//		  if(!lastLine.isEmpty()){
//		  fill(ts2);
//		  System.out.print(id.rotateAroundPoint(new Vec(0.5,0.5), 0.2 * Math.PI).to);
			  RasterImage img = RenderPaths.renderPaths(ts2);
			  Image grd = new UseAlphaLeft(img,
					  new TransformedImage(
							  new CircularGradient(Sample.RED, Sample.GREEN), 
							  id.scale(ts2.getBBox().width, ts2.getBBox().height).
							  translate(-ts2.getBBox().width/2.0, -ts2.getBBox().height/2.0).
//							  rotate(wheel / 100.0 * Math.PI).
							  translate(ts2.getBBox().width/2.0, ts2.getBBox().height/2.0)
							  ));
							 
			  
//			  System.out.print(img);
			  RasterImage img2 =  GaussianBlur.blur(grd, Math.abs(wheel / 250.0));
//			  System.out.print(img2);
			  blit(img2);
//		  }
		  

	}

}
