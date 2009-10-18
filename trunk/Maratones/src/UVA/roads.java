package UVA;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class roads
{
	public static class FibonacciHeap {
	  /**
	   * Points to the minimum node in the heap.
	   */
	  private Node m_min;

	  /**
	   * Number of nodes in the heap.
	   */
	  private int m_n;

	  /**
	   * Constructs a FibonacciHeap object that contains no elements.
	   */
	  public FibonacciHeap() {
	  } // FibonacciHeap

	  /**
	   * Tests if the Fibonacci heap is empty or not. Returns true if the heap is
	   * empty, false otherwise.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @return true if the heap is empty, false otherwise
	   */
	  public boolean isEmpty() {
	    return m_min == null;
	  }


	  // isEmpty

	  /**
	   * Removes all elements from this heap.
	   */
	  public void clear() {
	    m_min = null;
	    m_n = 0;
	  }


	  // clear

	  /**
	   * Decreases the key value for a heap node, given the new value to take on.
	   * The structure of the heap may be changed and will not be consolidated.
	   * <p/>
	   * <p/>
	   * Running time: O(1) amortized
	   * </p>
	   *
	   * @param x node to decrease the key of
	   * @param k new key value for node x
	   * @throws IllegalArgumentException Thrown if k is larger than x.key
	   *                                  value.
	   */
	  public void decreaseKey(Node x, int k) {
	    if (k > x.m_key) {
	      throw new IllegalArgumentException("decreaseKey() got larger key value");
	    }

	    x.m_key = k;

	    Node y = x.m_parent;

	    if ((y != null) && (x.m_key < y.m_key)) {
	      cut(x, y);
	      cascadingCut(y);
	    }

	    if (x.m_key < m_min.m_key) {
	      m_min = x;
	    }
	  }


	  // decreaseKey

	  /**
	   * Deletes a node from the heap given the reference to the node. The trees
	   * in the heap will be consolidated, if necessary. This operation may fail
	   * to remove the correct element if there are nodes with key value
	   * -Infinity.
	   * <p/>
	   * <p/>
	   * Running time: O(log n) amortized
	   * </p>
	   *
	   * @param x node to remove from heap
	   */
	  public void delete(Node x) {
	    // make x as small as possible
	    decreaseKey(x, Integer.MIN_VALUE);

	    // remove the smallest, which decreases n also
	    removeMin();
	  }


	  // delete

	  /**
	   * Inserts a new data element into the heap. No heap consolidation is
	   * performed at this time, the new node is simply inserted into the root
	   * list of this heap.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @param node new node to insert into heap
	   * @param key  key value associated with data object
	   */
	  public void insert(Node node, int key) {
	    node.m_key = key;

	    // concatenate node into min list
	    if (m_min != null) {
	      node.m_left = m_min;
	      node.m_right = m_min.m_right;
	      m_min.m_right = node;
	      node.m_right.m_left = node;

	      if (key < m_min.m_key) {
	        m_min = node;
	      }
	    } else {
	      m_min = node;
	    }

	    m_n++;
	  }


	  // insert

	  /**
	   * Returns the smallest element in the heap. This smallest element is the
	   * one with the minimum key value.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @return heap node with the smallest key
	   */
	  public Node min() {
	    return m_min;
	  }


	  // min

	  /**
	   * Removes the smallest element from the heap. This will cause the trees in
	   * the heap to be consolidated, if necessary.
	   * <p/>
	   * <p/>
	   * Running time: O(log n) amortized
	   * </p>
	   *
	   * @return node with the smallest key
	   */
	  public Node removeMin() {
	    Node z = m_min;

	    if (z != null) {
	      int numKids = z.m_degree;
	      Node x = z.m_child;
	      Node tempRight;

	      // for each child of z do...
	      while (numKids > 0) {
	        tempRight = x.m_right;

	        // remove x from child list
	        x.m_left.m_right = x.m_right;
	        x.m_right.m_left = x.m_left;

	        // add x to root list of heap
	        x.m_left = m_min;
	        x.m_right = m_min.m_right;
	        m_min.m_right = x;
	        x.m_right.m_left = x;

	        // set parent[x] to null
	        x.m_parent = null;
	        x = tempRight;
	        numKids--;
	      }

	      // remove z from root list of heap
	      z.m_left.m_right = z.m_right;
	      z.m_right.m_left = z.m_left;

	      if (z == z.m_right) {
	        m_min = null;
	      } else {
	        m_min = z.m_right;
	        consolidate();
	      }

	      // decrement size of heap
	      m_n--;
	    }

	    return z;
	  }


	  // removeMin

	  /**
	   * Returns the size of the heap which is measured in the number of elements
	   * contained in the heap.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @return number of elements in the heap
	   */
	  public int size() {
	    return m_n;
	  }


	  // size

	  /**
	   * Joins two Fibonacci heaps into a new one. No heap consolidation is
	   * performed at this time. The two root lists are simply joined together.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @param h1 first heap
	   * @param h2 second heap
	   * @return new heap containing h1 and h2
	   */
	  public static FibonacciHeap union(FibonacciHeap h1, FibonacciHeap h2) {
	    FibonacciHeap h = new FibonacciHeap();

	    if ((h1 != null) && (h2 != null)) {
	      h.m_min = h1.m_min;

	      if (h.m_min != null) {
	        if (h2.m_min != null) {
	          h.m_min.m_right.m_left = h2.m_min.m_left;
	          h2.m_min.m_left.m_right = h.m_min.m_right;
	          h.m_min.m_right = h2.m_min;
	          h2.m_min.m_left = h.m_min;

	          if (h2.m_min.m_key < h1.m_min.m_key) {
	            h.m_min = h2.m_min;
	          }
	        }
	      } else {
	        h.m_min = h2.m_min;
	      }

	      h.m_n = h1.m_n + h2.m_n;
	    }

	    return h;
	  }


	  // union

	  /**
	   * Performs a cascading cut operation. This cuts y from its parent and then
	   * does the same for its parent, and so on up the tree.
	   * <p/>
	   * <p/>
	   * Running time: O(log n); O(1) excluding the recursion
	   * </p>
	   *
	   * @param y node to perform cascading cut on
	   */
	  protected void cascadingCut(Node y) {
	    Node z = y.m_parent;

	    // if there's a parent...
	    if (z != null) {
	      // if y is unmarked, set it marked
	      if (!y.m_mark) {
	        y.m_mark = true;
	      } else {
	        // it's marked, cut it from parent
	        cut(y, z);

	        // cut its parent as well
	        cascadingCut(z);
	      }
	    }
	  }


	  // cascadingCut

	  /**
	   * Consolidates the trees in the heap by joining trees of equal degree
	   * until there are no more trees of equal degree in the root list.
	   * <p/>
	   * <p/>
	   * Running time: O(log n) amortized
	   * </p>
	   */
	  protected void consolidate() {
	    int arraySize = 32; // log en base 2 del numero maximo de elementos que puede tener la cola
	    Node[] array = new Node[arraySize];

	    // Initialize degree array
	    for (int i = 0; i < arraySize; i++) {
	      array[i] = null;
	    }

	    // Find the number of root nodes.
	    int numRoots = 0;
	    Node x = m_min;

	    if (x != null) {
	      numRoots++;
	      x = x.m_right;

	      while (x != m_min) {
	        numRoots++;
	        x = x.m_right;
	      }
	    }

	    // For each node in root list do...
	    while (numRoots > 0) {
	      // Access this node's degree..
	      int d = x.m_degree;
	      Node next = x.m_right;
	      // ..and see if there's another of the same degree.
	      while (array[d] != null) {
	        // There is, make one of the nodes a child of the other.
	        Node y = array[d];

	        // Do this based on the key value.
	        if (x.m_key > y.m_key) {
	          Node temp = y;
	          y = x;
	          x = temp;
	        }

	        // Node y disappears from root list.
	        link(y, x);

	        // We've handled this degree, go to next one.
	        array[d] = null;
	        d++;
	      }

	      // Save this node for later when we might encounter another
	      // of the same degree.
	      array[d] = x;

	      // Move forward through list.
	      x = next;
	      numRoots--;
	    }

	    // Set min to null (effectively losing the root list) and
	    // reconstruct the root list from the array entries in array[].
	    m_min = null;

	    for (int i = 0; i < arraySize; i++) {
	      if (array[i] != null) {
	        // We've got a live one, add it to root list.
	        if (m_min != null) {
	          // First remove node from root list.
	          array[i].m_left.m_right = array[i].m_right;
	          array[i].m_right.m_left = array[i].m_left;

	          // Now add to root list, again.
	          array[i].m_left = m_min;
	          array[i].m_right = m_min.m_right;
	          m_min.m_right = array[i];
	          array[i].m_right.m_left = array[i];

	          // Check if this is a new min.
	          if (array[i].m_key < m_min.m_key) {
	            m_min = array[i];
	          }
	        } else {
	          m_min = array[i];
	        }
	      }
	    }
	  }


	  // consolidate

	  /**
	   * The reverse of the link operation: removes x from the child list of y.
	   * This method assumes that min is non-null.
	   * <p/>
	   * <p/>
	   * Running time: O(1)
	   * </p>
	   *
	   * @param x child of y to be removed from y's child list
	   * @param y parent of x about to lose a child
	   */
	  protected void cut(Node x, Node y) {
	    // remove x from childlist of y and decrement degree[y]
	    x.m_left.m_right = x.m_right;
	    x.m_right.m_left = x.m_left;
	    y.m_degree--;

	    // reset y.child if necessary
	    if (y.m_child == x) {
	      y.m_child = x.m_right;
	    }

	    if (y.m_degree == 0) {
	      y.m_child = null;
	    }

	    // add x to root list of heap
	    x.m_left = m_min;
	    x.m_right = m_min.m_right;
	    m_min.m_right = x;
	    x.m_right.m_left = x;

	    // set parent[x] to nil
	    x.m_parent = null;

	    // set mark[x] to false
	    x.m_mark = false;
	  }


	  // cut

	  /**
	   * Make node y a child of node x.
	   * <p/>
	   * <p/>
	   * Running time: O(1) actual
	   * </p>
	   *
	   * @param y node to become child
	   * @param x node to become parent
	   */
	  protected void link(Node y, Node x) {
	    // remove y from root list of heap
	    y.m_left.m_right = y.m_right;
	    y.m_right.m_left = y.m_left;

	    // make y a child of x
	    y.m_parent = x;

	    if (x.m_child == null) {
	      x.m_child = y;
	      y.m_right = y;
	      y.m_left = y;
	    } else {
	      y.m_left = x.m_child;
	      y.m_right = x.m_child.m_right;
	      x.m_child.m_right = y;
	      y.m_right.m_left = y;
	    }

	    // increase degree[x]
	    x.m_degree++;

	    // set mark[y] false
	    y.m_mark = false;
	  }

	  // link


	  // Node
	}

	  /**
	   * Implements a node of the Fibonacci heap. It holds the information
	   * necessary for maintaining the structure of the heap. It also holds the
	   * reference to the key value (which is used to determine the heap
	   * structure).  Additional Node data should be stored in a subclass.
	   *
	   * @author Nathan Fiedler
	   */
	  public static class Node {
	    /**
	     * first child node
	     */
	    Node m_child;

	    /**
	     * left sibling node
	     */
	    Node m_left;

	    /**
	     * parent node
	     */
	    Node m_parent;

	    /**
	     * right sibling node
	     */
	    Node m_right;

	    /**
	     * true if this node has had a child removed since this node was added
	     * to its parent
	     */
	    boolean m_mark;

	    /**
	     * key value for this node
	     */
	    int m_key;

	    /**
	     * number of children of this node (does not count grandchildren)
	     */
	    int m_degree;

	    /**
	     * Default constructor.  Initializes the right and left pointers,
	     * making this a circular doubly-linked list.
	     *
	     * @param key initial key for node
	     */
	    public Node(int key) {
	      m_right = this;
	      m_left = this;
	      m_key = key;
	    }

	    /**
	     * Obtain the key for this node.
	     *
	     * @return the key
	     */
	    public final int getKey() {
	      return m_key;
	    }

	    /**
	     * Return the string representation of this object.
	     *
	     * @return string representing this object
	     */
	    public String toString() {
	      if (true) {
	        return Double.toString(m_key);
	      } else {
	        StringBuffer buf = new StringBuffer();
	        buf.append("Node=[parent = ");

	        if (m_parent != null) {
	          buf.append(Double.toString(m_parent.m_key));
	        } else {
	          buf.append("---");
	        }

	        buf.append(", key = ");
	        buf.append(Double.toString(m_key));
	        buf.append(", degree = ");
	        buf.append(Integer.toString(m_degree));
	        buf.append(", right = ");

	        if (m_right != null) {
	          buf.append(Double.toString(m_right.m_key));
	        } else {
	          buf.append("---");
	        }

	        buf.append(", left = ");

	        if (m_left != null) {
	          buf.append(Double.toString(m_left.m_key));
	        } else {
	          buf.append("---");
	        }

	        buf.append(", child = ");

	        if (m_child != null) {
	          buf.append(Double.toString(m_child.m_key));
	        } else {
	          buf.append("---");
	        }

	        buf.append(']');

	        return buf.toString();
	      }
	    }

	    // toString
	  }
	// FibonacciHeap



	@SuppressWarnings("unchecked")
	public static void main(String [] args) throws IOException
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			String linea = sc.readLine();
			String [] nv = linea.split(" ");
			int nodos = Integer.parseInt(nv[0]);
			int vertices = Integer.parseInt(nv[1]);
			if(nodos == 0 && vertices == 0)
				return;
			FibonacciHeap pila = new FibonacciHeap();
			vertice primero = new vertice(0);
			primero.n = 0;
			pila.insert(primero, 0);
			vertice [] listaV = new vertice[nodos];
			listaV[0] = primero;
			for(int i = 1; i < nodos; i++)
			{
				vertice actual = new vertice(0);
				actual.n = i;
				pila.insert(actual, Integer.MAX_VALUE);
				listaV[i] = actual;
			}
			LinkedList <Nodo> [] listaA = new LinkedList [nodos];
			int acum = 0;
			for(int i = 0; i < vertices; i++)
			{
				linea = sc.readLine();
				int s = linea.indexOf(' ');
				int s1 = linea.lastIndexOf(' ');
				int a = Integer.parseInt(linea.substring(0, s));
				int b = Integer.parseInt(linea.substring(s + 1, s1));
				int peso = Integer.parseInt(linea.substring(s1 + 1));
				if(listaA[a] == null)
					listaA[a] = new LinkedList <Nodo> ();
				if(listaA[b] == null)
					listaA[b] = new LinkedList <Nodo> ();
				listaA[a].add(new Nodo(b, peso));
				listaA[b].add(new Nodo(a, peso));
				acum += peso;
			}
			while(!pila.isEmpty())
			{
				vertice latest_addition = (vertice) pila.removeMin();
				if(!latest_addition.is)
					continue;
				latest_addition.is = false;
				for(Nodo n : listaA[latest_addition.n])
				{
					if(listaV[n.otro].is && n.peso < listaV[n.otro].m_key)
					{
						listaV[n.otro].padre = latest_addition;
						pila.decreaseKey(listaV[n.otro], n.peso);
					}
				}
			}
			int acum2 = 0;
			for(int i = 0; i < nodos; i++)
			{
				acum2 += listaV[i].m_key;
			}
			System.out.println(acum - acum2);
		}
	}

	
	static class vertice extends Node
	{
		public vertice(int key) 
		{
			super(key);
		}

		int n;
		vertice padre = null;
		boolean is = true;		
	}
	
	static class Nodo
	{
		int otro;
		int peso;
		
		public Nodo(int o, int p)
		{
			otro = o;
			peso = p;
		}
	}
}