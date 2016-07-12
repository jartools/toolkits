package com.bowlong.third.groovy.examples.config;

public class DataSourceConfig {
	public String name;
	public String driverClassName;
	public String url;
	public String username;
	public String password;
	public int maxActive;
	public int maxIdle;
	public int maxWait;
	public boolean removeAbandoned;
	public int removeAbandonedTimeout;
	

	@Override
	public String toString() {
		return "DataSourceConfig [name=" + name + ", driverClassName="
				+ driverClassName + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", maxActive=" + maxActive
				+ ", maxIdle=" + maxIdle + ", maxWait=" + maxWait
				+ ", removeAbandoned=" + removeAbandoned
				+ ", removeAbandonedTimeout=" + removeAbandonedTimeout + "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}
	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}
	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
}
