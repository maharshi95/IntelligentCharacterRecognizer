package neural;

import java.util.Random;

import res.LearningData;
import ui.MLUI;

public class MLNetwork {

	public static Random rgen = new Random();
	public static final int TOTAL_PATTERNS = LearningData.TOTAL_PATTERNS;
	
	public static final int N_INPUT = LearningData.N_INPUT;
	public static final int N_OUTPUT = LearningData.N_OUTPUT;
	public static final int N_HIDDEN = LearningData.N_HIDDEN;
	
	public static int[][] input_L = LearningData.INPUT;
	public static int[][] output_L = LearningData.OUTPUT;
	
	public static final int TRAIN_LIMIT = 1000000;
	
	public static final double LAMBDA = 1;
	public static final double LEARNING_RATE = 0.8;
	public static final double THRESHOLD = 0.5;
	public static final double ERROR_LIMIT = 0.45;
	
	public static double[][] WEIGHT_HIDDEN = new double[N_HIDDEN][N_INPUT];
	public static double[][] WEIGHT_OUTPUT = new double[N_OUTPUT][N_HIDDEN];
	
	private static MLUI ui;
	
	public static void main(String[] args) {
		for(int i=0; i<N_OUTPUT; i++) {
			for(int j=0; j<N_HIDDEN; j++) {
				WEIGHT_OUTPUT[i][j] = rgen.nextGaussian();
			}
		}
		for(int i=0; i<N_HIDDEN; i++) {
			for(int j=0; j<N_INPUT; j++) {
				WEIGHT_HIDDEN[i][j] = rgen.nextGaussian();
			}
		}
		
		System.out.println("Starting training...");
		trainData();
		System.out.println("Training ended... starting app...");
		
		ui = new MLUI();
		int[] testData = new int[]{
				0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 
				0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 
				0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 
				1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 
				1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 
				1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 
				1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 
				0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 
				0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 
				0, 0, 0, 1, 1, 1, 1, 0, 0, 0};
		int[] y = test_output(testData);
		System.out.println("test output: " + y[0] + " " + y[1]);
		System.out.println("\n");
		
		for(int i =0; i<N_HIDDEN; i++) {
		//	printVec(WEIGHT_HIDDEN[i]);
		}
	}
	
	public static final double sigmoid(double x) {
	//	return x;
		return (1.0/(1.0 + Math.exp(-LAMBDA*x)));
		//return (1.0 - Math.exp(-2*LAMBDA*x))/(1.0 + Math.exp(-2*LAMBDA*x));
	}
	
	public static final double d_sigmoid(double x) {
//		return 1;
		return (LAMBDA * Math.exp(-LAMBDA * x))/(Math.pow(1 + Math.exp(-LAMBDA*x), 2));
		//return 1 - sigmoid(x)*sigmoid(x);
	}
	
	public static final int threshold(double x) {
		return x >= THRESHOLD ? 1 : 0;
	}
	
	public static void trainData() {
		int counter = 0;
		int l = 0;
		int i = 0;
		int max=0;
		while((counter < TOTAL_PATTERNS) && (l < TRAIN_LIMIT)) {
			double[] error = train_vector(input_L[i], output_L[i]);
			String err = "";
			for(int j=0; j<error.length; j++) {
				err += "" + error[j] + " ";
			}
			System.out.println(counter + " " + i + " " + l + " " + err+" "+max);
			
			if(isErrorUnderLimit(error)) {
				counter++;
			}
			else {
				counter = 0;
			}
			l++;
			if(counter>max)
			{
				max=counter;
			}
			i = (i+1)%TOTAL_PATTERNS;
		}
		
		if(counter == TOTAL_PATTERNS) {
			System.out.println("All vectors trained");
		}
		else {
			System.out.println("Limit reached");
		}
	}
	
	public static double[] V_hidden(int[] ip_vector) {
		double[] op_vector = new double[N_HIDDEN];
		for(int i=0; i<N_HIDDEN; i++) {
			double sum = 0;
			for(int j=0; j<N_INPUT; j++) {
				sum += ip_vector[j] * WEIGHT_HIDDEN[i][j];
			}
			op_vector[i] = sum;
		}
		return op_vector;
	}
	
	public static double[] V_output(double[] ip_vector) {
		double[] op_vector = new double[N_OUTPUT];
		for(int i=0; i<N_OUTPUT; i++) {
			double sum = 0;
			for(int j=0; j<N_HIDDEN; j++) {
				sum += ip_vector[j] * WEIGHT_OUTPUT[i][j];
			}
			op_vector[i] = sum;
		}
		return op_vector;
	}
	
	public static double[] sigmoid(double[] V) {
		double[] Y = new double[V.length];
		for(int i=0; i<V.length; i++) {
			Y[i] = sigmoid(V[i]);
		}
		return Y;
	}
	
	public static double[] d_sigmoid(double[] V) {
		double[] Y = new double[V.length];
		for(int i=0; i<V.length; i++) {
			Y[i] = d_sigmoid(V[i]);
		}
		return Y;
	}
	
	public static int[] threshold(double[] V) {
		int[] Y = new int[V.length];
		for(int i=0; i<V.length; i++) {
			Y[i] = threshold(V[i]);
		}
		return Y;
	}
	
	public static double[] train_vector(int[] input, int[] output) {
		double[] Vh = V_hidden(input);
		double[] Yh = sigmoid(Vh);
		double[] Vo = V_output(Yh);
		double[] Yo = sigmoid(Vo);
		double[] Eo = E_output(Yo, output);
		double[] delta_o = new double[output.length];
		double[][] tempW = new double[N_OUTPUT][N_HIDDEN];
				
		for(int i=0; i<N_OUTPUT; i++) {
			delta_o[i]  = d_sigmoid(Vo[i]) * Eo[i];
			for(int j=0; j<N_HIDDEN; j++) {
				tempW[i][j] = WEIGHT_OUTPUT[i][j];
				WEIGHT_OUTPUT[i][j] += (LEARNING_RATE * Yh[j] * delta_o[i]);  
			}
		}
		
		for(int i=0; i<N_HIDDEN; i++) {
			double delta_h = 0;
			for(int k=0; k<N_OUTPUT; k++) {
				delta_h += delta_o[k] * tempW[k][i];
			}
			delta_h *= d_sigmoid(Vh[i]);
			
			for(int j=0; j<N_INPUT; j++) {
				WEIGHT_HIDDEN[i][j] += (LEARNING_RATE * input[j] * delta_h);
			}
		}
		
		return Eo;
	}
	
	public static boolean isErrorUnderLimit(double[] E) {
		for(int i=0; i<E.length; i++) {
			if(Math.abs(E[i]) > ERROR_LIMIT)
				return false;
		}
		return true;
	}
	
	public static int[] test_output(int[] ip_vector) {
		return threshold((sigmoid(V_output(sigmoid(V_hidden(ip_vector))))));
	}
	
	public static double[] E_output(double[] Y, int[] O) {
		double[] E = new double[Y.length];
		for(int i=0; i<Y.length; i++) {
			E[i] = O[i] - Y[i];
		}
		return E;
	}
	
	public static void printVec(int[] vec) {
		String str = "[";
		for(int i=0; i<vec.length; i++) {
			str += "" + vec[i] + " ";
		}
		str += "]";
		System.out.println(str);
	}
	public static void printVec(double[] vec) {
		String str = "[";
		for(int i=0; i<vec.length; i++) {
			str += "" + vec[i] + " ";
		}
		str += "]";
		System.out.println(str);
	}
}
