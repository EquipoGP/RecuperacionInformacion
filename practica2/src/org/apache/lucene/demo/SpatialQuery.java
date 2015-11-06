package org.apache.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SpatialQuery {

	/**
	 * Metodo main. Lanza el query parser con las distintas consultas
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String[] bbQueries = { "spatial:-180.0,180.0,-90.0,90.0", "spatial:-15.6,6.6,35.0,44.7",
				"spatial:-15.6,6.6,50.0,72.0", "spatial:-135.0,-110.0,50.0,72.0" };

		for (String s : bbQueries) {
			System.out.println(s);
			bbQueryParser(s);
		}
	}

	/**
	 * Parsea la consulta @param bbQuery y muestra por pantalla el resultado
	 */
	private static void bbQueryParser(String bbQuery) {
		try {
			BooleanQuery query = new BooleanQuery();

			String index = "index";
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
			IndexSearcher searcher = new IndexSearcher(reader);

			String[] terms = bbQuery.split(":");

			if (terms[0].equals("spatial")) {
				String[] coords = terms[1].split(",");
				double w = Double.parseDouble(coords[0]);
				double e = Double.parseDouble(coords[1]);
				double s = Double.parseDouble(coords[2]);
				double n = Double.parseDouble(coords[3]);

				/* Xmin <= EAST, Xmax >= WEST, Ymin <= NORTH, Ymax >= SOUTH */
				NumericRangeQuery<Double> westRangeQuery = NumericRangeQuery.newDoubleRange("west", null, e, true,
						true);
				NumericRangeQuery<Double> eastRangeQuery = NumericRangeQuery.newDoubleRange("east", w, null, true,
						true);
				NumericRangeQuery<Double> southRangeQuery = NumericRangeQuery.newDoubleRange("south", null, n, true,
						true);
				NumericRangeQuery<Double> northRangeQuery = NumericRangeQuery.newDoubleRange("north", s, null, true,
						true);

				query.add(westRangeQuery, BooleanClause.Occur.MUST);
				query.add(eastRangeQuery, BooleanClause.Occur.MUST);
				query.add(southRangeQuery, BooleanClause.Occur.MUST);
				query.add(northRangeQuery, BooleanClause.Occur.MUST);
			}

			/* Realiza la busqueda */
			int hitsPerPage = 30;
			TopDocs results = searcher.search(query, hitsPerPage);
			ScoreDoc[] hits = results.scoreDocs;

			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");

			int start = 0;
			int end = Math.min(numTotalHits, hitsPerPage);

			for (int i = start; i < end; i++) {

				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");

				if (path != null) {
					String[] folders = path.split(Pattern.quote(File.separator));
					String name = folders[folders.length - 1];
					String[] splittedName = name.split("-");
					String number = splittedName[0];
					System.out.println((i + 1) + ". " + number);
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
