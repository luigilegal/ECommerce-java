package application.services;

import application.App;
import application.dao.ClienteDAO;
import application.dao.EnderecoDAO;
import application.entidades.Cliente;
import application.entidades.Endereco;

import java.time.Instant;
import java.util.Calendar;
import java.util.Scanner;

public class ClienteService {

    private Cliente cliente;
    private ClienteDAO clienteDAO;
    private Endereco endereco;
    private EnderecoDAO enderecoDAO;

    public EnderecoDAO getEnderecoDAO() {
        return enderecoDAO;
    }

    public void setEnderecoDAO(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private final Scanner sc = new Scanner(System.in);
    public void cadastrarEndereco(){

    }
    public void register(){

        Scanner sc = new Scanner(System.in);

        String ano, mes, dia, select;
        boolean verify = false;

        System.out.println("Registre-se");
        do {
            System.out.print("*NOME: ");
            getCliente().setNome(sc.nextLine());
        }while (!getCliente().getNome().matches("^[\\p{L} ]+$") || getCliente().getNome().length() < 4);

        do {
            System.out.print("*EMAIL: ");
            getCliente().setEmail(sc.nextLine());
        } while (getCliente().getEmail().isEmpty() || !getCliente().getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));

        do{
            System.out.print("*CPF: ");
            getCliente().setCpf(sc.nextLine());
        }while (!(getCliente().getCpf().length() == 11) || !getCliente().getCpf().matches("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$"));

        do {
            System.out.print("*SENHA: ");
            getCliente().setSenha(sc.nextLine());
        }while (!getCliente().getSenha().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$") || getCliente().getSenha().length() > 32);

        do{
            System.out.print("Qual sexo voce se indentifica?\n\n1-Masculino.\n2-Feminino.\n0-Prefiro nao dizer\n\nSelecione: ");
            select = sc.nextLine();
            switch (select){
                case "1" -> {
                    getCliente().setSexo("M");
                    verify = false;
                }

                case "2" -> {
                    getCliente().setSexo("F");
                    verify = false;
                }

                case "0" -> {
                    System.out.println("Entendo.");
                    verify = false;
                }

                default -> {
                    System.out.println("Invalido");
                    verify = true;
                }
            }
        }while (verify);

        System.out.print("Telefone (Opcional): ");
        getCliente().setTelefone(sc.nextLine());
        if (getCliente().getTelefone().matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") && getCliente().getTelefone().length() == 13){
            getCliente().setTelefone("55+ "+ getCliente().getTelefone());
        }else if (getCliente().getTelefone().isEmpty() || getCliente().getTelefone().isBlank() || getCliente().getTelefone().matches("^[^\\d]+$") || getCliente().getTelefone().length() != 13) {
            getCliente().setTelefone(null);

        }
        System.out.println(getCliente().getTelefone());
        do {
            verify = false;
            System.out.println("*Data de nacimento.");
            System.out.print("*DIA (dd): ");
            dia = sc.nextLine();
            if (dia.length() == 1){
                dia = "0" + dia;
            }
            try {
                int d = Integer.parseInt(dia);
                if (d > 31){
                    System.out.println("Porfavor digite um numero valido");
                    verify = true;
                }
            }catch (Exception e){
                System.out.println("porfavor digite um numero");
                verify = true;
            }
            System.out.println(verify);
        }while (!(dia.length() <= 2) || !dia.matches("^\\d+$")|| verify);

        do{
            verify = false;
            System.out.print("*MES (mm): ");
            mes = sc.nextLine();
            if(mes.length() == 1){
                mes = "0" + mes;
            }
            try{
                int m = Integer.parseInt(mes);
                if (m > 12){
                    verify = true;
                }
            }catch (Exception e){
                System.out.println("Porfavor digite um numero");
                verify = true;
            }
        }while (!(mes.length() <= 2) || !mes.matches("^\\d+$") || verify);

        do{
            System.out.print("*ANO (aaaa): ");
            ano = sc.nextLine();
            System.out.println(ano.length() == 4);
            System.out.println(ano.matches("^\\d+$"));
        }while(!(ano.length() == 4) || !ano.matches("^\\d+$"));

        getCliente().setDataNacimento(ano,mes,dia);
        setCliente(getCliente());
        getClienteDAO().insertion(getCliente());
    }
    public void confirmar(){
        String select;
        boolean verify = false;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.print("Tem certeza que quer excluir sua conta?\n1-SIM.\n2-NAO.\nSelecione: ");
            select = sc.nextLine();
            switch (select){
                case "1" -> {
                    String sql = "DELETE FROM cliente where ID_cliente = ?";
                    String identifier = "Conta";
                    System.out.println("Voce fez sua escolha.");
                    getClienteDAO().delete(sql, identifier);
                    verify = false;
                    if (getClienteDAO().getVerify()){
                        nullify();
                        App.main(new String[]{});
                    }

                }

                case "2" -> {
                    System.out.println("Voltando..");
                    verify = false;
                }
                default -> {
                    System.out.println("\nInvalido.");
                    verify = true;
                }
            }

        }while (verify);
    }
    public void alterDataCliente(Cliente cliente){
        String select, dia, mes, ano;
        boolean verify = false;

        if (getCliente() == null){
            setCliente(cliente);
        }

        setCliente(getClienteDAO().verGetVar());

        if (getCliente().getSexo() == null){
            getCliente().setSexo("Optou por nao dizer.");
        }
        if (getCliente().getTelefone() == null){
            getCliente().setTelefone("Optou por nao dizer.");
        }

        do {
            System.out.print("\n\nMeus dados:\n"
                    +"\n1-Nome:              | " + cliente.getNome()
                    +"\n2-sexo:              | " + cliente.getSexo()
                    +"\n3-Telefone:          | " + cliente.getTelefone()
                    +"\n4-Data de nacimento: | " + cliente.getDataNacimento()
                    +"\n--Data de registro:  | " + cliente.getDataReg() + "--"
                    +"\n0-Sair"
                    +"\n\nSelecione: "
            );
            select = sc.nextLine();
            getClienteDAO().setVerify(false);
            switch (select){
                case "1" ->{
                    String sql = "UPDATE cliente SET nome = ? where ID_cliente = ? limit 1;";
                    String identifier = "Nome";
                    do {
                        System.out.print("*NOME: ");
                        getClienteDAO().setSql(sc.nextLine());
                    }while (!getClienteDAO().getSql().matches("^[\\p{L} ]+$") || getClienteDAO().getSql().length() < 4);
                    getClienteDAO().update(sql, identifier);
                    if (getClienteDAO().getVerify()){
                        getCliente().setNome(getClienteDAO().getSql());
                    }
                }
                case "2" ->{
                    String sql = "UPDATE cliente SET Sexo = ? where ID_cliente = ? limit 1;";
                    do{
                        System.out.println("Qual sexo voce se indentifica?\n\n1-Masculino.\n2-Feminino.\n0-Sair.\n\nSelecione:");
                        select = sc.nextLine();
                        switch (select){
                            case "1" -> {
                                getClienteDAO().setSql("M");
                                verify = false;
                            }

                            case "2" -> {
                                getClienteDAO().setSql("F");
                                verify = false;
                            }

                            case "0" -> System.out.println("Voltando...");

                            default -> {
                                System.out.println("Invalido");
                                verify = true;
                            }
                        }
                        if (select.equals("1") || select.equals("2")){
                            String identifier = "Sexo";
                            getClienteDAO().update(sql, identifier);
                        }
                        if (getClienteDAO().getVerify()){
                            getCliente().setSexo(getClienteDAO().getSql());
                        }

                    }while (verify);

                }
                case "3" ->{
                    String identifier = "Telefone";
                    String sql = "UPDATE cliente set telefone = ? where ID_cliente = ? limit 1;";
                    System.out.print("Telefone (Opcional): ");
                    getClienteDAO().setSql(sc.nextLine());
                    if (getClienteDAO().getSql().matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") && getClienteDAO().getSql().length() == 13){
                        getClienteDAO().setSql(getCliente().getTelefone());
                    }else if (getClienteDAO().getSql().isEmpty() || getClienteDAO().getSql().isBlank() || getClienteDAO().getSql().matches("^[^\\d]+$")) {
                        getClienteDAO().setSql(null);

                    }
                    getClienteDAO().update(sql,identifier);
                    if (getClienteDAO().getVerify()){
                        getCliente().setTelefone(getClienteDAO().getSql());
                    }
                }


                case "4" -> {
                    String identifier = "Data de nacimento";
                    String sql = "UPDATE cliente SET data_nascimento = ? where ID_cliente = ?";
                    do {
                        System.out.println("*Data de nacimento: DIA-MES-ANO: ");
                        System.out.print("*DIA: ");
                        dia = sc.nextLine();
                        if (dia.length() == 1){
                            dia = "0" + dia;
                        }
                        try {
                            int d = Integer.parseInt(dia);
                            if (d > 31){
                                System.out.println("Porfavor digite um numero valido");
                                verify = true;
                            }
                        }catch (Exception e){
                            System.out.println("porfavor digite um numero");
                            verify = true;
                        }

                    }while (!(dia.length() <= 2) || !dia.matches("^\\d+$")|| verify);

                    do{
                        System.out.print("*MES: ");
                        mes = sc.nextLine();
                        if(mes.length() == 1){
                            mes = "0" + mes;
                        }
                        try{
                            int m = Integer.parseInt(mes);
                            if (m > 12){
                                verify = true;
                            }
                        }catch (Exception e){
                            System.out.println("Porfavor digite um numero");
                            verify = true;
                        }
                    }while (!(mes.length() <= 2) || !mes.matches("^\\d+$") || verify);

                    do {
                        System.out.print("*ANO: ");
                        ano = sc.nextLine();
                        System.out.println(ano.length() == 4);
                        System.out.println(ano.matches("^\\d+$"));
                    }while(!(ano.length() == 4) || !ano.matches("^\\d+$"));
                    getClienteDAO().setSql(ano,mes,dia);
                    getClienteDAO().update(sql,identifier);
                    if(getClienteDAO().getVerify()){
                        getCliente().setDataNacimento(getClienteDAO().getSql());
                    }
                }
                case "0" -> System.out.println("Voltando...");

                default -> System.out.println("Invalido");
            }
        }while (!select.equals("0"));
    }

    public String dataRegnow() {
        Calendar data = Calendar.getInstance();
        Instant data2 = data.toInstant();
        String dt = data2.toString();
        int index = dt.indexOf("T");
        return dt.substring(0, index);
    }

    public void nullify(){
        getCliente().setIdUser(null);
        getCliente().setNome(null);
        getCliente().setEmail(null);
        getCliente().setSenha(null);
        getCliente().setSexo(null);
        getCliente().setTelefone(null);
        getCliente().setDataNacimento(null);
        getCliente().setDataReg(null);
        getCliente().setIdPerm(null);
        getEndereco().setFkIdCli(null);
    }
    public void showClienteinfos(){

        getCliente().showData();

    }
}
