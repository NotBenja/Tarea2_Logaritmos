import java.util.ArrayList;

/**
 * Es la clase que implementa los métodos de una cola tipo heap.
 * Tiempo = O(n)
 */
public class Heap {
    private ArrayList<Pair> q;

    /** @return La lista asociada al heap que contiene los nodos del grafo. */
    public ArrayList<Pair> getQ() {return q;}

    /**
     * Tiempo = O(log n)
     * Actualiza la distancia del par que representa al nodo respectivo en Q.
     * @param p Es el par del nodo al que se le actualiza la distancia.
     * @param newDist Es la nueva distancia que tiene el nodo n.
     * @param index Es el índice del par a modificar.
     */
    public void decreaseKey(Pair p, double newDist, int index) {
        p.setDist(newDist);

        // Si este nodo es la raíz, no hacemos nada
        if (index > 0) {
            // Padre del nodo actual al que se le modifica la distancia
            int parentIndex = (index-1)/2;
            Pair parent = q.remove(parentIndex);

            // Swap sobre el nodo y el padre, llamamos recursivamente
            if (parent.getDist() > newDist) {
                q.add(parentIndex, p); // ponemos al hijo en la posición del padre
                q.remove(index);
                q.add(index, parent); // y al padre en la posición del hijo

                // Llamamos recursivamente sobre la posición parentIndex
                decreaseKey(p, newDist, parentIndex);
            }
        }
    }

    /**
     * @return true si la cola está vacía
     */
    public boolean isEmpty() {
        return q.isEmpty();
    }

    /**
     * @return el primer elemento de la cola y eliminarlo.
     */
    public Pair get() {
        Pair p = q.remove(0);
        if (q.size() > 1) {
            Pair pRemoved = q.remove(q.size()-1);
            q.add(0, pRemoved);
            heapify(q, q.size(), 0);
        }
        return p;
    }

    /**
     * Hace min heapify sobre una lista de pares.
     * @param pq Es una pseudo cola de pares.
     */
    public void heapify(ArrayList<Pair> pq, int N, int i) {
        Pair smallest = pq.get(i);
        int l = 2 * i + 1; //índice del hijo izquierdo del nodo min
        int r = 2 * i + 2; //índice del hijo derecho del nodo min

        if (l >= pq.size()) {
            // Si l está fuera de rango significa que el nodo del índice i no tiene hijos,
            // no se hace nada
            return;
        } else if (r >= pq.size()) {
            // Si solo tiene un hijo (el izquierdo) solo aplicamos el proceso sobre ese nodo
            int index = i;
            double smallestDist = smallest.getDist();
            double leftDist = pq.get(l).getDist();

            //Si la distancia del hijo izquierdo es menor que la de la raiz
            if (l < N && leftDist < smallestDist) {
                smallest = pq.get(l);
                index = l;
            }

            // Si la raíz no corresponde al par definido como smallest, se hace un swap
            if (smallest != pq.get(i)) {
                Pair swap = pq.get(i);
                pq.add(i, smallest);
                pq.remove(i+1);

                pq.add(index, swap);
                pq.remove(index+1);

                // Llamamos recursivamente a heapify
                heapify(pq, N, index);
            }
        } else {
            int index = i;
            double smallestDist = smallest.getDist();
            double leftDist = pq.get(l).getDist();
            double rightDist = pq.get(r).getDist();

            //Si la distancia del hijo izquierdo es menor que la de la raiz
            if (l < N && leftDist < smallestDist) {
                smallest = pq.get(l);
                index = l;
            }

            // Si la distancia del hijo derecho es la menor hasta ahora
            if (r < N && rightDist < smallestDist) {
                smallest = pq.get(r);
                index = r;
            }

            // Si la raíz no corresponde al par definido como smallest, se hace un swap
            if (smallest != pq.get(i)) {
                Pair swap = pq.get(i);
                pq.add(i, smallest);
                pq.remove(i+1);

                pq.add(index, swap);
                pq.remove(index+1);

                // Llamamos recursivamente a heapify
                heapify(pq, N, index);
            }
        }
    }

    /**
     * Construye un Min-Heap a base de una lista de pares.
     * @param pq Es una pseudo cola de pares.
     * @param N El número de nodos totales.
     */
    public void buildHeap(ArrayList<Pair> pq, int N) {
        // El índice que representa el último nodo
        int startIdx = (N / 2) - 1;

        // Hacemos heapify desde el último nodo del árbol cambiado
        for (int i = startIdx-1; i >= 0; i--) {
            heapify(pq, N, i);
        }

        this.q = pq;
    }
}


