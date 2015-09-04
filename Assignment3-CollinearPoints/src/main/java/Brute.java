import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

/**
 * Created by presnakovr on 8/10/2015.
 */
public class Brute
{

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

        for (int p = 0; p < count; p++) {
            for (int q = p + 1; q < count; q++) {
                for (int r = q + 1; r < count; r++) {
                    for (int s = r + 1; s < count; s++) {
                        if (p != q && p != r && p != s && q != r && r != s && q != s) {
                            double slopeQ = points[p].slopeTo(points[q]);
                            double slopeR = points[p].slopeTo(points[r]);
                            double slopeS = points[p].slopeTo(points[s]);
                            if ((slopeQ != Double.NEGATIVE_INFINITY && slopeR != Double.NEGATIVE_INFINITY && slopeS != Double.NEGATIVE_INFINITY)
                                    && (slopeQ == slopeR && slopeR == slopeS && slopeQ == slopeS)) {
                                    Point[] sortedPoints = new Point[] {points[p], points[q], points[r], points[s]};
                                    Arrays.sort(sortedPoints);
                                    System.out.print(sortedPoints[0] + " -> ");
                                    System.out.print(sortedPoints[1] + " -> ");
                                    System.out.print(sortedPoints[2] + " -> ");
                                    System.out.println(sortedPoints[3]);

                                    sortedPoints[0].drawTo(sortedPoints[3]);
                            }
                        }
                    }
                }
            }
        }
    }
}
