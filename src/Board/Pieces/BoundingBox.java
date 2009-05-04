package Board.Pieces;




// A class to store a bounding box [x0,x1] x [y0,y1]

public class BoundingBox {

	public int x0, y0, x1, y1;

	BoundingBox( int x0, int y0, int x1, int y1 ) {
	    this.x0 = x0;
	    this.y0 = y0;
	    this.x1 = x1;
	    this.y1 = y1;
	}
}


