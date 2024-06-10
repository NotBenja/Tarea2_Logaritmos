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
    private int mDegree = 0;
    /** Devuelve si el nodo está marcado. */
    private boolean mIsMarked = false;
    /** Elemento siguiente de la cola. */
    private FNode mNext;
    /** Elemento previo de la cola. */
    private FNode mPrev;
    /** El padre del arbol, si tiene. */
    private FNode mParent;
    /** Hijo del nodo, si tiene. */
    private FNode mChild;
    /** Distancia al nodo raíz y sus punteros al par que le corresponde. */
    private FPair mElem;
    /** La prioridad, que es equivalente a la distancia. */
    private double mPriority;

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
     * @param elem El elemento almacenado en la cola.
     */
    private FNode(FPair elem, double priority) {
      mNext = mPrev = this;
      mElem = elem;
      mPriority = priority;
    }
  }

  /** Puntero al elemento mínimo de la cola. */
  private FNode mMin = null;

  /** El tamaño de la cola. */
  private int mSize = 0;

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
    ++mSize;

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
    return mSize;
  }

  /**
   * Desencola el mínimo elemento y busca el nuevo mínimo para asignarlo.
   *
   * @return El elemento más pequeño de la cola.
   */
  public FNode extractMin() {
    // Como se pierde un elemento, se disminuye el tamaño de la cola.
    --mSize;

    // Se guarda el minimo, así se sabe qué retornar.
    FNode minElem = mMin;

    // Se busca eliminar el mínimo de la lista de raíces.
    // Caso 1:
    // Es el único elemento que hay
    if (mMin.mNext == mMin) {
      // Seteamos mMin a null
      mMin = null;
    }
    // Caso 2:
    // Reasignamos las referencias de los elementos que apuntan al mínimo, ya que será removido
    // Se reasigna el mínimo.
    else {
      mMin.mPrev.mNext = mMin.mNext;
      mMin.mNext.mPrev = mMin.mPrev;
      mMin = mMin.mNext;
    }

    // Se quitan las referencias padre de todos los hijos del mínimo.
    if (minElem.mChild != null) {
      // El primer nodo visitado
      FNode curr = minElem.mChild;
      do {
        curr.mParent = null;

        // Vamos al sgte nodo, y nos detenemos si volvemos al primero.
        curr = curr.mNext;
      } while (curr != minElem.mChild);
    }

    // Se une el nuevo minimo con sus hijos.
    mMin = mergeLists(mMin, minElem.mChild);

   // Si no hay más elementos, terminamos
    if (mMin == null) return minElem;

    // Se necesita tener sólo 1 árbol de cada grado, para lo cual se crea una lista que contenga dichos arboles
    // o nulo si es que aún no hay un arbol de dicho grado i.
    List<FNode> treeTable = new ArrayList<FNode>();

    // Hay que pasar a través de toda la lista, y al ser circular se debe detectar cuando se pasa 2 veces por el mismo.
    List<FNode> toVisit = new ArrayList<FNode>();

    // Para añadir, se itera entre los elementos hasta que se encuentra el primer elemento 2 veces
    for (FNode curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.mNext)
      toVisit.add(curr);

    // Unimos los elementos de toVisit
    for (FNode curr: toVisit) {

      while (true) {
        // Nos aseguramos de que la lista es lo suficientemente larga para contener al grado del arbol.
        while (curr.mDegree >= treeTable.size())
          treeTable.add(null);

        // Si no hay nada, se puede guardar el grado del arbol y se termina de procesar.
        if (treeTable.get(curr.mDegree) == null) {
          treeTable.set(curr.mDegree, curr);
          break;
        }

        // Sino, se une lo que haya.
        FNode other = treeTable.get(curr.mDegree);
        treeTable.set(curr.mDegree, null); // Clear the slot

        // Se determina cual de los dos arboles es el más pequeño.
        FNode min = (other.mPriority < curr.mPriority)? other : curr;
        FNode max = (other.mPriority < curr.mPriority)? curr  : other;

        // Se cuelga el arbol max del arbol min.
        max.mNext.mPrev = max.mPrev;
        max.mPrev.mNext = max.mNext;

        // Se crea un singleton para poder unir los arboles.
        max.mNext = max.mPrev = max;
        min.mChild = mergeLists(min.mChild, max);

        // Se reparentiza el maximo apropiadamente.
        max.mParent = min;

        // Limpia la marca, ya que se puede perder un hijo.
        max.mIsMarked = false;

        // Incremente el grado del mínimo, ya que tiene un nuevo hijo.
        ++min.mDegree;

        // Continua uniendo el arbol.
        curr = min;
      }

      // Actualiza el mínimo global, basado en el nodo actual.
      if (curr.mPriority <= mMin.mPriority) mMin = curr;
    }
    return minElem;
  }

  /**
   * Disminuye llave del elemento entry a la nueva distancia newDist.
   * Tiempo = O(1)
   *
   * @param entry El elemento con la prioridad que debe cambiar.
   * @param newDist La nueva prioridad.
   */
  public void decreaseKey(FNode entry, double newDist) {
    // Cambiamos la prioridad.
    entry.mPriority = newDist;

    // Si el nodo ya no tiene una mejor prioridad que su padre, se corta.
    if (entry.mParent != null && entry.mPriority <= entry.mParent.mPriority)
      cutNode(entry);

    // Si el nuevo valor es el nuevo minimo, se cambia como tal.
    if (entry.mPriority <= mMin.mPriority)
      mMin = entry;
  }

  /**
   * Función auxiliar, que dados dos punteros a nodos de fibonacci,
   * las une para formar una sola en tiempo O(1).
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
    else if (one == null && two != null) { // La 1ra es nula, se devuelve la 2da.
      return two;
    }
    else { // Ambas son no nulas.
      FNode oneNext = one.mNext; // Se cuelga el más grande del más pequeño.
      one.mNext = two.mNext;
      one.mNext.mPrev = one;
      two.mNext = oneNext;
      two.mNext.mPrev = two;

      return one.mPriority < two.mPriority? one : two;
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
    entry.mIsMarked = false;

    // Caso Base:
    // El nodo no tiene padre, está listo.
    if (entry.mParent == null) return;

    // Se reconectan los nodos laterales si es que tiene.
    if (entry.mNext != entry) {
      entry.mNext.mPrev = entry.mPrev;
      entry.mPrev.mNext = entry.mNext;
    }

    /* If the node is the one identified by its parent as its child,
     * we need to rewrite that pointer to point to some arbitrary other
     * child.
     */
    // Si el nodo es identificado como hijo de su padre
    // Hacemos que apunte al sgte hijo arbitrariamente.
    if (entry.mParent.mChild == entry) {

      if (entry.mNext != entry) {
        entry.mParent.mChild = entry.mNext;
      }
      // No hay más hijos, hacemos que apunte a null.
      else {
        entry.mParent.mChild = null;
      }
    }

    // Se perdió un hijo, se disminuye el grado del nodo.
    --entry.mParent.mDegree;


    // Se divide el arbol en su raíz y lo convierte a singleton.
    entry.mPrev = entry.mNext = entry;
    mMin = mergeLists(mMin, entry);

    // Marca el padre y corta recursivamente si ya ha sido marcado.
    if (entry.mParent.mIsMarked)
      cutNode(entry.mParent);
    else
      entry.mParent.mIsMarked = true;

    // Limpia el nodo padre relocalizado, ya que ahora es raíz.
    entry.mParent = null;
  }
}
