import java.time.LocalTime;

/**
 * The `GameSystem` class represents a chess game system with a game board,
 * players, and methods for performing specific chess moves.
 */
public class GameSystem {
   /**
    * The default width of the game board.
    */
   public static int DEFAULT_BOARD_WIDTH = 8;

   /**
    * The default height of the game board.
    */
   public static int DEFAULT_BOARD_HEIGHT = 8;

   /**
    * Represents the game board as a two-dimensional array of squares.
    */
   private Square[][] board;

   /**
    * Represents the array of players participating in the game.
    */
   private Player[] players;

   /**
    * Represents the current player making a move.
    */
   private Player currentPlayer;

   /**
    * Private method to perform King-side castling.
    *
    * @param kingSourceCol The column of the king's source square.
    * @param kingDestCol   The column of the king's destination square.
    * @param rookSourceCol The column of the rook's source square.
    * @param rookDestCol   The column of the rook's destination square.
    * @param row           The row of the castling move.
    */
   private void performKingSideCastling(int kingSourceCol, int kingDestCol, int rookSourceCol, int rookDestCol,
         int row) {
      Square kingSourceSquare = board[row][kingSourceCol];
      Square kingDestSquare = board[row][kingDestCol];
      Square rookSourceSquare = board[row][rookSourceCol];
      Square rookDestSquare = board[row][rookDestCol];

      // Move the king for castling
      kingDestSquare.setPiece(kingSourceSquare.getPiece());
      kingSourceSquare.setPiece(null);

      // Move the rook for castling
      rookDestSquare.setPiece(rookSourceSquare.getPiece());
      rookSourceSquare.setPiece(null);
   }

   // DEVELOPED BY RAHUL
   /**
    * Private method to perform Queen-side castling.
    *
    * @param kingSourceCol The column of the king's source square.
    * @param kingDestCol   The column of the king's destination square.
    * @param rookSourceCol The column of the rook's source square.
    * @param rookDestCol   The column of the rook's destination square.
    * @param row           The row of the castling move.
    */
   private void performQueenSideCastling(int kingSourceCol, int kingDestCol, int rookSourceCol, int rookDestCol,
         int row) {
      Square kingSourceSquare = board[row][kingSourceCol];
      Square kingDestSquare = board[row][kingDestCol];
      Square rookSourceSquare = board[row][rookSourceCol];
      Square rookDestSquare = board[row][rookDestCol];

      // Move the king for queen-side castling
      kingDestSquare.setPiece(kingSourceSquare.getPiece());
      kingSourceSquare.setPiece(null);

      // Move the rook for queen-side castling
      rookDestSquare.setPiece(rookSourceSquare.getPiece());
      rookSourceSquare.setPiece(null);
   }

   // DEVELOPED BY RAHUL
   /**
    * Private helper method to check if the path is clear between two squares
    * horizontally or vertically.
    *
    * @param sourceRow The row of the source square.
    * @param sourceCol The column of the source square.
    * @param destRow   The row of the destination square.
    * @param destCol   The column of the destination square.
    * @return True if the path is clear, false otherwise.
    */
   private boolean isPathClear(int sourceRow, int sourceCol, int destRow, int destCol) {
      if (sourceRow == destRow) {
         // Check for a clear path horizontally
         int minCol = Math.min(sourceCol, destCol);
         int maxCol = Math.max(sourceCol, destCol);

         for (int col = minCol + 1; col < maxCol; col++) {
            if (board[sourceRow][col].getPiece() != null) {
               // There is a piece in the horizontal path
               return false;
            }
         }
      } else if (sourceCol == destCol) {
         // Check for a clear path vertically
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
      return true;
   }

   // -----------------PUBLIC METHODS/CONSTRUCTOR -----------------

   /**
    * Default constructor for the `GameSystem` class. Initializes the game board,
    * players, and sets the current player to the first player.
    */
   // DEVELOPED BY RAHUL
   public GameSystem(LocalTime timeLimit) {
      board = new Square[DEFAULT_BOARD_WIDTH][DEFAULT_BOARD_HEIGHT];
      // Initialize each Square object in the board array
      for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++) {
         for (int j = 0; j < DEFAULT_BOARD_HEIGHT; j++) {
            board[i][j] = new Square();
         }
      }
      // Initialize players array
      players = new Player[2];
      players[0] = new Player(0, timeLimit);
      players[1] = new Player(1, timeLimit);
      setCurrentPlayer(0);
   }

