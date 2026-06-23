package application.services;

import application.App;
import application.dao.ClienteDAO;
import application.dao.EnderecoDAO;
import application.entidades.Cliente;
import application.entidades.Endereco;
import application.entidades.Usuario;

import java.util.Scanner;

public class EnderecoService {

    private ClienteDAO clienteDAO;
    private Cliente cliente;
    private Endereco endereco;
    private EnderecoDAO enderecoDAO;
    private ValidarLogin login;
    private Usuario user;

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public EnderecoDAO getEnderecoDAO() {
        return enderecoDAO;
    }

    public ValidarLogin getLogin() {
        return login;
    }

    public Usuario getUser() {
        return user;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setEnderecoDAO(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void setLogin(ValidarLogin login) {
        this.login = login;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void cadastrarEndereco(Endereco endereco){

        setEndereco(endereco);
        Scanner sc = new Scanner(System.in);
        System.out.println("Coloque seu endereco");
        do {
            System.out.print("*PAIS: ");
            getEndereco().setPais(sc.nextLine());

        }while (!getEndereco().getPais().matches("^[\\p{L}]+$") || getEndereco().getPais().length() > 15);

        do {
            System.out.print("*ESTADO: ");
            getEndereco().setEstado(sc.nextLine());
            if (getEndereco().getEstado().length() != 2){
                System.out.println("*Estado (xx).");
            }
        }while(!getEndereco().getEstado().matches("^[\\p{L}]+$") || getEndereco().getEstado().length() != 2);

        do {
            System.out.print("*CIDADE: ");
            getEndereco().setCidade(sc.nextLine());
        }while (!getEndereco().getCidade().matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || getEndereco().getCidade().length() > 50);

        do {
            System.out.print("*BAIRRO: ");
            getEndereco().setBairro(sc.nextLine());
        }while (!getEndereco().getBairro().matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || getEndereco().getBairro().length() > 50);

        do {
            System.out.print("*Numero do endereco: ");
            getEndereco().setNumEndereco(sc.nextLine());
        }while (!(getEndereco().getNumEndereco().matches("^\\d+$")) || getEndereco().getNumEndereco().length() > 8);

        do {
            System.out.print("*CEP: ");
            getEndereco().setCep(sc.nextLine());
        }while (!(getEndereco().getCep().matches("^\\d+$")) || getEndereco().getCep().length() != 8);

        System.out.print("Informacao auxiliar (Opcional): ");
        getEndereco().setInfoAux(sc.nextLine());
        if (getEndereco().getInfoAux().matches("^[\\p{L}\\d ]+$") || getEndereco().getInfoAux().length() < 50){
            getEndereco().setInfoAux(getEndereco().getInfoAux());
        }
        else if (getEndereco().getInfoAux().isEmpty() || getEndereco().getInfoAux().isBlank() || getEndereco().getInfoAux().length() > 50){
            getEndereco().setInfoAux(null);
        }
        getEnderecoDAO().insertEndereco(getEndereco());
    }
    public void alterData(Endereco endereco){

        checkIduser(endereco);

        setEndereco(endereco);

        String select;
        do {
            if (getEndereco().getInfoAux() == null) {
                getEndereco().setInfoAux("Optou por nao dar.");
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("\nMeu endereco");
            System.out.print("\n1-Pais:                |" + getEndereco().getPais() +
                    "\n2-Estado:              |" + getEndereco().getEstado() +
                    "\n3-Cidade:              |" + getEndereco().getCidade() +
                    "\n4-Bairro:              |" + getEndereco().getBairro() +
                    "\n5-Numero de endereco:  |" + getEndereco().getNumEndereco() +
                    "\n6-CEP:                 |" + getEndereco().getCep() +
                    "\n7-Informacao auxiliar: |" + getEndereco().getInfoAux() +
                    "\n0-Voltar." +
                    "\n\nSelecione: "
            );
            select = sc.nextLine();
            switch (select) {
                case "1" -> updatePais();

                case "2" -> updateEstado();

                case "3" -> updateCidade();

                case "4" -> updateBairro();

                case "5" -> updateNumEnd();

                case "6" -> updateCep();

                case "7" -> updateInfAux();

                case "0" -> System.out.println("Voltando...");

                default -> System.out.println("Invalido.");
            }
        } while (!select.equals("0"));
    }

    public void updatePais(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET Pais = ? WHERE FK_id_cliente = ?";
        String identifier = "Pais";
        do {
            System.out.print("*PAIS: ");
            getEnderecoDAO().setSql(sc.nextLine());
        } while (!getEnderecoDAO().getSql().matches("^[\\p{L}]+$") || getEnderecoDAO().getSql().length() > 15);
        getEnderecoDAO().update(sql, identifier);

        if (getEnderecoDAO().getVerify()) {
            endereco.setPais(getEnderecoDAO().getSql());
        }

    }
    public void updateEstado(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET Estado = ? WHERE FK_id_cliente = ?";
        String identifier = "Estado";
        do {
            System.out.print("*ESTADO: ");
            getEnderecoDAO().setSql(sc.nextLine());
            if (getEnderecoDAO().getSql().length() != 2) {
                System.out.println("*Estado (xx).");
            }
        } while (!getEnderecoDAO().getSql().matches("^[\\p{L}]+$") || getEnderecoDAO().getSql().length() != 2);
        getEnderecoDAO().update(sql, identifier);
        if (getEnderecoDAO().getVerify()) {
            endereco.setEstado(getEnderecoDAO().getSql());

        }
    }
    public void updateCidade(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET Cidade = ? WHERE FK_id_cliente = ?";
        String identifier = "Cidade";
        do {
            System.out.print("*CIDADE: ");
            getEnderecoDAO().setSql(sc.nextLine());
        } while (!getEnderecoDAO().getSql().matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || getEnderecoDAO().getSql().length() > 50);
        getEnderecoDAO().update(sql, identifier);
        if (getEnderecoDAO().getVerify()) {
            endereco.setCidade(getEnderecoDAO().getSql());
        }

    }
    public void updateBairro(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET Bairro = ? WHERE FK_id_cliente = ?";
        String identifier = "Bairro";
        do {
            System.out.print("*BAIRRO: ");
            getEnderecoDAO().setSql(sc.nextLine());
        } while (!getEnderecoDAO().getSql().matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || getEnderecoDAO().getSql().length() > 50);
        getEnderecoDAO().update(sql, identifier);
        if (getEnderecoDAO().getVerify()) {
            endereco.setBairro(getEnderecoDAO().getSql());
        }


    }
    public void updateNumEnd(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET Num_endereco = ? WHERE FK_id_cliente = ?";
        String identifier = "Numero de endereco";
        do {
            System.out.print("*Numero do endereco: ");
            getEnderecoDAO().setSql(sc.nextLine());
        } while (!(getEnderecoDAO().getSql().matches("^\\d+$")) || getEnderecoDAO().getSql().length() > 8);
        getEnderecoDAO().update(sql, identifier);
        if (getEnderecoDAO().getVerify()) {
            endereco.setNumEndereco(getEnderecoDAO().getSql());
        }

    }
    public void updateCep(){
        Scanner sc = new Scanner(System.in);

        String sql = "UPDATE endereco SET CEP = ? WHERE FK_id_cliente = ?";
        String identifier = "CEP";
        do {
            System.out.print("*CEP: ");
            getEnderecoDAO().setSql(sc.nextLine());
        } while (!(getEnderecoDAO().getSql().matches("^\\d+$")) || getEnderecoDAO().getSql().length() != 8);
        getEnderecoDAO().update(sql, identifier);
        if (getEnderecoDAO().getVerify()) {
            endereco.setCep(getEnderecoDAO().getSql());
        }
    }
    public void updateInfAux(){
        Scanner sc = new Scanner(System.in);
        String sql = "UPDATE endereco SET info_aux = ? WHERE FK_id_cliente = ?";
        String identifier = "Informacao auxiliar";
        System.out.print("Informacao auxiliar (Opcional): ");

        getEnderecoDAO().setSql(sc.nextLine());
        if (getEnderecoDAO().getSql().matches("^[\\p{L}\\d ]+$") || getEnderecoDAO().getSql().length() < 50) {
            getEnderecoDAO().setSql(getEnderecoDAO().getSql());
            getEnderecoDAO().update(sql, identifier);
            if (getEnderecoDAO().getVerify()) {
                endereco.setInfoAux(getEnderecoDAO().getSql());
            }
        } else if (getEnderecoDAO().getSql().isEmpty() || getEnderecoDAO().getSql().isBlank() || getEnderecoDAO().getSql().length() > 50) {
            getEnderecoDAO().setSql(null);
        }
    }

    public void checkEndereco(Endereco endereco){


        setEndereco(endereco);
        String select;
        boolean verify;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("\n\nFoi indentificado que voce nao colocou nenhum endereco para querer alterar."
                    + "\nDeseja colocar um endereco?"
                    + "\n\n1-Sim.\n2-Nao."
                    + "\n\nSelecione: "
            );
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    cadastrarEndereco(endereco);
                    verify = false;
                }
                case "2" -> {
                    System.out.println("Voltando.");
                    verify = false;
                    if (getLogin().getChoose()){
                        App.configCliente();

                    }else {
                        App.mainframeFornecerdor();
                    }
                }

                default -> {
                    System.out.println("Invalido.");
                    verify = true;
                }
            }
        } while (verify);
    }

    public void checkIduser(Endereco endereco){
        getEnderecoDAO().getVaribles();

        System.out.println(getEndereco().getFkIdCli());
        System.out.println(getEndereco().getFkIDfor());
        System.out.println(getLogin().getChoose());
        System.out.println(getUser().getIdUser());

        if (getLogin().getChoose()){
            if (!(getUser().getIdUser().equals(getEndereco().getFkIdCli()))){
                System.out.println(getEndereco().getFkIdCli());
                checkEndereco(endereco);
            }
        }else {
            if (!(getUser().getIdUser().equals(getEndereco().getFkIDfor()))){
                checkEndereco(endereco);
            }
        }
    }

    public void chooseEndereco(){
        String select;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(
                    "\nDeseja colocar seu endereco para fazer"
                            +" compras e vender produtos?"
                            +"\n(Sim: 1/Nao: 2): "
            );
            select = sc.nextLine();
            switch (select){
                case "1" ->{
                    System.out.println("Por favor Insira os dados abaixo: ");
                    cadastrarEndereco(getEndereco());
                }
                case "2"-> System.out.println("Voltando...");

                default -> System.out.println("invalido");
            }
        }while(!select.equals("2"));
    }

    public void showEndereco(){
        getEnderecoDAO().getVaribles();
        System.out.println("\nMeu endereco: \n");
        System.out.println("--------------------------------------------------");
        System.out.println("PAIS:................|"+getEndereco().getPais());
        System.out.println("ESTADO:..............|"+getEndereco().getEstado());
        System.out.println("CIDADE:..............|"+getEndereco().getCidade());
        System.out.println("BAIRRO:..............|"+getEndereco().getBairro());
        System.out.println("NUMERO DE ENDERECO:..|"+getEndereco().getNumEndereco());
        System.out.println("CEP:.................|"+getEndereco().getCep());
        System.out.println("INFORMACAO AUXILIAR:.|"+getEndereco().getInfoAux());
        System.out.println("--------------------------------------------------");
    }
}
