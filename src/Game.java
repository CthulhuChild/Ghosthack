//Ghosthack: A simple java game by graeme hill
//Game.java contains most of the applet structure, as well as the main loop

import java.applet.*;
import java.awt.*;
import java.net.URL;
import java.nio.Buffer;

import Board.*;
import Board.Pieces.Piece;
import GFX.GFXLibrary;
import Sound.SoundLibrary;



import java.awt.image.BufferedImage;
import java.awt.event.*;





public class Game extends Applet implements Runnable
{
	

	//Important classes
	public BufferedImage bufferImage; //Buffered Image used to draw the game
	public Graphics2D buffer; //the graphics object used to draw images/text to the bufferImage
	public boolean doneRenderingFrame; //flag to indicate that a frame has finished drawing
	public MediaTracker tracker; //Tracker that keeps a manifest of loading graphics
	
	public GFXLibrary gfxLibrary; //a graphics library
 	public SoundLibrary soundLibrary; //a sound library
	public Board board; //the playing space
	
	public int xRes=800; //the resolution of the game can be changed, however
	public int yRes=600; //not without some fairly serious ramifications. thus
						 //I kept the resolution as an internal variable rather than
						 //risk any accidents.

	
	//Background text variables
	int colorDirection=1; //a flag related to the background text function
	int colorLevel=0; //another background text flag, which determines the intensity of the
	 				  //green text used in the background
	
	public Font smallFont; //the same font is used throughout the game, but 3 sizes are employed
	public Font largeFont;
	public Font mediumFont;
	
	//Main Loop Control flags
	public boolean loadingResources=true; //flag relating to progress of sound/gfx libraries
	public boolean gameRunning=false; //game running in main loop if this is true
	public boolean loadingBoard=false;  //game is "loading" if this is true (board is slowly 
										//Drawn across the screen
	public int loadingTimer=0; //timer to indicate the number of iterations in the loadingBoard loop
	public int numRowsDisplayed=0; //number of colums to display of the board during loadingBoard loop
	public boolean userQuery=false; //True if awaiting a mouseclick before starting a level
	
	
	public String queryText1=""; //text that appears in a bubble before start of the level
	public String queryText2="";
	public String queryText3="";
	public String wrapAroundText; //scroling text that appears underneith the games name
	public int wrapAroundCounter;
	
	//Level flags
	public int currentLevel=1;
	public int maxLevel=4;
	public boolean youWin;
	
	//initialization method
	public void init()
	{
		//initialize doublebuffer
		bufferImage = new BufferedImage(xRes, yRes, BufferedImage.BITMASK);    
		buffer = (Graphics2D)bufferImage.getGraphics();
		smallFont=new Font("Courier", Font.PLAIN, 12);
		largeFont=new Font("Courier", Font.PLAIN, 32);
		mediumFont=new Font("Courier", Font.PLAIN, 16);
		
		buffer.setColor(Color.black);
		buffer.fillRect(0, 0, xRes, yRes);
		//this seems excessive, but it's to seed the background with an aesthetically
		//pleasing number of 1s and 0s.
		for (int x=0; x<1050; x++)
		{
			advanceBackgroundText();
		}
		int colorDirection=1;
		int colorLevel=0;
		//initiate loadtracker
		tracker=new MediaTracker(this);
		//initiate graphics and sound
		gfxLibrary=new GFXLibrary(this, tracker, getCodeBase());
		soundLibrary=new SoundLibrary(this, getCodeBase());
		//initiate board
		board=new Board(gfxLibrary, soundLibrary, getCodeBase());
		//initiate micelisteners
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
		queryText1="//...";
		queryText2="Level "+currentLevel +" Loaded";
		queryText3="Click To Proceed";
	}

	//function to check if loading of files is complete
	public boolean checkDoneLoading()
	{
		
		return (gfxLibrary.isDoneLoading()&&soundLibrary.isDoneLoading());
	}
	
	//start method, automatically called after initiation is complete
	public void start ()
	{
		//initialize and start thread
		Thread th = new Thread (this);
		th.start ();
	}

	
	public void stop()
	{

	}

	//This applet is self-contained, so I'm not worried about garbage collection
	//if it is closed.
	public void destroy()
	{

	}
	
	
	
