package sistemaTradicional;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SearchDocs {
	/**
	 * Clase auxiliar para la busqueda de contenidos en el indice
	 * @version 1.0
	 */

	/**
	 * Busca en el indice que hay en <indexPath> las consultas que hay en las
	 * necesidades de informacion de <infoNeedsFile> y almacena el resultado
	 * segun el estandar en <resultsFile>
	 * 
	 * @param indexPath: ruta del indice
	 * @param infoNeedsFile: ruta del fichero de necesidades de informacion
	 * @param resultsFile: ruta del fichero donde volcar la salida
	 */
	public static void search(String indexPath, String infoNeedsFile, String resultsFile) {
		try {
			/* archivo de resultados */
			File f = new File(resultsFile);
			PrintWriter out = new PrintWriter(f);
			
			/* preparacion para la busqueda */
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new TradicionalAnalyzer();

			/* mostrar todos los resultados */
			int max_docs = reader.maxDoc();
			/* conseguir consultas */
			Map<String, String> consultas = parse(infoNeedsFile);

			for (Map.Entry<String, String> consulta : consultas.entrySet()) {
				BooleanQuery q = new BooleanQuery();
				
				/* parsear consulta en mayor profundidad */
//				Map<String, String> terminos = getFields(consulta.getValue());
//				for(Map.Entry<String, String> termino : terminos.entrySet()){
//					
//					/* buscar en creador */
//					if(termino.getKey().equals("creador")){
//						String creador = parse(termino.getValue(), analyzer);
//						String[]split = creador.split(" ");
//						
//						for(String s : split){
//							q.add(new TermQuery(new Term("creador", s)), 
//									BooleanClause.Occur.SHOULD);
//						}
//					}
//					/* buscar por fecha */
//					else if(termino.getKey().equals("anio")){
//						String[] split = termino.getValue().split(" ");
//						/* entre dos fechas */
//						if(split.length == 2){
//							int no1 = Integer.parseInt(split[0]);
//							int no2 = Integer.parseInt(split[1]);
//							
//							int min = Math.min(no1, no2);
//							int max = Math.max(no1, no2);
//							
//							q.add(NumericRangeQuery.newIntRange("fecha", min, max, true, true), 
//									BooleanClause.Occur.SHOULD);
//						}
//						/* en una fecha concreta */
//						else if(split.length == 1){
//							int no = Integer.parseInt(split[0]);
//							q.add(NumericRangeQuery.newIntRange("fecha", no, no, true, true), 
//							BooleanClause.Occur.SHOULD);
//						}
//					}
//					else if(termino.getKey().equals("principal")){
//						String query = parse(termino.getValue(), analyzer);
//						String[] terms = query.split(" ");
//						for(String term: terms){
//							/* buscar la consulta en el sumario y titulo */
//							q.add(new TermQuery(new Term("sumario", term)), BooleanClause.Occur.SHOULD);
//							q.add(new TermQuery(new Term("titulo", term)), BooleanClause.Occur.SHOULD);
//						}
//					}
//				}
				
				
				
				
				Map<String, String> terms = get(parse(consulta.getValue(), analyzer));
				for(Map.Entry<String, String> term: terms.entrySet()){
					if(term.getKey().equals("principal")){
						BooleanQuery mainbq = new BooleanQuery();
						System.out.println("Principal: " + term.getValue());
						
						String[] terminos = term.getValue().split(" ");
						for(String t: terminos){
							mainbq.add(new TermQuery(new Term("sumario", t)), BooleanClause.Occur.SHOULD);
							mainbq.add(new TermQuery(new Term("titulo", t)), BooleanClause.Occur.SHOULD);
						}
						q.add(mainbq, BooleanClause.Occur.MUST);
					}
					else if(term.getKey().equals("anio")){
						System.out.println("Anio: " + term.getValue());
						
						String[] terminos = term.getValue().split(" ");
						if(terminos.length == 2){
							q.add(NumericRangeQuery.newIntRange("fecha", Integer.parseInt(terminos[0]), 
									Integer.parseInt(terminos[1]), true, true), 
									BooleanClause.Occur.SHOULD);
						}
						else if(terminos.length == 1){
							q.add(NumericRangeQuery.newIntRange("fecha", null, 
									Integer.parseInt(terminos[0]), true, true), 
									BooleanClause.Occur.SHOULD);
						}
					}
					else if(term.getKey().equals("creator")){
						BooleanQuery creatorbq = new BooleanQuery();
						
						System.out.println("Creator: " + term.getValue());
						
						String[] terminos = term.getValue().split(" ");
						for(String t: terminos){
							q.add(new TermQuery(new Term("creador", t)), 
									BooleanClause.Occur.MUST);
						}
						q.add(creatorbq, BooleanClause.Occur.SHOULD);
					}
				}
				
				
				
				

				/* realizar la busqueda */
				TopDocs results = searcher.search(q, max_docs);
				ScoreDoc[] scores = results.scoreDocs;
				
				System.out.println(consulta.getKey() + " score: " + scores.length);
				
				
				/* parsear el resultado de la busqueda */
				for (int i = 0; i < scores.length; i++) {
					org.apache.lucene.document.Document doc = searcher.doc(scores[i].doc);
					String path = doc.get("path");

					if (path != null) {
						String[] folders = path.split(Pattern.quote(File.separator));
						path = folders[folders.length - 1];
					}
					out.println(consulta.getKey() + "\t" + path);
				}
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private static Map<String, String> get(String query){
		Map<String, String> terms = new HashMap<String, String>();
		
		if(query.contains("autor") || query.contains("director")){
			// autor o director
			String[] split = query.split("autor|director");
			
			terms.put("principal", split[0].replaceAll("autor|director", "").trim());			
			terms.put("creator", split[split.length - 1].replaceAll("autor|director", "").trim());
		}
		else if(query.contains("ultim")){
			// ultimos x anios
			String[] split = query.split("ultim");
			terms.put("principal", split[0].replaceAll("ultim", "").trim());
			
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int rango = 0;
			Scanner s = new Scanner(split[1]);
			while(s.hasNext()){
				if(s.hasNextInt()){
					rango = s.nextInt();
					break;
				}
				s.next();
			}
			s.close();
			if(year-rango >= 2008){
				terms.put("anio", (year - rango) + " " + year);
			}
		}
		else if(query.matches(".*\\d+.*")){
			// anios
			String main = "";
			
			int no1 = 0;
			int no2 = 0;
			
			Scanner s = new Scanner(query);
			while(s.hasNext()){
				if(s.hasNextInt()){
					if(no1 == 0){
						no1 = s.nextInt();
					}
					else{
						no2 = s.nextInt();
					}
				}
				else{
					main = main + " " + s.next();
				}
			}
			s.close();
			
			int min = Math.min(no1, no2);
			int max = Math.max(no1, no2);
			
			if(max < 2008){
				;
			}
			else{
				terms.put("anio", min + " " + max);
			}
			
			terms.put("principal", main);
		}
		else{
			terms.put("principal", query);
		}
		
		return terms;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Parsea el fichero de necesidades de informacion y devuelve las parejas
	 * identificador-texto para cada necesidad de informacion.
	 * 
	 * @param infoNeedsFile: ruta del fichero de necesidades de informacion
	 */
	private static Map<String, String> parse(String infoNeedsFile) {
		Map<String, String> queries = new HashMap<String, String>();
		try {
			/* file */
			File f = new File(infoNeedsFile);
			FileInputStream fis = new FileInputStream(f);

			/* documento */
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fis);
			doc.getDocumentElement().normalize();

			/* necesidades de informacion */
			NodeList infoNeeds = doc.getElementsByTagName("informationNeed");
			if (infoNeeds != null && infoNeeds.getLength() > 0) {
				/* recorrer las necesidades de informacion */
				for (int i = 0; i < infoNeeds.getLength(); i++) {
					Node n = infoNeeds.item(i);
					String id = null;
					String text = null;

					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element infoNeed = (Element) n;

						/* identificador */
						NodeList n_id = infoNeed.getElementsByTagName("identifier");
						if (n_id != null && n_id.getLength() > 0) {
							id = n_id.item(0).getTextContent();
						}

						/* texto asociado a la consulta */
						NodeList n_text = infoNeed.getElementsByTagName("text");
						if (n_text != null && n_text.getLength() > 0) {
							text = n_text.item(0).getTextContent();
						}
					}
					queries.put(id, text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queries;
	}

	/**
	 * Obtiene la consulta de la necesidad de informacion, parseandola
	 * 
	 * @param consulta: necesidad de informacion
	 * @param a: analizador que usar
	 */
	private static String parse(String consulta, Analyzer a) {
		String query = "";
		try {
			TokenStream ts = a.tokenStream(null, consulta);
			ts.reset();
			while (ts.incrementToken()) {
				query = query + ts.getAttribute(CharTermAttribute.class).toString() + " ";
			}
			ts.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	/**
	 * Devuelve una estructura con los campos en los que realizar la consulta 
	 * y que consulta realizar 
	 * @param consulta: consulta sin parsear
	 */
	private static Map<String, String> getFields(String consulta){
		Map<String, String> terminos = new HashMap<String, String>();
		
		terminos.putAll(getFieldsCuyo(consulta, terminos));
		terminos.putAll(getFieldsSobre(consulta, terminos));
		terminos.putAll(getFieldsAnio(consulta, terminos));
		
		if(!terminos.containsKey("principal")){
			terminos.put("principal", consulta);
		}
		
		return terminos;
	}
	
	/**
	 * 
	 * @param consulta
	 * @param terms
	 * @return
	 */
	private static Map<String, String> getFieldsCuyo(String consulta, Map<String, String> terms){
		String[] split = consulta.split("cuy.");
		
		if(split.length > 1){
			terms.put("principal", split[0]);
		}
		
		for (int i = 1; i < split.length; i++) {
			String s = split[i].trim().toLowerCase();
			
			// cuyo autor/director/creador...
			if(s.contains("autor") || s.contains("director") 
					|| s.contains("creador")){
				String content = s.replace("autor", "")
						.replace("director", "").replace("creador", "");
				terms.put("creador", content);
			}
			// cuyo.... [anio] ... [anio] ...
			else if(s.contains(".*\\d+.*")){
				Scanner sc = new Scanner(s);
				while(sc.hasNext()){
					if(sc.hasNextInt()){
						String content = terms.get("anio");
						if(content != null){
							content = content + " " + sc.nextInt();
						}
						else{
							content = sc.nextInt() + "";
						}
						terms.put("anio", content);
					}
				}
				sc.close();
			}
			else{
				terms.put("principal", consulta);
			}
		}
		
		return terms;
	}

	/**
	 * 
	 * @param consulta
	 * @param terms
	 * @return
	 */
	private static Map<String, String> getFieldsSobre(String consulta, Map<String, String> terms){
		String[] split = consulta.split("sobre");
		
		if(split.length > 1){
			terms.put("principal", split[1]);
		}
		
		return terms;
	}
	
	/**
	 * 
	 * @param consulta
	 * @param terms
	 * @return
	 */
	private static Map<String, String> getFieldsAnio(String consulta, Map<String, String> terms){
		Scanner s = new Scanner(consulta);
		while(s.hasNext()){
			if(s.hasNextInt()){
				if(terms.get("anio") != null){
					terms.put("anio", terms.get("anio") + " " + s.nextInt());
				}
				else{
					terms.put("anio", "" + s.nextInt());
				}
			}
			else{
				s.next();
			}
		}
		s.close();
		return terms;
	}
}
