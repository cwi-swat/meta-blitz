package deform.texturedshape;

import deform.BBox;
import deform.Combinators;
import deform.Library;
import static deform.Library.*;
import deform.Transform;
import deform.render.RenderContext;
import deform.shapes.IntersectionShapes;
import deform.shapes.Shape;
import deform.shapes.TransformShape;
import deform.transform.affine.IdentityTransform;

public class MinusTexturedShape extends TexturedShape{
	

	final TexturedShape orig;
	final Shape min;
	

	public MinusTexturedShape(TexturedShape orig, Shape min) {
		super();
		this.orig = orig;
		this.min = min;
	}

	@Override
	public BBox getBBox() {
		return orig.getBBox();
	}

	@Override
	public void render(Transform t, RenderContext ctx) {
		BBox me = t.transformBBox(getBBox());
		if(!me.overlaps(ctx.area)){
			return;
		}
		Shape curClip = ctx.getClip();
		Shape outside = Combinators.transform(
				scale(ctx.area.width(),ctx.area.height()).compose(translate(ctx.area.getLeftUp())),rectangle());
		Shape myClip = min;
		if(!(t instanceof IdentityTransform)){
			myClip = new TransformShape(min,t);
		}
		myClip = Combinators.set(outside,myClip);
		if(curClip != null){
			myClip = new IntersectionShapes(myClip, curClip);
		}
		
		ctx.setClip(myClip);
//		System.out.printf("%s %s -> %s\n", myClip.bbox,b, b.intersections(myClip.bbox));
//		ctx.setBBox(b.intersections(myClip.bbox));
		orig.render(t, ctx);
		ctx.setClip(curClip);

	}

}
