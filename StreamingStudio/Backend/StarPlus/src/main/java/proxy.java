import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/proxy/*")
public class proxy extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Construye la URL del servidor SOAP
        String soapUrl = "http://localhost:8084/star_plus/login";

        // Crea una conexión HTTP al servidor SOAP
        URL url = new URL(soapUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setDoOutput(true);

        // Envía los datos de la solicitud SOAP al servidor SOAP
        OutputStream out = connection.getOutputStream();
        out.write(request.getInputStream().readAllBytes());
        out.flush();
        out.close();

        // Obtiene la respuesta del servidor SOAP y la devuelve al cliente
        InputStream in = connection.getInputStream();
        byte[] responseBytes = in.readAllBytes();
        response.getOutputStream().write(responseBytes);
    }
}
