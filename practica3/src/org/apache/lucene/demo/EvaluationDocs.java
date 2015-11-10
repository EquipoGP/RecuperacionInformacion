package org.apache.lucene.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
		HashMap<Object, HashMap<Object,Object>> juicios = obtenerRelevancia(qrelsFileName);
		
		/* Calcula las medidas de evaluacion de los juicios obtenidos */
		
		
	}
	
	private static HashMap<Object, HashMap<Object,Object>> obtenerRelevancia(String qrelsFileName){
		
		HashMap<Object, HashMap<Object,Object>> juicios = new HashMap<Object, HashMap<Object,Object>>();
		
		try {
			Scanner leer = new Scanner(new File(qrelsFileName));
			
			/* Informacion del fichero */
			int infoNeed = 0;
			int prevInfoNeed = infoNeed;
			int docID = 0;
			int rel = 0;
			HashMap<Object,Object> infoDocs = new HashMap<Object,Object>();
			
			/* Lee el fichero qrelsFileName */
			while(leer.hasNextLine()){
				prevInfoNeed = infoNeed;
				
				infoNeed = leer.nextInt();
				docID = leer.nextInt();
				rel = leer.nextInt();
				
				if (infoNeed > prevInfoNeed) {
					juicios.put(infoNeed, infoDocs);
					
					infoDocs = new HashMap<Object,Object>();
					infoDocs.put(docID, rel);
				}
				else {
					infoDocs.put(docID, rel);
				}
			}
			
			leer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return juicios;
	}
}
