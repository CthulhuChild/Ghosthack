//this is a graphics library. It pre-loads all graphics, tracks it's loading
//and stores it so anything with a reference to the GFXLibrary can access it.
//While java is pretty good at garbage collecting, this helps keep everything 
//in one place

package GFX;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.net.URL;
import java.awt.geom.AffineTransform;

public class GFXLibrary extends Thread 
{
	//these are the number of frames in the animated pieces files
	public int virusNumFrames=20;
	public int virusSize;
	
	//this is the size of a piece, in pixels
	public int pieceSize=90;
	
	
	//these are all the graphical elements the game uses
	public BufferedImage [] virus;
	public BufferedImage [] virusWarning1;
	public BufferedImage [] virusWarning2;
	public BufferedImage [] virusWarning3;
	public BufferedImage [] virusYellowAlert;
	public BufferedImage [] virusRedAlert;
    
	public BufferedImage [] explosion;
	public BufferedImage [] summon;
	
	
	public BufferedImage [] exit;

    public BufferedImage [] start;

    
    
    public BufferedImage [] fourWay;
    public BufferedImage [] elbow;
    public BufferedImage [] straight;
    
    public BufferedImage blocker;
    public BufferedImage ice;
    public BufferedImage encrypted;   
    public BufferedImage borderLeft;
    public BufferedImage borderRight;
    public BufferedImage reverse;
    public BufferedImage speedDown;
    public BufferedImage speedUp;
    public BufferedImage time;
    public BufferedImage title;

    
    boolean doneLoading;
    
    MediaTracker tracker;
    Applet applet;
    URL codeBase;
    
   
    
	public GFXLibrary(Applet applet, MediaTracker tracker, URL codeBase) 
	{
		//we create this as a thread so it can run seperately
		this.tracker=tracker;
		this.codeBase=codeBase;
		this.applet=applet;
		setPriority(MIN_PRIORITY);
        start();
        doneLoading=false;
		
	}
	
	
	public boolean isDoneLoading()
	{
		return doneLoading;
	}
	
