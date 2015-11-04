package sistemaTradicional;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
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
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new TradicionalAnalyzer();

			int max_docs = reader.maxDoc();

			Map<String, String> consultas = parse(infoNeedsFile);
			QueryParser qp = new QueryParser("sumario", analyzer);

			for (Map.Entry<String, String> consulta : consultas.entrySet()) {
				BooleanQuery q = new BooleanQuery();
				Query query = qp.parse(consulta.getValue());
				
//				String query = parse(consulta.getValue(), analyzer);
//				q.add(new PrefixQuery(new Term("sumario", query)), BooleanClause.Occur.SHOULD);

				TopDocs results = searcher.search(query, max_docs);
				ScoreDoc[] scores = results.scoreDocs;

				for (int i = 0; i < scores.length; i++) {
					org.apache.lucene.document.Document doc = searcher.doc(scores[i].doc);
					String path = doc.get("path");

					if (path != null) {
						String[] folders = path.split(Pattern.quote(File.separator));
						path = folders[folders.length - 1];
					}

					System.out.println(consulta.getKey() + "\t" + path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public static String parse(String consulta, Analyzer a) {
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

}
