package com.bowlong.third.poi.excel.hss;

import java.util.Hashtable;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public class HSheet {
	final HWorkbook wb;
	final HSSFSheet sheet;
	final String sheetName;
	int sheetIndex;
	int maxCol = -1;
	int maxRow = -1;
	final Map<String, Object> cache = new Hashtable<String, Object>();

	public HSheet(final HWorkbook wb, final HSSFSheet sheet) {
		this.wb = wb;
		this.sheet = sheet;
		this.sheetName = sheet.getSheetName();

		int p = 0;
		for (HSSFSheet sh : wb.sheets) {
			if (sh.getSheetName().equals(sheetName)) {
				sheetIndex = p;
				break;
			}
			p++;
		}

		for (int row = 0; row < HSS.LINE_DATA_MAX; row++) {
			String str = getString(row, 1);
			if (str == null || str.isEmpty())
				break;
			this.maxRow = row;
		}

		for (int col = 0; col < HSS.COLUMN_MAX; col++) {
			String str = getString(1, col);
			if (str == null || str.isEmpty())
				break;
			this.maxCol = col;
		}

	}

	public final HWorkbook wb() {
		return this.wb;
	}

	public final HSSFSheet sheet() {
		return this.sheet;
	}

	public final int sheetIndex() {
		return this.sheetIndex;
	}

	public final String sheetName() {
		return this.sheetName;
	}

	public final boolean getBool(int row, int col) {
		if (!wb.iScached) 
			return HSS.getBool(this.sheet, row, col);
		
		String key = key("bool", row, col);
		Object val = (Object) cache.get(key);
		if (val == null || !(val instanceof Boolean)) {
			val = HSS.getBool(this.sheet, row, col);
			cache.put(key, val);
		}
		return (Boolean) val;
	}

	public final int getInt(int row, int col) {
		if (!wb.iScached) 
			return HSS.getInt(this.sheet, row, col);

		String key = key("int", row, col);
		Object val = (Object) cache.get(key);
		if (val == null || !(val instanceof Integer)) {
			val = HSS.getInt(this.sheet, row, col);
			cache.put(key, val);
		}
		return (Integer) val;
	}

	public final long getLong(int row, int col) {
		if (!wb.iScached) 
			return HSS.getLong(this.sheet, row, col);

		String key = key("long", row, col);
		Object val = (Object) cache.get(key);
		if (val == null || !(val instanceof Long)) {
			val = HSS.getLong(this.sheet, row, col);
			cache.put(key, val);
		}
		return (Long) val;
	}

	public final double getDouble(int row, int col) {
		if (!wb.iScached) 
			return HSS.getDouble(this.sheet, row, col);

		String key = key("double", row, col);
		Object val = (Object) cache.get(key);
		if (val == null || !(val instanceof Double)) {
			val = HSS.getDouble(this.sheet, row, col);
			cache.put(key, val);
		}
		return (Double) val;
	}

	public final String getString(int row, int col) {
		if (!wb.iScached) 
			return HSS.getString(this.sheet, row, col);

		String key = key("string", row, col);
		Object val = (Object) cache.get(key);
		if (val == null || !(val instanceof String)) {
			val = HSS.getString(this.sheet, row, col);
			cache.put(key, val);
		}
		return (String) val;
	}

	public final Object getJSON(int row, int col) {
		if (!wb.iScached) 
			return HSS.getJSON(this.sheet, row, col);

		String key = key("json", row, col);
		Object val = (Object) cache.get(key);
		if (val == null) {
			val = HSS.getJSON(this.sheet, row, col);
			cache.put(key, val);
		}
		return val;
	}

	public final String getCommnet(int row, int col) {
		return HSS.getComment(this.sheet, row, col);
	}

	public final int maxCol() {
		return maxCol;
	}

	public final int maxRow() {
		return maxRow;
	}

	private String key(String type, int row, int col) {
		return "type-" + row + "," + col;
	}

}
