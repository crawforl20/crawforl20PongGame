package com.example.crawf.ponggame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that animates a version of the classic game called Pong
 *
 * @author Logan Crawford
 * @version March 2018
 */

public class PongAnimator extends AppCompatActivity implements Animator
{
    //instance variables
    private int count = 0; // counts the number of logical clock ticks
    private Random rand = new Random(); //random instance variable called rand
    private int red = rand.nextInt(255);
    private int green = rand.nextInt(255);
    private int blue = rand.nextInt(255);
    private int maxX = 2048; //x max for Nexus 9 screen
    private int maxY = 1536; //y max for Nexus 9 screen
    private int padWidth = 100; //paddle width
    private int padHeight = 10; //paddle height
    private float padLeft = 974.0f; //left of paddle
    private float padTop = 30.0f; //top of paddle
    private float padRight = 1074.0f; //right of paddle
    private float padBottom = 10.0f; //bottom of paddle
    private ArrayList<PointF> balls = new ArrayList<PointF>(); //create ArrayList of type PointF
    /**
     * External Citation
     *  Date: 17 March 2018
     *  Problem: creating an array of spot objects seemed too much of a hassle
     *  Resource:
     *      https://developer.android.com/reference/android/graphics/PointF.html
     *  Solution: created an array of PointF objects to represent the center of each spot.
     */
    int px = rand.nextInt(2000)+20; //random x position
    int py = rand.nextInt(1500)+10; //random y position
    protected float x; //x-coordinate
    protected float y; //y-coordinate
    protected float vx = rand.nextFloat()+1.0f; //velocity in the x direction
    protected float vy = rand.nextFloat()+1.0f; //velocity in the y direction
    protected float ax; //acceleration in the x direction
    protected float ay; //acceleration in the y direction
    private PointF velBall = new PointF(5, 5);
    private PointF curBall = new PointF(px, py); //create new ball at random x,y position

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval()
    {
        return 30;
    }

    /**
     * A white background
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(255, 255, 255);
    }

    /**
     * Action to perform on clock tick
     *
     * @param canvas the graphics object on which to draw
     */
    public void tick(Canvas canvas) {
        count++;
        int randomColor = Color.rgb(red, green, blue); //color instance variable of randomColor
        Paint ballPaint = new Paint(); //new Paint object used later for our Ball objects.
        ballPaint.setColor(randomColor); //set the paint of our ball to a random color.
        int padColor = Color.WHITE;
        Paint padPaint = new Paint(); //new paint object for our paddle
        padPaint.setColor(padColor); //sets the paddle to white
        canvas.drawRect(padLeft, padTop, padRight, padBottom, padPaint); //draws our paddle, stationary
        //when the ball collides with a wall
        if(curBall.x > maxX) //collision with right wall
        {
            vx = -vx; //reverse x-direction
        }
        else if(curBall.y > maxY) //collision with bottom wall
        {
            vy = -vy; //reverse y-direction
        }
        else if(curBall.x < 0) //ball exited the left of the screen
        {
            curBall.set(px, py); //reset the ball
        }
        else if(curBall.y < 0) //collision with top wall
        {
            vy = -vy; //reverse y-direction
        }
        //collision with the paddle
        if(curBall.x < padRight && curBall.x > padLeft && curBall.y == padTop )
        {
            vx = -vx; //reverse x-direction
            vy = -vy; //reverse y-direction
        }

        if(count == count%100) //every 100 ticks decrease the acceleration by 1;
        {
            ax += -.01;
            ay += -.01;
        }
        else if(count == count%50) //every 50 ticks increase acceleration by 1;
        {
            ax += .01;
            ay += .01;
        }
        //change the velocity based on the acceleration...
        //in the x direction
        vx += ax;
        //in the y direction
        vy += ay;
        //move the ball based on its velocity
        curBall.set(x += vx, y += vy); //set new coordinates for the ball.
        canvas.drawCircle(curBall.x, curBall.y, 20, ballPaint); //draw the new position of the ball
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event)
    {

    }

}
