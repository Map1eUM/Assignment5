import processing.core.PApplet;

public class Assignment5 extends PApplet {
 /*
 COMP 1010 Fall 2021
 Assignment 5 - Units 1-19 - Minesweeper
 Q2 Template
 */

//Q1: Set up a grid on the canvas. Draw a "mine" cell
//    and a "number of mines nearby" cell.
//Q2: Add functions to draw the grid, a list of mines and
//    a list of number cells.

    final int CELLSIZE = 50;
    final int NUM_ROWS = 12;
    final int NUM_COLUMNS = 12;
    final int ARROW_WIDTH = CELLSIZE / 10;
    final int ARROW_HEIGHT = CELLSIZE / 2;
    final int TRIG_HEIGHT = ARROW_WIDTH;
    final int FIGURE_MARGIN = 5;
    //    final int TEXT_MARGIN = FIGURE_MARGIN;
    final int TEXT_STROKE = 8;
    final int TEXT_SIZE = 3 * CELLSIZE / 4;
    final int FINAL_TEXT_SIZE = 2 * 3 * min(NUM_COLUMNS, NUM_ROWS) * CELLSIZE / (5 * 12);
    final float TEXT_MARGIN = 4 * CELLSIZE / 5;
    final int MAXIMUM_MINES = NUM_COLUMNS * NUM_ROWS / 2;
    //colors
    final int WHITE = color(255);
    final int RED = color(255, 0, 0);
    final int BLACK = color(0);
    final int BLUE = color(0, 0, 255);
    final int YELLOW = color(255, 255, 0);
    //the worst situation is all grid cells are mines.
    int[] mineX = new int[MAXIMUM_MINES];
    int[] mineY = new int[MAXIMUM_MINES];
    int[] guessX = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessY = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessVal = new int[NUM_COLUMNS * NUM_ROWS];
    //number of mines.
    int numMines = 0, numGuess = 0, setMineNumber = MAXIMUM_MINES / 2, score = 0;
    boolean isLost = false, isWin = false;

    //settings can be used to set up size with constants.
    public void settings() {
        size(NUM_COLUMNS * CELLSIZE, NUM_ROWS * CELLSIZE);
    }


    public void setup() {
//        size(600, 500); //size MUST be (NUM_COLUMNS*CELLSIZE) by (NUM_ROWS*CELLSIZE);
//        noLoop(); //only draw once
    }

    public void draw() {
        background(WHITE);
        drawGrid();
        generateMines(mineX, mineY, setMineNumber);
        //after generateMines the numMines should be same as setMineNumber
        drawMines(mineX, mineY, numMines);
        drawNums(guessX, guessY, guessVal, numGuess);
        if (isLost) {
            //bigger text words
            drawMines(mineX, mineY, numMines);
            textSize(FINAL_TEXT_SIZE);
            text("GAME OVER! YOU LOST! \n To play again press n", TEXT_MARGIN / 3, height / 2);
        } else if (isWin) {
            //bigger text words
            drawMines(mineX, mineY, numMines);
            textSize(FINAL_TEXT_SIZE);
            text("GAME OVER! YOU WON!\n To play again press n \n your score is: " + str(score), TEXT_MARGIN / 3, height / 2);
        }
    }

    //**add your drawGrid function here**
    void drawGrid() {
        for (int i = 0; i < NUM_COLUMNS; ++i) {
            for (int j = 0; j < NUM_ROWS; ++j) {
                rect(CELLSIZE * i, CELLSIZE * j, CELLSIZE, CELLSIZE);
            }
        }

    }

    boolean searchMines(int[] x, int[] y, int numMines, int mineColumn, int mineRow) {
        //x and y are coordinates of mines
        for (int i = 0; i < numMines; ++i) {
            if (x[i] == mineColumn && y[i] == mineRow) return true;
        }
        return false;
    }

    int insertMines(int[] x, int[] y, int numMines, int mineColumn, int mineRow) {
        if (!searchMines(mineX, mineY, numMines, mineColumn, mineRow)) {
            x[numMines] = mineColumn;
            y[numMines++] = mineRow;
        }
        return numMines;
    }

    boolean searchGuess(int[] x, int[] y, int numGuess, int guessColumn, int guessRow) {
        for (int i = 0; i < numGuess; ++i) {
            if (x[i] == guessColumn && y[i] == guessRow) return true;
        }
        return false;
    }

    int insertGuess(int[] x, int[] y, int[] val, int numGuess, int guessColumn, int guessRow, int guessVal) {
        if (!searchGuess(x, y, numGuess, guessColumn, guessRow)) {
            x[numGuess] = guessColumn;
            val[numGuess] = guessVal;
            y[numGuess++] = guessRow;
        }
        return numGuess;
    }

    void generateMines(int[] mineX, int[] mineY, int MineNum) {
        // keep generating until there's MineNum mines.
        while (numMines < MineNum) {
            int newMineX = (int) random(NUM_COLUMNS);
            int newMineY = (int) random(NUM_ROWS);
            numMines = insertMines(mineX, mineY, numMines, newMineX, newMineY);
//            print(str(newMineX) + ' ' + str(newMineY) + ' ' + str(numMines) + "\n");
        }
    }

