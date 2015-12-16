/*
 * Fichero: Measures.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package org.apache.lucene.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Measures {
	
	/**
	 * Clase auxiliar para el calculo de medidas de evaluacion
	 * @version 1.0
	 */

	// codificar las entradas del mapa
	private static final String PRECISION = "precision", 
			RECALL = "recall", 
			F1 = "F1", 
			PREC10 = "prec@10",
			AVRG_PREC = "average_precision", 
			REC_PREC = "recall_precision",
			INT_REC_PREC = "interpolated_recall_precision", 
			MAP = "MAP";

	/**
	 * Calcula las measures
	 * 
	 * @param outputFileName : nombre del fichero en el que volcar los 
	 * 	resultados
	 * @param docs_relevantes : 
	 * @param relevancia : 
	 */
	public static void measures(String outputFileName, HashMap<String, Integer> docs_relevantes,
			HashMap<String, LinkedList<Boolean>> relevancia) {
		
		LinkedList<Data> data = new LinkedList<Data>();

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
				prec10 = getPrecK(10, drels);
				avg_prec = getAvgPrecision(precisiones, drels);
				
				recall_precision = merge(precisiones, recalls);
				int_recall_precision = mergeInterpolated(recall_precision);
				
				out.println("INFORMATION_NEED" + "\t" + key);
				out.printf("%s \t %.3f%n", PRECISION, precision);
				out.printf("%s \t %.3f%n", RECALL, recall);
				out.printf("%s \t %.3f%n", F1, f1);
				out.printf("%s \t %.3f%n", PREC10, prec10);
				out.printf("%s \t %.3f%n", AVRG_PREC, avg_prec);
				
				out.println(REC_PREC);
				for (RecallPrecision rc : recall_precision) {
					out.printf("%.3f \t %.3f%n", rc.getRecall(), rc.getPrecision());
				}

				out.println(INT_REC_PREC);
				for (RecallPrecision rc : int_recall_precision) {
					out.printf("%.3f \t %.3f%n", rc.getRecall(), rc.getPrecision());
				}
				
				Data d = new Data(precision, recall, f1, avg_prec, prec10, recall_precision, int_recall_precision);
				data.add(d);
				
				out.println();
			}
			
			Data d = calcularMedias(data);
			precision = d.getPrecision();
			recall = d.getRecall();
			f1 = d.getF1();
			prec10 = d.getPrec10();
			avg_prec = d.getAvg_prec();
			recall_precision = d.getRec_prec();
			int_recall_precision = d.getInt_rec_prec();
			
			out.println("TOTAL");
			out.printf("%s \t %.3f%n", PRECISION, precision);
			out.printf("%s \t %.3f%n", RECALL, recall);
			out.printf("%s \t %.3f%n", F1, f1);
			out.printf("%s \t %.3f%n", PREC10, prec10);
			out.printf("%s \t %.3f%n", MAP, avg_prec);

			out.println(INT_REC_PREC);
			for (RecallPrecision rc : int_recall_precision) {
				out.printf("%.3f \t %.3f%n", rc.getRecall(), rc.getPrecision());
			}

			out.flush();
			out.close();

		} catch (

		FileNotFoundException e){
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la precision con cada documento
	 * @param relevantes : lista de documentos con su relevancia (si/no)
	 */
	private static LinkedList<Double> getPrecisiones(LinkedList<Boolean> relevantes) {
		LinkedList<Double> precisiones = new LinkedList<Double>();
		
		int rels_parcial = 0;
		// suma los documentos relevantes
		for (int i = 0; i < relevantes.size(); i++) {
			if (relevantes.get(i)) {
				rels_parcial++;
			}
			precisiones.add((double)(rels_parcial / (double) (i + 1)));
		}
		return precisiones;
	}

	/**
	 * Obtiene el recall con cada documento
	 * @param relevantes : lista de documentos con su relevancia (si/no)
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
	 * Obtiene la medida F-Score (Beta=1) a partir de la precision y 
	 * recall
	 */
	private static double getF1(double precision, double recall){
		double numerador = 2 * precision * recall;
		double denominador = precision + recall;
		
		double f1 = numerador / denominador;
		
		return f1;
	}
	
	/**
	 * Obtiene la precision a @param k
	 */
	private static double getPrecK(int k, LinkedList<Boolean> drels){
		int relevantes = 0;
		if(drels.size() < k){
			for(Boolean b : drels){
				if(b)relevantes++;
			}
			return (double)((double)relevantes / (double)k);
		}
		else{
			for(int i = 0; i < k; i++){
				Boolean b = drels.get(i);
				if(b) relevantes++;
			}
			return (double)((double)relevantes / (double)k);
		}
	}
	
	/**
	 * Obtiene la precision media
	 */
	private static double getAvgPrecision(LinkedList<Double> precisiones,
			LinkedList<Boolean> relevantes) {
		double avg_precision = 0.0;
		int docs_rel = 0;
		for (int i = 0; i < relevantes.size(); i++) {
			if(relevantes.get(i)){
				avg_precision += precisiones.get(i);
				docs_rel++;
			}
		}
		avg_precision = avg_precision / docs_rel;
		
		return avg_precision;
	}
	
	private static double getMAP(LinkedList<Double> precisiones, int queries){
		double prec = 0.0;
		for(Double d : precisiones)
			prec = prec + d;
		return (prec / queries);
	}
	
	/**
	 * Obtiene los puntos recall-precision de la evaluacion
	 */
	private static LinkedList<RecallPrecision> merge(LinkedList<Double> precs, LinkedList<Double> recs){
		LinkedList<RecallPrecision> rec_prec = new LinkedList<RecallPrecision>();
		
		for (int i = 0; i < precs.size(); i++) {
			double p = precs.get(i);
			double r = recs.get(i);
			rec_prec.add(i, new RecallPrecision(r, p));
		}
		return rec_prec;
	}
	
	/**
	 * Obtiene los puntos recall-precision interpolados de la evaluacion
	 */
	private static LinkedList<RecallPrecision> mergeInterpolated(LinkedList<RecallPrecision> rec_prec){
		LinkedList<RecallPrecision> int_rec_prec = new LinkedList<RecallPrecision>();
		
		for(double recall = 0.0; recall <= 1.0; recall = recall + 0.1){
			double minR = recall;
			
			double prec_at_rec = 0.0;
			for(RecallPrecision rp : rec_prec){
				double r = rp.getRecall();
				double p = rp.getPrecision();
				
				if(r >= minR){
					prec_at_rec = Math.max(prec_at_rec, p);
				}
			}
			int_rec_prec.add(new RecallPrecision(recall, prec_at_rec));
		}
		return int_rec_prec;
	}
	
	/**
	 * Calcula la media de las medidas de evaluacion para todas las 
	 * necesidades de informacion
	 */
	private static Data calcularMedias(LinkedList<Data> data){
		double precision = 0.0, recall = 0.0, avg_prec = 0.0, prec10 = 0.0;
		LinkedList<RecallPrecision> rec_prec = new LinkedList<RecallPrecision>(),
				int_rec_prec = new LinkedList<RecallPrecision>();
		LinkedList<Double> precs = new LinkedList<Double>();
		// acumular
		for(Data d: data){
			precision = precision + d.getPrecision();
			recall = recall + d.getRecall();
			prec10 = prec10 + d.getPrec10();
			precs.add(d.getAvg_prec());
			
			for(int i = 0; i < d.getInt_rec_prec().size(); i++){
				double r = d.getInt_rec_prec().get(i).getRecall();
				double p = d.getInt_rec_prec().get(i).getPrecision();
				
				if(int_rec_prec.size() > i){
					double pp = int_rec_prec.get(i).getPrecision();
					
					p += pp;
					int_rec_prec.remove(i);
				}
				
				int_rec_prec.add(i, new RecallPrecision(r, p));
			}
		}
		
		// media
		precision = precision / data.size();
		recall = recall / data.size();
		avg_prec = getMAP(precs, data.size());
		prec10 = prec10 / data.size();
		
		for(int i = 0; i < int_rec_prec.size(); i++){
			double r = int_rec_prec.get(i).getRecall();
			double p = int_rec_prec.get(i).getPrecision() / data.size();
			
			int_rec_prec.remove(i);
			int_rec_prec.add(i, new RecallPrecision(r, p));
		}
		
		Data d = new Data(precision, recall, getF1(precision, recall), avg_prec, prec10, rec_prec, int_rec_prec);
		return d;
	}
}
