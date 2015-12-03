package org.apache.lucene.demo;

import java.util.LinkedList;

public class Data {
	
	private double precision, recall, f1, avg_prec, prec10;
	private LinkedList<RecallPrecision> rec_prec, int_rec_prec;
	
	public Data(double precision, double recall, double f1, double avg_prec, double prec10,
			LinkedList<RecallPrecision> rec_prec, LinkedList<RecallPrecision> int_rec_prec) {
		this.precision = precision;
		this.recall = recall;
		this.f1 = f1;
		this.avg_prec = avg_prec;
		this.prec10 = prec10;
		this.rec_prec = rec_prec;
		this.int_rec_prec = int_rec_prec;
	}
	
	public double getPrecision() {
		return precision;
	}
	
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
	public double getRecall() {
		return recall;
	}
	
	public void setRecall(double recall) {
		this.recall = recall;
	}
	
	public double getF1() {
		return f1;
	}
	
	public void setF1(double f1) {
		this.f1 = f1;
	}
	
	public double getAvg_prec() {
		return avg_prec;
	}
	
	public void setAvg_prec(double avg_prec) {
		this.avg_prec = avg_prec;
	}
	
	public double getPrec10() {
		return prec10;
	}
	
	public void setPrec10(double prec10) {
		this.prec10 = prec10;
	}
	
	public LinkedList<RecallPrecision> getRec_prec() {
		return rec_prec;
	}
	
	public void setRec_prec(LinkedList<RecallPrecision> rec_prec) {
		this.rec_prec = rec_prec;
	}
	
	public LinkedList<RecallPrecision> getInt_rec_prec() {
		return int_rec_prec;
	}
	
	public void setInt_rec_prec(LinkedList<RecallPrecision> int_rec_prec) {
		this.int_rec_prec = int_rec_prec;
	}
}
