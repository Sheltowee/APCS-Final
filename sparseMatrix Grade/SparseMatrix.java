import java.io.*;
import java.util.*;
public class SparseMatrix<anyType> implements Matrixable<anyType>
{      
   private ArrayList<Cell> list;
   private int numElements;
   private int numRows, numCols;
   
   public SparseMatrix (int r, int c)//basic constructor
   {
      list = new ArrayList();
      numElements = 0;
      numRows = r;
      numCols = c;      
   }
   
   public int numColumns()//returns the number of rows in the ArrayList
   {
      return numCols;
   }
   
   public int numRows()//returns the number of columns in the ArrayList
   {
      return numRows;
   }
   
   public int size()//returns the number of elements currently in the ArrayList
   {
      return numElements;
   }
   
   public anyType get(int r, int c)//finds and returns the value at the specified indecies
   {                               //returns null if nothing is found
      for(int i=0; i<list.size(); i++)
      {
         Cell temp=list.get(i);
         if(temp.getRows()==r && temp.getCol()==c)
         {
            //anyType val=(anyType)Cell.getVal();
            return (anyType)(temp.getVal());
         }
      }
      return null;
   }
   public anyType set(int r, int c, anyType x)//sets the value at row r, column c to value x
   {                                          //if no Cell exists at r, c, the value is then added at the specified location
      int place=(r*numColumns()+c);
      for(int i=0; i<list.size(); i++)
      {
         if(((list.get(i).getRows()*numColumns())+list.get(i).getCol())==place)
         {
            Cell old=list.get(i);
            list.set(i, new Cell(x, r, c));
            return (anyType)(old.getVal());
         }
      }
      return null;
   }
   public void add(int r, int c, anyType x)//adds anyType "x" at the specified indecies
   {
      int place=(r*numColumns()+c);
      numElements++;
      for(int i=0; i<list.size(); i++)
      {
         Cell temp=list.get(i);
         int key=(temp.getRows()*numColumns()+temp.getCol());
      
         if(key>place)
         {
            list.add(i, new Cell(x, r, c));
            return;
         }
      }
      list.add( new Cell(x, r, c));
   
   }
   public anyType remove(int r, int c)//finds and removes the value at the specified indecies
   {                                   //returns the old value, and null if nothing is found
      for(int i=0; i<list.size(); i++)
      {
         Cell temp=list.get(i);
         if(temp.getRows()==r && temp.getCol()==c)
         {
            list.remove(i);
            numElements--;
            return (anyType)(temp.getVal());
         }
      }
      return null;
   }
   
   public boolean contains(anyType x)//searches the ArrayList and returns true if x is found
   {
      for(int i=0; i<list.size(); i++)
      {
         if(list.get(i)==x)
            return true;
      }
      return false;
   }
   
   public void clear()  //Pre: There are elements in list
   {                    //clears the ArrayList of all objects
      list.removeAll(list);
   }
   
   public boolean isEmpty()//searches the ArrayList, and returns true if list is empty
   {
      for(int i=0; i<list.size(); i++)
      {
         if(list.get(i)!=null)
            return false;
      }
      return true;
   }
   public String toString()//returns an organized version of the ArrayList
   {
      String ans="";
      for(int i=0; i<list.size(); i++)
      {
         ans+=list.get(i);
      }
      return ans;
   }
}