	//the main thread of this program simply insantiates all the buffered images
	//then loads them
	public void run()
	{

		Graphics2D g2d;
		BufferedImage tempBuffer;
		
		//System.out.println("Loading Images");
		doneLoading=false;
		straight=new BufferedImage[5];
		elbow=new BufferedImage[5];
		fourWay=new BufferedImage[5];
		virus=new BufferedImage[20];
		virusWarning1=new BufferedImage[20];
		virusWarning2=new BufferedImage[20];
		virusWarning3=new BufferedImage[20];
		virusYellowAlert=new BufferedImage[20];
		virusRedAlert=new BufferedImage[20];
		
		explosion=new BufferedImage[12];
		summon=new BufferedImage[32];
		
		start=new BufferedImage[19];
		
		exit=new BufferedImage[19];
		
		String imagePath;
		URL url;
		try
		{	
			
			
			//provide the path for the file
			imagePath="GFX//elbow//ur_default.jpg";
			//create the URL
			url=new URL(codeBase, imagePath);
			//load the image
			elbow[0] =  ImageIO.read(url);
			//add to the tracker
			tracker.addImage(elbow[0], 320);
			
			imagePath="GFX//elbow//ur_select.jpg";
			url=new URL(codeBase, imagePath);			
			elbow[1] =  ImageIO.read(url);
			tracker.addImage(elbow[1], 321);
			
			imagePath="GFX//elbow//ur_ice.jpg";
			url=new URL(codeBase, imagePath);			
			elbow[2] =  ImageIO.read(url);
			tracker.addImage(elbow[2], 322);
			
			imagePath="GFX//elbow//ur_codered.jpg";
			url=new URL(codeBase, imagePath);			
			elbow[3] =  ImageIO.read(url);
			tracker.addImage(elbow[3], 323);
			
			imagePath="GFX//elbow//ur_codeyellow.jpg";
			url=new URL(codeBase, imagePath);			
			elbow[4] =  ImageIO.read(url);
			tracker.addImage(elbow[4], 324);	
			
			imagePath="GFX//fourWay//fourWay_default.jpg";
			url=new URL(codeBase, imagePath);			
			fourWay[0] =  ImageIO.read(url);
			tracker.addImage(fourWay[0], 310);
			
			imagePath="GFX//fourWay//fourWay_select.jpg";
			url=new URL(codeBase, imagePath);			
			fourWay[1] =  ImageIO.read(url);
			tracker.addImage(fourWay[1], 311);
			
			imagePath="GFX//fourWay//fourWay_ice.jpg";
			url=new URL(codeBase, imagePath);			
			fourWay[2] =  ImageIO.read(url);
			tracker.addImage(fourWay[2], 312);
			
			imagePath="GFX//fourWay//fourWay_codered.jpg";
			url=new URL(codeBase, imagePath);			
			fourWay[3] =  ImageIO.read(url);
			tracker.addImage(fourWay[3], 313);
			
			imagePath="GFX//fourway//fourway_codeyellow.jpg";
			url=new URL(codeBase, imagePath);			
			fourWay[4] =  ImageIO.read(url);
			tracker.addImage(fourWay[4], 314);			
			
			imagePath="GFX//Vertical//vt_default.jpg";
			url=new URL(codeBase, imagePath);			
			straight[0] =  ImageIO.read(url);
			tracker.addImage(straight[0], 300);
			
			imagePath="GFX//Vertical//vt_select.jpg";
			url=new URL(codeBase, imagePath);			
			straight[1] =  ImageIO.read(url);
			tracker.addImage(straight[1], 301);
			
			imagePath="GFX//Vertical//vt_ice.jpg";
			url=new URL(codeBase, imagePath);			
			straight[2] =  ImageIO.read(url);
			tracker.addImage(straight[2], 302);
			
			imagePath="GFX//Vertical//vt_codered.jpg";
			url=new URL(codeBase, imagePath);			
			straight[3] =  ImageIO.read(url);
			tracker.addImage(straight[3], 303);
			
			imagePath="GFX//Vertical//vt_codeyellow.jpg";
			url=new URL(codeBase, imagePath);			
			straight[4] =  ImageIO.read(url);
			tracker.addImage(straight[4], 304);
			
			
			
			
			for (int x=0; x<19; x++)
			{				
				imagePath="GFX//Startpiece//ep_default"+x+".jpg";
				url=new URL(codeBase, imagePath);			
				start[x] =  ImageIO.read(url);
				tracker.addImage(start[x], 200+x);

			}
			
			for (int x=0; x<19; x++)
			{				
				imagePath="GFX//EndPiece//sp_default"+x+".jpg";
				url=new URL(codeBase, imagePath);			
				exit[x] =  ImageIO.read(url);
				tracker.addImage(exit[x], 100+x);

				
			}
			
			
			//with the viruses and effects, I used sprite sheets
			//that contained multiple sprites on a single jpg, which
			//ultimately was much more conveninet to work with			
			int tempImageSize;
			imagePath="GFX//Virus//VirusDefault.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			//after the spritesheet has been loaded, we break it down into
			//individual frames
			for (int x=0; x<20; x++)
			{						
				virus[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virus[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			imagePath="GFX//Virus//VirusWarning1.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<20; x++)
			{						
				virusWarning1[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virusWarning1[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			imagePath="GFX//Virus//VirusWarning2.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<20; x++)
			{						
				virusWarning2[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virusWarning2[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			imagePath="GFX//Virus//VirusWarning3.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<20; x++)
			{						
				virusWarning3[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virusWarning3[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}

			imagePath="GFX//Virus//VirusDanger.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<20; x++)
			{						
				virusRedAlert[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virusRedAlert[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			
			imagePath="GFX//Virus//VirusSlowed.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<20; x++)
			{						
				virusYellowAlert[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)virusYellowAlert[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			imagePath="GFX//SFX//Explosion.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 200);
			tempImageSize=tempBuffer.getHeight();
			
			for (int x=0; x<12; x++)
			{						
				explosion[x] =  new BufferedImage(tempImageSize, tempImageSize, BufferedImage.BITMASK);
				g2d=(Graphics2D)explosion[x].getGraphics();
				g2d.drawImage(tempBuffer, 0, 0, tempImageSize-1, tempImageSize-1, (tempImageSize+1)*x, 0,(tempImageSize+1)*(x+1)-1, tempImageSize-1, null);
				
			}
			
			int frameCounter=0;
			imagePath="GFX//SFX//Spawning.jpg";
			url=new URL(codeBase, imagePath);			
			tempBuffer =  ImageIO.read(url);
			tracker.addImage(tempBuffer, 266);
			tempImageSize=tempBuffer.getHeight();
			
			//a nested loop is nececary for this sprite sheet
			//as the sprites are listed in rows of 6
			for (int y=0; y<6; y++)
			{						
				for (int x=0; (x<6)&&(frameCounter<32); x++)
				{						
					summon[frameCounter] =  new BufferedImage(96, 96, BufferedImage.BITMASK);
					g2d=(Graphics2D)summon[frameCounter].getGraphics();
					//System.out.println("X: "+x+" Y: "+y+" Frame Counter:"+frameCounter);
					g2d.drawImage(tempBuffer, 0, 0, 95, 95, 97*x+1, 97*y+1, 97*(x+1)-1, 97*(y+1)-1, null);
					frameCounter++;
					
				}
			}
			
			imagePath="GFX//Blocker//blocker.jpg";
			url=new URL(codeBase, imagePath);			
			blocker =  ImageIO.read(url);
			tracker.addImage(blocker, 10);
			
			imagePath="GFX//Ice//ice.jpg";
			url=new URL(codeBase, imagePath);			
			encrypted =  ImageIO.read(url);
			tracker.addImage(ice, 11);
			
			imagePath="GFX//Encrypted//encrypted.jpg";
			url=new URL(codeBase, imagePath);			
			ice  =  ImageIO.read(url);
			tracker.addImage(encrypted, 12);
			
			imagePath="GFX//GUI//border_left.jpg";
			url=new URL(codeBase, imagePath);			
			borderLeft =  ImageIO.read(url);
			tracker.addImage(borderLeft, 13);
			
			imagePath="GFX//GUI//border_right.jpg";
			url=new URL(codeBase, imagePath);			
			borderRight  =  ImageIO.read(url);
			tracker.addImage(borderRight, 14);
			
			imagePath="GFX//GUI//reverse.jpg";
			url=new URL(codeBase, imagePath);			
			reverse  =  ImageIO.read(url);
			tracker.addImage(reverse, 15);
			
			imagePath="GFX//GUI//SpeedDown.jpg";
			url=new URL(codeBase, imagePath);			
			speedDown  =  ImageIO.read(url);
			tracker.addImage(speedDown, 16);
			
			imagePath="GFX//GUI//SpeedUp.jpg";
			url=new URL(codeBase, imagePath);			
			speedUp  =  ImageIO.read(url);
			tracker.addImage(speedUp, 17);
			
			imagePath="GFX//GUI//Time.jpg";
			url=new URL(codeBase, imagePath);			
			time  =  ImageIO.read(url);
			tracker.addImage(time, 18);
			
			imagePath="GFX//GUI//Title.jpg";
			url=new URL(codeBase, imagePath);			
			title  =  ImageIO.read(url);
			tracker.addImage(title, 19);
			
			virusSize=virus[0].getHeight();
			pieceSize=encrypted.getHeight();
		}//end try
		catch (Exception e)
		{
			System.out.println(e.toString());
			//System.out.println(e.getLocalizedMessage());
			System.out.println("Failed to load images");			

		}//end catch
		
		//this loop constantly causes the thread to sleep while the files
		//are loaded int he background
		while (tracker.checkAll()==false)
		{
			try 
			{
				//System.out.println("Image library thread pausing");
	            sleep((int)(30));
	        } 
			catch (InterruptedException e) 
			{}
		}//end while tracker not done loading
		
		
		//once they are loaded, the images can be optimized
		optimizeAllImages();
		
        
		//with all that done, we can finally the library is done loading
		doneLoading=true;
		//System.out.println("Done Loading Images");
	}//end run
	
	//optimize all images simply calls optimize image on literally every
	//image int he library
	public void optimizeAllImages()
	{
		for (int x=0; x<5; x++)
		{
			elbow[x]=optimizeImage(elbow[x]);
			straight[x]=optimizeImage(straight[x]);
			fourWay[x]=optimizeImage(fourWay[x]);
		}
		for (int x=0; x<19; x++)
		{
			start[x]=optimizeImage(start[x]);

			exit[x]=optimizeImage(exit[x]);

			
		}
		for (int x=0; x<20; x++)
		{
			virus[x]=optimizeImage(virus[x]);
			virusWarning1[x]=optimizeImage(virusWarning1[x]);	
			virusWarning2[x]=optimizeImage(virusWarning2[x]);	
			virusWarning3[x]=optimizeImage(virusWarning3[x]);
			virusRedAlert[x]=optimizeImage(virusRedAlert[x]);
			virusYellowAlert[x]=optimizeImage(virusYellowAlert[x]);
		}
		for (int x=0; x<12; x++)
		{
			explosion[x]=optimizeImage(explosion[x]);
	
		}
		for (int x=0; x<32; x++)
		{
			summon[x]=optimizeImage(summon[x]);
	
		}
		
	    blocker=optimizeImage(blocker);
	    ice=optimizeImage(ice);
	    encrypted=optimizeImage(encrypted);   
	    borderLeft=optimizeImage(borderLeft);
	    borderRight=optimizeImage(borderRight);
	    reverse=optimizeImage(reverse);
	    speedDown=optimizeImage(speedDown);
	    speedUp=optimizeImage(speedUp);
	    time=optimizeImage(time);
	    title=optimizeImage(title);
		
		
	}//end optimize all images
	
		
	//optimize image takes an image and returns an "optimized" version.
	//in short, I want to make sure that regardless of the type I provide
	//the colorspace type of the resulting image is the correct kind 
	//(bitmask in this case). Also, all blacks below a certain threshold
	//need to be made transparent, to prevent clipping issues
	public static BufferedImage optimizeImage(BufferedImage img)
	{
		
		
		
		
		if (img!=null)
		{
			
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();				
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		boolean istransparent = img.getColorModel().hasAlpha();
		
		BufferedImage img2 = gc.createCompatibleImage(img.getWidth(), img.getHeight(),  Transparency.BITMASK );
		Graphics2D g = img2.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		Color rawColor;
		
		//here we replace any pixel who's RGB sums to less than 18 with 
		//a transparent pixel. 
		for (int x=0; x<img.getWidth();x++)
		{
			for (int y=0; y<img.getHeight();y++)
			{
			rawColor=new Color(img.getRGB(x, y));
			if ((rawColor.getRed()+rawColor.getGreen()+rawColor.getBlue())<18)
				{
					img2.setRGB(x, y, 0x00000);
				}//end if too dark
			}//end for y
		}//end for x
		return img2;
	   }
		else
			return null;
	}//end optimizeImage
	
	
}//end graphics
