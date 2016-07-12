package com.bowlong.third.assist;


public class Test {

	public static void main(String[] args) throws Exception {
		TestService.OutBoolVal bx = new TestService.OutBoolVal();
		bx.val = true;
		System.out.println(bx.toString());
		System.out.println(bx.toByteArray().length);

		TestService.OutIntVal b1 = new TestService.OutIntVal();
		b1.val = 51313;
		System.out.println(b1.toString());
		System.out.println(b1.toByteArray().length);

	}

}
