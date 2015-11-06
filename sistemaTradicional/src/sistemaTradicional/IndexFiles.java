package sistemaTradicional;

import java.util.Date;

public class IndexFiles {
	/**
	 * Clase para indexar los ficheros
	 * @version 1.0
	 */

	/**
	 * Llamada: java IndexFiles -index <indexPath> -docs <docsPath>
	 * - <indexPath>: lugar donde se generaran los indices de Lucence
	 * - <docsPath>: directorio que contiene los ficheros a indexar
	 */
	public static void main(String[] args) {
		String uso = "java IndexFiles -index <indexPath> -docs <docsPath>";
		String indexPath = null;
		String docsPath = null;
		
		/* obtener argumentos */
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			if(s.equals("-index")){
				/* indexPath */
				i++;
				indexPath = args[i];
			}
			else if(s.equals("-docs")){
				/* docsPath */
				i++;
				docsPath = args[i];
			}
		}
		
		/* comprobar argumentos */
		if(indexPath == null || docsPath == null){
			System.err.println(uso);
			return ;
		}
		
		/* continuar la indexacion */
		System.out.println("Indexando...");
		Date start = new Date();
		IndexDocs.index(indexPath, docsPath);
		Date end = new Date();
		System.out.println("Finalizada indexacion. Tiempo: " + (end.getTime() - start.getTime()));
	}

}
