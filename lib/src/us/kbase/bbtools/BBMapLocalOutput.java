
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
 * <p>Original spec-file type: BBMapLocalOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "out_mapped_reads_path",
    "out_unmapped_reads_path",
    "out_bam_path"
})
public class BBMapLocalOutput {

    @JsonProperty("out_mapped_reads_path")
    private String outMappedReadsPath;
    @JsonProperty("out_unmapped_reads_path")
    private String outUnmappedReadsPath;
    @JsonProperty("out_bam_path")
    private String outBamPath;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("out_mapped_reads_path")
    public String getOutMappedReadsPath() {
        return outMappedReadsPath;
    }

    @JsonProperty("out_mapped_reads_path")
    public void setOutMappedReadsPath(String outMappedReadsPath) {
        this.outMappedReadsPath = outMappedReadsPath;
    }

    public BBMapLocalOutput withOutMappedReadsPath(String outMappedReadsPath) {
        this.outMappedReadsPath = outMappedReadsPath;
        return this;
    }

    @JsonProperty("out_unmapped_reads_path")
    public String getOutUnmappedReadsPath() {
        return outUnmappedReadsPath;
    }

    @JsonProperty("out_unmapped_reads_path")
    public void setOutUnmappedReadsPath(String outUnmappedReadsPath) {
        this.outUnmappedReadsPath = outUnmappedReadsPath;
    }

    public BBMapLocalOutput withOutUnmappedReadsPath(String outUnmappedReadsPath) {
        this.outUnmappedReadsPath = outUnmappedReadsPath;
        return this;
    }

    @JsonProperty("out_bam_path")
    public String getOutBamPath() {
        return outBamPath;
    }

    @JsonProperty("out_bam_path")
    public void setOutBamPath(String outBamPath) {
        this.outBamPath = outBamPath;
    }

    public BBMapLocalOutput withOutBamPath(String outBamPath) {
        this.outBamPath = outBamPath;
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
        return ((((((((("BBMapLocalOutput"+" [outMappedReadsPath=")+ outMappedReadsPath)+", outUnmappedReadsPath=")+ outUnmappedReadsPath)+", outBamPath=")+ outBamPath)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
