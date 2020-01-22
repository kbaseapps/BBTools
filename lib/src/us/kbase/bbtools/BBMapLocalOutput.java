
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
 * <p>Original spec-file type: BBMapLocalOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "out_mapped_reads_paths",
    "out_unmapped_reads_paths",
    "out_bam_paths"
})
public class BBMapLocalOutput {

    @JsonProperty("out_mapped_reads_paths")
    private List<String> outMappedReadsPaths;
    @JsonProperty("out_unmapped_reads_paths")
    private List<String> outUnmappedReadsPaths;
    @JsonProperty("out_bam_paths")
    private List<String> outBamPaths;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("out_mapped_reads_paths")
    public List<String> getOutMappedReadsPaths() {
        return outMappedReadsPaths;
    }

    @JsonProperty("out_mapped_reads_paths")
    public void setOutMappedReadsPaths(List<String> outMappedReadsPaths) {
        this.outMappedReadsPaths = outMappedReadsPaths;
    }

    public BBMapLocalOutput withOutMappedReadsPaths(List<String> outMappedReadsPaths) {
        this.outMappedReadsPaths = outMappedReadsPaths;
        return this;
    }

    @JsonProperty("out_unmapped_reads_paths")
    public List<String> getOutUnmappedReadsPaths() {
        return outUnmappedReadsPaths;
    }

    @JsonProperty("out_unmapped_reads_paths")
    public void setOutUnmappedReadsPaths(List<String> outUnmappedReadsPaths) {
        this.outUnmappedReadsPaths = outUnmappedReadsPaths;
    }

    public BBMapLocalOutput withOutUnmappedReadsPaths(List<String> outUnmappedReadsPaths) {
        this.outUnmappedReadsPaths = outUnmappedReadsPaths;
        return this;
    }

    @JsonProperty("out_bam_paths")
    public List<String> getOutBamPaths() {
        return outBamPaths;
    }

    @JsonProperty("out_bam_paths")
    public void setOutBamPaths(List<String> outBamPaths) {
        this.outBamPaths = outBamPaths;
    }

    public BBMapLocalOutput withOutBamPaths(List<String> outBamPaths) {
        this.outBamPaths = outBamPaths;
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
        return ((((((((("BBMapLocalOutput"+" [outMappedReadsPaths=")+ outMappedReadsPaths)+", outUnmappedReadsPaths=")+ outUnmappedReadsPaths)+", outBamPaths=")+ outBamPaths)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
