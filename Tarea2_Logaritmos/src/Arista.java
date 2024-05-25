/** Clase que representa una arista de un grafo */
public class Arista {
    /** Es el peso correspondiente a la arista, es un valor entre (0,1] */
    private double weight;

    /** Primer nodo asociado a la arista */
    private Nodo node1;

    /** Segundo nodo asociado a la arista */
    private Nodo node2;

    /**
     * Es el constructor para una arista de un nodo.
     * @param w El peso correspondiente a la arista, entre (0,1]
     * @param n1 El primer nodo al que está asociado.
     * @param n2 El segundo nodo al que está asociado.
     */
    public Arista(double w, Nodo n1, Nodo n2) {
        this.weight = w;
        this.node1 = n1;
        this.node2 = n2;
    }

    /**
     * @return el peso asociado a la arista.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return uno de los nodos asociado a la arista.
     */
    public Nodo getNode1() {
        return node1;
    }

    /**
     * @return uno de los nodos asociado a la arista.
     */
    public Nodo getNode2() {
        return node2;
    }

    /**
     * @param w el peso nuevo que se quiere asociar.
     */
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * @param n1 El nodo nuevo que se quiere asociar.
     */
    public void setNode1(Nodo n1) {
        this.node1 = n1;
    }

    /**
     * @param n2 El nodo nuevo que se quiere asociar.
     */
    public void setNode2(Nodo n2) {
        this.node2 = n2;
    }
}
