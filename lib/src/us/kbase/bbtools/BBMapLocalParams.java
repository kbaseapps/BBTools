
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
 * <p>Original spec-file type: BBMapLocalParams</p>
 * <pre>
 * BBMap Local IO
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "in_assembly_path",
    "in_readslib_path",
    "out_file_basename"
})
public class BBMapLocalParams {

    @JsonProperty("in_assembly_path")
    private String inAssemblyPath;
    @JsonProperty("in_readslib_path")
    private String inReadslibPath;
    @JsonProperty("out_file_basename")
    private String outFileBasename;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("in_assembly_path")
    public String getInAssemblyPath() {
        return inAssemblyPath;
    }

    @JsonProperty("in_assembly_path")
    public void setInAssemblyPath(String inAssemblyPath) {
        this.inAssemblyPath = inAssemblyPath;
    }

    public BBMapLocalParams withInAssemblyPath(String inAssemblyPath) {
        this.inAssemblyPath = inAssemblyPath;
        return this;
    }

    @JsonProperty("in_readslib_path")
    public String getInReadslibPath() {
        return inReadslibPath;
    }

    @JsonProperty("in_readslib_path")
    public void setInReadslibPath(String inReadslibPath) {
        this.inReadslibPath = inReadslibPath;
    }

    public BBMapLocalParams withInReadslibPath(String inReadslibPath) {
        this.inReadslibPath = inReadslibPath;
        return this;
    }

    @JsonProperty("out_file_basename")
    public String getOutFileBasename() {
        return outFileBasename;
    }

    @JsonProperty("out_file_basename")
    public void setOutFileBasename(String outFileBasename) {
        this.outFileBasename = outFileBasename;
    }

    public BBMapLocalParams withOutFileBasename(String outFileBasename) {
        this.outFileBasename = outFileBasename;
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
        return ((((((((("BBMapLocalParams"+" [inAssemblyPath=")+ inAssemblyPath)+", inReadslibPath=")+ inReadslibPath)+", outFileBasename=")+ outFileBasename)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
