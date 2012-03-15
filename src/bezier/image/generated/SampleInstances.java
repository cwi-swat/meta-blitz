package bezier.image.generated;

import bezier.image.Sample;
import bezier.util.Util;

public class SampleInstances{

	
	public static class Sample1 implements Sample<Sample1>{
 	public final double a;
 
 	public Sample1(double a){
 		this.a = a;
 	}	
 
     public Sample1 add(Sample1 rhs){
 		return new Sample1(this.a + rhs.a);
 	}
 
     public Sample1 sub(Sample1 rhs){
 		return new Sample1(this.a - rhs.a);
 	}
  
     public Sample1 mul(Sample1 rhs){
 		return new Sample1(this.a * rhs.a);
 	}
 
 	public Sample1 mul(double d){
 		return new Sample1(this.a * d);
 	}
 
 	public Sample1 div(double d){
 		return new Sample1(this.a / d);
 	}
 
 	public double last(){ return a; }
 
 	public Sample1 lerp(double d,Sample1 rhs){
 		double od = 1.0 - d;
 		return new Sample1(od * this.a + d * rhs.a);
 	}
 
 	
 
 	 public Sample1 clamp(){
 		return new Sample1(Util.clamp(a));
 	}
 	
 	public Sample2 concat(Sample1 rhs) {
 		return new Sample2(this.a,rhs.a);
 	}
 	public Sample3 concat(Sample2 rhs) {
 		return new Sample3(this.a,rhs.a,rhs.b);
 	}
 	public Sample4 concat(Sample3 rhs) {
 		return new Sample4(this.a,rhs.a,rhs.b,rhs.c);
 	}
 	public Sample5 concat(Sample4 rhs) {
 		return new Sample5(this.a,rhs.a,rhs.b,rhs.c,rhs.d);
 	}
 	public Sample6 concat(Sample5 rhs) {
 		return new Sample6(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e);
 	}
 	public Sample7 concat(Sample6 rhs) {
 		return new Sample7(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e,rhs.f);
 	}
 	
 };
	
	public static class Sample2 implements Sample<Sample2>{
 	public final double a,b;
 
 	public Sample2(double a,double b){
 		this.a = a;
   this.b = b;
 	}	
 
     public Sample2 add(Sample2 rhs){
 		return new Sample2(this.a + rhs.a,this.b + rhs.b);
 	}
 
     public Sample2 sub(Sample2 rhs){
 		return new Sample2(this.a - rhs.a,this.b - rhs.b);
 	}
  
     public Sample2 mul(Sample2 rhs){
 		return new Sample2(this.a * rhs.a,this.b * rhs.b);
 	}
 
 	public Sample2 mul(double d){
 		return new Sample2(this.a * d,this.b * d);
 	}
 
 	public Sample2 div(double d){
 		return new Sample2(this.a / d,this.b / d);
 	}
 
 	public double last(){ return b; }
 
 	public Sample2 lerp(double d,Sample2 rhs){
 		double od = 1.0 - d;
 		return new Sample2(od * this.a + d * rhs.a,od * this.b + d * rhs.b);
 	}
 
 	 public Sample1 del2(){
 		return new Sample1(a);
 	} public Sample1 del1(){
 		return new Sample1(b);
 	}
 
 	 public Sample2 clamp(){
 		return new Sample2(Util.clamp(a),Util.clamp(b));
 	} public Sample2 clamp2(){
 		return new Sample2(a,Util.clamp(b));
 	} public Sample2 clamp1(){
 		return new Sample2(Util.clamp(a),b);
 	}
 	
 	public Sample3 concat(Sample1 rhs) {
 		return new Sample3(this.a,this.b,rhs.a);
 	}
 	public Sample4 concat(Sample2 rhs) {
 		return new Sample4(this.a,this.b,rhs.a,rhs.b);
 	}
 	public Sample5 concat(Sample3 rhs) {
 		return new Sample5(this.a,this.b,rhs.a,rhs.b,rhs.c);
 	}
 	public Sample6 concat(Sample4 rhs) {
 		return new Sample6(this.a,this.b,rhs.a,rhs.b,rhs.c,rhs.d);
 	}
 	public Sample7 concat(Sample5 rhs) {
 		return new Sample7(this.a,this.b,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e);
 	}
 	
 };
	
	public static class Sample3 implements Sample<Sample3>{
 	public final double a,b,c;
 
 	public Sample3(double a,double b,double c){
 		this.a = a;
   this.b = b;
   this.c = c;
 	}	
 
     public Sample3 add(Sample3 rhs){
 		return new Sample3(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c);
 	}
 
     public Sample3 sub(Sample3 rhs){
 		return new Sample3(this.a - rhs.a,this.b - rhs.b,this.c - rhs.c);
 	}
  
     public Sample3 mul(Sample3 rhs){
 		return new Sample3(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c);
 	}
 
 	public Sample3 mul(double d){
 		return new Sample3(this.a * d,this.b * d,this.c * d);
 	}
 
 	public Sample3 div(double d){
 		return new Sample3(this.a / d,this.b / d,this.c / d);
 	}
 
 	public double last(){ return c; }
 
 	public Sample3 lerp(double d,Sample3 rhs){
 		double od = 1.0 - d;
 		return new Sample3(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c);
 	}
 
 	 public Sample2 del3(){
 		return new Sample2(a,b);
 	} public Sample1 del12(){
 		return new Sample1(c);
 	} public Sample1 del13(){
 		return new Sample1(b);
 	} public Sample2 del2(){
 		return new Sample2(a,c);
 	} public Sample1 del23(){
 		return new Sample1(a);
 	} public Sample2 del1(){
 		return new Sample2(b,c);
 	}
 
