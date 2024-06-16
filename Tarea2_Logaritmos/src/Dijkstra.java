import java.util.ArrayList;
import java.util.HashMap;

/**
 * Es la clase que implementa el algoritmo Dijkstra.
 */
public class Dijkstra {
    public long iTime;

    /**
     * Aplica el algoritmo dijkstra sobre la raíz de un grafo.
     * @param grafo Es un nodo raíz de un grafo de e aristas y v
     *              nodos no dirigido y con pesos.
     * @return el árbol de caminos más cortos para el nodo raíz,
     * de modo que para cualquier otro nodo en el grafo, la distancia
     * entre la raíz y ese nodo es mínima para las aristas pertenecientes
     * al árbol que los conectan.
     */
    public Result dijkstraHeap(Grafo grafo) {
        // Tiempo en el que el algoritmo empezó a trabajar
        long initialTime = System.currentTimeMillis();

        // Crear una pseudo cola Q1
        ArrayList<Pair> q1 = new ArrayList<>();

        // Objeto que almacenará el resultado
        Result result = new Result();

        // Variable que representa el mayor double posible (el 'infinito')
        double infinito = Double.MAX_VALUE;

        // Conseguimos el nodo raiz
        Nodo raiz = grafo.getV().get(0);

        // Lista de distancias desde la raíz hasta los demás nodos
        HashMap<Integer,Double> distancias = new HashMap<>();
        // Lista de nodos anteriores respecto a la raiz
        HashMap<Integer, Nodo> previos = new HashMap<>();

        // Definimos distancia al nodo raíz desde el nodo raíz
        distancias.put(0, 0.0);

        // Definimos al nodo previo a la raíz como -1 y lo añadimos a previos
        Nodo previo = new Nodo(-1);
        previos.put(-1, previo);

        // Creamos el par (distancia, raiz) para luego agregarlo a la cola
        Pair qRaiz = new Pair();
        qRaiz.setDist(0.0);
        qRaiz.setNode(raiz);

        q1.add(qRaiz);


        // Paso 4
        // Consideramos i=0 como nodo raíz
        for (int i = 1; i<grafo.getV().size(); i++) {
            // El nodo al cual nos referimos en la iteración actual
            Nodo nodo = grafo.getV().get(i);
            // Nos aseguramos de poner el id actual en el hashmap
            int nodoId = nodo.getId();
            // Definimos distancias[v] = infinito y previos[v]=null, con v = id del nodo
            distancias.put(nodoId, infinito);
            previos.put(nodoId, null);

            // Agregamos el par (distancia, nodo) correspondiente a la cola q
            Pair qNodo = new Pair();
            qNodo.setDist(infinito);
            qNodo.setNode(nodo);
            q1.add(qNodo);

            // Ahora agregamos al nodo una referencia a su par correspondiente
            nodo.setPointer(qNodo);
        }

        // Paso 5
        // Creamos un heap en base al arreglo q1 para que contenga todos los pares
        Heap q2 = new Heap();
        q2.buildHeap(q1, q1.size());

        // Paso 6
        //noinspection DuplicatedCode
        while (!q2.isEmpty()) {
            // Obtenemos el par (d,v) con menor distancia en Q, que se elimina de la cola al hacer get()
            Pair pair = q2.get();
            Nodo v = pair.getNode();
            int vId = pair.getNode().getId();

            // Obtenemos las aristas del grafo, para buscar a los vecinos de v
            ArrayList<Arista> aristas = grafo.getE();

            // Recorremos el arreglo de aristas, y si encontramos un vecino, lo agregamos al array de vecinos
            for (Arista arista : aristas) {
                int nodo1Id = arista.getNode1().getId();
                int nodo2Id = arista.getNode2().getId();

                //Fijamos al vecino como null mientras
                Nodo u = null;

                // Identificamos si la arista que estamos revisando contiene al nodo v
                if (nodo1Id == vId) {
                    u = arista.getNode2();

                } else if (nodo2Id == vId) {
                    u = arista.getNode1();

                }
                if (u != null) {
                    int uId = u.getId();
                    double dist = distancias.get(vId) + arista.getWeight();

                    // Vemos si la distancia es mejor
                    if (distancias.get(uId) > dist) {
                        distancias.put(uId, dist);
                        previos.put(uId, v);

                        int uIndex = q2.getQ().indexOf(u.getPointer());
                        q2.decreaseKey(u.getPointer(), dist, uIndex);
                    }
                }
            }
        }
        result.setDistancias(distancias);
        result.setPrevios(previos);

        long actualTime = System.currentTimeMillis();
        result.setTime(actualTime-initialTime);

        return result;
    }

