package application.entidades;

public class Produto  {

    private String idProduto;
    private String sKUCodigoUnico;
    private String nome;
    private Double valor;
    private Integer quantidade;
    private String descricao;
    private String categoria;
    private Integer fkIdFor;
    private Integer fkIdCli;
    private Integer fkIdCat;

    public Produto(){

    }

    public Produto(String sKUCodigoUnico, String nome, double valor, int quantidade, String descricao, String categoria, Integer fkIdFor, Integer fkIdCli , Integer fkIdCat){
        this.sKUCodigoUnico = sKUCodigoUnico;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.fkIdFor = fkIdFor;
        this.fkIdCli = fkIdCli;
        this.fkIdCat = fkIdCat;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getsKUCodigoUnico(){
        return sKUCodigoUnico;
    }

    public String getNome(){
        return nome;
    }

    public Double getValor(){
        return valor;
    }

    public int getQuantidade(){
        return  quantidade;
    }

    public String getDescricao(){
        return descricao;
    }
    
    public String getCategoria(){
        return categoria;
    }

    public Integer getFkIdFor() {
        return fkIdFor;
    }

    public Integer getFkIdCli() {
        return fkIdCli;
    }

    public Integer getFkIdCat() {
        return fkIdCat;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public void setsKUCodigoUnico(String sKUCodigoUnico) {
        this.sKUCodigoUnico = sKUCodigoUnico;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFkIdFor(Integer fkIdFor) {
        this.fkIdFor = fkIdFor;
    }

    public void setFkIdCli(Integer fkIdCli) {
        this.fkIdCli = fkIdCli;
    }

    public void setFkIdCat(Integer fkIdCat) {
        this.fkIdCat = fkIdCat;
    }

    public Double valorTotal(){
        return valor * quantidade;
    }
}
