public abstract class Piece {
   private String name;
   private boolean isWhite;
   private boolean isCaptured;

   // DEVELOPED BY JUSTIN
   public Piece(String name, boolean isWhite) {
      this.name = name;
      this.isWhite = isWhite;
   }

   // DEVELOPED BY JUSTIN
   public String getName() {
      return name;
   }

   // DEVELOPED BY JUSTIN
   public void setCaptured(boolean isCaptured) {
      this.isCaptured = isCaptured;
   }

   // DEVELOPED BY JUSTIN
   public boolean getCaptured() {
      return isCaptured;
   }
   
   // DEVELOPED BY JUSTIN
   public void setWhite(boolean isWhite) {
      this.isWhite = isWhite;
   }
   
   // DEVELOPED BY JUSTIN
   public boolean getIsWhite() {
      return isWhite;
   }

   // DEVELOPED BY RAHUL
   public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Square[][] board);
}