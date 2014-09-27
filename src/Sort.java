import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Implemented 2 sort algorithm as required
 * 
 * @author Your name, Email and so on
 *
 */
public class Sort {
	private Random rand = new Random();
	/**
	 * Fill the input array with random number between (0, length),
	 * Where length is the length of the input array
	 * @param input
	 * @return an array with random input
	 */
	private double[] FillArrayRandom(double[] input){
		double output[] = input;
		for(int i=0; i<output.length;i++){
			output[i] = rand.nextDouble()*input.length;
		}
		return output;
	}
	
	private double[] CopyArray(double[] input){
		double output[] = new double[input.length];
		System.arraycopy(input, 0, output, 0, input.length);
		return output;
	}
	
	private double[] leftHalf(double array[]){
		double output[] = new double[array.length/2];
		System.arraycopy(array, 0, output, 0, array.length/2);
		return output;
	}
	
	private double[] rightHalf(double array[]){
		int size = array.length-array.length/2;
		double output[] = new double[size];
		System.arraycopy(array, array.length/2, output, 0, size);
		return output;
	}
	
	private boolean IsSorted(double input[]){
		for(int i=1; i<input.length;i++){
			if(input[i]<input[i-1])
				return false;	
		}
		return true;
	}
	
	private double[] InsertionSort(double input[]){
		double output[] = CopyArray(input);
		
		for(int i=1;i<output.length;i++){
			double temp = output[i];
			int j=i;
			while(j>0 && output[j-1]>output[j]){
				output[j]=output[j-1];
				j--;
				output[j]=temp;
			}
		}
		return output;
	}
	
	private double[] MergeSort(double input[]){
		double[] output = CopyArray(input);
		mergeSorter(output);
		return output;
	}
	private double[] mergeSorter(double input[]){
		if(input.length>1){// More than one element, then merge
			double[] leftArray=leftHalf(input);
			double[] rightArray = rightHalf(input);
			mergeSorter(leftArray);
			mergeSorter(rightArray);
			merge(input,leftArray,rightArray);
		}
		return input;
	}
	
	
	
	private double[] merge(double input[], double leftArray[], double rightArray[]){
		int left=0;
		int right=0;
		for(int i=0; i<input.length;i++){
			if(right>=rightArray.length||(left<leftArray.length&&leftArray[left]<=rightArray[right])){
				input[i]= leftArray[left];
				left++;
			}else{
				input[i]=rightArray[right];
				right++;
			}
		}
		return input;
	}
	
	private void resultsToCSV(String filename, ArrayList<String> results){
		try
		{
		    FileWriter writer = new FileWriter(filename);
		    Iterator<String> it = results.iterator();
		    while(it.hasNext()){
		    	writer.append(it.next()+"\n");
		    }
		    //generate whatever data you want
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		}	
	}
	
	public static void main(String args[]){
		int nmax[] = {10, 100,1000,10000,100000};
		int maxIterations = 100000;
		Sort sorter= new Sort();
		double ave_copy_time=0;
		double ave_insertion_sort_time=0;
		double ave_merge_sort_time = 0;
		ArrayList<String> results = new ArrayList<String>();

		/***Test case 1, data are in random order****/
		for(int i=0; i<nmax.length;i++){
			double A[]= new double[nmax[i]];
			double B[]= new double[A.length];
			A = sorter.FillArrayRandom(A);
			CpuTimer timer1 = new CpuTimer();
			// step 4
			for(int j=0; j<maxIterations;j++){
				B = sorter.CopyArray(A);
			}
			ave_copy_time = timer1.getElapsedCpuTime()/maxIterations;
			
			// step 5
			CpuTimer timer2 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.InsertionSort(A);
			}
			ave_insertion_sort_time = timer2.getElapsedCpuTime()/maxIterations-ave_copy_time;
			if(!sorter.IsSorted(B)){
				System.err.println("Insertion sort does not work");
			}

			CpuTimer timer3 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.MergeSort(A);
			}
			if(!sorter.IsSorted(B)){
				System.err.println("Merge sort does not work");
			}
			ave_merge_sort_time = timer3.getElapsedCpuTime()/maxIterations-ave_copy_time;
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"IR,"+ave_insertion_sort_time);
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"MR,"+ave_merge_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"IR,"+ave_insertion_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"MR,"+ave_merge_sort_time);
		}
		
		
		
		System.out.println("Test case 2, data are in ascending order======================");
		/***Test case 2, data are in ascending order****/
		for(int i=0; i<nmax.length;i++){
			double A[]= new double[nmax[i]];
			double B[]= new double[A.length];
			for(int iter=0; iter<A.length;iter++){
				A[iter]=iter;
			}
			System.out.println("A is sorted: "+sorter.IsSorted(A));
			CpuTimer timer1 = new CpuTimer();
			// step 4
			for(int j=0; j<maxIterations;j++){
				B = sorter.CopyArray(A);
			}
			ave_copy_time = timer1.getElapsedCpuTime()/maxIterations;
			
			// step 5
			CpuTimer timer2 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.InsertionSort(A);
			}
			ave_insertion_sort_time = timer2.getElapsedCpuTime()/maxIterations-ave_copy_time;
			if(!sorter.IsSorted(B)){
				System.err.println("Insertion sort does not work");
			}

			CpuTimer timer3 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.MergeSort(A);
			}
			if(!sorter.IsSorted(B)){
				System.err.println("Merge sort does not work");
			}
			ave_merge_sort_time = timer3.getElapsedCpuTime()/maxIterations-ave_copy_time;
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"IA,"+ave_insertion_sort_time);
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"MA,"+ave_merge_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"IA,"+ave_insertion_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"MA,"+ave_merge_sort_time);
		}
		/***Test case 3, data are in descending order****/
		System.out.println("Test case 3, data are in descending order======================");
		for(int i=0; i<nmax.length;i++){
			double A[]= new double[nmax[i]];
			double B[]= new double[A.length];
			for(int iter=0; iter<A.length;iter++){
				A[iter]=nmax[i]-iter;
			}
			System.out.println("A is sorted: "+sorter.IsSorted(A));
			CpuTimer timer1 = new CpuTimer();
			// step 4
			for(int j=0; j<maxIterations;j++){
				B = sorter.CopyArray(A);
			}
			ave_copy_time = timer1.getElapsedCpuTime()/maxIterations;
			
			// step 5
			CpuTimer timer2 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.InsertionSort(A);
			}
			ave_insertion_sort_time = timer2.getElapsedCpuTime()/maxIterations-ave_copy_time;
			if(!sorter.IsSorted(B)){
				System.err.println("Insertion sort does not work");
			}

			CpuTimer timer3 = new CpuTimer();
			for(int j=0; j<maxIterations;j++){
				B = sorter.MergeSort(A);
			}
			if(!sorter.IsSorted(B)){
				System.err.println("Merge sort does not work");
			}
			ave_merge_sort_time = timer3.getElapsedCpuTime()/maxIterations-ave_copy_time;
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"ID,"+ave_insertion_sort_time);
			System.out.println(nmax[nmax.length-1]+","+nmax[i]+","+"MD,"+ave_merge_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"ID,"+ave_insertion_sort_time);
			results.add(nmax[nmax.length-1]+","+nmax[i]+","+"MD,"+ave_merge_sort_time);
		}
		sorter.resultsToCSV("results.csv", results);
	}

}
