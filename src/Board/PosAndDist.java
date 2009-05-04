package Board;
//Pos and dist used to simply contain a position and velocity for a single ball
//it now includes many more variables that relate to a balls status.


public class PosAndDist {

	public int tileX, tileY; //the position (in tiles) of the ball
    public float x, y, vx, vy, distance; //the position, unit vector of velocity, 
    									 //and actual velocity of the ball
    public boolean inPlay=true;	//this is false if the ball is destroyed or finishes or not yet
    							//in play
    public boolean awaitingEntrance=false; //true if the ball has still not spawned 
    int inPlayCounter=0; //time until ball enters play
    public boolean finished=false; //true if the ball made it through the maze
    public boolean decelerated=false; // true if the ball is temporarily slowed down
    public boolean destroyed=false; //true if the ball is destroyed
    public int decelerationCounter; //time until the ball returns to normal speed 
    public int rage; //rage this ball currently has (it will explode if this is too high)
    
    PosAndDist() {}

    PosAndDist( float x, float y, float distance ) {
	this.x = x;
	this.y = y;
	this.distance = distance;
    }

    PosAndDist( float x, float y, float vx, float vy, float distance ) {
	this.x = x;
	this.y = y;
	this.vx = vx;
	this.vy = vy;
	this.distance = distance;
    }
    public String toString()
    {
    	return ("x="+x+" y="+y+" vx="+vx+" vy="+vy+" distance="+distance);
    }
}
