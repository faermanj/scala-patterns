package tailcall;

public class BigStack {
	static final Integer loop(Integer x)  {
	    System.out.println(x);
	    return loop(-1*x);
	  }

	public static void main(String[] args) {
		  loop(1);
	}
}
