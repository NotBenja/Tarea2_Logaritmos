import java.util.ArrayList;
import java.util.HashMap;
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

        HashMap<Nodo, ArrayList<Nodo>> grafo_aux = new HashMap<>();

        // Creamos los 2^i nodos
        for (int k=0; k<limiteNodos; k++) {
          Nodo nodo = new Nodo(k);
          g.addNode(nodo);
          grafo_aux.put(nodo, new ArrayList<>());
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

            // Agregamos la conexión al HashMap
            grafo_aux.get(nodoK).add(otroNodo);
            grafo_aux.get(otroNodo).add(nodoK);
        }

//        for (int k=0; k<limiteAristas-(limiteNodos-1); k++) {
//            // Buscamos 2 nodos cualquiera
//            int index1 = r.nextInt(limiteNodos);
//            int index2 = r.nextInt(limiteNodos);
//
//            Nodo nodo1 = nodos.get(index1);
//            Nodo nodo2 = nodos.get(index2);
//
//            // Generamos peso para la arista
//            double peso = r.nextDouble();
//
//            // Creamos una arista entre estos nodos
//            Arista a = new Arista(peso, nodo1, nodo2);
//            g.addEdge(a);
//        }

        // Las aristas restantes son la resta entre el tope de aristas para no ser multigrafo
        // y las v-1 aristas ya agregadas
        int max = (int) (limiteNodos *  (limiteNodos - 1) / 2) - (limiteNodos-1);
        // Vemos si es posible agregar la cantidad de aristas pedidas
        if (limiteNodos > max) {
            limiteNodos = max;
        }
        int count = 0;

        while (count < limiteNodos) {

            // Buscamos 2 nodos cualquiera
            int index1 = r.nextInt(limiteNodos);
            int index2 = r.nextInt(limiteNodos);

            Nodo nodo1 = nodos.get(index1);
            Nodo nodo2 = nodos.get(index2);
            // Si alguna conexión ya existe, entonces repetimos
            if (grafo_aux.get(nodo1).contains(nodo2) || grafo_aux.get(nodo2).contains(nodo1)) {
                continue;
            }

            // Generamos peso para la arista
            double peso = r.nextDouble();


            // Creamos una arista entre estos nodos
            Arista a = new Arista(peso, nodo1, nodo2);
            g.addEdge(a);
            // Añadimos la arista al HashMap
            grafo_aux.get(nodo1).add(nodo2);
            grafo_aux.get(nodo2).add(nodo1);

            count++;

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
     * Convierte un double en un string
     * @param value Un valor tipo double
     * @return El mismo valor transformado a string
     */
    public static String toString(double value) {
        return Double.toString(value);
    }

    /**

    /**
     * Escribe los resultados en tiempo de Dijkstra para un grafo de i nodos y j aristas con Heap y con Colas de Fibonacci
     * @param i la cantidad de nodos del grafo
     * @param j la cantidad de aristas del grafo
     * @param limite la cantidad de tests y grafos (i,j) creados
     */
    public static void testsDijkstra(int i, int j, int limite) {
        long totalFib = 0;
        long totalHeap = 0;

        String filenameHeap = "tests/Heap/resultsHeap";
        String filenameFibonacci = "tests/Fib/resultsFibonacci";
        // Para el par i, j hace 50 tests para cada algoritmo
        filenameHeap += toString(i) + "_" + toString(j) +".txt";
        filenameFibonacci += toString(i) + "_" + toString(j) + ".txt";

        long initialTime = System.nanoTime();
        for(int k = 0; k < limite; k++) {
            Grafo g = createGrafo(i, j);
            if (k != 0) {

                // Testing Fibonacci
                long startTimeFib = System.nanoTime();
                Result rfibb = d.dijkstraFibb(g);
                long endTimeFib = System.nanoTime();
                long durationFib = endTimeFib - startTimeFib;
                double durationFibInSeconds = durationFib / 1e9;
                totalFib += durationFib;
                w.write(filenameFibonacci, toString(durationFibInSeconds));

                // Testing Heap
                long startTimeHeap = System.nanoTime();
                Result rheap = d.dijkstraHeap(g);
                long endTimeHeap = System.nanoTime();
                long durationHeap = endTimeHeap - startTimeHeap;
                double durationHeapInSeconds = durationHeap / 1e9;
                totalHeap += durationHeap;
                w.write(filenameHeap, toString(durationHeapInSeconds));
                System.out.println("Iteración "+ Integer.toString(k));
            }
        }
        long finalTime = System.nanoTime();

        double totalTime = (finalTime - initialTime)/1e9;

        // Guardamos el promedio en los archivos
        totalFib = totalFib / (limite - 1);
        double promedioFibbInSeconds = totalFib / 1e9;
        w.write(filenameFibonacci, "Promedio: " + promedioFibbInSeconds);

        totalHeap = totalHeap / (limite - 1);
        double promedioHeapInSeconds = totalHeap / 1e9;
        w.write(filenameHeap, "Promedio: " + promedioHeapInSeconds);

        System.out.println("Tiempo total: " + totalTime + " segundos");

    }

    public static void main(String[] args) {
        int i = 14;
        int j = 18;
        testsDijkstra(i,j,51);

    }
}