package sistemaSemantico;

import java.util.HashSet;
import java.util.Set;

public class Tesauro {

	private static Set<String> guerraIndependencia, sonido, musica, energiaRenovable,
			disenio, desarrollo, programacion, videojuego, arquitectura, epoca,
			conservacion;

	public static void Tesauro() {
		prepararConsulta02_4();
		prepararConsulta13_2();
		prepararConsulta09_3();
		prepararConsulta07_2();
		prepararConsulta05_5();
	}
	
	public static boolean guerraIndependencia(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : guerraIndependencia){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean sonido(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : sonido){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean musica(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : musica){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean energiaRenovable(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : energiaRenovable){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean disenio(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : disenio){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean desarrollo(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : desarrollo){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}

	public static boolean programacion(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : programacion){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean videojuego(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : videojuego){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean arquitectura(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : arquitectura){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean epoca(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : epoca){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean conservacion(String description, String title){
		title = title.toLowerCase();
		description = description.toLowerCase();
		
		for(String s : guerraIndependencia){
			if(title.contains(s) || description.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	private static void prepararConsulta02_4() {
		guerraIndependencia = new HashSet<String>();
		guerraIndependencia.add("guerra");
		guerraIndependencia.add("ejercito");
		guerraIndependencia.add("1808-1814");
		guerraIndependencia.add("1808");
		guerraIndependencia.add("españa");
	}

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

	private static void prepararConsulta07_2() {
		disenio = new HashSet<String>();
		disenio.add("diseñar");
		disenio.add("diseño de personajes");

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

	private static void prepararConsulta05_5() {
		arquitectura = new HashSet<String>();
		arquitectura.add("edificios");
		arquitectura.add("escultura");

		epoca = new HashSet<String>();
		epoca.add("edad media");
		epoca.add("medieval");
		epoca.add("epoca gotica");
		epoca.add("oligarquía urbana");

		conservacion = new HashSet<String>();
		conservacion.add("rehabilitacion");
		conservacion.add("restauracion");
		conservacion.add("restauraciones");
	}

}
