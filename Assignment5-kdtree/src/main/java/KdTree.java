import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by presnakovr on 8/27/2015.
 */
public class KdTree {

    private static class Node {

        private Point2D point;
        private RectHV rect;

        private Node left = null;
        private Node right = null;

        private Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    private static class Coordinates {

        private double xmin, ymin;
        private double xmax, ymax;

        private Coordinates(double xmin, double ymin, double xmax, double ymax) {
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
        }
    }

    private Node root;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        root = insert(root,  p, new Coordinates(0, 0, 1, 1), true);
    }

    private Node insert(Node node, Point2D point, Coordinates coord, boolean compareX) {
        if (node == null) {
            RectHV resultRect = new RectHV(coord.xmin, coord.ymin, coord.xmax, coord.ymax);
            node = new Node(point, resultRect);
            size++;
            return node;
        }
        if (node.point.equals(point)) {
            return node;
        }

        if (compareX) {
            if (point.x() < node.point.x()) {
                node.left = insert(node.left, point,
                        new Coordinates(coord.xmin, coord.ymin, node.point.x(), coord.ymax), !compareX);
            } else {
                node.right = insert(node.right, point,
                        new Coordinates(node.point.x(), coord.ymin, coord.xmax, coord.ymax), !compareX);
            }
        } else {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point,
                        new Coordinates(coord.xmin, coord.ymin, coord.xmax, node.point.y()), !compareX);
            } else {
                node.right = insert(node.right, point,
                        new Coordinates(coord.xmin, node.point.y(), coord.xmax, coord.ymax), !compareX);
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        Node node = find(root, p, true);
        return node != null;
    }

    private Node find(Node node, Point2D point, boolean compareX) {
        if (node == null) {
            return null;
        }
        if (node.point.equals(point)) {
            return node;
        }

        if ((compareX && point.x() < node.point.x()) ||
                (!compareX && point.y() < node.point.y())) {
            return find(node.left, point, !compareX);
        } else {
            return find(node.right, point, !compareX);
        }
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.clear();
        draw(root, true);
    }

    private void draw(Node node, boolean drawVertical) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        Point2D pointt = node.point;
        pointt.draw();
        if (drawVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(pointt.x(), node.rect.ymin(), pointt.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), pointt.y(), node.rect.xmax(), pointt.y());
        }

        draw(node.left, !drawVertical);
        draw(node.right, !drawVertical);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        Stack<Point2D> stack = new Stack<>();
        findAllPointsInRect(root, rect, stack);
        return stack;
    }

    private void findAllPointsInRect(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            stack.push(node.point);
        }
        if (node.left != null && node.left.rect.intersects(rect)) {
            findAllPointsInRect(node.left, rect, stack);
        }
        if (node.right != null && node.right.rect.intersects(rect)) {
            findAllPointsInRect(node.right, rect, stack);
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            return null;
        }
        Point2D minPoint = findNearest(p, root, root.point, true);
        return minPoint;
    }

    private Point2D findNearest(Point2D point, Node node, Point2D minPoint, boolean compareX) {
        if (node == null) {
            return minPoint;
        }
        if (node.point.equals(point)) {
            return node.point;
        }
        if (node.rect.distanceSquaredTo(point) < point.distanceSquaredTo(minPoint)) {
            if (point.distanceSquaredTo(node.point) < point.distanceSquaredTo(minPoint)) {
                minPoint = node.point;
            }

            if ((compareX && point.x() < node.point.x()) ||
                    (!compareX && point.y() < node.point.y())) {
                minPoint = findNearest(point, node.left, minPoint, !compareX);
                minPoint = findNearest(point, node.right, minPoint, !compareX);
            } else {
                minPoint = findNearest(point, node.right, minPoint, !compareX);
                minPoint = findNearest(point, node.left, minPoint, !compareX);
            }
        }

        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
