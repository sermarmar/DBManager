package com.cice.db;

import java.awt.peer.SystemTrayPeer;
import java.sql.*;

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

    public void ejecutarSelect(String sql){
        conectarBBDD();
        try{
            ResultSet res = statement.executeQuery(sql);
            while(res.next()){
                int numeroCol = res.getMetaData().getColumnCount();

                ResultSetMetaData resMetaData = res.getMetaData();
                String columnTest = resMetaData.getColumnLabel(0);
                System.out.println(columnTest);

                /*for(int i=0; i<numeroCol;i++){
                    String dato = res.getString(i);
                    System.out.println(dato);
                }*/

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        desconectarBBDD();
    }

}
