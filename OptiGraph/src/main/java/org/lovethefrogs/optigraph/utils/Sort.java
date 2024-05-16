package org.lovethefrogs.optigraph.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

    public void quickSort(ArrayList<List<Double>> list) {
        quicksort(list, 0, list.size() - 1);
    }

    private static void swap(ArrayList<List<Double>> list, int i, int j) {
        List<Double> aux = list.get(i);
        list.set(i, list.get(j));
        list.set(j, aux);
    }

    private static int getMedian(ArrayList<List<Double>> list, int low, int mid, int high) {
        double a = list.get(low).get(list.get(low).size() - 1);
        double b = list.get(mid).get(list.get(mid).size() - 1);
        double c = list.get(high).get(list.get(high).size() - 1);

        return (a >= b && a <= c) || (a >= c && a <= b) ? low :
                (b >= a && b <= c) || (b >= c && b <= a) ? mid :
                        high;
    }

    private static int partition(ArrayList<List<Double>> list, int low, int high) {
        int mid = low + (high - low) / 2;
        int pivotIndex = getMedian(list, low, mid, high);
        swap(list, pivotIndex, high);
        List<Double> pivot = list.get(high);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).get(list.get(j).size() - 1) <= pivot.get(pivot.size() - 1)) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void quicksort(ArrayList<List<Double>> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quicksort(list, low, pivotIndex - 1);
            quicksort(list, pivotIndex + 1, high);
        }
    }

    public void bucketSort(ArrayList<List<Double>> list) {
        int n = list.size();

        double maxValue = Double.MIN_VALUE;
        for (List<Double> sublist : list) {
            double lastElement = sublist.get(sublist.size() - 1);
            if (lastElement > maxValue) {
                maxValue = lastElement;
            }
        }

        int numBuckets = n / 2;
        ArrayList<List<Double>>[] buckets = new ArrayList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (List<Double> sublist : list) {
            int bucketIndex = (int) (sublist.get(sublist.size() - 1) / maxValue * (numBuckets - 1));
            buckets[bucketIndex].add(sublist);
        }

        int index = 0;
        for (ArrayList<List<Double>> bucket : buckets) {
            Collections.sort(bucket, Comparator.comparingDouble(a -> a.get(a.size() - 1)));
            for (List<Double> sublist : bucket) {
                list.set(index++, sublist);
            }
        }
    }

}
