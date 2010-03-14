package control;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.BidAsk;
import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import control.conexion.ConexionServidor;





public class SistemaDailyFX extends SistemaEstrategias
{
	Estrategia breakout1;
	Estrategia breakout2;
	Estrategia range1;
	Estrategia range2;
	Estrategia momentum1;
	Estrategia momentum2;
	
	ArrayList <Estrategia> estrategias;
	
	public void cargarEstrategias()
	{
		escritor = new Escritor("dailyFX/experts/files/");
		breakout1 = Estrategia.leer(IdEstrategia.BREAKOUT1);
		if(breakout1 == null)
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
		}
		breakout1.escritor = escritor;
		breakout2 = Estrategia.leer(IdEstrategia.BREAKOUT2);
		if(breakout2 == null)
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
		}
		breakout2.escritor = escritor;
		range1 = Estrategia.leer(IdEstrategia.RANGE1);
		if(range1 == null)
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		range1.escritor = escritor;
		range2 = Estrategia.leer(IdEstrategia.RANGE2);
		if(range2 == null)
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		range2.escritor = escritor;
		momentum1 = Estrategia.leer(IdEstrategia.MOMENTUM1);
		if(momentum1 == null)
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		momentum1.escritor = escritor;
		momentum2 = Estrategia.leer(IdEstrategia.MOMENTUM2);
		if(momentum2 == null)
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		momentum2.escritor = escritor;
		estrategias = new ArrayList <Estrategia> ();
		estrategias.add(breakout1);
		estrategias.add(breakout2);
		estrategias.add(momentum1);
		estrategias.add(momentum2);
		estrategias.add(range1);
		estrategias.add(range2);
		try
		{
			metodoLectura = ConexionServidor.class.getMethod("leerServidorDailyFX");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error en metodo lectura de sistemaDailyFx");
		}
	}

	public void verificarConsistencia()
	{
		if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null ||
		   breakout1.verificarConsistencia() || breakout2.verificarConsistencia() || range1.verificarConsistencia() ||
		   range2.verificarConsistencia() || momentum1.verificarConsistencia() || momentum2.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	public void iniciarHilo() 
	{
		new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				int numeroErrores = 0;
				while(true)
				{
					try
					{
						verificarConsistencia();
						Thread.sleep(10000);
						iniciarProcesamiento();
					    escritor.escribir();
						escritor.leerMagicos();
						verificarConsistencia();
						persistir();
					}
					catch(Exception e)
					{	
						try
						{
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo dailyFX");
				    		Thread.sleep(60000);
							if(numeroErrores == 60)
							{
								Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
								Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
								{
									@Override
									public void run() 
									{
										try 
										{
											Runtime.getRuntime().exec("java -jar dailyOCR.jar");
										} 
										catch (IOException e)
										{
								    		Error.agregar(e.getMessage() + " Error reiniciando");
										}
									}
								}));
								System.exit(0);
							}
						}
						catch(Exception e1)
						{
				    		Error.agregar(e.getMessage() + " Error en el ciclo dailyFX");
						}
					}
				}
			}
		}).start();
	}

	protected ArrayList <Senal> leer(String [] entradas)
	{
		try
		{
			String entrada = entradas[0];
			String entrada1 = entrada.substring(entrada.indexOf("\"Signal\":["));
			ArrayList <Integer> curoplots = new ArrayList <Integer> ();
			ArrayList <Integer> strategyid = new ArrayList <Integer> ();
			ArrayList <String> symbol = new ArrayList <String> ();
			ArrayList <String> direction = new ArrayList <String> ();
			ArrayList <Double> entryprice = new ArrayList <Double> ();
			ArrayList <Double> bid = new ArrayList <Double> ();
			ArrayList <Double> ask = new ArrayList <Double> ();
			ArrayList <String> currency = new ArrayList <String> ();
			Pattern pattern = Pattern.compile("\\d+,\"strategyId\":\\d+");
			Pattern pattern2 = Pattern.compile("\"strategyId\":\\d+");
			Pattern pattern3 = Pattern.compile("\"symbol\":\"\\w+\"");
			Pattern pattern4 = Pattern.compile("\"direction\":\"\\w+\"");
			Pattern pattern5 = Pattern.compile("\"entryPrice\":\\d+.\\d+");
			Pattern pattern6 = Pattern.compile("\"bid\":\\d+.\\d+");
			Pattern pattern7 = Pattern.compile("\"ask\":\\d+.\\d+");
			Pattern pattern8 = Pattern.compile("\"currency\":\"\\w+\"");
			Matcher matcher = pattern.matcher(entrada1);
			Matcher matcher2 = pattern2.matcher(entrada1);
			Matcher matcher3 = pattern3.matcher(entrada1);
			Matcher matcher4 = pattern4.matcher(entrada1);
			Matcher matcher5 = pattern5.matcher(entrada1);
			Matcher matcher6 = pattern6.matcher(entrada);
			Matcher matcher7 = pattern7.matcher(entrada);
			Matcher matcher8 = pattern8.matcher(entrada);
			while(matcher.find()) 
			{  
				String S = matcher.group();
				S = S.substring(0, 1);
				curoplots.add(Integer.parseInt(S));
			} 
			while(matcher2.find())
			{
				String S = matcher2.group();
				S = S.substring(13);
				strategyid.add(Integer.parseInt(S));  
			}
			while(matcher3.find()) 
			{
				String S = matcher3.group();
				S = S.substring(10);
				S = S.replace("\"", "");
				symbol.add(S);
	  		}
			while(matcher4.find()) 
			{
		  		String S = matcher4.group();
		  		S = S.substring(13);
		  		S = S.replace("\"", "");
		  		direction.add(S);
			}
			while (matcher5.find()) 
			{
				String S = matcher5.group();
				S = S.substring(13);
				entryprice.add(Double.parseDouble(S));	
			}
			while (matcher6.find())
			{
				String S = matcher6.group();
				S = S.substring(6);
				bid.add(Double.parseDouble(S));	
			}
			while (matcher7.find())
			{
				String S = matcher7.group();
				S = S.substring(6);
				ask.add(Double.parseDouble(S));	
			}
			while (matcher8.find())
			{
				String S = matcher8.group();
				S = S.substring(12);
		  		S = S.replace("\"", "");
		  		currency.add(S);
			}
			ArrayList <Senal> nuevasSenales = new ArrayList <Senal> ();
			for(int i = 0; i<curoplots.size(); i++)
			{
				Senal actual = new Senal(IdEstrategia.darEstrategia(strategyid.get(i)), direction.get(i).equals("Buy"), Par.convertirPar(symbol.get(i)), curoplots.get(i), entryprice.get(i));
				nuevasSenales.add(actual);
			}
			ArrayList <BidAsk> precio = new ArrayList <BidAsk>();
			for(int i=0; i < bid.size(); i++)
			{
				if(Par.convertirPar(currency.get(i)) == null)
				{
					continue;
				}
				BidAsk actual = new BidAsk(bid.get(i), ask.get(i), Par.convertirPar(currency.get(i)));
				precio.add(actual);
			}
			dailyOCR.preciosActuales = precio;
			return nuevasSenales;
		}
		catch(Exception e)
		{
			Error.agregar("Error leyendo las senales de DailyFX " + e.getMessage());
			throw(new RuntimeException("Error de lectura"));
		}
	}

	protected void procesar(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				Estrategia actual = darEstrategia(senal.estrategia);
				Senal afectada = null;
				if((afectada = actual.tienePar(senal.par)) != null)
				{
					if(senal.compra != afectada.compra)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes, 0), afectada, false);
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
					}
					if(afectada.numeroLotes > senal.numeroLotes)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes - senal.numeroLotes, 0), afectada, false);
					}
				}
				else
				{
					actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
				}
			}
			for(Estrategia actual : estrategias)
			{
				Thread.sleep(1000);
				synchronized(actual.senales)
				{
					for(int i = 0; i < actual.senales.size(); i++)
					{
						Senal senal = null;
						try
						{
							senal = actual.senales.get(i);
						}
						catch(Exception e)
						{
							break;
						}
						boolean encontrada = false;
						for(Senal nueva : senalesLeidas)
						{
							if(actual.id.equals(nueva.estrategia) && senal.par.equals(nueva.par))
							{
								encontrada = true;
								break;
							}
						}
						if(!encontrada)
						{
							if(!senal.manual)
								actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal, false);
							if(senal.manual && senal.numeroLotes == 0)
							{
								actual.senales.remove(senal);
								i = -1;
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + ": Error al procesar se�ales de dailyFX.");
		}
	}

	public void persistir() 
	{
		breakout1.escribir();
		breakout2.escribir();
		range1.escribir();
		range2.escribir();
		momentum1.escribir();
		momentum2.escribir();
	}

	public Estrategia darEstrategia(IdEstrategia id)
	{
		switch(id)
		{
			case BREAKOUT1 : return breakout1;
			case BREAKOUT2 : return breakout2;
			case RANGE1 : return range1;
			case RANGE2 : return range2;
			case MOMENTUM1 : return momentum1;
			case MOMENTUM2 : return momentum2;
			default : return null;
		}
	}
}