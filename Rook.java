public class Rook extends Piece {

    private boolean hasMoved; // Keep track of whether the rook has moved

    // DEVELOPED BY RAHUL
    public Rook(String name, boolean isWhite) {
        super(name, isWhite);
        this.hasMoved = false;
    }

    // DEVELOPED BY RAHUL
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Square[][] board) {
        // Check if the move is either horizontal or vertical
        boolean isHorizontalMove = sourceRow == destRow && sourceCol != destCol;
        boolean isVerticalMove = sourceRow != destRow && sourceCol == destCol;

        // Check if the destination is within the bounds of the board
        boolean isWithinBounds = destRow >= 0 && destRow < board.length && destCol >= 0 && destCol < board[0].length;

        if ((isHorizontalMove || isVerticalMove) && isWithinBounds) {
            // Check for a clear path in the horizontal movement
            if (isHorizontalMove) {
                int minCol = Math.min(sourceCol, destCol);
                int maxCol = Math.max(sourceCol, destCol);

                for (int col = minCol + 1; col < maxCol; col++) {
                    if (board[sourceRow][col].getPiece() != null) {
                        // There is a piece in the horizontal path
                        return false;
                    }
                }
            }

            // Check for a clear path in the vertical movement
            if (isVerticalMove) {
                int minRow = Math.min(sourceRow, destRow);
                int maxRow = Math.max(sourceRow, destRow);

                for (int row = minRow + 1; row < maxRow; row++) {
                    if (board[row][sourceCol].getPiece() != null) {
                        // There is a piece in the vertical path
                        return false;
                    }
                }
            }

            // The path is clear
            hasMoved = true;
            return true;
        }

        // Not a valid horizontal or vertical move
        return false;
    }

    /**
     * Checks if the rook has moved.
     *
     * @return {@code true} if the rook has moved, {@code false} otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets the moved status of the rook.
     *
     * @param hasMoved {@code true} if the rook has moved, {@code false} otherwise.
     */
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
