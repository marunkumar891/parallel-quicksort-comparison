package parallelcomputing;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class app {
    public static void main(String [] args) {
        final int size = 10000000;
        int[] arr = new int[size];
        for(int i=0; i < size; i++){
            arr[i] = new Random().nextInt(size);
        }
        int[] serialArr = arr.clone();
        int[] parallelArr = arr.clone();

        System.out.println("Executing Quick sort on " + size + " elements\n");
        // Serial 
        SerialQuickSort sqs = new SerialQuickSort(0, size-1, serialArr);
        double startTime = System.nanoTime();
        sqs.quickSort(serialArr, 0, size-1);
        double endTime = System.nanoTime();
        double durationTimeSerial = (endTime - startTime)/1000000;
        System.out.println("Serial Execution Time: "+durationTimeSerial+" ms\n");

        // Parallel 
        ParallelQuickSort pqs = new ParallelQuickSort(0, size-1, parallelArr);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        startTime = System.nanoTime();
		pool.invoke(pqs);
        endTime = System.nanoTime();
        double durationTimeParallel = (endTime - startTime)/1000000;
        System.out.println("Parallel Execution Time: "+durationTimeParallel+" ms\n");
        System.out.println("Speedup obtained : "+durationTimeSerial/durationTimeParallel+" x\n");
        boolean check = true;
        for(int i=0; i< size; i++)
        {
            if (serialArr[i] != parallelArr[i])
            {
                check = false;
                System.out.println("Mismatch at index " + i); 
                System.out.print(serialArr[i]+" ");
                System.out.print(parallelArr[i]+" ");
                System.out.println(""); 
                break;
            }

        }
        System.out.println("Correctness : "+check);
    }
}
