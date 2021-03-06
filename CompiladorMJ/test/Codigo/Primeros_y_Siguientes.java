import java.util.Scanner;


public class Primeros_y_Siguientes 
{
	public static void main(String [] args)
	{
		Solucion.solucionar();
	}
}

class Solucion
{
	static void solucionar()
	{
		int nReglas;
		Regla[] reglas;
		int i = 0;
		ConjuntoSimbolos terminales = new ConjuntoSimbolos();
		ConjuntoSimbolos noTerminales = new ConjuntoSimbolos();
		Simbolo inicio = null;
		Simbolo finDeEntrada = new Terminal();
		finDeEntrada.iniciar(36);
		terminales.iniciar(20);
		noTerminales.iniciar(20);
		Library.println("Algoritmo para calcular los simbolos PRIMERO/SIGUIENTE");
		Library.println("");
		Library.println("Nota: para que funcione cada terminal y no terminal debe ser de longitud 1,");
		Library.println("los no terminales deben ser letras (A-Z) mayusculas, el primer simbolo debe");
		Library.println("ser el simbolo de inicio y no se debe usar el simbolo $ (fin de entrada)");
		Library.println("");
		Library.print("Ingrese el numero de reglas: ");
		nReglas = Library.readi();
		Library.println("");
		reglas = new Regla[nReglas];
		while(i < nReglas)
		{
			String nt;
			int[] ant;
			String c;
			int[] cuerpo;
			Simbolo noTerminal;
			Regla actual = new Regla();
			Library.print("Ingrese el no terminal de la regla " + Library.itos(i + 1) + ": ");
			nt = Library.readln();
			Library.println("");
			ant = Library.stoa(nt);
			noTerminal = noTerminales.buscarAgregar(ant[0], false);
			Library.print("Ingrese el cuerpo de la regla " + Library.itos(i + 1) + ": ");
			c = Library.readln();
			Library.println("");
			cuerpo = Library.stoa(c);
			actual.iniciarRegla(noTerminal, cuerpo, noTerminales, terminales);
			reglas[i] = actual;
			if(i == 0)
				inicio = noTerminal;
			i = i + 1;
		}
		inicio.siguiente.agregar(finDeEntrada);
		while(true)
		{
			int m = 0;
			boolean cambio = false;
			while(m < nReglas)
			{
				Regla actual = reglas[m];
				i = 0;
				if(actual.esAnulable() && !actual.noTerminal.anulable)
				{
					cambio = true;
					actual.noTerminal.anulable = true;
				}
				while(i < actual.cuerpo.length)
				{
					int j = i + 1;
					if(i == 0 || actual.subRegla(0, i - 1, noTerminales, terminales).esAnulable())
					{
						boolean nuevo = actual.noTerminal.primero.unir(actual.cuerpo[i].primero);
						cambio = cambio || nuevo;
					}
					if(i == actual.cuerpo.length - 1 || actual.subRegla(i + 1, actual.cuerpo.length - 1, noTerminales, terminales).esAnulable())
					{
						boolean nuevo = actual.cuerpo[i].siguiente.unir(actual.noTerminal.siguiente);
						cambio = cambio || nuevo;
					}
					while(j < actual.cuerpo.length)
					{
						if(i == 0 || actual.subRegla(0, i - 1, noTerminales, terminales).esAnulable())
						{
							boolean nuevo = actual.noTerminal.primero.unir(actual.cuerpo[i].primero);
							cambio = cambio || nuevo;
						}
						if(i == actual.cuerpo.length - 1 || actual.subRegla(i + 1, actual.cuerpo.length - 1, noTerminales, terminales).esAnulable())
						{
							boolean nuevo = actual.cuerpo[i].siguiente.unir(actual.noTerminal.siguiente);
							cambio = cambio || nuevo;
						}
						if(i + 1 == j || actual.subRegla(i + 1, j - 1, noTerminales, terminales).esAnulable())
						{
							boolean nuevo = actual.cuerpo[i].siguiente.unir(actual.cuerpo[j].primero);
							cambio = cambio || nuevo;
						}
						j = j + 1;
					}
					i = i + 1;
				}
				m = m + 1;
			}
			if(!cambio)
			{
				break;
			}
		}
		i = 0;
		while(i < noTerminales.tamano)
		{
			Simbolo actual = noTerminales.lista[i];
			int[] a = new int[1];
			a[0] = actual.id;
			Library.println("No terminal " + Library.atos(a) + ":");
			Library.println("Primero: " + actual.primero.toString());
			Library.println("Siguiente: " + actual.siguiente.toString());
			i = i + 1;
		}
	}
}

class Regla
{
	Simbolo noTerminal;
	Simbolo[] cuerpo;
	
	void iniciarRegla(Simbolo noTerminal1, int[] cuerpo1, ConjuntoSimbolos noTerminales, ConjuntoSimbolos terminales)
	{
		int i = 0;
		this.cuerpo = new Simbolo[cuerpo1.length];
		this.noTerminal = noTerminal1;
		while(i < cuerpo1.length)
		{
			Simbolo nuevo;
			if(cuerpo1[i] >= 65 && cuerpo1[i] <= 90)
			{
				nuevo = noTerminales.buscarAgregar(cuerpo1[i], false);
			}
			else
			{
				nuevo = terminales.buscarAgregar(cuerpo1[i], true);
			}
			this.cuerpo[i] = nuevo;
			i = i + 1;
		}
	}
	
