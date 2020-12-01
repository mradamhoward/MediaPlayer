import java.io.File;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DownloaderString implements Callable{

	String downloadedString;
	String host = "rmi://192.168.1.5/";
	int port = 1099;

	@Override
	public String call() throws Exception {
		try
		{
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
					
			final Registry registry = LocateRegistry.getRegistry(host, port);
	      
			String name = "192.168.1.4:1099/FILES/";
			downloadRemoteString RemoteObject = (downloadRemoteString) Naming.lookup(name);

			downloadRemoteString serverObject1 = (downloadRemoteString)RemoteObject;
			
			downloadedString =  serverObject1.downloadString();
		}
		catch (Exception e) {
			System.out.println("Error in invoking object method " +
				e.toString() + e.getMessage());
				e.printStackTrace();
		}
		
		return downloadedString;
	}
	
}
