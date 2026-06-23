package application.entidades;

public class ItemPedido {
    private String iDItemPedido;

    public ItemPedido(){

    }
    public ItemPedido(String iDItemPedido){
        this.iDItemPedido = iDItemPedido;
    }

    public String getIDItemPedido() {
        return iDItemPedido;
    }

    public void setIDItemPedido(String iDItemPedido) {
        this.iDItemPedido = iDItemPedido;
    }
}
