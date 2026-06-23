package application.entidades;

public class Pedido {

    private String idPedido;
    private String statusPedio;
    private String valorFinal;
    private String modoDePagamaento;
    private String dataDoPedido;
    private String fkIdCarrinho;
    private String fkIdCliente;

    public Pedido(){

    }

    public Pedido(String idPedido,String statusPedio, String valorFinal, String modoDePagamaento,String dataDoPedido, String fkIdCarrinho ,String fkIdCliente){
        this.idPedido = idPedido;
        this.statusPedio = statusPedio;
        this.valorFinal = valorFinal;
        this.modoDePagamaento = modoDePagamaento;
        this.dataDoPedido = dataDoPedido;
        this.fkIdCarrinho = fkIdCarrinho;
        this.fkIdCliente = fkIdCliente;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getStatusPedio() {
        return statusPedio;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public String getModoDePagamaento() {
        return modoDePagamaento;
    }

    public String getDataDoPedido() {
        return dataDoPedido;
    }

    public String getFkIdCarrinho() {
        return fkIdCarrinho;
    }

    public String getFkIdCliente() {
        return fkIdCliente;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public void setStatusPedio(String statusPedio) {
        this.statusPedio = statusPedio;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void setModoDePagamaento(String modoDePagamaento) {
        this.modoDePagamaento = modoDePagamaento;
    }

    public void setDataDoPedido(String dataDoPedido) {
        this.dataDoPedido = dataDoPedido;
    }

    public void setFkIdCarrinho(String fkIdCarrinho) {
        this.fkIdCarrinho = fkIdCarrinho;
    }

    public void setFkIdCliente(String fkIdCliente) {
        this.fkIdCliente = fkIdCliente;
    }
}
