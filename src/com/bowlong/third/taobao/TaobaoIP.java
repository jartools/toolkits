package com.bowlong.third.taobao;

import java.io.IOException;

import com.bowlong.lang.ByteEx;
import com.bowlong.net.http.HttpEx;
import com.bowlong.util.NewMap;
import com.bowlong.util.Ref;

public class TaobaoIP {

	static final String resetUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";

	// {"code":0,"data":{"country":"日本","country_id":"JP","area":"","area_id":"","region":"","region_id":"","city":"","city_id":"","county":"","county_id":"","isp":"","isp_id":"","ip":"133.88.44.178"}}

	
	static final NewMap<String, String> CACHE = new NewMap<>();

	public static String info(String ip) throws Exception {
		if (CACHE.containsKey(ip))
			return CACHE.get(ip);

		String url = String.format(resetUrl, ip);
		byte[] b = HttpEx.readUrl(url);
		if (b == null || b.length <= 0)
			return "{\"code\":-1}";

		String str = new String(b, "UTF-8");
		if (str == null || str.isEmpty() || !str.startsWith("{\"code\":0"))
			return "{\"code\":-1}";

		StringBuffer s1 = new StringBuffer(str);
		Ref<Integer> p1 = new Ref<>(0);
		Ref<Integer> p2 = new Ref<>(0);
		{ // country
			p1.val = p2.val = 0;
			String v = mid(s1, "country\":\"", "\"", p1, p2);
			s1 = s1.replace(p1.val, p2.val, str(v));
		}
		{ // area
			p1.val = p2.val = 0;
			String v = mid(s1, "area\":\"", "\"", p1, p2);
			s1 = s1.replace(p1.val, p2.val, str(v));
		}
		{ // region
			p1.val = p2.val = 0;
			String v = mid(s1, "region\":\"", "\"", p1, p2);
			s1 = s1.replace(p1.val, p2.val, str(v));
		}
		{ // city
			p1.val = p2.val = 0;
			String v = mid(s1, "city\":\"", "\"", p1, p2);
			s1 = s1.replace(p1.val, p2.val, str(v));
		}
		{ // isp
			p1.val = p2.val = 0;
			String v = mid(s1, "isp\":\"", "\"", p1, p2);
			s1 = s1.replace(p1.val, p2.val, str(v));
		}
		String r2 = s1.toString();
		CACHE.put(ip, r2);
		return r2;
	}

	private static final String mid(final StringBuffer s, final String begin,
			final String end, final Ref<Integer> p1, final Ref<Integer> p2) {
		p1.val = s.indexOf(begin);
		p1.val = p1.val < 0 ? 0 : p1.val + begin.length();
		p2.val = s.indexOf(end, p1.val);
		p2.val = p2.val < 0 ? s.length() : p2.val;
		return s.substring(p1.val, p2.val);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String ip = "133.88.44.178";
		System.out.println(info(ip));
		System.out.println(info(ip));
		System.out.println(info(ip));
	}

	private static final String str(String s) throws IOException {
		s = s == null ? "" : s;
		if (s == null || s.isEmpty())
			return s;
		if (!s.startsWith("\\u"))
			return s;
		s = s.replace("\\u", "");
		byte[] b = ByteEx.stringToByte(s);
		return new String(b, "Unicode");
	}

}
