import java.util.ArrayList;

/**
 * Clase que representa un grafo, contiene el conjunto de nodos y aristas asociadas
 */
public class Grafo {
    /** Contiene la lista de nodos en el grafo*/
    private ArrayList<Nodo> v;

    /** Contiene la lista de aristas en el grafo*/
    private ArrayList<Arista> e;

    /** @return La lista de nodos del grafo*/
    public ArrayList<Nodo> getV() {
        return v;
    }

    /** @return La lista de aristas del grafo*/
    public ArrayList<Arista> getE() {
        return e;
    }

    /** @param newNodo Nodo que se inserta en el grafo
     * Método que agrega un nodo al grafo
     * */
    public void addNode(Nodo newNodo) {
        if(v.contains(newNodo) || newNodo == null) {
            return;
        }
        v.add(newNodo);
    }

    /** @param newArista Arista que se inserta en el grafo
     * Método que agrega una arista al grafo
     * */
    public void addEdge(Arista newArista){
        if(e.contains(newArista) || newArista == null) {
            return;
        }
        e.add(newArista);
    }
}
