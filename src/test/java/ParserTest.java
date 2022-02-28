import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.autoMate.Helpers.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ParserTest {

    Map<String, JsonObject> eventLog = new HashMap<>();


    @Test
    public void expectNullEventToPassWhenNoVariables() {
        String input = "Hi world";
        String result= Parser.format(null,input);

        assert result.equals(input);
    }

    @Test
    public void expectMissingVariableRemoval() {
        String input = "Hi {{remove.please}}";
        String expected = "Hi ";
        String result=Parser.format(null,input);

        assert result.equals(expected);
    }

    @Test
    public void expectLopsidedBracketUntouched() {
        String input = "Hi {{leave.alone}";
        String result=Parser.format(null,input);

        assert result.equals(input);
    }

    @Test
    public void expectValidParameterUsedPasses() throws IOException {
        String read = "{\"Cameron\":\"singer\",\"City\": \"Miami\"}";
        JsonObject aCameron = JsonParser.parseString(read).getAsJsonObject();
        eventLog.put("Alex",aCameron);

        String input = "Hi {{Alex.Cameron}}";
        String expected = "Hi singer";
        String result=Parser.format(eventLog,input);

        assert result.equals(expected);
    }

    @Test
    public void expectNestedValidParameterUsedPasses() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("src\\test\\java\\SampleJson.txt"));
        Gson gson=new Gson();
        JsonObject aCameron = gson.fromJson(reader, JsonObject.class).get("test_data").getAsJsonObject();

        eventLog.put("Alex",aCameron.get("Alex").getAsJsonObject());
        eventLog.put("Someone",aCameron.get("Someone").getAsJsonObject());

        String input = "Hi {{Alex.nested.annoying}}";
        String expected = "Hi data";
        String result=Parser.format(eventLog,input);

        assert result.equals(expected);
    }
}