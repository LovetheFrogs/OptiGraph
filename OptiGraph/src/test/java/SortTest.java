import junit.framework.TestCase;
import org.lovethefrogs.optigraph.utils.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortTest extends TestCase {
    protected Sort sorter;
    protected ArrayList<ArrayList<List<Double>>> list = new ArrayList<>();

    @Override
    protected void setUp() throws Exception {
        sorter = new Sort();
        ArrayList<List<Double>> list1 = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            list1.add(List.of(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble()
            ));
        }
        ArrayList<List<Double>> list2 = new ArrayList<>();
        for (int i = 0; i < 4500; i++) {
            list2.add(List.of(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble()
            ));
        }
        ArrayList<List<Double>> list3 = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list3.add(List.of(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble()
            ));
        }
        ArrayList<List<Double>> list4 = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list4.add(List.of(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble()
            ));
        }
        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);
    }

    public void testQuickSort() {
        System.out.println("QuickSort time results: ");
        for (ArrayList<List<Double>> a : list) {
            long startTime = System.nanoTime();
            sorter.quickSort(a);
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1000000;
            System.out.println("time: " + executionTime + "ms");
        }
    }

    public void testBucketSort() {
        System.out.println("BucketSort time results: ");
        for (ArrayList<List<Double>> a : list) {
            long startTime = System.nanoTime();
            sorter.bucketSort(a);
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1000000;
            System.out.println("time: " + executionTime + "ms");
        }
    }
}