	//the run section. The paint and update Game logic functions are deliberately seperated,
	//as much of run is concerned with timing issues.
	public void run ()
	{
		//set thread priority
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		Long frameLength=40l;
		Long startTime;
		Long endTime;
		Long elapsedTime=0l;
		
		doneRenderingFrame=true;
		
		//loading loop
		while (!checkDoneLoading())
		{

			
			startTime=System.currentTimeMillis();
			if (doneRenderingFrame)
			{
			//System.out.println("Rendering");
			repaint();
			}
			else
			{
				//System.out.println("Skipping Rendering");
			}
			updateGameLogic();
			endTime=System.currentTimeMillis();
			
			
		
			
			elapsedTime=endTime-startTime;
			
			
				try
				{
					if (frameLength>elapsedTime)
					{
					Thread.sleep (frameLength-elapsedTime);
					}
					else
					{
					//System.out.println("-------------Frame took too long!---------------");
					}
				}
				catch (InterruptedException ex)
				{
					// do nothing
				}
		}//end while loading

		//graphics and sound have been loaded, load the board and begin main loop
		loadingResources=false;
		board.loadBoard("level1.txt");
		loadingBoard=true;
		wrapAroundText="Connecting. ";
		
		//main loop
		while (true)
		{

		
			//get start time of loop
			startTime=System.currentTimeMillis();
			
			//this if statement prevents a new frame from
			//being drawn until the old frame is done
			if (doneRenderingFrame)
			{
			//System.out.println("Rendering");
			repaint();
			}
			else
			{
				//System.out.println("Skipping Rendering");
			}
			
			//after a frame has been drawn (or skipped), update the games logic
			updateGameLogic();
			
			//record the end time of the loop
			endTime=System.currentTimeMillis();
			
			//calculate the elapsed time
			elapsedTime=endTime-startTime;
					
			
				try
				{
			
					if (frameLength>elapsedTime)
					{
					Thread.sleep (frameLength-elapsedTime);
					}
					else
					{
					//System.out.println("-------------Frame took too long!---------------");
					}
				}
				catch (InterruptedException ex)
				{
					//System.out.println("Fail");
				}
			}//end while game logic runs

	}//end run

	//this is a seemingly very messy function that is called every frame update
	//it actually is quite simple, mostly what it does is create a scatering of
	//1s and 0s across the screen, making a sort of matrix-style effect. It is 
	//more efficient (and more random) to do this procedurally than to actually
	//create an animation and run it.
	public void advanceBackgroundText()
	{
		
		
		int xRand=(int)(Math.random()*xRes/8)*8;
		int yRand=(int)(Math.random()*yRes/10)*10;
		//Basically colorLevel is the intensity of the green, while
		//color direction is whethor the color is getting brighter or darker
		//(the intenisty rises and falls regularly to create a gentle pulsing).
		colorLevel=colorLevel+colorDirection*5;
		if (colorLevel>235)
		{
			colorDirection=-1;
			colorLevel=235;
		}
		else if (colorLevel<10)
		{
			colorDirection=1;
			colorLevel=10;
		}
		//when intensity is rising we randomly draw in some text
		if (colorDirection>0)
		{
			buffer.setFont(smallFont);
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("0", xRand, yRand);
			
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("1", xRand, yRand);
	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("1", xRand, yRand);
			
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));			
			buffer.drawString("0", xRand, yRand);
			
