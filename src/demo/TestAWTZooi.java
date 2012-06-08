package demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import com.sun.corba.se.spi.ior.Writeable;

import paths.paths.factory.CircleFactory;
import paths.points.twod.Vec;
import textures.RenderPath;
import transform.AffineTransformation;

public class TestAWTZooi {

	public static void main(String[] argv){
		BufferedImage bi = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D)bi.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
	                RenderingHints.VALUE_ANTIALIAS_ON);
	   	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(new Color(ICC_ColorSpace.getInstance(ICC_ColorSpace.CS_GRAY), new float[]{1.0f}, 1.0f));
		g.fill(new DummyAWTSHape(CircleFactory.makeCircle(new Vec(300,100), 100)));
		g.dispose();
		WritableRaster ras = bi.getRaster();
		DataBuffer buf = ras.getDataBuffer();
//		for(int i = 0 ; i < 400 ; i++){
//			for(int j = 0; j < 400; j++){
//				int id = i * (400*3) + j * 3;
//				System.out.printf("%d %d %d %d %d \n",i,j,
//						buf.getElem(id ),
//						buf.getElem(id + 1),
//						buf.getElem(id + 2));
//			}
//		}
//		
//		System.out.println(buf.getDataType() == buf.TYPE_BYTE);
//		System.out.println("Offsets");
//		for(int i : buf.getOffsets()){
//			System.out.println(i);
//		}
//		System.out.println("Banks");
//		System.out.println(buf.getNumBanks());
//
//		System.out.printf("size %d\n", buf.getSize());
		System.out.println(buf instanceof DataBufferByte);
		
		
		
		
	}
	
	
}
