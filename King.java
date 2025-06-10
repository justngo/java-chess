/**
 * The King class represents a king chess piece. It extends the Piece class and
 * includes
 * additional functionality specific to the king, such as tracking whether the
 * king has moved
 * and checking the validity of its moves.
 *
 * Developed by Justin
 */
public class King extends Piece {

    private boolean hasMoved; // Keep track of whether the king has moved

    /**
     * Constructs a new King object with the specified name and color.
     *
     * @param name    The name of the king.
     * @param isWhite True if the king is white, false otherwise.
     */
    public King(String name, boolean isWhite) {
        super(name, isWhite);
        this.hasMoved = false;
    }

    /**
     * Checks if a move from the source square to the destination square is a valid
     * move for the king.
     * The validity is determined by the standard one-square move or the castling
     * maneuver.
     *
     * @param sourceRow The row index of the source square.
     * @param sourceCol The column index of the source square.
     * @param destRow   The row index of the destination square.
     * @param destCol   The column index of the destination square.
     * @param board     The current state of the chessboard.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, Square[][] board) {
        // Check if move is one square in any direction (Move distance <= 1)
        boolean isOneSquareMove = (Math.abs(destRow - sourceRow) <= 1) && (Math.abs(destCol - sourceCol) <= 1);

        // Check if destination is within the bounds of the board
        boolean isWithinBounds = (destRow >= 0) && (destRow < board.length) && (destCol >= 0)
                && (destCol < board[0].length);

        // Only allow pass if move is valid
        if (isWithinBounds) {
            // Regular one-square move
            if (isOneSquareMove) {
                this.hasMoved = true;
                return true;
            }
            // Automatically check for castling maneuver, check if King has moved and if
            // move is in correct column and row
            else if ((!hasMoved) && (Math.abs(destCol - sourceCol) == 2) && (destRow == sourceRow)) {
                int rookCol;
                // Check which side of the board the rook is on
                if (destCol > sourceCol) {
                    // Rook is on the right side
                    rookCol = board[0].length - 1;
                } else {
                    // Rook is on the left side
                    rookCol = 0;
                }
                Square rookSquare = board[destRow][rookCol];

                // Pass if the rookSquare has a rook and the rook hasn't moved
                if ((rookSquare.getPiece() instanceof Rook) && (!((Rook) rookSquare.getPiece()).hasMoved())) {
                    // Check which direction
                    int colIncrement;
                    if (destCol > sourceCol) {
                        // Moving right
                        colIncrement = 1;
                    } else {
                        // Moving left
                        colIncrement = -1;
                    }

                    // Check if the squares between the king and the rook are empty
                    // Return false if there is a piece in the path for castling
                    for (int col = sourceCol + colIncrement; col != destCol; col += colIncrement) {
                        if (board[destRow][col].getPiece() != null) {
                            return false;
                        }
                    }

                    // The castling maneuver is valid
                    return true;
                }
            }
        }

        // Not a valid move for the king
        return false;
    }

    /**
     * Checks if the king has moved for castling.
     *
     * @return True if the king has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    // DEVELOPED BY JUSTIN
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
