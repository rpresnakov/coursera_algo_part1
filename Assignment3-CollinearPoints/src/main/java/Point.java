/**
 * Created by presnakovr on 8/10/2015.
 */
/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int dy = that.y - y;
        int dx = that.x - x;

        if (dy == 0 && dx == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (dy == 0 && dx != 0) {
            return 0.0;
        }
        if (dx == 0 && dy != 0) {
            return Double.POSITIVE_INFINITY;
        }

        return dy / (double) dx;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int dy = y - that.y;
        int dx = x - that.x;

        if (dy != 0) {
            return dy;
        } else {
            return dx;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double slope1 = Point.this.slopeTo(o1);
                double slope2 = Point.this.slopeTo(o2);

                if (slope2 > slope1) {
                    return -1;
                } else {
                    if (slope2 == slope1) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        };
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
