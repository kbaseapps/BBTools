
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
 * <p>Original spec-file type: MemEstimatorOutput</p>
 * <pre>
 * estimate - the estimated amount of memory required to assemble the paired end files, in GB.
 * size - the total disk space in GB used by the reads files.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "estimate",
    "size"
})
public class MemEstimatorOutput {

    @JsonProperty("estimate")
    private Double estimate;
    @JsonProperty("size")
    private Double size;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("estimate")
    public Double getEstimate() {
        return estimate;
    }

    @JsonProperty("estimate")
    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public MemEstimatorOutput withEstimate(Double estimate) {
        this.estimate = estimate;
        return this;
    }

    @JsonProperty("size")
    public Double getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Double size) {
        this.size = size;
    }

    public MemEstimatorOutput withSize(Double size) {
        this.size = size;
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
        return ((((((("MemEstimatorOutput"+" [estimate=")+ estimate)+", size=")+ size)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
