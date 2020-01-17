
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
 * <p>Original spec-file type: BBMapInputParams</p>
 * <pre>
 * BBMap method (and App)
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "workspace_name",
    "in_assembly_ref",
    "in_readslib_ref",
    "out_obj_name",
    "out_mode",
    "input_parameter_suite",
    "use_modulo",
    "speed_mode",
    "min_id",
    "bandwidth",
    "min_hits",
    "kmer_len",
    "max_indel",
    "strict_max_indel",
    "subfilter_thresh",
    "delfilter_thresh",
    "require_correct_strand",
    "perfect_mode",
    "semiperfect_mode",
    "qual_score_mode"
})
public class BBMapInputParams {

    @JsonProperty("workspace_name")
    private String workspaceName;
    @JsonProperty("in_assembly_ref")
    private String inAssemblyRef;
    @JsonProperty("in_readslib_ref")
    private String inReadslibRef;
    @JsonProperty("out_obj_name")
    private String outObjName;
    @JsonProperty("out_mode")
    private String outMode;
    @JsonProperty("input_parameter_suite")
    private String inputParameterSuite;
    @JsonProperty("use_modulo")
    private Long useModulo;
    @JsonProperty("speed_mode")
    private String speedMode;
    @JsonProperty("min_id")
    private Double minId;
    @JsonProperty("bandwidth")
    private String bandwidth;
    @JsonProperty("min_hits")
    private Long minHits;
    @JsonProperty("kmer_len")
    private Long kmerLen;
    @JsonProperty("max_indel")
    private Long maxIndel;
    @JsonProperty("strict_max_indel")
    private Long strictMaxIndel;
    @JsonProperty("subfilter_thresh")
    private Long subfilterThresh;
    @JsonProperty("delfilter_thresh")
    private Long delfilterThresh;
    @JsonProperty("require_correct_strand")
    private Long requireCorrectStrand;
    @JsonProperty("perfect_mode")
    private Long perfectMode;
    @JsonProperty("semiperfect_mode")
    private Long semiperfectMode;
    @JsonProperty("qual_score_mode")
    private Long qualScoreMode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("workspace_name")
    public String getWorkspaceName() {
        return workspaceName;
    }

