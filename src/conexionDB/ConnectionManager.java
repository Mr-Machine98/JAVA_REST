package conexionDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnectionManager {
  //El String que contiene la ubicacion del driver para conexion con la bd.

  private String driver;
  private String url;
  private String login;
  private String password;
  private String Mensaje;
  private String user;
  private String nit;
  public String db;

  public ConnectionManager() throws Exception {
  }

  public ConnectionManager(String driver, String url, String login, String password) throws Exception {
    this.driver = driver;
    this.url = url;
    this.login = login;
    this.password = password;

    try {
      Class.forName(this.driver);
    } catch (ClassNotFoundException ex) {
      throw new Exception("La clase " + this.driver + " no pudo ser encontrada");
    }
  }

  public boolean getParametrosConn() throws Exception {
    boolean listo = false;
    try {
      File file = new File("./perseo.ini");
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String linea = null;

      if (br.ready()) {
        linea = br.readLine();
      }

      if (linea != null) {
        try {
          String[] desglose = linea.split(",");
          driver = desglose[0];
          if (driver.toUpperCase().contains("ORACLE")) {
            db = "ORACLE";
            url = desglose[1];
            login = desglose[2];
            password = desglose[3];
            nit = desglose[4];
          } else if (driver.toUpperCase().contains("SQLITE")) {
            db = "SQLITE";
            url = desglose[1];
          }
        } catch (Exception ex) {
          ex.printStackTrace(System.out);
          setMensaje("Los datos para la conexion no estan completos " + ex.getMessage());
        }
      }
      if (password == null) {
        password = "";
      }
      try {
        TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
        TimeZone.setDefault(timeZone);
      } catch (Exception e) {
      }

      Class.forName(this.driver);
      listo = true;

    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      setMensaje("Ocurrio un error al leer el archivo, verifique que posea el archivo simex.ini, y que este posea los datos correctos");
    }
    return listo;
  }

  /**
   * Regresa una conexion del tipo java.sql.Connection
   *
   * @return la conexion
   * @exception Exception en caso de que no se pueda establecer la conexion
   */
  public Connection getConnection() throws Exception {
    try {
      return DriverManager.getConnection(url, login, password);
    } catch (SQLException ex) {
      ex.printStackTrace(System.out);
      throw new Exception("No se pudo obtener la conexión a la Base de Datos con los parámetros descritos en el archivo de configuración.");
    }
  }

  public String getMensaje() {
    return Mensaje;
  }

  public void setMensaje(String Mensaje) {
    this.Mensaje = Mensaje;

  }

  public String getNit() {
    return nit;
  }

  public String getUser() {
    return user;
  }

  public String getDb() {
    return db;
  }

  public void setDb(String db) {
    this.db = db;
  }
}
