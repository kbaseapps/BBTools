
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
    "in_assembly_ref",
    "in_readslib_ref",
    "out_obj_name"
})
public class BBMapAppParams {

    @JsonProperty("workspace_name")
    private String workspaceName;
    @JsonProperty("in_assembly_ref")
    private String inAssemblyRef;
    @JsonProperty("in_readslib_ref")
    private String inReadslibRef;
    @JsonProperty("out_obj_name")
    private String outObjName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("workspace_name")
    public String getWorkspaceName() {
        return workspaceName;
    }

    @JsonProperty("workspace_name")
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public BBMapAppParams withWorkspaceName(String workspaceName) {
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

    public BBMapAppParams withInAssemblyRef(String inAssemblyRef) {
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

    public BBMapAppParams withInReadslibRef(String inReadslibRef) {
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

    public BBMapAppParams withOutObjName(String outObjName) {
        this.outObjName = outObjName;
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
        return ((((((((((("BBMapAppParams"+" [workspaceName=")+ workspaceName)+", inAssemblyRef=")+ inAssemblyRef)+", inReadslibRef=")+ inReadslibRef)+", outObjName=")+ outObjName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
