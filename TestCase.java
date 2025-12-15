import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {
    private String name;
    private Input input;
    private ExpectedOutput expectedOutput;

    // getters and setters
    public Input getInput(){
        return input;
    }

    public String getName() {
        return name;
    }
    public ExpectedOutput getExpectedOutput() {
        return expectedOutput;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInput(Input input) {
        this.input = input;
    }
    public void setExpectedOutput(ExpectedOutput expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

}
