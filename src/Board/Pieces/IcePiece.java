package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import java.awt.geom.AffineTransform;

import Board.Board;
import Board.PosAndDist;
import GFX.GFXLibrary;


public class IcePiece extends Piece
{


    
    public IcePiece (String name, Board board, GFXLibrary gfxLibrary, int x, int y)
    {
	
		super (name, board, gfxLibrary, x, y);			
		assertDefaultEntry();
		cachedImage=gfxLibrary.ice;
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

    }

    

    public void isExiting ()
    {    	

    }
    
    //when the ball enters an ice tile, we ICE a random tile
    public void isEntering ()
    {
    	board.performRandomICE();
    	board.soundLibrary.playICE();
    }


} //end class definition