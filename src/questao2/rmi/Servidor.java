package questao2.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor extends UnicastRemoteObject implements Chat {
    private ArrayList<String> mensagens = new ArrayList<String>();

    protected Servidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            String localizacao = "//localhost/Chat";
            Naming.rebind(localizacao,servidor);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarMensagem(String mensagem) throws RemoteException {
        mensagens.add(mensagem);
    }

    @Override
    public ArrayList<String> lerMensagens() throws RemoteException {
        return mensagens;
    }
}
