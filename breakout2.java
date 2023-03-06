//Created by Alan Chan
//Version 1.00
//Date: November 18, 2021

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class breakout2 implements MouseListener, MouseMotionListener{
	//properties
	JFrame theframe = new JFrame("BREAKOUT");
	BreakPanel2 thepanel = new BreakPanel2();
	//mouse location variables
	int intMouseX;
	int intMouseY;
	
	//methods
	public void mouseExited(MouseEvent evt){
		
	}
	
	public void mouseEntered(MouseEvent evt){
		
	}
	
	public void mouseReleased(MouseEvent evt){
		
	}
	
	public void mousePressed(MouseEvent evt){
		int intButton;
		intButton = evt.getButton();
		//to drop the ball and start gameplay
		if(intButton == 3 && thepanel.intDefY == 0){
			thepanel.intDefY = 5;
		//if player wants to reset game after win or loss
		}else if (intButton == 1 && thepanel.intNumBricks == 0 || thepanel.intHP == 0){
			thepanel = new BreakPanel2();
			theframe.setContentPane(thepanel);
			theframe.pack();
		}
	}
	
	public void mouseClicked(MouseEvent evt){
		
	}
	
	public void mouseMoved(MouseEvent evt){
		
	}
	
	public void mouseDragged(MouseEvent evt){
		//moves the paddle according to mouse movement
		//paddle will only move if cursor is in a certain area close to the paddle
		intMouseX = evt.getX();
		intMouseY = evt.getY();
		if(intMouseX >= thepanel.intX && intMouseX <= (thepanel.intX+90)&& intMouseY <= 640 && intMouseY >=540){
			thepanel.intX = evt.getX() -45;
		}
	}
	
	//constructor
	public breakout2(){
		//adding MouseListeners
		theframe.addMouseListener(this);
		theframe.addMouseMotionListener(this);
		
		//frame setup
		theframe.setResizable(false);
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
	}
	
	//main method
	public static void main(String[] args){
		new breakout2();
	}
}
