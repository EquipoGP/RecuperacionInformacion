/*
 * Fichero: IndexDocs.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaTradicional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.w3c.dom.NodeList;

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
	 */
	@SuppressWarnings("deprecation")
	public static void index(String indexPath, String docsPath) {
		final File fileDir = new File(docsPath); // directorio corpus

		/* gestion de errores */
		if (!fileDir.exists() || !fileDir.canRead()) {
			System.err.println("ERROR: " + fileDir.getAbsolutePath() + " no existe o no tiene derechos de lectura");
			return;
		}

		try {
			/* Creacion del directorio y analizador */
			Directory dir = FSDirectory.open(new File(indexPath));
			String[] files = fileDir.list();
			
			/* Analizador */
			Analyzer analyzer = new TradicionalAnalyzer();
			
			/* Configurar el writer */
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			
			/* Crear el writer */
			IndexWriter writer = new IndexWriter(dir, iwc);
			
			/* Indexar ficheros */
			for (String file : files) {
				File f = new File(fileDir, file);
				FileInputStream fis = new FileInputStream(f);
				Document doc = new Document();
				doc = parse(fis, doc);
				doc.add(new StringField("path", f.getPath(), Field.Store.YES));
				writer.addDocument(doc);
			}
			
			/* cerrar writer */
			writer.close();
		} catch (IOException e) {
			System.err.println("ERROR: error inesperado");
		}
	}
	
	/**
	 * Parsea el contenido de un fichero y devuelve el documento que contiene 
	 * los campos importantes para dicho documento
	 * @param fis: fichero a indexar
	 * @param doc: documento donde introducir los campos
	 * @return
	 */
	public static Document parse(InputStream fis, Document doc){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document d = db.parse(fis);
			
			d.getDocumentElement().normalize();
			
			String creators = getCreators(d);
			int date = getDate(d);
			String title = getTitle(d);
			String summary = getSummary(d);
			
			doc.add(new TextField("creador", creators, Field.Store.YES));
			doc.add(new IntField("fecha", date, Field.Store.YES));
			doc.add(new TextField("titulo", title, Field.Store.YES));
			doc.add(new TextField("sumario", summary, Field.Store.YES));
		}
		catch(Exception e){
			System.err.println("ERROR: error inesperado");
		}
		
		return doc;
	}
	
	/**
	 * Obtiene los creadores del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static String getCreators(org.w3c.dom.Document doc){
		NodeList creators = doc.getElementsByTagName("dc:creator");
		String owners = "";
		
		if(creators != null && creators.getLength() > 0){
			for (int i = 0; i < creators.getLength(); i++) {
				owners = owners + creators.item(i).getTextContent() + " ";
			}
		}
		
		return owners;
	}
	
	/**
	 * Obtiene el anio de creacion del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static int getDate(org.w3c.dom.Document doc){
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
	private static String getTitle(org.w3c.dom.Document doc){
		NodeList titles = doc.getElementsByTagName("doc:title");
		String title = "";
		
		if(titles != null && titles.getLength() > 0){
			title = titles.item(0).getTextContent();
		}
		
		return title;
	}

	/**
	 * Obtiene el sumario o resumen del documento
	 * @param doc: documento del que extraer informacion
	 */
	private static String getSummary(org.w3c.dom.Document doc){
		NodeList summaries = doc.getElementsByTagName("dc:description");
		String summary = "";
		
		if(summaries != null && summaries.getLength() > 0){
			summary = summaries.item(0).getTextContent();
		}
		
		return summary;
	}
}
