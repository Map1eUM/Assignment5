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
    final int NUM_COLS = 12;
    final int ARROW_WIDTH = CELLSIZE / 10;
    final int ARROW_HEIGHT = CELLSIZE / 2;
    final int TRIG_HEIGHT = ARROW_WIDTH;
    final int FIGURE_MARGIN = 5;
    //    final int TEXT_MARGIN = FIGURE_MARGIN;
    final int TEXT_STROKE = 8;
    final int TEXT_SIZE = 40;
    //colors
    final int WHITE = color(255);
    final int RED = color(255, 0, 0);
    final int BLACK = color(0);
    final int BLUE = color(0, 0, 255);
    final int YELLOW = color(255, 255, 0);
    //the worst situation is all grid cells are mines.
    int[] mineX = new int[NUM_COLS * NUM_ROWS];
    int[] mineY = new int[NUM_COLS * NUM_ROWS];
    //number of mines.
    int n = 0;

    public void settings() {
        size(NUM_COLS * CELLSIZE, NUM_ROWS * CELLSIZE);
    }

    public void setup() {
//        size(600, 500); //size MUST be (NUM_COLS*CELLSIZE) by (NUM_ROWS*CELLSIZE);
//        noLoop(); //only draw once
    }

    public void draw() {
        drawGrid();

//        //create test arrays
//        int[] mineX = {3, 1, 8, 11, 8, 4}; //mine column numbers
//        int[] mineY = {4, 0, 7, 9, 2, 1}; //mine row numbers
//
//        drawMines(mineX, mineY, 6); //fill the cells with mines
//
//        int[] numX = {0, 10, 5, 2, 6, 7, 1, 11, 6};
//        int[] numY = {2, 3, 6, 8, 0, 9, 6, 1, 4};
//        int[] numValue = {3, 1, 4, 2, 5, 6, 0, 7, 8};
//
//        drawNums(numX, numY, numValue, 9);
        generateMines(mineX,mineY,20);
        drawMines(mineX,mineY,20);

    }

    //**add your drawGrid function here**
    void drawGrid() {
        for (int i = 0; i < NUM_COLS; ++i) {
            for (int j = 0; j < NUM_ROWS; ++j) {
                rect(CELLSIZE * i, CELLSIZE * j, CELLSIZE, CELLSIZE);
            }
        }

    }

    boolean search(int[] x, int[] y, int n, int c, int r) {
        for (int i = 0; i < n; ++i) {
            if (x[i] == c && y[i] == r) return true;
        }
        return false;
    }

    int insert(int[] x, int[] y, int n, int c, int r) {
        if (!search(mineX, mineY, n, c, r)) {
            x[n] = c;
            y[n++] = r;
        }
        return n;
    }
    void generateMines(int[] mineX, int[] mineY, int MineNum) {
        while (n < MineNum) {
            int newMineX = (int) random(NUM_COLS + 1);
            int newMineY = (int) random(NUM_ROWS + 1);
            n = insert(mineX, mineY, n, newMineX, newMineY);
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
        }
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
    }

    public static void main(String... args) {
        PApplet.main("Assignment5");
    }
}
