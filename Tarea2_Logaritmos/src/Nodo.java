/**
 * Clase para representar un nodo de un grafo.
 */
public class Nodo {
    /** Es la identificación del nodo dentro del grafo */
    private int id;
    /** Es un puntero al elemento de la cola que indica su distancia a la raíz (que es un par) */
    private Pair pointer;

    /**
     * Constructor para crear un nodo de un grafo.
     * @param id Es el identificador del nodo.
     */
    public Nodo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Pair getPointer() {
        return pointer;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setPointer(Pair newPointer) {
        this.pointer = newPointer;
    }
}
