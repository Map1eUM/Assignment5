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
    final int NUM_ROWS = 10;
    final int NUM_COLUMNS = 12;
    final int ARROW_WIDTH = CELLSIZE / 10;
    final int ARROW_HEIGHT = CELLSIZE / 2;
    final int TRIG_HEIGHT = ARROW_WIDTH;
    final int FIGURE_MARGIN = 5;
    //    final int TEXT_MARGIN = FIGURE_MARGIN;
    final int TEXT_STROKE = 8;
    final int TEXT_SIZE = 40;
    final int TEXT_MARGIN = 50;
    //colors
    final int WHITE = color(255);
    final int RED = color(255, 0, 0);
    final int BLACK = color(0);
    final int BLUE = color(0, 0, 255);
    final int YELLOW = color(255, 255, 0);
    //the worst situation is all grid cells are mines.
    int[] mineX = new int[NUM_COLUMNS * NUM_ROWS];
    int[] mineY = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessX = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessY = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessVal = new int[NUM_COLUMNS * NUM_ROWS];
    //number of mines.
    int numMines = 0, numGuess = 0;
    boolean isLost = false, isWin = false;

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
        generateMines(mineX, mineY, 20);
//        drawMines(mineX, mineY, 20);
        drawNums(guessX, guessY, guessVal, numGuess);
        if (isLost) {
            //bigger text words
            drawMines(mineX, mineY, 20);
            textSize(3 * TEXT_SIZE / 2);
            text("GAME OVER! YOU LOST! \n To play again press n", TEXT_MARGIN / 3, height / 2);
        } else if (isWin) {
            //bigger text words
            drawMines(mineX, mineY, 20);
            textSize(3 * TEXT_SIZE / 2);
            text("GAME OVER! YOU WON!\n To play again press n", TEXT_MARGIN / 3, height / 2);
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
            float x1 = column[i] * CELLSIZE + CELLSIZE / 2;
            float y1 = row[i] * CELLSIZE + CELLSIZE / 2 + ARROW_HEIGHT / 3 + FIGURE_MARGIN;
            triangle(x1, y1, x1 - ARROW_WIDTH / 2, y1 + TRIG_HEIGHT, x1 + ARROW_WIDTH / 2, y1 + TRIG_HEIGHT);
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
        if (isLost) return;

        int gridX = getX(mouseX);
        int gridY = getY(mouseY);
//        ~("THE CURRENT ONE IS:"+str(gridX)+" "+str(gridY)+'\n');
        int gridVal = 0;
        for (int i = 0; i < numMines; ++i) {
            if ((int) abs(mineX[i] - gridX) <= 1 && (int) abs(mineY[i] - gridY) <= 1) {
                gridVal++;
//                print(str(mineX[i])+"   ");
//                print(str(mineY[i])+"\n");
            }
        }
        if (searchMines(mineX, mineY, numMines, gridX, gridY)) {
            isLost = true;

        } else {
            numGuess = insertGuess(guessX, guessY, guessVal, numGuess, gridX, gridY, gridVal);
            if (numGuess == NUM_ROWS * NUM_COLUMNS - numMines) isWin = true;
        }
    }

    @Override
    public void keyPressed() {
        if (key == 'n') {
            //restart the game
            isWin = isLost = false;
            numMines = numGuess = 0;
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
        float x1 = column * CELLSIZE + CELLSIZE / 2;
        float y1 = row * CELLSIZE + CELLSIZE / 2 + ARROW_HEIGHT / 3 + FIGURE_MARGIN;
        triangle(x1, y1, x1 - ARROW_WIDTH / 2, y1 + TRIG_HEIGHT, x1 + ARROW_WIDTH / 2, y1 + TRIG_HEIGHT);
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
        strokeWeight(1);
        noFill();
    }

    public static void main(String... args) {
        PApplet.main("Assignment5");
    }
}
