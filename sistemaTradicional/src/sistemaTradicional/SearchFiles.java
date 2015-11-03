package sistemaTradicional;

public class SearchFiles {
	/**
	 * Clase para buscar los ficheros de acuerdo a las necesidades de
	 * informacion
	 * @version 1.0
	 */

	/**
	 * java SearchFiles -index <indexPath> -infoNeeds <infoNeedsFile> -output
	 * <resultsFile>
	 * - <indexPath>: directorio donde se encuentran los indices
	 * - <infoNeedsFile>: fichero de las necesidades de informacion
	 * - <resultsFile>: fichero de salida de los resultados
	 */
	public static void main(String[] args) {
		String uso = "java SearchFiles -index <indexPath> -infoNeeds <infoNeedsFile> -output <resultsFile>";
		String indexPath = null;
		String infoNeedsFile = null;
		String resultsFile = null;
		
		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if(s.equals("-index")){
				i++;
				indexPath = args[i];
			}
			else if(s.equals("-infoNeeds")){
				i++;
				indexPath = args[i];
			}
			else if(s.equals("-output")){
				i++;
				resultsFile = args[i];
			}
		}
		
		if(indexPath == null || infoNeedsFile == null || resultsFile == null){
			System.err.println(uso);
			return ;
		}
	}
}
