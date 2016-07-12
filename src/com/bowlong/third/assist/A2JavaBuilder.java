package com.bowlong.third.assist;

import static com.bowlong.net.proto.gen.B2G.DATA;

import java.util.List;

@A2Class(namespace = "service", name = "TestService")
public class A2JavaBuilder {
	
	@A2Class(type = DATA, remark = "out boolVal")
	class OutBoolVal {
		public boolean val;
	}

	@A2Class(type = DATA, remark = "out intVal")
	class OutIntVal {
		public int val;
	}

	@A2Class(type = DATA, remark = "out longVal")
	class OutLongVal {
		public long val;
	}

	@A2Class(type = DATA, remark = "out stringVal")
	class OutStrVal {
		public String val;
	}

	@A2Class(type = DATA, remark = "out stringListVal")
	class OutStrListVal {
		public List<String> val;
	}

	@A2Class(type = DATA, remark = "out doubleVal")
	class OutDoubleVal {
		public double val;
	}

	public static void main(String[] args) throws Exception {
		Object obj = new A2JavaBuilder();
		A2Bio2GJava.b2g(obj);

	}

}
