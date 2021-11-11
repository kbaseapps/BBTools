
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
 * <p>Original spec-file type: BBMapAppOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "report_name",
    "report_ref",
    "run_command",
    "coverage",
    "read_align_stats"
})
public class BBMapAppOutput {

    @JsonProperty("report_name")
    private java.lang.String reportName;
    @JsonProperty("report_ref")
    private java.lang.String reportRef;
    @JsonProperty("run_command")
    private java.lang.String runCommand;
    @JsonProperty("coverage")
    private List<Map<String, Map<String, Double>>> coverage;
    @JsonProperty("read_align_stats")
    private List<Map<String, Double>> readAlignStats;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("report_name")
    public java.lang.String getReportName() {
        return reportName;
    }

    @JsonProperty("report_name")
    public void setReportName(java.lang.String reportName) {
        this.reportName = reportName;
    }

    public BBMapAppOutput withReportName(java.lang.String reportName) {
        this.reportName = reportName;
        return this;
    }

    @JsonProperty("report_ref")
    public java.lang.String getReportRef() {
        return reportRef;
    }

    @JsonProperty("report_ref")
    public void setReportRef(java.lang.String reportRef) {
        this.reportRef = reportRef;
    }

    public BBMapAppOutput withReportRef(java.lang.String reportRef) {
        this.reportRef = reportRef;
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

    public BBMapAppOutput withRunCommand(java.lang.String runCommand) {
        this.runCommand = runCommand;
        return this;
    }

    @JsonProperty("coverage")
    public List<Map<String, Map<String, Double>>> getCoverage() {
        return coverage;
    }

    @JsonProperty("coverage")
    public void setCoverage(List<Map<String, Map<String, Double>>> coverage) {
        this.coverage = coverage;
    }

    public BBMapAppOutput withCoverage(List<Map<String, Map<String, Double>>> coverage) {
        this.coverage = coverage;
        return this;
    }

    @JsonProperty("read_align_stats")
    public List<Map<String, Double>> getReadAlignStats() {
        return readAlignStats;
    }

    @JsonProperty("read_align_stats")
    public void setReadAlignStats(List<Map<String, Double>> readAlignStats) {
        this.readAlignStats = readAlignStats;
    }

    public BBMapAppOutput withReadAlignStats(List<Map<String, Double>> readAlignStats) {
        this.readAlignStats = readAlignStats;
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
        return ((((((((((((("BBMapAppOutput"+" [reportName=")+ reportName)+", reportRef=")+ reportRef)+", runCommand=")+ runCommand)+", coverage=")+ coverage)+", readAlignStats=")+ readAlignStats)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
