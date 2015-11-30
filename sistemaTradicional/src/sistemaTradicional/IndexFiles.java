/*
 * Fichero: IndexFiles.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaTradicional;

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
		String uso = "java IndexFiles -index <indexPath> [-docs <docsPath> | -dump <dumpPath>]";
		String indexPath = null;
		String docsPath = null;
		String dumpPath = null;
		
		boolean dump = false;
		
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
			else if(s.equals("-dump")){
				/* dumpPath */
				i++;
				dumpPath = args[i];
				dump = true;
			}
		}
		
		/* comprobar argumentos */
		if(indexPath == null || 
				(!dump && docsPath == null) ||
				(dump && dumpPath == null)){
			System.err.println(uso);
			return ;
		}
		
		/* continuar la indexacion */
		if(!dump)
			IndexDocs.index(indexPath, docsPath);
		else
			IndexDocsFromDump.index(indexPath, dumpPath);
	}

}
