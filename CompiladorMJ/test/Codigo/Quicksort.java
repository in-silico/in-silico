import java.util.Scanner;

public class Quicksort { 
	
	public static void main(String [] args){
    	int i = 0;
    	int [] arreglo;
    	int tam;
    	Quicksort prueba;
    	Library.print("Ingrese el tamano del arreglo: ");
    	tam = Library.readi();
    	Library.println("");
    	arreglo = new int[tam];
    	while(i < tam){
    		Library.print("Ingrese el elemento ");
    		Library.printi(i + 1);
    		Library.print(": ");
    		arreglo[i] = Library.readi();
    		Library.println("");
    		i = i + 1;
    	}
    	prueba = new Quicksort();
    	prueba.quicksort(arreglo, 0, tam - 1);
    	i = 0;
    	Library.println("");
    	Library.println("El vector ordenado es: ");
    	while(i < tam){
    		if(i == tam - 1){
    			Library.printi(arreglo[i]);
    		}
    		else{
    			Library.printi(arreglo[i]);
    			Library.print(" ");
    		}
    		i = i + 1;
    	}
	}
    	
	int partition(int [] a, int low, int high){ 
        int pivot = a[low]; 
        int i = low;
        int j = high;
        int tmp;
        while (true){ 
            while (a[i] < pivot) i = i+1;
            while (a[j] > pivot) j = j-1;
            if (i >= j) break;
            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            i = i + 1;
            j = j - 1;
        }
        return j; 
    }
	
    void quicksort(int [] a, int low, int high){ 
        if (low < high) { 
            int mid = partition(a, low, high); 
            quicksort(a, low, mid); 
            quicksort(a, mid+1, high); 
        } 
    }
}

class Library
{
	static Scanner sc = new Scanner(System.in);
	static void println(String a){System.out.println(a);}
	static void print(String b){System.out.print(b);}
	static void printi(int c){System.out.print(c);}
	static int readi(){return sc.nextInt();}
	static void exit(int a){}
}