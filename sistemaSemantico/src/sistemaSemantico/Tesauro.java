/*
 * Fichero: Tesauro.java
 * Autores: Patricia Lazaro Tello (554309)
 * 			Alejandro Royo Amondarain (560285)
 */

package sistemaSemantico;

import java.util.HashSet;
import java.util.Set;

public class Tesauro {

	/**
	 * Clase que emula un tesauro para enlazar los datos con conceptos SKOS
	 */

	/* atributos privados */
	private static Set<String> guerraIndependencia, sonido, musica,
			energiaRenovable, videojuego, arquitectura, epoca;

	/**
	 * Metodo para la inicializacion del tesauro
	 */
	public static void init() {
		prepararConsulta02_4();
		prepararConsulta13_2();
		prepararConsulta09_3();
		prepararConsulta07_2();
		prepararConsulta05_5();
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la guerra de independencia
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean guerraIndependencia(String description, String title) {
		return buscar(description, title, guerraIndependencia);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema del sonido
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean sonido(String description, String title) {
		return buscar(description, title, sonido);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la musica
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean musica(String description, String title) {
		return buscar(description, title, musica);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la energia renovable
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean energiaRenovable(String description, String title) {
		return buscar(description, title, energiaRenovable);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema del videojuego
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean videojuego(String description, String title) {
		return buscar(description, title, videojuego);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la arquitectura
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean arquitectura(String description, String title) {
		return buscar(description, title, arquitectura);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la epoca
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean epoca(String description, String title) {
		return buscar(description, title, epoca);
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se englobal en
	 * el tema elegido
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @param chosen
	 *            tema elegido
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	private static boolean buscar(String description, String title,
			Set<String> chosen) {
		title = normalizar(title);
		description = normalizar(description);

		for (String s : chosen) {
			String regex = "^" + s + ".*|.* " + s + ".*|.*-" + s + ".*";
			if (title.matches(regex) || description.matches(regex)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la guerra de la
	 * independencia
	 */
	private static void prepararConsulta02_4() {
		guerraIndependencia = new HashSet<String>();
		guerraIndependencia.add("guerra de la independencia");
		// guerraIndependencia.add("guerra");
		// guerraIndependencia.add("ejercito");
		guerraIndependencia.add("1808-1814");
		guerraIndependencia.add("1808");
		// guerraIndependencia.add("espana");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la musica y el sonido
	 */
	private static void prepararConsulta13_2() {
		sonido = new HashSet<String>();
		sonido.add("sonido");
		sonido.add("sonoro");
		sonido.add("audio");
		// sonido.add("ruido");

		musica = new HashSet<String>();
		musica.add("musica");
		musica.add("musical");
		musica.add("musico");
		musica.add("cancion");
		musica.add("ritmo");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la energia renovable
	 */
	private static void prepararConsulta09_3() {
		energiaRenovable = new HashSet<String>();
		// energiaRenovable.add("renovable");
		// energiaRenovable.add("energia");
		energiaRenovable.add("energia renovable");
		energiaRenovable.add("energias renovables");
		// energiaRenovable.add("combustibles fosiles");
		// energiaRenovable.add("pilas de combustible");
		// energiaRenovable.add("energia geotermica");
		// energiaRenovable.add("energia cinetica");
		// energiaRenovable.add("solar");
//		energiaRenovable.add("paneles solares");
		energiaRenovable.add("eolica");
//		energiaRenovable.add("parques eolicos");
//		energiaRenovable.add("biomasa");
		// energiaRenovable.add("biodiesel");
		// energiaRenovable.add("eficiencia energetica");
//		 energiaRenovable.add("medio ambiente");
//		 energiaRenovable.add("medioambiental");
		// energiaRenovable.add("cambio climatico");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con el disenio y desarrollo de
	 * videojuegos y personajes
	 */
	private static void prepararConsulta07_2() {
		videojuego = new HashSet<String>();
		videojuego.add("videojuego");
		videojuego.add("diseno de personajes");
		videojuego.add("desarrollo de videojuegos");
		videojuego.add("agente inteligente");
		// videojuego.add("bots");
		videojuego.add("motor grafico");
		 videojuego.add("animacion");
//		videojuego.add("animaciones");
		// videojuego.add("multiples jugadores");
		videojuego.add("realidad aumentada");
		videojuego.add("realidad virtual");
		videojuego.add("videojuego educativo");
//		videojuego.add("pokemon");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la arquitectura
	 * medio-gotica
	 */
	private static void prepararConsulta05_5() {
		arquitectura = new HashSet<String>();
		arquitectura.add("construccion arquitectonica");
		// arquitectura.add("edificios");
		// arquitectura.add("escultura");

		epoca = new HashSet<String>();
		// epoca.add("epoca");
		epoca.add("edad media");
		// epoca.add("medieval");
		epoca.add("epoca gotica");
//		epoca.add("oligarquia urbana");
	}

	/**
	 * Normaliza las cadenas de texto
	 * 
	 * @param s
	 *            cadena a normalizar
	 * @return la misma cadena de texto en minusculas y sin acentos
	 */
	private static String normalizar(String s) {
		if (s != null) {
			s = s.toLowerCase();
			s = s.replaceAll("á", "a");
			s = s.replaceAll("é", "e");
			s = s.replaceAll("í", "i");
			s = s.replaceAll("ó", "o");
			s = s.replaceAll("ú", "u");
			s = s.replaceAll("ñ", "n");

			return s;
		} else {
			return "";
		}
	}

}
