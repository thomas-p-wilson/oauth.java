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
package com.thomaspwilson.oauth2.provider.util;

import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import java.util.List;
import java.util.Map;

public class MapUtils {
    public static String getOnlyOrEmpty(final Map<String, List<String>> map,
            final String name) {
        String result = getOnlyOrNull(map, name);
        if (result == null) {
            return "";
        }
        return result;
    }
    public static String getOnlyOrNull(final Map<String, List<String>> map,
            final String name) {
        if (!map.containsKey(name)) {
            return null;
        }
        
        List<String> values = map.get(name);
        if (values.isEmpty()) {
            return null;
        }
        if (values.size() > 1) {
            throw new InvalidRequestException("Duplicated parameter "+name);
        }
        return values.get(0);
    }
}