
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
 * <p>Original spec-file type: RQCFilterParams</p>
 * <pre>
 * Contains all parameters for the RQCFilter program, EXCEPT for the inputs and outputs.
 * Those are added specifically by each function. This lets us describe them separately for the
 * local function that works mainly against the file system and the app that mainly works against
 * the Workspace.
 * This doesn't cover all of the 110+ parameters provided by rqcfilter. Those not listed here
 * are left as default values, except sketch=f (as that sends data to JGI servers for processing),
 * barcodefilter=f, and mapk=13.
 * Notes below are taken from the help output from rqcfilter2.sh ver 38.00
 * Parameters (format = param name - default - description):
 * ---------------------------------------------------------
 * library - frag - should be one of 'frag', 'clip', 'lfpe', or 'clrs'.
 * Adapter trimming parameters:
 * ----------------------------
 * trimfragadapter - f - Trim all known Illumina adapter sequences, including TruSeq and Nextera.
 * Quality trimming parameters:
 * ----------------------------
 * qtrim - f - Trim read ends to remove bases with quality below minq. Performed AFTER looking for kmers.
 *         Values: rl (trim both ends), f (neither end), r (right end only), l (left end only).
 * trimq - 10 - Trim quality threshold.  Must also set qtrim for direction, will be ignored if qtrim=f
 * maxns - 0 - Reads with more Ns than this will be discarded.
 * minavgquality - 5 - (maq) Reads with average quality (before trimming) below this will be discarded.
 * minlength - 45 - (ml) Reads shorter than this after trimming will be discarded.  Pairs will be discarded only if both are shorter.
 * mlf - 0.333 - (minlengthfraction) Reads shorter than this fraction of original length after trimming will be discarded.
 * trimhdist - 1 - Hamming distance used for trimming.
 * trimhdist2 - same as trimhdist - Hamming distance used for trimming with short kmers.  If unset, trimhdist will be used.
 * mink - 11 - Minimum kmer length for short kmers when trimming.
 * Mapping parameters (for vertebrate contaminants):
 * -------------------------------------------------
 * removemouse - f - (mouse) Remove mouse reads via mapping.
 * removecat - f - (cat) Remove cat reads via mapping.
 * removedog - f - (dog) Remove dog reads via mapping.
 * removehuman - f - (human) Remove human reads via mapping.
 * Microbial contaminant removal parameters:
 * -----------------------------------------
 * removemicrobes - f - (microbes) Remove common contaminant microbial reads via mapping, and place them in a separate file.
 * taxlist - emptylist - (tax) Remove these taxa from the database before filtering.  Typically, this would be the organism name or NCBI ID, or a comma-delimited list.  Organism names should have underscores instead of spaces, such as Escherichia_coli.
 * Filtering parameters (for artificial and microbial contaminants):
 * -----------------------------------------------------------------
 * rna - f - Remove reads containing RNA-specific artifacts.
 * phix - t - Remove reads containing phiX kmers.
 * Clumpify parameters:
 * --------------------
 * clumpify - f - Run clumpify.
 * dedupe - f - Remove duplicate reads.
 * opticaldupes - f - Remove optical duplicates (Clumpify optical flag).
 * Other processing parameters:
 * ----------------------------
 * khist - f - Set to true to generate a kmer-frequency histogram of the output data. (included in report in the app, as a file in local function)
 * Memory requirements (DON'T EXPOSE THESE TO APPS):
 * -------------------------------------------------
 * maxmem - 50 - Set maximum memory flag for RQCFilter to try to allocate. Should be an integer, in GB.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "library",
    "trimfragadapter",
    "qtrim",
    "trimq",
    "maxns",
    "minavgquality",
    "minlength",
    "trimhdist",
    "trimhdist2",
    "mink",
    "mlf",
    "removemouse",
    "removecat",
    "removedog",
    "removehuman",
    "removemicrobes",
    "taxlist",
    "rna",
    "phix",
    "clumpify",
    "dedupe",
    "opticaldupes",
    "khist",
    "maxmem"
})
public class RQCFilterParams {

    @JsonProperty("library")
    private java.lang.String library;
    @JsonProperty("trimfragadapter")
    private Long trimfragadapter;
    @JsonProperty("qtrim")
    private java.lang.String qtrim;
    @JsonProperty("trimq")
    private Long trimq;
    @JsonProperty("maxns")
    private Long maxns;
    @JsonProperty("minavgquality")
    private Long minavgquality;
    @JsonProperty("minlength")
    private Long minlength;
    @JsonProperty("trimhdist")
    private Long trimhdist;
    @JsonProperty("trimhdist2")
    private Long trimhdist2;
    @JsonProperty("mink")
    private Long mink;
    @JsonProperty("mlf")
    private Double mlf;
    @JsonProperty("removemouse")
    private Long removemouse;
    @JsonProperty("removecat")
    private Long removecat;
    @JsonProperty("removedog")
    private Long removedog;
    @JsonProperty("removehuman")
    private Long removehuman;
    @JsonProperty("removemicrobes")
    private Long removemicrobes;
    @JsonProperty("taxlist")
    private List<String> taxlist;
    @JsonProperty("rna")
    private Long rna;
    @JsonProperty("phix")
    private Long phix;
    @JsonProperty("clumpify")
    private Long clumpify;
    @JsonProperty("dedupe")
    private Long dedupe;
    @JsonProperty("opticaldupes")
    private Long opticaldupes;
    @JsonProperty("khist")
    private Long khist;
    @JsonProperty("maxmem")
    private Long maxmem;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("library")
    public java.lang.String getLibrary() {
        return library;
    }

