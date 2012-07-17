package textures.examples;


import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.util.List;

import paths.points.twod.Vec;

import textures.interfaces.ITexture;
import textures.interfaces.Sample;
import util.BinarySearches;
import util.Tuple;

public class LinearHorizontalGradient implements ITexture{

	final double[] offsets;
	final Sample[] colors;

	
	public LinearHorizontalGradient(List<Tuple<Double,Sample>> gradient) {
		offsets = new double[gradient.size()];
		colors = new Sample[gradient.size()];
		for(int i = 0; i < gradient.size() ; i++){
			offsets[i] = gradient.get(i).l;
			colors[i] = gradient.get(i).r;
		}
	}

	@Override
	public Sample sample(Vec v) {
		int start = BinarySearches.floorBinarySearch(offsets, v.x);
		if(start < 0){
			return colors[0];
		}
		if(start == colors.length -1){
			return colors[colors.length-1];
		}
		double diff = offsets[start + 1] - offsets[start];
		double frac = ( v.x - offsets[start]) / diff;
		return colors[start].lerp(frac, colors[start+1]);
	}
//
//	@Override
//	public Paint makePaint() {
//			float[] fracs = new float[offsets.length];
//			double diff = offsets[offsets.length-1] - offsets[0];
//			for(int i = 0 ; i < offsets.length ; i++){
//				
//				fracs[i] = (float)((offsets[i] - offsets[0])/diff);
//			}
//			java.awt.Color[] awtColors = new java.awt.Color[offsets.length];
//			for(int i = 0 ; i < colors.length ; i++){
//				awtColors[i] = colors[i].toAWT();
//			}
//			return new LinearGradientPaint((float)offsets[0],0.0f,(float)offsets[offsets.length-1], 0.0f,fracs, awtColors);
//	}

}
