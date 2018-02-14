
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
 * <p>Original spec-file type: RQCFilterAppParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "read_library_ref",
    "output_workspace_name",
    "output_library_name",
    "library",
    "rna",
    "trimfragadapter",
    "qtrim",
    "removemouse",
    "removecat",
    "removedog",
    "removehuman",
    "removemicrobes",
    "taxlist",
    "dedupe",
    "opticaldupes"
})
public class RQCFilterAppParams {

    @JsonProperty("read_library_ref")
    private java.lang.String readLibraryRef;
    @JsonProperty("output_workspace_name")
    private java.lang.String outputWorkspaceName;
    @JsonProperty("output_library_name")
    private java.lang.String outputLibraryName;
    @JsonProperty("library")
    private java.lang.String library;
    @JsonProperty("rna")
    private Long rna;
    @JsonProperty("trimfragadapter")
    private Long trimfragadapter;
    @JsonProperty("qtrim")
    private java.lang.String qtrim;
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
    @JsonProperty("dedupe")
    private Long dedupe;
    @JsonProperty("opticaldupes")
    private Long opticaldupes;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("read_library_ref")
    public java.lang.String getReadLibraryRef() {
        return readLibraryRef;
    }

    @JsonProperty("read_library_ref")
    public void setReadLibraryRef(java.lang.String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
    }

    public RQCFilterAppParams withReadLibraryRef(java.lang.String readLibraryRef) {
        this.readLibraryRef = readLibraryRef;
        return this;
    }

    @JsonProperty("output_workspace_name")
    public java.lang.String getOutputWorkspaceName() {
        return outputWorkspaceName;
    }

    @JsonProperty("output_workspace_name")
    public void setOutputWorkspaceName(java.lang.String outputWorkspaceName) {
        this.outputWorkspaceName = outputWorkspaceName;
    }

    public RQCFilterAppParams withOutputWorkspaceName(java.lang.String outputWorkspaceName) {
        this.outputWorkspaceName = outputWorkspaceName;
        return this;
    }

    @JsonProperty("output_library_name")
    public java.lang.String getOutputLibraryName() {
        return outputLibraryName;
    }

    @JsonProperty("output_library_name")
    public void setOutputLibraryName(java.lang.String outputLibraryName) {
        this.outputLibraryName = outputLibraryName;
    }

    public RQCFilterAppParams withOutputLibraryName(java.lang.String outputLibraryName) {
        this.outputLibraryName = outputLibraryName;
        return this;
    }

    @JsonProperty("library")
    public java.lang.String getLibrary() {
        return library;
    }

    @JsonProperty("library")
    public void setLibrary(java.lang.String library) {
        this.library = library;
    }

    public RQCFilterAppParams withLibrary(java.lang.String library) {
        this.library = library;
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

    public RQCFilterAppParams withRna(Long rna) {
        this.rna = rna;
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

    public RQCFilterAppParams withTrimfragadapter(Long trimfragadapter) {
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

    public RQCFilterAppParams withQtrim(java.lang.String qtrim) {
        this.qtrim = qtrim;
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

    public RQCFilterAppParams withRemovemouse(Long removemouse) {
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

    public RQCFilterAppParams withRemovecat(Long removecat) {
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

    public RQCFilterAppParams withRemovedog(Long removedog) {
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

    public RQCFilterAppParams withRemovehuman(Long removehuman) {
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

    public RQCFilterAppParams withRemovemicrobes(Long removemicrobes) {
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

    public RQCFilterAppParams withTaxlist(List<String> taxlist) {
        this.taxlist = taxlist;
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

    public RQCFilterAppParams withDedupe(Long dedupe) {
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

    public RQCFilterAppParams withOpticaldupes(Long opticaldupes) {
        this.opticaldupes = opticaldupes;
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
        return ((((((((((((((((((((((((((((((((("RQCFilterAppParams"+" [readLibraryRef=")+ readLibraryRef)+", outputWorkspaceName=")+ outputWorkspaceName)+", outputLibraryName=")+ outputLibraryName)+", library=")+ library)+", rna=")+ rna)+", trimfragadapter=")+ trimfragadapter)+", qtrim=")+ qtrim)+", removemouse=")+ removemouse)+", removecat=")+ removecat)+", removedog=")+ removedog)+", removehuman=")+ removehuman)+", removemicrobes=")+ removemicrobes)+", taxlist=")+ taxlist)+", dedupe=")+ dedupe)+", opticaldupes=")+ opticaldupes)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
