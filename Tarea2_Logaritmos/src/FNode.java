/** Clase que representa un nodo perteneciente a la cola de fibonacci, que también puede ser la raíz del heap */
public class FNode {

    private FPair pair = null;
    /** Nodo padre del nodo actual */
    private FNode parent = null;
    /** Nodo hijo del nodo actual*/
    private FNode child = null;
    /** Puntero al nodo izquierdo del nodo actual*/
    private FNode left = null;
    /** Puntero al nodo derecho del nodo actual */
    private FNode right = null;
    /** Valor que guarda la distancia del Pair asociado */
    private double key = 0.0;
    /** Grado del nodo actual */
    private int degree = -1;
    /** Marcador white/black para el nodo */
    private String mark = "";
    /** Flag para find */
    private String c = "";

    /** @param p el nodo padre al nodo actual */
    void setParent(FNode p) {
            this.parent=p;
        }
    /** @param child el nodo hijo del nodo actual */
    void setChild(FNode child){
        this.child=child;
    }
    /** Asigna el par asociado al nodo y los valores default del resto de variables
     * @param p el par que contiene el nodo */
    void setPair(FPair p) {
        this.pair=p;
        this.key=p.getDist();
        this.degree=0;
        this.mark = "W";
        this.c = "N";
        this.left = this;
        this.right=this;
    }
    /** @param l nodo izquierdo al nodo actual */
    void setLeft(FNode l){
        this.left=l;
    }
    /** @param r nodo derecho al nodo actual */
    void setRight(FNode r){
        this.right=r;
    }
    /** @param k la llave asociada al nodo actual */
    void setKey(double k){
        this.key = k;
    }
    /** @param m la marca asociada al nodo actual */
    void setMark(String m){
        this.mark=m;
    }
    /** @param d el grado asociado al nodo actual */
    void setDegree(int d){
        this.degree=d;
    }
    /** @param c flag del nodo actual */
    void setC(String c){
        this.c=c;
    }

    /** @return El nodo padre del nodo actual */
    public FNode getParent(){
        return this.parent;
    }
    /** @return El nodo hijo del nodo actual */
    public FNode getChild(){
        return this.child;
    }
    /** @return El nodo izquierdo al nodo actual*/
    public FNode getLeft(){
        return this.left;
    }
    /** @return El nodo derecho al nodo actual*/
    public FNode getRight(){
        return this.right;
    }
    /** @return La distancia asociada al Pair del nodo*/
    public double getKey(){
        return this.key;
    }
    /** @return El grado al nodo actual*/
    public int getDegree(){
        return this.degree;
    }
    /** @return El marcador del nodo actual*/
    public String getMark(){
        return this.mark;
    }
    /** @return La flag del nodo*/
    public String getC(){
        return this.c;
    }
    /** @return El par guardado en el nodo */
    public FPair getPair() {
        return this.pair;
    }
}
