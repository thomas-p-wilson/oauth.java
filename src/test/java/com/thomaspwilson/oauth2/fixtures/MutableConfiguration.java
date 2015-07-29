/*
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
package com.thomaspwilson.oauth2.fixtures;

import com.thomaspwilson.oauth2.provider.Configuration;
import com.thomaspwilson.oauth2.provider.TokenGranter;
import com.thomaspwilson.oauth2.provider.grant.clientcredentials.ClientCredentialsTokenGranter;
import com.thomaspwilson.oauth2.provider.grant.code.AuthorizationCodeTokenGranter;
import com.thomaspwilson.oauth2.provider.grant.password.ResourceOwnerPasswordTokenGranter;
import com.thomaspwilson.oauth2.provider.grant.refresh.RefreshTokenGranter;
import com.thomaspwilson.oauth2.provider.service.ClientService;
import com.thomaspwilson.oauth2.provider.service.CodeService;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import com.thomaspwilson.oauth2.provider.service.UserService;
import java.util.Arrays;
import java.util.Collection;

public class MutableConfiguration implements Configuration {
    private String realm = "Tests";
    private long defaultAccessTokenLifespan = 20 * 60 * 1000l;
    private long defaultRefreshTokenLifespan = 2 * 60 * 60 * 1000l;
    private boolean permissiveGrantTypes = false;
    private boolean allowRefreshTokens = true;
    private boolean allowRefreshTokenReuse = false;
    private boolean allowClientSecretInBody = false;
    private boolean allowScopeNarrowing = true;
    private TokenService tokenService;
    private CodeService codeService;
    private UserService userService;
    private ClientService clientService;
    private Collection<Class<? extends TokenGranter>> tokenGranters = Arrays.asList(
        (Class<? extends TokenGranter>)ClientCredentialsTokenGranter.class,
        AuthorizationCodeTokenGranter.class,
        ResourceOwnerPasswordTokenGranter.class,
        RefreshTokenGranter.class
    );

    @Override
    public String getRealm() {
        return realm;
    }
    public MutableConfiguration setRealm(final String realm) {
        this.realm = realm;
        return this;
    }

    @Override
    public long getDefaultAccessTokenLifespan() {
        return defaultAccessTokenLifespan;
    }
    public MutableConfiguration setDefaultAccessTokenLifespan(
            final long defaultAccessTokenLifespan) {
        this.defaultAccessTokenLifespan = defaultAccessTokenLifespan;
        return this;
    }

    @Override
    public long getDefaultRefreshTokenLifespan() {
        return defaultRefreshTokenLifespan;
    }
    public MutableConfiguration setDefaultRefreshTokenLifespan(
            final long defaultRefreshTokenLifespan) {
        this.defaultRefreshTokenLifespan = defaultRefreshTokenLifespan;
        return this;
    }

    @Override
    public boolean permissiveGrantTypes() {
        return permissiveGrantTypes;
    }
    public MutableConfiguration setPermissiveGrantTypes(
            final boolean permissiveGrantTypes) {
        this.permissiveGrantTypes = permissiveGrantTypes;
        return this;
    }

    @Override
    public boolean allowRefreshTokens() {
        return allowRefreshTokens;
    }
    public MutableConfiguration setAllowRefreshTokens(
            final boolean allowRefreshTokens) {
        this.allowRefreshTokens = allowRefreshTokens;
        return this;
    }

    @Override
    public boolean allowRefreshTokenReuse() {
        return allowRefreshTokenReuse;
    }
    public MutableConfiguration setAllowRefreshTokenReuse(
            final boolean allowRefreshTokenReuse) {
        this.allowRefreshTokenReuse = allowRefreshTokenReuse;
        return this;
    }

    @Override
    public boolean allowClientSecretInBody() {
        return allowClientSecretInBody;
    }
    public MutableConfiguration setAllowClientSecretInBody(
            final boolean allowClientSecretInBody) {
        this.allowClientSecretInBody = allowClientSecretInBody;
        return this;
    }

    @Override
    public boolean allowScopeNarrowing() {
        return allowScopeNarrowing;
    }
    public MutableConfiguration setAllowScopeNarrowing(
            final boolean allowScopeNarrowing) {
        this.allowScopeNarrowing = allowScopeNarrowing;
        return this;
    }

    @Override
    public TokenService getTokenService() {
        return tokenService;
    }
    public MutableConfiguration setTokenService(
            final TokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    @Override
    public CodeService getCodeService() {
        return codeService;
    }
    public MutableConfiguration setCodeService(final CodeService codeService) {
        this.codeService = codeService;
        return this;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }
    public MutableConfiguration setUserService(final UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    public ClientService getClientService() {
        return clientService;
    }
    public MutableConfiguration setClientService(
            final ClientService clientService) {
        this.clientService = clientService;
        return this;
    }

    @Override
    public Collection<Class<? extends TokenGranter>> getTokenGranters() {
        return tokenGranters;
    }
    public MutableConfiguration setTokenGranters(
            final Collection<Class<? extends TokenGranter>> tokenGranters) {
        this.tokenGranters = tokenGranters;
        return this;
    }
    
}