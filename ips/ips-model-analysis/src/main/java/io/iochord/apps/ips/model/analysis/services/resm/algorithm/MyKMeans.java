package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.iochord.apps.ips.model.analysis.services.resm.model.TimeUnit;
import io.iochord.apps.ips.model.analysis.services.resm.model.UnitDist;

public class MyKMeans {
	
	double[][] matrix;
	double[][] clusters;
	double[] threshold;
	Integer[] initArr;
	TimeUnit tu;
	Map<TimeUnit, Double> mapTU;
	UnitDist[] arUd;
	
	public MyKMeans(double[][] matrix, TimeUnit tu, UnitDist[] arUd) {
		this.matrix = matrix;
		this.tu = tu;
		this.arUd = arUd;
		this.mapTU = new HashMap<TimeUnit,Double>() {{put(TimeUnit.Hour,24d);put(TimeUnit.Minute,24d*60);put(TimeUnit.Second,24d*60*60);}};
	}
	
	public void init(int k) {
		int numbAttr = matrix[0].length; // number of attributes or column length
		clusters = new double[k][numbAttr];
		threshold = new double[numbAttr];
		
		// initialize cluster centroid from random data in dataset, if clusters length > data length then use random int 
		initArr = new Integer[matrix.length];
		Arrays.setAll(initArr, i -> i + 1);
		
		Random r = new Random();
		for (int u=0; u<clusters.length; u++)
		    for(int d=0; d<clusters[0].length; d++)
		    	clusters[u][d] = u < matrix.length ? matrix[initArr[u]-1][d] : r.nextInt(10);
		    	
		for(int i=0; i<numbAttr; i++) {
			if(arUd[i] == UnitDist.Time)
				threshold[i] = mapTU.get(tu);
		}
	}
	
	public void train() {
		Map<Integer,List<double[]>> mapDataToCluster = new HashMap<>();
		Map<Integer,double[]> mapCentroidToCluster = new HashMap<>();
		
		boolean isStop = false;
		
		while(!isStop) {
			mapDataToCluster.clear();
			List<Integer> pickOrder = Arrays.asList(initArr);
			Collections.shuffle(pickOrder);
			
			for(int row : pickOrder) {
				double[] dataInRow = matrix[row-1];
				// neuron of nodes lattice
				int bestNodeIndex = 0;
				double bestDist = Double.MAX_VALUE;
				for (int nodeNumb=0; nodeNumb<clusters.length; nodeNumb++) {
					double dist = euclidDistance(dataInRow, clusters[nodeNumb]);
					if(dist < bestDist) {
						bestNodeIndex = nodeNumb;
						bestDist = dist;
					}
				}
				List<double[]> listDataInCluster = mapDataToCluster.getOrDefault(bestNodeIndex, new ArrayList<>());
				listDataInCluster.add(dataInRow);
				mapDataToCluster.put(bestNodeIndex, listDataInCluster);
			}
			
			isStop = true;
			for (int nodeNumb=0; nodeNumb<clusters.length; nodeNumb++) {
				double[] newCentroid = newCentroid(clusters[nodeNumb], mapDataToCluster.getOrDefault(nodeNumb, new ArrayList<>()));
				if(!isEqual(mapCentroidToCluster.getOrDefault(nodeNumb, new double[]{0,0,0}), newCentroid))
					isStop = false;
				clusters[nodeNumb] = newCentroid;
				mapCentroidToCluster.put(nodeNumb, newCentroid);
			}
		}
	}
	
	public boolean isEqual(double[] oldCentroid, double[] newCentroid) {
		boolean equal = true;
		for(int i=0; i< newCentroid.length; i++)
			if(oldCentroid[i] != newCentroid[i])
				equal = false;
		return equal;
		
	}
	
	public double minDist(double a, double b) {
		return a-b;
	}
	
	public double timeDist(double a, double b, double thresTimeUnit) {
		return Math.min(Math.max(a, b) - Math.min(a, b),Math.min(a, b)+thresTimeUnit - Math.max(a, b));
	}
	
