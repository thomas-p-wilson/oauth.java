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
package com.thomaspwilson.oauth2.provider.impl.memory.service.memory;

import com.thomaspwilson.oauth2.provider.impl.memory.model.InMemoryAuthorizationCodeImpl;
import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;
import com.thomaspwilson.oauth2.provider.service.CodeService;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCodeServiceImpl implements CodeService {
    
    private static final Map<String, InMemoryAuthorizationCodeImpl> CODES
            = new HashMap<>();

    @Override
    public InMemoryAuthorizationCodeImpl find(final String code) {
        if (CODES.containsKey(code)) {
            return CODES.get(code);
        }
        return null;
    }

    @Override
    public void save(final AuthorizationCode authorization) {
        CODES.put(authorization.getId(), (InMemoryAuthorizationCodeImpl)authorization);
    }

    @Override
    public void delete(final AuthorizationCode authorization) {
        CODES.remove(authorization.getId());
    }
    
}