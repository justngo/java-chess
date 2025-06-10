/**
 * The {@code Square} class represents a square on a game board.
 * Each square may contain a {@link Piece}.
 * <p>
 * This class is developed by Rahul.
 */
public class Square {
    private Piece piece;
    public boolean getIsWhite;

    /**
     * Sets the piece on this square.
     *
     * @param piece The piece to be placed on the square.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gets the piece currently on this square.
     *
     * @return The piece on the square, or {@code null} if no piece is present.
     */
    public Piece getPiece() {
        return piece;
    }
    
}
