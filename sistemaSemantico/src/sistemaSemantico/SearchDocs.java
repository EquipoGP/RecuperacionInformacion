package sistemaSemantico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class SearchDocs {

	public static void searchDocs(String rdfPath, String rdfsPath, 
			String infoNeeds, String resultsFile) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(resultsFile);
		Map<String, String> infos = getInfoNeeds(infoNeeds);
		
		// Load ontologia (RDFS) y datos (RDF)
		OntModel base = ModelFactory.createOntologyModel();
		base.read(rdfsPath, "RDF/XML" );
		Model data = FileManager.get().loadModel(rdfPath);
		base.add(data);

		for (Map.Entry<String, String> entry : infos.entrySet()){
			String info = entry.getKey();
			String query = entry.getValue();
			
			List<String> ids = null;
			ids = executeQuery(query);
			
			if(ids != null){
				for(String id : ids){
					out.println(info + "\t" + id);
				}
			}
		}
		out.close();
	}

	private static List<String> executeQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Map<String, String> getInfoNeeds(String infoNeeds) throws FileNotFoundException {
		Map<String, String> infos = new HashMap<String, String>();
		Scanner s = new Scanner(new File(infoNeeds));
		while(s.hasNextLine()){
			String line = s.nextLine();
			String[] splitted = line.split(" ");
			
			String id = splitted[0];
			String info = "";
			for(int i = 1; i < splitted.length; i++){
				info += splitted[i];
			}
			infos.put(id, info);
		}
		s.close();
		
		return infos;
	}

}
