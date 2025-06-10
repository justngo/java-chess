import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code Player} class represents a player with a unique ID, a timer,
 * and methods for managing time-related activities.
 * 
 * This class includes functionality for starting, stopping, pausing, and
 * continuing a timer. It also provides methods to retrieve the time consumed
 * and the time passed in minutes.
 * 
 */
public class Player {

    /**
     * The unique identifier for the player.
     * 
     * DEVELOPED BY RAHUL
     */
    private int playerID;

    /**
     * The time consumed by the player, initialized to 0 hours and 0 minutes.
     * 
     * DEVELOPED BY RAHUL
     */
    private LocalTime timeConsumed = LocalTime.of(0, 0);

    /**
     * The time limit for the player's activities.
     * 
     * DEVELOPED BY RAHUL
     */
    private LocalTime timeLimit;

    /**
     * The timer used to track player time.
     * 
     * DEVELOPED BY RAHUL
     */
    private Timer timer;

    /**
     * Flag indicating whether the timer is paused.
     * 
     * DEVELOPED BY RAHUL
     */
    private boolean isTimerPaused = false;

    // DEVELPED BY RAHUL
    private boolean isTimeFinished = false;
    // DEVELOPED BY RAHUL

    /**
     * The count of captured pieces.
     * 
     * DEVELOPED BY RAHUL
     */
    private int capturedCount;

    /**
     * The array of captured pieces by the player.
     * 
     * DEVELOPED BY RAHUL
     */
    private Piece[] capturedPieces;

    private int numOfTurns = 0;

    private int maxNumOfTurns = 50;

    /**
     * Constructs a new player with the specified ID.
     * 
     * @param id The unique identifier for the player.
     * 
     *           DEVELOPED BY RAHUL
     */
    public Player(int id) {
        playerID = id;
        capturedPieces = new Piece[16];
        capturedCount = 0;
    }

    /**
     * Constructs a new player with the specified ID and time limit.
     * 
     * @param id        The unique identifier for the player.
     * @param timeLimit The time limit for the player's activities.
     * 
     *                  DEVELOPED BY RAHUL
     */
    public Player(int id, LocalTime timeLimit) {
        playerID = id;
        this.timeLimit = timeLimit;
        capturedPieces = new Piece[16];
        capturedCount = 0;
    }

    /**
     * Fetches the player ID.
     * 
     * @return the playerID.
     * 
     *         DEVELOPED BY JUSTIN
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Adds a captured piece to the player's array.
     *
     * @param capturedPiece The piece captured by the player.
     * 
     *                      DEVELOPED BY RAHUL
     */
    public void capturePiece(Piece capturedPiece) {
        if (capturedCount < capturedPieces.length) {
            capturedPieces[capturedCount++] = capturedPiece;
        }
    }

    /**
     * Gets the array of captured pieces.
     *
     * @return The array of captured pieces.
     * 
     *         DEVELOPED BY RAHUL
     */
    public Piece[] getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * Gets the count of captured pieces.
     *
     * @return The count of captured pieces.
     * 
     *         DEVELOPED BY RAHUL
     */
    public int getCapturedCount() {
        return capturedCount;
    }

    /**
     * Starts the timer for the player. The timer increments the time consumed by
     * one second at regular intervals.
     * 
     * DEVELOPED BY RAHUL
     */
    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!isTimerPaused) {
                    // Increment timeConsumed by one second
                    timeConsumed = timeConsumed.plusSeconds(1);

                    // Check if the timeLimit is reached
                    if (timeLimit != null && timeConsumed.compareTo(timeLimit) >= 0) {
                        isTimeFinished = true;
                    }
                }
            }
        }, 0, 1000); // Start immediately and repeat every 1000 milliseconds (1 second)
    }

    /**
     * To get if the time is finished.
     * 
     * DEVELOPED BY RAHUL
     */
    public boolean getIsTimeFinished() {
        return isTimeFinished;
    }

    /**
     * Pauses the player's timer.
     * 
     * DEVELOPED BY RAHUL
     */
    public void pauseTimer() {
        isTimerPaused = true;
    }

    /**
     * Continues the player's timer from where it was paused.
     * 
     * DEVELOPED BY RAHUL
     */
    public void continueTimer() {
        isTimerPaused = false;
    }

    /**
     * Gets the total time consumed by the player.
     * 
     * @return The time consumed by the player.
     * 
     *         DEVELOPED BY RAHUL
     */
    public LocalTime getTimeConsumed() {
        return timeConsumed;
    }

    /**
     * Gets the total time passed in minutes.
     * 
     * @return The time passed in minutes.
     * 
     *         DEVELOPED BY RAHUL
     */
    public long getTimePassedInMinutes() {
        return timeConsumed.toSecondOfDay() / 60;
    }

    /**
     * Set maximum number of turns.
     * 
     * @param newMax Max num of turns set by user.
     * 
     *         DEVELOPED BY JUSTIN
     */
    public void setMaxNumOfTurns(int newMax) {
        this.maxNumOfTurns = newMax;
    }

    /**
     * Get maximum number of turns.
     * 
     * @return the maximum number of turns possible set by user.
     * 
     *         DEVELOPED BY JUSTIN
     */
    public int getMaxNumOfTurns() {
        return this.maxNumOfTurns;
    }

    /**
     * Get current number of turns.
     * 
     * @return a player's number of turns completed.
     * 
     *         DEVELOPED BY JUSTIN
     */
    public int getNumOfTurns() {
        return this.numOfTurns;
    }

    /**
     * Set a number of turns.
     * 
     * @param newNum New num of turns set by user.
     * 
     *         DEVELOPED BY JUSTIN
     */
    public void setNumOfTurns(int newNum) {
        this.numOfTurns = newNum;
    }
}