	public double euclidDistance(double[] el1, double[] el2) {
		double sumSquare = 0;
		for(int i=0; i<el1.length; i++) {
			double unitDist = arUd[i] == UnitDist.NonTime ? minDist(el1[i], el2[i]) : timeDist(el1[i], el2[i], threshold[i]);
			sumSquare += Math.pow(unitDist, 2);
		}
		return Math.sqrt(sumSquare);
	}
	
	public double[] newCentroid(double[] old, List<double[]> assignedData) {
		int arLength = old.length;
		double[] newCentroid = new double[arLength];
		for(int i=0; i<arLength; i++) {
			double meanData = 0;
			for(double[] data : assignedData) {
				if(arUd[i] == UnitDist.Time) { // for time, we use incremental average / mean
					if(Math.max(data[i], meanData) - Math.min(data[i], meanData) <= (Math.min(data[i], meanData) + threshold[i] - Math.max(data[i], meanData)))
						meanData += (data[i] - meanData)/(i+1);
					else
						meanData = meanData < data[i] ? (meanData + threshold[i]) + (data[i] - meanData - threshold[i])/(i+1) : (data[i] + threshold[i]) + (meanData - data[i] - threshold[i])/(i+1);
					
					if(meanData >= 24)
						meanData = meanData - 24;
				}
				else {
					meanData += data[i]/assignedData.size();  
				}
			}
			newCentroid[i] = meanData;
		}
		return newCentroid;
	}
	
	//method below is used for elbow method (automatic decision for k)
	public double shortestDistancePointToLine(double x, double y, double x1, double y1, double x2, double y2) {
		double A = x - x1;
		double B = y - y1;
		double C = x2 - x1;
		double D = y2 - y1;
		double E = -D; // orthogonal vector
		
		double dot = A * E + B * C;
		double len_sq = E * E + C * C;
		
		return Math.abs(dot) / Math.sqrt(len_sq);
	}
	
	public int[] getNode() {
		int[] dataMapToNode = new int[matrix.length];
		
		for(int x=0; x<matrix.length; x++) {
			double[] dataInRow = matrix[x];
			
			// neuron of nodes lattice
			int bmuIndex = 0;
			double bestDist = Double.MAX_VALUE;
			for (int nodeNumb=0; nodeNumb<clusters.length; nodeNumb++) {
				double dist = euclidDistance(dataInRow, clusters[nodeNumb]);
				if(dist < bestDist) {
					bmuIndex = nodeNumb;
					bestDist = dist;
				}
			}
			
			dataMapToNode[x] = bmuIndex;
		}
		
		return dataMapToNode;
	}
	
	/* this method is just for testing / debugging
	public static void main(String[] args) {
		double[][] arr = new double[][]{
			{8,16,8},
			{9,17,8},
			{16,22,6},
			{17,23,6},
			{23.30,6,6.30},
			{0.30,7,6.30},
		};
		MyKMeans mos = new MyKMeans(arr,TimeUnit.Hour,new UnitDist[]{UnitDist.Time, UnitDist.Time, UnitDist.NonTime});
		System.out.println(mos.shortestDistancePointToLine(0, 0, 0, 4, 4, 0));
		
		mos.init(3);
		mos.train();
		
		for(int x=0; x<mos.matrix.length; x++) {
			double[] dataInRow = mos.matrix[x];
			
			// neuron of nodes lattice
			int bestNodeIndex = 0;
			double bestDist = Double.MAX_VALUE;
			for (int nodeNumb=0; nodeNumb<mos.clusters.length; nodeNumb++) {
				double dist = mos.euclidDistance(dataInRow, mos.clusters[nodeNumb]);
				if(dist < bestDist) {
					bestNodeIndex = nodeNumb;
					bestDist = dist;
				}
			}
			
			System.out.println("Row "+x+" - "+Arrays.toString(dataInRow)+" : "+bestNodeIndex);
		}
	}
	*/
}