   /**
    * Gets the current state of the game board.
    *
    * @return A two-dimensional array representing the current game board.
    */
   // DEVELOPED BY RAHUL
   public Square[][] getBoard() {
      return board;
   }

   /**
    * Initializes the positions of chess pieces on the game board at the beginning
    * of the game.
    */
   // DEVELOPED BY RAHUL
   public void initializePiecePositions() {
      // Initialize pieces for the white player
      board[0][0].setPiece(new Rook("W Rook", true));
      board[0][1].setPiece(new Knight("W Knight", true));
      board[0][2].setPiece(new Bishop("W Bishop", true));
      board[0][3].setPiece(new Queen("W Queen", true));
      board[0][4].setPiece(new King("W King", true));
      board[0][5].setPiece(new Bishop("W Bishop", true));
      board[0][6].setPiece(new Knight("W Knight", true));
      board[0][7].setPiece(new Rook("W Rook", true));

      for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++) {
         board[1][i].setPiece(new Pawn("W Pawn", true));
      }

      // Initialize pieces for the black player
      board[7][0].setPiece(new Rook("B Rook", false));
      board[7][1].setPiece(new Knight("B Knight", false));
      board[7][2].setPiece(new Bishop("B Bishop", false));
      board[7][3].setPiece(new Queen("B Queen", false));
      board[7][4].setPiece(new King("B King", false));
      board[7][5].setPiece(new Bishop("B Bishop", false));
      board[7][6].setPiece(new Knight("B Knight", false));
      board[7][7].setPiece(new Rook("B Rook", false));

