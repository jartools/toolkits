package com.bowlong.third.groovy.examples.config;

import java.util.Date;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("rawtypes")
public class GroovyContext {
	public int id;
	public String name;
	public Date date;
	
	public Map map;
	
	public DruidDataSource ds = new DruidDataSource();
	
	public DataSourceConfig ds1 = new DataSourceConfig();
	
	public DataSourceConfig ds_design = new DataSourceConfig();
	public DataSourceConfig ds_log = new DataSourceConfig();
	
	@Override
	public String toString() {
		return "AppConfig [id=" + id + ", name=" + name + ", date=" + date +", ds1=" + ds1+", ds_design=" + ds_design+", ds_log=" + ds_log + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public DataSourceConfig getDs1() {
		return ds1;
	}

	public void setDs1(DataSourceConfig ds1) {
		this.ds1 = ds1;
	}

}
