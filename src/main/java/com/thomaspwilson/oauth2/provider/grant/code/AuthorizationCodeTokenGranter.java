/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thomaspwilson.oauth2.provider.grant.code;

import com.thomaspwilson.oauth2.provider.AccessTokenManager;
import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.OAuth2Principal;
import com.thomaspwilson.oauth2.provider.Parameters;
import com.thomaspwilson.oauth2.provider.exception.InvalidClientException;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.grant.AbstractTokenGranter;
import com.thomaspwilson.oauth2.provider.io.MutableRequest;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.util.StringUtils;
import java.util.HashSet;

/**
 * The <code>AuthorizationCodeTokenGranter</code> converts an authorization code
 * to an access token.
 * 
 * {@link https://tools.ietf.org/html/rfc6749#section-4.1.3}
 */
public class AuthorizationCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "authorization_code";

    public AuthorizationCodeTokenGranter(AccessTokenManager tokenManager) {
        super(tokenManager);
    }

    @Override
    public AccessToken grant(final Request request) {
        if (!grantType().equals(request.getGrantType())) {
            return null;
        }
        
        validateRequest(request);
        
        Client client = authenticateClient(request);
        
        // Grab the stored principal
        AuthorizationCode code = tokenManager.getCodeService().find(request.getCode());
        if (code == null) {
            throw new InvalidGrantException("Invalid authorization code");
        }
        
        // If the code has already been redeemed, we revoke all access tokens
        // associated with the token and toss an error
        if (code.isRedeemed()) {
            // XXX Revoke access tokens
            throw new InvalidGrantException("Invalid authorization code");
        }
        
        // Ensure the client making the request is the same as the auth code
        // owner
        if (!code.getClientId().equals(client.getId())) {
            throw new InvalidClientException("Authorization code belongs to another client");
        }
        
        // If redirect_uri was included in the authcode request, we MUST ensure
        // that it is identical to the one provided here.
        if (!StringUtils.isBlank(code.getRedirectUri())) {
            if (StringUtils.isBlank(request.getRedirectUri())) {
                throw new InvalidRequestException("Missing or empty field "+Parameters.REDIRECT_URI);
            }
            if (!code.getRedirectUri().equals(request.getRedirectUri())) {
                throw new InvalidGrantException("Requested redirect_uri does not match redirect_uri given in authorization code request.");
            }
        }
        
        // The AccessToken scope must be taken from the scope requested during
        // the authorization code request
        MutableRequest req = new MutableRequest(request);
        req.setScope(new HashSet<>(code.getScope()));
        
        // Create the AccessToken
        OAuth2Principal principal = new OAuth2Principal(client, null);
        AccessToken token = tokenManager.createAccessToken(request, principal);
        tokenManager.getTokenService().save(token);
        
        // Keep the authcode but mark it as redeemed
        code.setIsRedeemed(true);
        tokenManager.getCodeService().save(code);
        
        return token;
    }

    @Override
    protected void validateRequest(Request request) {
        if (StringUtils.isBlank(request.getCode())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CODE);
        }
        if (StringUtils.isBlank(request.getClientId())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_ID);
        }
    }

    @Override
    protected Client authenticateClient(Request request) {
        // TODO: If client is confidential, was issued client credentials, or
        //       was assigned other auth requirements, client MUST authenticate
        //       as described in https://tools.ietf.org/html/rfc6749#section-3.2.1
        if (!StringUtils.isBlank(request.getClientSecret())) {
            return super.authenticateClient(request);
        }
        Client client = clientService.find(request.getClientId());
        if (client.getType().equals(ClientType.CONFIDENTIAL)) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_SECRET);
        }
        return client;
    }
    
    

//    @Override
//    protected OAuth2Principal getOauth2Principal(Client client, Request request) {


// TAKEN FROM AccessTokenManager
////        if (RequestUtil.isAuthCodeRequest(parameters)) {
////            // The scope was requested or determined during the authorization step
////            if (!request.getScope().isEmpty()) {
////                LOG.debug("Clearing scope of incoming token request");
////                request.setScope(null);
////            }
////        }
        
        
////        Map<String, String> parameters = new HashMap<>(request.getRequestParameters());
////        String authorizationCode = parameters.get("code");
////        String redirectUri = parameters.get("redirect_uri");
////
////        if (authorizationCode == null) {
////            throw new InvalidRequestException("An authorization code must be supplied.");
////        }
////
////        OAuth2Principal storedAuth = authorizationCodeServices.consumeAuthorizationCode(authorizationCode);
////        if (storedAuth == null) {
////            throw new InvalidGrantException("Invalid authorization code: " + authorizationCode);
////        }
//
//        Request pendingOAuth2Request = storedAuth.getRequest();
//        // https://jira.springsource.org/browse/SECOAUTH-333
//        // This might be null, if the authorization was done without the redirect_uri parameter
//        String redirectUriApprovalParameter = pendingOAuth2Request.getRequestParameters().get(
//                "redirect_uri");
//
//        if ((redirectUri != null || redirectUriApprovalParameter != null)
//                && !pendingOAuth2Request.getRedirectUri().equals(redirectUri)) {
//            throw new RedirectMismatchException("Redirect URI mismatch.");
//        }
//
////        String pendingClientId = pendingOAuth2Request.getClientId();
////        String clientId = request.getClientId();
////        if (clientId != null && !clientId.equals(pendingClientId)) {
////            // just a sanity check.
////            throw new InvalidClientException("Client ID mismatch");
////        }
//
//		// Secret is not required in the authorization request, so it won't be available
//        // in the pendingAuthorizationRequest. We do want to check that a secret is provided
//        // in the token request, but that happens elsewhere.
//        Map<String, String> combinedParameters = new HashMap<>(pendingOAuth2Request
//                .getRequestParameters());
//        // Combine the parameters adding the new ones last so they override if there are any clashes
//        combinedParameters.putAll(parameters);
//
//        // Make a new stored request with the combined parameters
//        Request finalStoredOAuth2Request = new MutableRequest(pendingOAuth2Request).setRequestParameters(combinedParameters);
//
//        Authentication userAuth = storedAuth.getUserAuthentication();
//
//        return new OAuth2Authentication(finalStoredOAuth2Request, userAuth);
//        return null;
//
//    }

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }

}
