import java.util.ArrayList;
import java.util.Random;

public class    Main {
    private static Dijkstra d = new Dijkstra();
    private static Writer w = new Writer();

    /**
     * Crea un grafo conexo y no dirigido.
     * @param i Es la potencia hasta la cual se generan nodos.
     * @param j Es la potencia hasta la cual se generan aristas.
     */
    public static Grafo createGrafo(int i, int j) {
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
            int index1 = r.nextInt(limiteNodos);
            int index2 = r.nextInt(limiteNodos);

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

    /**
     * Convierte un long en un string
     * @param value Un valor tipo long
     * @return El mismo valor transformado a string
     */
    public static String toString(long value) {
        return Long.toString(value);
    }

    /**
     * Escribe los resultados en tiempo para un grafo de i nodos y j aristas con cola Heap
     * @param i la cantidad de nodos del grafo
     * @param j la cantidad de aristas del grafo
     * @param limite la cantidad de tests y grafos (i,j) creados
     * @param filename el nombre del archivo donde se guardan los tests
     */
    public static void testsHeap(int i, int j, int limite, String filename) {
        long total = 0;
        for(int k=0; k<limite; k++){
            Grafo g = createGrafo(i, j);
            Result r = d.dijkstraHeap(g);
            long content = r.getTime();
            if (k > 0) {
                total += content;
                w.write(filename, toString(content));
            }
        }
        total = total/50;
        w.write(filename, "Promedio: " + toString(total));
    }

    /**
     * Escribe los resultados en tiempo para un grafo de i nodos y j aristas con cola Fibonacci
     * @param i la cantidad de nodos del grafo
     * @param j la cantidad de aristas del grafo
     * @param limite la cantidad de tests y grafos (i,j) creados
     * @param filename el nombre del archivo donde se guardan los tests
     */
    public static void testsFibonacci(int i, int j, int limite, String filename) {
        // Se hacen limite tests sobre un grafo i,j
        long total = 0;
        for (int k = 0; k<limite; k++) {
            Grafo g = createGrafo(i,j);
            Result rfibb = d.dijkstraFibb(g);
            long content = rfibb.getTime();
            if (k > 0) {
                w.write(filename, toString(content));
                total+=content;
            }
        }
        total = total/50;
        w.write(filename, "Promedio: " + toString(total));
    }

    // Sets experimentación:
    // v = 2^i Nodos, i en {10,12,14}
    // e = 2^j Aristas, j en {16,17,18}
    public static void main(String[] args) {
        //int i = 10;
        //int j = 16;
        //String filenameHeap = "tests/resultsHeap";
        //String filenameFibonacci = "tests/resultsFibonacci";

        // Para el par i, j hace 50 tests para cada algoritmo
        //filenameHeap += toString(i) + "_" + toString(j) +".txt";
        //filenameFibonacci += toString(i) + "_" + toString(j) + ".txt";

        //System.out.println("Tests heap");
        //testsHeap(i, j, 51, filenameHeap);
        //System.out.println("Tests heap finalizados");
        //System.out.println("Tests fibonacci");
        //testsFibonacci(i, j, 51, filenameFibonacci);
        //System.out.println("Tests fibonacci finalizados");

        /**
        // POR CADA PAR (i,j) se hacen 50 tests
        // Para regresión lineal: Suma de errores al cuadrado
        //Zamy
        System.out.println("Creando Grafo");
        Grafo g = createGrafo(10,16);
        System.out.println("Grafo Ok");
        //createGrafo(10,17);
        //createGrafo(10,18);
         */
        // Eve
        Grafo g = createGrafo(12,16);
        //createGrafo(12,17);
        //createGrafo(12,18);
        // Benja
        //createGrafo(14,16);
        //createGrafo(14,17);
        //createGrafo(14,18);

        Result rfibb = d.dijkstraFibb(g);
        System.out.println("Dijkstra Fibb Ok");
        Result rheap = d.dijkstraHeap(g);
        System.out.println("Dijkstra Heap Ok");
        System.out.println("Dist");
        //System.out.println(rfibb.getDist());
        //System.out.println("Previos");
        //System.out.println(rfibb.getPrevios());
        System.out.println("Tiempo fibb");
        System.out.println(rfibb.getTime());
        System.out.println("Tiempo heap");
        System.out.println(rheap.getTime());

    }
}