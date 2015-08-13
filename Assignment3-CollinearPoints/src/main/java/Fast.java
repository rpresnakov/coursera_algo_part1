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

        Map<Double, Set<Double>> foundLines = new HashMap<>();
        // array for storing line segment points
        Point[] lineSegment = new Point[count];
        for (int p = 0; p < count - 3; p++) {
            // base point
            Point basePoint = points[p];

            String basePointString = basePoint.toString();
            int x = Integer.valueOf(basePointString.substring(basePointString.indexOf('(') + 1, basePointString.indexOf(',')));
            int y = Integer.valueOf(basePointString.substring(basePointString.indexOf(' ') + 1, basePointString.indexOf(')')));

            // copy and 'merge' sort all other points excepting base point
            Point[] sorted = Arrays.copyOfRange(points, p + 1, count);
            MergeX.sort(sorted, basePoint.SLOPE_ORDER);

            int ind = 0;
            int j = 0;

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
                            printout(basePoint, lineSegment, ind + 1, foundLines);
                        }
                        ind = 0;
                    }
                }
            }

            if (ind >= 3) {
                printout(basePoint, lineSegment, ind + 1, foundLines);
            }

        }
        //System.out.println("End");
    }

    private static void printout(Point basePoint, Point[] lineSegment, int count, Map<Double, Set<Double>> foundLines) {
        //check if lines were drawn before
        double slope = basePoint.slopeTo(lineSegment[0]);
        Set<Double> foundLinePartB = foundLines.get(slope);
        if (foundLinePartB == null) {
            foundLinePartB = new HashSet<>();
            foundLines.put(slope, foundLinePartB);
        }

        boolean found;
        double idValue;

        //parsing x and y from string as Point API don't allow us to do that eitherway
        String basePointString = basePoint.toString();
        int x = Integer.valueOf(basePointString.substring(basePointString.indexOf('(') + 1, basePointString.indexOf(',')));
        int y = Integer.valueOf(basePointString.substring(basePointString.indexOf(' ') + 1, basePointString.indexOf(')')));

        if (slope == Double.POSITIVE_INFINITY) {
            idValue = x;
        } else if (slope == 0) {
            idValue = y;
        } else {
            idValue = y - x * slope;
            //rounding
            idValue = (long) (idValue * 1000) / 1000.;
        }
        found = foundLinePartB.contains(idValue);

        if (found) {
            return;
        }

        // decide if to print it out or not
        Point[] toSort = new Point[count];
        toSort[0] = basePoint;
        for (int ii = 0; ii < count - 1; ii++) {
            toSort[ii + 1] = lineSegment[ii];
        }
        Arrays.sort(toSort);

        for (int ii = 0; ii < count - 1; ii++) {
            System.out.print(toSort[ii] + " -> ");
        }
        System.out.println(toSort[count - 1]);
        toSort[0].drawTo(toSort[count - 1]);

        foundLinePartB.add(idValue);
    }
}
