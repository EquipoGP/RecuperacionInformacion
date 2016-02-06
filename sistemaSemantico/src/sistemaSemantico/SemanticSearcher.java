/*
 * Fichero: SemanticSearcher.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import java.io.FileNotFoundException;

public class SemanticSearcher {

	/**
	 * Clase principal para la busqueda de queries en un modelo semantico
	 */

	/**
	 * Metodo principal para la busqueda de consultas/necesidades de informacion
	 * sobre un modelo semantico
	 * 
	 * @param args
	 *            argumentos
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String rdfPath = null;
		String rdfsPath = null;
		String infoNeeds = null;
		String resultsFile = null;

		/* salir si no hay argumentos */
		if (args.length == 0) {
			System.err.println("Uso: java SemanticSearcher -rdf <rdfPath> "
					+ "-rdfs <rdfsPath> -infoNeeds <infoNeedsFile> "
					+ "-output <resultsFile>");
			System.exit(1);
		}

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (arg.equals("-rdf")) {
				i++;
				rdfPath = args[i];
			} else if (arg.equals("-rdfs")) {
				i++;
				rdfsPath = args[i];
			} else if (arg.equals("-infoNeedsFile")) {
				i++;
				infoNeeds = args[i];
			} else if (arg.equals("-resultsFile")) {
				i++;
				resultsFile = args[i];
			}
		}

		/* salir si no se han introducido todos los argumentos */
		if (rdfPath == null || rdfsPath == null || infoNeeds == null
				|| resultsFile == null) {
			System.err.println("Uso: java SemanticSearcher -rdf <rdfPath> "
					+ "-rdfs <rdfsPath> -infoNeeds <infoNeedsFile> "
					+ "-output <resultsFile>");
			System.exit(1);
		}

		SearchDocs.searchDocs(rdfPath, rdfsPath, infoNeeds, resultsFile);
	}
}