    @JsonProperty("workspace_name")
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public BBMapInputParams withWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
        return this;
    }

    @JsonProperty("in_assembly_ref")
    public String getInAssemblyRef() {
        return inAssemblyRef;
    }

    @JsonProperty("in_assembly_ref")
    public void setInAssemblyRef(String inAssemblyRef) {
        this.inAssemblyRef = inAssemblyRef;
    }

    public BBMapInputParams withInAssemblyRef(String inAssemblyRef) {
        this.inAssemblyRef = inAssemblyRef;
        return this;
    }

    @JsonProperty("in_readslib_ref")
    public String getInReadslibRef() {
        return inReadslibRef;
    }

    @JsonProperty("in_readslib_ref")
    public void setInReadslibRef(String inReadslibRef) {
        this.inReadslibRef = inReadslibRef;
    }

    public BBMapInputParams withInReadslibRef(String inReadslibRef) {
        this.inReadslibRef = inReadslibRef;
        return this;
    }

    @JsonProperty("out_obj_name")
    public String getOutObjName() {
        return outObjName;
    }

    @JsonProperty("out_obj_name")
    public void setOutObjName(String outObjName) {
        this.outObjName = outObjName;
    }

    public BBMapInputParams withOutObjName(String outObjName) {
        this.outObjName = outObjName;
        return this;
    }

    @JsonProperty("out_mode")
    public String getOutMode() {
        return outMode;
    }

    @JsonProperty("out_mode")
    public void setOutMode(String outMode) {
        this.outMode = outMode;
    }

    public BBMapInputParams withOutMode(String outMode) {
        this.outMode = outMode;
        return this;
    }

    @JsonProperty("input_parameter_suite")
    public String getInputParameterSuite() {
        return inputParameterSuite;
    }

    @JsonProperty("input_parameter_suite")
    public void setInputParameterSuite(String inputParameterSuite) {
        this.inputParameterSuite = inputParameterSuite;
    }

    public BBMapInputParams withInputParameterSuite(String inputParameterSuite) {
        this.inputParameterSuite = inputParameterSuite;
        return this;
    }

    @JsonProperty("use_modulo")
    public Long getUseModulo() {
        return useModulo;
    }

    @JsonProperty("use_modulo")
    public void setUseModulo(Long useModulo) {
        this.useModulo = useModulo;
    }

    public BBMapInputParams withUseModulo(Long useModulo) {
        this.useModulo = useModulo;
        return this;
    }

    @JsonProperty("speed_mode")
    public String getSpeedMode() {
        return speedMode;
    }

    @JsonProperty("speed_mode")
    public void setSpeedMode(String speedMode) {
        this.speedMode = speedMode;
    }

    public BBMapInputParams withSpeedMode(String speedMode) {
        this.speedMode = speedMode;
        return this;
    }

    @JsonProperty("min_id")
    public Double getMinId() {
        return minId;
    }

    @JsonProperty("min_id")
    public void setMinId(Double minId) {
        this.minId = minId;
    }

    public BBMapInputParams withMinId(Double minId) {
        this.minId = minId;
        return this;
    }

    @JsonProperty("bandwidth")
    public String getBandwidth() {
        return bandwidth;
    }

    @JsonProperty("bandwidth")
    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public BBMapInputParams withBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
        return this;
    }

    @JsonProperty("min_hits")
    public Long getMinHits() {
        return minHits;
    }

    @JsonProperty("min_hits")
    public void setMinHits(Long minHits) {
        this.minHits = minHits;
    }

    public BBMapInputParams withMinHits(Long minHits) {
        this.minHits = minHits;
        return this;
    }

    @JsonProperty("kmer_len")
    public Long getKmerLen() {
        return kmerLen;
    }

    @JsonProperty("kmer_len")
    public void setKmerLen(Long kmerLen) {
        this.kmerLen = kmerLen;
    }

    public BBMapInputParams withKmerLen(Long kmerLen) {
        this.kmerLen = kmerLen;
        return this;
    }

    @JsonProperty("max_indel")
    public Long getMaxIndel() {
        return maxIndel;
    }

    @JsonProperty("max_indel")
    public void setMaxIndel(Long maxIndel) {
        this.maxIndel = maxIndel;
    }

    public BBMapInputParams withMaxIndel(Long maxIndel) {
        this.maxIndel = maxIndel;
        return this;
    }

    @JsonProperty("strict_max_indel")
    public Long getStrictMaxIndel() {
        return strictMaxIndel;
    }

    @JsonProperty("strict_max_indel")
    public void setStrictMaxIndel(Long strictMaxIndel) {
        this.strictMaxIndel = strictMaxIndel;
    }

    public BBMapInputParams withStrictMaxIndel(Long strictMaxIndel) {
        this.strictMaxIndel = strictMaxIndel;
        return this;
    }

    @JsonProperty("subfilter_thresh")
    public Long getSubfilterThresh() {
        return subfilterThresh;
    }

    @JsonProperty("subfilter_thresh")
    public void setSubfilterThresh(Long subfilterThresh) {
        this.subfilterThresh = subfilterThresh;
    }

    public BBMapInputParams withSubfilterThresh(Long subfilterThresh) {
        this.subfilterThresh = subfilterThresh;
        return this;
    }

    @JsonProperty("delfilter_thresh")
    public Long getDelfilterThresh() {
        return delfilterThresh;
    }

    @JsonProperty("delfilter_thresh")
    public void setDelfilterThresh(Long delfilterThresh) {
        this.delfilterThresh = delfilterThresh;
    }

    public BBMapInputParams withDelfilterThresh(Long delfilterThresh) {
        this.delfilterThresh = delfilterThresh;
        return this;
    }

    @JsonProperty("require_correct_strand")
    public Long getRequireCorrectStrand() {
        return requireCorrectStrand;
    }

    @JsonProperty("require_correct_strand")
    public void setRequireCorrectStrand(Long requireCorrectStrand) {
        this.requireCorrectStrand = requireCorrectStrand;
    }

    public BBMapInputParams withRequireCorrectStrand(Long requireCorrectStrand) {
        this.requireCorrectStrand = requireCorrectStrand;
        return this;
    }

    @JsonProperty("perfect_mode")
    public Long getPerfectMode() {
        return perfectMode;
    }

    @JsonProperty("perfect_mode")
    public void setPerfectMode(Long perfectMode) {
        this.perfectMode = perfectMode;
    }

    public BBMapInputParams withPerfectMode(Long perfectMode) {
        this.perfectMode = perfectMode;
        return this;
    }

    @JsonProperty("semiperfect_mode")
    public Long getSemiperfectMode() {
        return semiperfectMode;
    }

    @JsonProperty("semiperfect_mode")
    public void setSemiperfectMode(Long semiperfectMode) {
        this.semiperfectMode = semiperfectMode;
    }

    public BBMapInputParams withSemiperfectMode(Long semiperfectMode) {
        this.semiperfectMode = semiperfectMode;
        return this;
    }

    @JsonProperty("qual_score_mode")
    public Long getQualScoreMode() {
        return qualScoreMode;
    }

    @JsonProperty("qual_score_mode")
    public void setQualScoreMode(Long qualScoreMode) {
        this.qualScoreMode = qualScoreMode;
    }

    public BBMapInputParams withQualScoreMode(Long qualScoreMode) {
        this.qualScoreMode = qualScoreMode;
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
        return ((((((((((((((((((((((((((((((((((((((((((("BBMapInputParams"+" [workspaceName=")+ workspaceName)+", inAssemblyRef=")+ inAssemblyRef)+", inReadslibRef=")+ inReadslibRef)+", outObjName=")+ outObjName)+", outMode=")+ outMode)+", inputParameterSuite=")+ inputParameterSuite)+", useModulo=")+ useModulo)+", speedMode=")+ speedMode)+", minId=")+ minId)+", bandwidth=")+ bandwidth)+", minHits=")+ minHits)+", kmerLen=")+ kmerLen)+", maxIndel=")+ maxIndel)+", strictMaxIndel=")+ strictMaxIndel)+", subfilterThresh=")+ subfilterThresh)+", delfilterThresh=")+ delfilterThresh)+", requireCorrectStrand=")+ requireCorrectStrand)+", perfectMode=")+ perfectMode)+", semiperfectMode=")+ semiperfectMode)+", qualScoreMode=")+ qualScoreMode)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
