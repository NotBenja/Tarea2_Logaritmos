import java.util.List;
import java.util.ArrayList;

/**
 * Clase para las colas de Fibonacci.
 */
public class FibonacciHeap {

  /**
   * Clase para los Nodos de Fibonacci.
   */
  public static final class FNode {
    /** Número de hijos. */
    private int degree = 0;
    /** Devuelve si el nodo está marcado. */
    private boolean isMarked = false;
    /** Elemento siguiente de la cola. */
    private FNode next;
    /** Elemento previo de la cola. */
    private FNode prev;
    /** El padre del arbol, si tiene. */
    private FNode parent;
    /** Hijo del nodo, si tiene. */
    private FNode child;
    /** Distancia al nodo raíz y sus punteros al par que le corresponde. */
    private FPair mElem;
    /** La prioridad, que es equivalente a la distancia. */
    private double priority;

    /**
     * @return El elemento contenido en la cola.
     */
    public FPair getPair() {
      return mElem;
    }

    /**
     * Constructor para un nuevo FNode que contiene el elemento elem,
     * con una cierta prioridad priority.
     *
     * @param newElem El elemento almacenado en la cola.
     * @param newPriority Es la prioridad del elemento almacenado en la cola.
     */
    private FNode(FPair newElem, double newPriority) {
      next = prev = this;
      mElem = newElem;
      priority = newPriority;
    }
  }

  /** Puntero al elemento mínimo de la cola. */
  private FNode mMin = null;

  /** El tamaño de la cola. */
  private int size = 0;

  /**
   * Inserta el elemento value a la cola de Fibonacci con la prioridad indicada en dist del value.
   *
   * @param value El FPair a insertar.
   * @return Un FNode que representa el nuevo elemento del árbol.
   */
  public FNode enqueue(FPair value) {
    double priority = value.getDist();

    // Crea el objeto, que es una cola circular de largo 1.
    FNode result = new FNode(value, priority);

    // Une el singleton con la cola en el árbol.
    mMin = mergeLists(mMin, result);

    // Aumenta el tamaño de la cola.
    ++size;

    // Retorna la referencia al nuevo elemento
    return result;
  }

  /**
   * @return El mínimo elemento de la cola.
   */
  public FNode min() {
    return mMin;
  }

  /**
   * @return Si la cola es vacía.
   */
  public boolean isEmpty() {
    return mMin == null;
  }

  /**
   * @return El número de elementos del heap.
   */
  public int size() {
    return size;
  }

  /**
   * Desencola el mínimo elemento y busca el nuevo mínimo para asignarlo.
   * O(log n)
   */
  public void extractMin() {
    // Como se pierde un elemento, se disminuye el tamaño de la cola.
    --size;

    // Se guarda el minimo dado que se desencolará
    FNode minElem = mMin;

    // Se busca eliminar el mínimo de la lista de raíces.
    // Caso 1:
    // Es el único elemento que hay
    if (mMin.next == mMin) {
      // Seteamos mMin a null
      mMin = null;
    }
    // Caso 2:
    // Reasignamos las referencias de los elementos que apuntan al mínimo, ya que será removido
    // Se reasigna el mínimo.
    else {
      mMin.prev.next = mMin.next;
      mMin.next.prev = mMin.prev;
      mMin = mMin.next;
    }

    // Se quitan las referencias padre de todos los hijos del mínimo.
    if (minElem.child != null) {
      // El primer nodo visitado
      FNode curr = minElem.child;
      do {
        curr.parent = null;

        // Vamos al sgte nodo, y nos detenemos si volvemos al primero.
        curr = curr.next;
      } while (curr != minElem.child);
    }

    // Se une el nuevo minimo con sus hijos.
    mMin = mergeLists(mMin, minElem.child);

   // Si no hay más elementos, terminamos
    if (mMin == null) return;

    // Se necesita tener sólo 1 árbol de cada grado, para lo cual se crea una lista que contenga dichos arboles
    // o nulo si es que aún no hay un arbol de dicho grado i.
    List<FNode> treeTable = new ArrayList<FNode>();

    // Hay que pasar a través de toda la lista, y al ser circular se debe detectar cuando se pasa 2 veces por el mismo.
    List<FNode> toVisit = new ArrayList<FNode>();

    // Para añadir, se itera entre los elementos hasta que se encuentra el primer elemento 2 veces
    for (FNode curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.next)
      toVisit.add(curr);

    // Unimos los elementos de toVisit
    for (FNode curr: toVisit) {

      while (true) {
        // Nos aseguramos de que la lista es lo suficientemente larga para contener al grado del arbol.
        while (curr.degree >= treeTable.size())
          treeTable.add(null);

        // Si no hay nada, se puede guardar el arbol en el grado=i correspondiente y se termina de procesar.
        if (treeTable.get(curr.degree) == null) {
          treeTable.set(curr.degree, curr);
          break;
        }

        // Sino, se unen los dos arboles binomiales de = grado y se colocan en el grado que le corresponde.
        FNode other = treeTable.get(curr.degree);
        treeTable.set(curr.degree, null); // Clear the slot

        // Se determina cual de los dos arboles es el más pequeño.
        FNode min = (other.priority < curr.priority)? other : curr;
        FNode max = (other.priority < curr.priority)? curr  : other;

        // Se desconecta el max de sus vecinos
        max.next.prev = max.prev;
        max.prev.next = max.next;

        // Se crea un singleton para poder unir los arboles.
        max.next = max.prev = max;
        // Se cuelga el arbol max del arbol min.
        min.child = mergeLists(min.child, max);

        // Se reparentiza el maximo apropiadamente.
        max.parent = min;

        // Resetea la marca, ya que se puede perder un hijo.
        max.isMarked = false;

        // Incremente el grado del mínimo, ya que tiene un nuevo hijo.
        ++min.degree;

        // Continua uniendo el arbol.
        curr = min;
      }

      // Actualiza el mínimo global, basado en el nodo actual.
      if (curr.priority <= mMin.priority) mMin = curr;
    }
  }

