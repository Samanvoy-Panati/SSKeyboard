/*
 * Sample code for CS 2610 Homework 1
 * 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Keyboard{
	
	JFrame window;
	Key[] keys;
	JPanel board; //for loading buttons
	JTextField input;
	JLabel outputdisplay;
	JTextArea comment;
	JButton prediction1;
	JButton prediction2;
	JButton prediction3;
	JButton prediction4;
	JButton prediction5;
	ArrayList<JButton> predictions_array= new ArrayList<JButton>();
	Container panel;
	MouseListener mouselistener;
	private static boolean USE_CROSS_PLATFORM_UI = true;
	Dictionary dict;
	public Keyboard(Dictionary dict) throws FileNotFoundException{
		this.dict = dict;
		if(USE_CROSS_PLATFORM_UI) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mouselistener = new MouseListener();
		window = new JFrame("keyboard");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600,400);
		
		//set the keyboard pad layout
		board = new JPanel();
		Border border = BorderFactory.createEmptyBorder(0,10,20,10);
		board.setBorder(border);
		//information display
		
		input = new JTextField("THE NEW SWYPE KEYBOARD");
		input.setFont(new Font("Serif", Font.PLAIN, 16));
		input.setBackground(new Color(237,237,237));
		input.setEditable(true);
		Border title = BorderFactory.createTitledBorder("Input: ");
		Border bevel = BorderFactory.createLoweredBevelBorder();
		Border border1 = BorderFactory.createEmptyBorder(0,10,5,10);
		Border border2 = BorderFactory.createEmptyBorder(10,10,5,10);
		Border border3 = BorderFactory.createCompoundBorder(border2, bevel);
		Border border4 = BorderFactory.createCompoundBorder(border3, title);
		input.setBorder(BorderFactory.createCompoundBorder(border4, border1));
		
		//modify input sample border
		outputdisplay = new JLabel("_");
		Border title2 = BorderFactory.createTitledBorder("Output: ");
		Border border5 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border6 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border7 = BorderFactory.createCompoundBorder(title2,border6);
		outputdisplay.setBorder(BorderFactory.createCompoundBorder(border5,border7));
		outputdisplay.setForeground(Color.blue);
		outputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));
		
		
		board.setLayout(new GridBagLayout());

		//added for to-be-predicted buttons
		prediction1 = new JButton();
		prediction2 = new JButton();
		prediction3 = new JButton();
		prediction4 = new JButton();
		prediction5 = new JButton();
		prediction1.setVisible(false);
		prediction2.setVisible(false);
		prediction3.setVisible(false);
		prediction4.setVisible(false);
		prediction5.setVisible(false);
		addKey(board,prediction1,0,0,2,1);
		addKey(board,prediction2,2,0,2,1);
		addKey(board,prediction3,4,0,2,1);
		addKey(board,prediction4,6,0,2,1);
		addKey(board,prediction5,8,0,2,1);
		predictions_array.add(prediction1);
		predictions_array.add(prediction2);
		predictions_array.add(prediction3);
		predictions_array.add(prediction4);
		predictions_array.add(prediction5);
		
		//set the buttons:
		int[] keyNum =  {10,9,7};
		keys = new Key[27];
		//keys = new Key[30];
		String[] keyLabels ={"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"}; //change to keyboard setting
		
		//first line
		for (int i = 0; i < keyNum[0]; i++){ //first line of keys
			String label = keyLabels[0].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(false);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+1,1,1,1);
		}
		
		final JButton STATS = new JButton("STATS");
		Color clr = new Color(100,100,200);
		STATS.setBackground(clr);
		addKey(board,STATS,10+1,1,2,1);
		
		//second line
		for (int i = 0; i < keyNum[1]; i++){ //second line of keys
			String label = keyLabels[1].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(false);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+1,2,1,1);
		}
		
		final JButton CLEAR_WORD = new JButton("CLEAR_WORD");
		clr = new Color(100,200,100);
		CLEAR_WORD.setBackground(clr);
		addKey(board,CLEAR_WORD,9+1,2,3,1);
		
		for (int i = 0;i< keyNum[2]; i++){ //third line of keys
			String label = keyLabels[2].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(false);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+2,3,1,1);
		}
		
		final JButton BACK = new JButton("BACKSPACE");
		clr = new Color(200,100,100);
		BACK.setBackground(clr);
		addKey(board,BACK,7+2,3,3,1);		

		//set the space button
		keys[26] = new Key(" ");
		keys[26].setName(" ");
		keys[26].setFocusPainted(false);
		keys[26].addMouseListener(mouselistener);
		keys[26].addMouseMotionListener(mouselistener);
		addKey(board,keys[26],2,4,7,1);
		
		final JButton CLEAR_TEXT = new JButton("CLEAR_TEXT");
		clr = new Color(250,250,100);
		CLEAR_TEXT.setBackground(clr);
		addKey(board,CLEAR_TEXT,9,4,3,1);
		
		comment = new JTextArea();
		Border title3 = BorderFactory.createTitledBorder("Comment: ");
		Border border8 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border9 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border10 = BorderFactory.createCompoundBorder(title3,border9);
		comment.setBorder(BorderFactory.createCompoundBorder(border8,border10));
		comment.setForeground(Color.blue);
		comment.setFont(new Font("Serif", Font.PLAIN, 16));
		
		
		
		panel = window.getContentPane();
		//use gridBag layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor= GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(input,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(outputdisplay,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 4;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,10,10);
		panel.add(board,c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridheight = 5;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(comment,c);
		
		window.setPreferredSize(new Dimension(1000, 800));
		window.pack();// adjust the window size
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		
		BACK.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String oldString = outputdisplay.getText();
				String newString;
				if(oldString.length()==1){
					comment.setText("There are no characters to delete");
				}
				else{
					newString = oldString.substring(0, oldString.length()-2)+ "_";
					outputdisplay.setText(newString);
				}
				
			}
		});
		
		CLEAR_WORD.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String oldString = outputdisplay.getText();
				//latest space found
				int last_index = oldString.lastIndexOf(" ");
				int second_last_index;
				//int display_length = oldString.length();
				String newString = "";
				//no space yet
				if(last_index==-1){
					//If there is not string...do nothing
					if(oldString.length()==1){
						comment.setText("There is no word to clear");
						newString = "_";
					}
					//If there is a word or letter, delete that
					else{
						newString = "_";
					}
				}
				else {
					//second latest space
					second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
					if (second_last_index==-1){
					newString = "_";
					}
					else{
						newString = oldString.substring(0, second_last_index)+" _";
					}
				}
				outputdisplay.setText(newString);
			}
			
			
		});
		
		CLEAR_TEXT.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//String oldString = outputdisplay.getText();
				String newString = "_";
				outputdisplay.setText(newString);
			}
		});

		STATS.addMouseListener(new MouseListener() {
			
			public int levenshteinDistance(String output){
				String given_input;
				int distance = 0;
				//System.out.println("Enter the input sentence");
				given_input = input.getText();
				//Scanner sc = new Scanner(System.in);
				//intended_input = sc.nextLine();
				int x = given_input.length();
				int y = output.length()-2;
				System.out.println("ACTUAL_SENTENCE: "+output.substring(0, output.length()-2));
				System.out.println("GIVEN_SENTENCE: "+given_input);
				
				if(output.length()==0){
					distance = given_input.length();
				}
				else if(given_input.length()==0){
					distance = output.length();
				}
				else{
					int[][] matrix = new int[x+1][y+1];
					
					for(int i=0;i<=x;i++){
						matrix[i][0] = i;
					}
					for(int i=0;i<=y;i++){
						matrix[0][i] = i;
					}
					for(int i=1;i<=x;i++){
						for(int j=1;j<=y;j++){
							if(given_input.charAt(i-1) == output.charAt(j-1)){
								matrix[i][j] = matrix[i-1][j-1];
							}
							else{
								matrix[i][j] = Math.min( Math.min(matrix[i-1][j-1]+1, matrix[i-1][j]+1), matrix[i][j-1]+1);
							}
						}
					}
					
					System.out.println("Levenshtein matrix: ");
					for(int i=0;i<=x;i++){
						for(int j=0;j<=y;j++){
							System.out.print(matrix[i][j]+"  ");
						}
						System.out.println();
					}
					
					distance = matrix[x][y];
					System.out.println("Distance: "+distance);
					traceback(matrix,x,y, given_input, output);
				}
				//sc.close();
				return distance;
				
			}
			
			public void traceback(int[][] matrix, int x, int y, String given_input, String output){
				
				int[] oper_input = new int[x];
				int[] oper_output = new int[y];
				int insertions = 0;
				int deletions = 0;
				int replacements = 0;
				//0 for match		1 for replacement
				//2 for insertion	3 for deletion
				int i = x;
				int j = y;
				while(i!=0 || j!=0){
						//If there is a match
						//System.out.println("i: "+i+"  j:"+j);
						if(i!=0 && j!=0 && given_input.charAt(i-1) == output.charAt(j-1)){
							//System.out.println("matched");
							oper_input[i-1] = 0;
							oper_output[j-1] = 0;
							i--;
							j--;
						}
						//If there is a replacement
						else if(i!=0 && j!=0 && matrix[i][j] == (matrix[i-1][j-1]+1)){
							//System.out.println("replaced");
							oper_input[i-1] = 1;
							oper_output[j-1] = 1;
							i--;
							j--;
							replacements++;
						}
						//If there is an insertion
						else if(i!=0 && matrix[i][j] == (matrix[i-1][j]+1)){
							//System.out.println("inserted");
							oper_input[i-1] = 2;
							i--;
							insertions++;
						}
						//If there is a deletion
						else if(j!=0 && matrix[i][j] == (matrix[i][j-1]+1)){
							//System.out.println("deleted");
							oper_output[j-1] = 3;
							j--;
							deletions++;
						}
					
				}
				/*
				System.out.println("Input matches ");
				for(int a=0;a<x;a++){
					System.out.print(oper_input[a]+"  ");
				}
				System.out.println();
				System.out.println("Output matches ");
				for(int a=0;a<y;a++){
					System.out.print(oper_output[a]+"  ");
				}
				System.out.println();
				*/
				//to show the steps performed in changing the output to input
				//System.out.println("x: "+x+"   y:"+y);
				System.out.println("Deletions Required: "+deletions);
				System.out.println("Insertions Required: "+insertions);
				System.out.println("Replacements Required: "+replacements);

				String[] deletion = new String[y-deletions];
				String[] insertion = new String[y-deletions+insertions];
				String[] replacement = new String[y-deletions+insertions];
				String steps = "";
				steps = steps+"Intended Input:\t"+given_input+"\n"+"User input:\t\t"+output.substring(0,output.length()-2)+"\n";
				//for deletions
				int d=0;
				for(int a=0;a<y;a++){
					if(oper_output[a]==3)
						continue;
					else{
						deletion[d] = output.substring(a,a+1);
						d++;
					}
				}
				//for insertions
				int e=0;
				for(int a=0;a<(y-deletions);a++){
					insertion[a] = deletion[a];
				}
				for(int a=(y-deletions);a<(y-deletions+insertions);a++){
					insertion[a] = "";
				}
				
				//System.out.println("y-deletions+insertions: "+(y-deletions+insertions));
				//System.out.println("y-deletions-1: "+(y-deletions-1));
				
				for(int b=0;b<(y-deletions+insertions);b++){
					//The length of the intended input and (y-deletions+insertions) will be same 
					//if that is the to-be-inserted character
					if(oper_input[b]==2){
						for(e=(y-deletions-1);e>=b;e--){
							//System.out.println("e: "+e);
							insertion[e+1] = insertion[e];
						}
						//System.out.println("inserted character : "+given_input.substring(b,b+1));
						insertion[e+1] = given_input.substring(b,b+1);
					}
				}
				//for replacements
				for(int c=0;c<(y-deletions+insertions);c++){
					replacement[c] = insertion[c];
				}
				for(int c=0;c<(y-deletions+insertions);c++){
					if(oper_input[c] == 1){
						replacement[c] = given_input.substring(c,c+1);
					}
				}
				steps = steps+"After deletion: \t";
				for(int c=0;c<y-deletions;c++){
					//System.out.println(" "+deletion[c]);
					steps = steps+deletion[c];
				}
				steps = steps+"\n"+"After Insertion: \t";
				for(int c=0;c<(y-deletions+insertions);c++){
					steps = steps+insertion[c];
				}
				steps = steps+"\n"+"After Replacement: \t";
				for(int c=0;c<(y-deletions+insertions);c++){
					steps = steps+replacement[c];
				}
				
				steps = steps+"\n";
				System.out.println("STEPS:"+steps);
				comment.setText(steps);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int distance = levenshteinDistance(outputdisplay.getText());
				System.out.println("LEVENSHTEIN DISTANCE: "+distance);
			}
		});
		
		
		prediction1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JButton theEventer = (JButton) e.getSource();
				String oldString = outputdisplay.getText();
				int last_index = oldString.lastIndexOf(" ");
				String newString = "";
				int second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
				//System.out.println("******"+second_last_index);
				//last_index == (display_length-2)
				if (second_last_index==-1){
					newString = theEventer.getText();
				}
				else{
					
					newString = oldString.substring(0, second_last_index)+ " "+theEventer.getText();
				}
				outputdisplay.setText(newString+" "+"_");
				prediction1.setVisible(false);
				prediction2.setVisible(false);
				prediction3.setVisible(false);
				prediction4.setVisible(false);
				prediction5.setVisible(false);
			}
		});

		prediction2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JButton theEventer = (JButton) e.getSource();
				String oldString = outputdisplay.getText();
				int last_index = oldString.lastIndexOf(" ");
				String newString = "";
				int second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
				//System.out.println("******"+second_last_index);
				//last_index == (display_length-2)
				if (second_last_index==-1){
					newString = theEventer.getText();
				}
				else{
					
					newString = oldString.substring(0, second_last_index)+ " "+theEventer.getText();
				}
				outputdisplay.setText(newString+" "+"_");
				prediction1.setVisible(false);
				prediction2.setVisible(false);
				prediction3.setVisible(false);
				prediction4.setVisible(false);
				prediction5.setVisible(false);
			}
		});
		
		prediction3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JButton theEventer = (JButton) e.getSource();
				String oldString = outputdisplay.getText();
				int last_index = oldString.lastIndexOf(" ");
				String newString = "";
				int second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
				//System.out.println("******"+second_last_index);
				//last_index == (display_length-2)
				if (second_last_index==-1){
					newString = theEventer.getText();
				}
				else{
					
					newString = oldString.substring(0, second_last_index)+ " "+theEventer.getText();
				}
				outputdisplay.setText(newString+" "+"_");
				prediction1.setVisible(false);
				prediction2.setVisible(false);
				prediction3.setVisible(false);
				prediction4.setVisible(false);
				prediction5.setVisible(false);
			}
		});
		
		prediction4.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JButton theEventer = (JButton) e.getSource();
				String oldString = outputdisplay.getText();
				int last_index = oldString.lastIndexOf(" ");
				String newString = "";
				int second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
				//System.out.println("******"+second_last_index);
				//last_index == (display_length-2)
				if (second_last_index==-1){
					newString = theEventer.getText();
				}
				else{
					
					newString = oldString.substring(0, second_last_index)+ " "+theEventer.getText();
				}
				outputdisplay.setText(newString+" "+"_");
				prediction1.setVisible(false);
				prediction2.setVisible(false);
				prediction3.setVisible(false);
				prediction4.setVisible(false);
				prediction5.setVisible(false);
			}
		});
		
		prediction5.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JButton theEventer = (JButton) e.getSource();
				String oldString = outputdisplay.getText();
				int last_index = oldString.lastIndexOf(" ");
				String newString = "";
				int second_last_index = oldString.substring(0,last_index-2).lastIndexOf(" ");
				//System.out.println("******"+second_last_index);
				//last_index == (display_length-2)
				if (second_last_index==-1){
					newString = theEventer.getText();
				}
				else{
					
					newString = oldString.substring(0, second_last_index)+ " "+theEventer.getText();
				}
				outputdisplay.setText(newString+" "+"_");
				prediction1.setVisible(false);
				prediction2.setVisible(false);
				prediction3.setVisible(false);
				prediction4.setVisible(false);
				prediction5.setVisible(false);
			}
		});
	}	
	
	public void addKey(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = 30;
		c.ipadx = 30;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		container.add(component, c);
	}
	
	
	
	public void playSound(String soundName)
    {
      try 
      {
       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
       Clip clip = AudioSystem.getClip();
       clip.open(audioInputStream);
       clip.start();
      }
      catch(Exception ex)
      {
        System.out.println("Error with playing sound.");
        ex.printStackTrace( );
      }
    }
	
	
	public ArrayList<String> predictWord(String trace_word){
		//predicted_words contain all the words that can be possible with that swyping pattern
		ArrayList<String> predicted_words = new ArrayList<String>(); 
		ArrayList<String> unsorted_words = new ArrayList<String>();
		ArrayList<String> dup_unsorted_words = new ArrayList<String>();
		int trace_length = trace_word.length();
		String first = trace_word.substring(0, 1);
		String last = trace_word.substring(trace_length-1, trace_length);
		//System.out.println("Starting letter: "+first);
		//System.out.println("Ending letter: "+last);
		ArrayList<String> wtable = dict.wtable;
		ArrayList<Integer> ftable = dict.ftable;
		int table_size = wtable.size();
		//goes through the table with the words
		for(int i=0;i<table_size;i++){
			String word = wtable.get(i);
			int word_length = word.length();
			//checks whether the starting and ending letters same or not
			if( first.equals(word.substring(0, 1)) && last.equals(word.substring(word_length-1, word_length)) ){
				int count = 0;
				//goes through all the words in the dictionary
				for(int j=1;j<word_length-1;j++){
					//goes through the trace_word's letters
					for(int k=1;k<trace_length;k++){
						//checks whether the words letters are present in the trace_word
						if(word.substring(j,j+1).equals(trace_word.substring(k,k+1))){
							k = trace_length-1;
							count++;
						}
					}
				}
				//checks if all letters in the word between first and end are present in trace 
				if(count == word_length-2){
					unsorted_words.add(word);
					dup_unsorted_words.add(word);
				}
			}
			else
				continue;
		}
		
		
		String swap="";
		for(int i=0;i<unsorted_words.size()-1;i++){
			for(int j=0;j<(unsorted_words.size()-i-1);j++){
				int freq1 = ftable.get(wtable.indexOf(unsorted_words.get(j)));
				int freq2 = ftable.get(wtable.indexOf(unsorted_words.get(j+1)));
				//System.out.println("Word is:"+unsorted_words.get(j)+" freq is :"+freq1);
				//System.out.println("Word is:"+unsorted_words.get(j+1)+" freq is :"+freq2);

				if(freq1<freq2){
					swap = unsorted_words.get(j);
					unsorted_words.set(j,unsorted_words.get(j+1));
					unsorted_words.set(j+1, swap);
				}
			}
		}
		
		predicted_words = unsorted_words;
		//System.out.println("#####");
		//for(int i =0;i<predicted_words.size();i++){
		//	System.out.println("****predicted list: "+predicted_words.get(i));
		//}
		
		return predicted_words;
	}
	
	public void showPredictions(ArrayList<String> predicted_words){
		int num_of_words = predicted_words.size();
		System.out.println("Number of words predicted: "+num_of_words);
		int min = Math.min(num_of_words-1,5);
		for(int i=0;i<min;i++){
			predictions_array.get(i).setVisible(true);
			predictions_array.get(i).setText(predicted_words.get(i+1));
		}
	
	}
	
	
	
	class MouseListener extends MouseAdapter implements MouseMotionListener{
		boolean tracing;			// whether the input method is button clicking or tracing
		ArrayList<Key> tracelist;	// a list to store all buttons on the trace
		Key curKey;
		
		MouseListener() throws FileNotFoundException{
			super();
			tracing = false;
			tracelist = new ArrayList<Key>();
			curKey = new Key("");
		}
		
		
		private void updateOutput (Key theEventer){
			String theChar = theEventer.getText();
			String oldString = outputdisplay.getText();
			String newString = oldString.substring(0, oldString.length()-1) + theChar + "_";
			outputdisplay.setText(newString);
		}
		
		
		private void updateDisplay(String output){
			String oldString = outputdisplay.getText();
			String newString = oldString.substring(0, oldString.length()-1) + output + "_";
			outputdisplay.setText(newString);
		}
		
		public void setColor(Key theEventer,int r, int g, int b){
			Color c = new Color(r,g,b);
			theEventer.setBackground(c);
		}
		
		private void recoverState(){
			//when mouse is released, tracing is ended. reset the letter state in the tmptlist
			//change status
			while (!tracelist.isEmpty()){
				Key e = tracelist.get(0);
				e.LineList.clear();
				e.PointList.clear();
				e.repaint();
				tracelist.remove(0);	
			}
		}
		
		public void normalState(ArrayList<Key> tracelist){
			//for(int i=0;i<tracelist.size();i++){
			//	this.tracelist.get(i).getModel().setPressed(false);;
			//}
			for(int i=0;i<tracelist.size();i++){
				this.tracelist.get(i).setBackground(new JButton().getBackground());;
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			Key theEventer = (Key) e.getSource();
			
			if (tracing == false){//start tracing
				curKey = theEventer;
				tracelist.add(theEventer);
				tracing = true;
				System.out.println("Entering Mouse tracing mode");
                theEventer.PointList.add(e.getPoint());
                playSound("click.wav");
			} else{
				Point2D p = e.getPoint();
				int x = (int)p.getX()- (curKey.getX() - theEventer.getX());
				int y = (int)p.getY()- (curKey.getY() - theEventer.getY());
				Point newPoint = new Point(x, y);
				System.out.println("Mouse position:(" + (curKey.getX() + x) + "," + (curKey.getY() + y) + "), In key " + curKey.getText() + ".");
				
				curKey.PointList.add(newPoint);
				curKey.repaint();
			}
			setColor(theEventer,30,80,230);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			playSound("click.wav");
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (tracing) {
				Key theEventer = (Key) e.getSource();
				curKey = theEventer;
				tracelist.add(theEventer);
				theEventer.setFocusPainted(true);
				setColor(theEventer,30,80,230);
				//Instead of giving a background color we can set all the traced buttons in pressed state
				//theEventer.getModel().setPressed(true);
				//start the mouse trace in this button
	            theEventer.PointList.add(e.getPoint());
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(tracing){
				Key theEventer = (Key) e.getSource();
				theEventer.setFocusPainted(false); 
                theEventer.LineList.add(theEventer.PointList);
                theEventer.PointList = new ArrayList<Point>();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			theEventer.setFocusPainted(true);
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();//e is the same source as pressed
			playSound("release.wav");
			if (tracing == false) 
			{
				updateOutput(theEventer);
				System.out.println("Input key " + theEventer.getText());
			
			} 
			else
			{
				tracing = false;
				System.out.println("Tracing Completes. Clear all traces.");
				//trace_word contains all the characters involved in swyping
				String trace_word = "";
				for(int i=0;i<tracelist.size();i++){
					trace_word= trace_word+tracelist.get(i).getText();
				}
				System.out.println("characters pressed: "+ trace_word);
				//returns arraylist of the predicted words for that trace
				ArrayList<String> predicted_words = predictWord(trace_word);
				String output = "";
				if (predicted_words.size()>0){
					comment.setText("");
					output = predicted_words.get(0);
					
					String oldString = outputdisplay.getText();
					int last_index = oldString.lastIndexOf(" ");
					int display_length = oldString.length();
					prediction1.setVisible(false);
					prediction2.setVisible(false);
					prediction3.setVisible(false);
					prediction4.setVisible(false);
					prediction5.setVisible(false);
					if (last_index==-1 && display_length==0){
						updateDisplay(output);
					}
					else{
						updateDisplay(output+" ");
					}
					
					//To show other ambigiuos words possible with the trace
					showPredictions(predicted_words);
				}
				else{
					//todo-code for showing that the word is not present
					prediction1.setVisible(false);
					prediction2.setVisible(false);
					prediction3.setVisible(false);
					prediction4.setVisible(false);
					prediction5.setVisible(false);
					comment.setText("Word not found with the given trace....Please try again");
				}
				//should change the color back
				normalState(tracelist);
				recoverState();
			}
		}
	}
	    
	public static void main(String[] args) throws FileNotFoundException,IOException {
		
		Dictionary dict = new Dictionary();
		Keyboard gui = new Keyboard(dict); 
	}
}