      for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++) {
         board[6][i].setPiece(new Pawn("B Pawn", false));
      }

      // Initialize the rest of the board with empty squares
      for (int i = 2; i < 6; i++) {
         for (int j = 0; j < DEFAULT_BOARD_WIDTH; j++) {
            board[i][j] = new Square();
         }
      }
   }

   /**
    * Sets the current player based on the provided player ID.
    *
    * @param playerID The ID of the player to set as the current player.
    */
   // DEVELOPED BY RAHUL
   public void setCurrentPlayer(int playerID) {
      currentPlayer = players[playerID];
   }

   /**
    * Gets the current player making a move.
    *
    * @return The current player object.
    */
   // DEVELOPED BY RAHUL
   public Player getCurrentPlayer() {
      return currentPlayer;
   }

   /**
    * Gets the player object based on the provided player ID.
    *
    * @param playerID The ID of the player to retrieve.
    * @return The player object corresponding to the provided player ID.
    */
   // DEVELOPED BY RAHUL
   public Player getPlayer(int playerID) {
      return players[playerID];
   }

   /**
    * Switches the current player between players[0] and players[1].
    */
   // DEVELOPED BY RAHUL
   public void switchPlayer() {
      if (currentPlayer.getPlayerID() == 0) {
         this.currentPlayer = players[1];
      } else {
         this.currentPlayer = players[0];
      }
   }

   /**
    * Moves a chess piece from the source square to the destination square on the
    * game board.
    * Utilizes movement rules of the pieces and handles various scenarios,
    * returning
    * specific codes to indicate the result of the move.
    *
    * @param sourceRow The row index of the source square.
    * @param sourceCol The column index of the source square.
    * @param destRow   The row index of the destination square.
    * @param destCol   The column index of the destination square.
    * @return -1 if the destination is of the same color, -2 for an invalid
    *         combination
    *         of movement, -3 for an unclear path, -4 for invalid castling
    *         movement,
    *         and 1 for a valid movement.
    *
    *         DEVELOPED BY RAHUL
    *         // Utilizes Movement of the pieces
    *         // Returns -1 if the destination is of the same color
    *         // Returns -2 for invalid combination of movement
    *         // Returns -3 for unclear path
    *         // Returns -4 for invalid castling movement
    *         // Returns -5 for invalid color piece to choose
    *         // Returns 1 for valid movement
    */
   public int move(int sourceRow, int sourceCol, int destRow, int destCol) {
      Square sourceSquare = board[sourceRow][sourceCol];
      Square destSquare = board[destRow][destCol];

      Piece pieceToMove = sourceSquare.getPiece();

      if (sourceSquare.getPiece() == null) {
         return -6;
      }

      // If player 0 source color choice is not white or if player 1 source color
      // choice is not black
      if (!(currentPlayer.getPlayerID() == 0 && sourceSquare.getPiece().getIsWhite()
            || currentPlayer.getPlayerID() == 1 && !(sourceSquare.getPiece().getIsWhite()))) {
         // Wrong color piece to choose
         return -5;
      }

      // Check if the move is valid for the specific piece
      if (pieceToMove.isValidMove(sourceRow, sourceCol, destRow, destCol, board)) {
         // Check if the destination square is empty or has an opponent's piece

         if (destSquare.getPiece() == null || destSquare.getPiece().getIsWhite() != pieceToMove.getIsWhite()) {
            // Capture the opponent's piece if it exists

            if (destSquare.getPiece() != null) {
               // Handle capture logic directly, as there is no separate capture method
               destSquare.getPiece().setCaptured(true);
               currentPlayer.capturePiece(destSquare.getPiece());
            }

            // Check for king-side castling (king moving two squares horizontally)
            if (pieceToMove instanceof King && destCol - sourceCol == 2) {
               // Determine the positions for king-side castling
               int kingDestCol;
               int rookSourceCol;
               int rookDestCol;

               if (destCol > sourceCol) {
                  kingDestCol = destCol;
                  rookSourceCol = board[destRow].length - 1;
                  rookDestCol = destCol - 1;
               } else {
                  kingDestCol = sourceCol;
                  rookSourceCol = 0;
                  rookDestCol = destCol + 1;
               }

               // Check for a clear path in the horizontal movement
               if (isPathClear(sourceRow, sourceCol, destRow, kingDestCol)) {
                  // Perform king-side castling
                  performKingSideCastling(sourceCol, kingDestCol, rookSourceCol, rookDestCol, sourceRow);
                  return 1;
               } else {
                  // Path is not clear for castling
                  return -4;
               }
            }

            // Check for queen-side castling (king moving two squares horizontally to the
            // left)
            if (pieceToMove instanceof King && destCol - sourceCol == 2) {
               // Determine the positions for king-side castling
               int kingDestCol = destCol;
               int rookSourceCol = board[destRow].length - 1;
               int rookDestCol = destCol - 1;

               // Check for a clear path in the horizontal movement
               if (isPathClear(sourceRow, sourceCol, destRow, kingDestCol)) {
                  // Perform king-side castling
                  performKingSideCastling(sourceCol, kingDestCol, rookSourceCol, rookDestCol, sourceRow);
                  return 1;
               } else {
                  // Path is not clear for castling
                  return -4;
               }
            }

            // Check for queen-side castling (king moving two squares horizontally to the
            // left)
            if (pieceToMove instanceof King && sourceCol - destCol == 2) {
               // Determine the positions for queen-side castling
               int kingDestCol = sourceCol - 2;
               int rookSourceCol = 0;
               int rookDestCol = sourceCol - 1;

               // Check for a clear path in the horizontal movement
               if (isPathClear(sourceRow, sourceCol, destRow, kingDestCol)) {
                  // Perform queen-side castling
                  performQueenSideCastling(sourceCol, kingDestCol, rookSourceCol, rookDestCol, sourceRow);
                  return 1;
               } else {
                  // Path is not clear for castling
                  return -4;
               }
            }

            // Check for a clear path in the horizontal or vertical movement
            if (isPathClear(sourceRow, sourceCol, destRow, destCol)) {
               // Move the piece to the destination square
               destSquare.setPiece(pieceToMove);
               sourceSquare.setPiece(null);

               return 1;
            } else {
               // Path is not clear
               return -3;
            }
         } else {
            // Destination square has a piece of the same color (invalid move)
            return -1;
         }
      } else {
         // Invalid move for the piece
         return -2;
      }
   }

   /**
    * Goes through every piece to check if it puts opponent king under threat
    * 
    * DEVELOPED BY JUSTIN
    */
   public boolean isCheck() {
      boolean currentKingColor;
      int currentKingRow = 0;
      int currentKingCol = 0;
      Piece pieceToCheck;

      if (currentPlayer == players[0]) {
         currentKingColor = true; // Set current king color to white if first player's turn
      } else {
         currentKingColor = false; // Set current king color to black if second player's turn
      }

      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] != null && board[row][col].getPiece() != null) { // Check if block is empty
               if (board[row][col].getPiece() instanceof King) { // Check if block is king
                  if (board[row][col].getPiece().getIsWhite() == currentKingColor) { // Check if king is same color as
                                                                                     // current player
                     currentKingRow = row;
                     currentKingCol = col;
                  }
               }
            }
         }
      }
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] != null && board[row][col].getPiece() != null) { // Check if block is empty
               if ((board[row][col].getPiece().getIsWhite() != board[currentKingRow][currentKingCol].getPiece()
                     .getIsWhite())) {
                  pieceToCheck = board[row][col].getPiece();
                  if (pieceToCheck.isValidMove(row, col, currentKingRow, currentKingCol, board)) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * Checks whether the king is alive or captured // on the board.
    *
    * @return boolean Whether the king is on the board or has been captured.
    *
    *         DEVELOPED BY JUSTIN
    */
   public boolean isKingAlive() {
      boolean currentKingColor;

      if (currentPlayer == players[0]) {
         currentKingColor = true; // Set current king color to white if first player's turn
      } else {
         currentKingColor = false; // Set current king color to black if second player's turn
      }

      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] != null && board[row][col].getPiece() != null) { // Check if block is empty
               if (board[row][col].getPiece() instanceof King) { // Check if block is king
                  if (board[row][col].getPiece().getIsWhite() == currentKingColor) { // Check if king is same color as
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * Compares the number of current turns to the user-set limit of turns.
    *
    * @return boolean whether the number of turns exceeds limit.
    *
    *         DEVELOPED BY JUSTIN
    */
   public boolean isDraw() {
      if (players[0].getNumOfTurns() >= players[0].getMaxNumOfTurns()
            && players[1].getNumOfTurns() >= players[1].getMaxNumOfTurns()) {
         return true;
      } else {
         return false;
      }
   }

   /**
    * Set upper limit of turns.
    *
    * @param num The limit input by the user.
    *
    *            DEVELOPED BY JUSTIN
    */
   public void setMaxNumOfTurns(int num) {
      players[0].setMaxNumOfTurns(num);
      players[1].setMaxNumOfTurns(num);
   }

   /**
    * Gets the number of turns a player has completed
    *
    * @return number of turns.
    *
    *         DEVELOPED BY JUSTIN
    */
   public int getNumOfTurns(Player player) {
      return player.getNumOfTurns();
   }

   /**
    * Set the number of terms per specified playerID.
    *
    * @param player The ID of the player to set.
    * @param num    The number to increase turns by.
    *
    *               DEVELOPED BY JUSTIN
    */
   public void setNumOfTurns(Player player, int num) {
      player.setNumOfTurns(getNumOfTurns(player) + num);
   }

   /**
    * Checks whether the time has been completed.
    *
    * @return method from Player that compares each players elapsed time.
    *
    *         DEVELOPED BY JUSTIN
    */
   public boolean isTimeFinished() {
      return getCurrentPlayer().getIsTimeFinished();
   }

   /**
    * Gets the player array to use in main to display captured pieces.
    *
    * @return player array.
    *
    * DEVELOPED BY JUSTIN
    */
   public Player[] getPlayerArray() {
      return players;
   }
}