  /**
   * Disminuye prioridad del elemento entry a la nueva distancia newDist.
   * Tiempo = O(1)
   *
   * @param entry El elemento con la prioridad que debe cambiar.
   * @param newDist La nueva prioridad.
   */
  public void decreaseKey(FNode entry, double newDist) {
    // Cambiamos la prioridad.
    entry.priority = newDist;

    // Si el nodo tiene una mejor prioridad que su padre, se corta.
    if (entry.parent != null && entry.priority <= entry.parent.priority)
      cutNode(entry);

    // Si el nuevo valor es el nuevo minimo, se cambia como tal.
    if (entry.priority <= mMin.priority)
      mMin = entry;
  }

  /**
   * Función auxiliar, que dados dos punteros a nodos de fibonacci,
   * las une para formar una sola cola en tiempo O(1).
   *
   * @param one El primer elemento que debe ser unido.
   * @param two El segundo elemento que debe ser unido.
   * @return Un puntero al elemento más pequeño de los que fueron unidos.
   */
  private static FNode mergeLists(FNode one, FNode two) {
    // Hay 4 casos dependiendo si existen nulos o no.
    if (one == null && two == null) { // Ambos nulos, se devuelve un nulo.
      return null;
    }
    else if (one != null && two == null) { // La 2da es nula, se devuelve la 1ra.
      return one;
    }
    else if (one == null) { // La 1ra es nula, se devuelve la 2da.
      return two;
    }
    else { // Ambas son no nulas.
      // Se cuelga el más grande del más pequeño.
      FNode oneNext = one.next;
      one.next = two.next;
      one.next.prev = one;
      two.next = oneNext;
      two.next.prev = two;


      return one.priority < two.priority? one : two;
    }
  }

  /**
   * Corta un nodo de su padre. Si el padre ya fue marcado,
   * corta recursivamente dicho nodo de su padre también.
   *
   * @param entry El nodo a cortar de su padre.
   */
  private void cutNode(FNode entry) {
    // Se cambia la marca del nodo, ya que será cortado.
    entry.isMarked = false;

    // Caso Base:
    // El nodo no tiene padre, está listo.
    if (entry.parent == null) return;

    // Se desconecta de sus nodos laterales si es que tiene.
    if (entry.next != entry) {
      entry.next.prev = entry.prev;
      entry.prev.next = entry.next;
    }

    // Si el nodo es identificado como hijo de su padre
    if (entry.parent.child == entry) {
      // Hacemos que apunte al sgte hijo arbitrariamente.
      if (entry.next != entry) {
        entry.parent.child = entry.next;
      }
      // No hay más hijos, hacemos que apunte a null.
      else {
        entry.parent.child = null;
      }
    }

    // Se perdió un hijo, se disminuye el grado del nodo.
    --entry.parent.degree;

    // Se divide el arbol en su raíz y lo convierte a singleton.
    entry.prev = entry.next = entry;
    mMin = mergeLists(mMin, entry);

    // Marca el padre y corta recursivamente si ya ha sido marcado.
    if (entry.parent.isMarked)
      cutNode(entry.parent);
    else
      entry.parent.isMarked = true;

    // Resetea el nodo padre del nodo cortado, ya que ahora es raíz.
    entry.parent = null;
  }
}
