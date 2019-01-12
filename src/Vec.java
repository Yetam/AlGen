
public class Vec {
	
	float x;
	float y;
	
	Vec(){
		x=0;
		y=0;
	}
	Vec(float xx,float yy){
		x=xx;
		y=yy;
	}
	void copy(Vec orginal){
		this.x = orginal.x;
		this.y = orginal.y;
	}
	
	/* --- METODY DODAJĄCE --- */
	void add(Vec addition){
		this.x += addition.x;
		this.y += addition.y;
	}
	void addInto(Vec a, Vec b){
		this.x = a.x+b.x;
		this.y = a.y+b.y;
	}
	void addScaledInto(Vec a, float factor){
		this.x += a.x * factor;
		this.y += a.y * factor;
	}
	
	/* --- METODY ODEJMUJĄCE --- */
	void sub(Vec subtraction){
		this.x -= subtraction.x;
		this.y -= subtraction.y;
	}
	void subInto(Vec a, Vec b){
		this.x = a.x-b.x;
		this.y = a.y-b.y;
	}
	void subScaledInto(Vec a, float factor){
		this.x -= a.x * factor;
		this.y -= a.y * factor;
	}
	
	/* --- METODY SKALUJĄCE --- */					
	void scale(float d){
		this.x*=d;
		this.y*=d;
	}
	void scaleInto(Vec a, float factor){
		this.x=a.x*factor;
		this.x=a.y*factor;
	}
	void scaleToUnit(){
		float length = (float) (1./this.length());
		this.scale(length);
	}
	
	/* --- METODY RÓŻNE --- */
	float length(){
		return (float) Math.sqrt(this.x*this.x+this.y*this.y);
	}
	float product(Vec element){
		return this.x*element.x+this.y*element.y;
	}
	static float vectorsProduct(Vec a, Vec b){
		return a.x*b.x + a.y*b.y;
	}
	void setValues(float newX, float newY){
		x = newX;
		y = newY;
	}
	void rotateNegPi2(){
		//metoda obraca wektor o (-Pi/2)
		float tmp = this.y;
		this.y = this.x * -1;
		this.x = tmp;
	}

	
	void print(){
		System.out.println(x + " " + y);
	}
	void casOntoDirectionOfVec(Vec vec){
		float scaleFactor = (this.x*vec.x + this.y*vec.y)/(vec.x*vec.x + vec.y*vec.y);
		this.scale(scaleFactor);
	}
	
	public Vec copy() {
		return new Vec(x,y);
	}

}
