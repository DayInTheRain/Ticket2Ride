
import java.awt.Color;

public class Square {
	Vertex a, b, c, d;
	Color color;
	public Square(Vertex a, Vertex b, Vertex c, Vertex d, Color color)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.color = color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	
}
