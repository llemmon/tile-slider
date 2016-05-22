package tileSliderPackage;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class KeyboardTileSlider extends JFrame implements KeyListener {

	private static final long serialVersionUID = 4605453911769046422L;

	private BufferedImage image;

	private PicPanel[][] allPanels;

	private int totRow;		// location of Totoro
	private int totCol;
	private int totalMoves;

	public KeyboardTileSlider(){


		setSize(375,375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Tile Slider");
		//getContentPane().setBackground(Color.white);
		getContentPane().setBackground(Color.gray);
		
		setUndecorated( true );
		//getRootPane().setWindowDecorationStyle( JRootPane. PLAIN_DIALOG );  // blue title bar
		//getRootPane().setWindowDecorationStyle( JRootPane. COLOR_CHOOSER_DIALOG );  // green
		//getRootPane().setWindowDecorationStyle( JRootPane. QUESTION_DIALOG );		//green
		//getRootPane().setWindowDecorationStyle( JRootPane. INFORMATION_DIALOG );	//blue
		getRootPane().setWindowDecorationStyle( JRootPane. WARNING_DIALOG );		//orange
		//getRootPane().setWindowDecorationStyle( JRootPane. ERROR_DIALOG );		//pink
		
		//setLocationRelativeTo(null); 		// center on screen
		setLocation(50,75);

		allPanels = new PicPanel[4][4];
		setLayout(new GridLayout(4,4,2,2));
		setBackground(Color.black);
		addKeyListener(this);		
		
		//try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
		
		try {
			image = ImageIO.read(new File("bin/img/totoro.png"));


		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Could not read in the pic");
			System.exit(0);
		}

		boolean[] nums=new boolean[17];
		Random rGen=new Random();

		//int tally=0;
		boolean foundNumber = false;
		for(int row=0; row<4; row++){

			for(int col=0; col<4; col++){

				int val=-1;

				while((val==-1||nums[val]==true)&&!foundNumber) {

					val=rGen.nextInt(16) + 1;

					if(nums[val]==false){
						System.out.println(val);
						nums[val]=true;

						if(val!=16){
							allPanels[row][col]=new PicPanel();
							allPanels[row][col].setNumber(val);
						}
						else{
							allPanels[row][col]=new PicPanel();
							allPanels[row][col].setNumber(-1);
							allPanels[row][col].removeNumber();
							totRow = row;
							totCol = col;
						}
						add(allPanels[row][col]);
						foundNumber = true;
					}
						
					//	System.out.println(nums[val]);
				}
				foundNumber = false;
			}
		}

		System.out.println(3);
		
		setVisible(true);
	}

	public void keyPressed(KeyEvent arg0) {

		int move = arg0.getKeyCode();
		//System.out.println("in keyPressed  move = " + move);
		
		totalMoves++;

		if(move==KeyEvent.VK_LEFT){
			System.out.println("move left   totCol = " + totCol);
			if(totCol>=1){
				System.out.println("move  [" +totRow+"]["+totCol+"] to ["+totRow+"]["+(totCol-1)+"]");
				allPanels[totRow][totCol].setNumber(allPanels[totRow][totCol-1].number);
				allPanels[totRow][totCol-1].removeNumber();
				totCol--;
				totalMoves++;
				
			}
		}

		else if(move==KeyEvent.VK_RIGHT){
			System.out.println("move right   totCol = " + totCol);
			if(totCol<=2){
				System.out.println("move  [" +totRow+"]["+totCol+"] to ["+totRow+"]["+(totCol+1)+"]");
				allPanels[totRow][totCol].setNumber(allPanels[totRow][totCol+1].number);
				allPanels[totRow][totCol+1].removeNumber();
				totCol++;
				totalMoves++;
			}
		}

		else if(move==KeyEvent.VK_UP){
			System.out.println("move up   totRow = " + totRow);
			if(totRow>=1){
				System.out.println("move  [" +totRow+"]["+totCol+"] to ["+(totRow-1)+"]["+totCol+"]");
				allPanels[totRow][totCol].setNumber(allPanels[totRow-1][totCol].number);
				allPanels[totRow-1][totCol].removeNumber();
				totRow--;
				totalMoves++;
			}
		}

		else if(move==KeyEvent.VK_DOWN){
			System.out.println("move down   totRow = " + totRow);
			if(totRow<=2){
				System.out.println("move  [" +totRow+"]["+totCol+"] to ["+(totRow+1)+"]["+totCol+"]");
				allPanels[totRow][totCol].setNumber(allPanels[totRow+1][totCol].number);
				allPanels[totRow+1][totCol].removeNumber();
				totRow++;
				totalMoves++;
			}
		}
	}


	public boolean isGameOver(){

		int tally=0;

		for(int row=0; row<4; row++){
			for(int col=0; col<4; col++){
				//if tally number equal to number in row
				tally++;
				if (tally != allPanels[row][col].number) {
					System.out.println("in isGameOver:  found tally <> number");
					return false;
				} else {
					System.out.println("in isGameOver:  found tally == number at " +row+" "+col);
					try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}

		return true;
	}

	//leave this empty
	public void keyReleased(KeyEvent arg0) {


	}

	//leave this empty	
	public void keyTyped(KeyEvent arg0) {

	}

	class PicPanel extends JPanel{


		private int width = 76;
		private int height = 80;	//dimensions of the Panel 

		private int number=-1;		// -1 when Totoro is at that position.
		private JLabel text;

		public PicPanel(){

			setBackground(Color.white);
			setLayout(null);

		}		

		//changes the panel to have the given number
		public void setNumber(int num){	

			number = num;
			text = new JLabel(""+number,SwingConstants.CENTER);
			text.setFont(new Font("Calibri",Font.PLAIN,55));
			text.setBounds(0,35,70,50);
			this.add(text);
			//System.out.println("in setNumber  text = " + text.getText());

			repaint();
		}

		//replaces the number with Totoro
		public void removeNumber(){
			this.remove(text);
			number = -1;
			repaint();
		}

		public Dimension getPreferredSize() {
			return new Dimension(width,height);
		}

		//this will draw the image or the number
		// called by repaint and when the panel is initially drawn
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			if(number == -1)
				g.drawImage(image,8,0,this);
		}

	}

	public static void main(String[] args) {
		
		//System.out.println(System.getProperty("user.dir")); 
		KeyboardTileSlider keyboardTileSlider = new KeyboardTileSlider();
		while(!keyboardTileSlider.isGameOver()) {
		}
	}

}


