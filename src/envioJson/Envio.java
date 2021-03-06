package envioJson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import conexionDB.ConnectionManager;
import consultaDatos.DaoConsultaJsonBd;

/**
 *
 * @author Juan Camilo
 */
public class Envio {

    public static int enviarJSON(String token, String auth, ConnectionManager conn) throws MalformedURLException, IOException {

        String data;

        URL url = new URL(auth);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setRequestProperty("Content-Type", "application/json");

        try {

            //Construir el arreglo para enviar al SCO por http
            //Se retorna un string con los datos
            DaoConsultaJsonBd strJson = new DaoConsultaJsonBd(conn);
            data = strJson.consultaJson();

            if (data.equals("")) {
                System.out.println("No hay datos para enviar.");
            } else {
                byte[] out = data.getBytes(StandardCharsets.UTF_8);
                OutputStream stream = http.getOutputStream();
                stream.write(out);
                System.out.println("Request -> " + http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            }

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return http.getResponseCode();
    }
}
