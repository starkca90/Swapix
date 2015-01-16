package net.swapix.oauth;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential.AccessMethod;

/**
 * Enum that encapsulates the various OAuth2 connection parameters for the different providers
 * <p/>
 * We capture the following properties for the demo application
 * <p/>
 * clientId
 * clientSecret
 * scope
 * rederictUri
 * apiUrl
 * tokenServerUrl
 * authorizationServerEncodedUrl
 * accessMethod
 *
 * @author davydewaele
 */
public enum Oauth2Params {

    SWAPIX_OAUTH("wRnthGhTyxUpsVOB4AzwJaCO3MbOywXdwbPY4bxT", "jQxaFN3cUyIxIXNAFFqztbLWzd9dgVZ1Vk562NaC", "http://picture.jessestark.com/apps/access_token", "http://picture.jessestark.com/apps/authorize", BearerToken.authorizationHeaderAccessMethod(), "", "http://picture.jessestark.com/home", "Swapix", "http://picture.jessestark.com/token_login");

    private String clientId;
    private String clientSecret;
    private String scope;
    private String rederictUri;
    private String userId;
    private String apiUrl;

    private String tokenServerUrl;
    private String authorizationServerEncodedUrl;

    private AccessMethod accessMethod;

    Oauth2Params(String clientId, String clientSecret, String tokenServerUrl, String authorizationServerEncodedUrl, AccessMethod accessMethod, String scope, String rederictUri, String userId, String apiUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenServerUrl = tokenServerUrl;
        this.authorizationServerEncodedUrl = authorizationServerEncodedUrl;
        this.accessMethod = accessMethod;
        this.scope = scope;
        this.rederictUri = rederictUri;
        this.userId = userId;
        this.apiUrl = apiUrl;
    }

    public String getClientId() {
        if (this.clientId == null || this.clientId.length() == 0) {
            throw new IllegalArgumentException("Please provide a valid clientId in the Oauth2Params class");
        }
        return clientId;
    }

    public String getClientSecret() {
        if (this.clientSecret == null || this.clientSecret.length() == 0) {
            throw new IllegalArgumentException("Please provide a valid clientSecret in the Oauth2Params class");
        }
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public String getRederictUri() {
        return rederictUri;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getTokenServerUrl() {
        return tokenServerUrl;
    }

    public String getAuthorizationServerEncodedUrl() {
        return authorizationServerEncodedUrl;
    }

    public AccessMethod getAccessMethod() {
        return accessMethod;
    }

    public String getUserId() {
        return userId;
    }
}
