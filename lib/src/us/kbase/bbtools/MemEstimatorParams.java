
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
 * <p>Original spec-file type: MemEstimatorParams</p>
 * <pre>
 * reads_file - path to a reads file. If this is here alone, expect it to
 *              be interleaved.
 * reads_file2 - path to the pair of the first file.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "reads_file",
    "reads_file2"
})
public class MemEstimatorParams {

    @JsonProperty("reads_file")
    private String readsFile;
    @JsonProperty("reads_file2")
    private String readsFile2;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("reads_file")
    public String getReadsFile() {
        return readsFile;
    }

    @JsonProperty("reads_file")
    public void setReadsFile(String readsFile) {
        this.readsFile = readsFile;
    }

    public MemEstimatorParams withReadsFile(String readsFile) {
        this.readsFile = readsFile;
        return this;
    }

    @JsonProperty("reads_file2")
    public String getReadsFile2() {
        return readsFile2;
    }

    @JsonProperty("reads_file2")
    public void setReadsFile2(String readsFile2) {
        this.readsFile2 = readsFile2;
    }

    public MemEstimatorParams withReadsFile2(String readsFile2) {
        this.readsFile2 = readsFile2;
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
        return ((((((("MemEstimatorParams"+" [readsFile=")+ readsFile)+", readsFile2=")+ readsFile2)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
