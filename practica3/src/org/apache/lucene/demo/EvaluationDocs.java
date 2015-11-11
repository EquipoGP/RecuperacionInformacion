package org.apache.lucene.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
		
		/* Calcula las medidas de evaluacion de los juicios obtenidos */
		
		for (Entry<Integer, HashMap<Integer, Boolean>> entry : qrels.entrySet()){
			System.out.println("INFO NEED: " + entry.getKey());
			
			for(Entry<Integer, Boolean> entry2 : entry.getValue().entrySet()){
				System.out.println("DOC_ID: " + entry2.getKey() 
					+ " RELEVANCY: " + entry2.getValue());
			}
		}
	}
	
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
}
