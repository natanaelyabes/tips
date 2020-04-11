package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.util.Arrays;

public class Preprocessing {
	
	protected double [][] data;
	
	public Preprocessing(double [][] data)
	{
		this.data = data;
	}
	
	
	public double getObs(int row, int column)
	 {
		 return this.data[row][column];
	 }
	
	public double mean(double[] inputArray)
	{
		double result = 0;
		for(int i = 0; i < inputArray.length; i++)
		{
			result += inputArray[i];
		}
		return result / inputArray.length;
	}
	
	public double variance(double [] inputArray)
	{
		double xMean = mean(inputArray);
		double sumSq = 0;
		for(int i = 0; i < inputArray.length; i++)
		{
			sumSq += Math.pow((inputArray[i] - xMean), 2);
		}
		return sumSq / inputArray.length;
	}
	
	public boolean zeroVariance()
	{
		double[] currentColumn = new double[this.data.length];
		
		for(int i = 0; i < this.data[0].length; i++)
		{
			for(int j = 0; j < this.data.length; j++)
				currentColumn[j] = this.data[j][i];
			
			if(variance(currentColumn) == 0)
				return true;
		}
		return false;
	}
	
	
	public void scaleData()
	{
		if(zeroVariance())
			return;
		
		int nrows = this.data.length;
		int ncols = this.data[0].length;
		double colMean;
		double colVar;
		
		double[] currentColumn = new double[nrows];
		for(int j = 0; j < ncols; j++)
		{
			for(int i = 0; i < this.data.length; i++)
				currentColumn[i] = this.data[i][j];
			
			colMean = mean(currentColumn);
			colVar = variance(currentColumn);
			
			for(int k = 0; k < currentColumn.length; k ++)
				data[k][j] = (currentColumn[k] - colMean) / Math.sqrt(colVar);
		}
	}
	 
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.data.length; i++)
		{
			sb.append(Arrays.toString(this.data[i]));
			sb.append("\n");
		}
		return sb.toString();
	}
}
