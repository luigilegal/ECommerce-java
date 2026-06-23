package application.entidades;

import application.services.ClienteService;

public class Cliente extends Usuario{

    private String cpf;
    private String sexo;
    private String dataNacimento;
    private String dataReg;
    private String idPerm;

    public Cliente() {

    }

    public Cliente(String idUser,String nome, String email, String cpf, String senha, String sexo, String telefone, String dataNacimento) {
        super(idUser,nome,email,senha,telefone);
        this.cpf = cpf;
        this.sexo = sexo;
        this.telefone = telefone;
        this.dataNacimento = dataNacimento;

    }

    public String getCpf() {
        return cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public String getDataNacimento() {
        return dataNacimento;
    }

    public String getDataReg() {
        ClienteService cService = new ClienteService();
        if (this.dataReg == null){
            return dataReg = cService.dataRegnow();
        }else {
            return dataReg;
        }
    }

    public String getIdPerm() {
        return idPerm;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDataNacimento(String dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public void setDataNacimento(String ano, String mes, String dia) {
        this.dataNacimento = ano + "-" + mes + "-" + dia;
    }

    public void setIdPerm(String idPerm) {
        this.idPerm = idPerm;
    }

    public void setDataReg(String dataReg) {
        this.dataReg = dataReg;
    }

    @Override
    public void showData(){

        System.out.println("\nMeu dados:\n");
        System.out.println("--------------------------------------------------");
        System.out.println("NOME:..............|" + getNome());
        System.out.println("SEXO:..............|" + getSexo());
        System.out.println("TELEFONE:..........|" + getTelefone());
        System.out.println("DATA DE NACIMENTO:.|" + getDataNacimento());
        System.out.println("DATA REGEISTRO:....|" + getDataReg());
        System.out.println("--------------------------------------------------");

    }
}