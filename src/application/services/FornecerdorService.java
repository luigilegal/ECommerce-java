package application.services;

import application.App;
import application.dao.FornecerdorDAO;
import application.entidades.Fornecerdor;
import application.entidades.Usuario;

import java.time.Instant;
import java.util.Calendar;
import java.util.Scanner;

public class FornecerdorService {

    private FornecerdorDAO fornecerdorDAO;
    private Fornecerdor fornecerdor;
    private EnderecoService enderecoService;

    public FornecerdorDAO getFornecerdorDAO() {
        return fornecerdorDAO;
    }

    public Fornecerdor getFornecerdor() {
        return fornecerdor;
    }

    public EnderecoService getEnderecoService() {
        return enderecoService;
    }

    public void setFornecerdorDAO(FornecerdorDAO fornecerdorDAO) {
        this.fornecerdorDAO = fornecerdorDAO;
    }

    public void setFornecerdor(Fornecerdor fornecerdor) {
        this.fornecerdor = fornecerdor;
    }

    public void setEnderecoService(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public void register(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Registre o dados do fornecerdor:");

        do {
            System.out.print("\nCNPJ: ");
            getFornecerdor().setCnjp(sc.nextLine());
        }while (!getFornecerdor().getCnjp().matches("^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$"));
        do {
            System.out.print("\n*Nome da empresa: ");
            getFornecerdor().setNome(sc.nextLine());
        }while (!getFornecerdor().getNome().matches("^[\\p{L} ]+$"));
        do {
            System.out.print("\n*EMAIL: ");
            getFornecerdor().setEmail(sc.nextLine());
        }while (!getFornecerdor().getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
        do {
            System.out.print("\nSENHA: ");
            getFornecerdor().setSenha(sc.nextLine());
        }while (!getFornecerdor().getSenha().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$") || getFornecerdor().getSenha().length() > 32);

        System.out.print("Telefone (Opcional): ");
        getFornecerdor().setTelefone(sc.nextLine());
        if (getFornecerdor().getTelefone().matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") && getFornecerdor().getTelefone().length() == 13){
            getFornecerdor().setTelefone("55+ "+ getFornecerdor().getTelefone());
        }else if (getFornecerdor().getTelefone().isEmpty() || getFornecerdor().getTelefone().isBlank() || getFornecerdor().getTelefone().matches("^[^\\d]+$") || getFornecerdor().getTelefone().length() != 13) {
            getFornecerdor().setTelefone(null);

        }

        getFornecerdorDAO().insert(getFornecerdor());

    }

    public void configuracao(){
        String select;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("\nConfiguracoes:\n"
                    +"\n1-Meus dados."
                    +"\n2-Alterar meus dados."
                    +"\n3-Alterar meu endereco."
                    +"\n4-Deletar minha conta."
                    +"\n0-Sair"
                    +"\n\nSelecione: "
            );
            select = sc.nextLine();
            switch (select){
                case "1"-> dadosFornecerdor();

                case "2"-> alterData();

                case "3"-> getEnderecoService().alterData(getEnderecoService().getEndereco());

                case "4"-> confirmar();

                case "0"-> System.out.println("Voltando...");

                default -> System.out.println("invalido");
            }

        }while (!select.equals("0"));
    }
    public void dadosFornecerdor(){
        getFornecerdor().showData();
        getEnderecoService().showEndereco();
    }
    public void alterData(){
        String select;
        Scanner sc = new Scanner(System.in);
        getFornecerdorDAO().setVerify(false);
        do {
            System.out.println("\nMeus Dados\n" +
                               "--------------------------------------------------"+
                               "\n1-NOME:.............|" + getFornecerdor().getNome() +
                               "\n2-TELEFONE:.........|" + getFornecerdor().getTelefone() +
                               "\n--Data de registro:.|" + getFornecerdor().getDataReg() +
                               "\n0-Sair."
            );
            System.out.println("--------------------------------------------------");
            System.out.print("\nSelecione: ");
            select = sc.nextLine();

            switch (select){
                case "1" -> updateNome();

                case "2" -> updateTelefone();

                case "0" -> System.out.println("Voltando...");

                default -> System.out.println("Invalido.");
            }

        }while (!select.equals("0"));
    }
    public void updateNome(){
        Scanner sc = new Scanner(System.in);
        String sql = "UPDATE fornecerdor set nome = ? WHERE ID_fornecerdor = ?";
        String identifier = "Nome";
        do {
            System.out.print("\n*Nome da empresa: ");
            getFornecerdorDAO().setSql(sc.nextLine());
        }while (!getFornecerdorDAO().getSql().matches("^[\\p{L} ]+$"));
        getFornecerdorDAO().update(sql, identifier);
        if (getFornecerdorDAO().getVerify()){
            getFornecerdor().setNome(getFornecerdorDAO().getSql());
        }
    }
    public void updateTelefone(){
        Scanner sc = new Scanner(System.in);
        String sql = "UPDATE fornecerdor SET Telefone = ? WHERE ID_fornecerdor = ?";
        String identifer = "Telefone";

        System.out.print("TELEFONE (Opcional): ");
        getFornecerdorDAO().setSql(sc.nextLine());

        if (getFornecerdorDAO().getSql().matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") && getFornecerdorDAO().getSql().length() == 13){
            getFornecerdorDAO().update(sql, identifer);
            if (getFornecerdorDAO().getVerify()){
                getFornecerdorDAO().setSql("55+ "+ getFornecerdorDAO().getSql());
            }
        }else if (getFornecerdorDAO().getSql().isEmpty() || getFornecerdorDAO().getSql().isBlank() || getFornecerdorDAO().getSql().matches("^[^\\d]+$") || getFornecerdorDAO().getSql().length() != 13) {
            getFornecerdor().setTelefone(null);

        }
    }

    public void confirmar(){

        Scanner sc = new Scanner(System.in);

        boolean verfiy;
        String select;
        String sql = "DELETE FROM fornecerdor WHERE id_Fornecerdor = ?";
        String identifier = "Fornecerdor";
        getFornecerdorDAO().setVerify(false);
        do {
            System.out.print("\nTem certeza que quer deletar sua conta?\n" +
                               "\n1-Sim." +
                               "\n2-Nao." +
                               "\n\nSelecione: "
            );
            select = sc.nextLine();
            switch (select){

                case "1" ->{
                    System.out.println("Deletando...");
                    verfiy = false;
                    getFornecerdorDAO().delete(sql, identifier);
                    if (getFornecerdorDAO().getVerify()){
                        nullify();
                        App.callerMain();
                    }
                }

                case "2" ->{
                    System.out.println("Voltando...");
                    verfiy = false;
                }

                default -> {
                    System.out.println("INVALIDO.");
                    verfiy = true;
                }
            }

        }while (verfiy);
    }

    public void nullify(){
        getFornecerdor().setIdUser(null);
        getFornecerdor().setNome(null);
        getFornecerdor().setCnjp(null);
        getFornecerdor().setEmail(null);
        getFornecerdor().setSenha(null);
        getFornecerdor().setTelefone(null);
    }

    public String dataRegNow(){
        Calendar data = Calendar.getInstance();
        Instant data2 = data.toInstant();
        String dt = data2.toString();
        int index = dt.indexOf("T");
        return dt.substring(0, index);
    }
}
