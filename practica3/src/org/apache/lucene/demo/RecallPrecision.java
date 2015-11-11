package org.apache.lucene.demo;

public class RecallPrecision {
	private double recall;
	private double precision;
	
	public RecallPrecision(double recall, double precision){
		this.recall = recall;
		this.precision = precision;
	}
	
	public double getRecall(){
		return this.recall;
	}
	
	public double getPrecision(){
		return this.precision;
	}

}
