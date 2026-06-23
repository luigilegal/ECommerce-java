package application.entidades;

public class Endereco {

    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String numEndereco;
    private String cep;
    private String infoAux;
    private String fkIdCli;
    private String fkIDfor;

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumEndereco() {
        return numEndereco;
    }

    public String getCep() {
        return cep;
    }

    public String getInfoAux() {
        return infoAux;
    }

    public String getFkIdCli() {
        return fkIdCli;
    }

    public String getFkIDfor() {
        return fkIDfor;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setNumEndereco(String numEndereco) {
        this.numEndereco = numEndereco;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setInfoAux(String infoAux) {
        this.infoAux = infoAux;
    }

    public void setFkIdCli(String fkIdCli) {
        this.fkIdCli = fkIdCli;
    }

    public void setFkIDfor(String fkIDfor) {
        this.fkIDfor = fkIDfor;
    }

    public Endereco(){

    }
    public Endereco(String pais, String estado, String cidade, String bairro, String numEndereco, String cep, String infoAux){
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numEndereco = numEndereco;
        this.cep = cep;
        this.infoAux = infoAux;
    }
}
