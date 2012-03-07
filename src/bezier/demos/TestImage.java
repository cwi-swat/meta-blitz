package bezier.demos;

import com.sun.corba.se.spi.ior.MakeImmutable;

import bezier.composite.Paths;
import bezier.font.FontFactory;
import bezier.image.RenderPaths;
import bezier.points.Matrix;

public class TestImage extends DemoBase {

	public static void main(String[] args) {
        new TestImage();
    }
	
	@Override
	public void draw() {
		  Paths ts2 = FontFactory.text2Paths(lastLine);
			
		  ts2 = ts2.transform(Matrix.identity.translate(25,400).scale(5)).makeMonotomous();
//		  g.drawImage(RenderPaths.makeImage(ts2,(int)ts2.getBBox().x, (int)ts2.getBBox().y, 500, 500), 0, 0, null);
		  blit(RenderPaths.renderPaths(ts2));
		  

	}

}
