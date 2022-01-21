/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static aunteticar.Auth.autenticar;
import static envioJson.Envio.enviarJSON;
import conexionDB.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Juan Camilo Mamian
 */
public class Principal {

    private static final String SELECT = "SELECT VCURL_TOKEN, VCURL_ENVIO, VCCREDEN_USR, VCCREDEN_PASS FROM M_PARAMETROS_ADM";

    public static void main(String[] args) {
        try {

            //Conexion para obtener las credenciales.
            ConnectionManager conn = new ConnectionManager();
            String urlSignIn = "";
            String urlAuth = "";
            String usr = "";
            String pass = "";

            //Lee el archivo .ini cadena de conexion base de datos
            if (conn.getParametrosConn()) {

                Connection conexion = conn.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(SELECT);
                ResultSet rs = stmt.executeQuery();

                //Traer los datos de la bd
                while (rs.next()) {
                    urlSignIn = rs.getString("VCURL_TOKEN");
                    urlAuth = rs.getString("VCURL_ENVIO");
                    usr = rs.getString("VCCREDEN_USR");
                    pass = rs.getString("VCCREDEN_PASS");
                }

                doPost(urlSignIn, urlAuth, usr, pass, conn);
            }

        } catch (IOException ex) {
            System.out.println("Se generó un error al hacer HTTP POST.");
            ex.printStackTrace(System.out);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void doPost(String signIn, String auth, String usr, String pass, ConnectionManager conn) throws IOException {
        //Enviar usr and pass, Configurar el header mas en el send JSON's.
        enviarJSON(autenticar(signIn, usr, pass), auth, conn); // method auntenticar retorna el token 
    }

}