	Regla subRegla(int inicio, int fin, ConjuntoSimbolos noTerminales, ConjuntoSimbolos terminales)
	{
		int[] nuevoCuerpo = new int[fin - inicio + 1];
		Regla nueva;
		int i = inicio;
		int j = 0;
		while(i <= fin)
		{
			nuevoCuerpo[j] = cuerpo[i].id;
			i = i + 1;
			j = j + 1;
		}
		nueva = new Regla();
		nueva.iniciarRegla(noTerminal, nuevoCuerpo, noTerminales, terminales);
		return nueva;
	}
	
	boolean esAnulable()
	{
		int i = 0;
		while(i < cuerpo.length)
		{
			if(!cuerpo[i].anulable)
				return false;
			i = i + 1;
		}
		return true;
	}
}

class Simbolo
{
	boolean anulable;
	int id;
	ConjuntoSimbolos primero;
	ConjuntoSimbolos siguiente;
	
	void iniciar(int id1){}
}

class Terminal extends Simbolo
{
	void iniciar(int id1)
	{
		this.id = id1;
		primero = new ConjuntoSimbolos();
		primero.iniciar(1);
		primero.agregar(this);
		siguiente = new ConjuntoSimbolos();
		siguiente.iniciar(0);
		siguiente.esSiguienteT = true;
		anulable = false;
	}
}

class NoTerminal extends Simbolo
{
	void iniciar(int id1)
	{
		this.id = id1;
		primero = new ConjuntoSimbolos();
		primero.iniciar(20);
		siguiente = new ConjuntoSimbolos();
		siguiente.iniciar(20);
		anulable = false;
	}
}

class ConjuntoSimbolos
{
	Simbolo[] lista;
	int capacidad;
	int tamano;
	boolean esSiguienteT;
	
	void iniciar(int capacidadInicial)
	{
		lista = new Simbolo[capacidadInicial];
		tamano = 0;
		capacidad = capacidadInicial;
		esSiguienteT = false;
	}
	
	Simbolo buscarAgregar(int id, boolean esTerminal) 
	{
		int i = 0;
		while(i < tamano)
		{
			if(lista[i].id == id)
				return lista[i];
			i = i + 1;
		}
		if(!esTerminal)
		{
			Simbolo noTerminal;
			noTerminal = new NoTerminal();
			noTerminal.iniciar(id);
			agregar(noTerminal);
			return noTerminal;
		}
		else
		{
			Simbolo terminal;
			terminal = new Terminal();
			terminal.iniciar(id);
			agregar(terminal);
			return terminal;
		}
	}
	
	boolean agregar(Simbolo aAgregar)
	{
		int i = 0;
		while(i < tamano)
		{
			if(lista[i].id == aAgregar.id)
				return false;
			i = i + 1;
		}
		if(tamano + 1 > capacidad)
		{
			int j = 0;
			Simbolo[] listaNueva = new Simbolo[capacidad * 2];
			capacidad = capacidad * 2;
			tamano = tamano + 1;
			while(j < lista.length)
			{
				listaNueva[j] = lista[j];
				j = j + 1;
			}
			listaNueva[j] = aAgregar; 
			lista = listaNueva;
			return true;
		}
		else
		{
			lista[tamano] = aAgregar;
			tamano = tamano + 1;
			return true;
		}
	}
	
	boolean unir(ConjuntoSimbolos otro) 
	{
		int i = 0;
		boolean cambio = false;
		if(esSiguienteT)
			return false;
		while(i < otro.tamano)
		{
			boolean temp = agregar(otro.lista[i]);
			cambio = cambio || temp;
			i = i + 1;
		}
		return cambio;
	}
	
	public String toString()
	{
		int k = 0;
		String este = "";
		while(k < tamano)
		{
			int[] b = new int[1];
			b[0] = lista[k].id;
			if(k != tamano - 1)
			{
				este = este + Library.atos(b) + ", ";
			}
			else
			{
				este = este + Library.atos(b);
			}
			k = k + 1;
		}
		return este;
	}
	
}

class Library
{
	static Scanner sc = new Scanner(System.in);
	static void println(String a){System.out.println(a);}
	static void print(String b){System.out.print(b);}
	static void printi(int c){System.out.print(c);}
	static int readi(){return Integer.parseInt(sc.nextLine());}
	static String readln(){return sc.nextLine();}
	static String itos(int a){return a + "";}
	static int[] stoa(String d){char[] t = d.toCharArray(); int[] r = new int[t.length]; for(int i = 0; i < t.length; i++) r[i] = t[i]; return r;}
	static String atos(int[] e){char[] t = new char[e.length]; for(int i = 0; i < t.length; i++) t[i] = (char) e[i]; return new String(t);}
}