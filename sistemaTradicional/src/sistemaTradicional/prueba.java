package sistemaTradicional;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class prueba {
	public static void main(String[] args) throws IOException {
		Analyzer a = new TradicionalAnalyzer();
		String[] pruebas = {
				"Me interesan los trabajos en relación con el mundo de la musica y el sonido cuyo autor o director se llame Javier.",
				"Estoy interesado en el periodo histórico de la Guerra de Independencia (1808-1814). Me gustaría saber qué trabajos o artículos hay relacionados con este hecho englobados en la historia de España",
				"Tesis que traten de energías renovables, en el período de 2010 a 2015.",
				"Quiero documentos sobre desarrollo de videojuegos o diseño de personajes en los últimos 5 años.",
				"Me gustaría encontrar construcciones arquitectónicas situadas en España con elementos decorativos que pertenezcan tanto a la Edad Media como a la época gótica y cuyo estado de conservación sea óptimo." };

		for (int i = 0; i < pruebas.length; i++) {
			TokenStream ts = a.tokenStream(null, pruebas[i]);
			ts.reset();
			while (ts.incrementToken()) {
				System.out.print(ts.getAttribute(CharTermAttribute.class)
						.toString() + " ");
			}
			ts.close();
			System.out.println();
		}
		a.close();
	}

}
