import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Application {

	private static final int NUM_THREADS=4; //number of threads we will use
	public static void main(String[] args)throws InterruptedException{
		double intervalStart=0.0;
		double intervalEnd=Math.PI;
		Function <Double, Double> function = Math::sin; //or x->Math.sin(x);
		double result=computeDefiniteIntegral(function, intervalStart, intervalEnd);
		System.out.println("The definite integral of f(x) over the interval"
				+ "["+intervalStart+", "+intervalEnd+"] is: "+result);
		
		
	}
	private static double computeDefiniteIntegral(Function<Double, Double> function, double intervalStart, double intervalEnd) throws InterruptedException{
		//Compute the definite integral using parallelization
		double partitionSize=(intervalEnd-intervalStart)/NUM_THREADS;
		
		ExecutorService executor= Executors.newFixedThreadPool(NUM_THREADS);
		AtomicReference<Double>[] partialSums=new AtomicReference[NUM_THREADS]; //atomic -> cannot be interrupted
		
		for (int i=0; i<NUM_THREADS; i++) {
			partialSums[i]=new AtomicReference<>(0.0);
			int partitionNumber=i;
			executor.submit(()->{
				System.out.println("Submitting computation of integral on partition");
				double start=intervalStart+partitionNumber*partitionSize;
				double end=start+partitionSize;
				double partitionIntegral=computeIndefiniteIntegral(function, end)-computeIndefiniteIntegral(function, start);
				partialSums[partitionNumber].updateAndGet(sum->sum+partitionIntegral);
			});
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		double sum=0.0;
		for (AtomicReference<Double> partialSum: partialSums)
			sum+=partialSum.get();
		return sum;
	}
	private static double computeIndefiniteIntegral(Function<Double, Double> function, double x) {
		//Compute the indefinite integral F(x) at a given x using numerical approx
		double epsilon=0.00001; //Tolerance for numerical approximation
		double h=0.00001; //Step size for numerical approximation
		double integral=0.0;
		double previousValue=0.0;
		
		for (double t=0.0; t<x; t+=h) {
			double currentValue=function.apply(t);
			integral+=(currentValue+previousValue)*h/2.0;
			previousValue=currentValue;
		}
		return integral;
	}
	

}
