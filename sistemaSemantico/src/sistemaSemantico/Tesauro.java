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
			energiaRenovable, videojuego, arquitectura, sociedad;

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
	public static boolean sociedad(String description, String title) {
		return buscar(description, title, sociedad);
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
		guerraIndependencia.add("1808-1814");
		guerraIndependencia.add("1808");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la musica y el sonido
	 */
	private static void prepararConsulta13_2() {
		sonido = new HashSet<String>();
		sonido.add("sonido");
		sonido.add("audio");

		musica = new HashSet<String>();
		musica.add("musica");
		musica.add("musical");
		musica.add("musicos");
		musica.add("cancion");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la energia renovable
	 */
	private static void prepararConsulta09_3() {
		energiaRenovable = new HashSet<String>();
		energiaRenovable.add("energia renovable");
		energiaRenovable.add("energias renovables");
		energiaRenovable.add("energia solar");
		energiaRenovable.add("energia eolica");
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
		videojuego.add("motor grafico");
		videojuego.add("animacion");
		videojuego.add("realidad aumentada");
		videojuego.add("realidad virtual");
		videojuego.add("videojuego educativo");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la arquitectura
	 * medio-gotica
	 */
	private static void prepararConsulta05_5() {
		arquitectura = new HashSet<String>();
		arquitectura.add("monasterio");
		arquitectura.add("castillo-abadia");

		sociedad = new HashSet<String>();
		sociedad.add("arquitectura social");
		sociedad.add("oligarquia urbana");
		sociedad.add("concejo");
		sociedad.add("patrimonio historico");
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
			String s1 = s.toLowerCase();
			s1 = s1.replaceAll("á", "a");
			s1 = s1.replaceAll("é", "e");
			s1 = s1.replaceAll("í", "i");
			s1 = s1.replaceAll("ó", "o");
			s1 = s1.replaceAll("ú", "u");
			s1 = s1.replaceAll("ñ", "n");
			
			if(s.contains("Edad Media")){
				s1 = s1.replaceAll("edad media", "Edad Media");
			}

			return s1;
		} else {
			return "";
		}
	}

}
