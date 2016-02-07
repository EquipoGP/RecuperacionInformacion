/*
 * Fichero: Modelo.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.*;

public class Modelo {

	/**
	 * Clase para la creacion de los modelos RDFS y SKOS
	 */

	/* atributos publicos */
	public static final String prefix = "http://recinfo.org/";
	public static final String skos = "http://www.w3.org/2004/02/skos/core#";

	/**
	 * Metodo para la creacion del modelo RDFS sobre el que se basaran los datos
	 * RDF
	 * 
	 * @return modelo
	 */
	public static Model crearModelo() {
		Model model = ModelFactory.createDefaultModel();

		/* Clases */
		Resource documento = model.createResource(prefix + "#Documento");
		Resource persona = model.createResource(prefix + "#Persona");

		/* +++++++++ Propiedades +++++++++ */

		/* Documento -> creator -> Persona */
		Property creator = model.createProperty(prefix + "#creator");
		creator.addProperty(RDFS.domain, documento);
		creator.addProperty(RDFS.range, persona);

		/* Persona -> name -> Literal */
		Property name = model.createProperty(prefix + "#name");
		name.addProperty(RDFS.domain, persona);
		name.addProperty(RDFS.range, RDFS.Literal);

		/* Documento -> identifier -> Literal */
		Property identifier = model.createProperty(prefix + "#identifier");
		identifier.addProperty(RDFS.domain, documento);
		identifier.addProperty(RDFS.range, RDFS.Literal);

		/* Documento -> date -> integer */
		Property date = model.createProperty(prefix + "#date");
		date.addProperty(RDFS.domain, documento);
		date.addProperty(RDFS.range, XSD.xint);

		/* Documento -> keyword -> SKOS:concept */
		Property keyword = model.createProperty(prefix + "#keyword");
		keyword.addProperty(RDFS.domain, documento);
		keyword.addProperty(RDFS.range, skos + "Concept");

		return model;
	}

	/**
	 * Metodo para la creacion del modelo SKOS
	 * 
	 * @return modelo
	 */
	public static Model crearSkosModel() {
		Model model = ModelFactory.createDefaultModel();

		Resource concept = model.createResource(skos + "Concept");
		Property prefLabel = model.createProperty(skos + "prefLabel");
		Property narrower = model.createProperty(skos + "narrower");
		Property broader = model.createProperty(skos + "broader");

		/* energias renovables */
		Resource energiaRenovable = model.createResource(prefix
				+ "EnergiaRenovable");
		energiaRenovable.addProperty(RDF.type, concept);
		energiaRenovable.addProperty(prefLabel, "EnergiaRenovable");

		/* musica y sonido */
		Resource musica = model.createResource(prefix + "Musica");
		musica.addProperty(RDF.type, concept);
		musica.addProperty(prefLabel, "Musica");

		Resource sonido = model.createResource(prefix + "Sonido");
		sonido.addProperty(RDF.type, concept);
		sonido.addProperty(prefLabel, "Sonido");
		
		musica.addProperty(broader, sonido);

		/* Guerra de la Independencia */
		Resource guerra = model.createResource(prefix + "GuerraIndependencia");
		guerra.addProperty(RDF.type, concept);
		guerra.addProperty(prefLabel, "GuerraIndependencia");

		/* videojuegos y personajes */
		Resource videojuegos = model.createResource(prefix + "Videojuegos");
		videojuegos.addProperty(RDF.type, concept);
		videojuegos.addProperty(prefLabel, "Videojuegos");

		/* Edad Media y Gotico */
		Resource edadMediaGotica = model.createResource(prefix + "EdadMediaGotica");
		edadMediaGotica.addProperty(RDF.type, concept);
		edadMediaGotica.addProperty(prefLabel, "EdadMediaGotica");
		
		Resource arquitectura = model.createResource(prefix + "Arquitectura");
		arquitectura.addProperty(RDF.type, concept);
		arquitectura.addProperty(prefLabel, "Arquitectura");

		Resource epoca = model.createResource(prefix + "Sociedad");
		epoca.addProperty(RDF.type, concept);
		epoca.addProperty(prefLabel, "Sociedad");
		
		edadMediaGotica.addProperty(narrower, arquitectura);
		edadMediaGotica.addProperty(narrower, epoca);

		return model;
	}

}
