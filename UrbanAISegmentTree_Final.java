import java.util.*;

public class UrbanAISegmentTree {

    int[] segmentTree;
    int size;

    // Constructor
    UrbanAISegmentTree(int[] trafficData) {

        size = trafficData.length;

        segmentTree = new int[4 * size];

        buildTree(trafficData, 0, 0, size - 1);
    }

    // Build Segment Tree
    void buildTree(int[] data,
                   int node,
                   int start,
                   int end) {

        if (start == end) {

            segmentTree[node] = data[start];
        }
        else {

            int mid = (start + end) / 2;

            buildTree(data,
                    2 * node + 1,
                    start,
                    mid);

            buildTree(data,
                    2 * node + 2,
                    mid + 1,
                    end);

            segmentTree[node] =
                    segmentTree[2 * node + 1]
                  + segmentTree[2 * node + 2];
        }
    }

    // Range Query Method
    int rangeQuery(int node,
                   int start,
                   int end,
                   int left,
                   int right) {

        // No Overlap
        if (right < start || left > end)
            return 0;

        // Complete Overlap
        if (left <= start && end <= right)
            return segmentTree[node];

        // Partial Overlap
        int mid = (start + end) / 2;

        int leftSum =
                rangeQuery(
                        2 * node + 1,
                        start,
                        mid,
                        left,
                        right);

        int rightSum =
                rangeQuery(
                        2 * node + 2,
                        mid + 1,
                        end,
                        left,
                        right);

        return leftSum + rightSum;
    }

    // Update Traffic Data
    void update(int node,
                int start,
                int end,
                int index,
                int value) {

        if (start == end) {

            segmentTree[node] = value;
        }
        else {

            int mid = (start + end) / 2;

            if (index <= mid)

                update(
                        2 * node + 1,
                        start,
                        mid,
                        index,
                        value);

            else

                update(
                        2 * node + 2,
                        mid + 1,
                        end,
                        index,
                        value);

            segmentTree[node] =
                    segmentTree[2 * node + 1]
                  + segmentTree[2 * node + 2];
        }
    }

    // Display Traffic Data
    void displayTrafficData(int[] trafficData) {

        System.out.println(
                "\n====== SMART CITY TRAFFIC DATA ======\n");

        for (int i = 0; i < trafficData.length; i++) {

            System.out.println(
                    "Zone "
                  + (i + 1)
                  + " Traffic Count : "
                  + trafficData[i]);
        }
    }

    // Main Method
    public static void main(String[] args) {

        int[] trafficData = {
                120, 250, 180, 300, 220, 150
        };

        UrbanAISegmentTree city =
                new UrbanAISegmentTree(trafficData);

        // Display Initial Traffic Data
        city.displayTrafficData(trafficData);

        // Range Query Analytics
        System.out.println(
                "\n====== TRAFFIC ANALYTICS REPORT ======\n");

        int totalTraffic =
                city.rangeQuery(
                        0,
                        0,
                        city.size - 1,
                        1,
                        4);

        System.out.println(
                "Total Traffic from Zone 2 to Zone 5 : "
              + totalTraffic);

        // Update Traffic Data
        System.out.println(
                "\nUpdating Traffic Data for Zone 3...\n");

        city.update(
                0,
                0,
                city.size - 1,
                2,
                400);

        trafficData[2] = 400;

        // Display Updated Traffic Data
        city.displayTrafficData(trafficData);

        // Updated Query
        int updatedTraffic =
                city.rangeQuery(
                        0,
                        0,
                        city.size - 1,
                        1,
                        4);

        System.out.println(
                "\nUpdated Traffic from Zone 2 to Zone 5 : "
              + updatedTraffic);

        System.out.println(
                "\n====== SMART CITY ANALYTICS COMPLETED ======");
    }
}
