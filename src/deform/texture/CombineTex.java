package deform.texture;

import deform.BBox;
import deform.Color;
import deform.ColorCombine;
import deform.Texture;
import deform.Vec;

public class CombineTex extends Texture{

	final Texture l, r;
	final ColorCombine comb;
	
	public CombineTex(ColorCombine comb, Texture l, Texture r) {
		this.comb = comb;
		this.l = l;
		this.r = r;
	}

	
	@Override
	public Color sample(Vec point) {
		return comb.combine(l.sample(point), r.sample(point));
	}
	
	public BBox bounds(){
		return l.bounds().union(r.bounds());
	}
	
	
	
}
