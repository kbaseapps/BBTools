
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
 * <p>Original spec-file type: RQCFilterLocalParams</p>
 * <pre>
 * Parameters for local version of RQCFilter.
 * read_library_ref - UPA for the read library to filter.
 * -OR-
 * reads_file - path to the reads file to filter. Expects an interleaved file, if it's paired end.
 * If both of the above are given, the read_library_ref takes precedence.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "read_library_ref",
    "reads_file"
})
public class RQCFilterLocalParams {

    @JsonProperty("read_library_ref")
    private String readLibraryRef;
    @JsonProperty("reads_file")
    private String readsFile;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("read_library_ref")
    public String getReadLibraryRef() {
        return readLibraryRef;
    }

    @JsonProperty("read_library_ref")
    public void setReadLibraryRef(String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
    }

    public RQCFilterLocalParams withReadLibraryRef(String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
        return this;
    }

    @JsonProperty("reads_file")
    public String getReadsFile() {
        return readsFile;
    }

    @JsonProperty("reads_file")
    public void setReadsFile(String readsFile) {
        this.readsFile = readsFile;
    }

    public RQCFilterLocalParams withReadsFile(String readsFile) {
        this.readsFile = readsFile;
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
        return ((((((("RQCFilterLocalParams"+" [readLibraryRef=")+ readLibraryRef)+", readsFile=")+ readsFile)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
