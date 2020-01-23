
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
 * <p>Original spec-file type: BBMapParams</p>
 * <pre>
 * BBMap method (App and Local)
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "get_mapped_reads",
    "get_unmapped_reads",
    "get_bam",
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
public class BBMapParams {

    @JsonProperty("get_mapped_reads")
    private String getMappedReads;
    @JsonProperty("get_unmapped_reads")
    private String getUnmappedReads;
    @JsonProperty("get_bam")
    private String getBam;
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

    @JsonProperty("get_mapped_reads")
    public String getGetMappedReads() {
        return getMappedReads;
    }

    @JsonProperty("get_mapped_reads")
    public void setGetMappedReads(String getMappedReads) {
        this.getMappedReads = getMappedReads;
    }

    public BBMapParams withGetMappedReads(String getMappedReads) {
        this.getMappedReads = getMappedReads;
        return this;
    }

    @JsonProperty("get_unmapped_reads")
    public String getGetUnmappedReads() {
        return getUnmappedReads;
    }

    @JsonProperty("get_unmapped_reads")
    public void setGetUnmappedReads(String getUnmappedReads) {
        this.getUnmappedReads = getUnmappedReads;
    }

    public BBMapParams withGetUnmappedReads(String getUnmappedReads) {
        this.getUnmappedReads = getUnmappedReads;
        return this;
    }

    @JsonProperty("get_bam")
    public String getGetBam() {
        return getBam;
    }

    @JsonProperty("get_bam")
    public void setGetBam(String getBam) {
        this.getBam = getBam;
    }

    public BBMapParams withGetBam(String getBam) {
        this.getBam = getBam;
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

    public BBMapParams withInputParameterSuite(String inputParameterSuite) {
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

    public BBMapParams withUseModulo(Long useModulo) {
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

    public BBMapParams withSpeedMode(String speedMode) {
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

    public BBMapParams withMinId(Double minId) {
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

    public BBMapParams withBandwidth(String bandwidth) {
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

    public BBMapParams withMinHits(Long minHits) {
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

    public BBMapParams withKmerLen(Long kmerLen) {
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

    public BBMapParams withMaxIndel(Long maxIndel) {
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

    public BBMapParams withStrictMaxIndel(Long strictMaxIndel) {
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

    public BBMapParams withSubfilterThresh(Long subfilterThresh) {
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

    public BBMapParams withDelfilterThresh(Long delfilterThresh) {
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

    public BBMapParams withRequireCorrectStrand(Long requireCorrectStrand) {
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

    public BBMapParams withPerfectMode(Long perfectMode) {
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

    public BBMapParams withSemiperfectMode(Long semiperfectMode) {
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

    public BBMapParams withQualScoreMode(Long qualScoreMode) {
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
        return ((((((((((((((((((((((((((((((((((((((("BBMapParams"+" [getMappedReads=")+ getMappedReads)+", getUnmappedReads=")+ getUnmappedReads)+", getBam=")+ getBam)+", inputParameterSuite=")+ inputParameterSuite)+", useModulo=")+ useModulo)+", speedMode=")+ speedMode)+", minId=")+ minId)+", bandwidth=")+ bandwidth)+", minHits=")+ minHits)+", kmerLen=")+ kmerLen)+", maxIndel=")+ maxIndel)+", strictMaxIndel=")+ strictMaxIndel)+", subfilterThresh=")+ subfilterThresh)+", delfilterThresh=")+ delfilterThresh)+", requireCorrectStrand=")+ requireCorrectStrand)+", perfectMode=")+ perfectMode)+", semiperfectMode=")+ semiperfectMode)+", qualScoreMode=")+ qualScoreMode)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
