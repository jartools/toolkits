package com.bowlong.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.bowlong.third.xml.XMLEx;

public class Countrys {
	static final InputStream in = new Countrys().getClass()
			.getResourceAsStream("Countrys.xml");
	static final InputStreamReader reader = new InputStreamReader(in,
			Charset.forName("UTF-8"));

	private static Element root = null;

	private static Element getRoot() throws DocumentException {
		if (root == null)
			root = XMLEx.getRoot(reader);
		return root;
	}

	private static NewList<Country> _datas = new NewList<Country>();
	private static NewMap<String, Country> _countryName = new NewMap<>();
	private static NewMap<String, Country> _code = new NewMap<>();
	private static NewMap<String, Country> _countryNameCN = new NewMap<>();
	private static NewMap<Integer, Country> _numberCode = new NewMap<>();
	private static NewMap<String, Country> _dialingCode = new NewMap<>();

	@SuppressWarnings("rawtypes")
	public static NewList<Country> datas() throws Exception {
		if (_datas != null && !_datas.isEmpty()) {
			return _datas;
		}

		synchronized (_datas) {
			Element root = getRoot();
			List cs = root.elements("Country");
			for (Object object : cs) {
				Element e = (Element) object;
				Country c = ins.newCountry(e);
				_datas.add(c);
				_countryName.put(c.countryName, c);
				_code.put(c.code, c);
				_countryNameCN.put(c.countryNameCN, c);
				_numberCode.put(c.numberCode, c);
				_dialingCode.put(c.dialingCode, c);
			}
		}
		return _datas;
	}

	public static final NewList<Country> getAll() throws Exception {
		return datas();
	}

	public static final Country getByCountryName(final String key)
			throws Exception {
		datas();
		return _countryName.get(key.toUpperCase());
	}

	public static final Country getByCode(final String key) throws Exception {
		datas();
		return _code.get(key.toUpperCase());
	}

	public static final Country getByCountryNameCN(final String key)
			throws Exception {
		datas();
		return _countryNameCN.get(key);
	}

	public static final Country getByNumberCode(final int key) throws Exception {
		datas();
		return _numberCode.get(key);
	}

	public static final Country getByDialingCode(final String key)
			throws Exception {
		datas();
		return _dialingCode.get(key);
	}

	static Countrys ins = new Countrys();

	public final Country newCountry(Element e) {
		return new Country(e);
	}

	public class Country {
		public String countryName;
		public String code;
		public String countryNameCN;
		public int numberCode;
		public String aliasTW;
		public String aliasHK;
		public String dialingCode;

		@Override
		public String toString() {
			return "Country [countryName=" + countryName + ", code=" + code
					+ ", countryNameCN=" + countryNameCN + ", numberCode="
					+ numberCode + ", aliasTW=" + aliasTW + ", aliasHK="
					+ aliasHK + ", dialingCode=" + dialingCode + "]";
		}

		public Country(final Element e) {
			Element Country_name = e.element("Country_name");
			Element Code = e.element("Code");
			Element Country_name_CN = e.element("Country_name_CN");
			Element NumberCode = e.element("NumberCode");
			Element Alias_TW = e.element("Alias_TW");
			Element Alias_HK = e.element("Alias_HK");
			Element DialingCode = e.element("DialingCode");

			this.countryName = XMLEx.getText(Country_name);
			this.code = XMLEx.getText(Code);
			this.countryNameCN = XMLEx.getText(Country_name_CN);
			this.numberCode = XMLEx.getInt(NumberCode);
			this.aliasTW = XMLEx.getText(Alias_TW);
			this.aliasHK = XMLEx.getText(Alias_HK);
			this.dialingCode = XMLEx.getText(DialingCode);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(datas());
		System.out.println(getByCountryName("china"));
		System.out.println(getByCountryNameCN("ол╣Щ"));
		System.out.println(getByCode("CN"));
		System.out.println(getByNumberCode(156));
		System.out.println(getByDialingCode("86"));
	}

}
