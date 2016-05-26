import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class metaTTTBoard extends JPanel implements MouseListener, MouseMotionListener
{
   private ImageIcon white = new ImageIcon("white.jpg");
   private ImageIcon black = new ImageIcon("black.jpg");
   private ImageIcon blank = new ImageIcon("blank.jpg");
   private ImageIcon crossHair = new ImageIcon("crossHair.GIF");	//GIF immages can have transparency

   private static final int SIZE=40;	//size of cell being drawn
 
   //This arrayList will be represented graphically on the screen
   public static SparseMatrix <Integer> list=new SparseMatrix(9, 9);//initialized with a 9x9 playing field
 	//A moveable smiley face will start in the center of the field
   private static int playerR;			//start row for the player
   private static int playerC;			//start col for the player
   private static int numRows;
   private static int numColumns;
   public static int mouseX;			//locations for the mouse pointer
   public static int mouseY;
   private int prevX;
   private int prevY;


   public metaTTTBoard()
   {
      addMouseListener( this );
      addMouseMotionListener( this );
      mouseX = 0;
      mouseY = 0;
      numRows=list.numRows();
      numColumns=list.numColumns();
      for(int r=0;r<numRows;r++)					//fill with random values (0,1 or 2)
         for(int c=0;c<numColumns;c++)
            list.set(r, c, (int)(Math.random()*3));
      playerR = 4;							//start player position in the middle
      playerC = 4;	
   }


	//post:  shows different pictures on the screen in grid format depending on the values stored in the array board
	//			0-blank, 1-white, 2-black and gives priority to drawing the player
   public void showBoard(Graphics g)	
   {
      int x =0, y=0;		//upper left corner location of where image will be drawn
      for(int r=0;r<numRows;r++)
      {
         x = 0;						//reset the row distance
         for(int c=0;c<numColumns;c++)
         {
            if(list.get(r, c)==0)
               g.drawImage(blank.getImage(), x, y, SIZE, SIZE, null);  //scaled image
            else
               if(list.get(r, c)==1)
                  g.drawImage(white.getImage(), x, y, SIZE, SIZE, null);  //scaled image
               else // if(board[r][c]==2)
                  g.drawImage(black.getImage(), x, y, SIZE, SIZE, null);  //scaled image
           
            if(r==playerR && c==playerC)	//draw the crosshair on the board after the cell has been drawn
               g.drawImage(crossHair.getImage(), x, y, SIZE, SIZE, null);  //scaled image
         
            x+=SIZE;
         }
         y+=SIZE;
      }
   }
   
   public int getPrevX()//returns the X-position of the previous move
   {
      return prevX;
   }
   
   public int getPrevY() //returns the Y-position of the previous move
   {
      return prevY;
   }
   
   public int getMouseX()
   {
      return mouseX;
   }
   
   public int getMouseY()
   {
      return mouseY;
   }
   
	//THIS METHOD IS ONLY CALLED THE MOMENT A KEY IS HIT - NOT AT ANY OTHER TIME
	//pre:   k is a valid keyCode
	//post:  changes the players position depending on the key that was pressed (sent from the driver)
	//			keeps the player in the bounds of the size of the array board, then the enemy moves
   public void processUserInput(int k)
   {
      if(k==KeyEvent.VK_Q || k==KeyEvent.VK_ESCAPE)					//End the program	
         System.exit(1);
      if(k==KeyEvent.VK_UP && playerR>0)
         playerR--;
      else 
         if(k==KeyEvent.VK_DOWN && playerR<numRows-1)
            playerR++;
         else
            if(k==KeyEvent.VK_LEFT && playerC>0)
               playerC--;
            else 
               if(k==KeyEvent.VK_RIGHT && playerC<numColumns-1)
                  playerC++;
               else 
                  if(k==KeyEvent.VK_SPACE)	//toggle the value of the array at that position
                     list.set(playerR, playerC, ((list.get(playerR, playerC)+1) % 3));
      repaint();			//refresh the screen
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g); 
      g.setColor(Color.blue);		//draw a blue boarder around the board
      g.fillRect(0, 0, (numColumns*SIZE), (numRows*SIZE));
      showBoard(g);					//draw the contents of the array board on the screen
   }
   
	 //***BEGIN MOUSE STUFF***
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)	//this is called for each timer iteration - make the enemy move randomly
      {
         repaint();
      }
   }

   public void mouseClicked( MouseEvent e )
   {
      int button = e.getButton();
      if(button == MouseEvent.BUTTON1)
      {
         int mouseR = (mouseY/SIZE);
         int mouseC = (mouseX/SIZE);
      
         if(mouseR >=0 && mouseC >= 0 && mouseR < numRows && mouseC < numColumns)
         {
            playerR = mouseR;
            playerC = mouseC;
            if(button == MouseEvent.BUTTON1)	//button 1 toggles the space
            {
               list.set(playerR, playerC, (list.get((playerR), playerC+1) % 3));
            }
         }
         else
         {
            playerR = 4;
            playerC = 4;
         
         }
      
      } 
      repaint();
   }

   public void mousePressed( MouseEvent e )
   {}

   public void mouseReleased( MouseEvent e )
   { 
      prevX=playerR;//sets the X-position of the previous move to the most recent
      prevY=playerC;//sets the Y-position of the previous move to the most recent
   }

   public void mouseEntered( MouseEvent e )
   {}

   public void mouseMoved( MouseEvent e)
   {
      mouseX = e.getX();
      mouseY = e.getY();
      
      int mouseR = (mouseY/SIZE);
      int mouseC = (mouseX/SIZE);
     // System.out.println(mouseR+":"+mouseC);
      if(mouseR >=0 && mouseC >= 0 && mouseR < numRows && mouseC < numColumns)
      {
         playerR = mouseR;
         playerC = mouseC;
      }
      else
      {
         playerR = 4;
         playerC = 4;
      
      }
      repaint();			//refresh the screen
   }

   public void mouseDragged( MouseEvent e)
   {}

   public void mouseExited( MouseEvent e )
   {}

}
