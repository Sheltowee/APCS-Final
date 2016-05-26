import java.util.*;
import java.io.*;

public class Mark
{
   public int marked;

   public Mark(int m)
   {
      marked=m;
      
   }
   
   public int getMarked()
   {
      return marked;
   }
   
   public void setMark(int num)
   {
      marked=num;
   }
   
   public String toString()
   {
      if(getMarked()==0)
         return "O";
      else
         return "X";
   }
}