import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObserverAbstractForDirectory implements Observer {

	public ObserverAbstractForDirectory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		try {
			Method loadMedia = Controller.class.getMethod("loadMediatoLists");
			loadMedia.invoke(loadMedia, null);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setSubject(Subject sub) {
		// TODO Auto-generated method stub
		
	}

}
