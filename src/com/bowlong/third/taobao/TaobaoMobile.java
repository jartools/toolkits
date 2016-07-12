package com.bowlong.third.taobao;

import java.util.HashSet;
import java.util.Set;

import com.bowlong.io.FileEx;
import com.bowlong.lang.StrEx;
import com.bowlong.net.http.HttpEx;
import com.bowlong.util.NewMap;

public class TaobaoMobile {

	static final String _url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=%s";

	/*
	 * __GetZoneResult_ = { mts:'1592808', province:'四川', catName:'中国移动',
	 * telString:'15928080157' }
	 */

	static final NewMap<String, String> CACHE = new NewMap<>();

	public static final String mobileTelSegment(String phoneNum)
			throws Exception {
		if (phoneNum.startsWith("+"))
			phoneNum = StrEx.right(phoneNum, phoneNum.length() - 3);
		phoneNum = phoneNum.trim();

		String seg = StrEx.left(phoneNum, 7);
		if (CACHE.containsKey(seg))
			return CACHE.get(seg);

		String url = String.format(_url, phoneNum);
		byte[] b = HttpEx.readUrl(url);
		if (b == null || b.length <= 0)
			return "";

		String res = new String(b, "GBK");
		if (res == null || res.isEmpty())
			return "";

		// String mts = StrEx.mid(res, "mts:'", "',");
		// if (mts != null && !mts.isEmpty())
		CACHE.put(seg, res);

		return res;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<String> eqs = new HashSet<>();
		for (long i = 1300000; i < 1990000; i++) {
			String phoneNum = i + "0157";
			String seg = StrEx.left(phoneNum, 7);
			if (eqs.contains(seg))
				continue;

			String mts = mobileTelSegment(phoneNum);
			sb.append(seg).append("\r\n").append(mts);
			System.out.println(seg + "\r\n" + mts);
			eqs.add(seg);
		}
		FileEx.writeAll("mts.txt",false, sb.toString());
	}

}
