import processing.core.PApplet;

public class Assignment5 extends PApplet {
    /*
 COMP 1010 Fall 2021
 Assignment 5 - Units 1-19 - Minesweeper
 Question 1 Template
 */

//Q1: Set up a grid on the canvas. Draw a "mine" cell
//    and a "number of mines nearby" cell.

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

    public void settings() {
        size(NUM_COLS * CELLSIZE, NUM_ROWS * CELLSIZE);
    }

    public void setup() {
//        size(600, 500); //siz`e MUST be (NUM_COLS*CELLSIZE) by (NUM_ROWS*CELLSIZE);
        noLoop(); //only draw once
    }

    public void draw() {
        drawMine(3, 4);  //draw a mine in col 3, row 4
        drawMine(1, 0);  //draw a mine in col 1, row 0
        drawMine(8, 7);  //draw a mine in col 8, row 7
        drawMine(11, 9);  //draw a mine in col 11, row 9

        drawNum(0, 2, 3); //draw a 3 in col 0, row 2
        drawNum(10, 3, 1); //draw a 1 in col 10, row 3
        drawNum(5, 6, 4); //draw a 4 in col 5, row 6
        drawNum(2, 8, 2); //draw a 2 in col 2, row 8
    }

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
//        fill(BLUE);
        fill(WHITE);
        strokeWeight(1);
        rect(CELLSIZE * column, CELLSIZE * row, CELLSIZE, CELLSIZE);
//        stroke(BLUE);
        fill(BLUE);
        strokeWeight(TEXT_STROKE);
        textSize(TEXT_SIZE);
        text(str(num), CELLSIZE * column + CELLSIZE / 2 - textWidth(str(num)) / 2, CELLSIZE * (row + 1) - textDescent());
//        strokeWeight(1);
//        stroke(BLACK);
    }

    public static void main(String... args) {
        PApplet.main("Assignment5");
    }
}
