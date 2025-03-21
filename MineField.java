// Name:
// USC NetID:
// CS 455 PA3
// Spring 2025

import java.util.Random;

/** 
   MineField
      Class with locations of mines for a minesweeper game.
      This class is mutable, because we sometimes need to change it once it's created.
      Mutators: populateMineField, resetEmpty
      Includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   
   // <put instance variables here>
   private int rows;
   private int columns;
   private int mineCount;
   private Random random;
   private boolean[][] mineLocation;
   
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in
      the array such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice
      versa.  numMines() for this minefield will corresponds to the number of 'true' values in 
      mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {

      rows = mineData.length;
      columns = mineData[0].length;
      mineCount = 0;
      random = new Random();

      /**
         creating array to store mine locations
         mineLocation array is same size as the mineData
       */
      mineLocation = new boolean[rows][columns]; 

      //creating defensive copy of mineData
      for (int r = 0; r < rows; r++){
         for (int c = 0; c < columns; c++){
            mineLocation[r][c] = mineData[r][c];
            if (mineLocation[r][c]){
               mineCount++;
            }
         }
      }
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a 
      MineField, numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      assert (numRows > 0 && numCols > 0) && (numMines >= 0 && numMines < (numRows * numCols) / 3);
      
      rows = numRows;
      columns = numCols;
      mineCount = numMines;
      random = new Random();

      //create the mine grid
      mineLocation = new boolean[rows][columns];
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on
      the minefield, ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {

      assert inRange(row, col) && numMines() < (numRows() * numCols()) / 3;

      resetEmpty();
      int usedMines = 0;

      while(usedMines < mineCount){
         int r = random.nextInt(rows);
         int c = random.nextInt(columns);

         /**
          * checks avoid placing mine at (row, col)
          * checks if a mine was placed before at the [r][c]
          */
         if ((r != row || c != col) && !mineLocation[r][c]){
            mineLocation[r][c] = true;
            usedMines ++;
         }
      }
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or
      numCols().  Thus, after this call, the actual number of mines in the minefield does not match
      numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in at the 
      beginning of a game.
    */
   public void resetEmpty() {
      for (int r = 0; r < rows; r++){
         for (int c = 0; c < columns; c++){
            mineLocation[r][c] = false;
         }
      }
   }

   
  /**
     Returns the number of mines adjacent to the specified location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      assert inRange(row, col);

      int foundMineCount = 0;
      // traverse in 3*3 grid squares - nested loop 
      for (int pointerRowLoc = -1; pointerRowLoc <= 1; pointerRowLoc++){
         for (int pointerColLoc = -1; pointerColLoc <= 1; pointerColLoc++){
            int newRowIndex = row + pointerRowLoc;
            int newColIndex = col + pointerColLoc;

            // not checking the selected square
            if (pointerRowLoc == 0 && pointerColLoc == 0){
               continue;
            }
            if (inRange(newRowIndex, newColIndex) && mineLocation[newRowIndex][newColIndex]){
               foundMineCount++;
            }
         }
      }
      return foundMineCount;       
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
      if (row < 0 || row >= rows || col < 0 || col >= columns){
         return false;       
      }
      return true;
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return rows;       
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return columns;       
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {

      assert inRange(row, col);
      
      return mineLocation[row][col];
      
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg
      constructor, some of the time this value does not match the actual number of mines currently
      on the field.  See doc for that constructor, resetEmpty, and populateMineField for more
      details.
      @return number of mines
    */
   public int numMines() {
      return mineCount;       
   }

   @Override
   public String toString(){
      String result = "Number of Rows: " + rows + "\n";
      result += "Number of Columns: " + columns + "\n";
      result += "Number of Mines: " + mineCount + "\n";
      result += "Mine Locations: \n";

      for (int r = 0; r < rows; r++){
         for (int c = 0; c< columns; c++){
            if (mineLocation[r][c]){
               result += "Mine ";
            }
            else{
               result += "- ";
            }
         }
         result += "\n";
      }
      return result;
   }
   
   // <put private methods here>

         
}

