/////////////////////////////////////////
// packages
import java.util.*;
import java.sql.*;
import groovy.sql.*;
import com.alibaba.druid.pool.*;

/////////////////////////////////////////
// datasource sea
def ds = app.ds;
ds.setDriverClassName("com.mysql.jdbc.Driver");
ds.setUrl("jdbc:mysql://192.168.2.241:3306/sea?autoReconnect=true&useUnicode=true&characterEncoding=utf-8");
ds.setUsername("root");
ds.setPassword("12345670");
ds.setMaxActive(30);
ds.setInitialSize(4);
ds.setMaxWait(6000);
ds.setRemoveAbandoned(true);
ds.setRemoveAbandonedTimeout(10);
// test connection
def sql = new Sql(ds);
sql.execute("SELECT 1;");
sql.close();
/////////////////////////////////////////

app.id = 100;
app.name = "hello";
app.date = new Date();

/////////////////////////////////////////
def ds1 = app.ds1;

ds1.name            = "coh";
ds1.driverClassName = "com.mysql.jdbc.Driver";
ds1.url             = "jdbc:mysql://127.0.0.1:3306/hoc?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
ds1.username        = "root";
ds1.password        = "";
ds1.maxActive       = 30;
ds1.maxIdle         = 5;
ds1.maxWait         = 6000;
ds1.removeAbandoned = true;
ds1.removeAbandonedTimeout = 10;

def ds_design = app.ds_design;
ds_design.name            = "coh";
ds_design.driverClassName = "com.mysql.jdbc.Driver";
ds_design.url             = "jdbc:mysql://127.0.0.1:3306/hoc_design?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
ds_design.username        = "root";
ds_design.password        = "";
ds_design.maxActive       = 30;
ds_design.maxIdle         = 5;
ds_design.maxWait         = 6000;
ds_design.removeAbandoned = true;
ds_design.removeAbandonedTimeout = 10;

def ds_log = app.ds_log;
ds_log.name            = "coh";
ds_log.driverClassName = "com.mysql.jdbc.Driver";
ds_log.url             = "jdbc:mysql://127.0.0.1:3306/hoc_log?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
ds_log.username        = "root";
ds_log.password        = "";
ds_log.maxActive       = 10;
ds_log.maxIdle         = 2;
ds_log.maxWait         = 6000;
ds_log.removeAbandoned = true;
ds_log.removeAbandonedTimeout = 10;

// can't set app.map != def map = app.map;

app.map = [url:'jdbc:hsqldb:mem:testDB', user:'sa', password:'', driver:'org.hsqldb.jdbc.JDBCDriver'];

/*
def db = [url:'jdbc:hsqldb:mem:testDB', user:'sa', password:'', driver:'org.hsqldb.jdbc.JDBCDriver']
def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
*/