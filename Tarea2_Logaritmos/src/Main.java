import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Dijkstra d = new Dijkstra();

    /**
     * Crea un grafo conexo y no dirigido.
     * @param i Es la potencia hasta la cual se generan nodos.
     * @param j Es la potencia hasta la cual se generan aristas.
     */
    public Grafo createGrafo(int i, int j) {
        // Sets experimentación:
        // v = 2^i Nodos, i en {10,12,14}
        // e = 2^j Aristas, j en {16,17,18}
        int limiteNodos = (int) Math.pow(2, i);
        int limiteAristas = (int) Math.pow(2, j);
        Grafo g = new Grafo();

        // Creamos los 2^i nodos
        for (int k=0; k<limiteNodos; k++) {
          Nodo nodo = new Nodo(k);
          g.addNode(nodo);
        }

        // Obtenemos la lista de nodos del grafo
        ArrayList<Nodo> nodos = g.getV();

        // Generamos un objeto random
        Random r = new Random();

        // Conectar los nodos del grafo (v-1 aristas)
        for (int k=1; k<limiteNodos-1; k++) {
            Nodo nodoK = nodos.get(k);
            int index = r.nextInt(k);
            Nodo otroNodo = nodos.get(index);

            // Generamos peso para la arista
            double peso = r.nextDouble();

            // Creamos una arista entre estos nodos
            Arista a = new Arista(peso, nodoK, otroNodo);
            g.addEdge(a);
        }

        for (int k=0; k<limiteAristas-(limiteNodos-1); k++) {
            // Buscamos 2 nodos cualquiera
            int index1 = r.nextInt();
            int index2 = r.nextInt();

            Nodo nodo1 = nodos.get(index1);
            Nodo nodo2 = nodos.get(index2);

            // Generamos peso para la arista
            double peso = r.nextDouble();

            // Creamos una arista entre estos nodos
            Arista a = new Arista(peso, nodo1, nodo2);
            g.addEdge(a);
        }

        return g;
    }

    // Sets experimentación:
    // v = 2^i Nodos, i en {10,12,14}
    // e = 2^j Aristas, j en {16,17,18}
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}