package Sound;

//the sound library is similar to the graphics library (but for audio clips, obviously)
//it basically just loads 'em all, so they can be played back easily.

//playback of sound in appletes is, incidently, a touchy thing with a ton of little bugs
//especially where playback of multiple clips started at the same time is concerned.
//Ultimately java appletes could use a more robust implimentation for sound, but it's
//more trouble than it's worth for a game like mine, I'd rather spend the dev time elsewhere

import java.applet.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundLibrary extends Thread {
    
	Applet applet;
    URL codeBase;
    
    public Clip click;
    public Clip encryption_voice;
    public Clip encryption_sfx;
    public Clip ice_sfx;
    public Clip ice_voice;
    public Clip rebound;
    public Clip rotate;
    public Clip security;
    public Clip swap;
    public Clip youLoseA;
    public Clip youLoseB;
    public Clip youLoseC;
    public Clip youWinA;
    public Clip youWinB;
    public Clip youWinC;
    public Clip explosion;
    
    boolean doneLoading;
    
    public SoundLibrary(Applet applet,
                       URL codeBase)
    {
    	doneLoading=false;
        this.applet = applet;
        this.codeBase = codeBase;        
        setPriority(MIN_PRIORITY);
        start();
    }
    
    public boolean isDoneLoading()
	{
		return doneLoading;
	}

    public void run() 
    {
    	String filename;
    	//System.out.println("Loading Sounds");
    	doneLoading=false;
    	
    	try
    	{
    	    //this loadst loads a wav file, balances it automatically, and gives simple 
    		//controls (Play, stop and rewind) 
    	    AudioInputStream audiosource;
    	    DataLine.Info info;     	    
    	    URL url;
    	    
       
    	    url=new URL(codeBase,"Sound\\sounds\\youlosea.wav" );
            audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youLoseA = (Clip) AudioSystem.getLine (info);
    	    youLoseA.open (audiosource);
    	    
    	    url=new URL(codeBase,"Sound\\sounds\\explosion.wav" );
            audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    explosion = (Clip) AudioSystem.getLine (info);
    	    explosion.open (audiosource);
    	    
    	    url=new URL(codeBase,"sound\\sounds\\youloseb.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youLoseB = (Clip) AudioSystem.getLine (info);
    	    youLoseB.open (audiosource);
    	    url=new URL(codeBase,"sound\\sounds\\youlosec.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youLoseC = (Clip) AudioSystem.getLine (info);
    	    youLoseC.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\youwina.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youWinA = (Clip) AudioSystem.getLine (info);
    	    youWinA.open (audiosource);
    	    url=new URL(codeBase,"sound\\sounds\\youwinB.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youWinB = (Clip) AudioSystem.getLine (info);
    	    youWinB.open (audiosource);
    	    url=new URL(codeBase,"sound\\sounds\\youwinc.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    youWinC = (Clip) AudioSystem.getLine (info);
    	    youWinC.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\swap.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    swap = (Clip) AudioSystem.getLine (info);
    	    swap.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\rebound.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    rebound = (Clip) AudioSystem.getLine (info);
    	    rebound.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\rotate.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    rotate = (Clip) AudioSystem.getLine (info);
    	    rotate.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\click.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    click = (Clip) AudioSystem.getLine (info);
    	    click.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\ICE_voice.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    ice_voice = (Clip) AudioSystem.getLine (info);
    	    ice_voice.open (audiosource);
    	    url=new URL(codeBase,"sound\\sounds\\ice_sfx.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    ice_sfx = (Clip) AudioSystem.getLine (info);
    	    ice_sfx.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\encrypt_voice.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    encryption_voice = (Clip) AudioSystem.getLine (info);
    	    encryption_voice.open (audiosource);
    	    url=new URL(codeBase,"sound\\sounds\\encryption_sfx.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    encryption_sfx = (Clip) AudioSystem.getLine (info);
    	    encryption_sfx.open (audiosource);

    	    url=new URL(codeBase,"sound\\sounds\\security.wav");
    	    audiosource = AudioSystem.getAudioInputStream(url);
    	    info = new DataLine.Info (Clip.class, audiosource.getFormat ());
    	    security = (Clip) AudioSystem.getLine (info);
    	    security.open (audiosource);
    	} // end try

    	catch (UnsupportedAudioFileException e)
    	{
    	    System.out.println ("Unsuported Audio Format!");
    	}
    	catch (LineUnavailableException e)
    	{
    	    System.out.println ("Line Not Available!!");
    	}
    	catch (IOException e)
    	{
    	    System.out.println ("IO fault!");
    	}
        

       doneLoading=true;
       //System.out.println("Done Loading Sounds");
       
    }//end run

    //the various methods here simply rewind a clip and play it back.
    public void playYouLose ()
    {

	//plays a random "You lose" voice-over
	int randomspeech = (int) (3 * Math.random ());
	if (randomspeech == 0)
	{
	    youLoseA.stop (); //stop and rewind sound
	    youLoseA.setFramePosition (0);
	    youLoseA.start (); //play sound. Easy, huh?
	}
	else if (randomspeech == 1)
	{
	    youLoseB.stop ();
	    youLoseB.setFramePosition (0);
	    youLoseB.start ();
	}
	else if (randomspeech == 2)
	{
	    youLoseC.stop ();
	    youLoseC.setFramePosition (0);
	    youLoseC.start ();
	}
    } //end play you lose


    public void playYouWin ()
    {
	//plays a random "You win" voice-over
	int randomspeech = (int) (3 * Math.random ());
	if (randomspeech == 0)
	{
	    youWinA.stop ();
	    youWinA.setFramePosition (0);
	    youWinA.start ();
	}
	else if (randomspeech == 1)
	{
	    youWinB.stop ();
	    youWinB.setFramePosition (0);
	    youWinB.start ();
	}
	else if (randomspeech == 2)
	{
	    youWinC.stop ();
	    youWinC.setFramePosition (0);
	    youWinC.start ();
	}
    } //end playyouwin


    public void playSwap ()
    {
	swap.stop ();
	swap.setFramePosition (0);
	swap.start ();
    }


    public void playRebound ()
    {
    
	rebound.stop ();
	rebound.setFramePosition (0);
	rebound.start ();
    }


    public void playRotate ()
    {
	rotate.stop ();
	rotate.setFramePosition (0);
	rotate.start ();
    }


    public void playClick ()
    {
	click.stop ();
	click.setFramePosition (0);
	click.start ();
    }


    public void playEncrypt ()
    {

    	security.stop ();
    	security.setFramePosition (0);
    	security.start ();
    }
    
    public void playFinish ()
    {

	ice_sfx.stop ();
	ice_sfx.setFramePosition (0);


	ice_sfx.start ();
    }


    public void playICE ()
    {
	encryption_sfx.stop ();
	encryption_sfx.setFramePosition (0);

	
	encryption_sfx.start ();
    }


    public void playSecurity ()
    {
    	
	security.stop ();
	security.setFramePosition (0);
	security.start ();
    }
    
    public void playExplosion ()
    {
    	
    explosion.stop ();
	explosion.setFramePosition (0);
	explosion.start ();
    }
    
}//end class 
