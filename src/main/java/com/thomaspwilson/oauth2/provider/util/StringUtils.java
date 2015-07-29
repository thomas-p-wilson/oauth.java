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
package com.thomaspwilson.oauth2.provider.util;

import java.util.Iterator;

public class StringUtils {
    
//    public static String[] split(final String str, final String separatorChars) {
//        if (str == null || str.isEmpty()) {
//            return new String[0];
//        }
//        return str.split(separatorChars);
//    }
    
    public static boolean isBlank(final String str) {
        return str == null || str.trim().isEmpty();
    }
    
//    public static String join(final Iterable<?> iterable, final String separator) {
//        if (iterable == null) {
//            return null;
//        }
//        return join(iterable.iterator(), separator);
//    }
//    public static String join(final Iterator<?> iterator, final String separator) {
//        if (iterator == null) {
//            return null;
//        }
//        if (!iterator.hasNext()) {
//            return "";
//        }
//        final Object first = iterator.next();
//        if (!iterator.hasNext()) {
//            return first == null ? "" : first.toString();
//        }
//
//        final StringBuilder buf = new StringBuilder(256);
//        if (first != null) {
//            buf.append(first);
//        }
//
//        while (iterator.hasNext()) {
//            if (separator != null) {
//                buf.append(separator);
//            }
//            final Object obj = iterator.next();
//            if (obj != null) {
//                buf.append(obj);
//            }
//        }
//        return buf.toString();
//    }
    
}