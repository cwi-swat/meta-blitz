package textures.texturedpaths;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import deform.BBox;

import paths.paths.paths.Path;
import textures.examples.AWTRenderedTexture;
import textures.interfaces.ISample;
import transform.Matrix;

public class AWTPaintTexturedPathAffine<Sample extends ISample<Sample>> extends AWTPaintTexturedPath<Sample>{


	final Matrix matrix;
	AffineTransform awtTrans;
	
	
	public AWTPaintTexturedPathAffine(Matrix m, Path path,
			AWTRenderedTexture<Sample> texture) {
		super(path, texture);
		this.matrix = m;
	}
	

	public AffineTransform getAWTAffine(){
		makeAWTAffine();
		return awtTrans;
	}
	
	
	private void makeAWTAffine() {
		if(awtTrans == null){
			awtTrans = new AffineTransform(matrix.x1, matrix.y1, matrix.x2, matrix.y2, matrix.x3, matrix.y3);
		} 
	}

	@Override
	public void render(Graphics2D g, BBox b) {
		AffineTransform t = g.getTransform();
		g.transform(getAWTAffine());
		super.render(g, b);
		g.setTransform(t);
	}
}
