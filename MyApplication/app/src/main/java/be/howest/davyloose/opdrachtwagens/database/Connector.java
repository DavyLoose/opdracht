package be.howest.davyloose.opdrachtwagens.database;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by davy on 6/07/2017.
 */

public class Connector  {

    private static String serverUrl = "http://androidcarspage.dx.am/handle-requests.php?action=";



    public String getconnection(String urlString, java.util.Map<?,?> parameters){
        if (!parameters.isEmpty()){
            for (Map.Entry<?, ?> entry : parameters.entrySet())
            {
                urlString = urlString + "&"+(entry.getKey() + "=" + entry.getValue());
            }
        }
        URL url = null;
        try {
             url = new URL(serverUrl+urlString);
        } catch (MalformedURLException e) {
        System.out.println("The URL is not valid.");
        System.out.println(e.getMessage());
        }
        try {
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
           return(readStream(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}
