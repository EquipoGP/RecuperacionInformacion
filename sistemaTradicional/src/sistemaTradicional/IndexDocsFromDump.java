/*
 * Fichero: IndexDocsFromDump.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaTradicional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexDocsFromDump {
	/**
	 * Clase auxiliar para la indexacion de documentos desde un 
	 * fichero de dump
	 * @version 1.0
	 */
	
	/**
	 * Indexa los contenidos de <dumpPath> creando el indice en <indexPath>
	 * 
	 * @param indexPath: ruta donde dejar el indice
	 * @param dumpPath: ruta donde esta el fichero de dump
	 */
	@SuppressWarnings("deprecation")
	public static void index(String indexPath, String dumpPath) {
		try {
			/* Creacion del directorio y analizador */
			Directory dir = FSDirectory.open(new File(indexPath));
			/* Analizador */
			Analyzer analyzer = new TradicionalAnalyzer();

			/* Configurar el writer */
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);

			/* Crear el writer */
			IndexWriter writer = new IndexWriter(dir, iwc);

			File f = new File(dumpPath);
			Scanner s = new Scanner(new FileInputStream(f), "UTF-8");

			String url = null;

			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (line.contains("url:")) {
					String[] array = line.split("/");
					url = array[array.length - 1];
				} else if (line.contains("<?xml")) {
					/*
					 * los ficheros XML se encuentran en una unica linea,
					 * comenzando por "<?xml"
					 */
					Document doc = new Document();
					InputStream fis = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));

					doc = IndexDocs.parse(fis, doc);
					doc.add(new StringField("path", url, Field.Store.YES));
					writer.addDocument(doc);
					url = null;
				}
			}
			s.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