    @JsonProperty("library")
    public void setLibrary(java.lang.String library) {
        this.library = library;
    }

    public RQCFilterParams withLibrary(java.lang.String library) {
        this.library = library;
        return this;
    }

    @JsonProperty("trimfragadapter")
    public Long getTrimfragadapter() {
        return trimfragadapter;
    }

    @JsonProperty("trimfragadapter")
    public void setTrimfragadapter(Long trimfragadapter) {
        this.trimfragadapter = trimfragadapter;
    }

    public RQCFilterParams withTrimfragadapter(Long trimfragadapter) {
        this.trimfragadapter = trimfragadapter;
        return this;
    }

    @JsonProperty("qtrim")
    public java.lang.String getQtrim() {
        return qtrim;
    }

    @JsonProperty("qtrim")
    public void setQtrim(java.lang.String qtrim) {
        this.qtrim = qtrim;
    }

    public RQCFilterParams withQtrim(java.lang.String qtrim) {
        this.qtrim = qtrim;
        return this;
    }

    @JsonProperty("trimq")
    public Long getTrimq() {
        return trimq;
    }

    @JsonProperty("trimq")
    public void setTrimq(Long trimq) {
        this.trimq = trimq;
    }

    public RQCFilterParams withTrimq(Long trimq) {
        this.trimq = trimq;
        return this;
    }

    @JsonProperty("maxns")
    public Long getMaxns() {
        return maxns;
    }

    @JsonProperty("maxns")
    public void setMaxns(Long maxns) {
        this.maxns = maxns;
    }

    public RQCFilterParams withMaxns(Long maxns) {
        this.maxns = maxns;
        return this;
    }

    @JsonProperty("minavgquality")
    public Long getMinavgquality() {
        return minavgquality;
    }

    @JsonProperty("minavgquality")
    public void setMinavgquality(Long minavgquality) {
        this.minavgquality = minavgquality;
    }

    public RQCFilterParams withMinavgquality(Long minavgquality) {
        this.minavgquality = minavgquality;
        return this;
    }

    @JsonProperty("minlength")
    public Long getMinlength() {
        return minlength;
    }

    @JsonProperty("minlength")
    public void setMinlength(Long minlength) {
        this.minlength = minlength;
    }

    public RQCFilterParams withMinlength(Long minlength) {
        this.minlength = minlength;
        return this;
    }

    @JsonProperty("trimhdist")
    public Long getTrimhdist() {
        return trimhdist;
    }

    @JsonProperty("trimhdist")
    public void setTrimhdist(Long trimhdist) {
        this.trimhdist = trimhdist;
    }

    public RQCFilterParams withTrimhdist(Long trimhdist) {
        this.trimhdist = trimhdist;
        return this;
    }

    @JsonProperty("trimhdist2")
    public Long getTrimhdist2() {
        return trimhdist2;
    }

    @JsonProperty("trimhdist2")
    public void setTrimhdist2(Long trimhdist2) {
        this.trimhdist2 = trimhdist2;
    }

    public RQCFilterParams withTrimhdist2(Long trimhdist2) {
        this.trimhdist2 = trimhdist2;
        return this;
    }

    @JsonProperty("mink")
    public Long getMink() {
        return mink;
    }

    @JsonProperty("mink")
    public void setMink(Long mink) {
        this.mink = mink;
    }

    public RQCFilterParams withMink(Long mink) {
        this.mink = mink;
        return this;
    }

    @JsonProperty("mlf")
    public Double getMlf() {
        return mlf;
    }

    @JsonProperty("mlf")
    public void setMlf(Double mlf) {
        this.mlf = mlf;
    }

    public RQCFilterParams withMlf(Double mlf) {
        this.mlf = mlf;
        return this;
    }

    @JsonProperty("removemouse")
    public Long getRemovemouse() {
        return removemouse;
    }

    @JsonProperty("removemouse")
    public void setRemovemouse(Long removemouse) {
        this.removemouse = removemouse;
    }

    public RQCFilterParams withRemovemouse(Long removemouse) {
        this.removemouse = removemouse;
        return this;
    }

    @JsonProperty("removecat")
    public Long getRemovecat() {
        return removecat;
    }