    /**
     * Calcula el tiempo
     * @param initialTime
     */
    public long actualTime(long initialTime) {
        long time = System.currentTimeMillis();
        long calculated_time = time - initialTime;
        System.out.println(calculated_time);
        this.iTime = System.currentTimeMillis();
        return calculated_time;
    }

    /**
     * Aplica el algoritmo dijkstra sobre la raíz de un grafo.
     * @param grafo Es un nodo raíz de un grafo de e aristas y v
     *              nodos no dirigido y con pesos.
     * @return el árbol de caminos más cortos para el nodo raíz,
     * de modo que para cualquier otro nodo en el grafo, la distancia
     * entre la raíz y ese nodo es mínima para las aristas pertenecientes
     * al árbol que los conectan.
     */
    public Result dijkstraFibb(Grafo grafo) {
        // Tiempo en el que el algoritmo empezó a trabajar
        long initialTime = System.currentTimeMillis();

        // Cola de fibonacci para ir trabajando con los elementos
        FibonacciHeap q = new FibonacciHeap();

        // Objeto que almacenará el resultado
        Result result = new Result();

        // Variable que representa el mayor double posible (el 'infinito')
        double infinito = Double.MAX_VALUE;

        // Conseguimos el nodo raiz
        Nodo raiz = grafo.getV().get(0);

        // Lista de distancias desde la raíz hasta los demás nodos
        HashMap<Integer,Double> distancias = new HashMap<>();
        // Lista de nodos anteriores respecto a la raiz
        HashMap<Integer, Nodo> previos = new HashMap<>();

        // Definimos distancia al nodo raíz desde el nodo raíz
        distancias.put(0, 0.0);

        // Definimos al nodo previo a la raíz como -1 y lo añadimos a previos
        Nodo previo = new Nodo(-1);
        previos.put(-1, previo);

        // Creamos el par (distancia, raiz) para luego agregarlo a la cola
        FPair qRaiz = new FPair();
        qRaiz.setDist(0.0);
        qRaiz.setNode(raiz);
        raiz.setfPointer(qRaiz);
        FibonacciHeap.FNode fNodeRoot = q.enqueue(qRaiz);
        qRaiz.setFNode(fNodeRoot);

        //PASO 4, consideramos i=0 como nodo raíz
        for (int i = 1; i<grafo.getV().size(); i++) {
            // El nodo al cual nos referimos en la iteración actual
            Nodo nodo = grafo.getV().get(i);
            // Nos aseguramos de poner el id actual en el hashmap
            int nodoId = nodo.getId();
            // Definimos distancias[v] = infinito y previos[v]=null, con v = id del nodo
            distancias.put(nodoId, infinito);
            previos.put(nodoId, null);

            // Agregamos el par (distancia, nodo) correspondiente a la cola q
            FPair qNodo = new FPair();
            qNodo.setDist(infinito);
            qNodo.setNode(nodo);
            nodo.setfPointer(qNodo);
            FibonacciHeap.FNode fNode = q.enqueue(qNodo);
            qNodo.setFNode(fNode);
        }

        //Paso 6
        while (!q.isEmpty()) {
            // Obtenemos el par (d,v) con menor distancia en Q, que se elimina de la cola al hacer get()
            FPair pair = q.min().getPair();
            q.extractMin();
            Nodo v = pair.getNode();
            int vId = pair.getNode().getId();

            // Obtenemos las aristas del grafo, para buscar a los vecinos de v
            ArrayList<Arista> aristas = grafo.getE();

            // Recorremos el arreglo de aristas en busca de vecinos
            for (Arista arista : aristas) {
                int nodo1Id = arista.getNode1().getId();
                int nodo2Id = arista.getNode2().getId();

                // Fijamos al vecino como null mientras
                Nodo u = null;

                // Identificamos si la arista que estamos revisando contiene al nodo v
                if (nodo1Id == vId) {
                    u = arista.getNode2();
                } else if (nodo2Id == vId) {
                    u = arista.getNode1();
                }

                // Si la arista contenía a v, u es un vecino
                if (u != null) {
                    int uId = u.getId();
                    double dist = distancias.get(vId) + arista.getWeight();

                    // Vemos si la distancia es mejor
                    if (distancias.get(uId) > dist) {
                        distancias.put(uId, dist);
                        previos.put(uId, v);
                        q.decreaseKey(u.getfPointer().getFNode(), dist);
                    }
                }
            }
        }

        result.setDistancias(distancias);
        result.setPrevios(previos);

        long actualTime = System.currentTimeMillis();
        result.setTime(actualTime-initialTime);

        return result;
    }
}
