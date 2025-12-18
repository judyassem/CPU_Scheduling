import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {
    @JsonProperty("name")
    private String name;

    @JsonProperty("input")
    private Input input;

    @JsonProperty("expectedOutput")
    private ExpectedOutput expectedOutput;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public ExpectedOutput getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(ExpectedOutput expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}