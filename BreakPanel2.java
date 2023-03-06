//Created by Alan Chan
//Version 1.00
//Date: November 18, 2021

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;

public class BreakPanel2 extends JPanel implements ActionListener{
	//properties
	Timer thetimer = new Timer(1000/60,this);
	//basic location and gameplay variables
	int intX = 355;
	int intY = 570;
	//ball location
	int intBallX = 395;
	int intBallY = 300;
	//first brick location (the rest are setup in loop)
	int intRectX = 0;
	int intRectY = 30;
	//deflection of ball
	int intDefX = 0;
	int intDefY = 0;
	int intCount;
	//player has 3 lives
	int intHP = 3;
	//if player destroys all 30, they win
	int intNumBricks = 30;
	//invsible rectangle objects, if intersect detected, it is drawn over
	Rectangle[] rects = new Rectangle[31];
	//boolean for every brick to record if touched or not
	boolean blntouch[] = new boolean[31];
	
	//win and lose screen images
	BufferedImage winscreen = null;
	BufferedImage losesceen = null;
	BufferedImage breakoutball = null;
	BufferedImage breakoutpaddle = null;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,800,600);
		
		//rectangle ball object setup
		Rectangle ball = new Rectangle(intBallX, intBallY, 10, 10);
		g.setColor(Color.WHITE);
		
		//Message to drop the ball
		if(intDefY == 0 && intHP != 0){
			g.drawString("Right click to drop ball",350, 400);
		}
		
		g.drawString("Lives: "+intHP, 10, 20);
		
		//draw bricks
		//YELLOW row
		for(intCount = 1; intCount <= 10; intCount++){
			if(blntouch[intCount] == false){
				g.setColor(Color.YELLOW);
				g.fillRect((intCount-1)*80, 30, 80, 50);
			}
		}
		
		//GREEN row
		for(intCount = 11; intCount <= 20; intCount++){
			if(blntouch[intCount] == false){
				g.setColor(Color.GREEN);
				g.fillRect((intCount-11)*80, 80, 80, 50);
			}
		}
		
		//BLUE row
		for(intCount = 21; intCount <= 30; intCount++){
			if(blntouch[intCount] == false){
				g.setColor(Color.BLUE);
				g.fillRect((intCount-21)*80, 130, 80, 50);
			}
		}
		
		//collision detection and deflection section
		g.setColor(Color.BLACK);
		
		//first row
		for(intCount = 1; intCount <= 10; intCount++){
			if(ball.intersects(rects[intCount])){
				intNumBricks = intNumBricks - 1;
				if(intBallY > 30){
					intDefY = 5;
				}else{
					intDefY = -5;
				}
				blntouch[intCount] = true;
				rects[intCount] = new Rectangle(-10,10,0,0);
			}
		}
		//second row
		for(intCount = 11; intCount <= 20; intCount++){
			if(ball.intersects(rects[intCount])){
				intNumBricks = intNumBricks - 1;
				if(intBallY > 80){
					intDefY = 5;
				}else{
					intDefY = -5;
				}
				blntouch[intCount] = true;
				rects[intCount] = new Rectangle(-10,10,0,0);
			}
		}
		//third row
		for(intCount = 21; intCount <= 30; intCount++){
			if(ball.intersects(rects[intCount])){
				intNumBricks = intNumBricks - 1;
				if(intBallY > 130){
					intDefY = 5;
				}else{
					intDefY = -5;
				}
				blntouch[intCount] = true;
				rects[intCount] = new Rectangle(-10,10,0,0);
			}	
		}
	
		
		g.setColor(Color.WHITE);
		g.drawImage(breakoutpaddle, intX, intY, null);
	
		intBallX = intBallX + intDefX;
		intBallY = intBallY + intDefY;
		
		//Check for win or loss
		if(intHP == 0){
			intBallX = -10;
			intBallY = -10;
			g.drawString("YOU LOSE!!!", 350, 350);
			g.drawString("Left click to play again", 350, 380);
			g.drawImage(losesceen, 0, 0, null);
		}else if(intNumBricks == 0){
			intBallX = -10;
			intBallY = -10;
			g.drawString("YOU WIN!!!", 350, 350);
			g.drawString("Left click to play again", 350, 380);
			g.drawImage(winscreen, 0, 0, null);
		}
		
		//ball boundaries
		if(intBallX <= 0){
			intDefX = 3;
		}else if(intBallX >= 800){
			intDefX = -3;
		}
		if(intBallY <= 0){
			intDefY = 5;
		}
		//deflection on paddle
		if(intBallY == 570 && intBallX >= intX-3 && intBallX <= (intX+30)){
			intDefX = -2;
			intDefY = -5;
		}else if(intBallY == 570 && intBallX >= (intX+31) && intBallX <= intX+61){
			intDefX = 0;
			intDefY = -5;
		}else if(intBallY == 570 && intBallX >= (intX+62) && intBallX <= intX+93){
			intDefX = 2;
			intDefY = -5;
		}
		//draw ball
		g.drawImage(breakoutball,intBallX, intBallY, null);
		
		//if ball leaves, player loses life
		if(intBallY > 600){
			intBallX = 395;
			intBallY = 300;
			intDefX = 0;
			intDefY = 0;
			intHP = intHP - 1;
		}

		//boundary of playermovement
		if(intX <= 0){
			intX = 0;
		}else if(intX > 710){
			intX = 710;
		}
		g.setColor(Color.BLACK);
	}
	
	//constructor
	public BreakPanel2(){
		super();
		//load images
		try{
			winscreen = ImageIO.read(new File("breakoutwin.png"));
		}catch(IOException e){
			System.out.println("ERROR LOADING IMAGE FILE");
		}
		
		try{
			losesceen = ImageIO.read(new File("breakoutlose.png"));
		}catch(IOException e){
			System.out.println("ERROR LOADING IMAGE FILE");
		}
		
		try{
			breakoutball = ImageIO.read(new File("breakoutball.png"));
		}catch(IOException e){
			System.out.println("ERROR LOADING IMAGE FILE");
		}
		
		try{
			breakoutpaddle = ImageIO.read(new File("breakoutpaddle.png"));
		}catch(IOException e){
			System.out.println("ERROR LOADING IMAGE FILE");
		}
		
		//sets up all rectangles
		for(intCount = 1; intCount <= 30; intCount++){
			rects[intCount] = new Rectangle(intRectX, intRectY, 80, 50);
			blntouch[intCount] = false;
			intRectX = intRectX + 80;
			if(intRectX == 800){
				intRectX = 0;
				intRectY = intRectY + 50;
			}
		}
		this.setLayout(null);
		this.setPreferredSize(new Dimension(800, 600));
		thetimer.start();
	}
}
