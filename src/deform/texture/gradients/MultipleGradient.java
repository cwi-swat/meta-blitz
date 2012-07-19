package deform.texture.gradients;

import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.geom.Point2D;

import util.BinarySearches;

import deform.Color;
import deform.Library;
import deform.Texture;
import deform.Vec;
import deform.texture.AffineTransformableTex;
import deform.texture.ConvertColor;
import deform.texture.Java2DTexture;
import deform.transform.affine.AffineTransform;

public abstract class MultipleGradient implements Texture, Java2DTexture, AffineTransformableTex {

	final double[] fractions;
	final double[] lengths;
	final Color[] colors;
	final AffineTransform aff;
	final double avrg;
	

	MultipleGradient(double[] fractions, Color[] colors, AffineTransform aff) {
		this.fractions = fractions;
		this.colors = colors;
		this.aff = aff;
		this.lengths = makeLengths(fractions);
		this.avrg = 1.0/ (double)(fractions.length-1);
	}


	MultipleGradient(Library.ColorAndFraction[] cs, AffineTransform aff){
		int sub = (cs[0].fraction == 0 ? 0 : 1);
		int extra = sub + (cs[cs.length-1].fraction == 1 ? 0 : 1);
		fractions = new double[cs.length + extra];
		colors = new Color[cs.length + extra];
		int i = 0 ;
		if(cs[0].fraction != 0){
			fractions[0] = 0;
			colors[0] = cs[0].color;
			i++;
		}
		for(i = 0 ; i < cs.length ; i++){
			fractions[i+ sub] = cs[i].fraction;
			colors[i + sub] = cs[i].color;
		}
		if(cs[cs.length-1].fraction != 1.0){
			fractions[cs.length] = 1.0;
			colors[cs.length] = cs[cs.length-1].color;
		}
		this.aff = aff;	
		this.lengths = makeLengths(fractions);
		this.avrg = 1.0/ (double)(fractions.length-1);
	}
	
	static double[] makeLengths(double[] fracs){
		double[] res = new double[fracs.length];
		for(int i = 0 ; i < fracs.length - 1; i++){
			res[i] = fracs[i + 1] - fracs[i];
		}
		return res;
	}

	
	Color getColor(double frac){
		int start;
		for( start = 0 ; start < fractions.length -1 && fractions[start] <= frac ; start++){
			
		}
		start--;
		double l = (frac - fractions[start]) / lengths[start];
		return colors[start].lerp(l, colors[start+1]);
	}

	

	abstract MultipleGradientPaint.CycleMethod getCycleMethod();

}
