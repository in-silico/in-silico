package implementacion.genetico;

import genericos.genetico.Cromosoma;
import genericos.genetico.Genetico;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JPanel;

public class CromosomaPaquete implements Cromosoma
{
	/** 
	 * peso: es un arreglo que indica en cada una de sus posiciones el peso de cada uno de los elementos
	 */
	static int peso [] = {20, 10, 15, 50, 30, 25, 35, 40};
	
	/** 
	  * utilidad: es un arreglo que asigna un valor a cada elemento seg�n la utilidad que proporciona
	  */
    static int utilidad [] = {10, 4, 8, 22, 16, 13, 17, 20};
    
    static int Mactual [] = new int [peso.length];
    
    static Grafico g;
    
	public static FramePrincipal principal;
    
	/** 
	 * pesoMaximo: es el peso m�ximo que puede cargar la mochila
	 */
    static int pesoMaximo = 500;
    
	/** 
	 * FactorMutacion: es el factor de mutaci�n dado en porcentaje
	 */
    static double FactorMutacion = 0.1;
    
    int cromosomaPaquete;
    
    public CromosomaPaquete(int n)
    {
    	cromosomaPaquete = n;
    }
    
    /**
     * Retorna un numero al azar entre a y b, inclusivos
     * @param a: limite inferior
     * @param b: limite superior
     * @return azar: numero elegido al azar entre a y b, inclusivos
     */
    public static int azar(int a, int b)
    {
            return Genetico.generator.nextInt(b - a + 1) + a;
    }
    
	public Cromosoma cruzar(Cromosoma otro)
	{
		int hijo;
		int c1 = cromosomaPaquete;
		int c2 = ((CromosomaPaquete) otro).cromosomaPaquete;
		do
		{
			hijo = 0;
			for(int i = 0; i < 8; i++)
			{
				// Representa la parte i de la soluci�n c1
				int d1 = (c1 >> i * 4) & 15;
				
				// Representa la parte i de la soluci�n c2
				int d2 = (c2 >> i * 4) & 15;
				
				// Selecciona la parte i de la nueva soluci�n,
				// tomando bien sea d1 o d2.
				if(azar(0, 1) == 0)
				{
					hijo |= d1 << i * 4;
				}
				else
				{
					hijo |= d2 << i * 4;
				}
			}
			
			// Muta aleatoriamente un solo bit
			if(azar(0, 1000) < FactorMutacion * 10)
			{
				int a = azar(0, 31);
				if(((hijo >> a) & 1) == 1)
				{
					hijo -= 1 << a;
				}
				else
				{
					hijo += 1 << a;
				}
			}
		}
		while(!validar(hijo));
		return new CromosomaPaquete(hijo);
     }
	
	public int numeroDescendientes(double media) 
	{
		return (int) (darAptitud() * 64 / media);
	}
	
	public void establecerMejorActual(Cromosoma mejorSolucion) 
	{
		System.out.println(mejorSolucion);
		g.Mactual = Mactual;
	}
     
	public double darAptitud()
	{
		int aptitud = 0;
		for(int i = 0; i < 8; i++)
		{
			int nActual = cromosomaPaquete >> i * 4;
			nActual &= 15;
			aptitud += utilidad[i] * nActual;
		}
		return aptitud;
     }
     
     /**
      * Valida la soluci�n entregada; es decir, dice si el n�mero de 
      * elementos que sugiere la soluci�n, se puede llevar en la mochila, 
      * de acuerdo a su capacidad definida en pesoMaximo
      * @param solucion: la solucion a probar
      * @return booleano: true si es valida la soluci�n, false si no lo es
      */
	public static boolean validar(int solucion)
	{
		int ap = 0;
		for(int i = 0; i < 8; i++)
		{
			int nActual = solucion >> i * 4;
			nActual &= 15;
			ap += peso[i] * nActual;
			if(ap > pesoMaximo)
				return false;
		}
		return ap <= pesoMaximo;
	}

