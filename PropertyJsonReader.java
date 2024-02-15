import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
public class PropertyJsonReader {
    public PropertyJsonReader() {
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("property.json")) {
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonfile.get("1");
            for (Object i: Land) {
                Game.squares.add(new Land(Integer.parseInt((String)((JSONObject) i).get("id")), (String)((JSONObject) i).get("name"), Integer.parseInt((String)((JSONObject) i).get("cost"))));
            }
            JSONArray RailRoad = (JSONArray) jsonfile.get("2");
            for (Object i: RailRoad) {
                Game.squares.add(new Railroad(Integer.parseInt((String)((JSONObject) i).get("id")), (String)((JSONObject) i).get("name"), Integer.parseInt((String)((JSONObject) i).get("cost"))));
            }
            JSONArray Company = (JSONArray) jsonfile.get("3");
            for (Object i: Company) {
                Game.squares.add(new Company(Integer.parseInt((String)((JSONObject) i).get("id")), (String)((JSONObject) i).get("name"), Integer.parseInt((String)((JSONObject) i).get("cost"))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Game.squares.add(new SpecialPlace(1,"is at Go"));
        Game.squares.add(new SpecialPlace(5,"paid Tax"));
        Game.squares.add(new SpecialPlace(11,"went to jail"));
        Game.squares.add(new SpecialPlace(21,"is in free parking"));
        Game.squares.add(new SpecialPlace(31,"went to jail"));
        Game.squares.add(new SpecialPlace(39,"paid Tax"));
        Collections.sort(Game.squares, Comparator.comparing(Square::getId));
    }
}