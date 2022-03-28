package parallelcomputing;

import java.util.Random;
import java.util.concurrent.RecursiveTask;
//import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort extends RecursiveTask<Integer> {

	int start, end;
	int[] arr;
	final static int threshold = 20000;

	private int partition(int start, int end, int[] arr)
	{

		int i = start, j = end;

		int pivot = new Random().nextInt(j - i)+ i;

		int t = arr[j];
		arr[j] = arr[pivot];
		arr[pivot] = t;
		j--;

		while (i <= j) {

			if (arr[i] <= arr[end]) {
				i++;
				continue;
			}

			if (arr[j] >= arr[end]) {
				j--;
				continue;
			}

			t = arr[j];
			arr[j] = arr[i];
			arr[i] = t;
			j--;
			i++;
		}

		t = arr[j + 1];
		arr[j + 1] = arr[end];
		arr[end] = t;
		return j + 1;
	}

	public ParallelQuickSort(int start, int end, int[] arr)
	{
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute()
	{
		if (start >= end)
			return null;

		if (end - start <= threshold)
		{
			SerialQuickSort sqs = new SerialQuickSort(start, end , arr);
			sqs.quickSort(arr, start, end);
			return null;
		}
		else
		{
			int p = partition(start, end, arr);

			ParallelQuickSort left = new ParallelQuickSort(start, p - 1, arr);

			ParallelQuickSort right = new ParallelQuickSort(p + 1, end, arr);
			
			left.fork();
			right.compute();
			left.join();

			return null;
		}
	}

}
