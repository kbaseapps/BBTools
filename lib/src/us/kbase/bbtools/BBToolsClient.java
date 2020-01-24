package us.kbase.bbtools;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UnauthorizedException;

/**
 * <p>Original spec-file module name: BBTools</p>
 * <pre>
 * </pre>
 */
public class BBToolsClient {
    private JsonClientCaller caller;
    private String serviceVersion = null;


    /** Constructs a client with a custom URL and no user credentials.
     * @param url the URL of the service.
     */
    public BBToolsClient(URL url) {
        caller = new JsonClientCaller(url);
    }
    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public BBToolsClient(URL url, AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, token);
    }

    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public BBToolsClient(URL url, String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password);
    }

    /** Constructs a client with a custom URL
     * and a custom authorization service URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @param auth the URL of the authorization server.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public BBToolsClient(URL url, String user, String password, URL auth) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password, auth);
    }

    /** Get the token this client uses to communicate with the server.
     * @return the authorization token.
     */
    public AuthToken getToken() {
        return caller.getToken();
    }

    /** Get the URL of the service with which this client communicates.
     * @return the service URL.
     */
    public URL getURL() {
        return caller.getURL();
    }

    /** Set the timeout between establishing a connection to a server and
     * receiving a response. A value of zero or null implies no timeout.
     * @param milliseconds the milliseconds to wait before timing out when
     * attempting to read from a server.
     */
    public void setConnectionReadTimeOut(Integer milliseconds) {
        this.caller.setConnectionReadTimeOut(milliseconds);
    }

    /** Check if this client allows insecure http (vs https) connections.
     * @return true if insecure connections are allowed.
     */
    public boolean isInsecureHttpConnectionAllowed() {
        return caller.isInsecureHttpConnectionAllowed();
    }

    /** Deprecated. Use isInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public boolean isAuthAllowedForHttp() {
        return caller.isAuthAllowedForHttp();
    }

    /** Set whether insecure http (vs https) connections should be allowed by
     * this client.
     * @param allowed true to allow insecure connections. Default false
     */
    public void setIsInsecureHttpConnectionAllowed(boolean allowed) {
        caller.setInsecureHttpConnectionAllowed(allowed);
    }

    /** Deprecated. Use setIsInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public void setAuthAllowedForHttp(boolean isAuthAllowedForHttp) {
        caller.setAuthAllowedForHttp(isAuthAllowedForHttp);
    }

    /** Set whether all SSL certificates, including self-signed certificates,
     * should be trusted.
     * @param trustAll true to trust all certificates. Default false.
     */
    public void setAllSSLCertificatesTrusted(final boolean trustAll) {
        caller.setAllSSLCertificatesTrusted(trustAll);
    }
    
    /** Check if this client trusts all SSL certificates, including
     * self-signed certificates.
     * @return true if all certificates are trusted.
     */
    public boolean isAllSSLCertificatesTrusted() {
        return caller.isAllSSLCertificatesTrusted();
    }
    /** Sets streaming mode on. In this case, the data will be streamed to
     * the server in chunks as it is read from disk rather than buffered in
     * memory. Many servers are not compatible with this feature.
     * @param streamRequest true to set streaming mode on, false otherwise.
     */
    public void setStreamingModeOn(boolean streamRequest) {
        caller.setStreamingModeOn(streamRequest);
    }

    /** Returns true if streaming mode is on.
     * @return true if streaming mode is on.
     */
    public boolean isStreamingModeOn() {
        return caller.isStreamingModeOn();
    }

    public void _setFileForNextRpcResponse(File f) {
        caller.setFileForNextRpcResponse(f);
    }

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String newValue) {
        this.serviceVersion = newValue;
    }

    /**
     * <p>Original spec-file function name: run_BBMap</p>
     * <pre>
     * </pre>
     * @param   ioParams   instance of type {@link us.kbase.bbtools.BBMapAppParams BBMapAppParams}
     * @param   runParams   instance of type {@link us.kbase.bbtools.BBMapParams BBMapParams}
     * @return   parameter "output" of type {@link us.kbase.bbtools.BBMapAppOutput BBMapAppOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public BBMapAppOutput runBBMap(BBMapAppParams ioParams, BBMapParams runParams, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ioParams);
        args.add(runParams);
        TypeReference<List<BBMapAppOutput>> retType = new TypeReference<List<BBMapAppOutput>>() {};
        List<BBMapAppOutput> res = caller.jsonrpcCall("BBTools.run_BBMap", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: run_BBMap_local</p>
     * <pre>
     * </pre>
     * @param   ioParams   instance of type {@link us.kbase.bbtools.BBMapLocalParams BBMapLocalParams}
     * @param   runParams   instance of type {@link us.kbase.bbtools.BBMapParams BBMapParams}
     * @return   parameter "output" of type {@link us.kbase.bbtools.BBMapLocalOutput BBMapLocalOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public BBMapLocalOutput runBBMapLocal(BBMapLocalParams ioParams, BBMapParams runParams, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ioParams);
        args.add(runParams);
        TypeReference<List<BBMapLocalOutput>> retType = new TypeReference<List<BBMapLocalOutput>>() {};
        List<BBMapLocalOutput> res = caller.jsonrpcCall("BBTools.run_BBMap_local", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: run_RQCFilter_app</p>
     * <pre>
     * </pre>
     * @param   ioParams   instance of type {@link us.kbase.bbtools.RQCFilterAppParams RQCFilterAppParams}
     * @param   runParams   instance of type {@link us.kbase.bbtools.RQCFilterParams RQCFilterParams}
     * @return   parameter "output" of type {@link us.kbase.bbtools.RQCFilterAppOutput RQCFilterAppOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public RQCFilterAppOutput runRQCFilterApp(RQCFilterAppParams ioParams, RQCFilterParams runParams, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ioParams);
        args.add(runParams);
        TypeReference<List<RQCFilterAppOutput>> retType = new TypeReference<List<RQCFilterAppOutput>>() {};
        List<RQCFilterAppOutput> res = caller.jsonrpcCall("BBTools.run_RQCFilter_app", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: run_RQCFilter_local</p>
     * <pre>
     * </pre>
     * @param   ioParams   instance of type {@link us.kbase.bbtools.RQCFilterLocalParams RQCFilterLocalParams}
     * @param   runParams   instance of type {@link us.kbase.bbtools.RQCFilterParams RQCFilterParams}
     * @return   parameter "output" of type {@link us.kbase.bbtools.RQCFilterLocalOutput RQCFilterLocalOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public RQCFilterLocalOutput runRQCFilterLocal(RQCFilterLocalParams ioParams, RQCFilterParams runParams, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ioParams);
        args.add(runParams);
        TypeReference<List<RQCFilterLocalOutput>> retType = new TypeReference<List<RQCFilterLocalOutput>>() {};
        List<RQCFilterLocalOutput> res = caller.jsonrpcCall("BBTools.run_RQCFilter_local", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: run_mem_estimator</p>
     * <pre>
     * This is a local function that estimates how much memory SPAdes or metaSPAdes needs
     * to assemble a paired end library.
     * Returns a float, representing the estimated memory use in GB.
     * </pre>
     * @param   params   instance of type {@link us.kbase.bbtools.MemEstimatorParams MemEstimatorParams}
     * @return   parameter "output" of type {@link us.kbase.bbtools.MemEstimatorOutput MemEstimatorOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public MemEstimatorOutput runMemEstimator(MemEstimatorParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<MemEstimatorOutput>> retType = new TypeReference<List<MemEstimatorOutput>>() {};
        List<MemEstimatorOutput> res = caller.jsonrpcCall("BBTools.run_mem_estimator", args, retType, true, false, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: bbtools_version</p>
     * <pre>
     * Returns the semantic version of the currently installed BBTools. So something like "38.08"
     * </pre>
     * @return   parameter "version" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String bbtoolsVersion(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("BBTools.bbtools_version", args, retType, true, false, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    public Map<String, Object> status(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<Map<String, Object>>> retType = new TypeReference<List<Map<String, Object>>>() {};
        List<Map<String, Object>> res = caller.jsonrpcCall("BBTools.status", args, retType, true, false, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }
}
