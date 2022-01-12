import processing.core.PApplet;

public class Temporary extends PApplet {

//public class Assignment5 extends PApplet {

    /*
     *  COMP 1010 SECTION [A03]
     *  INSTRUCTOR: Celion Lalitupe
     *  NAME: James Chen(Ruiyang Chen)
     *  ASSIGNMENT: #5
     *  QUESTION: #5
     *
     *  PURPOSE: A classical game --minesweeper
     */

    //Q1: Set up a grid on the canvas. Draw a "mine" cell
//    and a "number of mines nearby" cell.
//Q2: Add functions to draw the grid, a list of mines and
//    a list of number cells.
//----------------- basic constants ----------------------------------------------
    final int CELLSIZE = 50;
    final int NUM_ROWS = 10;
    final int NUM_COLUMNS = 12;
    final int MAXIMUM_MINES = NUM_COLUMNS * NUM_ROWS / 2;
    // ----------------------- FIGURE DESIGN CONSTANTS ---------------------------
    final int ARROW_WIDTH = CELLSIZE / 10;
    final int ARROW_HEIGHT = CELLSIZE / 2;
    final int TRIG_HEIGHT = ARROW_WIDTH;
    final int FIGURE_MARGIN = 5;
    //-----------------------------TEXT CONSTANTS------------------------------
    final int TEXT_STROKE = 8;
    final int TEXT_SIZE = 3 * CELLSIZE / 4;
    final int FINAL_TEXT_SIZE = 2 * 3 * min(NUM_COLUMNS, NUM_ROWS) * CELLSIZE / (5 * 12);
    final float TEXT_MARGIN = 4 * CELLSIZE / 5;
    //--------------------------------------color constants---------------------------
    final color WHITE = color(255);
    final color RED = color(255, 0, 0);
    final color BLACK = color(0);
    final color BLUE = color(0, 0, 255);
    final color YELLOW = color(255, 255, 0);

    //-----------------------------------GLOBAL VARIABLES-----------------------------
    int[] mineX = new int[MAXIMUM_MINES];
    int[] mineY = new int[MAXIMUM_MINES];
    int[] guessX = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessY = new int[NUM_COLUMNS * NUM_ROWS];
    int[] guessVal = new int[NUM_COLUMNS * NUM_ROWS];
    //number of mines.
    int numMines = 0, numGuess = 0, setMineNumber = MAXIMUM_MINES / 2, score = 0;
    boolean isLost = false, isWin = false;
//----------------------------------------------------------------------------------
    ////settings can be used to set up size with constants.
    //public void settings() {
    //    size(NUM_COLUMNS * CELLSIZE, NUM_ROWS * CELLSIZE);
    //}

    //------------------------------------- built-in override section-----------------------------------
    void setup() {
        size(600, 500); //size MUST be (NUM_COLUMNS*CELLSIZE) by (NUM_ROWS*CELLSIZE);
//        noLoop(); //only draw once
    }

    void draw() {
        background(WHITE);
        drawGrid();
        generateMines(mineX, mineY, setMineNumber);
        //after generateMines the numMines should be same as setMineNumber
//        drawMines(mineX, mineY, numMines);
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

    /*
     *     //main part of the game design
     *  built-in function override.
     *  react to the mouse clicks
     * calculate which cell the mouse clicked and react.
     */
    void mouseClicked() {
        if (isLost || isWin) return;

        int gridX = getColumnNumber(mouseX);
        int gridY = getRowNumber(mouseY);
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
                isWin = true;
            }
        }
    }

    //@Override

    /*
     *     //restart the gamee when press 'n', reset all values.
     *  built-in global variable key and this is built-in function override
     *  new game status(graphics and programming logic)
     * new game will have one more mine if it not reaches MAXIMUM_MINES
     */
    void keyPressed() {
        if (key == 'n') {
            isWin = isLost = false;
            numMines = numGuess = score = 0;
            //increase mine number for harder game
            if (setMineNumber < MAXIMUM_MINES) setMineNumber++;
        }
    }
