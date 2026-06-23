package application.services;

import application.dao.CategoriaDAO;
import application.entidades.Categoria;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoriaService {

    private Categoria categoria;
    private CategoriaDAO categoriaDAO;
    private ArrayList<String> list;

    public Categoria getCategoria() {
        return categoria;
    }

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void verifyCategoria(){

        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("*NOME:");
            getCategoria().setNome(sc.nextLine());
        }while (getCategoria().getNome().matches("") || getCategoria().getNome().length() > 50);

        do {
            System.out.print("DESCRICAO (Opcional): ");
            getCategoria().setDescricao(sc.nextLine());
            if (getCategoria().getDescricao().length() > 230){
            System.out.println("O tamanho da descricao tem que ser menor ou igual a 50");
        }
        }while (getCategoria().getDescricao().length() > 230);

        if (getCategoria().getDescricao().isEmpty()){
            getCategoria().setDescricao(null);
        }

        getCategoriaDAO().insert(getCategoria());
    }

    public String selectCatgegoria(){

        ArrayList<String> list = new ArrayList<>();
        setList(list);
        getCategoriaDAO().listCategoria(getList());

        Scanner sc = new Scanner(System.in);
        boolean verify = false;
        String check;
        int index;

        do {
            try{

                System.out.println("\nCategorias\n");

                for (int i = 0; i < getList().size(); i ++){

                    String trimer = getList().get(i);
                    int first = trimer.indexOf("-");
                    int last = trimer.lastIndexOf("-");
                    System.out.println( i+ "-" +trimer.substring(first + 1, last));
                }

                System.out.println("\nZ-Sair.");
                System.out.print(  "\nSelecione: ");
                check = sc.nextLine();

                index = Integer.parseInt(check);

                String trimer = list.get(index);
                int first = trimer.indexOf("-");
                getCategoria().setIdCategoria(trimer.substring(0,first));
                System.out.println(getList().get(index));

            }catch (Exception e){
                System.out.println("Porfavor digite um numero.");
                System.out.println(e);
                verify = true;
            }

        }while (verify);

        return getCategoria().getIdCategoria();
    }

    public void listCategoria(){
        ArrayList<String> list = new ArrayList<>();
        setList(list);
        getCategoriaDAO().listCategoria(getList());

        Scanner sc = new Scanner(System.in);
        boolean verify = false;
        String check;
        int index;

        do {
            try{

                System.out.println("\nCategorias\n");

                for (int i = 0; i < getList().size(); i ++){

                    String trimer = getList().get(i);
                    int first = trimer.indexOf("-");
                    int last = trimer.lastIndexOf("-");
                    System.out.println( i+ "-" +trimer.substring(first + 1, last));
                }

                System.out.println("\nZ-Sair.");
                System.out.print(  "\nSelecione: ");
                check = sc.nextLine();

                if (check.equalsIgnoreCase("z")){
                    System.out.println("voltando...");
                    verify = false;
                }else {
                    index = Integer.parseInt(check);

                    if (index >= list.size() || index < 0){
                        System.out.println("IVALIDO.");
                        verify = true;
                    }else {


                        String trimer = list.get(index);
                        int first = trimer.indexOf("-");
                        getCategoria().setIdCategoria(trimer.substring(0, first));


                        System.out.println(getList().get(index));
                        verify = false;
                    }
                }



            }catch (Exception e){
                System.out.println("Porfavor digite um numero.");
                System.out.println(e);
                verify = true;
            }

        }while (verify);
    }
}
