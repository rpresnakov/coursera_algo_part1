import java.util.*;

/**
 * Created by presnakovr on 8/10/2015.
 */
public class Fast {
    public static void main(String[] args) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        final String fileName = args[0];
        In file = new In(fileName);
        int count = file.readInt();

        final Point[] points = new Point[count];

        int i = 0;
        int[] coordinates = file.readAllInts();
        while (i < count) {
            points[i] = new Point(coordinates[i * 2], coordinates[i * 2 + 1]);
            points[i].draw();
            i++;
        }

        Arrays.sort(points);
        // array for storing line segment points
        Point[] lineSegment = new Point[count];
        for (int p = 0; p < count; p++) {
            // base point
            Point basePoint = points[p];

            // copy and 'merge' sort all other points excepting base point
            Point[] sorted = points.clone();
            MergeX.sort(sorted, basePoint.SLOPE_ORDER);

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
        //System.out.println("End");
    }

    private static void printout(Point basePoint, Point[] lineSegment, int count) {
        // we rely on that fact that the first base point should be less than the next because
        // originally before outer loop entire array was naturally sorted.
        // And the very first segment printed out should contain the least first point.
        if (basePoint.compareTo(lineSegment[0]) > 0) {
            return;
        }

        System.out.print(basePoint + " -> ");
        for (int ii = 0; ii < count - 1; ii++) {
            System.out.print(lineSegment[ii] + " -> ");
        }
        System.out.println(lineSegment[count - 1]);
        basePoint.drawTo(lineSegment[count - 1]);
    }
}
