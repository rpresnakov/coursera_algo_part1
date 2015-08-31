import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by presnakovr on 8/27/2015.
 */
public class PointSET {

    private Set<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        Stack<Point2D> stack = new Stack<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                stack.push(point);
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        double min = 3;
        Point2D minPoint = null;

        for (Point2D point : points) {
            double length = point.distanceSquaredTo(p);
            if (length < min) {
                min = length;
                minPoint = point;
            }
        }

        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
