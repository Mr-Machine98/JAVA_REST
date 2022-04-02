/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultaDatos;

import conexionDB.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author Juan Camilo Mamian
 */
public class DaoConsultaJsonBd {

    private final ConnectionManager conn;
    private final String SELECT = "SELECT ID, DEFECHA, PERIODO, CONCEPTID, VCSERIE_ELEMENTID, NLEC_VALUE FROM ENVIO_MVM WHERE ESTADO_ENVIO = 0";
    private final String UPDATE = "UPDATE ENVIO_MVM SET ESTADO_ENVIO = 1 WHERE ID = ?";

    public DaoConsultaJsonBd(ConnectionManager conn) {
        this.conn = conn;
    }

    public String consultaJson() {

        ArrayList<String> arrJson = new ArrayList<>();

        try {

            Connection conexion = conn.getConnection();
            PreparedStatement stmt = conexion.prepareStatement(SELECT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                String date = String.valueOf(rs.getTimestamp("DEFECHA"));
                date = date.replace(" ", "T").trim().substring(0, 19);
                
                JSONObject obj = new JSONObject();
                obj.put("Date", date);
                obj.put("Period", rs.getInt("PERIODO"));
                obj.put("ConceptId", rs.getString("CONCEPTID"));
                obj.put("ElementId", rs.getString("VCSERIE_ELEMENTID"));
                obj.put("Value", rs.getFloat("NLEC_VALUE"));
                arrJson.add(obj.toString());
                
                actualizarJson(rs.getInt("ID")); // this method is slow, quit out  this method the bucle while
            }

            System.out.println(arrJson);
            System.out.println("Datos enviados: " + arrJson.size() + " Json's.");
            stmt.close();
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return (arrJson.isEmpty()) ? "" : arrJson.toString();
    }
    
    public void actualizarJson(int id){
        try {
            Connection conexion = conn.getConnection();
            PreparedStatement stmt = conexion.prepareStatement(UPDATE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            conexion.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        
    }

}
