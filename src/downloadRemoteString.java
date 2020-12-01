import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.util.ArrayList;

import javax.crypto.SealedObject;

public interface downloadRemoteString extends Remote {
	public String downloadString() throws RemoteException;
	
	public byte[] downloadFile(String fileName) throws
	   RemoteException, IOException;
	
	public ArrayList<File> getAllAsList() throws RemoteException;
	
	public byte[] downloadAll() throws RemoteException, IOException;
	
	public byte[] downloadFiles() throws IOException, RemoteException;
	
	public int getPollingLimit() throws RemoteException;
	
	public byte[] downloadNextFile() throws IOException, RemoteException;
	
	public SealedObject downloadSecure() throws RemoteException;
	
	public DSAPublicKey getPublicKey() throws RemoteException;
	

	public DSAPrivateKey getKey() throws RemoteException;

		    
}
