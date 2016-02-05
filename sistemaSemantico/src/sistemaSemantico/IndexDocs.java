/*
 * Fichero: IndexDocs.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IndexDocs {
	/**
	 * Clase auxiliar para la indexacion de documentos
	 * @version 1.0
	 */

	/**
	 * Indexa los contenidos de <docsPath> creando el indice en <indexPath>
	 * 
	 * @param indexPath: ruta donde dejar el indice
	 * @param docsPath: ruta donde estan los ficheros a indexar
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws FileNotFoundException 
	 */
	public static void index(String indexPath, String docsPath) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		final File fileDir = new File(docsPath); // directorio corpus

		/* gestion de errores */
		if (!fileDir.exists() || !fileDir.canRead()) {
			System.err.println("ERROR: " + fileDir.getAbsolutePath() + " no existe o no tiene derechos de lectura");
			return;
		}
		/* Creacion del directorio y analizador */
		String[] files = fileDir.list();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		/* Indexar ficheros */
		for (String file : files) {
			File f = new File(fileDir, file);
			Document d = db.parse(new FileInputStream(f));
			d.getDocumentElement().normalize();
			
			String[] creators = parseCreator(d);
			String title = parseTitle(d);
			int date = parseDate(d);
			String description = parseSummary(d);
			String identifier = f.getPath();
		}
	}
	
	/**
	 * Obtiene los creadores del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static String[] parseCreator(Document doc){
		NodeList creators = doc.getElementsByTagName("dc:creator");
		String[] owners = null;
		
		if(creators != null && creators.getLength() > 0){
			owners = new String[creators.getLength()];
			for (int i = 0; i < creators.getLength(); i++) {
				owners[i] = creators.item(i).getTextContent();
			}
		}
		return owners;
	}
	
	/**
	 * Obtiene el anio de creacion del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static int parseDate(Document doc){
		NodeList dates = doc.getElementsByTagName("dc:date");
		int date = -1;
		
		if(dates != null && dates.getLength() > 0){
			date = Integer.parseInt(dates.item(0).getTextContent().trim());
		}
		
		return date;
	}
	
	/**
	 * Obtiene el titulo del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static String parseTitle(Document doc){
		NodeList titles = doc.getElementsByTagName("doc:title");
		String title = null;
		
		if(titles != null && titles.getLength() > 0){
			title = titles.item(0).getTextContent();
		}
		
		return title;
	}

	/**
	 * Obtiene el sumario o resumen del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static String parseSummary(Document doc){
		NodeList summaries = doc.getElementsByTagName("dc:description");
		String summary = "";
		
		if(summaries != null && summaries.getLength() > 0){
			summary = summaries.item(0).getTextContent();
		}
		
		return summary;
	}
}
