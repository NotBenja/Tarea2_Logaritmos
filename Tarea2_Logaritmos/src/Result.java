import java.util.ArrayList;

/**
 * Es la clase que representa los resultados de realizar el algoritmo Dijkstra sobre un grafo.
 */
public class Result {
    /** Es una lista de distancias al nodo raíz para los distintos nodos del camino */
    private ArrayList<Double> distancias;

    /** Es una lista de nodos previos al nodo actual */
    private ArrayList<Nodo> previos;

    /** El tiempo en milisegundos que se demoró el algoritmo */
    private long time;

    /** @return el arreglo de distancias */
    public ArrayList<Double> getDist() {
        return distancias;
    }

    /** @param newDist el nuevo arreglo de distancias */
    public void setDist(ArrayList<Double> newDist) {
        this.distancias = newDist;
    }

    /** @return el arreglo de nodos previos */
    public ArrayList<Nodo> getPrevios() {
        return previos;
    }

    /** @param newPrevios el nuevo arreglo de nodos previos */
    public void setPrevios(ArrayList<Nodo> newPrevios) {
        this.previos = newPrevios;
    }

    /** @return el tiempo que demoró el algoritmo */
    public long getTime() {
        return time;
    }

    /** @param newTime el nuevo tiempo asociado al algoritmo */
    public void setTime(long newTime) {
        this.time = newTime;
    }
}
