package bezier.demos;


import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.RasterImage;
import bezier.image.RenderPaths;
import bezier.points.Matrix;
import static bezier.points.Transformation.id;
public class TestImage extends DemoBase {

	public static void main(String[] args) {
        new TestImage();
    }
	
	@Override
	public void draw() {
		  Paths ts2 = FontFactory.text2Paths("Hallo");
			
		  ts2 = ts2.transform(id.translate(25,400).scale(5)).makeMonotomous();
//		  g.drawImage(RenderPaths.makeImage(ts2,(int)ts2.getBBox().x, (int)ts2.getBBox().y, 500, 500), 0, 0, null);
		 
//		  if(!lastLine.isEmpty()){
			  RasterImage img = RenderPaths.renderPaths(ts2);
			  System.out.print(img);
			  blit(img);
//		  }
		  

	}

}