			buffer.setFont(smallFont);
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("0", xRand, yRand);
			
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("1", xRand, yRand);
	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));		
			buffer.drawString("1", xRand, yRand);
			
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));			
			buffer.drawString("0", xRand, yRand);
			
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.black);
			buffer.fillRect(xRand, yRand-10, 8,10);
			buffer.setColor (new Color(0,255-colorLevel+(int)(Math.random()*20-10),0));
			buffer.drawString(""+(char)((int)(Math.random()*256)), xRand, yRand);
			
			
		}
		//when the intensity is falling, we delite random patches of text
		else
		{
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.BLACK);
			buffer.fillRect(xRand, yRand-10, 8,10);	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.BLACK);
			buffer.fillRect(xRand, yRand-10, 8,10);	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.BLACK);
			buffer.fillRect(xRand, yRand-10, 8,10);	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.BLACK);
			buffer.fillRect(xRand, yRand-10, 8,10);	
			xRand=(int)(Math.random()*xRes/8)*8;
			yRand=(int)(Math.random()*yRes/10)*10;
			buffer.setColor(Color.BLACK);
			buffer.fillRect(xRand, yRand-10, 8,10);	
		}
		
		//after doing that, if we are loading resources, we write 'Initializing' across the screen
		if (loadingResources)
		{
		buffer.setColor(Color.BLACK);
		buffer.fillRect(520, 525, 232,32);
		buffer.setFont(largeFont);

		buffer.setColor (new Color(0,colorLevel,0));
		buffer.drawString("INITIALIZING", 520, 550);
		
		
		}//end if loading
		//if the game is over, we tell the people
		else if (youWin)
		{
			buffer.setColor(Color.BLACK);
			buffer.fillRect(160, 215, 532,32);
			buffer.setFont(largeFont);
			buffer.setColor (new Color(0,colorLevel,0));
			buffer.drawString("Congratulations! You've won!", 160, 240);
			
			buffer.setColor(Color.BLACK);
			buffer.fillRect(125, 250, 612,12);
			buffer.setFont(mediumFont);
			buffer.setColor (new Color(0,colorLevel,0));
			buffer.drawString("If I had an animation budget, I'd show you an ending cutscene.", 125, 260);
			
		}
		//if neither of these conditions are true, we draw the name of the game and
		//a scrolling bar of text
		else
		{
			buffer.setColor(Color.BLACK);
			buffer.fillRect(yRes+14, 20, 172,25);
			buffer.setFont(largeFont);
			buffer.setColor (Color.green);		
			buffer.drawString("GHOSTHACK", yRes+14, 42);
			
			buffer.setColor(Color.BLACK);
			buffer.fillRect(yRes+40, 50, 120,16);
			buffer.setFont(mediumFont);
			buffer.setColor(Color.GREEN);
			//only the first 12 characters of the text are displayed (the size of the box)
			buffer.drawString(wrapAroundText.substring(0, 12), yRes+40, 63);
			//this counter controls the speed that the text scroll, by only incrimenting
			//the text once every 10 iterations
			wrapAroundCounter++;
			if (wrapAroundCounter>10)
			{
			//set the first character to the last.
			wrapAroundText=wrapAroundText.substring(1)+wrapAroundText.charAt(0);
			wrapAroundCounter=0;
			}
		} //else if not loading resources

		if (userQuery)
		{
			//This draws information about the board, once it has been loaded
			if (board.doneLoading)
			{
				drawSidebarData();
			}
			
			//This draws a box with information to the user
			//IE "level loaded, click to continue"
			buffer.setColor(new Color(0,colorLevel,0));
		    buffer.fillRect(yRes+16, 516, 168,64);
			buffer.setColor(Color.BLACK);
			buffer.fillRect(yRes+18, 518, 164,60);
			buffer.setFont(mediumFont);
	
			buffer.setColor (new Color(0,colorLevel,0));
			buffer.drawString(queryText1, yRes+20, 536);
			buffer.drawString(queryText2, yRes+20, 552);
			buffer.drawString(queryText3, yRes+20, 568);
			
	
			
		}//end if user query
		
		
	}//end loadingText
	
	
	//the main function, this updates the internal logic of the game every 20th of a second
	public void updateGameLogic()
	{
		//if the board is "loading", we increase the loading timer (thus displaying more of
		//the board). When the board is fully viewable, we set the flags to await user input
		
		if (loadingBoard)
		{
			
				loadingTimer++;
				numRowsDisplayed=loadingTimer*40;
				if (numRowsDisplayed>board.boardPixelWidth)
				{
					//System.out.println("Done Playing Loadboard Animation");
					loadingBoard=false;
					numRowsDisplayed=0;
					loadingTimer=0;
					userQuery=true;
					
					wrapAroundText="Hack Halted. Awaiting User Input. Hack Halted. Please Restart. ";
					
					
				}
			
		}//if playing boardLoading animation
		//If the game is running, we update the boards logic
		else if (gameRunning)
		{
			
			//most of the mechanics are handled in moveball
			board.moveBall();
			
			//if after moving the balls the level is complete, we have some work to do
			if (board.levelComplete)
			{
				//if the level wasn't failed, we go on to the next levvel
				if (!board.levelFailed)
				{
					gameRunning=false;
					loadingBoard=true;
					currentLevel=currentLevel+1;
					wrapAroundText="Connecting. ";
					soundLibrary.playYouWin();
					//if the next level, you know, *exists*, we load it
					if (currentLevel<=maxLevel)
					{
					queryText1="//...";
					queryText2="Level "+currentLevel +" Loaded";
				    queryText3="Click To Proceed";
					board=new Board(gfxLibrary, soundLibrary, getCodeBase());
					
					board.loadBoard("level"+currentLevel+".txt");
					}
					//otherwise the user has won.
					else
					{
						loadingBoard=false;
						youWin=true;
					}
					
				}
				//else, if the user failed the level, reload it and try again 
				else 
				{					
					gameRunning=false;
					loadingBoard=true;					
					wrapAroundText="Connecting. ";
					soundLibrary.playYouLose();
					queryText1="//Hack Failed";
					queryText2="Reloading..." ;
					queryText3="Click To Proceed";
					board=new Board(gfxLibrary, soundLibrary, getCodeBase());
					board.loadBoard("level"+currentLevel+".txt");
				}
			}//end if level complete
		}//if level not complete
	}//end class updateGameLogic;#
	
	//makes a little chart with the rage levels of every ball, and draws
	//important information about the current level
	public void drawSidebarData()
	{
		buffer.setFont(mediumFont);
		//buffer.setColor(Color.RED);
		//buffer.fillRect(yRes+16, 80, 168,460);
		buffer.setColor(Color.GREEN);
		int scaling=80;
		
		//each of these draws a little black box (to obscure the background clutter)
		//and fills it with information about the game
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+26, 105+18*-1+scaling, 100+13,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString("   In Play: "+board.ballsInPlay, yRes+16,100+18*0+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+20, 105+18*1+scaling, 100+19,18);
		buffer.setColor(Color.GREEN);		
		buffer.drawString("  Complete: "+board.ballsFinished, yRes+16,100+18*2+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+26, 105+18*3+scaling, 100+13,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString("   Deleted: "+board.ballsDestroyed, yRes+16,100+18*4+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+36, 105+18*5+scaling, 103,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString("    Needed: "+board.minBallsForSuccess, yRes+16,100+18*6+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+9, 105+18*7+scaling, 100+30,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString(" Remaining: "+(board.minBallsForSuccess-board.ballsFinished), yRes+16,100+18*8+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+9, 105+18*9+scaling, 100+30,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString(" Tolerance: "+(board.maxBallsDestroyed-board.ballsDestroyed), yRes+16,100+18*10+scaling);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+16+48, 105+18*11+scaling, 94,18);
		buffer.setColor(Color.GREEN);
		buffer.drawString("     Time: "+board.timeRemaining/100, yRes+16,100+18*12+scaling);
		
		buffer.setColor(new Color(0,colorLevel,0));
	    buffer.fillRect(yRes+16, 516, 168,64);
		buffer.setColor(Color.BLACK);
		buffer.fillRect(yRes+18, 518, 164,60);
		
		int rectangleSpacing=164/(board.numBalls+1);
		Color barColor;
		float rageRatio;
		int lastX=yRes+18;
		int lastY=578;
		int thisX=0;
		int thisY=0;
		//here we draw a little graph of the rage levels for each
		//ball. It's more for visual effect than for game purpose
		for (int x=0; x<board.numBalls;x++)
		{
				
				rageRatio=(float)board.balls[x].rage/(float)board.explodeRage;
				if (rageRatio<0)
					rageRatio=0;
				if (rageRatio>1)
					rageRatio=1;
				
				barColor=new Color(rageRatio,1-rageRatio,0);
				buffer.setColor(barColor);		
				thisX=(int)(yRes+18+(x+1)*rectangleSpacing);
				thisY=(int)(518+60*(1-rageRatio));
				buffer.fillRect(thisX, thisY, 1,(int)(60*rageRatio));
				buffer.setColor(new Color(0,colorLevel,0));
				buffer.drawLine(lastX, lastY, thisX, thisY);
				lastX=thisX;
				lastY=thisY;
		}//end for x=0 to numballs
		buffer.drawLine(lastX, lastY,yRes+18+164 , 578);
	}//end drawBallRageBox;
	
	//update is called by the system inorder to redraw the applet. 
	//We use that to our advantage to update the buffer, then simply
	//draw that buffer onto the applets viewable space
	public void update (Graphics g)
	{
		//if we are loading the resources, we simply animate the 
		//background of the "Initializing" screen
		if (loadingResources)
		{
			
			doneRenderingFrame=false;		
			
			advanceBackgroundText();
			
			g.drawImage (bufferImage, 0, 0, this);
			doneRenderingFrame=true;
		}
		// if the board is int he middle of a loading animation
		else if (loadingBoard)
		{
			//System.out.println("Performing LoadingBoard animation");
			doneRenderingFrame=false;
			advanceBackgroundText();
			//do not render the board if the board is still loading.
			//since the board.loadBoard method involves IO to the disk
			//or server, and update is threaded, we must prevent
			//drawboard from being called until board is done loading
			if (board.doneLoading)			
			{
				//this is a messy line of code, but basically, we take a portion of the 
				//board, and draw it to the buffer. As time goes on, numRowsDisplayed increases
				//and more is displayed
				buffer.drawImage(board.drawBoard(),0,0,(int)(((double)(numRowsDisplayed*yRes))/((double)board.boardPixelWidth)),yRes,0,0,numRowsDisplayed,board.boardPixelWidth ,null); //send the buffer images G2D to the board renderer
			}
			
			g.drawImage(bufferImage, 0,0,this);
			doneRenderingFrame=true;
		}//end if loadingBoard
		//if we are in the middle of a user query
		else if (userQuery)
		{
			
			doneRenderingFrame=false;
			advanceBackgroundText();
			if (board.doneLoading)
			{
				buffer.drawImage(board.drawBoard(),0,0, yRes,yRes,null); //send the buffer images G2D to the board renderer
			}
			
			g.drawImage(bufferImage, 0,0,this);
			doneRenderingFrame=true;
		}//end if userQuery
		else if (gameRunning)
		{
			//System.out.println("Game Running");
			doneRenderingFrame=false;
			advanceBackgroundText();
			
			//draw the information contained in the sidebar
			drawSidebarData();
			
			if (board.doneLoading)
			{
				//draws and scales the board down to a square region (the board can be larger
				//or smaller than yRes, but will always be square)
				buffer.drawImage(board.drawBoard(),0,0, yRes,yRes,null); //send the buffer images G2D to the board renderer
			}
			
			g.drawImage(bufferImage, 0,0,this);
			doneRenderingFrame=true;
		}//end if game is running
		else if (youWin)
		{
			//System.out.println("Game Running");
			doneRenderingFrame=false;
			advanceBackgroundText();
			
			
			
			g.drawImage(bufferImage, 0,0,this);
			doneRenderingFrame=true;
		}//end if game is running
		
	}//end done rendering frame

	public String getAppletInfo() 
	{		
	    return "Ghosthack - A small browser game, by Graeme Hill";
	}
	
	//this is a private class which handles mouse interaction in the game
	private class MouseHandler implements MouseListener
	{
		private int initialMouseX; //initial location of a mouse click
		private int initialMouseY;
		private int actualMouseX; //actual location, on the board, measured in tiles
		private int actualMouseY;
		
		public void mouseClicked(MouseEvent e)
		{
			


		}
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}
		public void mouseReleased(MouseEvent e)
		{
			//System.out.println("Mouse Released");
			//when the mouse is released, we process the beast list (doing any swaps
			//or rotations
			if (gameRunning)
			{
			board.processPieceList();
			}
		}
		//single mouseclick
		public void mousePressed(MouseEvent e)
		{
			//a mouse click will do different things, depending on the status of the game
			//if the board is loading, we do nothing
			if (loadingBoard||loadingResources)
			{
				//do nothing
			}
			//if there is a user query up, we begin the level
			else if (userQuery)
			{
		    
		    userQuery=false;
		    gameRunning=true;
		    wrapAroundText="Hack Engaged. Bypassing Primary Network Firewall. Securing Data. Data Bore Initiated. ";
			}
			//if the game is running, we handle the click accordingly
			else if (gameRunning)
			{
			    if (e.getX()<yRes) //if click lands within board area
				{
				    initialMouseX = e.getX ();
				    initialMouseY = e.getY ();
				    actualMouseX=(initialMouseX*board.boardWidth)/yRes;
				    actualMouseY=(initialMouseY*board.boardWidth)/yRes;
				    //System.out.println("Click on Piece: "+actualMouseX+", "+actualMouseY);
				    board.dragEnabled=true;
				    board.addPiece(actualMouseX, actualMouseY);
					
				}
			}
		}//end mousePressed


	}//end private class mouseHandler;
	
	//Mousemotion is a seperate class. This simply handles the result of draggin the mouse
	private class MouseMotionHandler implements MouseMotionListener
	{
		private int initialMouseX;
		private int initialMouseY;
		private int actualMouseX;
		private int actualMouseY;
		
		public void mouseMoved(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}
	
		public void mouseDragged(MouseEvent e)
		{
			//
			if (gameRunning)
			{
				if (e.getX()<yRes) //if click lands within board area
				{
				    initialMouseX = e.getX ();
				    initialMouseY = e.getY ();
				    //based on the boards width, you can calculate what tile is being
				    //clicked on
				    actualMouseX=(initialMouseX*board.boardWidth)/yRes;
				    actualMouseY=(initialMouseY*board.boardWidth)/yRes;
				   
	
				    board.addPiece(actualMouseX, actualMouseY);
					
				}
			}
		}

	}//end private class mouseMotionHandler;
	
	
	
}//end class

