package org.apache.lucene.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

public class EvaluationDocs {
	/**
	 * Clase auxiliar para realizar la evaluacion de un sistema
	 * de recuperacion de informacion
	 * @version 1.0
	 */

	/**
	 * Obtiene a partir del fichero <qrelsFileName> la relevancia
	 * de un documento para la pareja infoNeed-docID, la busca
	 * en el fichero <resultsFileName>, calcula las medidas de
	 * evaluacion para esos resultados y escribe los resultados
	 * en el fichero <outputFileName>
	 * 
	 * - <qrelsFileName>: fichero con los juicios de relevancia
	 * - <resultsFileName>: fichero con los documentos recuperados
	 * - <outputFileName>: fichero con el resultado de la evaluacion
	 */
	public static void evaluate(String qrelsFileName, String resultsFileName, String outputFileName){
		
		/* Obtiene los juicios de relevancia a partir del fichero <qrelsFileName> */
		HashMap<String, HashMap<String,Boolean>> qrels 
			= obtenerRelevancia(qrelsFileName);
		
		HashMap<String, LinkedList<String>> results = obtenerResultados(resultsFileName);
		
		HashMap<String, LinkedList<Boolean>> relevancia = merge(qrels, results);
		HashMap<String, Integer> docs_relevantes = documentos_relevantes(qrels);
		
		/* Calcula las medidas de evaluacion de los juicios obtenidos */
		Measures.measures(outputFileName, docs_relevantes, relevancia);
		
//		for (Entry<String, HashMap<String, Boolean>> entry : qrels.entrySet()){
//			System.out.println("INFO NEED: " + entry.getKey());
//			
//			for(Entry<String, Boolean> entry2 : entry.getValue().entrySet()){
//				System.out.println("DOC_ID: " + entry2.getKey() 
//					+ " RELEVANCY: " + entry2.getValue());
//			}
//		}
	}
	
	/**
	 * Obtiene los datos de @param qrelsFileName en un TAD
	 */
	private static HashMap<String, HashMap<String,Boolean>> obtenerRelevancia(String qrelsFileName){
		HashMap<String, HashMap<String,Boolean>> qrels
			= new HashMap<String, HashMap<String,Boolean>>();
		
		try {
			Scanner s = new Scanner(new File(qrelsFileName));
			
			while(s.hasNextLine()){
				// obtener datos
				String infoNeed = s.next();
				String docid = s.next();
				boolean relevancy = s.nextInt() == 1;
				
				// obtener el mapa de infoNeed
				HashMap<String, Boolean> docs = qrels.get(infoNeed);
				
				// crear el mapa si no existe todavia
				if(docs == null){
					docs = new HashMap<String, Boolean>();
				}
				
				// agregar el nuevo valor al mapa
				docs.put(docid, relevancy);
				qrels.put(infoNeed, docs);
				
				s.nextLine();
			}
			
			s.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return qrels;
	}
	
	/**
	 * 
	 * @param resultsFileName
	 * @return
	 */
	private static HashMap<String, LinkedList<String>> obtenerResultados(String resultsFileName){
		HashMap<String, LinkedList<String>> results = new HashMap<String, LinkedList<String>>();
		
		try{
			Scanner s = new Scanner(new File(resultsFileName));
			
			while(s.hasNextLine()){
				// obtener datos
				String infoNeed = s.next();
				String docid = s.next();
				
				// obtener la lista y crearla si es necesario
				LinkedList<String> lista = results.get(infoNeed);
				if(lista == null){
					lista = new LinkedList<String>();
				}
				
				// agregar datos
				lista.add(docid);
				results.put(infoNeed, lista);
				
				s.nextLine();
			}
			
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	/**
	 * 
	 */
	private static HashMap<String, LinkedList<Boolean>> merge(HashMap<String, HashMap<String, Boolean>> qrels,
			HashMap<String, LinkedList<String>> results){
		// crear el TAD para devolver resultados
		HashMap<String, LinkedList<Boolean>> relevancia = new HashMap<String, LinkedList<Boolean>>();
		
		for (Entry<String, LinkedList<String>> entry : results.entrySet()){
			// necesidad de informacion
			String infoNeed = entry.getKey();
			LinkedList<Boolean> rel = new LinkedList<Boolean>();
			
			for (int i = 0; i < entry.getValue().size(); i++) {
				// doc id
				String docid = entry.getValue().get(i);
				
				// obtener relevancia
				Boolean relevante = qrels.get(infoNeed).get(docid);
				if(relevante == null){
					relevante = false;
				}
				// agregar a la lista
				rel.add(relevante);
			}
			
			relevancia.put(infoNeed, rel);
		}
		
		return relevancia;
	}
	
	/**
	 * 
	 */
	private static HashMap<String, Integer> documentos_relevantes(HashMap<String, HashMap<String, Boolean>> qrels){
		HashMap<String, Integer> docs_rels = new HashMap<String, Integer>();
		
		for (Entry<String,  HashMap<String, Boolean>> entry : qrels.entrySet()){
			String infoNeed = entry.getKey();
			
			int relevantes = 0;
			for(Entry<String, Boolean> entry2 : entry.getValue().entrySet()){
				if(entry2.getValue()){
					relevantes++;
				}
			}
			
			docs_rels.put(infoNeed, relevantes);
		}
		return docs_rels;
	}
}
