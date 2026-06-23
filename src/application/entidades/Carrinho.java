package application.entidades;

public class Carrinho {

    private String idCarrinho;
    private String fkIDItemPedido;

    public String getIdCarrinho() {
        return idCarrinho;
    }

    public String getFkIDItemPedido() {
        return fkIDItemPedido;
    }

    public void setIdCarrinho(String idCarrinho) {
        this.idCarrinho = idCarrinho;
    }


    public void setFkIDItemPedido(String fkIDItemPedido) {
        this.fkIDItemPedido = fkIDItemPedido;
    }

    public Carrinho(){

    }

    private Carrinho(String idCarrinho, String qtdItems, Double valorTotal, String fkIDItemPedido){
        this.idCarrinho = idCarrinho;
        this.fkIDItemPedido = fkIDItemPedido;
    }
}