	/**
	 * Genera una poblacion inicial con 64 cromosomas escogidos al azar
	 * con la unica restricci�n de que deben ser validos.
	 * @return arregloCromosomas: arreglo con los 64 cromosomas iniciales
	 */
	public static Cromosoma[] poblacionInicial()
	{
		Cromosoma[] resultado = new Cromosoma[64];
		for(int i = 0; i < 64; i++)
		{
			int posible = 0;
			do
			{
				posible = azar(0, Integer.MAX_VALUE - 1);
			}
			while(!CromosomaPaquete.validar(posible));
			resultado[i] = new CromosomaPaquete(posible);
		}
		return resultado;
	}
	    
	/**
	 * M�todo que imprime el n�mero de soluciones que hay con una utilidad 
	 * mayor o igual a un numero dado. Con 266 (la utilidad mas alta posible, 
	 * imprime que hay 15 soluciones).
	 * nota: se demora bastante porque revisa todas las posibilidades.
	 * @param numero: Es un entero que representa una utilidad
	 */
	public void imprimirMejores(int numero)
	{
		int cuenta = 0;
		for(int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++)
		{
			if(CromosomaPaquete.validar(i) && new CromosomaPaquete(i).darAptitud() >= numero)
			{
				cuenta++;
			}
		}
		System.out.println(cuenta);
	}
	    
	@Override
	public int compareTo(Cromosoma otro)
	{
		return new Double(darAptitud()).compareTo(((CromosomaPaquete) otro).darAptitud());
	}
	
	@Override
	public String toString()
	{
		String acumulado = "";
		for(int i = 0; i < 8; i++)                 
		{                         
			int nActual = cromosomaPaquete >> i * 4;  
			nActual &= 15;                 
			acumulado += (" " +  (char)('a' + i) + ":" + nActual);
			Mactual[i] = nActual;
		}         
		acumulado += " Aptitud: " + darAptitud();
		return acumulado;
	}
	
	public static void main(String[] args)
	{
	}
	
	public static class FramePrincipal extends JApplet
	{
		private static final long serialVersionUID = 4596205343676591069L;
		private static PanelInicial panelInicial;
		
		public Image darImagen(String imagen)
		{
//			try {
//			    Image img = getImage(getDocumentBase(), imagen);
//			    System.out.print(getDocumentBase());
//				MediaTracker mt = new MediaTracker(this);
//				mt.addImage(img,0);
//				mt.waitForID(0);
//				return img;
				try {
					return ImageIO.read(new File("C:\\sga\\Proyectos\\" + imagen));
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
//			} catch (InterruptedException e) {
//				return null;
//			}
		}
		public void init()
		{
			principal = this;
			setSize(new Dimension(1200,760));
			panelInicial = new PanelInicial();
			setContentPane(panelInicial);
		}
		
		public void iniciar() 
		{
			g = new Grafico();
			g.pesos = peso;
			g.utilidades = utilidad;
			add(g);
			JPanel nuevo = new JPanel();
			nuevo.add(g);
			setContentPane(nuevo);
			resize(new Dimension(1200,761));
			new Thread(new Runnable()
									{

										@Override
										public void run() 
										{
											peso[0] = panelInicial.tren.jSlider.getValue();
											utilidad[0] = panelInicial.tren.jSlider1.getValue();
											peso[1] = panelInicial.oso.jSlider.getValue();
											utilidad[1] = panelInicial.oso.jSlider1.getValue();
											peso[2] = panelInicial.libro.jSlider.getValue();
											utilidad[2] = panelInicial.libro.jSlider1.getValue();
											peso[3] = panelInicial.conejita.jSlider.getValue();
											utilidad[3] = panelInicial.conejita.jSlider1.getValue();
											peso[4] = panelInicial.xbox.jSlider.getValue();
											utilidad[4] = panelInicial.xbox.jSlider1.getValue();
											peso[5] = panelInicial.portatil.jSlider.getValue();
											utilidad[5] = panelInicial.portatil.jSlider1.getValue();
											peso[6] = panelInicial.bicicleta.jSlider.getValue();
											utilidad[6] = panelInicial.bicicleta.jSlider1.getValue();
											peso[7] = panelInicial.elefante.jSlider.getValue();
											utilidad[7] = panelInicial.elefante.jSlider1.getValue();
											Cromosoma[] poblacionInicial = poblacionInicial();
											Genetico genetico = new Genetico();
											genetico.solucionar(poblacionInicial, 10000);
										}			
									}).start();
			}
		}
	}


