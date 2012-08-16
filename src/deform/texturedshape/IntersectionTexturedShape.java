package deform.texturedshape;

import deform.BBox;
import deform.Transform;
import deform.render.RenderContext;
import deform.shapes.IntersectionShapes;
import deform.shapes.Shape;
import deform.shapes.TransformShape;
import deform.transform.affine.IdentityTransform;
import static deform.Combinators.*;
import static deform.Library.*;

public class IntersectionTexturedShape extends TexturedShape {

	final TexturedShape orig;
	final Shape inter;
	
	public IntersectionTexturedShape(TexturedShape orig, Shape inter) {
		super();
		this.orig = orig;
		this.inter = inter;
	}

	@Override
	public BBox getBBox() {
		return orig.getBBox().
				intersections(this.inter.bbox);
	}

	@Override
	public void render(Transform t, RenderContext ctx) {
		BBox me = t.transformBBox(getBBox());
		if(!me.overlaps(ctx.area)){
			System.out.printf("Not rendering!\n");
			return;
		}
		Shape curClip = ctx.getClip();
		Shape myClip = transform(translate(ctx.size.getLeftUp().negate()),inter);
		if(!(t instanceof IdentityTransform)){
			myClip = new TransformShape(inter,t);
		}
		
		if(curClip != null){
			myClip = new IntersectionShapes(myClip, curClip);
		}
		ctx.setClip(myClip);
		BBox b = ctx.getBBox();
//		System.out.printf("%s %s -> %s\n", myClip.bbox,b, b.intersections(myClip.bbox));
		ctx.setBBox(b.intersections(myClip.bbox));
		orig.render(t, ctx);
		ctx.setClip(curClip);
		ctx.setBBox(b);

	}

}
