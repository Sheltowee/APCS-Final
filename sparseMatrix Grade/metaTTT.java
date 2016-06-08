import java.util.*;
import java.io.*;

public class metaTTT//Sometimes has issues deciding where to place a piece, leading to a null move
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
   private static String answer="Y";
   private static int winner=-1;
   
   public static void main(String[]arg)
   {
      while(answer.equals("Y"))
      {
         while(winCond()=="")
         {
            boardPrt();
            playerMove();
            System.out.println(winCond());//if a player wins, prints who won out to the screen, and clears the board
            if(winCond()!="")
               break;
            boardPrt();
            aiMove();
            System.out.println(winCond());//if a player wins, prints who won out to the screen, and clears the board
            if(winCond()!="")
               break;
         }
         System.out.println("Do you want to play again? \n Y:yes \n N:no");
         answer=in.nextLine();
      }
   }
   
   public static void startOver()//clears the ArrayList for a new game
   {
      list.clear();
      if(winner<1)
         playCount=0;
   }
   
   public static void playerMove()//allows to player to place their piece, within the subBoard by typing a set of coordinates
   {
      int playValX=0;
      int playValY=0;
      if(playCount>0)
      {
         checkPrevMove(prevX, prevY);
      }
      if(playCount==0)//for the first play of the game
      {
         playCount++;
         System.out.println("You can play in any square on the board");
         System.out.println("Enter the coordinates for the place you wish to place your mark (1-9)");
         System.out.println("Press Enter after each coordinate");
         playValX=in.nextInt()-1;
         playValY=in.nextInt()-1;
         while((playValX>8||playValX<0)||(playValY>8||playValY<0))//checks validity of data
         {
            System.out.println("That is an invalid move. Please enter valid coordinates");
            playValX=in.nextInt()-1;
            playValY=in.nextInt()-1;
         }
         if(list.get(playValX, playValY)==null)//if no data at chosen point, add entered data
         {
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
         else
         {
            while(list.get(playValX, playValY)!=null)//else, does not add, requesting valid data, then adds it in
            {
               System.out.println("That is an invalid move. Please enter valid coordinates");
               playValX=(in.nextInt()-1);
               playValY=(in.nextInt()-1);
            }
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
      }
      else if(posX<0&&posY<0)//when the opponent plays a move allowing free movement
      {
         System.out.println("You can play in any square on the board");
         System.out.println("Enter the coordinates for the place you wish to place your mark (1-9)");
         System.out.println("Press Enter after each coordinate");
         playValX=in.nextInt()-1;
         playValY=in.nextInt()-1;
         while((playValX>8||playValX<0)||(playValY>8||playValY<0))//checks validity of data
         {
            System.out.println("That is an invalid move. Please enter valid coordinates");
            playValX=in.nextInt()-1;
            playValY=in.nextInt()-1;
         }
         if(list.get(playValX, playValY)==null)//if no data at chosen point, add entered data
         {
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
         else
         {
            while(list.get(playValX, playValY)!=null)//else, does not add, requesting valid data, then adds it in
            {
               System.out.println("That is an invalid move. Please enter valid coordinates");
               playValX=in.nextInt()-1+posX;
               playValY=in.nextInt()-1+posY;
            }
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
      }
      else//after a move by the AI has already occured
      {
         System.out.println("You can play in the subboard beginning at "+(posX+1)+", "+(posY+1));
         System.out.println("Enter the coordinates for the place you wish to place your mark (1-3)");
         System.out.println("Press Enter after each coordinate");
         playValX=posX+in.nextInt()-1;
         playValY=posY+in.nextInt()-1;
         while((playValX>8||playValX<0)||(playValY>8||playValY<0))//checks validity of data
         {
            System.out.println("That is an invalid move. Please enter valid coordinates");
            playValX=posX+in.nextInt()-1;
            playValY=posY+in.nextInt()-1;
         }
         if(list.get(playValX, playValY)==null)//if no data at chosen point, add entered data
         {
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
         else
         {
            while(list.get(playValX, playValY)!=null)//else, does not add, requesting valid data, then adds it in
            {
               System.out.println("That is an invalid move. Please enter valid coordinates");
               playValX=posX+in.nextInt()-1;
               playValY=posY+in.nextInt()-1;
            }
            list.add(playValY, playValX, xPiece);
            prevX=playValX;
            prevY=playValY;
         }
      }
   }
   
   public static void checkPrevMove(int x, int y)//sets the available sub-board to the proper one
   {
      if((x==0&&y==0)||(x==4&&y==0)||(x==8&&y==0)||(x==0&&y==4)||(x==4&&y==4)||(x==8&&y==4)||(x==0&&y==8)||(x==4&&y==8)||(x==8&&y==8))
      {
         posX=-1;
         posY=-1;
      }
      else if((x==3&&y==0)||(x==6&&y==0)||(x==0&&y==3)||(x==3&&y==3)||(x==6&&y==3)||(x==0&&y==6)||(x==3&&y==6)||(x==6&&y==6))
      {           //for X - -
         posX=0;      //- - -
         posY=0;      //- - -
      }
      else if((x==1&&y==0)||(x==7&&y==0)||(x==1&&y==3)||(x==4&&y==3)||(x==7&&y==3)||(x==1&&y==6)||(x==4&&y==6)||(x==7&&y==6))
      {           //for - X -
         posX=0;     // - - -
         posY=3;     // - - -
      }
      else if((x==2&&y==0)||(x==5&&y==0)||(x==2&&y==3)||(x==5&&y==3)||(x==8&&y==3)||(x==2&&y==6)||(x==5&&y==6)||(x==8&&y==6))
      {           //for - - X
         posX=0;  //    - - -
         posY=6;  //    - - -
      }
      else if((x==0&&y==1)||(x==3&&y==1)||(x==6&&y==1)||(x==3&&y==4)||(x==6&&y==4)||(x==0&&y==7)||(x==3&&y==7)||(x==6&&y==7))
      {           //for - - -
         posX=3;  //    X - -
         posY=0;  //    - - -
      }
      else if((x==1&&y==1)||(x==4&&y==1)||(x==7&&y==1)||(x==1&&y==4)||(x==7&&y==4)||(x==1&&y==7)||(x==4&&y==7)||(x==7&&y==7))
      {           //for - - -
         posX=3;  //    - X -
         posY=3;  //    - - -
      }
      else if((x==2&&y==1)||(x==5&&y==1)||(x==8&&y==1)||(x==2&&y==4)||(x==5&&y==4)||(x==2&&y==7)||(x==5&&y==7)||(x==8&&y==7))
      {           //for - - -
         posX=3;  //    - - X
         posY=6;  //    - - -
      }
      else if((x==0&&y==2)||(x==3&&y==2)||(x==6&&y==2)||(x==0&&y==5)||(x==3&&y==5)||(x==6&&y==5)||(x==3&&y==8)||(x==6&&y==8))
      {           //for - - -
         posX=6;  //    - - -
         posY=0;  //    X - -
      }
      else if((x==1&&y==2)||(x==4&&y==2)||(x==8&&y==2)||(x==1&&y==5)||(x==4&&y==5)||(x==7&&y==5)||(x==1&&y==8)||(x==7&&y==8))
      {           //for - - -
         posX=6;  //    - - -
         posY=3;  //    - X -
      }
      else if((x==2&&y==2)||(x==5&&y==2)||(x==8&&y==2)||(x==2&&y==5)||(x==5&&y==5)||(x==8&&y==5)||(x==2&&y==8)||(x==5&&y==8))
      {           //for - - -
         posX=6;  //    - - -
         posY=6;  //    - - X
      }
   }
   
   public static void aiMove()//checks and then makes a move
   {
      Mark temp=new Mark(0);
      if(winCond()=="")
      {
         checkPrevMove(prevX, prevY);
         if(posX<0&&posY<0)
         {
            for(int c=0; c<10; c++)
            {
               for(int r=0; r<10; r++)//cycles through all positions in the board
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
                                    if(list.get(r+2, c)==null) //makes  O O O
                                    {                          //       - - -
                                       list.add(r+2, c, temp);
                                       prevX=r+2;
                                       prevY=c;
                                       winCond();
                                       boardPrt();
                                       return;
                                    }
                                 }
                              }
                              if(j==1)
                              {
                                 if(k==-1)
                                 {
                                    if(list.get(r-2, c+2)==null) //makes - - O
                                    {                            //      - O -
                                       list.add(r-2, c+2, temp); //      O - -
                                       prevX=r-2;
                                       prevY=c+2;
                                       winCond();boardPrt();
                                       return;
                                    }
                                 }
                                 if(k==0)
                                 {
                                    if(list.get(r, c+2)==null) //makes - O - 
                                    {                          //      - O -
                                       list.add(r, c+2, temp); //      - O -
                                       prevX=r;
                                       prevY=c+2;
                                       winCond();boardPrt();
                                       return;
                                    }
                                 }
                                 if(k==1)
                                 {
                                    if(list.get(r+2, c+2)==null) //makes O - - 
                                    {                            //      - O -
                                       list.add(r+2, c+2,temp);  //      - - O
                                       prevX=r+2;
                                       prevY=c+2;
                                       winCond();boardPrt();
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
                           if(list.get(r+k, c+j)!=null&&list.get(r+k, c+j).getMarked()==1)//finds another X in those 4 possible positions 
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
         }
         else
         {
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                           if(list.get(r+k, c+j)!=null&&list.get(r+k, c+j).getMarked()==1)//finds another X in those 4 possible positions 
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
                                       winCond();boardPrt();
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
         }
      }
      if(posX<0&&posY<0)
      {
         int m=(int)(Math.random()*9);//if no winning or blocking moves are found, places piece randomly in the subBoard
         int o=(int)(Math.random()*9);
         list.add(m, o, temp);
         prevX=m;
         prevY=o;
         winCond();
      }
      else
      {
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
      for(int c=0; c<10; c+=3)
      {
         for(int r=0; r<10; r+=3)
         {
            if(list.get(r, c)!=null&&list.get(r, c).getMarked()==1)             //for X X X
            {                                                                   //    - - -
               if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==1)      //    - - -
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1) //for X - -
               {                                                                    //    - X -
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)   //    - - X
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
               else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==1)     //for X - -
               {                                                                    //    X - -
                  if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==1)       //    X - -
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
            }
            else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==1)        //for - - -
            {                                                                       //    X X X
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)      //    - - -
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
            }
            else if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==1)       //for - - -
            {                                                                      //    - - -
               if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==1)     //    X X X
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1) //for - - X
               {                                                                    //    - X -
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)       //    X - -
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
            }
            else if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==1)        //for - X -
            {                                                                       //    - X -
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==1)      //    - X -
               {
                  if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==1)
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
            }
            else if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==1)        //for - - X
            {                                                                       //    - - X
               if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==1)      //    - - X
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==1)
                  {
                     startOver();
                     winner=1; 
                     return "X won!";
                  }
               }
            }
            else if(list.get(r, c)!=null&&list.get(r, c).getMarked()==0)            //for O O O
            {                                                                       //    - - -
               if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==0)          //    - - -
               {
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0) //for O - -
               {                                                                    //    - O -
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)   //    - - O
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
               else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==0)     //for O - -
               {                                                                    //    O - -
                  if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==0)       //    O - -
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
            }
            else if(list.get(r, c+1)!=null&&list.get(r, c+1).getMarked()==0)        //for - - -
            {                                                                       //    O O O
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)      //    - - -
               {
                  if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==0)
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
            }
            else if(list.get(r, c+2)!=null&&list.get(r, c+2).getMarked()==0)        //for - - -
            {                                                                       //    - - -
               if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==0)      //    O O O
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
               else if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0) //for - - O
               {                                                                    //    - O -
                  if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)       //    O - -
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
            }
            else if(list.get(r+1, c)!=null&&list.get(r+1, c).getMarked()==0)        //for - O -
            {                                                                       //    - O -
               if(list.get(r+1, c+1)!=null&&list.get(r+1, c+1).getMarked()==0)      //    - O -
               {
                  if(list.get(r+1, c+2)!=null&&list.get(r+1, c+2).getMarked()==0)
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
               }
            }
            else if(list.get(r+2, c)!=null&&list.get(r+2, c).getMarked()==0)        //for - - O
            {                                                                       //    - - O
               if(list.get(r+2, c+1)!=null&&list.get(r+2, c+1).getMarked()==0)      //    - - O
               {
                  if(list.get(r+2, c+2)!=null&&list.get(r+2, c+2).getMarked()==0)
                  {
                     startOver();
                     winner=0; 
                     return "O won!";
                  }
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
      int stepCount=1;
      ans+="1 2 3   4 5 6   7 8 9\n";
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
            {
               ans+=stepCount;
               stepCount++;
               System.out.println();
            }
            if(count%27==0&&count!=81)
               ans+="\n_______________________\n";
         }
         ans+="\n";
      }
      System.out.println(ans);
   }
}