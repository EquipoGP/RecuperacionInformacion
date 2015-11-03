package sistemaTradicional;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.SpanishStemmer;

public class TradicionalAnalyzer extends Analyzer {
	/**
	 * Analizador especifico del tema Recuperacion de Informacion (2015-2016)
	 * @version 1.0
	 */

	/* Stop Words especificas del tema */
	public static final String[] CUSTOM_STOP_WORDS = { 
			"interes", "cuy", "relacion", "quier", "gust", "trat", "encontr", 
			"llam", "mund", "sab", "hech", "englob", "period", "document", 
			"pertenezc", "situ" 
			};
	
	/**
	 * Analizador: crea una cadena de filtros que se compone de:
	 * - Filtro estandar (signos de puntuacion...)
	 * - Filtro de lowercase (pasar la entrada a minusculas)
	 * - Filtro de StopWords genericas del castellano
	 * - Filtro de stemmer generico del castellano
	 * - Filtro de StopWords especificas del tema
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected TokenStreamComponents createComponents(String field, Reader reader) {
		CharArraySet stopTable = StopFilter.makeStopSet(CUSTOM_STOP_WORDS, true);

		Tokenizer source = new StandardTokenizer(Version.LUCENE_44, reader);

		TokenFilter filters = new StandardFilter(source);
		filters = new LowerCaseFilter(filters);
		filters = new StopFilter(filters, SpanishAnalyzer.getDefaultStopSet());
		filters = new SnowballFilter(filters, new SpanishStemmer());
		filters = new StopFilter(filters, stopTable);

		return new TokenStreamComponents(source, filters);
	}

}
