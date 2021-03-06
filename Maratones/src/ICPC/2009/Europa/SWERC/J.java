import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class J
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
	    	System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		public String next()
		{

			while(st == null || !st.hasMoreTokens())
			{
				try { st = new StringTokenizer(br.readLine()); }
				catch(Exception e) { throw new RuntimeException(); }
			}
			return st.nextToken();
		}

		public int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		public double nextDouble()
		{
			return Double.parseDouble(next());
		}
		
		public String nextLine()
		{
			st = null;
			try { return br.readLine(); }
			catch(Exception e) { throw new RuntimeException(); }
		}
		
		public boolean endLine()
		{
			try 
			{
				String next = br.readLine();
				while(next != null && next.trim().isEmpty())
					next = br.readLine();
				if(next == null)
					return true;
				st = new StringTokenizer(next);
				return st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}
	
	static boolean leq(int a1, int a2,   int b1, int b2) 
	{
		return(a1 < b1 || a1 == b1 && a2 <= b2);
	}             

	static boolean leq(int a1, int a2, int a3,   int b1, int b2, int b3) 
	{
		return(a1 < b1 || a1 == b1 && leq(a2,a3, b2,b3));
	}

	static int[] c;

	static void radixPass(int[] a, int[] b, int[] r, int n, int K, int delta)
	{
		if(c == null || c.length != K + 1) c = new int[K + 1];
		for(int i = 0;  i <= K;  i++) c[i] = 0;
		for(int i = 0;  i < n;  i++) c[r[a[i] + delta]]++;
		for(int i = 0, sum = 0;  i <= K;  i++) 
		{
			int t = c[i];
			c[i] = sum;
			sum += t;
		}
		for (int i = 0;  i < n;  i++) b[c[r[a[i] + delta]]++] = a[i];
	}

	// find the suffix array SA of s[0..n-1] in {1..K}^n
	// require s[n]=s[n+1]=s[n+2]=0, n>=2
	static void suffixArray(int[] s, int[] SA, int n, int K) 
	{
		int n0=(n+2)/3, n1=(n+1)/3, n2=n/3, n02=n0+n2;
		int[] s12  = new int[n02 + 3];  s12[n02] = s12[n02+1] = s12[n02+2] = 0;
		int[] SA12 = new int[n02 + 3]; SA12[n02]=SA12[n02+1]=SA12[n02+2]=0;
		int[] s0   = new int[n0];
		int[] SA0  = new int[n0];
		for (int i=0, j=0; i < n + (n0 - n1); i++) if (i % 3 != 0) s12[j++] = i;
		radixPass(s12, SA12, s, n02, K, 2);
		radixPass(SA12, s12 , s, n02, K, 1);
		radixPass(s12, SA12, s, n02, K, 0);
		int name = 0, c0 = -1, c1 = -1, c2 = -1;
		for(int i = 0; i < n02; i++) 
		{
			if(s[SA12[i]] != c0 || s[SA12[i] + 1] != c1 || s[SA12[i] + 2] != c2) 
			{
				name++;
				c0 = s[SA12[i]];
				c1 = s[SA12[i] + 1];
				c2 = s[SA12[i] + 2];
			}
			if(SA12[i] % 3 == 1) s12[SA12[i] / 3] = name;
			else s12[SA12[i] / 3 + n0] = name;
		}
		if (name < n02) 
		{
			suffixArray(s12, SA12, n02, name);
			for (int i = 0; i < n02; i++) s12[SA12[i]] = i + 1;
		} 
		else for(int i = 0; i < n02; i++) SA12[s12[i] - 1] = i;
		for (int i = 0, j = 0; i < n02; i++) if (SA12[i] < n0) s0[j++] = 3 * SA12[i];
		radixPass(s0, SA0, s, n0, K, 0);
		for(int p = 0,  t = n0 - n1,  k = 0;  k < n;  k++) 
		{
			int i = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
			int j = SA0[p];
			if (SA12[t] < n0 ? leq(s[i], s12[SA12[t] + n0], s[j], s12[j/3]) :
				leq(s[i],s[i+1],s12[SA12[t]-n0+1], s[j],s[j+1],s12[j/3+n0]))
			{
				SA[k] = i;
				t++;
				if (t == n02) for(k++; p < n0; p++, k++) SA[k] = SA0[p]; 
			} 
			else 
			{
				SA[k] = j;
				p++;
				if(p == n0) for(k++; t < n02; t++, k++) SA[k] = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2); 
			}
		}
	}

	static class SuffixArray
	{
		int n;
		int[] g, b, sa, lcp, rmq, aux;
		char[] t;

		SuffixArray(String entrada)
		{
			int tamMax = entrada.length() + 1;
			g = new int[tamMax];
			b = new int[tamMax];
			sa = new int[tamMax];
			lcp = new int[tamMax];
			aux = new int[tamMax + 3];
			int logn = 1;
			int n = tamMax;
			for (int k = 1; k < n; k *= 2) ++logn;
			rmq = new int[n * logn];
			buildSA(entrada);
		}

		void buildSA(String sas)
		{
			n = sas.length() + 1;
			t = sas.toCharArray();
			for(int i = 0; i < n - 1; i++)
				aux[i] = t[i];
			aux[n - 1] = '$';
			aux[n] = aux[n + 1] = aux[n + 2] = 0;
			suffixArray(aux, sa, n, 128);
			n--;
			buildLCP();
			buildRMQ();
		}

		void buildLCP()
		{
			int[] a = sa;
			int h = 0;
			for(int i = 0; i < n + 1; i++) b[a[i]] = i;
			for(int i = 0; i < n + 1; i++) 
			{
				if (b[i] != 0)
				{
					for(int j = a[b[i] - 1]; j + h < n && i + h < n && t[j + h] == t[i + h]; ++h);
					lcp[b[i]] = h;
				} 
				else lcp[b[i]] = -1;
				if(h > 0) --h;
			}
		}

		void buildRMQ() 
		{
			int temp = n;
			int n = temp + 1;
			int[] b = rmq; 
			System.arraycopy(lcp, 0, b, 0, n);
			int delta = 0;
			for(int k = 1; k < n; k *= 2) 
			{
				System.arraycopy(b, delta, b, n + delta, n);
				delta += n;
				for(int i = 0; i < n - k; i++) b[i + delta] = Math.min(b[i + delta], b[i + k + delta]);
			}
		}

		// Naive matching O(m log n)
		int findN(String o) 
		{
			int temp = n;
			int n = temp + 1;
			int a = 0, b = n;
			String este = new String(t);
			while (a < b) 
			{
				int c = (a + b) / 2;
				String nuevo = este.substring(sa[c]);
				if(nuevo.length() > o.length()) nuevo = nuevo.substring(0, o.length());
				if(nuevo.compareTo(o) < 0) a = c + 1; 
				else b = c;
			}
			String nuevo = este.substring(sa[a]);
			if(nuevo.length() > o.length()) nuevo = nuevo.substring(0, o.length());
			return nuevo.compareTo(o) == 0 ? sa[a] : -1;
		}

		int minimum(int x, int y) 
		{
			int temp = n;
			int n = temp + 1;
			int z = y - x, k = 0, e = 1, s;
			s = (((z & 0xffff0000) != 0) ? 1 : 0) << 4; z >>= s; e <<= s; k |= s;
			s = (((z & 0x0000ff00) != 0) ? 1 : 0) << 3; z >>= s; e <<= s; k |= s;
			s = (((z & 0x000000f0) != 0) ? 1 : 0) << 2; z >>= s; e <<= s; k |= s;
			s = (((z & 0x0000000c) != 0) ? 1 : 0) << 1; z >>= s; e <<= s; k |= s;
			s = (((z & 0x00000002) != 0) ? 1 : 0) << 0; z >>= s; e <<= s; k |= s;
			return Math.min(rmq[x + n * k], rmq[y + n * k - e + 1]);
		}

		// outer LCP computation: O(m - o)
		int computeLCP(char[] t, int n, char[] p, int m, int o, int k) 
		{
			int i = o;
			for (; i < m && k + i < n && p[i] == t[k + i]; ++i);
			return i;
		}

		boolean COMP(int h, int k, int m, char[] p)
		{
			return (h == m || (k + h < n && p[h] < t[k + h]));
		}
		
		// Mamber-Myers's O(m + log n) string matching with LCP/RMQ
		int find(String o) 
		{
			char[] p = o.toCharArray();
			int m = o.length();
			int l = 0, lh = 0, r = n, rh = computeLCP(t, n, p, m, 0, sa[n]);
			if(!COMP(rh, sa[r], m, p)) return -1;
			for(int k = (l + r) / 2; l + 1 < r; k = (l + r) / 2)
			{
				int A = minimum(l + 1, k), B = minimum(k + 1, r);
				if(A >= B) 
				{
					if(lh < A) l = k;
					else if(lh > A)
					{
						r = k;
						rh = A;
					}
					else
					{
						int i = computeLCP(t, n, p, m, A, sa[k]);
						if (COMP(i, sa[k], m, p)) 
						{
							r = k;
							rh = i;
						}
						else
						{
							l = k;
							lh = i;
						}
					}
				}
				else 
				{
					if (rh < B) r = k;
					else if (rh > B) 
					{
						l = k;
						lh = B;
					}
					else
					{
						int i = computeLCP(t, n, p, m, B, sa[k]);
						if(COMP(i, sa[k], m, p))
						{
							r = k;
							rh = i;
						}
						else 
						{
							l = k;
							lh = i;
						}
					}	
				}
			}
			return rh == m ? sa[r] : -1;
		}
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int m = sc.nextInt();
			if(m == 0)
				return;
			String entrada = sc.next();
			SuffixArray sa = new SuffixArray(entrada);
			int mejor = 0;
			int mejorPos = 0;
			int[] ssa = sa.sa;
			int limite = entrada.length() - m;
			int[] lcp = new int[limite + 2];
			if(m == 1)
			{
				System.out.println(entrada.length() + " 0");
				continue;
			}
			for(int i = 1; i <= limite + 1; i++)
			{
				lcp[i] = sa.minimum(i + 1, i + m - 1);
				if(lcp[i] > mejor)
					mejor = lcp[i];
			}
			for(int i = 1; i <= limite + 1; i++)
			{
				int respuesta = lcp[i];
				int limite2 = i + m;
				if(respuesta == mejor)
				{
					for(int j = i; j < limite2; j++)
						if(ssa[j] > mejorPos)
							mejorPos = ssa[j];
				}
			}
			if(mejor == 0)
				System.out.println("none");
			else
				System.out.println(mejor + " " + mejorPos);
		}
	}
}