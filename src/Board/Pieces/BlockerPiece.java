package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import Board.Board;
import Board.PosAndDist;
import GFX.GFXLibrary;


public class BlockerPiece extends Piece
{

    private float rotation;     // angle that piece is rotated from
    // the standard (left-right) orientation

    
    public BlockerPiece (String name, Board board, GFXLibrary gfxLibrary, int x, int y)
    {
	
		super (name, board, gfxLibrary, x, y);		
		this.rotation = rotation;
		cachedImage=gfxLibrary.blocker;
		assertDefaultEntry();
		

    }//end constructor
 
    public void rotate ()
    {
	
    } //end rotate
    
    //if  a piece ever gets into a blocker, blow it up.
    public float moveBall (PosAndDist ball, float distanceToGo)
    {
    	ball.destroyed=true;
    	board.soundLibrary.playExplosion();
    	ball.inPlay=false;
    	return 0;
    }
    
} //end class definition

