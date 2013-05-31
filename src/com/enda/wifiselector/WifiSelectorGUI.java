package com.enda.wifiselector;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;

public class WifiSelectorGUI {

	Canvas can;
	Frame map;
	Panel controller;
	Panel top;
	ArrayList<TextField> circleList;
	ArrayList<Router> routeList;
	Button add;
	Button paintCircle;
	Button paintLine;
	Button clear;
	Button calculate;
	ArrayList<Integer> xPoints;
	ArrayList<Integer> yPoints;
	private static Color[] colors = { 
	    Color.green, Color.black, Color.blue, Color.red, 
	    Color.yellow, Color.orange, Color.cyan, Color.pink, 
	    Color.magenta};
	public WifiSelectorGUI(){
		xPoints = new ArrayList<Integer>();
	    yPoints = new ArrayList<Integer>();
		can = new Canvas();
		can.setSize(500,550);
		map =new Frame("Model");
		add = new Button("Add One Router");
		paintCircle = new Button("Show Router Location");
		paintLine = new Button("Show Walking Path");
		clear = new Button("Repaint");
		calculate = new Button("Calculation");
		controller = new Panel();
		top = new Panel();
		top.setLayout(new FlowLayout(FlowLayout.LEADING));
		top.add(add);
		top.add(paintCircle);
		top.add(paintLine);
		top.add(clear);
		top.add(calculate);
		circleList= new ArrayList<TextField>();
		routeList = new ArrayList<Router>();
		controller.setLayout(new BoxLayout(controller, BoxLayout.Y_AXIS));
		addOneCircle();
		addAction();
		
		map.add("North", top);
		map.add("West", can);
		map.add("East", controller);
		map.setLocation(200, 20);
		map.setSize(700,800);
		map.setVisible(true); 
	
		
	}
	public void addAction(){
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == add)
					addOneCircle();
				controller.doLayout();			
				}
			
		});
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == clear)
					can.repaint();	
					xPoints.clear();
					yPoints.clear();
					routeList.clear();
					circleList.clear();  
				}
			
		});
		map.addWindowListener(new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
			System.exit(0);
		    }
		});
		calculate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == calculate)
					for(int i =0; i< routeList.size(); i++){
						System.out.println("NO.");
						System.out.println("有效距离："+ accumulate(routeList.get(i)));
					}
					convertCanvasToImage(can);
				}
						
			
		});
		paintCircle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					if(e.getSource() == paintCircle)
					{
						Graphics g = can.getGraphics();
						int group = circleList.size()/3;
						for(int i=0;i < group;i++)
						{
							System.out.print(i);
							int index = i*3;
							int xPos = Integer.parseInt(circleList.get(index).getText());
							int yPos = Integer.parseInt(circleList.get(index+1).getText());
							int radius = Integer.parseInt(circleList.get(index+2).getText());
							g.setColor(colors[i]);
							g.drawOval((xPos-radius), (yPos-radius), 2*radius, 2*radius);
							routeList.add(new Router(xPos, yPos, radius));
							char ascll = (char)(65 + i);
							g.drawString(new String(new char[] { ascll }), xPos, yPos);
//							System.out.println((xPos-radius)+":"+(yPos+radius)+":"+(2*radius));
						}
//						draw2DCoordinate();
					}
					
				}
			
		});	
		paintLine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					if(e.getSource() == paintLine)
					{
					
						paint(can.getGraphics());
						
					}
					
				}
			
		});	
		can.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				xPoints.add(arg0.getX());
				yPoints.add(arg0.getY());
				can.getGraphics().fillOval(arg0.getX(), arg0.getY(), 5, 5);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
			}});
		
		
	}
	public void draw2DCoordinate(){
		Graphics g = can.getGraphics();
		g.setFont(new Font("TimesRoman", Font.PLAIN,18));
		g.drawString("O", 15, 25);
		g.drawString("x", 500, 15);
		g.drawString("y", 10, 500);
		int[] x1 = {10,500};
		int[] y1 = {10, 10};
		g.drawPolyline(x1, y1, 2);
		int[] x2 = {10, 10};
		int[] y2 = {10, 200};
		g.drawPolyline(x2, y2, 2);
		
	}
	public void addOneCircle(){
		TextField c1_x = new TextField("100",5);
		TextField c1_y = new TextField("100",5);
		TextField c1_r = new TextField("100",5);
		circleList.add(c1_x);
		circleList.add(c1_y);
		circleList.add(c1_r);
		int index = circleList.size()-3;
		controller.add(new Label("++++++++ NO. "+circleList.size()/3+"++++++++"));
		controller.add(new Label("X Position:"));
		controller.add(circleList.get(index));
		controller.add(new Label("Y Position:"));
		controller.add(circleList.get(index+1));
		controller.add(new Label("Range:"));  
		controller.add(circleList.get(index+2));
	}
	public void paint(Graphics g) {
		int size = xPoints.size();
        int[] xP = new int[size];
        int[] yP = new int[size];
       
        for(int i=0; i <size; ++i) {
          xP[i] = xPoints.get(i);
          yP[i] = yPoints.get(i);
        }
        g.drawPolyline(xP, yP, size);
        //showStatus(arraySize + " points");
      }
	public double accumulate(Router route){
		double distance = 0;
		for(int i =1; i < xPoints.size(); i++)
		{
			int routeX = route.xPos;
			int routeY = route.yPos;
			int radius = route.radius;
			int _preX = xPoints.get(i-1);
			int _preY = yPoints.get(i-1);
			System.out.println(" x1: "+_preX +", y1: "+ _preY);
			int _curX = xPoints.get(i);
			int _curY = yPoints.get(i);
			System.out.println(" x2: "+_curX +", y2: "+ _curY);
			double _pre = distance(routeX,_preX,routeY,_preY);
			double _cur = distance(routeX,_curX,routeY,_curY);
			if(_pre< radius && _cur < radius)
				distance += distance(_preX,_curX,_preY,_curY);
			else if (_pre > radius && _cur < radius){
				// y = ax +b
				double a,b = 0.000;
				if(_preX == _curX){
					a = 1;
					b =_preX;
				}
				else{
					a = (_preY - _curY)*1.000/(_preX - _curX)*1.000; //a = (y1 - y2)/(x1 - x2)
					b = _preY - a*_preX; // b = y-ax
				}
				System.out.println("a= "+ a + ", b= " + b + ")");
				double innerX = _preX;
				double innerY = innerX*a + b;
				while(distance(routeX,innerX,routeY,innerY) > radius){
					innerX += 0.5;
					innerY = innerX*a + b;
				}
				  distance += distance(_curX,innerX,_curY,innerY);
				  System.out.println("交汇点:  ("+ innerX + "," + innerY + ")");
				
			}
			else if (_pre < radius && _cur > radius){
				// y = ax +b
				double a,b = 0;
				if(_preX == _curX){
					a = 1;
					b =_preX;
				}
				else{
					a = (_preY - _curY)/(_preX - _curX);
					b = _preY - a*_preX;
				}
				double innerX = _curX;
				double innerY = 0;
				while(distance(routeX,innerX,routeY,innerY) > radius){
					innerX -= 0.5;
					innerY = innerX*a + b;
				}
				  distance += distance(_preX,innerX,_preY,innerY);
				  System.out.println("交汇点:  ("+ innerX + "," + innerY + ")");
			}
				
		}
		return distance;
			
	}
	public double distance(double x1,double x2,double y1, double y2){
		return  Math.sqrt(Math.pow(x1-x2, 2)+ Math.pow(y1-y2, 2));
	}
	private void convertCanvasToImage(Canvas cnvs) {
		Container pane = cnvs.getParent();
		BufferedImage img = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		pane.printAll(g2d);
		g2d.dispose();
		try {
		    ImageIO.write(img, "png", new File("F:/save.png"));
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WifiSelectorGUI();
	}

}
       ;    