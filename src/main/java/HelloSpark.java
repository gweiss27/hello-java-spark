import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import static spark.Spark.*;

/**
 * @author Gregory Weiss (gw186023)
 * Date: 3/23/18
 * @version $Id: $
 */
public class HelloSpark
{
    public static void main(String[] args)
    {
        get("/", (request, response) -> "Hello, World!");
    }

    private static String getTrackUrl(String query) {
        String url = "http://api.spotify.com/v1/search";
        HttpResponse<JsonNode> jsonResponse;

        try
        {
            jsonResponse = Unirest.get(url)
                    .header("accept", "applicationjson")
                    .queryString("q", query)
                    .queryString("type", "track")
                    .asJson();

            return jsonResponse
                    .getBody()
                    .getObject()
                    .getJSONObject("tracks")
                    .getJSONArray("items")
                    .getJSONObject(0)
                    .getString("preview_url");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
