package sistemaSemantico;

public class SemanticGenerator {

	public static void main(String[] args) {
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
			else if(arg.equals("-docsPath")){
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
	}
}
