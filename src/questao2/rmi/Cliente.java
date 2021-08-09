package questao2.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try {
            Chat chat = null;
            ArrayList<String> mensagens;
            String localizacao = "//localhost/Chat";
            chat = (Chat) Naming.lookup(localizacao);
            Scanner s = new Scanner(System.in);
            System.out.print("Nome: ");
            String nome = s.next();

            while(true) {
                mensagens = chat.lerMensagens();
                Runtime.getRuntime().exec("clear");
                for (String mensagem: mensagens) {
                    System.out.println(mensagem);
                }
                System.out.print("Insira sua mensagem: ");
                chat.enviarMensagem(nome+": "+s.next());
            }

        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
