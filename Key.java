/*
 * Sample code for CS 2610 Homework 1
 * 
 */

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JButton;

public class Key extends JButton{
	public ArrayList<ArrayList<Point>> LineList;
	public ArrayList<Point> PointList;

	public Key(String s) {
		// TODO Auto-generated constructor stub
		super(s);
		PointList = new ArrayList<Point>();
		LineList = new ArrayList<ArrayList<Point>>();
		this.setFont(new Font("Default",Font.PLAIN, 18));
	}
	
	@Override 
    public void paintComponent(Graphics g) { 
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Stroke tstroke = new BasicStroke(Float.parseFloat("3.0f"));
        g2d.setPaint(new Color(116,178,143)); 
        g2d.setStroke(tstroke);

        int length = LineList.size();
        
        if(length > 0){
        	for(int i = 0; i < length; i++){
        		 ArrayList<Point> line = LineList.get(i);
        		 
        		 for(int j=0; j < line.size()-1; j++)
        		 {
        			 Point point1 = line.get(j);
        			 Point point2 = line.get(j+1);
        			 g2d.drawLine(point1.x,point1.y,point2.x,point2.y);
        		 }
        	  }
         }
        
         length = PointList.size();
         if(length > 1){
        	 for(int i= 0; i < length -1; i++)
        	 {
        		 Point point1 = PointList.get(i);
        		 Point point2 = PointList.get(i+1);
        		 g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
        	 }
        }
    }
}
