package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import java.awt.geom.AffineTransform;

import Board.Board;
import Board.PosAndDist;
import GFX.GFXLibrary;


public class ElbowPiece extends Piece
{

    private float rotation;     // angle that piece is rotated from
    // the standard (left-right) orientation

    
    public ElbowPiece (String name, float rotation, Board board, GFXLibrary gfxLibrary, int x, int y)
    {
	
		super (name, board, gfxLibrary, x, y);
		this.rotation = rotation;		
		assertDefaultEntry();
		
		movable = true;
    }//end constructor


    public void assertDefaultEntry()
    {
    	//[0] = above; [1] = right; [2] = below; [3] = left
    	switch ((int) rotation)
		{
		    case 0:
			entry [0] = true;
			entry [1] = true;
			entry [2] = false;
			entry [3] = false;
			break;
		    case 90:
			entry [0] = false;
			entry [1] = true;
			entry [2] = true;
			entry [3] = false;
			break;
		    case 180:
			entry [0] = false;
			entry [1] = false;
			entry [2] = true;
			entry [3] = true;
			break;
		    case 270:
			entry [0] = true;
			entry [1] = false;
			entry [2] = false;
			entry [3] = true;
			break;
		}
    }//end assertDefaultEntry
    
    
    public void updateCachedImage()
    {
    	BoundingBox bb = getBoundingBox ();
    	drawTransform=new AffineTransform();
    	drawTransform.translate(bb.x0, bb.y0);
    	drawTransform.rotate(Math.toRadians(rotation), (int)(gfxLibrary.pieceSize/2), (int)(gfxLibrary.pieceSize/2));
    	
	    if (selectHighlight)
			cachedImage=gfxLibrary.elbow[1];
	    else if (ICEd)
	    	cachedImage=gfxLibrary.elbow[2];
	    else if (redSecurity)
	    	cachedImage=gfxLibrary.elbow[3];
	    else if (yellowSecurity)
	    	cachedImage=gfxLibrary.elbow[4];
	    else
	    	cachedImage=gfxLibrary.elbow[0];

    }



    // Move the ball through this piece.  Return any distance that was left over
    // if the ball exits this piece.

    public float moveBall (PosAndDist ballState, float distance)
    {

    	BoundingBox bb = getBoundingBox ();
    	int centerx = (int) (bb.x0 + gfxLibrary.pieceSize / 2);
    	int centery = (int) (bb.y0 + gfxLibrary.pieceSize / 2);

    	//first, we make the ball move towards the center (if appropriate)

    	//ball moving right, right not an exit
    	if ((ballState.vx > 0) && (entry [1] == false))
    	{
    	    ballState.x = ballState.x + distance; //move the ball

    	    if (ballState.x > centerx) //moved to far to the right
    	    {

    		distance = ballState.x - centerx; //track difference of distance
    		ballState.x = centerx; //re-center
    		ballState.vx = 0;
    		if (entry [0] == true) //move up
    		    ballState.vy = -1;
    		else //move down
    		    ballState.vy = 1;

    	    } //end if ballstate.x>center x

    	    else
    		distance = 0;

    	} //end ball moving right

    	//ball moving left, left not an exit
    	else if ((ballState.vx < 0) && (entry [3] == false))
    	{
    	    ballState.x = ballState.x - distance;

    	    if (ballState.x < centerx) //moved to far to the left
    	    {

    		distance = centerx - ballState.x;
    		ballState.x = centerx;
    		ballState.vx = 0;

    		if (entry [0] == true) //move up
    		    ballState.vy = -1;
    		else //move down
    		    ballState.vy = 1;

    	    } //end if ballstate.x>center x

    	    else
    		distance = 0;

    	} //end ball moving left

    	//ball moving up, up not an exit
    	else if ((ballState.vy < 0) && (entry [0] == false))
    	{
    	    ballState.y = ballState.y - distance;

    	    if (ballState.y < centery)
    	    {
    		distance = centery - ballState.y;
    		ballState.y = centery;
    		ballState.vy = 0;

    		if (entry [1] == true) //move right
    		    ballState.vx = 1;
    		else //move left
    		    ballState.vx = -1;
    	    }
    	    else
    		distance = 0;
    	} //end ball moving up

    	//ball moving down, down not an exit
    	else if ((ballState.vy > 0) && (entry [2] == false))
    	{

    	    ballState.y = ballState.y + distance;

    	    if (ballState.y > centery)
    	    {
    		distance = ballState.y - centery;
    		ballState.y = centery;
    		ballState.vy = 0;

    		if (entry [1] == true) //move right
    		    ballState.vx = 1;
    		else //move left
    		    ballState.vx = -1;
    	    }
    	    else
    		distance = 0;
    	} //end ball moving down

    	//now we make it move towards an exit, if there's still
    	//distance to move
    	if (distance > 0)
    	{
    	    //ball moving right
    	    if ((ballState.vx > 0) && (entry [1] == true))
    	    {
    		ballState.x = ballState.x + distance;
    		if (ballState.x > bb.x1) //outside the far right
    		{
    		    distance = ballState.x - bb.x1; //returns how much over the edge it is
    		    ballState.x = bb.x1; //sets the ball on the very edge of the cell
    		}
    		else
    		    distance = 0;
    	    }
    	    //ball moving left to exit
    	    else if ((ballState.vx < 0) && (entry [3] == true))
    	    {
    		ballState.x = ballState.x - distance;
    		if (ballState.x < bb.x0)
    		{
    		    distance = bb.x0 - ballState.x;
    		    ballState.x = bb.x0;
    		}
    		else
    		    distance = 0;
    	    }
    	    //ball moving up to exit
    	    else if ((ballState.vy < 0) && (entry [0] == true))
    	    {
    		ballState.y = ballState.y - distance;
    		if (ballState.y < bb.y0)
    		{
    		    distance = bb.y0 - ballState.y;
    		    ballState.y = bb.y0;
    		}
    		else
    		    distance = 0;

    	    }
    	    //ball moving down to exit
    	    else if ((ballState.vy > 0) && (entry [2] == true))
    	    {
    		ballState.y = ballState.y + distance;

    		if (ballState.y > bb.y1)
    		{
    		    distance = ballState.y - bb.y1;
    		    ballState.y = bb.y1;
    		}
    		else
    		    distance = 0;
    	    }
    	} //end if distance>0

    	return distance;
    } //end moveball


    public void rotate ()
    {
		if (name.equals ("ul"))
		{
		    name = "ur";
		    rotation = 0;
		    assertDefaultEntry();
	
		}
		else if (name.equals ("ur"))
		{
		    name = "lr";
		    rotation = 90;
		    assertDefaultEntry();
		}
		else if (name.equals ("lr"))
		{
		    name = "ll";
		    rotation = 180;
		    assertDefaultEntry();
		}
		else
		{
		    name = "ul";
		    rotation = 270;
		    assertDefaultEntry();
		}
    } //end rotate
} //end class definition
