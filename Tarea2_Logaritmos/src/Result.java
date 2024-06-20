import java.util.HashMap;

/**
 * Es la clase que representa los resultados de realizar el algoritmo Dijkstra sobre un grafo.
 */
public class Result {
    /** Es una lista de distancias al nodo raíz para los distintos nodos del camino */
    private HashMap<Integer, Double> distancias;

    /** Es una lista de nodos previos al nodo actual */
    private HashMap<Integer, Nodo> previos;

    /** @return el arreglo de distancias */
    public HashMap<Integer, Double> getDist() {
        return distancias;
    }

    /** @return el arreglo de nodos previos */
    public HashMap<Integer, Nodo> getPrevios() {
        return previos;
    }

    /** @param newPrevios el nuevo Hashmap que representa los nodos previos */
    public void setPrevios(HashMap<Integer, Nodo> newPrevios) {
        this.previos = newPrevios;
    }

    /** @param distancias Hashmap que representa las distancias entre nodos al utilizar Dijkstra */
    public void setDistancias(HashMap<Integer, Double> distancias){
        this.distancias = distancias;
    }

}
