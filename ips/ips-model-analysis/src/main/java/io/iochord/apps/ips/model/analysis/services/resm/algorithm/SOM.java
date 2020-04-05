package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

/*
 *  Take this SOM source code from the internet for tips project
 *  And use some part of it based on our requirement
 *  Later will modify the code
 *  Some part is not optimized
 */

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SOM extends Preprocessing {
	
	private Random r = new Random();
	
	private int xD;
	private int yD;
	
	private int epochs; // epochs of iteration for SOM training
	private int [][] pairArray;
	private double [][] weights;
	
	private int finalNodes[];
	private double finalDistances[];
	
	public SOM(double[][] matrix, int xD, int yD, int epochs)
	{
		super(matrix);
		
		this.xD = xD;
		this.yD = yD;
		this.epochs = epochs;
	}
	
	public double [] getDistances()
	{
		return this.finalDistances;
	}
	 
	public int [] getNodes()
	{
		return this.finalNodes;
	}
	
	public void train()
	{
		this.init();
		int dataRows = this.data.length;
		int dataColumns = this.data[0].length;
		int weightsRows = this.weights.length;
		int weightsColumns = this.weights[0].length;
		int iterations = this.epochs * dataRows;
		double learningRate = 0.05;
		double neighborhood;
		
		int currentObs;
		int nearest = 0;
		double nearestDistance;
		
		double dist;
		double tmp;
		
		double [] data = new double[dataRows * dataColumns];
		double [] nodes = new double[weightsRows * weightsColumns];
		double [] distPairs = new double[this.pairArray.length * this.pairArray.length];
		int count = 0;
		for(int i = 0; i < dataColumns; i++)
		{
			for(int j = 0; j < dataRows; j++)
			{
				data[count] = this.data[j][i];
				count++;
			}
		}
		count = 0;
		for(int i = 0; i < weightsColumns; i++)
		{
			for(int j = 0; j < weightsRows; j++)
			{
				nodes[count] = this.weights[j][i];
				count++;
			}
		}
		count = 0;
		int currentX;
		int currentY;
		double xDist;
		double yDist;
		for(int i = 0; i < this.pairArray.length; i++)
		{
			currentX = this.pairArray[i][0];
			currentY = this.pairArray[i][1];
			for(int j = 0; j < this.pairArray.length; j++)
			{
				xDist = Math.abs(this.pairArray[j][0] - currentX);
				yDist = Math.abs(this.pairArray[j][1] - currentY);
				distPairs[count] = xDist + yDist;
				count++;
			}
		}
		
		neighborhood = 1.75 * variance(distPairs);
		
		for(int i = 0; i < iterations; i++)
		{
			currentObs = r.nextInt(dataRows);
			
			nearestDistance = Double.MAX_VALUE;
			nearest = 0;
			for(int j = 0; j < weightsRows; j++)
			{
				dist = 0;
				for(int k = 0; k < dataColumns; k++)
				{
					tmp = data[currentObs + k * dataRows] - nodes[j + k * weightsRows];
					dist += (tmp * tmp);
				}
				
				if(dist < nearestDistance)
				{
					nearest = j;
					nearestDistance = dist;
				}
			}
			
			learningRate -= (0.04 * i / iterations);
			neighborhood -= (1.0 * i / iterations);
			
			for(int l = 0; l < weightsRows; l++)
			{
				if(distPairs[l + weightsRows * nearest] <= neighborhood)
				{
					for(int m = 0; m < dataColumns; m++)
					{
						tmp = data[currentObs + m * dataRows] - nodes[l + m * weightsRows];
						nodes[l + m * weightsRows] += (tmp * learningRate);
					}
				}
			}
		}
		
		count = 0;
		double [] mapDist = new double[dataRows * weightsRows];
		for(int i = 0; i < dataRows; i++)
		{
			for(int j = 0; j < weightsRows; j++)
			{
				mapDist[count] = 0;
				
				for(int k = 0; k < weightsColumns; k++)
				{
					tmp = data[i + k * dataRows] - nodes[j + k * weightsRows];
					mapDist[count] += (tmp * tmp);	
				}
				count++;
			}
		}
		
		count = 0;
		double [][] distanceMatrix = new double [dataRows][weightsRows];
		for(int i = 0; i < dataRows; i++)
		{
			for(int j = 0; j < weightsRows; j++)
			{
				distanceMatrix[i][j] = mapDist[count];
				count++;
			}
		}
		
		finalNodes = new int[dataRows];
		finalDistances = new double[dataRows];
		int nodeIndex;
		double minDistance;
		for(int i = 0; i < distanceMatrix.length; i++)
		{
			count = 0;
			nodeIndex = count;
			minDistance = Double.MAX_VALUE;
			for(int j = 0; j < weightsRows; j++)
			{
				if(distanceMatrix[i][j] < minDistance)
				{
					minDistance = distanceMatrix[i][j];
					nodeIndex = count;
				}
				count++;
			}
			finalNodes[i] = nodeIndex;
			finalDistances[i] = minDistance;
		}
	}
	
	private void init()
	{
		this.scaleData();
		
		pairArray = new int[this.xD * this.yD][2];
		int count = 0;
		for(int i = 0; i < this.xD; i++)
		{
			for(int j = 0; j < this.yD; j++)
			{
				this.pairArray[count][0] = i;
				this.pairArray[count][1] = j;
				count++;
			}
		}
		
		int pairRows = this.pairArray.length;
		int dataRows = this.data.length;
		
		Set <Integer> samplePoints = new HashSet <Integer>();
		while(samplePoints.size() < pairRows)
		{
			samplePoints.add(r.nextInt(dataRows));
		}
		Integer [] sampleIndex = samplePoints.toArray(new Integer[samplePoints.size()]);
		
		weights = new double[pairRows][data[0].length];
		int weightCount = 0;
		for(Integer i : sampleIndex)
		{
			for(int j = 0; j < data[0].length; j++)
			{
				weights[weightCount][j] = data[i][j];
			}
			weightCount++;
		}
	}
}
