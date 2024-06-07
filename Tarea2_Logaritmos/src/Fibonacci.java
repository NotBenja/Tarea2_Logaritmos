import java.util.ArrayList;

/**
 * Es la clase que implementa los métodos de una cola de fibonacci
 */
public class Fibonacci {
    /** Nodo que contiene el mínimo */
    private FNode mini = null;
    /** Número de nodos que posee */
    int numberOfNodes = 0;

    /**
     * Inserta un par p en la cola.
     * @param p El par insertado.
     */
    public FNode enqueue(FPair p) {
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

        return newNode;
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
     * Une dos colas de fibonacci.
     */
    public void union() {
        int temp2 = (int) (Math.log(numberOfNodes)/Math.log(2));
        int temp3 = temp2+1;
        ArrayList<FNode> arr = new ArrayList<FNode>(temp3);

        for (int i = 0; i<temp3; i++) {
            arr.add(null);
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

            if (nodoj != null) {
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

/** Método que extrae el mínimo de una cola de fibonacci*/
    public void extractMin() {
        if (mini != null) {
            FNode temp = mini;
            FNode pntr = temp;
            FNode x = null;

            if (temp.getChild() != null) {
                x = temp.getChild();

                while (true) {
                    pntr = x.getRight();
                    mini.getLeft().setRight(x);
                    x.setRight(mini);
                    x.setLeft(mini.getLeft());
                    mini.setLeft(x);

                    if (x.getKey() < mini.getKey()) {
                        mini = x;
                    }
                    x.setParent(null);
                    x = pntr;

                    if (pntr == temp.getChild()) {
                        break;
                    }

                }
            }
            temp.getLeft().setRight(temp.getRight());
            temp.getRight().setLeft(temp.getLeft());
            mini = temp.getRight();

            if (temp == temp.getRight() && temp.getChild() == null) {
                mini = null;
            } else {
                mini = temp.getRight();
                union();
            }
            numberOfNodes -= 1;
        }

    }

    /**
     * Corta un nodo de la cola para colocarlo en la raíz.
     * @param found Es el nodo a cambiar de lugar.
     * @param temp Es el nodo padre de found.
     */
    public void cut(FNode found, FNode temp) {
        // Si found no tiene hijos, se le asigna a temp un hijo null
        if (found.getChild() == found) {
            temp.setChild(null);
        }

        // Se elimina a found de la lista de hermanos
        found.getLeft().setRight(found.getRight());
        found.getRight().setLeft(found.getLeft());

        // Si found sigue declarado como el hijo de temp, se le asigna a
        // temp (como hijo) el hermano a la derecha de found
        if (found == temp.getChild()) {
            temp.setChild(found.getRight());
        }

        // Se actualiza la información:
        // Se reduce la cantidad de hijos de temp
        temp.setDegree(temp.getDegree()-1);
        // Se aisla found de la lista de hermanos
        found.setRight(found);
        found.setLeft(found);
        // Se conectan mini y found
        mini.getLeft().setRight(found);
        found.setRight(mini);
        found.setLeft(mini.getLeft());
        mini.setLeft(found);
        found.setParent(null);
        found.setMark("B");
    }

    /**
     * Es la parte recursiva de la función Cut.
     * @param temp un nodo de fibonacci.
     */
    public void cascadeCut(FNode temp) {
        FNode ptr5 = temp.getParent();

        if (ptr5 != null) {
            if (temp.getMark().equals("W")) {
                temp.setMark("B");
            } else {
                cut(temp, ptr5);
                cascadeCut(ptr5);
            }
        }
    }

    /**
     * Tiempo = O(1)
     * Actualiza la distancia del par que representa al nodo respectivo en Q.
     * @param p Es el par de la cola que debe modificarse.
     * @param newDist Es la nueva distancia que debe colocarse al par de la cola.
     */
    public void decreaseKey(double newDist, FPair p) {
        if (mini == null || p == null) {
        } else {
            // Accedemos al FNode asociado al par y actualizamos la distancia en ambos
            FNode found = p.getFNode();
            p.setDist(newDist);
            found.setKey(newDist);

            // temp será el padre de found
            FNode temp = found.getParent();

            if (temp != null && found.getKey() > temp.getKey()) {
                cut(found, temp);
                cascadeCut(temp);
            }
            if (found.getKey() < mini.getKey()) {
                mini = found;
            }
        }
    }

    /** @return true si la cola está vacía. */
    public boolean isEmpty() {
        return mini == null;
    }

    /** @return el primer elemento de la cola y eliminarlo. */
    public FNode getMini() {
        return mini;
    }
}
