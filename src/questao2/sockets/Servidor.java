package questao2.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class Servidor extends Thread {
    public static String ip = "127.0.0.1";
    public static int porta = 4000;
    private static Vector clientes;
    private Socket conexao;
    private String nome;
    private static ArrayList<String> nomes = new ArrayList<>();
    public Servidor(Socket socket) {
        this.conexao = socket;
    }

    public boolean armazena(String nome){
        for (int i = 0; i < nomes.size(); i++){
            if(nomes.get(i).equals(nome))
                return true;
        }
        nomes.add(nome);
        return false;
    }

    public void remove(String nome) {
        for (int i = 0; i< nomes.size(); i++){
            if(nomes.get(i).equals(nome))
                nomes.remove(nome);
        }
    }

    public static void main(String args[]) {
        clientes = new Vector();

        ServerSocket server = null;
        try {
            server = new ServerSocket(porta);

            while (true) {
                Socket conexao = server.accept();
                Thread thread = new Servidor(conexao);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));

            PrintStream saida = new PrintStream(this.conexao.getOutputStream());

            this.nome = entrada.readLine();

            if (this.nome == null) {
                return;
            }

            clientes.add(saida);

            while(true) {
                String mensagem = entrada.readLine();
                if (mensagem != null) {
                    enviarMensagem(saida, mensagem);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(PrintStream saida, String mensagem) throws IOException {
        Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {

            PrintStream chat = (PrintStream) e.nextElement();

            if (chat != saida) {
                chat.println(this.nome + ": " + mensagem);
            }
        }
    }
}