import com.google.gson.JsonObject;
import com.autoMate.Actions.HttpRequestAction;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

class HttpRequestActionTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void fetchExpectSuccess() throws Exception {
        HttpRequestAction httpRequestAction = new HttpRequestAction("test");
        JsonObject response = httpRequestAction.fetch("http://worldtimeapi.org/api/ip");

        assert response.get("abbreviation").getAsString().equals("GMT");
    }

    @Test
    public void fetchExpectFailure() {
        HttpRequestAction httpRequestAction = new HttpRequestAction("test");

        try {
            httpRequestAction.fetch("http://worldtimeapi.org/bad/address");
            fail("Exected bad format to fail");
        } catch (Exception e) {
            //fail expected
        }
    }

}