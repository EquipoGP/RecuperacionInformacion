package org.apache.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
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

		String[] bbQueries2 = { "spatial:-180.0,180.0,-90.0,90.0 title:natura",
				"spatial:-180.0,180.0,-90.0,90.0 title:nacional" };

		for (String s : bbQueries2) {
			System.out.println(s);
			bbQueryParser(s);
		}

		String[] bbQueries3 = { "issued:[1980 TO 2010]", "created:[1980 TO 2010]",
				"issued:[1980 TO 2010] created:[1980 TO 2010]", "issued:[19890101 TO 19950101]", "issued:19940101" };

		for (String s : bbQueries3) {
			System.out.println(s);
			bbQueryParser(s);
		}
		
		String[] bbQueries4 = { "temporal:[1980 TO 2010]" };

		for (String s : bbQueries4) {
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
			
			bbQuery = bbQuery.replaceAll(" TO ", "/");

			String[] queries = bbQuery.split(" ");
			for (String q : queries) {

				String[] terms = q.split(":");

				if (terms[0].equals("spatial")) {
					String[] coords = terms[1].split(",");
					double w = Double.parseDouble(coords[0]);
					double e = Double.parseDouble(coords[1]);
					double s = Double.parseDouble(coords[2]);
					double n = Double.parseDouble(coords[3]);

					/*
					 * Xmin <= EAST, Xmax >= WEST, Ymin <= NORTH, Ymax >= SOUTH
					 */
					NumericRangeQuery<Double> westRangeQuery = NumericRangeQuery.newDoubleRange("west", null, e, true,
							true);
					NumericRangeQuery<Double> eastRangeQuery = NumericRangeQuery.newDoubleRange("east", w, null, true,
							true);
					NumericRangeQuery<Double> southRangeQuery = NumericRangeQuery.newDoubleRange("south", null, n, true,
							true);
					NumericRangeQuery<Double> northRangeQuery = NumericRangeQuery.newDoubleRange("north", s, null, true,
							true);

					BooleanQuery bbq = new BooleanQuery();
					bbq.add(westRangeQuery, BooleanClause.Occur.MUST);
					bbq.add(eastRangeQuery, BooleanClause.Occur.MUST);
					bbq.add(southRangeQuery, BooleanClause.Occur.MUST);
					bbq.add(northRangeQuery, BooleanClause.Occur.MUST);

					query.add(bbq, BooleanClause.Occur.SHOULD);
				} else if (terms[0].contains("issued") || terms[0].contains("created") 
						|| terms[0].contains("temporal")) {
					String date = terms[1].replaceAll("\\[", "").replaceAll("\\]", "").trim();
					
					String n1 = "";
					String n2 = "";

					String[] dates = date.split("\\/");

					// rango
					if (dates.length == 2) {
						String no1 = dates[0];
						String no2 = dates[1];

						if (no1.length() == 4) {
							no1 = no1 + "0101";
						}
						if (no2.length() == 4) {
							no2 = no2 + "0101";
						}

						n1 = no1;
						n2 = no2;
					}
					// no rango
					else {
						String no = dates[0];

						if (no.length() == 4) {
							no = no + "0101";
						}

						n1 = no;
						n2 = n1;
					}

					if(terms[0].contains("temporal")){
						BooleanQuery bqq = new BooleanQuery();
						
						TermRangeQuery beginquery = TermRangeQuery.newStringRange("begin", n1, null, true, true);
						TermRangeQuery endquery = TermRangeQuery.newStringRange("end", null, n2, true, true);
						
						bqq.add(beginquery, BooleanClause.Occur.MUST);
						bqq.add(endquery, BooleanClause.Occur.MUST);
						
						query.add(bqq, BooleanClause.Occur.SHOULD);
					}
					else{
						TermRangeQuery datequery = TermRangeQuery.newStringRange(terms[0].trim(),
								n1, n2, true, true);
						query.add(datequery, BooleanClause.Occur.SHOULD);
					}
				} else {
					query.add(new TermQuery(new Term(terms[0], terms[1])), BooleanClause.Occur.SHOULD);
				}
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
