package sistemaTradicional;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class prueba {
	public static void main(String[] args) throws IOException {
		Analyzer a = new TradicionalAnalyzer();
		String[] pruebas = {
				"Me interesan los trabajos en relaci�n con el mundo de la musica y el sonido cuyo autor o director se llame Javier.",
				"Estoy interesado en el periodo hist�rico de la Guerra de Independencia (1808-1814). Me gustar�a saber qu� trabajos o art�culos hay relacionados con este hecho englobados en la historia de Espa�a",
				"Tesis que traten de energ�as renovables, en el per�odo de 2010 a 2015.",
				"Quiero documentos sobre desarrollo de videojuegos o dise�o de personajes en los �ltimos 5 a�os.",
				"Me gustar�a encontrar construcciones arquitect�nicas situadas en Espa�a con elementos decorativos que pertenezcan tanto a la Edad Media como a la �poca g�tica y cuyo estado de conservaci�n sea �ptimo." };

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
