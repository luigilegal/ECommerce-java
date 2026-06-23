package application.entidades;

public class Fornecerdor extends Usuario{

    private String cnjp;
    private String dataReg;
    private String fkIdEndereco;


    public Fornecerdor(){

    }

    public Fornecerdor(String idUser, String cnjp, String nome, String email, String senha, String telefone, String fkIdEndereco){
        super(idUser,nome, email, senha, telefone);
        this.cnjp = cnjp;
        this.fkIdEndereco = fkIdEndereco;
    }


    public String getCnjp() {
        return cnjp;
    }

    public String getDataReg() {
        return dataReg;
    }

    public String getFkIdEndereco() {
        return fkIdEndereco;
    }

    public void setCnjp(String cnjp) {
        this.cnjp = cnjp;
    }

    public void setDataReg(String dataReg) {
        this.dataReg = dataReg;
    }

    public void setFkIdEndereco(String fkIdEndereco) {
        this.fkIdEndereco = fkIdEndereco;
    }

    @Override
    public void showData(){
        System.out.println("\nMeu dados:\n");
        System.out.println("--------------------------------------------------");
        System.out.println("NOME:..............|" + getNome());
        System.out.println("TELEFONE:..........|" + getTelefone());
        System.out.println("DATA REGEISTRO:....|" + getDataReg());
        System.out.println("--------------------------------------------------");
    }
}