    //**add your drawMines function here**
    void drawMines(int[] column, int[] row, int numMine) {
        for (int i = 0; i < numMine; ++i) {
            fill(WHITE);
            rect(CELLSIZE * column[i], CELLSIZE * row[i], CELLSIZE, CELLSIZE);
            fill(YELLOW);
            ellipse(CELLSIZE * column[i] + CELLSIZE / 2, CELLSIZE * row[i] + CELLSIZE / 2, CELLSIZE, CELLSIZE);
            fill(BLACK);
            rect(column[i] * CELLSIZE + CELLSIZE / 2 - ARROW_WIDTH / 2, row[i] * CELLSIZE + CELLSIZE / 2 - 2 * ARROW_HEIGHT / 3, ARROW_WIDTH, ARROW_HEIGHT);
            float triangleX1 = column[i] * CELLSIZE + CELLSIZE / 2;
            float triangleY1 = row[i] * CELLSIZE + CELLSIZE / 2 + ARROW_HEIGHT / 3 + FIGURE_MARGIN;
            triangle(triangleX1, triangleY1, triangleX1 - ARROW_WIDTH / 2, triangleY1 + TRIG_HEIGHT, triangleX1 + ARROW_WIDTH / 2, triangleY1 + TRIG_HEIGHT);
            noFill();
        }
    }

    int getX(float x) {
        return (int) x / CELLSIZE;
    }

    int getY(float y) {
        return (int) y / CELLSIZE;
    }

    public void mouseClicked() {
        if (isLost || isWin) return;

        int gridX = getX(mouseX);
        int gridY = getY(mouseY);
        int gridVal = 0;
        for (int i = 0; i < numMines; ++i) {
            if ((int) abs(mineX[i] - gridX) <= 1 && (int) abs(mineY[i] - gridY) <= 1)
                gridVal++;
        }
        if (searchMines(mineX, mineY, numMines, gridX, gridY)) {
            isLost = true;

        } else {
            //get one more score
            ++score;
            numGuess = insertGuess(guessX, guessY, guessVal, numGuess, gridX, gridY, gridVal);
            if (numGuess == NUM_ROWS * NUM_COLUMNS - setMineNumber) {
                isWin = true；
        }
    }

    //    @Override
    public void keyPressed() {
        if (key == 'n') {
            //restart the game
            isWin = isLost = false;
            numMines = numGuess = score = 0;
            //increase mine number for harder game
            if (setMineNumber < MAXIMUM_MINES) setMineNumber++;
        }
    }

    //**add your drawNums function here**
    void drawNums(int[] column, int[] row, int[] val, int numNumber) {
        for (int i = 0; i < numNumber; ++i) {
            fill(WHITE);
            strokeWeight(1);
            rect(CELLSIZE * column[i], CELLSIZE * row[i], CELLSIZE, CELLSIZE);
            fill(BLUE);
            strokeWeight(TEXT_STROKE);
            textSize(TEXT_SIZE);
            text(str(val[i]), CELLSIZE * column[i] + CELLSIZE / 2 - textWidth(str(val[i])) / 2, CELLSIZE * (row[i] + 1) - textDescent());
            strokeWeight(1);
        }
        noFill();
    }

    //**paste your drawMine and drawNum functions from Q1 here**
    //**add your drawMine function here**
    void drawMine(int column, int row) {
        fill(WHITE);
        rect(CELLSIZE * column, CELLSIZE * row, CELLSIZE, CELLSIZE);
        fill(YELLOW);
        ellipse(CELLSIZE * column + CELLSIZE / 2, CELLSIZE * row + CELLSIZE / 2, CELLSIZE, CELLSIZE);
        fill(BLACK);
        rect(column * CELLSIZE + CELLSIZE / 2 - ARROW_WIDTH / 2, row * CELLSIZE + CELLSIZE / 2 - 2 * ARROW_HEIGHT / 3, ARROW_WIDTH, ARROW_HEIGHT);

        //draw the triangle figure.
        float triangleX1 = column * CELLSIZE + CELLSIZE / 2;
        float triangleY1 = row * CELLSIZE + CELLSIZE / 2 + ARROW_HEIGHT / 3 + FIGURE_MARGIN;
        triangle(triangleX1, triangleY1, triangleX1 - ARROW_WIDTH / 2, triangleY1 + TRIG_HEIGHT, triangleX1 + ARROW_WIDTH / 2, triangleY1 + TRIG_HEIGHT);
        noFill();
    }

    //**add your drawNum function here**
    void drawNum(int column, int row, int num) {
        fill(WHITE);
        strokeWeight(1);
        rect(CELLSIZE * column, CELLSIZE * row, CELLSIZE, CELLSIZE);

        fill(BLUE);
        strokeWeight(TEXT_STROKE);
        textSize(TEXT_SIZE);
        text(str(num), CELLSIZE * column + CELLSIZE / 2 - textWidth(str(num)) / 2, CELLSIZE * (row + 1) - textDescent());
        //backtracking
        strokeWeight(1);
        noFill();
    }

    public static void main(String... args) {
        PApplet.main("Assignment5");
    }
}
