/**
 * Clase para representar un nodo de un grafo.
 */
public class Nodo {
    /** Es la identificación del nodo dentro del grafo */
    private int id;
    /** Es un puntero al elemento de la cola que indica su distancia a la raíz (que es un par) */
    private Pair pointer;
    private FPair fPointer;

    /**
     * Constructor para crear un nodo de un grafo.
     * @param id Es el identificador del nodo.
     */
    public Nodo(int id) {
        this.id = id;
    }

    /** @return id del nodo del grafo */
    public int getId() {
        return id;
    }
    /** @return el puntero a un tipo par del nodo del grafo */
    public Pair getPointer() {
        return pointer;
    }
    /** @return el puntero a un tipo fPar del nodo del grafo */
    public FPair getfPointer(){
            return this.fPointer;
    }
    /** @param newId el nuevo id asociado al nodo */
    public void setId(int newId) {
        this.id = newId;
    }
    /** @param newPointer el nuevo puntero a un par asociado al nodo */
    public void setPointer(Pair newPointer) {
        this.pointer = newPointer;
    }

    /** @param newfPointer el nuevo puntero a un fPar asociado al nodo */
    public void setfPointer(FPair newfPointer) { this.fPointer = newfPointer; }


}
