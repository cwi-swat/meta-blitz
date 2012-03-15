package bezier.image.generated;
import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.SampleInstances.*;
public class ImageSampleOpers{
			
		
			




			
		
			
			public static Image<Sample2> append11(final Image<Sample1> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample2>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample2 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample1> del2_2(final Image<Sample2> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample1> del2_1(final Image<Sample2> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
		
			
			public static Image<Sample3> append12(final Image<Sample1> l, final Image<Sample2> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample3>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample3 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample3> append21(final Image<Sample2> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample3>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample3 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample2> del3_3(final Image<Sample3> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del3();
					}
				};
			}
			



		
			public static Image<Sample1> del3_12(final Image<Sample3> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del12();
					}
				};
			}
			



		
			public static Image<Sample1> del3_13(final Image<Sample3> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del13();
					}
				};
			}
			



		
			public static Image<Sample2> del3_2(final Image<Sample3> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample1> del3_23(final Image<Sample3> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del23();
					}
				};
			}
			



		
			public static Image<Sample2> del3_1(final Image<Sample3> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
		
			
			public static Image<Sample4> append13(final Image<Sample1> l, final Image<Sample3> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample4>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample4 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample4> append22(final Image<Sample2> l, final Image<Sample2> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample4>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample4 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample4> append31(final Image<Sample3> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample4>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample4 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample2> del4_34(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del34();
					}
				};
			}
			



		
			public static Image<Sample1> del4_124(final Image<Sample4> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del124();
					}
				};
			}
			



		
			public static Image<Sample1> del4_134(final Image<Sample4> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del134();
					}
				};
			}
			



		
			public static Image<Sample2> del4_24(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del24();
					}
				};
			}
			



		
			public static Image<Sample1> del4_234(final Image<Sample4> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del234();
					}
				};
			}
			



		
			public static Image<Sample2> del4_14(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del14();
					}
				};
			}
			



		
			public static Image<Sample3> del4_4(final Image<Sample4> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del4();
					}
				};
			}
			



		
			public static Image<Sample3> del4_3(final Image<Sample4> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del3();
					}
				};
			}
			



		
			public static Image<Sample2> del4_12(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12();
					}
				};
			}
			



		
			public static Image<Sample2> del4_13(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del13();
					}
				};
			}
			



		
			public static Image<Sample3> del4_2(final Image<Sample4> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample2> del4_23(final Image<Sample4> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del23();
					}
				};
			}
			



		
			public static Image<Sample3> del4_1(final Image<Sample4> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
			public static Image<Sample1> del4_123(final Image<Sample4> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del123();
					}
				};
			}
			



		
		
			
			public static Image<Sample5> append14(final Image<Sample1> l, final Image<Sample4> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample5>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample5 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample5> append23(final Image<Sample2> l, final Image<Sample3> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample5>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample5 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample5> append32(final Image<Sample3> l, final Image<Sample2> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample5>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample5 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample5> append41(final Image<Sample4> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample5>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample5 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample2> del5_135(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del135();
					}
				};
			}
			



		
			public static Image<Sample3> del5_25(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del25();
					}
				};
			}
			



		
			public static Image<Sample3> del5_34(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del34();
					}
				};
			}
			



		
			public static Image<Sample2> del5_124(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del124();
					}
				};
			}
			



		
			public static Image<Sample3> del5_35(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del35();
					}
				};
			}
			



		
			public static Image<Sample2> del5_125(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del125();
					}
				};
			}
			



		
			public static Image<Sample2> del5_134(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del134();
					}
				};
			}
			



		
			public static Image<Sample3> del5_24(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del24();
					}
				};
			}
			



		
			public static Image<Sample1> del5_1235(final Image<Sample5> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del1235();
					}
				};
			}
			



		
			public static Image<Sample4> del5_5(final Image<Sample5> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del5();
					}
				};
			}
			



		
			public static Image<Sample2> del5_234(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del234();
					}
				};
			}
			



		
			public static Image<Sample3> del5_14(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del14();
					}
				};
			}
			



		
			public static Image<Sample2> del5_235(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del235();
					}
				};
			}
			



		
			public static Image<Sample3> del5_15(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del15();
					}
				};
			}
			



		
			public static Image<Sample1> del5_1234(final Image<Sample5> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del1234();
					}
				};
			}
			



		
			public static Image<Sample4> del5_4(final Image<Sample5> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del4();
					}
				};
			}
			



		
			public static Image<Sample1> del5_1345(final Image<Sample5> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del1345();
					}
				};
			}
			



		
			public static Image<Sample2> del5_245(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del245();
					}
				};
			}
			



		
			public static Image<Sample4> del5_3(final Image<Sample5> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del3();
					}
				};
			}
			



		
			public static Image<Sample3> del5_12(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del12();
					}
				};
			}
			



		
			public static Image<Sample2> del5_345(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del345();
					}
				};
			}
			



		
			public static Image<Sample1> del5_1245(final Image<Sample5> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del1245();
					}
				};
			}
			



		
			public static Image<Sample3> del5_13(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del13();
					}
				};
			}
			



		
			public static Image<Sample4> del5_2(final Image<Sample5> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample3> del5_45(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del45();
					}
				};
			}
			



		
			public static Image<Sample3> del5_23(final Image<Sample5> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del23();
					}
				};
			}
			



		
			public static Image<Sample4> del5_1(final Image<Sample5> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
			public static Image<Sample1> del5_2345(final Image<Sample5> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del2345();
					}
				};
			}
			



		
			public static Image<Sample2> del5_145(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del145();
					}
				};
			}
			



		
			public static Image<Sample2> del5_123(final Image<Sample5> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del123();
					}
				};
			}
			



		
		
			
			public static Image<Sample6> append15(final Image<Sample1> l, final Image<Sample5> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample6>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample6 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample6> append24(final Image<Sample2> l, final Image<Sample4> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample6>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample6 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample6> append33(final Image<Sample3> l, final Image<Sample3> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample6>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample6 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample6> append42(final Image<Sample4> l, final Image<Sample2> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample6>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample6 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample6> append51(final Image<Sample5> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample6>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample6 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample3> del6_456(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del456();
					}
				};
			}
			



		
			public static Image<Sample3> del6_236(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del236();
					}
				};
			}
			



		
			public static Image<Sample4> del6_16(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del16();
					}
				};
			}
			



		
			public static Image<Sample3> del6_135(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del135();
					}
				};
			}
			



		
			public static Image<Sample4> del6_25(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del25();
					}
				};
			}
			



		
			public static Image<Sample4> del6_34(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del34();
					}
				};
			}
			



		
			public static Image<Sample3> del6_124(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del124();
					}
				};
			}
			



		
			public static Image<Sample1> del6_23456(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del23456();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1456(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1456();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1236(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1236();
					}
				};
			}
			



		
			public static Image<Sample5> del6_6(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del6();
					}
				};
			}
			



		
			public static Image<Sample4> del6_35(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del35();
					}
				};
			}
			



		
			public static Image<Sample3> del6_125(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del125();
					}
				};
			}
			



		
			public static Image<Sample3> del6_134(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del134();
					}
				};
			}
			



		
			public static Image<Sample4> del6_24(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del24();
					}
				};
			}
			



		
			public static Image<Sample1> del6_13456(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del13456();
					}
				};
			}
			



		
			public static Image<Sample2> del6_2456(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del2456();
					}
				};
			}
			



		
			public static Image<Sample4> del6_36(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del36();
					}
				};
			}
			



		
			public static Image<Sample3> del6_126(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del126();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1235(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1235();
					}
				};
			}
			



		
			public static Image<Sample5> del6_5(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del5();
					}
				};
			}
			



		
			public static Image<Sample3> del6_234(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del234();
					}
				};
			}
			



		
			public static Image<Sample4> del6_14(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del14();
					}
				};
			}
			



		
			public static Image<Sample2> del6_3456(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del3456();
					}
				};
			}
			



		
			public static Image<Sample1> del6_12456(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del12456();
					}
				};
			}
			



		
			public static Image<Sample3> del6_136(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del136();
					}
				};
			}
			



		
			public static Image<Sample4> del6_26(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del26();
					}
				};
			}
			



		
			public static Image<Sample3> del6_235(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del235();
					}
				};
			}
			



		
			public static Image<Sample4> del6_15(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del15();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1234(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1234();
					}
				};
			}
			



		
			public static Image<Sample5> del6_4(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del4();
					}
				};
			}
			



		
			public static Image<Sample1> del6_12356(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del12356();
					}
				};
			}
			



		
			public static Image<Sample4> del6_56(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del56();
					}
				};
			}
			



		
			public static Image<Sample2> del6_2346(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del2346();
					}
				};
			}
			



		
			public static Image<Sample3> del6_146(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del146();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1345(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1345();
					}
				};
			}
			



		
			public static Image<Sample3> del6_245(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del245();
					}
				};
			}
			



		
			public static Image<Sample5> del6_3(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del3();
					}
				};
			}
			



		
			public static Image<Sample4> del6_12(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del12();
					}
				};
			}
			



		
			public static Image<Sample2> del6_2356(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del2356();
					}
				};
			}
			



		
			public static Image<Sample3> del6_156(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del156();
					}
				};
			}
			



		
			public static Image<Sample1> del6_12346(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del12346();
					}
				};
			}
			



		
			public static Image<Sample4> del6_46(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del46();
					}
				};
			}
			



		
			public static Image<Sample3> del6_345(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del345();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1245(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1245();
					}
				};
			}
			



		
			public static Image<Sample4> del6_13(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del13();
					}
				};
			}
			



		
			public static Image<Sample5> del6_2(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1356(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1356();
					}
				};
			}
			



		
			public static Image<Sample3> del6_256(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del256();
					}
				};
			}
			



		
			public static Image<Sample3> del6_346(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del346();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1246(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1246();
					}
				};
			}
			



		
			public static Image<Sample1> del6_12345(final Image<Sample6> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del12345();
					}
				};
			}
			



		
			public static Image<Sample4> del6_45(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del45();
					}
				};
			}
			



		
			public static Image<Sample4> del6_23(final Image<Sample6> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del23();
					}
				};
			}
			



		
			public static Image<Sample5> del6_1(final Image<Sample6> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
			public static Image<Sample3> del6_356(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del356();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1256(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1256();
					}
				};
			}
			



		
			public static Image<Sample2> del6_1346(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del1346();
					}
				};
			}
			



		
			public static Image<Sample3> del6_246(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del246();
					}
				};
			}
			



		
			public static Image<Sample2> del6_2345(final Image<Sample6> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del2345();
					}
				};
			}
			



		
			public static Image<Sample3> del6_145(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del145();
					}
				};
			}
			



		
			public static Image<Sample3> del6_123(final Image<Sample6> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del123();
					}
				};
			}
			



		
		
			
			public static Image<Sample7> append16(final Image<Sample1> l, final Image<Sample6> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample7> append25(final Image<Sample2> l, final Image<Sample5> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample7> append34(final Image<Sample3> l, final Image<Sample4> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample7> append43(final Image<Sample4> l, final Image<Sample3> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample7> append52(final Image<Sample5> l, final Image<Sample2> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			
			public static Image<Sample7> append61(final Image<Sample6> l, final Image<Sample1> r){
				final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
				return new Image<Sample7>(){
					
					public PixelArea getArea(){
						return area;
					}
					
					public Sample7 get(double x, double y){
						return l.get(x,y).concat(r.get(x,y));
					}
				};
			}
			




			
			public static Image<Sample3> del7_3567(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del3567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_13467(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del13467();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2467(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2467();
					}
				};
			}
			



		
			public static Image<Sample2> del7_23457(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del23457();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1457(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1457();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1237(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1237();
					}
				};
			}
			



		
			public static Image<Sample6> del7_7(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del7();
					}
				};
			}
			



		
			public static Image<Sample1> del7_123456(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del123456();
					}
				};
			}
			



		
			public static Image<Sample4> del7_456(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del456();
					}
				};
			}
			



		
			public static Image<Sample4> del7_236(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del236();
					}
				};
			}
			



		
			public static Image<Sample5> del7_16(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del16();
					}
				};
			}
			



		
			public static Image<Sample4> del7_135(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del135();
					}
				};
			}
			



		
			public static Image<Sample5> del7_25(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del25();
					}
				};
			}
			



		
			public static Image<Sample5> del7_34(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del34();
					}
				};
			}
			



		
			public static Image<Sample4> del7_124(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del124();
					}
				};
			}
			



		
			public static Image<Sample2> del7_13567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del13567();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2567(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2567();
					}
				};
			}
			



		
			public static Image<Sample3> del7_3467(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del3467();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12467(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12467();
					}
				};
			}
			



		
			public static Image<Sample1> del7_123457(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del123457();
					}
				};
			}
			



		
			public static Image<Sample4> del7_457(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del457();
					}
				};
			}
			



		
			public static Image<Sample4> del7_237(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del237();
					}
				};
			}
			



		
			public static Image<Sample5> del7_17(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del17();
					}
				};
			}
			



		
			public static Image<Sample2> del7_23456(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del23456();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1456(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1456();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1236(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1236();
					}
				};
			}
			



		
			public static Image<Sample6> del7_6(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del6();
					}
				};
			}
			



		
			public static Image<Sample5> del7_35(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del35();
					}
				};
			}
			



		
			public static Image<Sample4> del7_125(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del125();
					}
				};
			}
			



		
			public static Image<Sample4> del7_134(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del134();
					}
				};
			}
			



		
			public static Image<Sample5> del7_24(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del24();
					}
				};
			}
			



		
			public static Image<Sample2> del7_23567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del23567();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1567(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1567();
					}
				};
			}
			



		
			public static Image<Sample1> del7_123467(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del123467();
					}
				};
			}
			



		
			public static Image<Sample4> del7_467(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del467();
					}
				};
			}
			



		
			public static Image<Sample3> del7_3457(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del3457();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12457(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12457();
					}
				};
			}
			



		
			public static Image<Sample4> del7_137(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del137();
					}
				};
			}
			



		
			public static Image<Sample5> del7_27(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del27();
					}
				};
			}
			



		
			public static Image<Sample2> del7_13456(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del13456();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2456(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2456();
					}
				};
			}
			



		
			public static Image<Sample5> del7_36(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del36();
					}
				};
			}
			



		
			public static Image<Sample4> del7_126(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del126();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1235(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1235();
					}
				};
			}
			



		
			public static Image<Sample6> del7_5(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del5();
					}
				};
			}
			



		
			public static Image<Sample4> del7_234(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del234();
					}
				};
			}
			



		
			public static Image<Sample5> del7_14(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del14();
					}
				};
			}
			



		
			public static Image<Sample1> del7_123567(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del123567();
					}
				};
			}
			



		
			public static Image<Sample4> del7_567(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_23467(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del23467();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1467(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1467();
					}
				};
			}
			



		
			public static Image<Sample2> del7_13457(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del13457();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2457(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2457();
					}
				};
			}
			



		
			public static Image<Sample5> del7_37(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del37();
					}
				};
			}
			



		
			public static Image<Sample4> del7_127(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del127();
					}
				};
			}
			



		
			public static Image<Sample3> del7_3456(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del3456();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12456(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12456();
					}
				};
			}
			



		
			public static Image<Sample4> del7_136(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del136();
					}
				};
			}
			



		
			public static Image<Sample5> del7_26(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del26();
					}
				};
			}
			



		
			public static Image<Sample4> del7_235(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del235();
					}
				};
			}
			



		
			public static Image<Sample5> del7_15(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del15();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1234(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1234();
					}
				};
			}
			



		
			public static Image<Sample6> del7_4(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del4();
					}
				};
			}
			



		
			public static Image<Sample2> del7_34567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del34567();
					}
				};
			}
			



		
			public static Image<Sample1> del7_124567(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del124567();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1367(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1367();
					}
				};
			}
			



		
			public static Image<Sample4> del7_267(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del267();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2357(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2357();
					}
				};
			}
			



		
			public static Image<Sample4> del7_157(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del157();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12347(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12347();
					}
				};
			}
			



		
			public static Image<Sample5> del7_47(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del47();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12356(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12356();
					}
				};
			}
			



		
			public static Image<Sample5> del7_56(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del56();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2346(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2346();
					}
				};
			}
			



		
			public static Image<Sample4> del7_146(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del146();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1345(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1345();
					}
				};
			}
			



		
			public static Image<Sample4> del7_245(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del245();
					}
				};
			}
			



		
			public static Image<Sample6> del7_3(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del3();
					}
				};
			}
			



		
			public static Image<Sample5> del7_12(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del12();
					}
				};
			}
			



		
			public static Image<Sample1> del7_134567(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del134567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_24567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del24567();
					}
				};
			}
			



		
			public static Image<Sample4> del7_367(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del367();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1267(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1267();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12357(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12357();
					}
				};
			}
			



		
			public static Image<Sample5> del7_57(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del57();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2347(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2347();
					}
				};
			}
			



		
			public static Image<Sample4> del7_147(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del147();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2356(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2356();
					}
				};
			}
			



		
			public static Image<Sample4> del7_156(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del156();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12346(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12346();
					}
				};
			}
			



		
			public static Image<Sample5> del7_46(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del46();
					}
				};
			}
			



		
			public static Image<Sample4> del7_345(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del345();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1245(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1245();
					}
				};
			}
			



		
			public static Image<Sample5> del7_13(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del13();
					}
				};
			}
			



		
			public static Image<Sample6> del7_2(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del2();
					}
				};
			}
			



		
			public static Image<Sample1> del7_234567(final Image<Sample7> l){
				return new Image<Sample1>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample1 get(double x, double y){
						return l.get(x,y).del234567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_14567(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del14567();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12367(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12367();
					}
				};
			}
			



		
			public static Image<Sample5> del7_67(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del67();
					}
				};
			}
			



		
			public static Image<Sample4> del7_357(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del357();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1257(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1257();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1347(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1347();
					}
				};
			}
			



		
			public static Image<Sample4> del7_247(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del247();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1356(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1356();
					}
				};
			}
			



		
			public static Image<Sample4> del7_256(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del256();
					}
				};
			}
			



		
			public static Image<Sample4> del7_346(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del346();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1246(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1246();
					}
				};
			}
			



		
			public static Image<Sample2> del7_12345(final Image<Sample7> l){
				return new Image<Sample2>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample2 get(double x, double y){
						return l.get(x,y).del12345();
					}
				};
			}
			



		
			public static Image<Sample5> del7_45(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del45();
					}
				};
			}
			



		
			public static Image<Sample5> del7_23(final Image<Sample7> l){
				return new Image<Sample5>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample5 get(double x, double y){
						return l.get(x,y).del23();
					}
				};
			}
			



		
			public static Image<Sample6> del7_1(final Image<Sample7> l){
				return new Image<Sample6>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample6 get(double x, double y){
						return l.get(x,y).del1();
					}
				};
			}
			



		
			public static Image<Sample3> del7_4567(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del4567();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2367(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2367();
					}
				};
			}
			



		
			public static Image<Sample4> del7_167(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del167();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1357(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1357();
					}
				};
			}
			



		
			public static Image<Sample4> del7_257(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del257();
					}
				};
			}
			



		
			public static Image<Sample4> del7_347(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del347();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1247(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1247();
					}
				};
			}
			



		
			public static Image<Sample4> del7_356(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del356();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1256(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1256();
					}
				};
			}
			



		
			public static Image<Sample3> del7_1346(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del1346();
					}
				};
			}
			



		
			public static Image<Sample4> del7_246(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del246();
					}
				};
			}
			



		
			public static Image<Sample3> del7_2345(final Image<Sample7> l){
				return new Image<Sample3>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample3 get(double x, double y){
						return l.get(x,y).del2345();
					}
				};
			}
			



		
			public static Image<Sample4> del7_145(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del145();
					}
				};
			}
			



		
			public static Image<Sample4> del7_123(final Image<Sample7> l){
				return new Image<Sample4>(){

					public PixelArea getArea(){ return l.getArea(); }

					public Sample4 get(double x, double y){
						return l.get(x,y).del123();
					}
				};
			}
			



		
		
	}

