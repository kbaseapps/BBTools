
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
 * <p>Original spec-file type: BBMapAppParams</p>
 * <pre>
 * BBMap App IO
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "workspace_name",
    "in_assembly_refs",
    "in_readslib_ref",
    "out_obj_name"
})
public class BBMapAppParams {

    @JsonProperty("workspace_name")
    private java.lang.String workspaceName;
    @JsonProperty("in_assembly_refs")
    private List<String> inAssemblyRefs;
    @JsonProperty("in_readslib_ref")
    private java.lang.String inReadslibRef;
    @JsonProperty("out_obj_name")
    private java.lang.String outObjName;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("workspace_name")
    public java.lang.String getWorkspaceName() {
        return workspaceName;
    }

    @JsonProperty("workspace_name")
    public void setWorkspaceName(java.lang.String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public BBMapAppParams withWorkspaceName(java.lang.String workspaceName) {
        this.workspaceName = workspaceName;
        return this;
    }

    @JsonProperty("in_assembly_refs")
    public List<String> getInAssemblyRefs() {
        return inAssemblyRefs;
    }

    @JsonProperty("in_assembly_refs")
    public void setInAssemblyRefs(List<String> inAssemblyRefs) {
        this.inAssemblyRefs = inAssemblyRefs;
    }

    public BBMapAppParams withInAssemblyRefs(List<String> inAssemblyRefs) {
        this.inAssemblyRefs = inAssemblyRefs;
        return this;
    }

    @JsonProperty("in_readslib_ref")
    public java.lang.String getInReadslibRef() {
        return inReadslibRef;
    }

    @JsonProperty("in_readslib_ref")
    public void setInReadslibRef(java.lang.String inReadslibRef) {
        this.inReadslibRef = inReadslibRef;
    }

    public BBMapAppParams withInReadslibRef(java.lang.String inReadslibRef) {
        this.inReadslibRef = inReadslibRef;
        return this;
    }

    @JsonProperty("out_obj_name")
    public java.lang.String getOutObjName() {
        return outObjName;
    }

    @JsonProperty("out_obj_name")
    public void setOutObjName(java.lang.String outObjName) {
        this.outObjName = outObjName;
    }

    public BBMapAppParams withOutObjName(java.lang.String outObjName) {
        this.outObjName = outObjName;
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
        return ((((((((((("BBMapAppParams"+" [workspaceName=")+ workspaceName)+", inAssemblyRefs=")+ inAssemblyRefs)+", inReadslibRef=")+ inReadslibRef)+", outObjName=")+ outObjName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
