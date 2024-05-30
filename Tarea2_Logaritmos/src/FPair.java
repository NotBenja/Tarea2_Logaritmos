/**
 * Clase que representa un elemento de la cola Q del tipo fibonacci, un par del tipo (distancia, nodo)
 */
public class FPair {
    /** Representa la menor distancia encontrada entre la raíz y el nodo asociado */
    private double dist;

    /** Nodo de fibonacci asociado al par */
    private FNode fNode;

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

    /** @param newNode el nuevo nodo asociado al par */
    public void setFNode(FNode newNode) {
        this.fNode = newNode;
    }

    /** @return el nodo asociado al par */
    public FNode getFNode() {
        return fNode;
    }

    /** @param newNode el nuevo nodo asociado al par */
    public void setNode(Nodo newNode) {
        this.node = newNode;
    }

    /** @return el nodo asociado al par */
    public Nodo getNode() {
        return node;
    }
}