package application.entidades;

public abstract class Usuario {

    protected String idUser;
    protected String nome;
    protected String email;
    protected String senha;
    protected String telefone;
    protected boolean verify;

    public Usuario(){

    }
    public Usuario(String idUser, String nome, String email, String senha, String telefone){
        this.idUser = idUser;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean getVerify(){
        return verify;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    protected abstract void showData();

}
