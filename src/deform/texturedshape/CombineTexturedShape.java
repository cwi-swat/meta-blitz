package deform.texturedshape;

import paths.points.oned.Interval;
import deform.BBox;
import deform.Color;
import deform.ColorCombine;
import deform.Transform;
import deform.render.RenderContext;
import deform.render.ScanLiner;

public class CombineTexturedShape extends TexturedShape{
	
	final TexturedShape a, b;
	final ColorCombine comb;
	
	public CombineTexturedShape(TexturedShape a, TexturedShape b, ColorCombine comb) {
		this.a = a;
		this.b = b;
		this.comb = comb;
	}
	



	@Override
	public BBox getBBox() {
		return a.getBBox().union(b.getBBox());
	}




	@Override
	public void render(Transform t, RenderContext ctx) {
		BBox me = t.transformBBox(getBBox());
		if(!me.overlaps(ctx.area)){
			return;
		}
		me = me.intersections(ctx.area);
		BBox ab = t.transformBBox(a.getBBox());
		if(!ab.overlaps(ctx.area)){
			b.render(t, ctx);
			return;
		}
		ab = ab.intersections(ctx.area);
		BBox bb = t.transformBBox(b.getBBox());
		if(!bb.overlaps(ctx.area)){
			a.render(t, ctx);
			return;
		}
		bb = bb.intersections(ctx.area);
		RenderContext ctxa = new RenderContext(ab, ctx.getClip());
		a.render(t, ctxa);
		RenderContext ctxb= new RenderContext(bb, ctx.getClip());
		b.render(t, ctxb);
		
		ScanLiner it = new ScanLiner(ctx.size, ctx.size);
		ScanLiner ita = new ScanLiner(ab, ab);
		ScanLiner itb = new ScanLiner(bb, bb);
		while(!it.isDone()){
			Color a = Color.transparent;
			if(ab.isInsideExRightBoder(it.getLoc())){
				a = ctxa.getElem(ita.cur);
				ita.increment();
			}
			Color b = Color.transparent;
			if(bb.isInsideExRightBoder(it.getLoc())){
				b = ctxb.getElem(itb.cur);
				itb.increment();
			}
			if(a.a != 0 || b.b != 0){
				ctx.addElem(it.cur, comb.combine(a, b));
			}

			it.increment();
		}
		
		
	}


}
