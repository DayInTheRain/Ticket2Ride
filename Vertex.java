
public class Vertex {
    double x;
    double y;
    double z;
    double w;
    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
       
    }
    
    public void VectorToPoint(Vector3f vector)
    {
    	this.x = vector.x;
    	this.y = vector.y;
    	this.z = vector.z;
    	
    	
    }
    
    public String toString()   
    {
    	String s = "(" + x + ", " + y + ", " + z + ")";
    	return s;
    }
    
    public void multiply(double scalar)
    {
    	this.x = x*scalar;
    	this.y = y*scalar;
    	this.z = z*scalar;
    }
    
    public void TranslateX(double translate)
    {
    	this.x += translate;
    }
    
    public void setX(double x)
    {
    	this.x = x;
    }
    
    public double getX()
    {
    	return x;
    }
}
