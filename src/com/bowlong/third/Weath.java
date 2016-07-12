package com.bowlong.third;

import java.io.File;
import java.io.InputStream;

import com.bowlong.bio2.B2Helper;
import com.bowlong.io.FileEx;
import com.bowlong.lang.StrEx;
import com.bowlong.net.http.HttpEx;
import com.bowlong.third.taobao.TaobaoIP;
import com.bowlong.util.NewMap;

public class Weath {
	static final InputStream in = new Weath().getClass().getResourceAsStream(
			"weath.citys.bio2");

	// static final InputStream in = new Weath().getClass().getResourceAsStream(
	// "Weath.txt");
	// static final InputStreamReader reader = new InputStreamReader(in,
	// Charset.forName("UTF-8"));

	static final String skUrl = "http://www.weather.com.cn/data/sk/%s.html";
	// {"weatherinfo":{"city":"成都","cityid":"101270101","temp":"23","WD":"北风","WS":"3级","SD":"91%","WSE":"3","time":"15:00","isRadar":"1","Radar":"JC_RADAR_AZ9280_JB"}}
	static final String cityInfo = "http://www.weather.com.cn/data/cityinfo/%s.html";
	// {"weatherinfo":{"city":"成都","cityid":"101270101","temp1":"28℃","temp2":"24℃","weather":"阵雨转中雨","img1":"d3.gif","img2":"n8.gif","ptime":"11:00"}}
	static final String weathData = "http://m.weather.com.cn/data/%s.html";
	// {"weatherinfo":{"city":"成都","city_en":"chengdu","date_y":"2013年7月4日","date":"","week":"星期四","fchh":"11","cityid":"101270101","temp1":"28℃~24℃","temp2":"32℃~23℃","temp3":"31℃~21℃","temp4":"32℃~24℃","temp5":"29℃~24℃","temp6":"27℃~24℃","tempF1":"82.4?~75.2?","tempF2":"89.6?~73.4?","tempF3":"87.8?~69.8?","tempF4":"89.6?~75.2?","tempF5":"84.2?~75.2?","tempF6":"80.6?~75.2?","weather1":"阵雨转中雨","weather2":"多云","weather3":"多云","weather4":"多云转阴","weather5":"阵雨转中雨","weather6":"中雨","img1":"3","img2":"8","img3":"1","img4":"99","img5":"1","img6":"99","img7":"1","img8":"2","img9":"3","img10":"8","img11":"8","img12":"99","img_single":"3","img_title1":"阵雨","img_title2":"中雨","img_title3":"多云","img_title4":"多云","img_title5":"多云","img_title6":"多云","img_title7":"多云","img_title8":"阴","img_title9":"阵雨","img_title10":"中雨","img_title11":"中雨","img_title12":"中雨","img_title_single":"阵雨","wind1":"北风3-4级","wind2":"南风小于3级","wind3":"南风小于3级","wind4":"南风小于3级","wind5":"南风小于3级","wind6":"南风小于3级","fx1":"北风","fx2":"北风","fl1":"3-4级","fl2":"小于3级","fl3":"小于3级","fl4":"小于3级","fl5":"小于3级","fl6":"小于3级","index":"热","index_d":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","index48":"炎热","index48_d":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","index_uv":"弱","index48_uv":"中等","index_xc":"不宜","index_tr":"适宜","index_co":"较舒适","st1":"25","st2":"20","st3":"32","st4":"23","st5":"31","st6":"21","index_cl":"较不宜","index_ls":"不太适宜","index_ag":"极不易发"}}

	static NewMap<String, String> __citys = new NewMap<>();

	static final NewMap<String, CacheValue> CACHE = new NewMap<>();

	public static class CacheValue {
		public String key;
		public String val;
		public long tm; // 过期时间一个小时

		public CacheValue(String code, String val) {
			this.key = code;
			this.val = val;
			this.tm = System.currentTimeMillis();
		}

		public boolean isTimeout() {
			long now = System.currentTimeMillis();
			return now > (tm + 60 * 60 * 1000);
		}
	}

	@SuppressWarnings("unchecked")
	static final NewMap<String, String> _cityInit() throws Exception {
		if (__citys != null && !__citys.isEmpty())
			return __citys;
		__citys = B2Helper.toMap(in);
		return __citys;
	}

