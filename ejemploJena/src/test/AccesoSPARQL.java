package test;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class AccesoSPARQL {
	
	public static void main(String args[]) {
		
		// cargamos el fichero deseado
		Model model = FileManager.get().loadModel("card.rdf");
		
		System.out.println();
		System.out.println("=================================================");
		System.out.println("CONSULTA 1: Literales que contienen \"Berners-Lee\"");
		System.out.println("=================================================");
		System.out.println();
		
		consulta1(model);
		
		System.out.println();
		System.out.println("=============================================================");
		System.out.println("CONSULTA 2: Titulo de los documentos con autor: \"Berners-Lee\"");
		System.out.println("=============================================================");
		System.out.println();
		
		consulta2(model);
	}

	private static void consulta1(Model model) {
		//definimos la consulta (tipo query)
		String queryString = "Select ?x ?y ?z"
						+ " WHERE {?x ?y ?z"
						+ " FILTER(isLiteral(?z))"
						+ " FILTER regex(str(?z), \"Berners-Lee\")"
						+ "}" ;
		
		//ejecutamos la consulta y obtenemos los resultados
		  Query query = QueryFactory.create(queryString) ;
		  QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode z = soln.get("z") ;
		      System.out.println(z.toString());
		    }
		  } finally { qexec.close() ; }
	}
	
	private static void consulta2(Model model) {
		//definimos la consulta (tipo query)
		String queryString = "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
					   	+ "PREFIX con: <http://www.w3.org/2000/10/swap/pim/contact#>"
					   	+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " SELECT ?title"
						+ " WHERE {"
							+ "{ ?x dc:title ?title }"
							+ " UNION { con:Male rdf:about ?autor }"
							+ " UNION { ?x rdf:resource ?autor }"
						+ "}" ;
		
		//ejecutamos la consulta y obtenemos los resultados
		  Query query = QueryFactory.create(queryString) ;
		  QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode title = soln.get("title");
		      System.out.println(title);
		    }
		  } finally { qexec.close() ; }
	}

	/**
	 * ejecución de consultas sparql
	 */
	public static void main2(String args[]) {
		
		// cargamos el fichero deseado
		Model model = FileManager.get().loadModel("card.rdf");

		//definimos la consulta (tipo query)
		String queryString = "Select ?x ?y ?z WHERE  {?x ?y ?z }" ;
		
		//ejecutamos la consulta y obtenemos los resultados
		  Query query = QueryFactory.create(queryString) ;
		  QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      Resource x = soln.getResource("x");
		      Resource y = soln.getResource("y");
		      RDFNode z = soln.get("z") ;  
		      if (z.isLiteral()) {
					System.out.println(x.getURI() + " - "
							+ y.getURI() + " - "
							+ z.toString());
				} else {
					System.out.println(x.getURI() + " - "
							+ y.getURI() + " - "
							+ z.asResource().getURI());
				}
		    }
		  } finally { qexec.close() ; }
		
		System.out.println("----------------------------------------");

		//definimos la consulta (tipo describe)
		queryString = "Describe <http://www.w3.org/People/Berners-Lee/card#i>" ;
		query = QueryFactory.create(queryString) ;
		qexec = QueryExecutionFactory.create(query, model) ;
		Model resultModel = qexec.execDescribe() ;
		qexec.close() ;
		resultModel.write(System.out);
		
		System.out.println("----------------------------------------");

		
		//definimos la consulta (tipo ask)
		queryString = "ask {<http://www.w3.org/People/Berners-Lee/card#i> ?x ?y}" ;
		query = QueryFactory.create(queryString) ;
		qexec = QueryExecutionFactory.create(query, model) ;
		System.out.println( qexec.execAsk()) ;
		qexec.close() ;
		
		System.out.println("----------------------------------------");
	
		//definimos la consulta (tipo cosntruct)
		queryString = "construct {?x <http://miuri/inverseSameAs> ?y} where {?y <http://www.w3.org/2002/07/owl#sameAs> ?x}" ;
		query = QueryFactory.create(queryString) ;
		qexec = QueryExecutionFactory.create(query, model) ;
		resultModel = qexec.execConstruct() ;
		qexec.close() ;
		resultModel.write(System.out);
		
	}
	
}
