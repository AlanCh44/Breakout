//Created by Alan Chan
//Version 1.00
//Date: November 18, 2021

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class MovementPanel extends JPanel implements ActionListener{
	//properties
	Timer thetimer = new Timer(1000/60, this);
	//new fonts
	Font stringFont = new Font("Sanserif", Font.PLAIN, 200);
	Font stringFontsmall = new Font("Sanserif", Font.PLAIN,15);
	//general positional and gameplay properties
	//ball location
	int intBallX=395;
	int intBallY=2;
	//deflection of ball
	int intBallDefX = 0;
	int intBallDefY = 0;
	//position of paddles
	int intP1PosX=0;
	int intP1PosY=300;
	int intP2PosX=790;
	int intP2PosY=300;
	//deflection off paddles
	int intP1Def;
	int intP2Def;
	int intP1Points;
	int intP2Points;
	//keeps track of previous X position
	int intPreX;

	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		//if a player scores, their opponent's side will become a brighter shade of red
		if(intP1Points == 1){
			g.setColor(new Color(30,0,0));
			g.fillRect(405,0,395,600);
		}else if(intP1Points == 2){
			g.setColor(new Color(70,0,0));
			g.fillRect(405,0,395,600);
		}else if(intP1Points == 3){
			g.setColor(new Color(150,0,0));
			g.fillRect(405,0,395,600);
		}else if(intP1Points ==4){
			g.setColor(new Color(255,0,0));
			g.fillRect(405,0,395,600);
		}
		if(intP2Points == 1){
			g.setColor(new Color(30,0,0));
			g.fillRect(0,0,395,600);
		}else if(intP2Points == 2){
			g.setColor(new Color(70,0,0));
			g.fillRect(0,0,395,600);
		}else if(intP2Points == 3){
			g.setColor(new Color(150,0,0));
			g.fillRect(0,0,395,600);
		}else if(intP2Points ==4){
			g.setColor(new Color(255,0,0));
			g.fillRect(0,0,395,600);
		}
		
		g.setColor(Color.WHITE);
		//deflection movement
		intP1PosY = intP1PosY+intP1Def;
		intP2PosY = intP2PosY+intP2Def;
		//paddles
		g.fillRect(intP1PosX, intP1PosY, 10, 70);
		g.fillRect(intP2PosX, intP2PosY, 10, 70);
		//ball movement
		intBallX = intBallX + intBallDefX;
		intBallY = intBallY + intBallDefY;
		//draw ball
		g.fillOval(intBallX, intBallY, 10, 10);
		//middle line
		g.fillRect(395,0,10,600);
		
		g.setFont(stringFontsmall);
		if(intBallX == 395 && intBallY == 2){
			g.drawString("Press space to serve", 30, 50);
		}
		
		//bounce off paddles
		if(intBallX == 10 && intBallY >= intP1PosY && intBallY <= (intP1PosY+70)){
			intBallDefX = 5;
		}else if(intBallX == 790 && intBallY >= intP2PosY && intBallY <= (intP2PosY+70)){
			intBallDefX = -5;
		}
		//bounce of walls
		if(intBallY <= 0){
			intBallDefY = 5;
		}else if(intBallY >= 590){
			intBallDefY = -5;
		}
		g.setFont(stringFont);
		g.drawString(intP1Points+"", 270, 150);
		g.drawString(intP2Points+"", 420, 150);
		g.setFont(stringFontsmall);
	
		//adding points after winning round
		if(intBallX < 0){
			intPreX = intBallX;
			intP2Points = intP2Points + 1;
			intBallX = 395;
			intBallY = 4;
			intBallDefX = 0;
			intBallDefY = 0;
		}else if(intBallX > 800){
			intPreX = intBallX;
			intP1Points = intP1Points + 1;
			intBallX = 395;
			intBallY = 4;
			intBallDefX = 0;
			intBallDefY = 0;
		}
		
		//press spacebar to serve message
		if(intBallX == 395 && intBallY ==4 && intPreX > 800){
			g.drawString("Press spacebar to serve",620, 50);
		}else if(intBallX == 395 && intBallY ==4 && intPreX < 0){
			g.drawString("Press spacebar to serve",30, 50);
		}
		
		//Setting boundaries for player movement
		if(intP1PosY <= 0){
			intP1PosY = 0;
		}else if(intP1PosY + 70 >=600){
			intP1PosY = 530;
		}
		if(intP2PosY <= 0){
			intP2PosY = 0;
		}else if(intP2PosY + 70 >=600){
			intP2PosY = 530;
		}
		
		//if a player wins
		if(intP1Points == 5){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,600);
			g.setColor(Color.WHITE);
			g.drawString("Player 1 wins", 300, 200);
			g.drawString("Press enter key to play again!", 300, 220);
		}else if(intP2Points == 5){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,600);
			g.setColor(Color.WHITE);
			g.drawString("Player 2 wins", 300, 200);
			g.drawString("Press enter key to play again!", 300, 220);
		}
			
		//joke
		if(intP1Points == 4 && intP2Points == 0){
			g.drawString("Do you need an easy mode?",500, 300);
		}else if(intP2Points == 4 && intP1Points == 0){
			g.drawString("Do you need an easy mode?",100, 300);
		}
	}
	
	//constructor
	public MovementPanel(){
		super();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(800,600));
		
		thetimer.start();
	}
}
