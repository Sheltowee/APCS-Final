import java.util.*;
import java.io.*;

public class metaTTT
{
   public static SparseMatrix <Mark> list=new SparseMatrix(9, 9);
   public static metaTTTBoard board=new metaTTTBoard();
   private static int countO;
   private static int countX;
   private static int prevX=4;
   private static int prevY=4;
   private static int posX=4;
   private static int posY=4;
   private static Mark oPiece=new Mark(0);
   private static Mark xPiece=new Mark(1);
   private static int playCount=0;
   private static Scanner in=new Scanner(System.in);
   
   public static void main(String[]arg)
   {
      while(winCond()=="")
      {
         boardPrt();
         playerMove();
         System.out.println(winCond());
         aiMove();
      }
      System.out.println(winCond());
   }
   
   public static void startOver()//clears the ArrayList for a new game
   {
      list.clear();
   }
   
   public static void playerMove()//allows to player to place their piece, within the subBoard by typing a set of coordinates
   {
      int playValX=0;
      int playValY=0;
      if(playCount==0)//for the first play of the game
      {
         playCount++;
         System.out.println("You can play in any square on the board");
         System.out.println("Enter the coordinates for the place you wish to place your mark (1-9)");
         System.out.println("Press Enter after each coordinate");
         playValX=in.nextInt()-1;
         playValY=in.nextInt()-1;
         while((playValX>8||playValX<0)||(playValY>8||playValY<0))
         {
            System.out.println("That is an invalid move. Please enter valid coordinates");
            playValX=in.nextInt()-1;
            playValY=in.nextInt()-1;
         }
         if(list.get(playValX, playValY)==null)
         {
            list.add(playValX, playValY, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
         else
         {
            while(list.get(playValX, playValY)!=null)
            {
               System.out.println("That is an invalid move. Please enter valid coordinates");
               playValX=(in.nextInt()-1);
               playValY=(in.nextInt()-1);
            }
            list.add(playValX, playValY, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
      }
      else//after a move by the AI has already occured
      {
         checkPrevMove(prevX, prevY);
         System.out.println("You can play in the subboard beginning at "+posX+", "+posY);
         System.out.println("Enter the coordinates for the place you wish to place your mark (1-3)");
         System.out.println("Press Enter after each coordinate");
         playValX=in.nextInt()-1;
         playValY=in.nextInt()-1;
         while((playValX>2||playValX<0)||(playValY>2||playValY<0))
         {
            System.out.println("That is an invalid move. Please enter valid coordinates");
            playValX=in.nextInt()-1;
            playValY=in.nextInt()-1;
         }
         if(list.get(playValX, playValY)==null)
         {
            list.add(playValX, playValY, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
         else
         {
            while(list.get(playValX, playValY)!=null)
            {
               System.out.println("That is an invalid move. Please enter valid coordinates");
               playValX=in.nextInt()-1;
               playValY=in.nextInt()-1;
            }
            list.add(playValX, playValY, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
      }
   }
   
   public static void checkPrevMove(int x, int y)//sets the available sub-board to the proper one
   {
      if(x==0||x==4||x==8)
         posX=(x-(x%3));
      else if(x==1||x==7)
         posX=3;
      else if(x==2||x==5)
         posX=6;
      else if(x==3||x==6)
         posX=0;
      if(y==0||y==4||y==8)
         posY=(y-(y%3));
      else if(y==1||y==7)
         posY=3;
      else if(y==2||y==5)
         posY=6;
      else if(y==3||y==6)
         posY=0;
   }
   
   public static void aiMove()//checks and then makes a move
   {
      if(winCond()=="")
      {
         checkPrevMove(prevX, prevY);
         Mark temp=new Mark(0);
         for(int c=posY; c<posY+3; c++)
         {
            for(int r=posX; r<posX+3; r++)//cycles through all positions in a subboard
            {
               if(list.get(r, c)!=null&&list.get(r, c).getMarked()==0)//finds an O
               {
                  for(int j=0; j<2; j++)
                  {
                     for(int k=-1; k<2; k++)// checks   O -
                     {                      //        - - -
                        if(j==0&&k==0)
                           continue;
                        if(list.get(r+k, c+j)!=null&&list.get(r+k, c+j).getMarked()==0)//finds another O in those 4 possible positions 
                        {
                           if(j==0)//cycles through all possibles, and adds an O to the free space, leading to a win
                           {
                              if(k==1)
                              {
                                 if(list.get(r+2, c)==null)
                                 {
                                    list.add(r+2, c, temp);
                                    prevX=r+2;
                                    prevY=c;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                           }
                           if(j==1)
                           {
                              if(k==-1)
                              {
                                 if(list.get(r-2, c+2)==null)
                                 {
                                    list.add(r-2, c+2, temp);
                                    prevX=r-2;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                              if(k==0)
                              {
                                 if(list.get(r, c+2)==null)
                                 {
                                    list.add(r, c+2, temp);
                                    prevX=r;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                              if(k==1)
                              {
                                 if(list.get(r+2, c+2)==null)
                                 {
                                    list.add(r+2, c+2,temp);
                                    prevX=r+2;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
               if(list.get(r, c)!=null&&list.get(r, c).getMarked()==1)
               {
                  for(int j=0; j<2; j++)
                  {
                     for(int k=-1; k<2; k++)// checks   X -
                     {                      //        - - -
                        if(j==0&&k==0)
                           continue;
                        if(list.get(r+k, c+j)!=null&&list.get(r+k, c+j).getMarked()==0)//finds another X in those 4 possible positions 
                        {
                           if(j==0)//cycles through all possibles, and adds an O to the free space, leading to a block
                           {
                              if(k==1)
                              {
                                 if(list.get(r+2, c)==null)
                                 {
                                    list.add(r+2, c, temp);
                                    prevX=r+2;
                                    prevY=c;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                           }
                           if(j==1)
                           {
                              if(k==-1)
                              {
                                 if(list.get(r-2, c+2)==null)
                                 {
                                    list.add(r-2, c+2, temp);
                                    prevX=r-2;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                              if(k==0)
                              {
                                 if(list.get(r, c+2)==null)
                                 {
                                    list.add(r, c+2, temp);
                                    prevX=r;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                              if(k==1)
                              {
                                 if(list.get(r+2, c+2)==null)
                                 {
                                    list.add(r+2, c+2,temp);
                                    prevX=r+2;
                                    prevY=c+2;
                                    winCond();System.out.println(list);
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
         int m=(int)(Math.random()*3)+posX;//if no winning or blocking moves are found, places piece randomly in the subBoard
         int o=(int)(Math.random()*3)+posY;
         list.add(m, o, temp);
         prevX=m;
         prevY=o;
         winCond();
      }
   }
   
   public static String winCond()//checks all possible win conditions
   {                      //returns a string that is either empty, or who won
      for(int c=0; c<9; c+=3)
      {
         for(int r=0; r<9; r+=3)
         {
            if(list.get(r, c)!=null&&list.get(r, c).getMarked()==1)
            {
               if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==1)
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)
                     startOver();
                  return "X won!";
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
               else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==1)
               {
                  if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
            }
            else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==1)
            {
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
            }
            else if(list.get(r, c+2)!=null&&list.get(r, c+=2).getMarked()==1)
            {
               if(list.get(r+1, c+2)!=null&&list.get(r+1, c+=2).getMarked()==1)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)
                     startOver();
                  return "X won!";
               }
            }
            else if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==1)
            {
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)
               {
                  if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
            }
            else if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)
            {
               if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==1)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                     startOver();
                  return "X won!";
               }
            }
            else if(list.get(r, c)!=null&&list.get(r, c).getMarked()==0)
            {
               if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==0)
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)
                     startOver();
                  return "O won!";
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)
                     startOver();
                  return "O won!";
               }
               else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==0)
               {
                  if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==0)
                     startOver();
                  return "O won!";
               }
            }
            else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==0)
            {
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)
               {
                  if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==0)
                     startOver();
                  return "O won!";
               }
            }
            else if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==0)
            {
               if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==0)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)
                     startOver();
                  return "O won!";
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)
                     startOver();
                  return "O won!";
               }
            }
            else if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==0)
            {
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)
               {
                  if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==0)
                     startOver();
                  return "O won!";
               }
            }
            else if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)
            {
               if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==0)
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)
                     startOver();
                  return "O won!";
               }
            }
         }
      }
      return "";
   }
   
   public static void boardPrt()//prints out the game board after every turn
   {
      String ans="";
      int count=0;
      for(int r=0; r<list.numRows(); r++)
      {
         for(int c=0; c<list.numColumns(); c++)
         {
            count++;
            Mark temp=list.get(r, c);
            if(temp==null)
               ans+="- ";
            else
               ans+=temp+" ";;
            if(count%3==0)
               ans+="| ";
            if(count%9==0)
               System.out.println("");
            if(count%27==0&&count!=81)
               ans+="\n_____________________\n";
         }
         ans+="\n";
      }
      System.out.println(ans);
   }
}