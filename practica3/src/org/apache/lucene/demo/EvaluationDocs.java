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
		HashMap<Integer, HashMap<Integer,Boolean>> qrels 
			= obtenerRelevancia(qrelsFileName);
		
		HashMap<Integer, LinkedList<Integer>> results = obtenerResultados(resultsFileName);
		
		HashMap<Integer, LinkedList<Boolean>> relevancia = merge(qrels, results);
		HashMap<Integer, Integer> docs_relevantes = documentos_relevantes(qrels);
		
		/* Calcula las medidas de evaluacion de los juicios obtenidos */
		
		for (Entry<Integer, HashMap<Integer, Boolean>> entry : qrels.entrySet()){
			System.out.println("INFO NEED: " + entry.getKey());
			
			for(Entry<Integer, Boolean> entry2 : entry.getValue().entrySet()){
				System.out.println("DOC_ID: " + entry2.getKey() 
					+ " RELEVANCY: " + entry2.getValue());
			}
		}
	}
	
	/**
	 * Obtiene los datos de @param qrelsFileName en un TAD
	 */
	private static HashMap<Integer, HashMap<Integer,Boolean>> obtenerRelevancia(String qrelsFileName){
		HashMap<Integer, HashMap<Integer,Boolean>> qrels
			= new HashMap<Integer, HashMap<Integer,Boolean>>();
		
		try {
			Scanner s = new Scanner(new File(qrelsFileName));
			
			while(s.hasNextLine()){
				// obtener datos
				int infoNeed = s.nextInt();
				int docid = s.nextInt();
				boolean relevancy = s.nextInt() == 1;
				
				// obtener el mapa de infoNeed
				HashMap<Integer, Boolean> docs = qrels.get(infoNeed);
				
				// crear el mapa si no existe todavia
				if(docs == null){
					docs = new HashMap<Integer, Boolean>();
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
	private static HashMap<Integer, LinkedList<Integer>> obtenerResultados(String resultsFileName){
		HashMap<Integer, LinkedList<Integer>> results = new HashMap<Integer, LinkedList<Integer>>();
		
		try{
			Scanner s = new Scanner(new File(resultsFileName));
			
			while(s.hasNextLine()){
				// obtener datos
				int infoNeed = s.nextInt();
				int docid = s.nextInt();
				
				// obtener la lista y crearla si es necesario
				LinkedList<Integer> lista = results.get(infoNeed);
				if(lista == null){
					lista = new LinkedList<Integer>();
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
	private static HashMap<Integer, LinkedList<Boolean>> merge(HashMap<Integer, HashMap<Integer, Boolean>> qrels,
			HashMap<Integer, LinkedList<Integer>> results){
		// crear el TAD para devolver resultados
		HashMap<Integer, LinkedList<Boolean>> relevancia = new HashMap<Integer, LinkedList<Boolean>>();
		
		for (Entry<Integer, LinkedList<Integer>> entry : results.entrySet()){
			// necesidad de informacion
			int infoNeed = entry.getKey();
			LinkedList<Boolean> rel = new LinkedList<Boolean>();
			
			for (int i = 0; i < entry.getValue().size(); i++) {
				// doc id
				int docid = entry.getValue().get(i);
				
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
	private static HashMap<Integer, Integer> documentos_relevantes(HashMap<Integer, HashMap<Integer, Boolean>> qrels){
		HashMap<Integer, Integer> docs_rels = new HashMap<Integer, Integer>();
		
		for (Entry<Integer,  HashMap<Integer, Boolean>> entry : qrels.entrySet()){
			int infoNeed = entry.getKey();
			
			int relevantes = 0;
			for(Entry<Integer, Boolean> entry2 : entry.getValue().entrySet()){
				if(entry2.getValue()){
					relevantes++;
				}
			}
			
			docs_rels.put(infoNeed, relevantes);
		}
		return docs_rels;
	}
}