 	 public Sample3 clamp3(){
 		return new Sample3(a,b,Util.clamp(c));
 	} public Sample3 clamp12(){
 		return new Sample3(Util.clamp(a),Util.clamp(b),c);
 	} public Sample3 clamp13(){
 		return new Sample3(Util.clamp(a),b,Util.clamp(c));
 	} public Sample3 clamp2(){
 		return new Sample3(a,Util.clamp(b),c);
 	} public Sample3 clamp23(){
 		return new Sample3(a,Util.clamp(b),Util.clamp(c));
 	} public Sample3 clamp1(){
 		return new Sample3(Util.clamp(a),b,c);
 	} public Sample3 clamp(){
 		return new Sample3(Util.clamp(a),Util.clamp(b),Util.clamp(c));
 	}
 	
 	public Sample4 concat(Sample1 rhs) {
 		return new Sample4(this.a,this.b,this.c,rhs.a);
 	}
 	public Sample5 concat(Sample2 rhs) {
 		return new Sample5(this.a,this.b,this.c,rhs.a,rhs.b);
 	}
 	public Sample6 concat(Sample3 rhs) {
 		return new Sample6(this.a,this.b,this.c,rhs.a,rhs.b,rhs.c);
 	}
 	public Sample7 concat(Sample4 rhs) {
 		return new Sample7(this.a,this.b,this.c,rhs.a,rhs.b,rhs.c,rhs.d);
 	}
 	
 };
	
	public static class Sample4 implements Sample<Sample4>{
 	public final double a,b,c,d;
 
 	public Sample4(double a,double b,double c,double d){
 		this.a = a;
   this.b = b;
   this.c = c;
   this.d = d;
 	}	
 
     public Sample4 add(Sample4 rhs){
 		return new Sample4(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d);
 	}
 
     public Sample4 sub(Sample4 rhs){
 		return new Sample4(this.a - rhs.a,this.b - rhs.b,this.c - rhs.c,this.d - rhs.d);
 	}
  
     public Sample4 mul(Sample4 rhs){
 		return new Sample4(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d);
 	}
 
 	public Sample4 mul(double d){
 		return new Sample4(this.a * d,this.b * d,this.c * d,this.d * d);
 	}
 
 	public Sample4 div(double d){
 		return new Sample4(this.a / d,this.b / d,this.c / d,this.d / d);
 	}
 
 	public double last(){ return d; }
 
 	public Sample4 lerp(double d,Sample4 rhs){
 		double od = 1.0 - d;
 		return new Sample4(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d);
 	}
 
 	 public Sample2 del34(){
 		return new Sample2(a,b);
 	} public Sample1 del124(){
 		return new Sample1(c);
 	} public Sample1 del134(){
 		return new Sample1(b);
 	} public Sample2 del24(){
 		return new Sample2(a,c);
 	} public Sample1 del234(){
 		return new Sample1(a);
 	} public Sample2 del14(){
 		return new Sample2(b,c);
 	} public Sample3 del4(){
 		return new Sample3(a,b,c);
 	} public Sample3 del3(){
 		return new Sample3(a,b,d);
 	} public Sample2 del12(){
 		return new Sample2(c,d);
 	} public Sample2 del13(){
 		return new Sample2(b,d);
 	} public Sample3 del2(){
 		return new Sample3(a,c,d);
 	} public Sample2 del23(){
 		return new Sample2(a,d);
 	} public Sample3 del1(){
 		return new Sample3(b,c,d);
 	} public Sample1 del123(){
 		return new Sample1(d);
 	}
 
 	 public Sample4 clamp34(){
 		return new Sample4(a,b,Util.clamp(c),Util.clamp(d));
 	} public Sample4 clamp124(){
 		return new Sample4(Util.clamp(a),Util.clamp(b),c,Util.clamp(d));
 	} public Sample4 clamp134(){
 		return new Sample4(Util.clamp(a),b,Util.clamp(c),Util.clamp(d));
 	} public Sample4 clamp24(){
 		return new Sample4(a,Util.clamp(b),c,Util.clamp(d));
 	} public Sample4 clamp234(){
 		return new Sample4(a,Util.clamp(b),Util.clamp(c),Util.clamp(d));
 	} public Sample4 clamp14(){
 		return new Sample4(Util.clamp(a),b,c,Util.clamp(d));
 	} public Sample4 clamp(){
 		return new Sample4(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d));
 	} public Sample4 clamp4(){
 		return new Sample4(a,b,c,Util.clamp(d));
 	} public Sample4 clamp3(){
 		return new Sample4(a,b,Util.clamp(c),d);
 	} public Sample4 clamp12(){
 		return new Sample4(Util.clamp(a),Util.clamp(b),c,d);
 	} public Sample4 clamp13(){
 		return new Sample4(Util.clamp(a),b,Util.clamp(c),d);
 	} public Sample4 clamp2(){
 		return new Sample4(a,Util.clamp(b),c,d);
 	} public Sample4 clamp23(){
 		return new Sample4(a,Util.clamp(b),Util.clamp(c),d);
 	} public Sample4 clamp1(){
 		return new Sample4(Util.clamp(a),b,c,d);
 	} public Sample4 clamp123(){
 		return new Sample4(Util.clamp(a),Util.clamp(b),Util.clamp(c),d);
 	}
 	
