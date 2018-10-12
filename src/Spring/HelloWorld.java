package Spring;

/**
 * Created by liyangbd on 2018/9/19.
 */
public class HelloWorld {

	private String message;

	public void getMessage() {
		System.out.println("YOU MESSAGE IS :"+message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void init(){
		System.out.println("Bean init");
	}

	public void distory(){
		System.out.println("Bean distory");
	}
}
