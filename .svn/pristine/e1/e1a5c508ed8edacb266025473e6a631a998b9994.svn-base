package aunteticar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;

/**
 *
 * @author Juan Camilo Mamian
 */
public class Auth {
    public static String autenticar(String signIn, String usr, String pass) throws MalformedURLException, IOException {
        //Creemos un objeto de URL con una cadena de URI de destino que acepta los datos JSON a través del método HTTP
        URL url = new URL(signIn);

        //Desde el objeto URL anterior , podemos invocar el método openConnection para obtener el objeto HttpURLConnection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        /*
        Establezca el encabezado de la solicitud "content-type" en "application / json" 
        para enviar el contenido de la solicitud en formato JSON.
        
        Establezca el  encabezado de la solicitud "Aceptar" en "application / json"
        para leer la respuesta en el formato deseado.
        
        Para enviar contenido de la petición, vamos a permitir la URLConnection
        del objeto doOutput propiedad a true 
        
         */
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        //Build JSON a enviar
        JSONObject obj = new JSONObject();
        obj.put("Username", usr);
        obj.put("Password", pass);
        String jsonInputString = obj.toString();

        //Escribir el JSON para enviarlo
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        /*
        Obtenga el flujo de entrada para leer el contenido de la respuesta.
        Recuerde utilizar try-with-resources para cerrar el flujo de respuesta
        automáticamente.Lea todo el contenido de la respuesta e imprima la cadena
        de respuesta final
         */
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //Devuelve el token
            return response.substring(1, response.toString().length() - 1);
        }

    }
}
