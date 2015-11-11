package org.apache.lucene.demo;

public class Evaluation {
	/**
	 * Clase para realizar la evaluacion de un sistema
	 * de recuperacion de informacion
	 * @version 1.0
	 */

	/**
	 * java Evaluation -qrels <qrelsFileName>
	 * 				   -results <resultsFileName>
	 * 				   -output <outputFileName>
	 * 
	 * - <qrelsFileName>: fichero con los juicios de relevancia
	 * - <resultsFileName>: fichero con los documentos recuperados
	 * - <outputFileName>: fichero con el resultado de la evaluacion
	 */
	public static void main(String[] args) {
		String uso = "java Evaluation -qrels <qrelsFileName>"
								+ " -results <resultsFileName>"
								+ " -output <outputFileName>";
		String qrelsFileName = null;
		String resultsFileName = null;
		String outputFileName = null;
		
		/* obtener argumentos */
		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if(s.equals("-qrels")){
				/* qrelsFileName */
				i++;
				qrelsFileName = args[i];
			}
			else if(s.equals("-results")){
				/* resultsFileName */
				i++;
				resultsFileName = args[i];
			}
			else if(s.equals("-output")){
				/* outputFileName */
				i++;
				outputFileName = args[i];
			}
		}
		
		/* comprobar argumentos */
		if(qrelsFileName == null || resultsFileName == null || outputFileName == null){
			System.err.println(uso);
			return ;
		}
		
		/* continuar evaluacion */
		EvaluationDocs.evaluate(qrelsFileName, resultsFileName, outputFileName);
	}
}
