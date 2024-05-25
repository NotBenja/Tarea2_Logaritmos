import java.util.ArrayList;

/**
 * Es la clase que implementa el algoritmo Dijkstra.
 */
public class Dijkstra {

    /**
     * Aplica el algoritmo dijkstra sobre la raíz de un grafo.
     * @param q Es una cola de fibonacci o un heap, que contiene
     *          pares = (distancia, nodo).
     * @param grafo Es un nodo raíz de un grafo de e aristas y v
     *              nodos no dirigido y con pesos.
     * @return el árbol de caminos más cortos para el nodo raíz,
     * de modo que para cualquier otro nodo en el grafo, la distancia
     * entre la raíz y ese nodo es mínima para las aristas pertenecientes
     * al árbol que los conectan.
     */
    public static Result dijkstra(Grafo grafo, Cola q) {
        // Tiempo en el que el algoritmo empezó a trabajar
        long initialTime = System.currentTimeMillis();

        // Objeto que almacenará el resultado
        Result result = new Result();

        // Variable que representa el mayor double posible (el 'infinito')
        double infinito = Double.MAX_VALUE;

        // Conseguimos el nodo raiz
        Nodo raiz = grafo.getE().get(0);

        // Lista de distancias desde la raíz hasta los demás nodos
        ArrayList<Double> distancias = new ArrayList<Double>();
        // Lista de nodos anteriores respecto a la raiz
        ArrayList<Nodo> previos = new ArrayList<Nodo>();

        // Definimos distancia al nodo raíz desde el nodo raíz
        distancias.add(0.0);

        // Definimos al nodo previo a la raíz como -1 y lo añadimos a previos
        Nodo previo = new Nodo(-1);
        previos.add(previo);

        // Creamos el par (distancia, raiz) para luego agregarlo a la cola
        Pair qRaiz = new Pair();
        qRaiz.setDist(0.0);
        qRaiz.setNode(raiz);
        q.enqueue(qRaiz);

        //PASO 4
        for (int i = 0; ; grafo.getE().size()) {

        }












        return result;
    }
}
