package textures.examples;

import java.awt.Paint;

import textures.interfaces.ISample;

public abstract class AWTRenderedTexture<Sample extends ISample<Sample>> extends Texture<Sample>{

	Paint memoPaint;
	
	
	
	abstract Paint makePaint();

	public Paint getPaint(){
		if(memoPaint == null){
			memoPaint = makePaint();
		}
		return memoPaint;
	}
	
	
}