//------------------------------------- built-in override section end-----------------------------------

    //**add your drawGrid function here**

    /*
     *      draw the grid
     *      global constants
     *      produce a visual grid on screen
     */
    void drawGrid() {
        for (int i = 0; i < NUM_COLUMNS; ++i) {
            for (int j = 0; j < NUM_ROWS; ++j) {
                rect(CELLSIZE * i, CELLSIZE * j, CELLSIZE, CELLSIZE);
            }
        }

    }

    /*
     *  search mines if there's a duplicate mine
     *  pass in parameters.
     *  return if there's already a mine in given position.
     * [What processing or calculations or decisions or actions are done?]
     */
    boolean searchMines(int[] column, int[] row, int numMines, int mineColumn, int mineRow) {
        //column and row are coordinates of mines
        for (int i = 0; i < numMines; ++i) {
            if (column[i] == mineColumn && row[i] == mineRow) return true;
        }
        return false;
    }


    /*
     *  insert mine that make sure not duplicate mine
     *  [Where does it get information (mouse, keyboard, parameters, global variables)?]
     *  [What does it produce? (Graphics, console output, results returned, globals set?)]
     * [What processing or calculations or decisions or actions are done?]
     */
    int insertMines(int[] column, int[] row, int numMines, int mineColumn, int mineRow) {
        if (!searchMines(mineX, mineY, numMines, mineColumn, mineRow)) {
            column[numMines] = mineColumn;
            row[numMines++] = mineRow;
        }
        return numMines;
    }

    /*
    *  search if there's duplicate guess
    *  pass in parameters
      return true if it exists already
    */
    boolean searchGuess(int[] column, int[] row, int numGuess, int guessColumn, int guessRow) {
        for (int i = 0; i < numGuess; ++i) {
            if (column[i] == guessColumn && row[i] == guessRow) return true;
        }
        return false;
    }

    /*
     *  insert the new guess and make sure no duplicate guesses are inserted
     *  pass in
     *  new numGuess and if not duplicate , insert a new guess
     * ordered look up if there's a duplicate
     */
    int insertGuess(int[] column, int[] row, int[] val, int numGuess, int guessColumn, int guessRow, int guessVal) {
        if (!searchGuess(column, row, numGuess, guessColumn, guessRow)) {
            column[numGuess] = guessColumn;
            val[numGuess] = guessVal;
            row[numGuess++] = guessRow;
        }
        return numGuess;
    }


    /*
     *  before game start genenrate MineNum mines in different places.
     *  pass in parameters.
     *  arrays of mines positions.
     *  generated MineNum mines that are not overlapping
     */
    void generateMines(int[] mineX, int[] mineY, int MineNum) {
        // keep generating until there's MineNum mines.
        while (numMines < MineNum) {
            int newMineX = (int) random(NUM_COLUMNS);
            int newMineY = (int) random(NUM_ROWS);
            numMines = insertMines(mineX, mineY, numMines, newMineX, newMineY);
        }
    }

    //**add your drawMines function here**

    /*
     *   draw the mines if game ended
     * pass in parameters and use global constants
     *  graphic output of mines
     * designed figure positions calculated and fit to the canvas.
     */
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


    /*
     *    get the clicked grid column number
     *  pass in the mouse position
     *  return column number
     * simple integer division
     */
    int getColumnNumber(float column) {
        return (int) column / CELLSIZE;
    }

    /*
     *    get the clicked grid row number
     *  pass in the mouse position
     *  return row number
     * simple integer division
     */
    int getRowNumber(float row) {
        return (int) row / CELLSIZE;
    }


    //**add your drawNums function here**

    /*
        draw the guess numbers
    *  arrays and int var passed in.
    *  blue numbers graphic output.
    * calculations of numbers positions have been made
    */
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
    /*
     *  draw one mine on screen(not used)
     *  get column and row and draw in that cell
     *  graphic output
     *   figures designed for the mines
     */
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
    /*
     *  draw one guess number on screen(not used)
     *  pass in parameters
     *  graphic output blue numbers.
     *  text positions are calculated and fit the canvas.
     */
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

    //public static void main(String... args) {
    //    PApplet.main("Assignment5");
    //}
//}

}
