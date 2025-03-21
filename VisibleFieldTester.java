public class VisibleFieldTester {
   public static void main(String[] args) {
      // Create a simple 3x3 field with two mines
      boolean[][] mineData = {
         {false, true, false},
         {false, false, false},
         {true, false, false}
      };

      MineField mineField = new MineField(mineData);
      VisibleField visible = new VisibleField(mineField);

      System.out.println("=== Initial Field State ===");
      printVisibleField(visible);

      // Test uncovering a non-mine square
      System.out.println("\nUncovering (1, 1)... (should NOT be a mine)");
      boolean safe = visible.uncover(1, 1);
      System.out.println("Returned: " + safe);
      printVisibleField(visible);

      // Test uncovering a mine
      System.out.println("\nUncovering (0, 1)... (should be a MINE — lose)");
      boolean safe2 = visible.uncover(0, 1);
      System.out.println("Returned: " + safe2);
      printVisibleField(visible);

      // Test guess cycling
      System.out.println("\nCycling (2, 0) three times (flag → question → clear):");
      visible.cycleGuess(2, 0); // flag
      printVisibleField(visible);

      visible.cycleGuess(2, 0); // question
      printVisibleField(visible);

      visible.cycleGuess(2, 0); // covered again
      printVisibleField(visible);

      // Test number of mines left
      System.out.println("\nNumber of mines left to guess: " + visible.numMinesLeft());
   }

   // Helper to print the field
   public static void printVisibleField(VisibleField field) {
      for (int r = 0; r < field.getMineField().numRows(); r++) {
         for (int c = 0; c < field.getMineField().numCols(); c++) {
            int status = field.getStatus(r, c);

            String symbol = switch (status) {
               case VisibleField.COVERED       -> "■";
               case VisibleField.MINE_GUESS    -> "F";
               case VisibleField.QUESTION      -> "?";
               case VisibleField.MINE          -> "M";
               case VisibleField.EXPLODED_MINE -> "💥";
               case VisibleField.INCORRECT_GUESS -> "X";
               default -> String.valueOf(status); // uncovered numbers
            };

            System.out.print(symbol + " ");
         }
         System.out.println();
      }
   }
}
