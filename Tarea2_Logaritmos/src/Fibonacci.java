import java.util.ArrayList;

/**
 * Es la clase que implementa los métodos de una cola de fibonacci
 */
public class Fibonacci {
    /** Nodo que contiene el mínimo */
    FNode mini = null;
    /** Número de nodos que posee */
    int numberOfNodes = 0;

    /**
     * Inserta un par p en la cola.
     * @param p El par insertado.
     */
    public void enqueue(Pair p) {
        FNode newNode = new FNode();
        newNode.setPair(p);

        // Si no hay minimo, es el primer elemento añadido
        if (mini != null) {
            mini.getLeft().setRight(newNode);
            newNode.setRight(mini);
            newNode.setLeft(mini.getLeft());
            mini.setLeft(newNode);

            // Si la llave actual es menor, cambia el minimo
            if (newNode.getKey() < mini.getKey()) {
                mini = newNode;
            }
        } else {
            mini = newNode;
        }
        numberOfNodes += 1;
    }

    /** Actualiza la estructura y conexiones entre un nodo hijo y su nodo padre
     * @param ptr1 el nodo padre
     * @param ptr2 el nodo hijo */
    public void fibonacciLink(FNode ptr1, FNode ptr2) {
        // Se elimina ptr2 de la lista de hermanos
        ptr2.getLeft().setRight(ptr2.getRight());
        ptr2.getRight().setLeft(ptr2.getLeft());

        // Si ptr2 es el único nodo, se setea como el mínimo
        if (ptr1.getRight() == ptr1) {
          mini = ptr1;
        }

        // ptr2 apunta a sí mismo a la derecha e izquierda
        ptr2.setLeft(ptr2);
        ptr2.setRight(ptr2);

        // Se setea al padre de ptr2 al ptr1
        ptr2.setParent(ptr1);

        // Si ptr1 no tiene hijos, se le asigna ptr2
        if (ptr1.getChild() == null) {
            ptr1.setChild(ptr2);
        }

        // Se agrega ptr2 en la lista de hijos de ptr1
        ptr2.setRight(ptr1.getChild());
        ptr2.setLeft(ptr1.getChild().getLeft());
        ptr1.getChild().getLeft().setRight(ptr2);
        ptr1.getChild().setLeft(ptr2);

        // Si ptr2 es menor al mínimo anterior de ptr1, se actualiza
        if (ptr2.getKey() < ptr1.getChild().getKey()){
            ptr1.setChild(ptr2);
        }

        // Se aumenta el contador que guarda la cantidad de hijos de ptr1
        ptr1.setDegree(ptr1.getDegree() + 1);
    }

    /**
     *
     */
    public void union() {
        int temp2 = (int) (Math.log(numberOfNodes)/Math.log(2));
        int temp3 = temp2+1;
        ArrayList<FNode> arr = new ArrayList<FNode>(temp3);

        for (int i = 0; i<temp3; i++) {
            arr.set(i,null);
        }
            
        FNode ptr1 = mini;
        FNode ptr4 = ptr1;
        while (true) {
            ptr4 = ptr4.getRight();
            int temp1 = ptr1.getDegree();

            while (arr.get(temp1) != null) {
                FNode ptr2 = arr.get(temp1);

                if (ptr1.getKey() > ptr2.getKey()) {
                    FNode ptr3 = ptr1;
                    ptr1 = ptr2;
                    ptr2 = ptr3;
                } if (ptr2 == mini) {
                    mini = ptr1;
                }
                fibonacciLink(ptr2,ptr1);

                if (ptr1.getRight() == ptr1) {
                    mini = ptr1;
                }
                arr.set(temp1, null);
                temp1 += 1;
            }
            arr.set(temp1, ptr1);
            ptr1 = ptr1.getRight();

            if (ptr1 == mini) {
                break;
            }

        }

        mini = null;

        for (int j = 0; j<temp3; j++) {
            // Conseguimos el nodo en la posición j del arreglo
            FNode nodoj = arr.get(j);

            if (nodoj == null) {
                nodoj.setLeft(nodoj);
                nodoj.setRight(nodoj);

                if (mini != null) {
                    mini.getLeft().setRight(nodoj);
                    nodoj.setRight(mini);
                    nodoj.setLeft(mini.getLeft());
                    mini.setLeft(nodoj);

                    if (nodoj.getKey() < mini.getKey()) {
                        mini = nodoj;
                    }
                } else {
                    mini = arr.get(j);
                }
                if (mini == null) {
                    mini=arr.get(j);
                } else if (arr.get(j).getKey() < mini.getKey()) {
                    mini = arr.get(j);
                }
            }
        }
    }

    /**
     * Tiempo = O(1)
     * Actualiza la distancia del par que representa al nodo respectivo en Q.
     * @param p
     * @param newDist
     */
    public void decreaseKey(Pair p, double newDist) {

    }

    /** @return true si la cola está vacía. */
    public boolean isEmpty() {
        return false;
    }

    /** @return el primer elemento de la cola y eliminarlo. */
    public Pair get() {
        Pair p = new Pair();
        return p;
    }
}
