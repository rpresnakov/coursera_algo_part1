import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by presnakovr on 8/10/2015.
 */
public class FastCollinearPoints {

    private LineSegment[] segments;
    private int size;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
        Point[] newPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);
        for (int i = 0; i < newPoints.length - 1; i++) {
            if (newPoints[i].compareTo(newPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new LineSegment[newPoints.length * newPoints.length];
        size = 0;
        calculate(newPoints);
    }

    public int numberOfSegments() {
        return size;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, size);
    }

    private void calculate(Point[] points) {
        // array for storing line segment points
        Point[] lineSegment = new Point[points.length];
        for (int p = 0; p < points.length; p++) {
            // base point
            Point basePoint = points[p];

            // copy and 'merge' sort all other points excepting base point
            Point[] sorted = points.clone();
            MergeX.sort(sorted, basePoint.slopeOrder());

            int ind = 0;
            int j = 1;

            while (j < sorted.length) {
                if (ind == 0) {
                    lineSegment[ind] = sorted[j];

                    ind++;
                    j++;
                } else {
                    if (basePoint.slopeTo(lineSegment[ind - 1]) == basePoint.slopeTo(sorted[j])) {
                        lineSegment[ind] = sorted[j];
                        ind++;
                        j++;
                    } else {
                        if (ind >= 3) {
                            printout(basePoint, lineSegment, ind);
                        }
                        ind = 0;
                    }
                }
            }

            if (ind >= 3) {
                printout(basePoint, lineSegment, ind);
            }

        }
    }

    private void printout(Point basePoint, Point[] segment, int count) {
        // we rely on that fact that the first base point should be less than the next because
        // originally before outer loop entire array was naturally sorted.
        // And the very first segment printed out should contain the least first point.
        if (basePoint.compareTo(segment[0]) > 0) {
            return;
        }
        segments[size++] = new LineSegment(basePoint, segment[count - 1]);
    }

    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.0005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
