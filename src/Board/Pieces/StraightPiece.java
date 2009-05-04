package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import java.awt.geom.AffineTransform;

import Board.Board;
import GFX.GFXLibrary;


public class StraightPiece extends Piece
{

    private float rotation;     // angle that piece is rotated from
    // the standard (left-right) orientation

    
    public StraightPiece (String name, float rotation, Board board, GFXLibrary gfxLibrary, int x, int y)
    {	
		super (name, board, gfxLibrary, x, y);
		this.rotation = rotation;
		assertDefaultEntry();		
		movable = true;
    }//end constructor
    
    //depending on rotation, only top and bottom or left and right will be open
    public void assertDefaultEntry()
    {
    	if (rotation == 90)
		{
		    entry [0] = false;
		    entry [1] = true;
		    entry [2] = false;
		    entry [3] = true;
		}
		else
		{
		    entry [0] = true;
		    entry [1] = false;
		    entry [2] = true;
		    entry [3] = false;
		}
    }


    
    
    public void updateCachedImage()
    {
    	BoundingBox bb = getBoundingBox ();
    	drawTransform=new AffineTransform();
    	drawTransform.translate(bb.x0, bb.y0);
    	drawTransform.rotate(Math.toRadians(rotation), (int)(gfxLibrary.pieceSize/2), (int)(gfxLibrary.pieceSize/2));
    	
	    if (selectHighlight)
			cachedImage=gfxLibrary.straight[1];
	    else if (ICEd)
	    	cachedImage=gfxLibrary.straight[2];
	    else if (redSecurity)
	    	cachedImage=gfxLibrary.straight[3];
	    else if (yellowSecurity)
	    	cachedImage=gfxLibrary.straight[4];
	    else
	    	cachedImage=gfxLibrary.straight[0];

    }



    public void rotate ()
    {
	if (rotation==90)
	{
	    name = "vt";
	    rotation = 0;
	    assertDefaultEntry();
	}
	else
	{
	    name = "hz";
	    rotation = 90;
	    assertDefaultEntry();
	}
    } //end rotate
} //end class definition
