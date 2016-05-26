public class Cell<Object>
{
   private int row;
   private int col;
   private Object value;
   
   public Cell(Object v, int r, int c)
   {
      value=v;
      row=r;
      col=c;
   }
   
   public int getRows()//returns the row index of the Cell
   {
      return row;
   }
   
   public int getCol()//returns the column index of the Cell
   {
      return col;
   }
   
   public Object getVal()//returns the value of the Cell for inter-method use
   {
      return value;
   }
   
   public String toString()//returns the value of Cell if an SOP is called
   {
      return (String)value;
   }
}