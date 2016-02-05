package sistemaSemantico;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SemanticGenerator {

	public static void main(String[] args) 
			throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		String rdfPath = null;
		String skosPath = null;
		String docsPath = null;
		
		/* salir si no hay argumentos */
		if(args.length == 0){
			System.err.println("Uso: java SemanticGenerator -rdf <rdfPath> "
					+ "-skos <skosPath> -docs <docsPath>");
			System.exit(1);
		}
		
		for(int i = 0; i < args.length; i++){
			String arg = args[i];
			
			if(arg.equals("-rdf")){
				i++;
				rdfPath = args[i];
			}
			else if(arg.equals("-skos")){
				i++;
				skosPath = args[i];
			}
			else if(arg.equals("-docs")){
				i++;
				docsPath = args[i];
			}
		}
		
		/* salir si no se han introducido todos los argumentos */
		if(rdfPath == null || skosPath == null || docsPath == null){
			System.err.println("Uso: java SemanticGenerator -rdf <rdfPath> "
					+ "-skos <skosPath> -docs <docsPath>");
			System.exit(1);
		}
		
		IndexDocs.index(rdfPath, skosPath, docsPath);
	}
}
