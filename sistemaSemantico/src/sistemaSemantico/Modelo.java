package sistemaSemantico;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.*;

public class Modelo {

	public static final String prefix = "http://recinfo.org/";
	public static final String skos = "http://www.w3.org/2004/02/skos/core#";

	public static Model crearModelo() {
		Model model = ModelFactory.createDefaultModel();

		/* Clases */
		Resource documento = model.createResource(prefix + "#Documento");
		Resource persona = model.createResource(prefix + "#Persona");

		/* +++++++++ Propiedades +++++++++ */

		/* Documento -> title -> Literal */
//		Property title = model.createProperty(prefix + "#title");
//		title.addProperty(RDFS.domain, documento);
//		title.addProperty(RDFS.range, RDFS.Literal);

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
		keyword.addProperty(RDFS.range, skos + "#Concept");

		return model;
	}

	public static Model crearSkosModel() {
		Model model = ModelFactory.createDefaultModel();

		Resource concept = model.createResource(prefix + "#Concept");
		Property prefLabel = model.createProperty(skos + "#prefLabel");
		Property narrower = model.createProperty(skos + "#narrower");
		Property broader = model.createProperty(skos + "#broader");

		/* energias renovables */
		Resource energiaRenovable = model.createResource(prefix + "EnergiaRenovable");
		energiaRenovable.addProperty(RDF.type, concept);
		energiaRenovable.addProperty(prefLabel, "EnergiaRenovable");

		Resource energiaSolar = model.createResource(prefix + "EnergiaSolar");
		energiaSolar.addProperty(RDF.type, concept);
		energiaSolar.addProperty(prefLabel, "EnergiaSolar");

		energiaRenovable.addProperty(narrower, energiaSolar);

		/* musica y sonido */
		Resource musica = model.createResource(prefix + "Musica");
		musica.addProperty(RDF.type, concept);
		musica.addProperty(prefLabel, "Musica");

		/* Guerra de la Independencia */
		Resource guerra = model.createResource(prefix + "GuerraIndependencia");
		guerra.addProperty(RDF.type, concept);
		guerra.addProperty(prefLabel, "GuerraIndependencia");

		Resource historia = model.createResource(prefix + "Historia");
		historia.addProperty(RDF.type, concept);
		historia.addProperty(prefLabel, "Historia");

		guerra.addProperty(broader, historia);

		/* videojuegos y personajes */
		Resource videojuegos = model.createResource(prefix + "Videojuegos");
		videojuegos.addProperty(RDF.type, concept);
		videojuegos.addProperty(prefLabel, "Videojuegos");

		Resource personajes = model.createResource(prefix + "Personajes");
		personajes.addProperty(RDF.type, concept);
		personajes.addProperty(prefLabel, "Personajes");

		/* Edad Media y Gotico */
		Resource edadMediaGotico = model.createResource(prefix + "EdadMediaGotico");
		edadMediaGotico.addProperty(RDF.type, concept);
		edadMediaGotico.addProperty(prefLabel, "EdadMediaGotico");

		Resource edadMedia = model.createResource(prefix + "EdadMedia");
		edadMedia.addProperty(RDF.type, concept);
		edadMedia.addProperty(prefLabel, "EdadMedia");

		Resource gotico = model.createResource(prefix + "Gotico");
		gotico.addProperty(RDF.type, concept);
		gotico.addProperty(prefLabel, "Gotico");

		Resource arquitectura = model.createResource(prefix + "Arquitectura");
		arquitectura.addProperty(RDF.type, concept);
		arquitectura.addProperty(prefLabel, "Arquitectura");

		edadMediaGotico.addProperty(broader, arquitectura);
		edadMediaGotico.addProperty(narrower, gotico);
		edadMediaGotico.addProperty(narrower, edadMedia);

		return model;
	}

}