 	public Sample5 concat(Sample1 rhs) {
 		return new Sample5(this.a,this.b,this.c,this.d,rhs.a);
 	}
 	public Sample6 concat(Sample2 rhs) {
 		return new Sample6(this.a,this.b,this.c,this.d,rhs.a,rhs.b);
 	}
 	public Sample7 concat(Sample3 rhs) {
 		return new Sample7(this.a,this.b,this.c,this.d,rhs.a,rhs.b,rhs.c);
 	}
 	
 };
	
	public static class Sample5 implements Sample<Sample5>{
 	public final double a,b,c,d,e;
 
 	public Sample5(double a,double b,double c,double d,double e){
 		this.a = a;
   this.b = b;
   this.c = c;
   this.d = d;
   this.e = e;
 	}	
 
     public Sample5 add(Sample5 rhs){
 		return new Sample5(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e);
 	}
 
     public Sample5 sub(Sample5 rhs){
 		return new Sample5(this.a - rhs.a,this.b - rhs.b,this.c - rhs.c,this.d - rhs.d,this.e - rhs.e);
 	}
  
     public Sample5 mul(Sample5 rhs){
 		return new Sample5(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e);
 	}
 
 	public Sample5 mul(double d){
 		return new Sample5(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d);
 	}
 
 	public Sample5 div(double d){
 		return new Sample5(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d);
 	}
 
 	public double last(){ return e; }
 
 	public Sample5 lerp(double d,Sample5 rhs){
 		double od = 1.0 - d;
 		return new Sample5(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e);
 	}
 
 	 public Sample2 del135(){
 		return new Sample2(b,d);
 	} public Sample3 del25(){
 		return new Sample3(a,c,d);
 	} public Sample3 del34(){
 		return new Sample3(a,b,e);
 	} public Sample2 del124(){
 		return new Sample2(c,e);
 	} public Sample3 del35(){
 		return new Sample3(a,b,d);
 	} public Sample2 del125(){
 		return new Sample2(c,d);
 	} public Sample2 del134(){
 		return new Sample2(b,e);
 	} public Sample3 del24(){
 		return new Sample3(a,c,e);
 	} public Sample1 del1235(){
 		return new Sample1(d);
 	} public Sample4 del5(){
 		return new Sample4(a,b,c,d);
 	} public Sample2 del234(){
 		return new Sample2(a,e);
 	} public Sample3 del14(){
 		return new Sample3(b,c,e);
 	} public Sample2 del235(){
 		return new Sample2(a,d);
 	} public Sample3 del15(){
 		return new Sample3(b,c,d);
 	} public Sample1 del1234(){
 		return new Sample1(e);
 	} public Sample4 del4(){
 		return new Sample4(a,b,c,e);
 	} public Sample1 del1345(){
 		return new Sample1(b);
 	} public Sample2 del245(){
 		return new Sample2(a,c);
 	} public Sample4 del3(){
 		return new Sample4(a,b,d,e);
 	} public Sample3 del12(){
 		return new Sample3(c,d,e);
 	} public Sample2 del345(){
 		return new Sample2(a,b);
 	} public Sample1 del1245(){
 		return new Sample1(c);
 	} public Sample3 del13(){
 		return new Sample3(b,d,e);
 	} public Sample4 del2(){
 		return new Sample4(a,c,d,e);
 	} public Sample3 del45(){
 		return new Sample3(a,b,c);
 	} public Sample3 del23(){
 		return new Sample3(a,d,e);
 	} public Sample4 del1(){
 		return new Sample4(b,c,d,e);
 	} public Sample1 del2345(){
 		return new Sample1(a);
 	} public Sample2 del145(){
 		return new Sample2(b,c);
 	} public Sample2 del123(){
 		return new Sample2(d,e);
 	}
 
 	 public Sample5 clamp135(){
 		return new Sample5(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e));
 	} public Sample5 clamp25(){
 		return new Sample5(a,Util.clamp(b),c,d,Util.clamp(e));
 	} public Sample5 clamp34(){
 		return new Sample5(a,b,Util.clamp(c),Util.clamp(d),e);
 	} public Sample5 clamp124(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e);
 	} public Sample5 clamp35(){
 		return new Sample5(a,b,Util.clamp(c),d,Util.clamp(e));
 	} public Sample5 clamp125(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e));
 	} public Sample5 clamp134(){
 		return new Sample5(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e);
 	} public Sample5 clamp24(){
 		return new Sample5(a,Util.clamp(b),c,Util.clamp(d),e);
 	} public Sample5 clamp1235(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e));
 	} public Sample5 clamp5(){
 		return new Sample5(a,b,c,d,Util.clamp(e));
 	} public Sample5 clamp234(){
 		return new Sample5(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e);
 	} public Sample5 clamp14(){
 		return new Sample5(Util.clamp(a),b,c,Util.clamp(d),e);
 	} public Sample5 clamp235(){
 		return new Sample5(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e));
 	} public Sample5 clamp15(){
 		return new Sample5(Util.clamp(a),b,c,d,Util.clamp(e));
 	} public Sample5 clamp1234(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e);
 	} public Sample5 clamp4(){
 		return new Sample5(a,b,c,Util.clamp(d),e);
 	} public Sample5 clamp1345(){
 		return new Sample5(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp245(){
 		return new Sample5(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp3(){
 		return new Sample5(a,b,Util.clamp(c),d,e);
 	} public Sample5 clamp12(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),c,d,e);
 	} public Sample5 clamp345(){
 		return new Sample5(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp1245(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp13(){
 		return new Sample5(Util.clamp(a),b,Util.clamp(c),d,e);
 	} public Sample5 clamp2(){
 		return new Sample5(a,Util.clamp(b),c,d,e);
 	} public Sample5 clamp(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp45(){
 		return new Sample5(a,b,c,Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp23(){
 		return new Sample5(a,Util.clamp(b),Util.clamp(c),d,e);
 	} public Sample5 clamp1(){
 		return new Sample5(Util.clamp(a),b,c,d,e);
 	} public Sample5 clamp2345(){
 		return new Sample5(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp145(){
 		return new Sample5(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e));
 	} public Sample5 clamp123(){
 		return new Sample5(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e);
 	}
 	
 	public Sample6 concat(Sample1 rhs) {
 		return new Sample6(this.a,this.b,this.c,this.d,this.e,rhs.a);
 	}
 	public Sample7 concat(Sample2 rhs) {
 		return new Sample7(this.a,this.b,this.c,this.d,this.e,rhs.a,rhs.b);
 	}
 	
 };
	
	public static class Sample6 implements Sample<Sample6>{
 	public final double a,b,c,d,e,f;
 
 	public Sample6(double a,double b,double c,double d,double e,double f){
 		this.a = a;
   this.b = b;
   this.c = c;
   this.d = d;
   this.e = e;
   this.f = f;
 	}	
 
     public Sample6 add(Sample6 rhs){
 		return new Sample6(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e,this.f + rhs.f);
 	}
 
     public Sample6 sub(Sample6 rhs){
 		return new Sample6(this.a - rhs.a,this.b - rhs.b,this.c - rhs.c,this.d - rhs.d,this.e - rhs.e,this.f - rhs.f);
 	}
  
     public Sample6 mul(Sample6 rhs){
 		return new Sample6(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e,this.f * rhs.f);
 	}
 
 	public Sample6 mul(double d){
 		return new Sample6(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d,this.f * d);
 	}
 
 	public Sample6 div(double d){
 		return new Sample6(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d,this.f / d);
 	}
 
 	public double last(){ return f; }
 
 	public Sample6 lerp(double d,Sample6 rhs){
 		double od = 1.0 - d;
 		return new Sample6(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e,od * this.f + d * rhs.f);
 	}
 
 	 public Sample3 del456(){
 		return new Sample3(a,b,c);
 	} public Sample3 del236(){
 		return new Sample3(a,d,e);
 	} public Sample4 del16(){
 		return new Sample4(b,c,d,e);
 	} public Sample3 del135(){
 		return new Sample3(b,d,f);
 	} public Sample4 del25(){
 		return new Sample4(a,c,d,f);
 	} public Sample4 del34(){
 		return new Sample4(a,b,e,f);
 	} public Sample3 del124(){
 		return new Sample3(c,e,f);
 	} public Sample1 del23456(){
 		return new Sample1(a);
 	} public Sample2 del1456(){
 		return new Sample2(b,c);
 	} public Sample2 del1236(){
 		return new Sample2(d,e);
 	} public Sample5 del6(){
 		return new Sample5(a,b,c,d,e);
 	} public Sample4 del35(){
 		return new Sample4(a,b,d,f);
 	} public Sample3 del125(){
 		return new Sample3(c,d,f);
 	} public Sample3 del134(){
 		return new Sample3(b,e,f);
 	} public Sample4 del24(){
 		return new Sample4(a,c,e,f);
 	} public Sample1 del13456(){
 		return new Sample1(b);
 	} public Sample2 del2456(){
 		return new Sample2(a,c);
 	} public Sample4 del36(){
 		return new Sample4(a,b,d,e);
 	} public Sample3 del126(){
 		return new Sample3(c,d,e);
 	} public Sample2 del1235(){
 		return new Sample2(d,f);
 	} public Sample5 del5(){
 		return new Sample5(a,b,c,d,f);
 	} public Sample3 del234(){
 		return new Sample3(a,e,f);
 	} public Sample4 del14(){
 		return new Sample4(b,c,e,f);
 	} public Sample2 del3456(){
 		return new Sample2(a,b);
 	} public Sample1 del12456(){
 		return new Sample1(c);
 	} public Sample3 del136(){
 		return new Sample3(b,d,e);
 	} public Sample4 del26(){
 		return new Sample4(a,c,d,e);
 	} public Sample3 del235(){
 		return new Sample3(a,d,f);
 	} public Sample4 del15(){
 		return new Sample4(b,c,d,f);
 	} public Sample2 del1234(){
 		return new Sample2(e,f);
 	} public Sample5 del4(){
 		return new Sample5(a,b,c,e,f);
 	} public Sample1 del12356(){
 		return new Sample1(d);
 	} public Sample4 del56(){
 		return new Sample4(a,b,c,d);
 	} public Sample2 del2346(){
 		return new Sample2(a,e);
 	} public Sample3 del146(){
 		return new Sample3(b,c,e);
 	} public Sample2 del1345(){
 		return new Sample2(b,f);
 	} public Sample3 del245(){
 		return new Sample3(a,c,f);
 	} public Sample5 del3(){
 		return new Sample5(a,b,d,e,f);
 	} public Sample4 del12(){
 		return new Sample4(c,d,e,f);
 	} public Sample2 del2356(){
 		return new Sample2(a,d);
 	} public Sample3 del156(){
 		return new Sample3(b,c,d);
 	} public Sample1 del12346(){
 		return new Sample1(e);
 	} public Sample4 del46(){
 		return new Sample4(a,b,c,e);
 	} public Sample3 del345(){
 		return new Sample3(a,b,f);
 	} public Sample2 del1245(){
 		return new Sample2(c,f);
 	} public Sample4 del13(){
 		return new Sample4(b,d,e,f);
 	} public Sample5 del2(){
 		return new Sample5(a,c,d,e,f);
 	} public Sample2 del1356(){
 		return new Sample2(b,d);
 	} public Sample3 del256(){
 		return new Sample3(a,c,d);
 	} public Sample3 del346(){
 		return new Sample3(a,b,e);
 	} public Sample2 del1246(){
 		return new Sample2(c,e);
 	} public Sample1 del12345(){
 		return new Sample1(f);
 	} public Sample4 del45(){
 		return new Sample4(a,b,c,f);
 	} public Sample4 del23(){
 		return new Sample4(a,d,e,f);
 	} public Sample5 del1(){
 		return new Sample5(b,c,d,e,f);
 	} public Sample3 del356(){
 		return new Sample3(a,b,d);
 	} public Sample2 del1256(){
 		return new Sample2(c,d);
 	} public Sample2 del1346(){
 		return new Sample2(b,e);
 	} public Sample3 del246(){
 		return new Sample3(a,c,e);
 	} public Sample2 del2345(){
 		return new Sample2(a,f);
 	} public Sample3 del145(){
 		return new Sample3(b,c,f);
 	} public Sample3 del123(){
 		return new Sample3(d,e,f);
 	}
 
 	 public Sample6 clamp(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp456(){
 		return new Sample6(a,b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp236(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f));
 	} public Sample6 clamp16(){
 		return new Sample6(Util.clamp(a),b,c,d,e,Util.clamp(f));
 	} public Sample6 clamp135(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),f);
 	} public Sample6 clamp25(){
 		return new Sample6(a,Util.clamp(b),c,d,Util.clamp(e),f);
 	} public Sample6 clamp34(){
 		return new Sample6(a,b,Util.clamp(c),Util.clamp(d),e,f);
 	} public Sample6 clamp124(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,f);
 	} public Sample6 clamp23456(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp1456(){
 		return new Sample6(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp1236(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f));
 	} public Sample6 clamp6(){
 		return new Sample6(a,b,c,d,e,Util.clamp(f));
 	} public Sample6 clamp35(){
 		return new Sample6(a,b,Util.clamp(c),d,Util.clamp(e),f);
 	} public Sample6 clamp125(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),f);
 	} public Sample6 clamp134(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,f);
 	} public Sample6 clamp24(){
 		return new Sample6(a,Util.clamp(b),c,Util.clamp(d),e,f);
 	} public Sample6 clamp13456(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp2456(){
 		return new Sample6(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp36(){
 		return new Sample6(a,b,Util.clamp(c),d,e,Util.clamp(f));
 	} public Sample6 clamp126(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,d,e,Util.clamp(f));
 	} public Sample6 clamp1235(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f);
 	} public Sample6 clamp5(){
 		return new Sample6(a,b,c,d,Util.clamp(e),f);
 	} public Sample6 clamp234(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f);
 	} public Sample6 clamp14(){
 		return new Sample6(Util.clamp(a),b,c,Util.clamp(d),e,f);
 	} public Sample6 clamp3456(){
 		return new Sample6(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp12456(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp136(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),d,e,Util.clamp(f));
 	} public Sample6 clamp26(){
 		return new Sample6(a,Util.clamp(b),c,d,e,Util.clamp(f));
 	} public Sample6 clamp235(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f);
 	} public Sample6 clamp15(){
 		return new Sample6(Util.clamp(a),b,c,d,Util.clamp(e),f);
 	} public Sample6 clamp1234(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f);
 	} public Sample6 clamp4(){
 		return new Sample6(a,b,c,Util.clamp(d),e,f);
 	} public Sample6 clamp12356(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp56(){
 		return new Sample6(a,b,c,d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp2346(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp146(){
 		return new Sample6(Util.clamp(a),b,c,Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp1345(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp245(){
 		return new Sample6(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp3(){
 		return new Sample6(a,b,Util.clamp(c),d,e,f);
 	} public Sample6 clamp12(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,d,e,f);
 	} public Sample6 clamp2356(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp156(){
 		return new Sample6(Util.clamp(a),b,c,d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp12346(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp46(){
 		return new Sample6(a,b,c,Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp345(){
 		return new Sample6(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp1245(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp13(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),d,e,f);
 	} public Sample6 clamp2(){
 		return new Sample6(a,Util.clamp(b),c,d,e,f);
 	} public Sample6 clamp1356(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp256(){
 		return new Sample6(a,Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp346(){
 		return new Sample6(a,b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp1246(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp12345(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp45(){
 		return new Sample6(a,b,c,Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp23(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),d,e,f);
 	} public Sample6 clamp1(){
 		return new Sample6(Util.clamp(a),b,c,d,e,f);
 	} public Sample6 clamp356(){
 		return new Sample6(a,b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp1256(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f));
 	} public Sample6 clamp1346(){
 		return new Sample6(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp246(){
 		return new Sample6(a,Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f));
 	} public Sample6 clamp2345(){
 		return new Sample6(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp145(){
 		return new Sample6(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),f);
 	} public Sample6 clamp123(){
 		return new Sample6(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,f);
 	}
 	
 	public Sample7 concat(Sample1 rhs) {
 		return new Sample7(this.a,this.b,this.c,this.d,this.e,this.f,rhs.a);
 	}
 	
 };
	
	public static class Sample7 implements Sample<Sample7>{
 	public final double a,b,c,d,e,f,g;
 
 	public Sample7(double a,double b,double c,double d,double e,double f,double g){
 		this.a = a;
   this.b = b;
   this.c = c;
   this.d = d;
   this.e = e;
   this.f = f;
   this.g = g;
 	}	
 
     public Sample7 add(Sample7 rhs){
 		return new Sample7(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e,this.f + rhs.f,this.g + rhs.g);
 	}
 
     public Sample7 sub(Sample7 rhs){
 		return new Sample7(this.a - rhs.a,this.b - rhs.b,this.c - rhs.c,this.d - rhs.d,this.e - rhs.e,this.f - rhs.f,this.g - rhs.g);
 	}
  
     public Sample7 mul(Sample7 rhs){
 		return new Sample7(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e,this.f * rhs.f,this.g * rhs.g);
 	}
 
 	public Sample7 mul(double d){
 		return new Sample7(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d,this.f * d,this.g * d);
 	}
 
 	public Sample7 div(double d){
 		return new Sample7(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d,this.f / d,this.g / d);
 	}
 
 	public double last(){ return g; }
 
 	public Sample7 lerp(double d,Sample7 rhs){
 		double od = 1.0 - d;
 		return new Sample7(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e,od * this.f + d * rhs.f,od * this.g + d * rhs.g);
 	}
 
 	 public Sample3 del3567(){
 		return new Sample3(a,b,d);
 	} public Sample2 del12567(){
 		return new Sample2(c,d);
 	} public Sample2 del13467(){
 		return new Sample2(b,e);
 	} public Sample3 del2467(){
 		return new Sample3(a,c,e);
 	} public Sample2 del23457(){
 		return new Sample2(a,f);
 	} public Sample3 del1457(){
 		return new Sample3(b,c,f);
 	} public Sample3 del1237(){
 		return new Sample3(d,e,f);
 	} public Sample6 del7(){
 		return new Sample6(a,b,c,d,e,f);
 	} public Sample1 del123456(){
 		return new Sample1(g);
 	} public Sample4 del456(){
 		return new Sample4(a,b,c,g);
 	} public Sample4 del236(){
 		return new Sample4(a,d,e,g);
 	} public Sample5 del16(){
 		return new Sample5(b,c,d,e,g);
 	} public Sample4 del135(){
 		return new Sample4(b,d,f,g);
 	} public Sample5 del25(){
 		return new Sample5(a,c,d,f,g);
 	} public Sample5 del34(){
 		return new Sample5(a,b,e,f,g);
 	} public Sample4 del124(){
 		return new Sample4(c,e,f,g);
 	} public Sample2 del13567(){
 		return new Sample2(b,d);
 	} public Sample3 del2567(){
 		return new Sample3(a,c,d);
 	} public Sample3 del3467(){
 		return new Sample3(a,b,e);
 	} public Sample2 del12467(){
 		return new Sample2(c,e);
 	} public Sample1 del123457(){
 		return new Sample1(f);
 	} public Sample4 del457(){
 		return new Sample4(a,b,c,f);
 	} public Sample4 del237(){
 		return new Sample4(a,d,e,f);
 	} public Sample5 del17(){
 		return new Sample5(b,c,d,e,f);
 	} public Sample2 del23456(){
 		return new Sample2(a,g);
 	} public Sample3 del1456(){
 		return new Sample3(b,c,g);
 	} public Sample3 del1236(){
 		return new Sample3(d,e,g);
 	} public Sample6 del6(){
 		return new Sample6(a,b,c,d,e,g);
 	} public Sample5 del35(){
 		return new Sample5(a,b,d,f,g);
 	} public Sample4 del125(){
 		return new Sample4(c,d,f,g);
 	} public Sample4 del134(){
 		return new Sample4(b,e,f,g);
 	} public Sample5 del24(){
 		return new Sample5(a,c,e,f,g);
 	} public Sample2 del23567(){
 		return new Sample2(a,d);
 	} public Sample3 del1567(){
 		return new Sample3(b,c,d);
 	} public Sample1 del123467(){
 		return new Sample1(e);
 	} public Sample4 del467(){
 		return new Sample4(a,b,c,e);
 	} public Sample3 del3457(){
 		return new Sample3(a,b,f);
 	} public Sample2 del12457(){
 		return new Sample2(c,f);
 	} public Sample4 del137(){
 		return new Sample4(b,d,e,f);
 	} public Sample5 del27(){
 		return new Sample5(a,c,d,e,f);
 	} public Sample2 del13456(){
 		return new Sample2(b,g);
 	} public Sample3 del2456(){
 		return new Sample3(a,c,g);
 	} public Sample5 del36(){
 		return new Sample5(a,b,d,e,g);
 	} public Sample4 del126(){
 		return new Sample4(c,d,e,g);
 	} public Sample3 del1235(){
 		return new Sample3(d,f,g);
 	} public Sample6 del5(){
 		return new Sample6(a,b,c,d,f,g);
 	} public Sample4 del234(){
 		return new Sample4(a,e,f,g);
 	} public Sample5 del14(){
 		return new Sample5(b,c,e,f,g);
 	} public Sample1 del123567(){
 		return new Sample1(d);
 	} public Sample4 del567(){
 		return new Sample4(a,b,c,d);
 	} public Sample2 del23467(){
 		return new Sample2(a,e);
 	} public Sample3 del1467(){
 		return new Sample3(b,c,e);
 	} public Sample2 del13457(){
 		return new Sample2(b,f);
 	} public Sample3 del2457(){
 		return new Sample3(a,c,f);
 	} public Sample5 del37(){
 		return new Sample5(a,b,d,e,f);
 	} public Sample4 del127(){
 		return new Sample4(c,d,e,f);
 	} public Sample3 del3456(){
 		return new Sample3(a,b,g);
 	} public Sample2 del12456(){
 		return new Sample2(c,g);
 	} public Sample4 del136(){
 		return new Sample4(b,d,e,g);
 	} public Sample5 del26(){
 		return new Sample5(a,c,d,e,g);
 	} public Sample4 del235(){
 		return new Sample4(a,d,f,g);
 	} public Sample5 del15(){
 		return new Sample5(b,c,d,f,g);
 	} public Sample3 del1234(){
 		return new Sample3(e,f,g);
 	} public Sample6 del4(){
 		return new Sample6(a,b,c,e,f,g);
 	} public Sample2 del34567(){
 		return new Sample2(a,b);
 	} public Sample1 del124567(){
 		return new Sample1(c);
 	} public Sample3 del1367(){
 		return new Sample3(b,d,e);
 	} public Sample4 del267(){
 		return new Sample4(a,c,d,e);
 	} public Sample3 del2357(){
 		return new Sample3(a,d,f);
 	} public Sample4 del157(){
 		return new Sample4(b,c,d,f);
 	} public Sample2 del12347(){
 		return new Sample2(e,f);
 	} public Sample5 del47(){
 		return new Sample5(a,b,c,e,f);
 	} public Sample2 del12356(){
 		return new Sample2(d,g);
 	} public Sample5 del56(){
 		return new Sample5(a,b,c,d,g);
 	} public Sample3 del2346(){
 		return new Sample3(a,e,g);
 	} public Sample4 del146(){
 		return new Sample4(b,c,e,g);
 	} public Sample3 del1345(){
 		return new Sample3(b,f,g);
 	} public Sample4 del245(){
 		return new Sample4(a,c,f,g);
 	} public Sample6 del3(){
 		return new Sample6(a,b,d,e,f,g);
 	} public Sample5 del12(){
 		return new Sample5(c,d,e,f,g);
 	} public Sample1 del134567(){
 		return new Sample1(b);
 	} public Sample2 del24567(){
 		return new Sample2(a,c);
 	} public Sample4 del367(){
 		return new Sample4(a,b,d,e);
 	} public Sample3 del1267(){
 		return new Sample3(c,d,e);
 	} public Sample2 del12357(){
 		return new Sample2(d,f);
 	} public Sample5 del57(){
 		return new Sample5(a,b,c,d,f);
 	} public Sample3 del2347(){
 		return new Sample3(a,e,f);
 	} public Sample4 del147(){
 		return new Sample4(b,c,e,f);
 	} public Sample3 del2356(){
 		return new Sample3(a,d,g);
 	} public Sample4 del156(){
 		return new Sample4(b,c,d,g);
 	} public Sample2 del12346(){
 		return new Sample2(e,g);
 	} public Sample5 del46(){
 		return new Sample5(a,b,c,e,g);
 	} public Sample4 del345(){
 		return new Sample4(a,b,f,g);
 	} public Sample3 del1245(){
 		return new Sample3(c,f,g);
 	} public Sample5 del13(){
 		return new Sample5(b,d,e,f,g);
 	} public Sample6 del2(){
 		return new Sample6(a,c,d,e,f,g);
 	} public Sample1 del234567(){
 		return new Sample1(a);
 	} public Sample2 del14567(){
 		return new Sample2(b,c);
 	} public Sample2 del12367(){
 		return new Sample2(d,e);
 	} public Sample5 del67(){
 		return new Sample5(a,b,c,d,e);
 	} public Sample4 del357(){
 		return new Sample4(a,b,d,f);
 	} public Sample3 del1257(){
 		return new Sample3(c,d,f);
 	} public Sample3 del1347(){
 		return new Sample3(b,e,f);
 	} public Sample4 del247(){
 		return new Sample4(a,c,e,f);
 	} public Sample3 del1356(){
 		return new Sample3(b,d,g);
 	} public Sample4 del256(){
 		return new Sample4(a,c,d,g);
 	} public Sample4 del346(){
 		return new Sample4(a,b,e,g);
 	} public Sample3 del1246(){
 		return new Sample3(c,e,g);
 	} public Sample2 del12345(){
 		return new Sample2(f,g);
 	} public Sample5 del45(){
 		return new Sample5(a,b,c,f,g);
 	} public Sample5 del23(){
 		return new Sample5(a,d,e,f,g);
 	} public Sample6 del1(){
 		return new Sample6(b,c,d,e,f,g);
 	} public Sample3 del4567(){
 		return new Sample3(a,b,c);
 	} public Sample3 del2367(){
 		return new Sample3(a,d,e);
 	} public Sample4 del167(){
 		return new Sample4(b,c,d,e);
 	} public Sample3 del1357(){
 		return new Sample3(b,d,f);
 	} public Sample4 del257(){
 		return new Sample4(a,c,d,f);
 	} public Sample4 del347(){
 		return new Sample4(a,b,e,f);
 	} public Sample3 del1247(){
 		return new Sample3(c,e,f);
 	} public Sample4 del356(){
 		return new Sample4(a,b,d,g);
 	} public Sample3 del1256(){
 		return new Sample3(c,d,g);
 	} public Sample3 del1346(){
 		return new Sample3(b,e,g);
 	} public Sample4 del246(){
 		return new Sample4(a,c,e,g);
 	} public Sample3 del2345(){
 		return new Sample3(a,f,g);
 	} public Sample4 del145(){
 		return new Sample4(b,c,f,g);
 	} public Sample4 del123(){
 		return new Sample4(d,e,f,g);
 	}
 
 	 public Sample7 clamp3567(){
 		return new Sample7(a,b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp12567(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp13467(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp2467(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp23457(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp1457(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp1237(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,f,Util.clamp(g));
 	} public Sample7 clamp7(){
 		return new Sample7(a,b,c,d,e,f,Util.clamp(g));
 	} public Sample7 clamp123456(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp456(){
 		return new Sample7(a,b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp236(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f),g);
 	} public Sample7 clamp16(){
 		return new Sample7(Util.clamp(a),b,c,d,e,Util.clamp(f),g);
 	} public Sample7 clamp135(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),f,g);
 	} public Sample7 clamp25(){
 		return new Sample7(a,Util.clamp(b),c,d,Util.clamp(e),f,g);
 	} public Sample7 clamp34(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),e,f,g);
 	} public Sample7 clamp124(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,f,g);
 	} public Sample7 clamp13567(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp2567(){
 		return new Sample7(a,Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp3467(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp12467(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp123457(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp457(){
 		return new Sample7(a,b,c,Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp237(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,e,f,Util.clamp(g));
 	} public Sample7 clamp17(){
 		return new Sample7(Util.clamp(a),b,c,d,e,f,Util.clamp(g));
 	} public Sample7 clamp23456(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp1456(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp1236(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f),g);
 	} public Sample7 clamp6(){
 		return new Sample7(a,b,c,d,e,Util.clamp(f),g);
 	} public Sample7 clamp35(){
 		return new Sample7(a,b,Util.clamp(c),d,Util.clamp(e),f,g);
 	} public Sample7 clamp125(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),f,g);
 	} public Sample7 clamp134(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,f,g);
 	} public Sample7 clamp24(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),e,f,g);
 	} public Sample7 clamp23567(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp1567(){
 		return new Sample7(Util.clamp(a),b,c,d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp123467(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp467(){
 		return new Sample7(a,b,c,Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp3457(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp12457(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp137(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,e,f,Util.clamp(g));
 	} public Sample7 clamp27(){
 		return new Sample7(a,Util.clamp(b),c,d,e,f,Util.clamp(g));
 	} public Sample7 clamp13456(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp2456(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp36(){
 		return new Sample7(a,b,Util.clamp(c),d,e,Util.clamp(f),g);
 	} public Sample7 clamp126(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,e,Util.clamp(f),g);
 	} public Sample7 clamp1235(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f,g);
 	} public Sample7 clamp5(){
 		return new Sample7(a,b,c,d,Util.clamp(e),f,g);
 	} public Sample7 clamp234(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f,g);
 	} public Sample7 clamp14(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),e,f,g);
 	} public Sample7 clamp123567(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp567(){
 		return new Sample7(a,b,c,d,Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp23467(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp1467(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp13457(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp2457(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp37(){
 		return new Sample7(a,b,Util.clamp(c),d,e,f,Util.clamp(g));
 	} public Sample7 clamp127(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,e,f,Util.clamp(g));
 	} public Sample7 clamp3456(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp12456(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp136(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,e,Util.clamp(f),g);
 	} public Sample7 clamp26(){
 		return new Sample7(a,Util.clamp(b),c,d,e,Util.clamp(f),g);
 	} public Sample7 clamp235(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f,g);
 	} public Sample7 clamp15(){
 		return new Sample7(Util.clamp(a),b,c,d,Util.clamp(e),f,g);
 	} public Sample7 clamp1234(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f,g);
 	} public Sample7 clamp4(){
 		return new Sample7(a,b,c,Util.clamp(d),e,f,g);
 	} public Sample7 clamp34567(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp124567(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp1367(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp267(){
 		return new Sample7(a,Util.clamp(b),c,d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp2357(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp157(){
 		return new Sample7(Util.clamp(a),b,c,d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp12347(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp47(){
 		return new Sample7(a,b,c,Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp12356(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp56(){
 		return new Sample7(a,b,c,d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp2346(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp146(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp1345(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp245(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp3(){
 		return new Sample7(a,b,Util.clamp(c),d,e,f,g);
 	} public Sample7 clamp12(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,e,f,g);
 	} public Sample7 clamp134567(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp24567(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp367(){
 		return new Sample7(a,b,Util.clamp(c),d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp1267(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp12357(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp57(){
 		return new Sample7(a,b,c,d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp2347(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp147(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp2356(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp156(){
 		return new Sample7(Util.clamp(a),b,c,d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp12346(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp46(){
 		return new Sample7(a,b,c,Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp345(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp1245(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp13(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,e,f,g);
 	} public Sample7 clamp2(){
 		return new Sample7(a,Util.clamp(b),c,d,e,f,g);
 	} public Sample7 clamp234567(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp14567(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp12367(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp67(){
 		return new Sample7(a,b,c,d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp357(){
 		return new Sample7(a,b,Util.clamp(c),d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp1257(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp1347(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp247(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp1356(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp256(){
 		return new Sample7(a,Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp346(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp1246(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp12345(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp45(){
 		return new Sample7(a,b,c,Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp23(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,e,f,g);
 	} public Sample7 clamp1(){
 		return new Sample7(Util.clamp(a),b,c,d,e,f,g);
 	} public Sample7 clamp(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp4567(){
 		return new Sample7(a,b,c,Util.clamp(d),Util.clamp(e),Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp2367(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp167(){
 		return new Sample7(Util.clamp(a),b,c,d,e,Util.clamp(f),Util.clamp(g));
 	} public Sample7 clamp1357(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp257(){
 		return new Sample7(a,Util.clamp(b),c,d,Util.clamp(e),f,Util.clamp(g));
 	} public Sample7 clamp347(){
 		return new Sample7(a,b,Util.clamp(c),Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp1247(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,Util.clamp(d),e,f,Util.clamp(g));
 	} public Sample7 clamp356(){
 		return new Sample7(a,b,Util.clamp(c),d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp1256(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),c,d,Util.clamp(e),Util.clamp(f),g);
 	} public Sample7 clamp1346(){
 		return new Sample7(Util.clamp(a),b,Util.clamp(c),Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp246(){
 		return new Sample7(a,Util.clamp(b),c,Util.clamp(d),e,Util.clamp(f),g);
 	} public Sample7 clamp2345(){
 		return new Sample7(a,Util.clamp(b),Util.clamp(c),Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp145(){
 		return new Sample7(Util.clamp(a),b,c,Util.clamp(d),Util.clamp(e),f,g);
 	} public Sample7 clamp123(){
 		return new Sample7(Util.clamp(a),Util.clamp(b),Util.clamp(c),d,e,f,g);
 	}
 	
 	
 };
}
	
	