import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by presnakovr on 8/10/2015.
 */
public class BruteCollinearPoints
{
    private LineSegment[] segments;
    private int size;

    public BruteCollinearPoints(Point[] points) {
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
        int count = points.length;
        for (int p = 0; p < count; p++) {
            for (int q = p + 1; q < count; q++) {
                for (int r = q + 1; r < count; r++) {
                    for (int s = r + 1; s < count; s++) {
                        double slopeQ = points[p].slopeTo(points[q]);
                        if (slopeQ != Double.NEGATIVE_INFINITY) {
                            double slopeR = points[p].slopeTo(points[r]);
                            if (slopeR != Double.NEGATIVE_INFINITY && slopeQ == slopeR) {
                                double slopeS = points[p].slopeTo(points[s]);
                                if (slopeS != Double.NEGATIVE_INFINITY && slopeS == slopeR) {
                                    segments[size++] = new LineSegment(points[p], points[s]);
                                }
                            }
                        }
                    }
                }
            }
        }
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
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
