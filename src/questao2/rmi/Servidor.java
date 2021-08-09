package questao2.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements Chat {
    private String ultimaMensagem = null;


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
        ultimaMensagem = mensagem;
    }

    @Override
    public String lerUltimaMensagem() throws RemoteException {
        return ultimaMensagem;
    }
}
