package application;

import java.util.Locale;
import java.util.Scanner;

import application.dao.*;
import application.entidades.*;
import application.services.*;

public class App {

    public static Endereco endereco = new Endereco();
    public static EnderecoDAO enderecoDAO = new EnderecoDAO();
    public static EnderecoService enderecoService = new EnderecoService();

    public static Usuario fornecerdor = new Fornecerdor();
    public static Usuario cliente = new Cliente();

    public static FornecerdorDAO fornecerdorDAO = new FornecerdorDAO();
    public static FornecerdorService fornecerdorService = new FornecerdorService();

    public static ClienteDAO clienteDAO = new ClienteDAO();
    public static ClienteService clienteService = new ClienteService();

    public static CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
    public static Carrinho carrinho = new Carrinho();

    public static ItemCarrinhoService itemCarrinhoService = new ItemCarrinhoService();
    public static ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
    public static ItemCarrinho itemCarrinho = new ItemCarrinho();
    public static Pedido pedido= new Pedido();
    public static PedidoDAO pedidoDAO= new PedidoDAO();
    public static PedidoService pedidoService = new PedidoService();

    public static ValidarLogin login = new ValidarLogin();

    public static Produto produto = new Produto();
    public static ProdutoDAO produtoDAO = new ProdutoDAO();
    public static ProdutoService produtoService = new ProdutoService();

