
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
    "mapped_reads_paths",
    "unmapped_reads_paths",
    "bam_paths",
    "output_directory",
    "run_log",
    "run_command"
})
public class BBMapLocalOutput {

    @JsonProperty("mapped_reads_paths")
    private List<String> mappedReadsPaths;
    @JsonProperty("unmapped_reads_paths")
    private List<String> unmappedReadsPaths;
    @JsonProperty("bam_paths")
    private List<String> bamPaths;
    @JsonProperty("output_directory")
    private java.lang.String outputDirectory;
    @JsonProperty("run_log")
    private java.lang.String runLog;
    @JsonProperty("run_command")
    private java.lang.String runCommand;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("mapped_reads_paths")
    public List<String> getMappedReadsPaths() {
        return mappedReadsPaths;
    }

    @JsonProperty("mapped_reads_paths")
    public void setMappedReadsPaths(List<String> mappedReadsPaths) {
        this.mappedReadsPaths = mappedReadsPaths;
    }

    public BBMapLocalOutput withMappedReadsPaths(List<String> mappedReadsPaths) {
        this.mappedReadsPaths = mappedReadsPaths;
        return this;
    }

    @JsonProperty("unmapped_reads_paths")
    public List<String> getUnmappedReadsPaths() {
        return unmappedReadsPaths;
    }

    @JsonProperty("unmapped_reads_paths")
    public void setUnmappedReadsPaths(List<String> unmappedReadsPaths) {
        this.unmappedReadsPaths = unmappedReadsPaths;
    }

    public BBMapLocalOutput withUnmappedReadsPaths(List<String> unmappedReadsPaths) {
        this.unmappedReadsPaths = unmappedReadsPaths;
        return this;
    }

    @JsonProperty("bam_paths")
    public List<String> getBamPaths() {
        return bamPaths;
    }

    @JsonProperty("bam_paths")
    public void setBamPaths(List<String> bamPaths) {
        this.bamPaths = bamPaths;
    }

    public BBMapLocalOutput withBamPaths(List<String> bamPaths) {
        this.bamPaths = bamPaths;
        return this;
    }

    @JsonProperty("output_directory")
    public java.lang.String getOutputDirectory() {
        return outputDirectory;
    }

    @JsonProperty("output_directory")
    public void setOutputDirectory(java.lang.String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public BBMapLocalOutput withOutputDirectory(java.lang.String outputDirectory) {
        this.outputDirectory = outputDirectory;
        return this;
    }

    @JsonProperty("run_log")
    public java.lang.String getRunLog() {
        return runLog;
    }

    @JsonProperty("run_log")
    public void setRunLog(java.lang.String runLog) {
        this.runLog = runLog;
    }

    public BBMapLocalOutput withRunLog(java.lang.String runLog) {
        this.runLog = runLog;
        return this;
    }

    @JsonProperty("run_command")
    public java.lang.String getRunCommand() {
        return runCommand;
    }

    @JsonProperty("run_command")
    public void setRunCommand(java.lang.String runCommand) {
        this.runCommand = runCommand;
    }

    public BBMapLocalOutput withRunCommand(java.lang.String runCommand) {
        this.runCommand = runCommand;
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
        return ((((((((((((((("BBMapLocalOutput"+" [mappedReadsPaths=")+ mappedReadsPaths)+", unmappedReadsPaths=")+ unmappedReadsPaths)+", bamPaths=")+ bamPaths)+", outputDirectory=")+ outputDirectory)+", runLog=")+ runLog)+", runCommand=")+ runCommand)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
