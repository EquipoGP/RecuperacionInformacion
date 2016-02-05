/*
 * Fichero: IndexDocs.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class IndexDocs {
	
	private static Model rdfs, skos;
	
	/**
	 * Clase auxiliar para la indexacion de documentos
	 * 
	 * @version 1.0
	 */

	public static void index(String rdfPath, String skosPath, String docsPath)
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
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

		rdfs = Modelo.crearModelo();
		skos = Modelo.crearSkosModel();

		/* Indexar ficheros */
		for (String file : files) {
			File f = new File(fileDir, file);
			introducir(db, f);
		}
		
		// escribir los modelos
		rdfs.write(new FileOutputStream(new File(rdfPath)));
		skos.write(new FileOutputStream(new File(skosPath)));
	}

	private static void introducir(DocumentBuilder db, File f)
			throws FileNotFoundException, SAXException, IOException {
		Document d = db.parse(new FileInputStream(f));
		d.getDocumentElement().normalize();
		
		Property type = rdfs.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		Property name = rdfs.getProperty(Modelo.prefix + "#name");
		Property identifier = rdfs.getProperty(Modelo.prefix + "#identifier");
		Property creator = rdfs.getProperty(Modelo.prefix + "#creator");
		Property date = rdfs.getProperty(Modelo.prefix + "#date");

		String[] d_creators = parseCreator(d);
		String d_title = parseTitle(d);
		int d_date = parseDate(d);
		String d_description = parseSummary(d);
		String d_identifier = f.getPath();

		/* Documento */
		Resource documento = rdfs.createResource(Modelo.prefix + d_identifier);
		documento.addProperty(type, rdfs.getResource(Modelo.prefix + "#Documento"));
		// Propiedad date
		documento.addProperty(date, rdfs.createTypedLiteral(d_date));
		// Propiedad identifier
		documento.addProperty(identifier, rdfs.createTypedLiteral(d_identifier));
		
		/* Autores */
		if(d_creators != null && d_creators.length > 0){
			for(String autor : d_creators){
				// obtener persona
				Resource persona = rdfs.getResource(Modelo.prefix + autor);
				if(!rdfs.containsResource(persona)){
					// si lo acabamos de crear, ponerle name
					persona.addProperty(type, rdfs.getResource(Modelo.prefix + "#Persona"));
					persona.addProperty(name, autor);
				}
				// Propiedad creator
				documento.addProperty(creator, persona);
			}
		}
		tesauro(documento, d_title, d_description);
	}

	private static void tesauro(Resource documento, String d_title, String d_description) {
		// TODO stub SUCH WOW AMAZING LOL NO DEJAR ESTO
		Property keyword = rdfs.getProperty(Modelo.prefix + "#keyword");
		
		Resource energiaRenovable = skos.getResource(Modelo.prefix + "EnergiaRenovable");
		Resource energiaSolar = skos.getResource(Modelo.prefix + "EnergiaSolar");
		
		Resource musica = skos.getResource(Modelo.prefix + "Musica");
		
		Resource guerraIndependencia = skos.getResource(Modelo.prefix + "GuerraIndependencia");
		Resource historia = skos.getResource(Modelo.prefix + "Historia");
		
		Resource videojuegos = skos.getResource(Modelo.prefix + "Videojuegos");
		Resource personajes = skos.getResource(Modelo.prefix + "Personajes");
		
		Resource edadMediaGotico = skos.getResource(Modelo.prefix + "EdadMediaGotico");
		Resource edadMedia = skos.getResource(Modelo.prefix + "EdadMedia");
		Resource gotico = skos.getResource(Modelo.prefix + "Gotico");
		Resource arquitectura = skos.getResource(Modelo.prefix + "Arquitectura");
		
		// Para agregar el concepto de energia renovable a un documento
		documento.addProperty(keyword, energiaRenovable);
	}

	/**
	 * Obtiene los creadores del documento
	 * 
	 * @param doc:
	 *            documento del que extraer informacion
	 */
	private static String[] parseCreator(Document doc) {
		NodeList creators = doc.getElementsByTagName("dc:creator");
		String[] owners = null;

		if (creators != null && creators.getLength() > 0) {
			owners = new String[creators.getLength()];
			for (int i = 0; i < creators.getLength(); i++) {
				owners[i] = creators.item(i).getTextContent();
			}
		}
		return owners;
	}

	/**
	 * Obtiene el anio de creacion del documento
	 * 
	 * @param doc:
	 *            documento del que extraer informacion
	 */
	private static int parseDate(Document doc) {
		NodeList dates = doc.getElementsByTagName("dc:date");
		int date = -1;

		if (dates != null && dates.getLength() > 0) {
			date = Integer.parseInt(dates.item(0).getTextContent().trim());
		}

		return date;
	}

	/**
	 * Obtiene el titulo del documento
	 * 
	 * @param doc:
	 *            documento del que extraer informacion
	 */
	private static String parseTitle(Document doc) {
		NodeList titles = doc.getElementsByTagName("doc:title");
		String title = null;

		if (titles != null && titles.getLength() > 0) {
			title = titles.item(0).getTextContent();
		}

		return title;
	}

	/**
	 * Obtiene el sumario o resumen del documento
	 * 
	 * @param doc:
	 *            documento del que extraer informacion
	 */
	private static String parseSummary(Document doc) {
		NodeList summaries = doc.getElementsByTagName("dc:description");
		String summary = "";

		if (summaries != null && summaries.getLength() > 0) {
			summary = summaries.item(0).getTextContent();
		}

		return summary;
	}
}
