/*
 * Fichero: Data.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package evaluation;

import java.util.LinkedList;

public class Data {
	/**
	 * Clase para almacenar los datos correspondientes con la evaluacion de 
	 * una necesdad de informacion
	 * @version 1.0
	 */
	
	/* atributos privados */
	private double precision, recall, f1, avg_prec, prec10;
	private LinkedList<RecallPrecision> rec_prec, int_rec_prec;
	
	/**
	 * @param precision : precision del sistema
	 * @param recall : recall del sistema
	 * @param f1 : f-score del sistema
	 * @param avg_prec : precision media del sistema
	 * @param prec10 : precision a 10 del sistema
	 * @param rec_prec : curva de recall-precision del sistema
	 * @param int_rec_prec : cruva de recall-precision interpolada del sistema
	 */
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

	/**
	 * @return the precision
	 */
	public double getPrecision() {
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	/**
	 * @return the recall
	 */
	public double getRecall() {
		return recall;
	}

	/**
	 * @param recall the recall to set
	 */
	public void setRecall(double recall) {
		this.recall = recall;
	}

	/**
	 * @return the f1
	 */
	public double getF1() {
		return f1;
	}

	/**
	 * @param f1 the f1 to set
	 */
	public void setF1(double f1) {
		this.f1 = f1;
	}

	/**
	 * @return the avg_prec
	 */
	public double getAvg_prec() {
		return avg_prec;
	}

	/**
	 * @param avg_prec the avg_prec to set
	 */
	public void setAvg_prec(double avg_prec) {
		this.avg_prec = avg_prec;
	}

	/**
	 * @return the prec10
	 */
	public double getPrec10() {
		return prec10;
	}

	/**
	 * @param prec10 the prec10 to set
	 */
	public void setPrec10(double prec10) {
		this.prec10 = prec10;
	}

	/**
	 * @return the rec_prec
	 */
	public LinkedList<RecallPrecision> getRec_prec() {
		return rec_prec;
	}

	/**
	 * @param rec_prec the rec_prec to set
	 */
	public void setRec_prec(LinkedList<RecallPrecision> rec_prec) {
		this.rec_prec = rec_prec;
	}

	/**
	 * @return the int_rec_prec
	 */
	public LinkedList<RecallPrecision> getInt_rec_prec() {
		return int_rec_prec;
	}

	/**
	 * @param int_rec_prec the int_rec_prec to set
	 */
	public void setInt_rec_prec(LinkedList<RecallPrecision> int_rec_prec) {
		this.int_rec_prec = int_rec_prec;
	}
}
