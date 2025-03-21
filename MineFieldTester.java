public class MineFieldTester {
   public static void main(String[] args) {
      // === TEST CASE 1: Simple 3x3 field with 2 mines ===
      boolean[][] testData1 = {
         {false, true, false},
         {false, false, false},
         {true, false, false}
      };

      MineField field1 = new MineField(testData1);
      System.out.println("=== Test 1: 3x3 Field with 2 Mines ===");
      System.out.println(field1.toString());

      System.out.println("Has mine at (0,1)? [EXPECTED: T] " + field1.hasMine(0, 1)); // true
      System.out.println("Has mine at (1,1)? [EXPECTED: F]" + field1.hasMine(1, 1)); // false
      System.out.println("inRange(0,0)? [EXPECTED: T] " + field1.inRange(0, 0));       // true
      System.out.println("inRange(3,3)?  [EXPECTED: F]" + field1.inRange(3, 3));       // false
      System.out.println("numAdjacentMines(1,1): [EXPECTED: 2] " + field1.numAdjacentMines(1, 1)); // should be 2
      System.out.println("numRows(): [EXPECTED: 3]" + field1.numRows());             // 3
      System.out.println("numCols(): [EXPECTED: 3]" + field1.numCols());             // 3
      System.out.println("numMines(): [EXPECTED: 2]" + field1.numMines());           // 2
      System.out.println();

      // === TEST CASE 2: Empty 2x2 field, test reset ===
      MineField field2 = new MineField(2, 2, 1);
      System.out.println("=== Test 2: Empty 2x2 Field ===");
      System.out.println("Before populate:");
      System.out.println(field2.toString());

      field2.populateMineField(0, 0); // avoid placing at (0,0)
      System.out.println("After populateMineField(0, 0):");
      System.out.println(field2.toString());

      System.out.println("Resetting field...");
      field2.resetEmpty();
      System.out.println("After resetEmpty():");
      System.out.println(field2.toString());

      // === TEST CASE 3: Edge mine count ===
      boolean[][] testData3 = {
         {true, true, true},
         {true, false, true},
         {true, true, true}
      };
      MineField field3 = new MineField(testData3);
      System.out.println("=== Test 3: Surrounded Center ===");
      System.out.println("numAdjacentMines(1,1): [EXPECTED: 8]" + field3.numAdjacentMines(1, 1)); // should be 8
      System.out.println("numMines(): [EXPECTED: 8]" + field3.numMines()); // should be 8
   }
}
