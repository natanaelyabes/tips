package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.iochord.apps.ips.model.analysis.services.resm.model.TimeUnit;
import io.iochord.apps.ips.model.analysis.services.resm.model.UnitDist;

public class MyOwnSOM {
	
	double[][] matrix;
	double[][] weights;
	double[] threshold;
	Integer[] initArr;
	int xD;
	int yD;
	int numIter;
	int curIter;
	double initLR;
	TimeUnit tu;
	Map<TimeUnit, Double> mapTU;
	UnitDist[] arUd;
	
	public MyOwnSOM(double[][] matrix, int xD, int yD, int numIter, double initLR, TimeUnit tu, UnitDist[] arUd) {
		this.xD = xD;
		this.yD = yD;
		this.numIter = numIter;
		this.matrix = matrix;
		this.initLR = initLR;
		this.curIter = 0;
		this.tu = tu;
		this.arUd = arUd;
		this.mapTU = new HashMap<TimeUnit,Double>() {{put(TimeUnit.Hour,24d);put(TimeUnit.Minute,24d*60);put(TimeUnit.Second,24d*60*60);}};
		init();
	}
	
	public void init() {
		int numbAttr = matrix[0].length; // number of attributes or column length
		int numbNode = xD*yD; // number of neuron as nodes (not input vector)
		weights = new double[numbNode][numbAttr];
		threshold = new double[numbAttr];
		
		// initialize weight from random data in dataset, if weights length > data length then use random int 
		initArr = new Integer[matrix.length];
		Arrays.setAll(initArr, i -> i + 1);
		
		List<Integer> pickOrder = Arrays.asList(initArr);
		Collections.shuffle(pickOrder);
		
		Random r = new Random();
		for (int u=0; u<weights.length; u++)
		    for(int d=0; d<weights[0].length; d++)
		    	weights[u][d] = u < matrix.length ? matrix[pickOrder.get(u)-1][d] : r.nextInt(10);
		
		for(int i=0; i<numbAttr; i++) {
			if(arUd[i] == UnitDist.Time)
				threshold[i] = mapTU.get(tu);
		}
	}
	
	public void train() {
		for(int i=1; i<=numIter; i++) {
			curIter = i;
			List<Integer> pickOrder = Arrays.asList(initArr);
			Collections.shuffle(pickOrder);
			
			//System.out.println("Iter "+i);
			for(int row : pickOrder) {
				double[] dataInRow = matrix[row-1];
				// neuron of nodes lattice
				int bmuIndex = 0;
				double bestDist = Double.MAX_VALUE;
				for (int nodeNumb=0; nodeNumb<weights.length; nodeNumb++) {
					double dist = euclidDistance(dataInRow, weights[nodeNumb]);
					//System.out.println("Node "+Arrays.toString(weights[nodeNumb])+" "+nodeNumb+" : "+dist+" - bestDist "+bestDist+" - data "+Arrays.toString(dataInRow));
					if(dist < bestDist) {
						bmuIndex = nodeNumb;
						bestDist = dist;
					}
				}
				
				double[] bmu = weights[bmuIndex];
				for (int nodeNumb=0; nodeNumb<weights.length; nodeNumb++) {
					weights[nodeNumb] = newWeight(weights[nodeNumb], bmu, dataInRow);
				}
				//System.out.println(row-1+" : "+Arrays.toString(dataInRow)+" - Node"+bmuIndex+" : "+Arrays.toString(bmu));
			}
		}
	}
	
	public double learningRate() {
		return initLR*Math.exp(-(double)(curIter)/numIter);
	}
	
	public double initRad() {
		return Math.max(xD, yD);
	}
	
	public double timeConst() {
		return numIter/Math.log(initRad());
	}
	
	public double neighbourhoodRad() {
		return initRad()*Math.exp(-curIter/timeConst());
	}
	
	public double minDist(double a, double b) {
		return a-b;
	}
	
	public double timeDist(double a, double b, double thresTimeUnit) {
		return Math.min(Math.max(a, b) - Math.min(a, b),Math.min(a, b)+thresTimeUnit - Math.max(a, b));
	}
	
