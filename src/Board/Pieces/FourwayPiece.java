package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import java.awt.geom.AffineTransform;

import Board.Board;
import GFX.GFXLibrary;


public class FourwayPiece extends Piece
{
    
    public FourwayPiece (String name, Board board, GFXLibrary gfxLibrary, int x, int y)
    {
	
		super (name, board, gfxLibrary, x, y);			
		assertDefaultEntry();
		
		movable = true;
    }//end constructor


    public void assertDefaultEntry()
    {
			entry [0] = true;
			entry [1] = true;
			entry [2] = true;
			entry [3] = true;
					
    }//end assertDefaultEntry
    
    
    public void updateCachedImage()
    {
    	BoundingBox bb = getBoundingBox ();
    	drawTransform=new AffineTransform();
    	drawTransform.translate(bb.x0, bb.y0);    	
    	
	    if (selectHighlight)
			cachedImage=gfxLibrary.fourWay[1];
	    else if (ICEd)
	    	cachedImage=gfxLibrary.fourWay[2];
	    else if (redSecurity)
	    	cachedImage=gfxLibrary.fourWay[3];
	    else if (yellowSecurity)
	    	cachedImage=gfxLibrary.fourWay[4];
	    else
	    	cachedImage=gfxLibrary.fourWay[0];

    }


} //end class definition
