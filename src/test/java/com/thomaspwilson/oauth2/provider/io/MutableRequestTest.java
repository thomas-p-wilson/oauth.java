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
package com.thomaspwilson.oauth2.provider.io;

import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.io.MutableRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class MutableRequestTest {
    
    @Test
    public void testBasicAuthCredentialDecoding() {
        Map<String, List<String>> headers = new HashMap<String, List<String>>(){{
            put("Authorization", Collections.singletonList("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="));
        }};
        Request request = new MutableRequest(headers, new HashMap<String, List<String>>());
        assertThat(request.getClientId(), is(equalTo("Aladdin")));
        assertThat(request.getClientSecret(), is(equalTo("open sesame")));
    }
    
}
