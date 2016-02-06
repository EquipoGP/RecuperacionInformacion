/*
 * Fichero: SearchDocs.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

public class SearchDocs {

	/**
	 * Clase auxiliar para la busqueda de elementos en el modelo semantico
	 */

	/**
	 * Metodo para la busqueda de queries en el modelo semantico
	 * 
	 * @param rdfPath
	 *            ruta donde se encuentra el fichero de datos RDF
	 * @param rdfsPath
	 *            ruta donde se encuentra el modelo RDFS
	 * @param infoNeeds
	 *            ruta donde se encuentran las necesidades de informacion
	 * @param resultsFile
	 *            ruta donde almacenar los resultados
	 * @throws FileNotFoundException
	 */
	public static void searchDocs(String rdfPath, String rdfsPath,
			String infoNeeds, String resultsFile) throws FileNotFoundException {
		/* Preparacion */
		PrintWriter out = new PrintWriter(resultsFile);
		Map<String, String> infos = getInfoNeeds(infoNeeds);

		/* Cargar RDFS y datos (RDF) */
		OntModel base = ModelFactory.createOntologyModel();
		base.read(rdfsPath, "RDF/XML");
		Model data = FileManager.get().loadModel(rdfPath);
		base.add(data);

		/* buscar info de las queries */
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String info = entry.getKey();
			String query = entry.getValue();

			List<String> ids = null;
			ids = executeQuery(query, base);

			if (ids != null) {
				for (String id : ids) {
					out.println(info + "\t" + id);
					System.out.println(info + "\t" + id);
				}
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * Metodo para la ejecucion de una query
	 * 
	 * @param query
	 *            query a ejecutar
	 * @param model
	 *            modelo sobre el que ejecutar la query
	 * @return una lista con los identificadores de los documentos recuperados
	 */
	private static List<String> executeQuery(String query, OntModel model) {
		List<String> ids = new LinkedList<String>();

		Query q = QueryFactory.create(query);
		QueryExecution qexec = QueryExecutionFactory.create(q, model);

		/* iterar sobre los resultados */
		ResultSet results = qexec.execSelect();
		for (;results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			RDFNode id = soln.get("id");
			ids.add(id.toString());
		}
		qexec.close();

		return ids;
	}

	/**
	 * Metodo para la obtencion de las parejas <id, query> del fichero de
	 * necesidades de informacion
	 * 
	 * @param infoNeeds
	 *            ruta del fichero de necesidades de informacion
	 * @return un mapa con los pares <id, query> almacenados
	 * @throws FileNotFoundException
	 */
	private static Map<String, String> getInfoNeeds(String infoNeeds)
			throws FileNotFoundException {
		Map<String, String> infos = new HashMap<String, String>();
		Scanner s = new Scanner(new File(infoNeeds));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitted = line.split("\t");

			String id = splitted[0];
			String info = "";
			for (int i = 1; i < splitted.length; i++) {
				info += splitted[i];
			}
			infos.put(id, info);
		}
		s.close();

		return infos;
	}

}
