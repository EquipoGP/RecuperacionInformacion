package org.apache.lucene.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Measures {

	// codificar las entradas del mapa
	private static final String PRECISION = "precision", RECALL = "recall", F1 = "F1", PREC10 = "prec@10",
			AVRG_PREC = "average_precision", REC_PREC = "recall_precision",
			INT_REC_PREC = "interpolated_recall_precision", MAP = "MAP";

	/**
	 * Calcula las measures
	 * 
	 * @param outputFileName
	 * @param qrels
	 * @param results
	 */
	public static void measures(String outputFileName, HashMap<String, Integer> docs_relevantes,
			HashMap<String, LinkedList<Boolean>> relevancia) {

		try {
			// printear en el fichero
			PrintWriter out = new PrintWriter(new File(outputFileName));

			// variables a reutilizar
			LinkedList<Double> precisiones = null;
			LinkedList<Double> recalls = null;
			double precision = -1;
			double recall = -1;
			double f1 = -1;
			double prec10 = -1;
			double avg_prec = -1;
			LinkedList<RecallPrecision> recall_precision = null;
			LinkedList<RecallPrecision> int_recall_precision = null;

			// pasar todas las infoNeeds a una lista ordenada
			Set<String> keys_set = relevancia.keySet();
			List<String> keys = new LinkedList<String>(keys_set);
			java.util.Collections.sort(keys);

			for (int i = 0; i < keys.size(); i++) {
				// para cada clave, obtener medidas de evaluacion
				String key = keys.get(i);

				int docs_rel = docs_relevantes.get(key);
				LinkedList<Boolean> drels = relevancia.get(key);

				precisiones = getPrecisiones(drels);
				recalls = getRecalls(docs_rel, drels);
				
				precision = precisiones.getLast();
				recall = recalls.getLast();
				
				f1 = getF1(precision, recall);
				prec10 = getPrecK(10, precisiones);
				avg_prec = getAvgPrecision(docs_rel, precisiones, drels);

				out.println("INFORMATION_NEED" + "\t" + key);
				out.println(PRECISION + "\t" + precision);
				out.println(RECALL + "\t" + recall);
				out.println(F1 + "\t" + f1);
				out.println(PREC10 + "\t" + prec10);
				out.println(AVRG_PREC + "\t" + avg_prec);

				System.out.println(REC_PREC);
				for (RecallPrecision rc : recall_precision) {
					System.out.println(rc.getRecall() + "\t" + rc.getPrecision());
				}

				System.out.println(INT_REC_PREC);
				for (RecallPrecision rc : int_recall_precision) {
					System.out.println(rc.getRecall() + "\t" + rc.getPrecision());
				}
			}

			out.println("TOTAL");
			out.println(PRECISION + "\t" + precision);
			out.println(RECALL + "\t" + recall);
			out.println(F1 + "\t" + f1);
			out.println(PREC10 + "\t" + prec10);
			out.println(MAP + "\t" + avg_prec);

			System.out.println(REC_PREC);
			for (RecallPrecision rc : recall_precision) {
				System.out.println(rc.getRecall() + "\t" + rc.getPrecision());
			}

			System.out.println(INT_REC_PREC);
			for (RecallPrecision rc : int_recall_precision) {
				System.out.println(rc.getRecall() + "\t" + rc.getPrecision());
			}

			out.flush();
			out.close();

		} catch (

		FileNotFoundException e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static LinkedList<Double> getPrecisiones(LinkedList<Boolean> relevantes) {
		LinkedList<Double> precisiones = new LinkedList<Double>();
		
		int rels_parcial = 0;
		// suma los documentos relevantes
		for (int i = 0; i < relevantes.size(); i++) {
			if (relevantes.get(i)) {
				rels_parcial++;
			}
			precisiones.add((double)(rels_parcial / (double) relevantes.size()));
		}
		return precisiones;
	}

	/**
	 * 
	 */
	private static LinkedList<Double> getRecalls(int total_rels, LinkedList<Boolean> relevantes) {
		LinkedList<Double> recalls = new LinkedList<Double>();
		
		int rels_parcial = 0;
		// suma los documentos relevantes
		for (int i = 0; i < relevantes.size(); i++) {
			if (relevantes.get(i)) {
				rels_parcial++;
			}
			recalls.add((double) ((double) rels_parcial / (double) total_rels));
		}

		return recalls;
	}
	
	/**
	 * 
	 */
	private static double getF1(double precision, double recall){
		double numerador = 2 * precision * recall;
		double denominador = precision + recall;
		
		double f1 = numerador / denominador;
		
		return f1;
	}
	
	/**
	 * 
	 */
	private static double getPrecK(int k, LinkedList<Double> precisiones){
		double preck = 0.0;
		if(precisiones.size() < k){
			preck =(precisiones.getLast() * precisiones.size()) / k;
		}
		else{
			preck = precisiones.get(k-1);
		}
		return preck;
	}
	
	/**
	 * 
	 * @param docs_rel
	 * @param precisiones
	 * @return
	 */
	private static double getAvgPrecision(int docs_rel, LinkedList<Double> precisiones,
			LinkedList<Boolean> relevantes) {
		double avg_precision = 0.0;
		for (int i = 0; i < relevantes.size(); i++) {
			if(relevantes.get(i)){
				avg_precision += precisiones.get(i);
			}
		}
		avg_precision = avg_precision / docs_rel;
		
		return avg_precision;
	}
}
