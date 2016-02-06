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

	/**
	 * Metodo para la indexacion de documentos
	 * 
	 * @param rdfPath
	 *            ruta del fichero RDF de datos
	 * @param skosPath
	 *            ruta del fichero RDF del modelo SKOS
	 * @param docsPath
	 *            ruta de los ficheros a indexar
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void index(String rdfPath, String skosPath, String docsPath)
			throws ParserConfigurationException, FileNotFoundException,
			SAXException, IOException {
		Tesauro.init(); // inicializar tesauro
		final File fileDir = new File(docsPath); // directorio del corpus

		/* gestion de errores */
		if (!fileDir.exists() || !fileDir.canRead()) {
			System.err.println("ERROR: " + fileDir.getAbsolutePath()
					+ " no existe o no tiene derechos de lectura");
			return;
		}
		/* Creacion del directorio */
		String[] files = fileDir.list();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		/* creacion del modelo RDFS y SKOS */
		rdfs = Modelo.crearModelo();
		skos = Modelo.crearSkosModel();

		/* Indexar ficheros */
		for (String file : files) {
			File f = new File(fileDir, file);
			introducir(db, f);
		}

		/* escribir los modelos */
		rdfs.write(new FileOutputStream(new File(rdfPath)));
		skos.write(new FileOutputStream(new File(skosPath)));
	}

	/**
	 * Introduce un documento en el modelo RDF
	 * 
	 * @param db
	 *            necesario para crear el documento XML y parsearlo
	 * @param f
	 *            fichero a introducir en la coleccion semantica
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void introducir(DocumentBuilder db, File f)
			throws FileNotFoundException, SAXException, IOException {
		// creacion del XML
		Document d = db.parse(new FileInputStream(f));
		d.getDocumentElement().normalize();

		/* obtencion de propiedades */
		Property type = rdfs
				.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		Property name = rdfs.getProperty(Modelo.prefix + "#name");
		Property identifier = rdfs.getProperty(Modelo.prefix + "#identifier");
		Property creator = rdfs.getProperty(Modelo.prefix + "#creator");
		Property date = rdfs.getProperty(Modelo.prefix + "#date");

		/* obtencion de datos */
		String[] d_creators = parseCreator(d);
		String d_title = parseTitle(d);
		int d_date = parseDate(d);
		String d_description = parseSummary(d);
		String d_identifier = f.getName();

		/* Documento */
		Resource documento = rdfs.createResource(Modelo.prefix + d_identifier);
		documento.addProperty(type,
				rdfs.getResource(Modelo.prefix + "#Documento"));
		// Propiedad date
		documento.addProperty(date, rdfs.createTypedLiteral(d_date));
		// Propiedad identifier
		documento
				.addProperty(identifier, rdfs.createTypedLiteral(d_identifier));

		/* Autores */
		if (d_creators != null && d_creators.length > 0) {
			for (String autor : d_creators) {
				// obtener persona
				Resource persona = rdfs.getResource(Modelo.prefix + autor);
				if (!rdfs.containsResource(persona)) {
					// si lo acabamos de crear, ponerle name
					persona.addProperty(type,
							rdfs.getResource(Modelo.prefix + "#Persona"));
					persona.addProperty(name, autor);
				}
				// Propiedad creator
				documento.addProperty(creator, persona);
			}
		}
		// conceptos del tesauro
		tesauro(documento, d_title, d_description);
	}

	/**
	 * Enlaza el documento utilizando el titulo y la descripcion con los
	 * conceptos pertinentes del tesauro generado a partir del modelo SKOS
	 * 
	 * @param documento
	 *            documento a enlazar
	 * @param d_title
	 *            titulo del documento
	 * @param d_description
	 *            descripcion del documento
	 */
	private static void tesauro(Resource documento, String d_title,
			String d_description) {
		// Propiedad de asociacion
		Property keyword = rdfs.getProperty(Modelo.prefix + "#keyword");

		/* Conceptos SKOS */
		Resource energiaRenovable = skos.getResource(Modelo.prefix
				+ "EnergiaRenovable");

		Resource musica = skos.getResource(Modelo.prefix + "Musica");
		Resource sonido = skos.getResource(Modelo.prefix + "Sonido");

		Resource guerraIndependencia = skos.getResource(Modelo.prefix
				+ "GuerraIndependencia");

		Resource videojuegos = skos.getResource(Modelo.prefix + "Videojuegos");
		Resource diseño = skos.getResource(Modelo.prefix + "Diseño");
		Resource desarrollo = skos.getResource(Modelo.prefix + "Desarrollo");
		Resource programacion = skos
				.getResource(Modelo.prefix + "Programacion");

		Resource arquitectura = skos
				.getResource(Modelo.prefix + "Arquitectura");
		Resource conservacion = skos
				.getResource(Modelo.prefix + "Conservacion");
		Resource epoca = skos.getResource(Modelo.prefix + "Epoca");

		// comprobar en que conceptos va el documento
		if (Tesauro.energiaRenovable(d_description, d_title)) {
			documento.addProperty(keyword, energiaRenovable);
		}
		if (Tesauro.musica(d_description, d_title)) {
			documento.addProperty(keyword, musica);
		}
		if (Tesauro.sonido(d_description, d_title)) {
			documento.addProperty(keyword, sonido);
		}
		if (Tesauro.guerraIndependencia(d_description, d_title)) {
			documento.addProperty(keyword, guerraIndependencia);
		}
		if (Tesauro.videojuego(d_description, d_title)) {
			documento.addProperty(keyword, videojuegos);
		}
		if (Tesauro.disenio(d_description, d_title)) {
			documento.addProperty(keyword, diseño);
		}
		if (Tesauro.desarrollo(d_description, d_title)) {
			documento.addProperty(keyword, desarrollo);
		}
		if (Tesauro.programacion(d_description, d_title)) {
			documento.addProperty(keyword, programacion);
		}
		if (Tesauro.arquitectura(d_description, d_title)) {
			documento.addProperty(keyword, arquitectura);
		}
		if (Tesauro.conservacion(d_description, d_title)) {
			documento.addProperty(keyword, conservacion);
		}
		if (Tesauro.epoca(d_description, d_title)) {
			documento.addProperty(keyword, epoca);
		}
	}

	/**
	 * Obtiene los autores/creadores del documento
	 * 
	 * @param doc
	 *            documento del que obtener informacion
	 * @return los creadores del documento (dc:creator)
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
	 * Obtiene la fecha (anio) del documento
	 * 
	 * @param doc
	 *            documento del que obtener informacion
	 * @return el anio de creacion del documento (dc:date)
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
	 * @param doc
	 *            documento del que obtener informacion
	 * @return el titulo del documento (dc:title)
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
	 * Obtiene la descripcion del documento
	 * 
	 * @param doc
	 *            documento del que obtener informacion
	 * @return la descripcion del documento (dc:description)
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
