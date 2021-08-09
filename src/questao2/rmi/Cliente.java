package questao2.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Cliente {

    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);

        String localizacao = "//localhost/Chat";
        Scanner s = new Scanner(System.in);
        System.out.print("Nome: ");
        String nome = s.next();
        System.out.print("Para enviar as mensagens, basta escrever e apertar enter: ");

        Thread leitura = new Thread() {
            public void run() {
                Chat chat = null;
                try {
                    chat = (Chat) Naming.lookup(localizacao);
                    ArrayList<String> mensagens = new ArrayList<>();
                    while(true) {
                        String ultimaMensagem = chat.lerUltimaMensagem();
                        if (ultimaMensagem != null) {
                            if(mensagens.isEmpty()) {
                                mensagens.add(chat.lerUltimaMensagem());
                                if (!nome.equals(mensagens.get(mensagens.size() - 1).split(":")[0])) {
                                    System.out.println(mensagens.get(mensagens.size() - 1));
                                }
                            } else if (!ultimaMensagem.equals(mensagens.get(mensagens.size() - 1))) {
                                mensagens.add(chat.lerUltimaMensagem());
                                if (!nome.equals(mensagens.get(mensagens.size() - 1).split(":")[0])) {
                                    System.out.println(mensagens.get(mensagens.size() - 1));
                                }
                            }
                        }
                    }
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread escrita = new Thread() {
            public void run() {
                Chat chat = null;
                try {
                    chat = (Chat) Naming.lookup(localizacao);
                    while(true) {
                        chat.enviarMensagem(nome+": "+s.next());
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        };

        leitura.start();
        escrita.start();

    }
}
