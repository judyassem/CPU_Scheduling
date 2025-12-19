import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AG_TestCases {

    @JsonProperty("input")
    private AG_Input input;

    @JsonProperty("expectedOutput")
    private AG_ExpectedOutput expectedOutput;

    public AG_Input getInput() {
        return input;
    }

    public void setInput(AG_Input input) {
        this.input = input;
    }

    public AG_ExpectedOutput getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(AG_ExpectedOutput expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}