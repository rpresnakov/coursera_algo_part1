import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by presnakovr on 8/27/2015.
 */
public class KdTree {

    private static class Node {

        private Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }

        private Point2D point;
        private RectHV rect;

        private Node left = null;
        private Node right = null;
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

        root = insert(root,  p, new RectHV(0, 0, 1, 1), true);
        size++;
    }

    private Node insert(Node node, Point2D point, RectHV rect, boolean compareX) {
        if (node == null) {
            node = new Node(point, rect);
            return node;
        }

        if (compareX) {
            if ( point.x() < node.point.x()) {
                node.left = insert(node.left, point,
                        new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()), !compareX);
            } else {
                node.right = insert(node.right, point,
                        new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()), !compareX);
            }
        } else {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point,
                        new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()), !compareX);
            } else {
                node.right = insert(node.right, point,
                        new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()), !compareX);
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
//        for (Point2D point : points) {
//            if (rect.contains(point)) {
//                stack.push(point);
//            }
//        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        double min = 2;
        Point2D minPoint = null;

//        for (Point2D point : points) {
//            double length = point.distanceSquaredTo(p);
//            if (length < min) {
//                min = length;
//                minPoint = point;
//            }
//        }

        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
