package ca.neu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Homework - Searching
 * Find the selected number in the list.
 * The only method you can change is the search() method.
 * You can add additional methods if needed, without changing the load() and test() methods.
 */
public class Sorting {

    protected List list = new ArrayList<Integer>();
    private Random random = new Random();
    int RANGE = 9;
    int max = 9999985;


    /**
     * Loading the text files with double numbers
     */
    protected void load(){
        try (Stream<String> stream = Files.lines(Paths.get("numbers.txt"))) {
            stream.forEach(x -> list.add(Integer.parseInt(x)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing of your solution, using 100 shuffled examples
     * @return execution time
     */
    protected long test(){
        Instant start = Instant.now();
        for (int i=0; i<100; i++){
            Collections.shuffle(list, new Random(100));
            sort(list, (Integer) list.get(random.nextInt(list.size())));
        }
        Instant finish = Instant.now();
        return Duration.between(start, finish).toMillis();
    }

    /**
     * Searching method - add your code in here
     * @param list - list to be sorted and the searched
     */
    private void sort(List list, int index)  {

        radixsort(list,list.size());
//        Collections.sort(list);
    }

    public void countsort(List<Integer> arr, int n, int d){
        int[] output = new int[n];
        int[] count = new int[RANGE+1];

        for(int i = 0; i < n; i++){
            count[arr.get(i)/d % 10] += 1;
        }

        for(int i = 1; i <= RANGE; i++){
            count[i] += count[i-1];
        }

        for(int i = n-1; i >= 0; i--){
            output[count[arr.get(i)/d % 10]-1] = arr.get(i);
            count[arr.get(i)/d % 10] -= 1;
        }

        for(int i = 0; i < n; i++){
            arr.set(i,output[i]);
        }
    }

    public void radixsort(List<Integer> arr, int n){

        for(int d = 1; max/d > 0; d *= 10){
            countsort(arr, n, d);
        }
    }



}