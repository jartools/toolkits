package com.bowlong.third.netty3.httphand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.codec.http.multipart.Attribute;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData;

import com.alibaba.fastjson.JSON;
import com.bowlong.net.http.HttpBaseEx;
import com.bowlong.util.ListEx;
import com.bowlong.util.MapEx;

@SuppressWarnings("rawtypes")
public class N3HttpResp implements Serializable {

	private static final long serialVersionUID = 1L;

	static Log log = LogFactory.getLog(N3HttpResp.class);

	// 此处的res 是 response 的简称.
	// 此处的req 是 request 的简称.
	// ByPost 表示post 请求传参
	// ByGet 表示 get 请求传参
	public static byte[] getBytesByBuffer(HttpRequest request) {
		try {
			ChannelBuffer buffer = request.getContent();
			return buffer.array();
		} catch (Exception e) {
			log.error(e);
		}
		return new byte[0];
	}

	public static String getStrDataByBuffer(HttpRequest request,
			String strCharset) {
		byte[] bytes = getBytesByBuffer(request);
		if (bytes == null || bytes.length <= 0)
			return "";
		try {
			if (strCharset == null || "".equals(strCharset.trim()))
				strCharset = "UTF-8";
			return new String(bytes, strCharset);
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	// =============== get 请求传参

	public static String getParamsVal(String query, String key) {
		Map<String, String> mapPars = HttpBaseEx.buildMapByQuery(query);
		return MapEx.getString(mapPars, key);
	}

	// 根据uri取得get传递上来的所有参数集合的map对象
	static public Map<String, List<String>> getMapKVesByGet(String strUri) {
		Map<String, List<String>> v = null;
		if (strUri != null && !"".equals(strUri.trim())) {
			QueryStringDecoder qdec = new QueryStringDecoder(strUri);
			v = qdec.getParameters();
		}
		return v;
	}

	static public Map<String, String> getMapByGet(String strUri) {
		Map<String, String> result = null;
		Map<String, List<String>> v = getMapKVesByGet(strUri);
		if (v != null && !v.isEmpty()) {
			result = new ConcurrentHashMap<String, String>();
			StringBuffer buff = new StringBuffer();
			for (Entry<String, List<String>> item : v.entrySet()) {

				buff.setLength(0);

				List<String> tmp = item.getValue();
				if (tmp == null || tmp.isEmpty())
					continue;

				int lens = tmp.size();
				for (int i = 0; i < lens; i++) {
					buff.append(tmp.get(i));
					if (i < lens - 1) {
						buff.append(",");
					}
				}

				result.put(item.getKey(), buff.toString());
			}
		}
		return result;
	}

	// 根据parmetes 参数取得对应的value，value默认是key对应的所有值中的第一个值.
	public static Map<String, String> getMapByGetKeys(String strUri,
			String... keyes) {
		Map<String, String> v = new HashMap<String, String>();
		if (ListEx.isEmpty(keyes))
			return v;

		Map<String, String> map = getMapByGet(strUri);
		if (map != null && !map.isEmpty()) {
			for (String key : keyes) {
				boolean isHasKey = map.containsKey(key);
				if (!isHasKey) {
					v.put(key, "");
					continue;
				}
				String val = map.get(key);
				v.put(key, val);
			}
		}
		return v;
	}

	// ==================== post 请求传参
	private static HttpPostRequestDecoder getDecoderByPost(HttpRequest request)
			throws Exception {
		HttpDataFactory factory = new DefaultHttpDataFactory(false);
		HttpPostRequestDecoder postDecoder = new HttpPostRequestDecoder(
				factory, request);
		return postDecoder;
	}

	private static Attribute getAttributeByPost(HttpRequest request,
			String strAttr) throws Exception {
		HttpPostRequestDecoder postDecoder = getDecoderByPost(request);
		Attribute r = getAttributeByPost(postDecoder, strAttr);
		return r;
	}

	private static Attribute getAttributeByPost(
			HttpPostRequestDecoder postDecoder, String strAttr)
			throws Exception {
		InterfaceHttpData data = postDecoder.getBodyHttpData(strAttr);
		Attribute r = (Attribute) data;
		return r;
	}

	private static String getStrValByPostKey(
			HttpPostRequestDecoder postDecoder, String key) throws Exception {
		String r = "";
		Attribute attr = getAttributeByPost(postDecoder, key);
		if (attr != null) {
			r = attr.getValue();
		}
		return r;
	}

	public static String getStrValByPostKey(HttpRequest request, String key)
			throws Exception {
		String r = "";
		Attribute attr = getAttributeByPost(request, key);
		if (attr != null) {
			r = attr.getValue();
		}
		return r;
	}

	private static Map<String, String> getMapByPostBuffer(HttpRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String str = getStrDataByBuffer(request, "");
		Map mapData = JSON.parseObject(str, Map.class);
		if (mapData == null || mapData.isEmpty())
			return map;
		for (Object item : mapData.keySet()) {
			String key = item.toString();
			String val = "";
			Object o = MapEx.get(mapData, key);
			if (o instanceof Map) {
				val = JSON.toJSONString(o);
			} else if (o instanceof List) {
				val = JSON.toJSONString(o);
			} else {
				val = o.toString();
			}
			map.put(key, val);
		}
		return map;
	}

	private static Map<String, String> getMapKVByPostBuffer(
			HttpRequest request, String... keys) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null || keys.length <= 0)
			return map;
		Map<String, String> dataMap = getMapByPostBuffer(request);
		if (dataMap == null || dataMap.isEmpty())
			return map;
		for (String key : keys) {
			String val = MapEx.getString(dataMap, key);
			map.put(key, val);
		}
		return map;
	}

	public static Map<String, String> getMapKVByPost(HttpRequest request,
			String... keys) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null || keys.length <= 0)
			return map;

		HttpPostRequestDecoder postDecoder = null;
		try {
			postDecoder = getDecoderByPost(request);
			for (String key : keys) {
				String val = getStrValByPostKey(postDecoder, key);
				map.put(key, val);
			}
		} catch (Exception e) {
			log.error(e);
			map.clear();
		}

		if (postDecoder == null || map.isEmpty()) {
			map = getMapKVByPostBuffer(request, keys);
		}
		return map;
	}

	public static Map<String, String> getMapKVByPostBody(HttpRequest request,
			String... keys) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null || keys.length <= 0)
			return map;

		Map<String, String> bodyMap = getMapQueryByPostBody(request);
		for (String key : keys) {
			String val = MapEx.getString(bodyMap, key);
			map.put(key, val);
		}
		return map;
	}

	public static Map<String, String> getMapQueryByPostBody(HttpRequest request) {
		HttpPostRequestDecoder postDecoder = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			postDecoder = getDecoderByPost(request);
			List<InterfaceHttpData> datas = postDecoder.getBodyHttpDatas();
			for (InterfaceHttpData data : datas) {
				Attribute attr = (Attribute) data;
				String key = attr.getName();
				String val = attr.getValue();
				map.put(key, val);
			}
		} catch (Exception e) {
			log.error(e);
		}

		if (map.isEmpty()) {
			map = getMapByPostBuffer(request);
		}

		return map;
	}

	public static String getQueryByRequest(HttpRequest request) {
		try {
			Map<String, String> dataMap = getMapQueryByPostBody(request);
			return HttpBaseEx.buildQuery(dataMap, "");
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}
}
