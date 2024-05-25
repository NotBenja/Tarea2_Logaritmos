/**
 * Clase que representa un elemento de la cola Q, un par del tipo (distancia, nodo)
 */
public class Pair {
    /** Representa la menor distancia encontrada entre la raíz y el nodo asociado */
    private double dist;

    /** Nodo asociado al par */
    private Nodo node;

    /** @param newDist la nueva distancia asociada al par */
    public void setDist(double newDist) {
        this.dist = newDist;
    }

    /** @return la distancia asociada al par */
    public double getDist() {
        return dist;
    }

    /** @param newNode el nuevo nodo asociada al par */
    public void setNode(Nodo newNode) {
        this.node = newNode;
    }

    /** @return el nodo asociado al par */
    public Nodo getNode() {
        return node;
    }
}
