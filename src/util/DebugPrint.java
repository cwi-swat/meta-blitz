package util;

public class DebugPrint {

	public static <A> A debugPrint(String s, A a) {
		System.out.printf(s, a);
		return a;
	}

}
