
package us.kbase.bbtools;

import java.util.HashMap;
import java.util.List;
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
    "in_assembly_paths",
    "in_readslib_path",
    "out_file_basename"
})
public class BBMapLocalParams {

    @JsonProperty("in_assembly_paths")
    private List<String> inAssemblyPaths;
    @JsonProperty("in_readslib_path")
    private java.lang.String inReadslibPath;
    @JsonProperty("out_file_basename")
    private java.lang.String outFileBasename;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("in_assembly_paths")
    public List<String> getInAssemblyPaths() {
        return inAssemblyPaths;
    }

    @JsonProperty("in_assembly_paths")
    public void setInAssemblyPaths(List<String> inAssemblyPaths) {
        this.inAssemblyPaths = inAssemblyPaths;
    }

    public BBMapLocalParams withInAssemblyPaths(List<String> inAssemblyPaths) {
        this.inAssemblyPaths = inAssemblyPaths;
        return this;
    }

    @JsonProperty("in_readslib_path")
    public java.lang.String getInReadslibPath() {
        return inReadslibPath;
    }

    @JsonProperty("in_readslib_path")
    public void setInReadslibPath(java.lang.String inReadslibPath) {
        this.inReadslibPath = inReadslibPath;
    }

    public BBMapLocalParams withInReadslibPath(java.lang.String inReadslibPath) {
        this.inReadslibPath = inReadslibPath;
        return this;
    }

    @JsonProperty("out_file_basename")
    public java.lang.String getOutFileBasename() {
        return outFileBasename;
    }

    @JsonProperty("out_file_basename")
    public void setOutFileBasename(java.lang.String outFileBasename) {
        this.outFileBasename = outFileBasename;
    }

    public BBMapLocalParams withOutFileBasename(java.lang.String outFileBasename) {
        this.outFileBasename = outFileBasename;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((("BBMapLocalParams"+" [inAssemblyPaths=")+ inAssemblyPaths)+", inReadslibPath=")+ inReadslibPath)+", outFileBasename=")+ outFileBasename)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
