import java.util.*;

/**
 * This class puts a bug at the middle of a room and allows it to randomly move to an adjacent tile in the room. 
 * The simulation determines how many steps it takes to touch every tile in the room at least once.
 * 
 * @author Larry Baer
 * @version 9/7/21
 */
public class RandomWalk {
    private int width;
    private int height;
    private int[][] grid;
    private int totalSteps = 0;
    private int newTileSteppedOn = 0;
    private int currentX;
    private int currentY;

    /**
     * Default constructor if no parameters are provided.
     */
    public RandomWalk() {
        width = 5;
        height = 5;
        grid = new int[width][height];
    }

    /**
     * @param width Width of room in tiles.
     * @param height Height of room in tiles.
     */
    public RandomWalk(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[width][height];
    }

    /**
     *  Places bug in the center of the room and runs the simulation.
     *  @throws ArrayIndexOutOfBoundsException This may be caused by the user entering an invalid width and/or height such as (0, 0).
     */
    public void runBug() {
        int area = width * height;

        // Place bug in the center of the room.
        currentX = width / 2;
        currentY = height / 2;
        try {
            grid[currentX][currentY] = 1;
            totalSteps++;
            newTileSteppedOn++;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid length and/or width of the room, or leave the parameters empty.");
        }

        // Make bug go to an adjacent tile until every tile in the room has been stepped on.
        while (newTileSteppedOn < area) {
            nextMove();
            totalSteps++;
        }
        printRoom();
    }

    private void nextMove() {
        Random gen = new Random();
        int rndX;
        int rndY;
        int tempX = currentX;
        int tempY = currentY;

        // Compute a new move. If the move is outside of the room or 
        // makes the bug stay in the same location, throw that pair away
        // and compute a new move until a valid move is computed.
        do {
            rndX = gen.nextInt(3) - 1;
            rndY = gen.nextInt(3) - 1;
            currentX = tempX + rndX;
            currentY = tempY + rndY;
        } while (currentX >= width ||
            currentY >= height ||
            currentX < 0 ||
            currentY < 0 ||
            (rndX == 0 && rndY == 0));

        // Check if this tile has been stepped on before & update newTileSteppedOn accordingly.
        // This way, we will know to stop the simulation when newTileSteppedOn is greater than the area of the room.
        if (grid[currentX][currentY] == 0) {
            grid[currentX][currentY] = 1;
            newTileSteppedOn++;
        } else {
            grid[currentX][currentY] += 1;
        }
    }



    /**
     * @return Total number of steps taken at the time of calling the function.
     */
    public int getTotalSteps() {
        return totalSteps;
    }

    /**
     * Prints the room formatted nicely into console
     */
    public void printRoom() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.format("%4d", grid[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RandomWalk randomwalk1 = new RandomWalk(6, 6);
        RandomWalk randomwalk2 = new RandomWalk(10, 10);
        RandomWalk randomwalk3 = new RandomWalk();

        randomwalk1.runBug();
        System.out.println("Total steps: " + randomwalk1.getTotalSteps());
        System.out.println();

        randomwalk2.runBug();
        System.out.println("Total steps: " + randomwalk2.getTotalSteps());
        System.out.println();

        randomwalk3.runBug();
        System.out.println("Total steps: " + randomwalk3.getTotalSteps());
        System.out.println();
    }
}