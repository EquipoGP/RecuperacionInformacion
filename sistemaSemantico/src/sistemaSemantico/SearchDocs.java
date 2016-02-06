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

	public static void searchDocs(String rdfPath, String rdfsPath,
			String infoNeeds, String resultsFile) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(resultsFile);
		Map<String, String> infos = getInfoNeeds(infoNeeds);

		// Load ontologia (RDFS) y datos (RDF)
		OntModel base = ModelFactory.createOntologyModel();
		base.read(rdfsPath, "RDF/XML");
		Model data = FileManager.get().loadModel(rdfPath);
		base.add(data);

		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String info = entry.getKey();
			String query = entry.getValue();

			List<String> ids = null;
			ids = executeQuery(query, base);

			if (ids != null) {
				for (String id : ids) {
					out.println(info + "\t" + id);
				}
			}
		}
		out.close();
	}

	private static List<String> executeQuery(String query, OntModel model) {
		List<String> ids = new LinkedList<String>();
		Query q = QueryFactory.create(query);
		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode id = soln.get("id");
				ids.add(id.toString());
			}
		} finally {
			qexec.close();
		}
		return ids;
	}

	private static Map<String, String> getInfoNeeds(String infoNeeds)
			throws FileNotFoundException {
		Map<String, String> infos = new HashMap<String, String>();
		Scanner s = new Scanner(new File(infoNeeds));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitted = line.split(" ");

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
