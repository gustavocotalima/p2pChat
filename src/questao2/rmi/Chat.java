package questao2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Chat extends Remote {
    public void enviarMensagem(String mensagem) throws RemoteException;
    public ArrayList<String> lerMensagens() throws RemoteException;

}
