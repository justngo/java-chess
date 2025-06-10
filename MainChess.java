import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// DEVELOPED BY JUSTIN
public class MainChess {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean mainMenuRunning = true;
        int mainMenuSelection;
        GameSystem gs;
        Displayer displayer;
        Square[][] board;

        while (mainMenuRunning) {
            System.out.println("1. Start a new game FIX TIME AND PARAMETER");
            System.out.println("2. Exit");
            System.out.print("Your choice: ");
            mainMenuSelection = input.nextInt();

            if (mainMenuSelection == 1) { // Play game
                // Get the time limit from the user
                // System.out.print("Enter the time limit in HH:mm format: ");
                // String timeLimitInput = input.next();
                // Parse the user input to LocalTime
                // LocalTime timeLimit = LocalTime.parse(timeLimitInput);
                LocalTime timeLimit = LocalTime.parse("00:00"); // TEMP

                // Initialize game system, displayer, and board
                gs = new GameSystem(timeLimit);
                displayer = new Displayer();
                gs.initializePiecePositions();
                board = gs.getBoard();

                // System.out.print("Enter the maximum amount of turns: ");
                // int drawTurns = input.nextInt();
                // gs.setMaxNumOfTurns(drawTurns);
                gs.setMaxNumOfTurns(10);

                Player currentPlayer = gs.getCurrentPlayer();

                // Start the timer for both but pause and then go back to player 0
                currentPlayer.startTimer();
                currentPlayer.pauseTimer();

                gs.switchPlayer();
                currentPlayer = gs.getCurrentPlayer();

                currentPlayer.startTimer();
                currentPlayer.pauseTimer();

                gs.switchPlayer();

                while (true) {
                    String playerColor;
                    currentPlayer = gs.getCurrentPlayer();

                    if (currentPlayer.getPlayerID() == 1) {
                        playerColor = "Black";
                    } else {
                        playerColor = "White";
                    }

                    System.out.println("\n=====================================================");
                    System.out.println(playerColor + " Player's turn.");

                    if (gs.isDraw()) { // If equal amount of turns set by parameter
                        System.out.println("=====================================================");

                        System.out.println("Both players have ran out of turns, game ends in a draw");
                        break;
                    }
                    if (gs.isTimeFinished()) { // If time set by parameter has run out
                        System.out.println("=====================================================");

                        System.out.println(playerColor + " loses as they ran out of time. ");
                        break;
                    }
                    if (!gs.isKingAlive()) { // If King was killed
                        System.out.println("=====================================================");

                        System.out.println(playerColor + " King is dead, they lose the game");
                        break;
                    }
                    if (gs.isCheck()) { // If King is under threat
                        System.out.println("=============================");
                        System.out.println("| WARNING! YOU ARE CHECKED! |");
                        System.out.println("=============================");

                    }

                    System.out.println("-----------------------------------------------------");
                    displayer.printCapturedPieces(gs.getPlayerArray());
                    System.out.println("-----------------------------------------------------");

                    displayer.PrintBoard(board);

                    System.out.println("-----------------------------------------------------");
                    System.out.println(playerColor + " Player's time consumed so far: "
                            + formatTime(currentPlayer.getTimeConsumed()));
                    System.out.println("-----------------------------------------------------");

                    currentPlayer.continueTimer(); // Continue timer for the next player's turn

                    System.out
                            .print("Enter the x-coordinate of the piece you want to move ('-1' to quit): ");
                    int sourceCol = input.nextInt() - 1;
                    if (sourceCol == -2) { // Shortcut to forfeit
                        System.out.println("Game ended forcefully.");
                        break;
                    }
                    System.out.print("Enter the y-coordinate of the piece you want to move               : ");
                    int sourceRow = input.nextInt() - 1;
                    System.out.print("Enter the x-coordinate of the square you want to move to           : ");
                    int destCol = input.nextInt() - 1;
                    System.out.print("Enter the y-coordinate of the square you want to move to           : ");
                    int destRow = input.nextInt() - 1;
                    System.out.println("-----------------------------------------------------");

                    if (gs.isTimeFinished()) {
                        System.out.println(playerColor + " loses as they ran out of time. ");
                        System.out.println("-----------------------------------------------------");

                        break;
                    }

                    if ((sourceCol <= 8 && sourceCol >= 0) || (sourceRow <= 8 && sourceRow >= 0)
                            || (destCol <= 8 && destCol >= 0) || (destRow <= 8 && destRow >= 0)) {

                        int moveStatus = gs.move(sourceRow, sourceCol, destRow, destCol);

                        if (moveStatus == 1) {
                            System.out.println("Moved successfully, turn ending.");
                            System.out.println("=====================================================");
                            currentPlayer.pauseTimer(); // Pause timer after each move
                            gs.setNumOfTurns(gs.getCurrentPlayer(), 1);
                            gs.switchPlayer();
                        } else if (moveStatus == -1) {
                            System.out.println("ERROR: Destination is of same color");
                            System.out.println("=====================================================");

                        } else if (moveStatus == -2) {
                            System.out.println("ERROR: Invalid combination of movement");
                            System.out.println("=====================================================");

                        } else if (moveStatus == -3) {
                            System.out.println("ERROR: Path is not clear");
                            System.out.println("=====================================================");

                        } else if (moveStatus == -4) {
                            System.out.println("ERROR: Invalid castling movement");
                            System.out.println("=====================================================");

                        } else if (moveStatus == -5) {
                            System.out.println("ERROR: Invalid color piece to choose");
                            System.out.println("=====================================================");

                        }
                    } else {
                        System.out.println("ERROR: Invalid parameters!");
                        System.out.println("=====================================================");

                    }
                }

            } else if (mainMenuSelection == 2) {
                mainMenuRunning = false;
                System.out.println("Thank you for playing");
            } else {
                System.out.println("ERROR: Invalid menu selection!");
            }
        }

        // Close the scanner
        input.close();
    }

    // Helper method to format LocalTime as HH:mm:ss
    private static String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }
}
