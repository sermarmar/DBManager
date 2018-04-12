package com.cice.db;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.awt.peer.SystemTrayPeer;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Manager {

    private final String DRIVER;
    private final String HOST;
    private final String PUERTO;
    private final String USER;
    private final String PASS;
    private final String DATABASE;
    private Connection conn;
    private Statement statement;

    public Manager(){
        this.DRIVER = "com.mysql.jdbc.DRIVER";
        this.HOST = "localhost";
        this.PUERTO = "8889";
        this.USER = "root";
        this.PASS = "root";
        this.DATABASE = "prueba";
    }

    public Manager(String DRIVER, String HOST, String PUERTO, String USER, String PASS, String DATABASE) {
        this.DRIVER = DRIVER;
        this.HOST = HOST;
        this.PUERTO = PUERTO;
        this.USER = USER;
        this.PASS = PASS;
        this.DATABASE = DATABASE;
    }

    private String generarULR(){
        return "jdbc:mysql://"+HOST+":"+PUERTO+"/"+DATABASE;
    }

    private boolean conectarBBDD(){
        boolean esConectado = false;
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(generarULR(), USER, PASS);
            if(conn != null) {
                esConectado = true;
            }
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return esConectado;

    }

    private boolean desconectarBBDD(){
        boolean esDesconectado = false;

        try{
            conn.close();
            esDesconectado = true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return esDesconectado;
    }

    public CachedRowSet ejecutarSelect(String sql) throws SQLException {
        CachedRowSet cache = null;
        ResultSet res = null;
        conectarBBDD();
        try{
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            cache = RowSetProvider.newFactory().createCachedRowSet();
            cache.populate(res);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            res.close();
            statement.close();
        }

        desconectarBBDD();
    }

}
