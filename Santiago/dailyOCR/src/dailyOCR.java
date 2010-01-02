import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class dailyOCR
{
	public static final String pathPrincipal = "D:/Documents and Settings/Admin/Escritorio/dailyOcr/";
	public static final String pathSecundario = "\\\\SANTIAGO-PC\\Users\\Public\\dailyOCR\\";
	
	public static long tiempo = 0;
	public static ArrayList <Senal> actuales = new ArrayList <Senal> ();
	public static List <Senal> ultimasProcesadas = new ArrayList <Senal> (); // Prueba
	public static int errores = 0;
	
	public static void inicio()
	{ 
		iniciarEquipo();
		Breakout.leerSenales();
		Range.leerSenales();
		Momentum.leerSenales();
		Breakout2.leerSenales();
		Estrategia.leerSenales(new File(pathPrincipal + "Actuales.txt"), actuales);
		guardarPrueba(ultimasProcesadas); // Prueba
		try
		{
			Thread.sleep(12000);
			capturar();
		}
		catch(Exception e)
		{
			Error.agregar("Error en el inicio");
		}
		while(true)
		{
			try
			{
				esperar();
				boolean termino = false;
				while(!termino)
				{
					try
					{
						ArrayList <Senal> nuevas = leer();
						actuales = generar(nuevas);
						guardarPrueba(ultimasProcesadas); // Prueba
						eliminar(tiempo);
						termino = true;
						int negociosAbiertos = Math.max(Escritor.lineas.size(), EscritorB.lineas.size());
						Escritor.escribir();
						EscritorB.escribir();
						capturar();
						Thread.sleep(negociosAbiertos > 0 ? (60000 + 16000 * negociosAbiertos) : 0);
						Escritor.leerMagicos();
						EscritorB.leerMagicos();
						escribirActuales(pathPrincipal);
						escribirActuales(pathSecundario);
						copiarArchivo(new File(pathPrincipal + "Error.txt"), new File(pathSecundario + "Error.txt"));
						errores = 0;
					}
					catch(Exception e)
					{	
						Error.agregar("Error interpretando las senales");
						long tiempoant = tiempo;
						capturar();
						esperar();
						eliminar(tiempoant);
						errores++;
						if(errores == 10)
						{
							apagarEquipo();
						}
					}
				}
			}
			catch(Exception e)
			{
				Error.agregar("Error en el ciclo");
			}
		}
	}

	private static void iniciarEquipo()
	{
		try
		{
			Thread.sleep(60000);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(5000);
			new Robot().keyPress(KeyEvent.VK_LEFT);
			Thread.sleep(5000);
			new Robot().keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
			new Robot().mouseMove(30, 885);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(5000);
			new Robot().mouseMove(30, 485);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(5000);
			new Robot().mouseMove(1110, 250);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(10000);
			new Robot().mouseMove(1102, 35);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(30000);
			new Robot().keyPress(KeyEvent.VK_1);
			Thread.sleep(5000);
			new Robot().keyPress(KeyEvent.VK_DOWN);
			Thread.sleep(5000);
			new Robot().keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
			new Robot().keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(30000);
			actualizarPagina();
			Thread.sleep(5000);
			new Robot().mouseMove(200, 885);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
		}
		catch(Exception e)
		{
			Error.agregar("Error iniciando el equipo");
		}
	}

	private static void capturar() throws IOException, AWTException, InterruptedException
	{
		try
		{
			BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(545, 250, 385, 440));
			tiempo = System.currentTimeMillis();
			ImageIO.write(screencapture, "bmp", new File(pathPrincipal + "Io/" + tiempo + ".bmp"));
			if(!chequearConexion())
			{
				arreglarConexion();
			}
		}
		catch(Exception e)
		{
			Error.agregar("Error en la captura");
		}
	}
	
	private static void esperar() 
	{
		try
		{
			for(int i = 0; i < 90; i++)
			{
				Thread.sleep(1000);
				if(new File(pathPrincipal + tiempo + ".txt").exists())
				{
					return;
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar("Error esperando");
		}
	}
	
	private static ArrayList <Senal> leer() throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(pathPrincipal + tiempo + ".txt"));
		sc.useDelimiter("[\\Q*\\E]");
		ArrayList <Senal> nuevas = new ArrayList <Senal> ();
		while(sc.hasNext())
		{
			String estrategia = sc.next();
			String cuerpo = sc.next();
			nuevas.add(Estrategia.generarSenal(estrategia, cuerpo));
		}
		sc.close();
		return nuevas;
	}
	
	private static ArrayList <Senal> generar(ArrayList <Senal> nuevas) throws Exception 
	{
		if(nuevas.size() == 19)
		{
			if(actuales.isEmpty())
			{
				procesar(duplicar(nuevas));
				return nuevas;
			}
			for(int i = 0; i < 19; i++)
			{
				if(nuevas.subList(i, 19).equals(actuales.subList(0, 19 - i)))
				{
					    procesar(duplicar(nuevas.subList(0, i)));
						return nuevas;
				}
			}
			guardar(nuevas);
			for(int i = 0; i < 6; i++)
			{
				if(casiIguales(nuevas.subList(i, 19), actuales.subList(0, 19 - i)))
				{
					Error.agregar("Se detecto una coincidencia dudosa");
					return actuales;
				}
			}
			procesar(duplicar(nuevas));
			Error.agregar("No se detecto una coincidencia");
			return nuevas;
		}
		else
		{
			guardar(nuevas);
			throw(new Exception());
		}
		
	}

	private static boolean casiIguales(List <Senal> lista1, List <Senal> lista2)
	{
		int k = 0;
		for(int i = 0; i < Math.min(lista1.size(), lista2.size()); i++)
		{
			if(!lista1.get(i).equals(lista2.get(i)))
			{
				k++;
			}
		}
		if(k == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private static ArrayList <Senal> duplicar(List <Senal> nuevas)
	{	
		ArrayList <Senal> duplicado = new ArrayList <Senal> ();
		duplicado.addAll(nuevas);
		return duplicado;
	}

	private static void procesar(List <Senal> nuevas)
	{	
		nuevas = convertirBreakout2(nuevas);
		nuevas.addAll(leerManuales());
		ultimasProcesadas = nuevas; // Prueba
		for(Senal s : invertir(nuevas))
		{
			if(s.estrategia == Senal.BREAKOUT)
			{
				Breakout.intentarAgregar(s);
			}
			else if(s.estrategia == Senal.BREAKOUT2)
			{
				Breakout2.intentarAgregar(s);
			}
			else if(s.estrategia == Senal.MOMENTUM)
			{
				Momentum.intentarAgregar(s);
			}
			else if(s.estrategia == Senal.RANGE)
			{
				Range.intentarAgregar(s);
			}
		}
	}

	private static List <Senal> convertirBreakout2(List <Senal> nuevas) 
	{
		for(int i = 9; i < nuevas.size(); i++)
		{
			if(nuevas.get(i).tipo == Senal.TRADE && nuevas.get(i).estrategia == Senal.BREAKOUT)
			{
				int k = 0;
				for(int j = 0; j < i; j++)
				{
					Senal s = nuevas.get(j);
					if(s.estrategia == Senal.BREAKOUT && s.tipo == Senal.NOIMPORTA && s.par == nuevas.get(i).par)
					{
						k++;
					}
				}
				if(k > 7)
				{
					ArrayList <Senal> anteriores = duplicar(nuevas);					
					nuevas = nuevas.subList(0, i);
					nuevas.add(new Senal(Senal.BREAKOUT2, anteriores.get(i).par, Senal.TRADE, anteriores.get(i).compra));
					if(anteriores.size() > i + 1)
					{
						nuevas.addAll(anteriores.subList(i + 1, anteriores.size()));
					}
					break;
				}
			}
		}
		return nuevas;
	}

	private static ArrayList <Senal> leerManuales() 
	{
		try
		{
			ArrayList <Senal> manuales = new ArrayList <Senal> ();
			Scanner sc = new Scanner(new File(pathSecundario + "Ordenes.txt"));
			while(sc.hasNext())
			{
				int estrategia = sc.nextInt();
				int par = sc.nextInt();
				int tipo = sc.nextInt();
				boolean compra = sc.nextInt() == 1;
				int magico = sc.nextInt();
				manuales.add(new Senal(estrategia, par, tipo, compra, magico));
			}
			sc.close();
			new File(pathSecundario + "Ordenes.txt").delete();
			return manuales;
		}
		catch(Exception e)
		{
			return new ArrayList <Senal> ();
		}
	}
	
	private static List <Senal> invertir(List <Senal> nuevas) 
	{
		ArrayList <Senal> invertida = new ArrayList <Senal> ();
		for(int i = 0; i < nuevas.size(); i++)
		{
			invertida.add(0, nuevas.get(i));
		}
		return invertida;
		
	}
	
	private static void eliminar(long tiempo) 
	{
		try
		{
			new File(pathPrincipal + tiempo + ".txt").delete();
			new File(pathPrincipal + "Io/" + tiempo + ".bmp").delete();
		}
		catch(Exception e)
		{
			Error.agregar("Error eliminando los archivos");
		}
	}
	
	private static void escribirActuales(String pathPrincipal)
	{
		try
		{
			String nuevaLinea = System.getProperty("line.separator");
			File archivoEscritura = new File(pathPrincipal + "Range.txt");
			archivoEscritura.delete();
			archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura);
			for(Senal s : Range.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.close();
			archivoEscritura = new File(pathPrincipal + "Momentum.txt");
			archivoEscritura.delete();
			archivoEscritura.createNewFile();
			fw = new FileWriter(archivoEscritura);
			for(Senal s : Momentum.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);	
			}
			fw.close();
			archivoEscritura = new File(pathPrincipal + "Breakout.txt");
			archivoEscritura.delete();
			archivoEscritura.createNewFile();
			fw = new FileWriter(archivoEscritura);
			for(Senal s : Breakout.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.close();
			archivoEscritura = new File(pathPrincipal + "Breakout2.txt");
			archivoEscritura.delete();
			archivoEscritura.createNewFile();
			fw = new FileWriter(archivoEscritura);
			for(Senal s : Breakout2.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.close();
			archivoEscritura = new File(pathPrincipal + "Actuales.txt");
			archivoEscritura.delete();
			archivoEscritura.createNewFile();
			fw = new FileWriter(archivoEscritura);
			for(Senal s : actuales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.close();
		}
		catch(Exception e)
		{
		}
	}

	private static void guardar(ArrayList <Senal> nuevas) 
	{
		try 
		{	
			String nuevaLinea = System.getProperty("line.separator");
			copiarArchivo(new File(pathPrincipal + "Io/" + tiempo + ".bmp"), new File(pathPrincipal + "Error/" + tiempo + ".bmp"));
			File archivoEscritura = new File(pathPrincipal + "Error/" + tiempo + ".txt");
			archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura);
			fw.write("Actuales:" + nuevaLinea);
			for(Senal s : actuales)
			{
				fw.write(s.estrategia + " " + Estrategia.darNombrePar(s) + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Nuevas:" + nuevaLinea);
			for(Senal s : nuevas)
			{
				fw.write(s.estrategia + " " + Estrategia.darNombrePar(s) + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.close();
		} 
		catch (Exception e) 
		{
		}
	}
	
	private static void guardarPrueba(List <Senal> nuevas) // Prueba
	{
		try 
		{	
			boolean bp = false;
			for(Senal s : nuevas)
			{
				if(s.tipo == Senal.TRADE || s.tipo == Senal.HIT)
				{
					bp = true;
				}
			}
			if(!bp)
			{
				return;
			}
			String nuevaLinea = System.getProperty("line.separator");
			copiarArchivo(new File(pathPrincipal + "Io/" + tiempo + ".bmp"), new File(pathPrincipal + "Error/Prueba/" + tiempo + ".bmp"));
			File archivoEscritura = new File(pathPrincipal + "Error/Prueba/" + tiempo + ".txt");
			archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura);
			fw.write("Actuales:" + nuevaLinea);
			for(Senal s : actuales)
			{
				fw.write(s.estrategia + " " + Estrategia.darNombrePar(s) + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Nuevas:" + nuevaLinea);
			for(Senal s : nuevas)
			{
				fw.write(s.estrategia + " " + Estrategia.darNombrePar(s) + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Range:" + nuevaLinea);
			for(Senal s : Range.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Momentum:" + nuevaLinea);
			for(Senal s : Momentum.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Breakout:" + nuevaLinea);
			for(Senal s : Breakout.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Breakout2:" + nuevaLinea);
			for(Senal s : Breakout2.senales)
			{
				fw.write(s.estrategia + " " + s.par + " " + s.tipo + " " + (s.compra ? 1 : 0) + " " + s.magico + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("Escritor:" + nuevaLinea);
			for(String s : Escritor.lineas)
			{
				fw.write(s + nuevaLinea);
			}
			fw.write(nuevaLinea);
			fw.write("EscritorB:" + nuevaLinea);
			for(String s : EscritorB.lineas)
			{
				fw.write(s + nuevaLinea);
			}
			fw.close();
		} 
		catch (Exception e) 
		{
		}
	}
	
	public static void copiarArchivo(File entrada, File salida) 
	{
	    try 
	    {
	    	salida.createNewFile();
		    FileInputStream fis  = new FileInputStream(entrada);
		    FileOutputStream fos = new FileOutputStream(salida);
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) 
	        {
	            fos.write(buf, 0, i);
	        }
		    if (fis != null)
		    {
		    	fis.close();
		    }
		    if (fos != null)
		    {
		    	fos.close();
		    }
	    }
	    catch (Exception e) 
	    {
	    }
	}

	
	public static boolean leerEstrategias(int par, String estrategia)
	{
		try
		{
			Thread.sleep(500);
			new Robot().mouseMove(1430, 820);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			new Robot().mouseMove(500, 300);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			new Robot().mouseMove(1430, 230);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			for(int i = 0; i < 14; i++)
			{
				new Robot().keyPress(KeyEvent.VK_UP);
				Thread.sleep(500);
			}
			for(int i = 0; i < par - 1; i++)
			{	
				new Robot().keyPress(KeyEvent.VK_DOWN);
				Thread.sleep(500);
			}
			BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(545, 250, 250, 600));
			Thread.sleep(500);
			new Robot().mouseMove(200, 885);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			ImageIO.write(screencapture, "bmp", new File(pathPrincipal + "Io/estrategias.bmp"));
			boolean encontrado = false;
			for(int i = 0; i < 90; i++)
			{
				Thread.sleep(1000);
				if(new File(pathPrincipal + "estrategias.txt").exists())
				{
					encontrado = true;
					break;
				}
			}
			if(encontrado)
			{
				Scanner sc = new Scanner(new File(pathPrincipal + "estrategias.txt"));
				String linea = "";
				while(sc.hasNextLine())
				{
					linea += sc.nextLine();
				}
				sc.close();
				new File(pathPrincipal + "estrategias.txt").delete();
				new File(pathPrincipal + "Io/estrategias.bmp").delete();
				return linea.contains(estrategia);
			}
			else
			{
				new File(pathPrincipal + "estrategias.txt").delete();
				new File(pathPrincipal + "Io/estrategias.bmp").delete();
				return false;
			}
			
		}
		catch(Exception e)
		{
			new File(pathPrincipal + "estrategias.txt").delete();
			new File(pathPrincipal + "Io/estrategias.bmp").delete();
			return false;
		}
	}
	
	public static boolean chequearConexion()
	{
		try
		{
			URL url = new URL("http://www.dailyfx.com");
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String s = "";
			String contents = "";
			while((s = bufferedReader.readLine()) != null)
			{
				contents = contents.concat(s);
			}
			inputStream.close();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	} 
	
	public static void arreglarConexion()
	{
		try
		{
			Error.agregar("Error de conexinn, intentando arreglar");
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "primero.bat");
			Thread.sleep(60000);
			actualizarPagina();
			if(!chequearConexion())
			{
				Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "segundo.bat");
				Thread.sleep(60000);
				actualizarPagina();
			}
		} 
		catch (Exception e)
		{
		}
	}

	public static void actualizarPagina()
	{
		try
		{
			new Robot().mouseMove(1102, 35);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			new Robot().mouseMove(820, 370);
			Thread.sleep(30000);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseMove(820, 800);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			Error.agregar("Error interno actualizando pagina");
		}
	}
	
	public static void apagarEquipo()
	{
		try
		{
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "tercero.bat");
			Runtime.getRuntime().exit(0);
		}
		catch(Exception e)
		{
			Error.agregar("Error apagando el equipo");
		}
	}
	
	public static void main(String [] args)
	{
		inicio();
	}
}	