    public static Categoria categoria = new Categoria();
    public static CategoriaService categoriaService = new CategoriaService();
    public static CategoriaDAO categoriaDAO = new CategoriaDAO();
    public static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        String select;
        Locale.setDefault(Locale.US);
        do {
            System.out.print("\nSeja bem vindo ao E-commerce!\n\n1-Login.\n2-Registre-se.\n3-Continuar como visitante.\n0-Sair.\n\nSelecione:");


            select = sc.nextLine();
            switch (select){
                case "1" ->{
                    modoDeLogin();
                }

                case "2" -> {
                    modoDeReg();
                }

                case "3" -> {
                    mainframeGuest();

                }

                case "0"-> {
                    System.out.println("Adios...");
                    System.exit(0);
                }

                default -> System.out.println("Invalido");
            }
        }while (!select.equals("0"));
    }

    public static void mainframeGuest(){
        String select2;
        do {
            System.out.print("\n\nBem vindo Convidado\n\n1-Categorias\n2-Produtos\n0-Sair\n\nSelecione:");
            select2 = sc.nextLine();
            switch(select2){
                case "1"->{

                    System.out.println("Not getter: " + clienteService.dataRegnow());
                }

                case "2" -> System.out.println("nada");

                case "0" -> {
                    System.out.println("Voltando...");
                    callerMain();
                }

                default-> System.out.println("invalido");
            }
        }while(!select2.equals("0"));

    }

    public static void mainframeClient() {
        String select;
        do {
            System.out.print("\n\nECOMMERCE\n\n1-Categorias\n2-Produtos\n3-Publicar produtos\n4-Carrinho\n5-Meus pedidos\n6-Configuracoes\n0-Sair\n\nSelecione: ");
            select = sc.nextLine();
            switch (select) {
                case "1" ->{
                    categoriaCaller();
                    categoriaService.listCategoria();
                }

                case "2" ->{
                    categoriaCaller();
                    produtoService.setUser(cliente);
                    produtoService.listProduto();
                }

                case "3" -> {
                    carrinhoCaller();
                    carrinhoDAO.getId();
                    produtoService.setUser(cliente);
                    produtoService.register();
                }

                case "4" -> {
                    categoriaCaller();
                    itemCarrinhoService.listItems();
                }

                case "5" ->{
                    carrinhoDAO.getId();
                }

                case "6" -> configCliente();

                case "0" -> {
                    System.out.println("Voltando...");
                }

                default -> System.out.println("invalido");
            }
        } while (!select.equals("0"));
    }

    public static void mainframeFornecerdor(){
        String select;
        do {
            System.out.print("\n\nECOMMERCE\n\n1-Categorias\n2-Produtos\n3-Publicar produtos\n4-Configuracoes\n0-Sair\n\nSelecione: ");
            select = sc.nextLine();
            switch (select){
                case "1" -> System.out.println("");

                case "2" -> System.out.println("");

                case "3" -> {
                    categoriaCaller();
                    produtoService.setUser(fornecerdor);
                    produtoService.register();
                }

                case "4" -> fornecerdorService.configuracao();

                case "0" -> System.out.println("Saindo...");

                default -> System.out.println("INVALIDO.");
            }
        }while (!select.equals("0"));

    }

    public static void modoDeLogin(){

        boolean verify;
        String select;

        do {
            System.out.print("\nComo deseja fazer login?\n"+
                    "\n1-Cliente" +
                    "\n2-Fornecerdor" +
                    "\n0-Voltar." +
                    "\n\nSelecione: "
            );
            select = sc.nextLine();
            switch (select){
                case "1" -> {
                    objectInitializer();
                    login.setChoose(true);
                    enderecoService.setUser(cliente);
                    enderecoDAO.setUser(cliente);
                    login.setCarrinhoDAO(carrinhoDAO);
                    login.setClienteDAO(clienteDAO);
                    login.getLogin(cliente);
                    verify = false;
                }

                case "2" -> {
                    objectInitializer();
                    login.setChoose(false);
                    enderecoService.setUser(fornecerdor);
                    enderecoDAO.setUser(fornecerdor);
                    login.setFornecerdorDAO(fornecerdorDAO);
                    login.getLogin(fornecerdor);
                    verify = false;
                }

                case "0" -> {
                    System.out.println("Voltando...");
                    verify = false;
                }


                default -> {
                    System.out.println("Invalido");
                    verify = true;
                }
            }

        }while (verify);
    }

    public static void modoDeReg(){
        String select;
        boolean verify;
        do {
            System.out.print("Como deseja registrar-se?\n\n1-Cliente\n2-Fornecerdor\n0-voltar\n\nSelecione:");
            select = sc.nextLine();
            switch (select){
                case "1"->{
                    login.setChoose(true);
                    objectInitializer();
                    enderecoService.setUser(cliente);
                    enderecoDAO.setUser(cliente);
                    login.setCarrinhoDAO(carrinhoDAO);
                    clienteService.register();
                    verify = false;
                }

                case "2" -> {
                    login.setChoose(false);
                    objectInitializer();
                    enderecoService.setUser(fornecerdor);
                    enderecoDAO.setUser(fornecerdor);
                    fornecerdorService.register();
                    verify = false;
                }

                case "0" -> {
                    System.out.println("Voltando...");
                    verify = false;
                }
                default -> {
                    System.out.println("Invalido.");
                    verify = true;
                }
            }

        }while (verify);


    }



    public static void configCliente(){
        String select;

        do {
            System.out.print("\n\nConfiguracao\n\n1-Meus dados\n2-Alterar endereco\n3-Alterar meus dados\n4-Deletar meus dados\n0-Voltar\n\nSelecione: ");
            select = sc.nextLine();
            switch (select){
                case "1" -> {
                    clienteService.showClienteinfos();
                    enderecoService.showEndereco();
                }

                case "2" -> enderecoService.alterData(endereco);

                case "3" -> clienteService.alterDataCliente((Cliente) cliente);

                case "4" -> clienteService.confirmar();

                case "0" -> System.out.println("Voltando...");

                default -> System.out.println("Invalido");
            }
        }while (!select.equals("0"));

    }

    public static void objectInitializer(){

        enderecoService.setCliente((Cliente) cliente);
        enderecoService.setEndereco(endereco);
        enderecoService.setEnderecoDAO(enderecoDAO);
        enderecoService.setClienteDAO(clienteDAO);
        enderecoService.setLogin(login);


        enderecoDAO.setFornecerdorDAO(fornecerdorDAO);
        enderecoDAO.setFornecerdor((Fornecerdor) fornecerdor);
        enderecoDAO.setClienteDAO(clienteDAO);
        enderecoDAO.setCliente((Cliente) cliente);
        enderecoDAO.setEndereco(endereco);
        enderecoDAO.setLogin(login);

        clienteDAO.setCliente((Cliente) cliente);
        clienteDAO.setEndereco(endereco);
        clienteDAO.setEnderecoService(enderecoService);
        clienteDAO.setCarrinhoDAO(carrinhoDAO);

        carrinhoDAO.setUser(cliente);
        carrinhoDAO.setCarrinho(carrinho);

        clienteService.setCliente((Cliente) cliente);
        clienteService.setClienteDAO(clienteDAO);
        clienteService.setEnderecoDAO(enderecoDAO);
        clienteService.setEndereco(endereco);


        fornecerdorService.setFornecerdorDAO(fornecerdorDAO);
        fornecerdorService.setFornecerdor((Fornecerdor) fornecerdor);
        fornecerdorService.setEnderecoService(enderecoService);

        fornecerdorDAO.setFornecerdorService(fornecerdorService);
        fornecerdorDAO.setFornecerdor((Fornecerdor) fornecerdor);

        produtoService.setItemCarrinhoService(itemCarrinhoService);
        produtoService.setItemCarrinhoDAO(itemCarrinhoDAO);
        produtoService.setCarrinho(carrinho);
        produtoCaller();
        carrinhoCaller();
        categoriaCaller();
    }

    public static void callerMain(){
        main(null);
    }

    public static void mainframeADMIN(){
        Scanner sc = new Scanner(System.in);
        String select;


        do {
            System.out.println("Seja bem vindo ADMINISTRADO.\n");
            System.out.print("Oque deseja fazer?" +
                    "\n\n1-Adicionar categoria" +
                    "\n2-Deletar usuario." +
                    "\n3-Deletar fornecerdor." +
                    "\n0-Sair.\n" +
                    "\nSelecione: "
            );

            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    categoriaCaller();
                    categoriaService.verifyCategoria();
                }

                case "2" -> {
                    System.out.println("por enquanto nada");
                }

                case "3" -> {
                    System.out.println("nada");
                }

                case "0" -> {
                    System.out.println("Voltando...");
                }

                default -> {
                    System.out.println("INVALIDO...");

                }
            }
        }while (!select.equals("0"));
    }
    public static void categoriaCaller(){
        categoriaService.setCategoria(categoria);
        categoriaService.setCategoriaDAO(categoriaDAO);

        itemCarrinhoService.setProdutoDAO(produtoDAO);

        categoriaDAO.setCategoria(categoria);
        categoriaDAO.setCategoriaService(categoriaService);
    }
    public static void produtoCaller(){
        produtoService.setProduto(produto);
        produtoService.setProdutoDAO(produtoDAO);
        produtoService.setCategoriaService(categoriaService);
        produtoService.setClienteDAO(clienteDAO);
        produtoService.setLogin(login);
        produtoService.setFornecerdorDAO(fornecerdorDAO);
        produtoService.setCarrinho(carrinho);
        produtoService.setItemCarrinhoService(itemCarrinhoService);
        produtoService.setItemCarrinhoDAO(itemCarrinhoDAO);

        produtoDAO.setLogin(login);
        produtoDAO.setProduto(produto);
        produtoDAO.setProdutoService(produtoService);
        produtoDAO.setCategoriaDAO(categoriaDAO);

        carrinhoDAO.setCarrinho(carrinho);
        carrinhoDAO.setUser(cliente);

        itemCarrinhoDAO.setItemCarrinho(itemCarrinho);
        itemCarrinhoDAO.setCarrinho(carrinho);
        itemCarrinhoDAO.setItemCarrinhoService(itemCarrinhoService);
        itemCarrinhoDAO.setProduto(produto);

        itemCarrinhoService.setItemCarrinho(itemCarrinho);
        itemCarrinhoService.setProduto(produto);
        itemCarrinhoService.setItemCarrinhoDAO(itemCarrinhoDAO);
        itemCarrinhoService.setPedido(pedido);
    }
    public static void carrinhoCaller(){

        itemCarrinhoService.setProduto(produto);
        itemCarrinhoService.setItemCarrinho(itemCarrinho);
        itemCarrinhoService.setItemCarrinhoDAO(itemCarrinhoDAO);

        itemCarrinhoDAO.setProduto(produto);
        itemCarrinhoDAO.setCarrinho(carrinho);
        itemCarrinhoDAO.setItemCarrinhoService(itemCarrinhoService);
        itemCarrinhoDAO.setItemCarrinho(itemCarrinho);
    }
    public static void pedidoCaller(){
        pedidoDAO.setPedido(pedido);
        pedidoDAO.setCarrinho(carrinho);
        pedidoDAO.setUser(cliente);

    }
}
