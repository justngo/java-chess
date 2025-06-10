public class Bishop extends Piece {

    // DEVELOPED BY RAHUL
    public Bishop(String name, boolean isWhite) {
        super(name, isWhite);
    }

    // DEVELOPED BY RAHUL
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Square[][] board) {
        // Check if the move is diagonal
        if (Math.abs(destRow - sourceRow) == Math.abs(destCol - sourceCol)) {
            // Check if there are no pieces in the diagonal path

            // Assign 1 if upward movement (true), -1 if downward movement (false)
            int rowIncrement = (destRow > sourceRow) ? 1 : -1;
            // Assign 1 if rightward movement (true), -1 if leftward movement (false)
            int colIncrement = (destCol > sourceCol) ? 1 : -1; 
            
            // initialized to the position immediately next to the source position in the
            // chosen diagonal direction.
            int currentRow = sourceRow + rowIncrement;
            int currentCol = sourceCol + colIncrement;

            // While loop with limit of destination coordinate
            while (currentRow != destRow && currentCol != destCol) {
                // Check if there is a piece in the diagonal path
                if (board[currentRow][currentCol].getPiece() != null) {
                    return false;
                }
                // Increment loop parameters to continue index
                currentRow += rowIncrement;
                currentCol += colIncrement;
            }
            // No pieces in the diagonal path
            return true;
        }
        // Not a valid diagonal move
        return false;
    }
}