	// static final NewMap<String, String> _cityInit2() throws IOException {
	// if (__citys != null && !__citys.isEmpty())
	// return __citys;
	// synchronized (__citys) {
	// BufferedReader br = new BufferedReader(reader);
	// String s1;
	// String s2;
	// String s3;
	// String li;
	// for (int i = 0; i < 2000; i++) {
	// s1 = br.readLine();
	// s2 = br.readLine();
	// s3 = br.readLine();
	// li = br.readLine();
	// if (s1 == null || s2 == null || s3 == null || li == null)
	// break;
	// String ss2[] = s2.split(",");
	// String ss3[] = s3.split(",");
	// if (ss2.length != ss3.length)
	// break;
	// for (int j = 0; j < ss2.length; j++) {
	// String _name = ss2[j];
	// String _code = ss3[j];
	// __citys.put(_name, _code);
	// }
	// }
	// }
	// return __citys;
	// }

	static final String _code(String name) throws Exception {
		NewMap<String, String> cs = _cityInit();
		byte[] buf = B2Helper.toBytes(cs);
		FileEx.write(new File("Weath.citys.bio2"), buf);
		String r2 = cs.get(name);
		return r2;
	}

	public static String sk(String name) throws Exception {
		String code = _code(name);
		if (code == null || code.isEmpty())
			return "";
		String url = String.format(skUrl, code);
		CacheValue cval = CACHE.get(url);
		if (cval != null && !cval.isTimeout())
			return cval.val;

		byte[] bs = HttpEx.readUrl(url);
		String val = new String(bs, "UTF-8");

		cval = new CacheValue(url, val);
		CACHE.put(url, cval);

		return val;
	}

	public static String cityInfo(String name) throws Exception {
		String code = _code(name);
		if (code == null || code.isEmpty())
			return "";
		String url = String.format(cityInfo, code);
		CacheValue cval = CACHE.get(url);
		if (cval != null && !cval.isTimeout())
			return cval.val;
		byte[] bs = HttpEx.readUrl(url);
		String val = new String(bs, "UTF-8");

		cval = new CacheValue(url, val);
		CACHE.put(url, cval);

		return val;
	}

	public static String weathData(String name) throws Exception {
		String code = _code(name);
		if (code == null || code.isEmpty())
			return "";
		String url = String.format(weathData, code);
		CacheValue cval = CACHE.get(url);
		if (cval != null && !cval.isTimeout())
			return cval.val;

		byte[] bs = HttpEx.readUrl(url);
		String val = new String(bs, "UTF-8");

		cval = new CacheValue(url, val);
		CACHE.put(url, cval);

		return val;
	}

	public static String skByIp(String ip) throws Exception {
		String sIP = TaobaoIP.info(ip);
		String cityName = StrEx.sub(sIP, "city\":\"", "\",");
		if (cityName.endsWith("市")) {
			cityName = StrEx.left(cityName, cityName.length() - 1);
		}
		return sk(cityName);
	}

	public static String cityInfoByIp(String ip) throws Exception {
		String sIP = TaobaoIP.info(ip);
		String cityName = StrEx.sub(sIP, "city\":\"", "\",");
		if (cityName.endsWith("市")) {
			cityName = StrEx.left(cityName, cityName.length() - 1);
		}
		return cityInfo(cityName);
	}

	public static String weathDataByIp(String ip) throws Exception {
		String str = TaobaoIP.info(ip);
		String cityName = StrEx.sub(str, "city\":\"", "\",");
		if (cityName.endsWith("市")) {
			cityName = StrEx.left(cityName, cityName.length() - 1);
		}
		return weathData(cityName);
	}

	public static void main(String[] args) throws Exception {
		String ct = "成都";
		System.out.println(sk(ct));
		System.out.println(cityInfo(ct));
		System.out.println(weathData(ct));

		String ip = "218.88.44.174";
		System.out.println(skByIp(ip));
		System.out.println(cityInfoByIp(ip));
		System.out.println(weathDataByIp(ip));

		// byte[] b = HttpEx.readUrl("http://61.4.185.48:81/g/");
		// System.out.println(new String(b, "UTF-8"));
	}
}
