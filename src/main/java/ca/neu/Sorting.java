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

  /**
   * Loading the text files with double numbers
   */
  protected void load() {
    try (Stream<String> stream = Files.lines(Paths.get("numbers.txt"))) {
      stream.forEach(x -> list.add(Integer.parseInt(x)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Testing of your solution, using 100 shuffled examples
   *
   * @return execution time
   */
  protected long test() {
    Instant start = Instant.now();
    for (int i = 0; i < 100; i++) {
      Collections.shuffle(list, new Random(100));
      sort(list, (Integer) list.get(random.nextInt(list.size())));
    }
    Instant finish = Instant.now();
    return Duration.between(start, finish).toMillis();
  }


  /**
   * Searching method - add your code in here
   *
   * @param list - list to be sorted and the searched
   */
  private void sort(List list, int index) {
    radixSort(list.stream().mapToInt(i -> (int) i).toArray(), list.size());
//        Collections.sort(list);
  }

  /**
   * Do Counting Sort to  for digit at (exp)th place of the list in the Radix Sort.
   *
   * @param list unsorted list
   * @param n    size of list
   * @param exp  to get the current digit number
   * @param base the size of array we need to sort
   */
  static void countSort(int[] list, int n, int exp, int base) {
    int sortedList[] = new int[n];
    int count[] = new int[base];
    int mod[] = new int[n];
    for (int i = 0; i < n; i++) {
      mod[i] = (list[i] / exp) % base;
      count[mod[i]]++;
    }

    for (int i = 1; i < base; i++)
      count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--)
        sortedList[count[mod[i]]-- - 1] = list[i];

    for (int i = 0; i < n; i++)
      list[i] = sortedList[i];
  }

  /**
   * Do Radix Sort for list of size n.
   *
   * @param list the list
   * @param n    size of list
   */
  private void radixSort(int[] list, int n) {
    int max = maxValue(list);
    int base = 256;
    for (int exp = 1; max / exp > 0; exp *= base)
      countSort(list, n, exp, base);
  }

  /**
   * Helper method to get the max element in array
   *
   * @param list of integers
   * @return the max value in the array of integers
   */
  private static int maxValue(int[] list) {
    int max = list[0];
    for (int element : list) {
      max = element > max ? element : max;
    }
    return max;
  }
}