
package us.kbase.bbtools;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: RQCFilterAppParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "read_library_ref",
    "output_workspace_name",
    "output_library_name"
})
public class RQCFilterAppParams {

    @JsonProperty("read_library_ref")
    private String readLibraryRef;
    @JsonProperty("output_workspace_name")
    private String outputWorkspaceName;
    @JsonProperty("output_library_name")
    private String outputLibraryName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("read_library_ref")
    public String getReadLibraryRef() {
        return readLibraryRef;
    }

    @JsonProperty("read_library_ref")
    public void setReadLibraryRef(String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
    }

    public RQCFilterAppParams withReadLibraryRef(String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
        return this;
    }

    @JsonProperty("output_workspace_name")
    public String getOutputWorkspaceName() {
        return outputWorkspaceName;
    }

    @JsonProperty("output_workspace_name")
    public void setOutputWorkspaceName(String outputWorkspaceName) {
        this.outputWorkspaceName = outputWorkspaceName;
    }

    public RQCFilterAppParams withOutputWorkspaceName(String outputWorkspaceName) {
        this.outputWorkspaceName = outputWorkspaceName;
        return this;
    }

    @JsonProperty("output_library_name")
    public String getOutputLibraryName() {
        return outputLibraryName;
    }

    @JsonProperty("output_library_name")
    public void setOutputLibraryName(String outputLibraryName) {
        this.outputLibraryName = outputLibraryName;
    }

    public RQCFilterAppParams withOutputLibraryName(String outputLibraryName) {
        this.outputLibraryName = outputLibraryName;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((((("RQCFilterAppParams"+" [readLibraryRef=")+ readLibraryRef)+", outputWorkspaceName=")+ outputWorkspaceName)+", outputLibraryName=")+ outputLibraryName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
