package com.bowlong.third.assist;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.bowlong.bio2.t3a.B2X;
import com.bowlong.io.ByteInStream;
import com.bowlong.util.NewList;
import com.bowlong.util.Ref;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class TestService {
	public static final class OutBoolVal extends B2X {
		public static final OutBoolVal _G = new OutBoolVal();

		public boolean val;

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeBool(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readBool(_in);
		}

		public void readObj(byte[] _in) throws Exception {
			Ref<Integer> rf = new Ref<>();
			this.val = readBool(_in, rf);
		}

		public static OutBoolVal readInstance(byte[] _in) throws Exception {
			OutBoolVal r2 = new OutBoolVal();
			r2.readObject(_in);
			return r2;
		}

		public static OutBoolVal readInstance(InputStream _in) throws Exception {
			OutBoolVal r2 = new OutBoolVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutBoolVal[val=" + val + "]";
		}

	}

	public static final class OutDoubleVal extends B2X {
		public static final OutDoubleVal _G = new OutDoubleVal();

		public double val;

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeDouble(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readDouble(_in);
		}

		public static OutDoubleVal readInstance(byte[] _in) throws Exception {
			return readInstance(new ByteInStream(_in));
		}

		public static OutDoubleVal readInstance(InputStream _in)
				throws Exception {
			OutDoubleVal r2 = new OutDoubleVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutDoubleVal[val=" + val + "]";
		}

	}

	public static final class OutIntVal extends B2X {
		public static final OutIntVal _G = new OutIntVal();

		public int val;

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeInt(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readInt(_in);
		}

		public static OutIntVal readInstance(byte[] _in) throws Exception {
			return readInstance(new ByteInStream(_in));
		}

		public static OutIntVal readInstance(InputStream _in) throws Exception {
			OutIntVal r2 = new OutIntVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutIntVal[val=" + val + "]";
		}

	}

	public static final class OutLongVal extends B2X {
		public static final OutLongVal _G = new OutLongVal();

		public long val;

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeLong(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readLong(_in);
		}

		public static OutLongVal readInstance(byte[] _in) throws Exception {
			return readInstance(new ByteInStream(_in));
		}

		public static OutLongVal readInstance(InputStream _in) throws Exception {
			OutLongVal r2 = new OutLongVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutLongVal[val=" + val + "]";
		}

	}

	public static final class OutStrListVal extends B2X {
		public static final OutStrListVal _G = new OutStrListVal();

		public List<String> val = new NewList();

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeList(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readList(_in, stringVal);
		}

		public static OutStrListVal readInstance(byte[] _in) throws Exception {
			return readInstance(new ByteInStream(_in));
		}

		public static OutStrListVal readInstance(InputStream _in)
				throws Exception {
			OutStrListVal r2 = new OutStrListVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutStrListVal[val=" + val + "]";
		}

	}

	public static final class OutStrVal extends B2X {
		public static final OutStrVal _G = new OutStrVal();

		public String val = "";

		@Override
		public void writeObject(OutputStream _out) throws Exception {
			writeString(_out, val);
		}

		@Override
		public void readObject(InputStream _in) throws Exception {
			this.val = readString(_in);
		}

		public static OutStrVal readInstance(byte[] _in) throws Exception {
			return readInstance(new ByteInStream(_in));
		}

		public static OutStrVal readInstance(InputStream _in) throws Exception {
			OutStrVal r2 = new OutStrVal();
			r2.readObject(_in);
			return r2;
		}

		public String toString() {
			return "OutStrVal[val=" + val + "]";
		}

	}

}
