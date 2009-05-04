package Board.Pieces;
//GhostHack: V1.0




// Piece.java
//
// One piece on the board



import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Board.PosAndDist;
import Board.Board;
import GFX.GFXLibrary;


public class Piece
{

    

    public String name;         // the type of the piece
    public int posX, posY;    // position on the board

    public boolean [] entry;    // directions from which entry into cell is possible
    // [0] = above; [1] = right; [2] = below; [3] = left

    public boolean selectHighlight; //tile is selected (for swapping/rotating()
    public boolean movable;  //tile can be moved/swapped
    
    public boolean redSecurity; //true if a ball is currently inside
    public boolean yellowSecurity; //true when a ball has recently exited 
    public int securityCount; //the number of seconds remaining until the security flag is off.
    public boolean ICEd;// check if an ice tile has frozen this piece

    public Board board;
    public GFXLibrary gfxLibrary;
    protected BufferedImage cachedImage; //the image the tile currently should display
    public AffineTransform drawTransform; //the transformation needed to rotate and position the tile
    // Constructor

    Piece (String name, Board board,GFXLibrary gfxLibrary, int x, int y)
    {
		this.name = name;
		this.posX=x;
		this.posY=y;
		entry = new boolean [4];
		
		selectHighlight = false;

		
		movable = false;
		securityCount=0;
		redSecurity=false;
		yellowSecurity=false;
		ICEd=false;
		this.board =board;
		this.gfxLibrary=gfxLibrary;
		drawTransform = new AffineTransform();

    }
    //this sets the entry points to the default for a tile of the right type and rotation
    public void assertDefaultEntry()
    {
		entry [0] = false;
		entry [1] = false;
		entry [2] = false;
		entry [3] = false;
    }

    //returns an image of the tile to be drawn
    public BufferedImage getImage()
    {
		
    	return cachedImage;
    	    	
    }
    
    //returns the transformation needed to resposition and rotate the tile
    public AffineTransform getTransform()
    {
    	return drawTransform;
    }


    // Move a ball through this piece.  Given the (x,y) position of
    // the ball and a distance, return the new position of the ball
    // after it has traveled that distance through this piece.  If the
    // ball leaves the piece, return the position at exit, along with
    // the distance remaining to go. The generic moveball (overwriten
    // in several types of movement) assumes all 4 sides are open
    // and that a ball entering one side will pass out the other
    // without obstruction.

    public float moveBall (PosAndDist ball, float distanceToGo)
    {
    	//System.out.println("Moving inside "+name);


    	BoundingBox bb = getBoundingBox ();

    	
    	
    	    ball.x = ball.x + distanceToGo*ball.vx;
    	    ball.y = ball.y + distanceToGo*ball.vy;
    	    if (ball.x > bb.x1) //outside the far right
    	    {
    		distanceToGo = ball.x - bb.x1; //returns how much over the edge it is
    		ball.x = bb.x1; //sets the ball on the very edge of the cell
    		//System.out.println("Outside Far Right");
    		//System.out.println("BB X0: "+bb.x0+" X1: "+bb.x1+" Y0: "+bb.y0+" Y1: "+bb.y1);
    	    }
    	    else if (ball.x < bb.x0) //outside the far left
    	    {
        		distanceToGo = bb.x0-ball.x;
        		ball.x = bb.x0;
        		//System.out.println("Outside Far Left");
        		//System.out.println("BB X0: "+bb.x0+" X1: "+bb.x1+" Y0: "+bb.y0+" Y1: "+bb.y1);
        	}
    	    else if (ball.y > bb.y1) //outside the bottom
    	    {
    		distanceToGo = ball.y - bb.y1; 
    		ball.y = bb.y1; 
    		//System.out.println("Outside Bottom");
    		//System.out.println("BB X0: "+bb.x0+" X1: "+bb.x1+" Y0: "+bb.y0+" Y1: "+bb.y1);
    	    }
    	    else if (ball.y < bb.y0) //outside the top
    	    {
        		distanceToGo = bb.y0-ball.y; 
        		ball.y = bb.y0; 
        		//System.out.println("Outside Top");
        		//System.out.println("BB X0: "+bb.x0+" X1: "+bb.x1+" Y0: "+bb.y0+" Y1: "+bb.y1);
        	}
            else 
            {
    		distanceToGo = 0;
            }

    	
    	return distanceToGo;

    }


    //rotates the piece
    public void rotate ()
    {

    }


    //called when the ball is entering this tile
    public void isEntering ()
    {
    	
    }


    //called when the ball is leaving this tile
    public void isExiting ()
    {    	
    	//if this tile still has a ball in it, redSecurity will be reactivated immediately.
    	this.redSecurity=false;
    	//add to security list (yellow alert) if iced.
    	if (!this.ICEd)
    	{
	    	board.securityList.add(this);
	    	this.yellowSecurity=true;
	    	this.securityCount=250;
    	}
    	this.updateCachedImage();
    }

    // Get the bounding box (in pixels) of this piece


    public BoundingBox getBoundingBox ()
    {

	int x0 = (int) (posX * gfxLibrary.pieceSize);
	int x1 = (int) ((posX + 1) * gfxLibrary.pieceSize);
	int y0 = (int) (posY * gfxLibrary.pieceSize);
	int y1 = (int) ((posY + 1) * gfxLibrary.pieceSize);
	return new BoundingBox (x0, y0, x1, y1);
    }


    // Determine whether pixel (x,y) is inside this piece

    public boolean pointIsInside (int x, int y)
    {

	BoundingBox bb = getBoundingBox ();

	return (x >= bb.x0 && x <= bb.x1 &&
		y >= bb.y0 && y <= bb.y1);
    }

    //updates the cached image for easy retrieval. 
    //This is to prevent dozens of if-thens from
    //being called to determine WHICH sprite should be
    //returned (the red? The default? The ICE?)
    public void updateCachedImage()
    {
    	BoundingBox bb = getBoundingBox ();
    	drawTransform=new AffineTransform();
    	drawTransform.translate(bb.x0, bb.y0);
    }

    
    
    
} //end piece class
