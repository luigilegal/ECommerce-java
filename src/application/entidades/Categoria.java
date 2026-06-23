package application.entidades;

public class Categoria {
    private String idCategoria;
    private String nome;
    private String descricao;

    public  Categoria (){

    }

    public Categoria(String idCategoria, String nome , String descricao){

        this.idCategoria = idCategoria;
        this.nome = nome;
        this.descricao = descricao;

    }
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString(){

        return getNome();
    }
}
