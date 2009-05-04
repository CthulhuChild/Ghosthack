package Board.Pieces;



// A piece with a straight passage from one side of the cell to the
// other.  The passage has a default horizontal orientation, but can
// be rotated by 0 or 90 degrees.


import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Board.Board;
import Board.PosAndDist;
import GFX.GFXLibrary;


public class EndPiece extends Piece
{

    private float rotation;     // angle that piece is rotated from
    // the standard (left-right) orientation
    private int frameCounter;
    
    public EndPiece (String name, float rotation, Board board, GFXLibrary gfxLibrary, int x, int y)
    {	
		super (name, board, gfxLibrary, x, y);
		this.rotation = rotation;
		assertDefaultEntry();
		
		movable = false;
		frameCounter=0;
    }//end constructor
    
    public void assertDefaultEntry()
    {
    	//[0] = above; [1] = right; [2] = below; [3] = left
    	switch ((int) rotation)
		{
		    case 0:
			entry [0] = false;
			entry [1] = true;
			entry [2] = false;
			entry [3] = false;
			break;
		    case 90:
			entry [0] = false;
			entry [1] = false;
			entry [2] = true;
			entry [3] = false;
			break;
		    case 180:
			entry [0] = false;
			entry [1] = false;
			entry [2] = false;
			entry [3] = true;
			break;
		    case 270:
			entry [0] = true;
			entry [1] = false;
			entry [2] = false;
			entry [3] = false;
			break;
		}
    }


    public float moveBall (PosAndDist ball, float distanceToGo)
    {
    	//System.out.println("Moving inside "+name);

    	
    	boolean movingTowardsExit=false;
    	if (entry[0]&&(ball.vy<0))
    	{
    		movingTowardsExit=true;
    	}
    	else if (entry[1]&&(ball.vx>0))
    	{
    		movingTowardsExit=true;
    	}
    	else if (entry[2]&&(ball.vy>0))
    	{
    		movingTowardsExit=true;
    	}
    	else if (entry[3]&&(ball.vx<0))
    	{
    		movingTowardsExit=true;
    	}	
    	if (movingTowardsExit)
    	{
    		//System.out.println("moving towards exit");
    		distanceToGo=super.moveBall(ball, distanceToGo);
    	}
    	else //else moving towards middle
    	{
    		//System.out.println("moving towards middle");
    		BoundingBox bb = getBoundingBox ();
        	int centerx = (int) (bb.x0 + gfxLibrary.pieceSize / 2);
        	int centery = (int) (bb.y0 + gfxLibrary.pieceSize / 2);
        	ball.x = ball.x + distanceToGo*ball.vx;
     	    ball.y = ball.y + distanceToGo*ball.vy;
     	    
     	    if ((centerx-ball.x)*ball.vx<0)
     	    {
     	    	//if the ball hits the center of this tile,
     	    	//it is out of the maze. It will move no further
     	    	//and a sound will play
     	    	
     	    	distanceToGo=0;
     	    	ball.x=centerx;
     	    	ball.finished=true;
     	    	ball.inPlay=false;
     	    	board.soundLibrary.playFinish();
     	    	
     	    }
     	    else if ((centery-ball.y)*ball.vy<0)
    	    {
    	    	distanceToGo=0;
    	    	ball.y=centery;
    	    	ball.finished=true;
     	    	ball.inPlay=false;
     	    	board.soundLibrary.playFinish();
    	    }
     	    else
     	    {
     	    	distanceToGo=0;
     	    }
    	}//end else moving towards middle
    	    
    	return distanceToGo;

    }
    
    
    public void updateCachedImage()
    {
    	BoundingBox bb = getBoundingBox ();
    	drawTransform=new AffineTransform();
    	drawTransform.translate(bb.x0, bb.y0);
    	drawTransform.rotate(Math.toRadians(rotation), (int)(gfxLibrary.pieceSize/2), (int)(gfxLibrary.pieceSize/2));
    	cachedImage=gfxLibrary.start[frameCounter];
    }
    
    //instead of simply returning the cached image, we return
    //the appropriate frame of the buffered image
    public BufferedImage getImage()
    {
		frameCounter++;
		if (frameCounter>18)
			frameCounter=0;
    	return gfxLibrary.exit[frameCounter];
    	    	
    }

    public void isExiting ()
    {    	

    }

} //end class definition