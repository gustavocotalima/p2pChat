package questao2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    public void enviarMensagem(String mensagem) throws RemoteException;
    public String lerUltimaMensagem() throws RemoteException;

}
