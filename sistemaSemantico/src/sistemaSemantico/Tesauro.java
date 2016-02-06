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
			energiaRenovable, disenio, desarrollo, programacion, videojuego,
			arquitectura, epoca, conservacion;

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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : guerraIndependencia) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : sonido) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : musica) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : energiaRenovable) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema del disenio
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean disenio(String description, String title) {
		title = normalizar(title);
		description = normalizar(description);

		for (String s : disenio) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema del desarrollo
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean desarrollo(String description, String title) {
		title = normalizar(title);
		description = normalizar(description);

		for (String s : desarrollo) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la programacion
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean programacion(String description, String title) {
		title = normalizar(title);
		description = normalizar(description);

		for (String s : programacion) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : videojuego) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : arquitectura) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
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
		title = normalizar(title);
		description = normalizar(description);

		for (String s : epoca) {
			if (title.contains(s) || description.contains(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si el documento referenciado por los argumentos se engloba en
	 * el tema de la conservacion
	 * 
	 * @param description
	 *            descripcion del documento
	 * @param title
	 *            titulo del documento
	 * @return true si tiene que ver con el tema, false en caso contrario
	 */
	public static boolean conservacion(String description, String title) {
		title = normalizar(title);
		description = normalizar(description);

		for (String s : conservacion) {
			if (title.contains(s) || description.contains(s)) {
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
		guerraIndependencia.add("guerra");
		guerraIndependencia.add("ejercito");
		guerraIndependencia.add("1808-1814");
		guerraIndependencia.add("1808");
		guerraIndependencia.add("espana");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la musica y el sonido
	 */
	private static void prepararConsulta13_2() {
		sonido = new HashSet<String>();
		sonido.add("sonido");
		sonido.add("sonoro");
		sonido.add("audio");
		sonido.add("ruido");

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
		energiaRenovable.add("renovable");
		energiaRenovable.add("energia");
		energiaRenovable.add("combustibles fosiles");
		energiaRenovable.add("pilas de combustible");
		energiaRenovable.add("energia geotermica");
		energiaRenovable.add("energia cinetica");
		energiaRenovable.add("solar");
		energiaRenovable.add("paneles solares");
		energiaRenovable.add("eolica");
		energiaRenovable.add("parques eolicos");
		energiaRenovable.add("biomasa");
		energiaRenovable.add("biodiesel");
		energiaRenovable.add("eficiencia energetica");
		energiaRenovable.add("medio ambiente");
		energiaRenovable.add("medioambiental");
		energiaRenovable.add("cambio climatico");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con el disenio y desarrollo de
	 * videojuegos y personajes
	 */
	private static void prepararConsulta07_2() {
		disenio = new HashSet<String>();
		disenio.add("disenar");
		disenio.add("diseno de personajes");

		desarrollo = new HashSet<String>();
		desarrollo.add("desarrollo de videojuegos");

		programacion = new HashSet<String>();
		programacion.add("procedural");

		videojuego = new HashSet<String>();
		videojuego.add("videojuegos");
		videojuego.add("agente inteligente");
		videojuego.add("bots");
		videojuego.add("motor grafico");
		videojuego.add("animacion");
		videojuego.add("animaciones");
		videojuego.add("multiples jugadores");
		videojuego.add("realidad aumentada");
		videojuego.add("realidad virtual");
		videojuego.add("videojuego educativo");
		videojuego.add("pokemon");
	}

	/**
	 * Prepara el tesauro para enlazar documentos con la arquitectura
	 * medio-gotica
	 */
	private static void prepararConsulta05_5() {
		arquitectura = new HashSet<String>();
		arquitectura.add("edificios");
		arquitectura.add("escultura");

		epoca = new HashSet<String>();
		epoca.add("edad media");
		epoca.add("medieval");
		epoca.add("epoca gotica");
		epoca.add("oligarquia urbana");

		conservacion = new HashSet<String>();
		conservacion.add("rehabilitacion");
		conservacion.add("restauracion");
		conservacion.add("restauraciones");
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