	public double timeDelta(double a, double b, double thresTimeUnit) {
		if(a >= b) {
			if((a - b) <= (b + thresTimeUnit - a))
				return a - b;
			else
				return -(b + thresTimeUnit - a);
		}
		else {
			if((b - a) <= (a + thresTimeUnit - b)) {
				return a - b;
			}
			else {
				return -(a + thresTimeUnit - b);
			}
		}
	}
	
	public double euclidDistance(double[] el1, double[] el2) {
		double sumSquare = 0;
		for(int i=0; i<el1.length; i++) {
			double unitDist = arUd[i] == UnitDist.NonTime ? minDist(el1[i], el2[i]) : timeDist(el1[i], el2[i], threshold[i]);
			sumSquare += Math.pow(unitDist, 2);
		}
		return Math.sqrt(sumSquare);
	}
	
	public double topologicalNeighbourhood(double[] bmu, double[] nb) {
		return Math.exp(-Math.pow(euclidDistance(bmu, nb),2)/(2*Math.pow(neighbourhoodRad(), 2)));
	}
	
	public double[] newWeight(double[] old, double[] bmu, double[] inpVec) {
		int arLength = old.length;
		double[] newWeight = new double[arLength];
		for(int i=0; i<arLength; i++) {
			newWeight[i] = old[i] + learningRate()*topologicalNeighbourhood(bmu, old)*(arUd[i] == UnitDist.NonTime ? minDist(inpVec[i], old[i]) : timeDelta(inpVec[i], old[i], threshold[i]));
			if(arUd[i] == UnitDist.Time && (newWeight[i] >= threshold[i] || newWeight[i] < 0)) {
				if(newWeight[i] < 0)
					newWeight[i] = threshold[i] + newWeight[i];
				else
					newWeight[i] = newWeight[i] - threshold[i];
			}
		}
		return newWeight;
	}
	
	public int[] getNode() {
		int[] dataMapToNode = new int[matrix.length];
		
		for(int x=0; x<matrix.length; x++) {
			double[] dataInRow = matrix[x];
			
			// neuron of nodes lattice
			int bmuIndex = 0;
			double bestDist = Double.MAX_VALUE;
			for (int nodeNumb=0; nodeNumb<weights.length; nodeNumb++) {
				double dist = euclidDistance(dataInRow, weights[nodeNumb]);
				if(dist < bestDist) {
					bmuIndex = nodeNumb;
					bestDist = dist;
				}
			}
			
			dataMapToNode[x] = bmuIndex;
		}
		
		return dataMapToNode;
	}
	
	/* this method is just for testing
	public static void main(String[] args) {
		//double[][] arr = new double[][]{
		//	{8,16,8},
		//	{9,17,8},
		//	{16,22,6},
		//	{17,23,6},
		//	{23.30,6,6.30},
		//	{0.30,7,6.30},
		//};
		
		double[][] arr = new double[][]{
			{8},
			{9},
			{16},
			{17},
			{23},
			{0},
		};
		int numbOfNeuron = (int) Math.round(5*Math.sqrt(arr.length));
		int lengthPerSide = (int) Math.round(Math.sqrt(numbOfNeuron));
		
		MyOwnSOM mos = new MyOwnSOM(arr,lengthPerSide,lengthPerSide,100,0.6,TimeUnit.Hour,new UnitDist[]{UnitDist.Time});
		mos.train();
		
		for(int x=0; x<mos.matrix.length; x++) {
			double[] dataInRow = mos.matrix[x];
			
			// neuron of nodes lattice
			int bmuIndex = 0;
			double bestDist = Double.MAX_VALUE;
			for (int nodeNumb=0; nodeNumb<mos.weights.length; nodeNumb++) {
				double dist = mos.euclidDistance(dataInRow, mos.weights[nodeNumb]);
				if(dist < bestDist) {
					bmuIndex = nodeNumb;
					bestDist = dist;
				}
			}
			
			System.out.println("Row "+x+" - "+Arrays.toString(dataInRow)+" : "+bmuIndex);
		}
	}
	*/
}
