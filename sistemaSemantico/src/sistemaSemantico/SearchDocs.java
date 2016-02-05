package sistemaSemantico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class SearchDocs {

	public static void searchDocs(String rdfPath, String rdfsPath, 
			String infoNeeds, String resultsFile) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(resultsFile);
		List<String> infos = getInfoNeeds(infoNeeds);
		
		// Load ontologia (RDFS) y datos (RDF)
		OntModel base = ModelFactory.createOntologyModel();
		base.read(rdfsPath, "RDF/XML" );
		Model data = FileManager.get().loadModel(rdfPath);
		base.add(data);

		for(String info : infos){
			List<String> ids = null;
			
			switch(info){
			case "13-2":
				ids = execConsulta1();
				break;
			case "02-4":
				ids = execConsulta2();
				break;
			case "09-3":
				ids = execConsulta3();
				break;
			case "07-2":
				ids = execConsulta4();
				break;
			case "05-5":
				ids = execConsulta5();
				break;
			}
			
			if(ids != null){
				for(String id : ids){
					out.println(info + "\t" + id);
				}
			}
		}
		out.close();
	}

	private static List<String> execConsulta1() {
		return null;
	}

	private static List<String> execConsulta2() {
		return null;
	}

	private static List<String> execConsulta3() {
		return null;
	}

	private static List<String> execConsulta4() {
		return null;
	}

	private static List<String> execConsulta5() {
		return null;
	}

	private static List<String> getInfoNeeds(String infoNeeds) throws FileNotFoundException {
		List<String> infos = new LinkedList<String>();
		Scanner s = new Scanner(new File(infoNeeds));
		while(s.hasNextLine()){
			String line = s.nextLine();
			String[] splitted = line.split(" ");
			infos.add(splitted[0]);
		}
		s.close();
		
		return infos;
	}

}