    @JsonProperty("removecat")
    public void setRemovecat(Long removecat) {
        this.removecat = removecat;
    }

    public RQCFilterParams withRemovecat(Long removecat) {
        this.removecat = removecat;
        return this;
    }

    @JsonProperty("removedog")
    public Long getRemovedog() {
        return removedog;
    }

    @JsonProperty("removedog")
    public void setRemovedog(Long removedog) {
        this.removedog = removedog;
    }

    public RQCFilterParams withRemovedog(Long removedog) {
        this.removedog = removedog;
        return this;
    }

    @JsonProperty("removehuman")
    public Long getRemovehuman() {
        return removehuman;
    }

    @JsonProperty("removehuman")
    public void setRemovehuman(Long removehuman) {
        this.removehuman = removehuman;
    }

    public RQCFilterParams withRemovehuman(Long removehuman) {
        this.removehuman = removehuman;
        return this;
    }

    @JsonProperty("removemicrobes")
    public Long getRemovemicrobes() {
        return removemicrobes;
    }

    @JsonProperty("removemicrobes")
    public void setRemovemicrobes(Long removemicrobes) {
        this.removemicrobes = removemicrobes;
    }

    public RQCFilterParams withRemovemicrobes(Long removemicrobes) {
        this.removemicrobes = removemicrobes;
        return this;
    }

    @JsonProperty("taxlist")
    public List<String> getTaxlist() {
        return taxlist;
    }

    @JsonProperty("taxlist")
    public void setTaxlist(List<String> taxlist) {
        this.taxlist = taxlist;
    }

    public RQCFilterParams withTaxlist(List<String> taxlist) {
        this.taxlist = taxlist;
        return this;
    }

    @JsonProperty("rna")
    public Long getRna() {
        return rna;
    }

    @JsonProperty("rna")
    public void setRna(Long rna) {
        this.rna = rna;
    }

    public RQCFilterParams withRna(Long rna) {
        this.rna = rna;
        return this;
    }

    @JsonProperty("phix")
    public Long getPhix() {
        return phix;
    }

    @JsonProperty("phix")
    public void setPhix(Long phix) {
        this.phix = phix;
    }

    public RQCFilterParams withPhix(Long phix) {
        this.phix = phix;
        return this;
    }

    @JsonProperty("clumpify")
    public Long getClumpify() {
        return clumpify;
    }

    @JsonProperty("clumpify")
    public void setClumpify(Long clumpify) {
        this.clumpify = clumpify;
    }

    public RQCFilterParams withClumpify(Long clumpify) {
        this.clumpify = clumpify;
        return this;
    }

    @JsonProperty("dedupe")
    public Long getDedupe() {
        return dedupe;
    }

    @JsonProperty("dedupe")
    public void setDedupe(Long dedupe) {
        this.dedupe = dedupe;
    }

    public RQCFilterParams withDedupe(Long dedupe) {
        this.dedupe = dedupe;
        return this;
    }

    @JsonProperty("opticaldupes")
    public Long getOpticaldupes() {
        return opticaldupes;
    }

    @JsonProperty("opticaldupes")
    public void setOpticaldupes(Long opticaldupes) {
        this.opticaldupes = opticaldupes;
    }

    public RQCFilterParams withOpticaldupes(Long opticaldupes) {
        this.opticaldupes = opticaldupes;
        return this;
    }

    @JsonProperty("khist")
    public Long getKhist() {
        return khist;
    }

    @JsonProperty("khist")
    public void setKhist(Long khist) {
        this.khist = khist;
    }

    public RQCFilterParams withKhist(Long khist) {
        this.khist = khist;
        return this;
    }

    @JsonProperty("maxmem")
    public Long getMaxmem() {
        return maxmem;
    }

    @JsonProperty("maxmem")
    public void setMaxmem(Long maxmem) {
        this.maxmem = maxmem;
    }

    public RQCFilterParams withMaxmem(Long maxmem) {
        this.maxmem = maxmem;
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
        return ((((((((((((((((((((((((((((((((((((((((((((((((((("RQCFilterParams"+" [library=")+ library)+", trimfragadapter=")+ trimfragadapter)+", qtrim=")+ qtrim)+", trimq=")+ trimq)+", maxns=")+ maxns)+", minavgquality=")+ minavgquality)+", minlength=")+ minlength)+", trimhdist=")+ trimhdist)+", trimhdist2=")+ trimhdist2)+", mink=")+ mink)+", mlf=")+ mlf)+", removemouse=")+ removemouse)+", removecat=")+ removecat)+", removedog=")+ removedog)+", removehuman=")+ removehuman)+", removemicrobes=")+ removemicrobes)+", taxlist=")+ taxlist)+", rna=")+ rna)+", phix=")+ phix)+", clumpify=")+ clumpify)+", dedupe=")+ dedupe)+", opticaldupes=")+ opticaldupes)+", khist=")+ khist)+", maxmem=")+ maxmem)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
