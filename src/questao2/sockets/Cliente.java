package questao2.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente extends Thread {
    private Socket conexao;

    public Cliente(Socket socket) {
        this.conexao = socket;
    }

    public static void main(String args[])
    {
        try {
            Socket socket = new Socket(Servidor.ip, Servidor.porta);
            PrintStream saida = new PrintStream(socket.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Nome: ");
            String nome = teclado.readLine();
            saida.println(nome);

            Thread thread = new Cliente(socket);
            thread.start();

            System.out.println("Para enviar as mensagens, basta escrever e apertar enter: ");
            String mensagem;
            while (true) {
                mensagem = teclado.readLine();
                saida.println(mensagem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            String mensagem;
            while (true)
            {
                mensagem = entrada.readLine();
                if (mensagem != null) {
                    System.out.println(mensagem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}