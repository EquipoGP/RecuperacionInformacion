/*
 * Fichero: RecallPrecision.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package evaluation;

public class RecallPrecision {
	/**
	 * Clase que representa un par recall-precision del sistema
	 * @version 1.0
	 */
	
	/* atributos privados */
	private double recall;
	private double precision;
	
	/**
	 * @param recall : recall
	 * @param precision : precision
	 */
	public RecallPrecision(double recall, double precision){
		this.recall = recall;
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
}
