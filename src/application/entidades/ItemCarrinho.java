package application.entidades;

public class ItemCarrinho {

    private String idItemCarrinho;
    private Integer quantidade;
    private Double precoUnitario;
    private String fkCarrinho;
    private String fkProduto;

    public ItemCarrinho() {
    }

    public ItemCarrinho(Integer quantidade, Double precoUnitario, String fkCarrinho, String fkProduto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.fkCarrinho = fkCarrinho;
        this.fkProduto = fkProduto;
    }

    public String getIdItemCarrinho() {
        return idItemCarrinho;
    }

    public void setIdItemCarrinho(String idItemCarrinho) {
        this.idItemCarrinho = idItemCarrinho;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getFkCarrinho() {
        return fkCarrinho;
    }

    public void setFkCarrinho(String fkCarrinho) {
        this.fkCarrinho = fkCarrinho;
    }

    public String getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(String fkProduto) {
        this.fkProduto = fkProduto;
    }

    public Double getSubtotal() {
        return quantidade * precoUnitario;
    }
}