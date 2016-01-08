package test;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class AccesoRDF {

	public static void main(String[] args) {

		// cargamos el fichero deseado
		Model model = FileManager.get().loadModel("card.rdf");

		// obtenemos todos los statements del modelo con uri = URI
		String URI = "http://www.w3.org/People/Berners-Lee/card#i";
		Resource tim = model.createResource(URI);
		
		StmtIterator it = model.listStatements(tim, null, (RDFNode) null);

		// obtenemos las propiedades de la URI
		Set<Property> properties = new HashSet<Property>();
		
		while (it.hasNext()) {
			Statement st = it.next();
			Property p = st.getPredicate();
			properties.add(p);
		}
		it.close();
		
		Set<Resource> recursos = new HashSet<Resource>();
		
		// obtenemos para cada propiedad las tripletas que la contienen
		for (Property p : properties) {
			it = model.listStatements(null, p, (RDFNode) null);
			
			/* Obtiene los recursos diferentes que cumplen la condicion */
			while (it.hasNext()) {
				Statement st = it.next();
				Resource r = st.getSubject();
				boolean esta = false;
				
				/* Comprueba si el nuevo recurso ya estaba en la coleccion */
				for (Resource rr : recursos) {
					if (rr.equals(r)) {
						esta = true;
						break;
					}
				}
				if (!esta && r != null) {
					recursos.add(r);
				}
			}
			it.close();
		}
		
		/* Muestra los recursos distintos del modelo que cumplen la condicion */
		for (Resource r : recursos) {
			System.out.println(r);
		}
		
	}
	
	/**
	 * accede de diferentes maneras a las propiedades de un modelo rdf
	 */
	public static void main2(String args[]) {

		// cargamos el fichero deseado
		Model model = FileManager.get().loadModel("card.rdf");

		// obtenemos todos los statements del modelo
		StmtIterator it = model.listStatements();

		// mostramos todas las tripletas cuyo objeto es un literal
		while (it.hasNext()) {
			Statement st = it.next();

			if (st.getObject().isLiteral()) {
				System.out.println(st.getSubject().getURI() + " - "
						+ st.getPredicate().getURI() + " - "
						+ st.getLiteral().toString());
			}
		}

		System.out.println("----------------------------------------");

		// mostramos los valores de todas las propiedades de un recurso
		// determinado
		Resource res = model
				.getResource("http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf");
		it = res.listProperties();
		while (it.hasNext()) {
			Statement st = it.next();

			if (st.getObject().isLiteral()) {
				System.out.println(st.getSubject().getURI() + " - "
						+ st.getPredicate().getURI() + " - "
						+ st.getLiteral().toString());
			} else {
				System.out.println(st.getSubject().getURI() + " - "
						+ st.getPredicate().getURI() + " - "
						+ st.getResource().getURI());
			}
		}

		System.out.println("----------------------------------------");

		// mostramos todos los recursos que contienen una propiedad determinada
		Property prop = model
				.getProperty("http://purl.org/dc/elements/1.1/title");
		ResIterator ri = model.listSubjectsWithProperty(prop);
		while (ri.hasNext()) {
			Resource r = ri.next();
			System.out.println(r.getURI());
		}

		System.out.println("----------------------------------------");

		// mostramos todos los recursos que contienen una propiedad determinada
		// forma alternativa que usa un filtro sobre los statements a recuperar
		it = model.listStatements(null, prop, (RDFNode) null);
		while (it.hasNext()) {
			Statement st = it.next();

			if (st.getObject().isLiteral()) {
				System.out.println(st.getSubject().getURI() + " - "
						+ st.getPredicate().getURI() + " - "
						+ st.getLiteral().toString());
			}
		}
	}

}
