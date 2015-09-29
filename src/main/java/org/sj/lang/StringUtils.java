/**
 * Copyright Sangram Jadhav. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sj.lang;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;
import java.nio.charset.Charset;

public final class StringUtils {

    /**
     * Checks if string is empty or null.
     */
    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Checks if string is not empty or not null. 
     */
    public static boolean isNotEmpty(final String s) {
        return s != null && s.length() > 0;
    }

    /**
     * Checks if a String is whitespace, empty ("") or null.
     */
    public static boolean isBlank(final String s) {
        int len;
        if (s == null || (len = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(s.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a String is not empty (""), not null and not whitespace only.
     */
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }


    /**
     * Get bytes of string. This method is NULL safe. It will return null if null argument provided.
     */
    public static byte[] getBytes(String s, String charsetName) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convinient method to get UTF8 bytes
     */
    public static byte[] getUtf8Bytes(String s) {
        return s.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String,
     * handling {@code null} by returning {@code null}.
     * </p>
     *
     * <p>
     * Trim removes start and end characters &lt;= 32.
     * </p>
     *
     */
    public static String trim(final String s) {
        return s == null ? null : s.trim();
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String
     * returning {@code null} if the String is empty ("") after the trim or if
     * it is {@code null}.
     *
     * <p>
     * Trim removes start and end characters &lt;= 32.
     * </p>
     *
     */
    public static String trimToNull(final String s) {
        final String ts = trim(s);
        return ts == null || ts.length() == 0 ? null : ts;
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String
     * returning an empty String ("") if the String is empty ("") after the trim
     * or if it is {@code null}.
     *
     * <p>
     * Trim removes start and
     * end characters &lt;= 32.
     * </p>
     *
     */
    public static String trimToEmpty(final String s) {
        return s == null ? "" : s.trim();
    }

    public static void trimAll(String[] ss) {
        for (int i = 0; i < ss.length; i++) {
            String string = ss[i];
            if (string != null) ss[i] = string.trim();
        }
    }

    /**
     * <p>
     * Returns either the passed in String, or if the String is {@code null},
     * the value of {@code defaultStr}.
     * </p>
     *
     */
    public static String defaultIfNull(final String s, final String defaultStr) {
        return s == null ? defaultStr : s;
    }

    /**
     * <p>
     * Returns either the passed in String, or if the String is
     * empty or {@code null}, the value of {@code defaultStr}.
     * </p>
     *
     */
    public static String defaultIfEmpty(final String s, final String defaultStr) {
        return isEmpty(s) ? defaultStr : s;
    }

    /**
     * <p>
     * Returns either the passed in String, or if the String is
     * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.
     * </p>
     *
     */
    public static String defaultIfBlank(final String s, final String defaultStr) {
        return isBlank(s) ? defaultStr : s;
    }

    /**
     * <p>
     * Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered to be equal. The comparison is case sensitive.
     * </p>
     *
     */
    public static boolean equals(final String s1, final String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * <p>
     * Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered equal. Comparison is case insensitive.
     * </p>
     *
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        if (s1 == null || s2 == null) {
            return s1 == s2;
        } else if (s1 == s2) {
            return true;
        } else if (s1.length() != s2.length()) {
            return false;
        } else {
            return s1.equalsIgnoreCase(s2);
        }
    }

    /**
     * <p>
     * Check if a String starts with a specified prefix.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered to be equal. The comparison is case sensitive.
     * </p>
     *
     * @param s
     *            the String to check, may be null
     * @param prefix
     *            the prefix to find, may be null
     * @return {@code true} if the String starts with the prefix, case
     *         sensitive, or both {@code null}
     */
    public static boolean startsWith(final String s, final String prefix) {
        return startsWith(s, prefix, false);
    }

    /**
     * <p>
     * Case insensitive check if a String starts with a specified prefix.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered to be equal. The comparison is case insensitive.
     * </p>
     *
     *
     * @see java.lang.String#startsWith(String)
     */
    public static boolean startsWithIgnoreCase(final String s, final String prefix) {
        return startsWith(s, prefix, true);
    }

    /**
     * <p>
     * Check if a String starts with a specified prefix (optionally case
     * insensitive).
     * </p>
     *
     */
    private static boolean startsWith(final String s, final String prefix, final boolean ignoreCase) {
        if (s == null || prefix == null) {
            return s == null && prefix == null;
        }
        if (prefix.length() > s.length()) {
            return false;
        }
        return s.toString().regionMatches(ignoreCase, 0, prefix.toString(), 0, prefix.length());
    }

    public static int startsWithOne(String s, String... dest) {
        for (int i = 0; i < dest.length; i++) {
            String m = dest[i];
            if (m != null) {
                if (s.startsWith(m)) return i;
            }
        }
        return -1;
    }

    public static int startsWithOneIgnoreCase(String s, String... dest) {
        for (int i = 0; i < dest.length; i++) {
            String m = dest[i];
            if (m != null) {
                if (startsWithIgnoreCase(s, m)) return i;
            }
        }
        return -1;
    }

    public static boolean startsWithChar(final String s, final char c) {
        if (s == null || s.length() == 0) {
            return false;
        }
        return s.charAt(0) == c;
    }

    /**
     * <p>
     * Check if a String ends with a specified suffix.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered to be equal. The comparison is case sensitive.
     * </p>
     *
     */
    public static boolean endsWith(final String s, final String suffix) {
        return endsWith(s, suffix, false);
    }

    /**
     * <p>
     * Case insensitive check if a String ends with a specified suffix.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered to be equal. The comparison is case insensitive.
     * </p>
     *
     */
    public static boolean endsWithIgnoreCase(final String s, final String suffix) {
        return endsWith(s, suffix, true);
    }

    /**
     * <p>
     * Check if a String ends with a specified suffix (optionally case
     * insensitive).
     * </p>
     */
    private static boolean endsWith(final String s, final String suffix, final boolean ignoreCase) {
        if (s == null || suffix == null) {
            return s == null && suffix == null;
        }
        if (suffix.length() > s.length()) {
            return false;
        }
        final int strOffset = s.length() - suffix.length();
        return s.toString().regionMatches(ignoreCase, strOffset, suffix.toString(), 0, suffix.length());
    }

    public static int endsWithOne(String src, String... dest) {
        for (int i = 0; i < dest.length; i++) {
            String m = dest[i];
            if (m != null) {
                if (src.endsWith(m)) return i;
            }
        }
        return -1;
    }

    public static int endsWithOneIgnoreCase(String s, String... dest) {
        for (int i = 0; i < dest.length; i++) {
            String m = dest[i];
            if (m != null) {
                if (endsWithIgnoreCase(s, m)) return i;
            }
        }
        return -1;
    }

    public static boolean endsWithChar(final String s, final char c) {
        if (s == null || s.length() == 0) {
            return false;
        }
        return s.charAt(s.length() - 1) == c;
    }

    /**
     * Finds first occurrence of a substring in the given source but within limited range [start, end).
     * It is fastest possible code, but still original <code>String.indexOf(String, int)</code>
     * is much faster (since it uses char[] value directly) and should be used when no range is needed.
     *
     * @param s                source string for examination
     * @param substr                substring to find
     * @param startIndex        starting index
     * @param endIndex                ending index
     * @return index of founded substring or -1 if substring not found
     */
    public static int indexOf(String s, String substr, int startIndex, int endIndex) {
        if (startIndex < 0) {
            startIndex = 0;
        }
        int srclen = s.length();
        if (endIndex > srclen) {
            endIndex = srclen;
        }
        int sublen = substr.length();
        if (sublen == 0) {
            return startIndex > srclen ? srclen : startIndex;
        }

        int total = endIndex - sublen + 1;
        char c = substr.charAt(0);
        mainloop: for (int i = startIndex; i < total; i++) {
            if (s.charAt(i) != c) {
                continue;
            }
            int j = 1;
            int k = i + 1;
            while (j < sublen) {
                if (substr.charAt(j) != s.charAt(k)) {
                    continue mainloop;
                }
                j++;
                k++;
            }
            return i;
        }
        return -1;
    }

    /**
     * Finds the first occurrence of a character in the given source but within limited range (start, end].
     */
    public static int indexOf(String s, char c, int startIndex, int endIndex) {
        if (startIndex < 0) {
            startIndex = 0;
        }
        int srclen = s.length();
        if (endIndex > srclen) {
            endIndex = srclen;
        }
        for (int i = startIndex; i < endIndex; i++) {
            if (s.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the first occurrence of a character in the given source but within limited range (start, end].
     */
    public static int indexOfIgnoreCase(String s, char c, int startIndex, int endIndex) {
        if (startIndex < 0) {
            startIndex = 0;
        }
        int srclen = s.length();
        if (endIndex > srclen) {
            endIndex = srclen;
        }
        c = Character.toLowerCase(c);
        for (int i = startIndex; i < endIndex; i++) {
            if (Character.toLowerCase(s.charAt(i)) == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds first index of a substring in the given source string with ignored case.
     *
     * @param s    source string for examination
     * @param substr   substring to find
     *
     * @return index of founded substring or -1 if substring is not found
     */
    public static int indexOfIgnoreCase(String s, String substr) {
        return indexOfIgnoreCase(s, substr, 0, s.length());
    }

    /**
     * Finds first index of a substring in the given source string with ignored
     * case. This seems to be the fastest way doing this, with common string
     * length and content (of course, with no use of Boyer-Mayer type of
     * algorithms). Other implementations are slower: getting char array first,
     * lower casing the source string, using String.regionMatch etc.
     *
     * @param s        source string for examination
     * @param substr       substring to find
     * @param startIndex starting index from where search begins
     *
     * @return index of founded substring or -1 if substring is not found
     */
    public static int indexOfIgnoreCase(String s, String substr, int startIndex) {
        return indexOfIgnoreCase(s, substr, startIndex, s.length());
    }

    /**
     * Finds first index of a substring in the given source string and range with
     * ignored case.
     *
     * @param s                source string for examination
     * @param substr                substring to find
     * @param startIndex        starting index from where search begins
     * @param endIndex                endint index
     * @return index of founded substring or -1 if substring is not found
     * @see #indexOfIgnoreCase(String, String, int)
     */
    public static int indexOfIgnoreCase(String s, String substr, int startIndex, int endIndex) {
        if (startIndex < 0) {
            startIndex = 0;
        }
        int srclen = s.length();
        if (endIndex > srclen) {
            endIndex = srclen;
        }

        int sublen = substr.length();
        if (sublen == 0) {
            return startIndex > srclen ? srclen : startIndex;
        }
        substr = substr.toLowerCase();
        int total = endIndex - sublen + 1;
        char c = substr.charAt(0);
        mainloop: for (int i = startIndex; i < total; i++) {
            if (Character.toLowerCase(s.charAt(i)) != c) {
                continue;
            }
            int j = 1;
            int k = i + 1;
            while (j < sublen) {
                char source = Character.toLowerCase(s.charAt(k));
                if (substr.charAt(j) != source) {
                    continue mainloop;
                }
                j++;
                k++;
            }
            return i;
        }
        return -1;
    }

    /**
     * Finds the very first index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code> if
     * noting found.
     *
     * @param s      source string
     * @param arr    string array
     */
    public static int[] indexOf(String s, String arr[]) {
        return indexOf(s, arr, 0);
    }

    /**
     * Finds the very first index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param arr    string array
     * @param start  starting position
     */
    public static int[] indexOf(String s, String arr[], int start) {
        int arrLen = arr.length;
        int index = Integer.MAX_VALUE;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = s.indexOf(arr[j], start);
            if (i != -1) {
                if (i < index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * Finds the very first index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code> if
     * noting found.
     *
     * @param s      source string
     * @param c      char array
     */
    public static int[] indexOf(String s, char c[]) {
        return indexOf(s, c, 0);
    }

    /**
     * Finds the very first index of a char from the specified array. It
     * returns an int[2] where int[0] represents the char index and int[1]
     * represents position where char was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param c      char array
     * @param start  starting position
     */
    public static int[] indexOf(String s, char c[], int start) {
        int arrLen = c.length;
        int index = Integer.MAX_VALUE;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = s.indexOf(c[j], start);
            if (i != -1) {
                if (i < index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * Finds the very first index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param arr    string array
     */
    public static int[] indexOfIgnoreCase(String s, String arr[]) {
        return indexOfIgnoreCase(s, arr, 0);
    }

    /**
     * Finds the very first index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param arr    string array
     * @param start  starting position
     */
    public static int[] indexOfIgnoreCase(String s, String arr[], int start) {
        int arrLen = arr.length;
        int index = Integer.MAX_VALUE;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = indexOfIgnoreCase(s, arr[j], start);
            if (i != -1) {
                if (i < index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * Finds last index of a substring in the given source string with ignored
     * case.
     *
     * @param s      source string
     * @param substr   substring to find
     *
     * @return last index of founded substring or -1 if substring is not found
     * @see #indexOfIgnoreCase(String, String, int)
     * @see #lastIndexOfIgnoreCase(String, String, int)
     */
    public static int lastIndexOfIgnoreCase(String s, String substr) {
        return lastIndexOfIgnoreCase(s, substr, s.length(), 0);
    }

    /**
     * Finds last index of a substring in the given source string with ignored
     * case.
     *
     * @param s        source string for examination
     * @param substr       substring to find
     * @param startIndex starting index from where search begins
     *
     * @return last index of founded substring or -1 if substring is not found
     * @see #indexOfIgnoreCase(String, String, int)
     */
    public static int lastIndexOfIgnoreCase(String s, String substr, int startIndex) {
        return lastIndexOfIgnoreCase(s, substr, startIndex, 0);
    }

    /**
     * Finds last index of a substring in the given source string with ignored
     * case in specified range.
     *
     * @param s                source to examine
     * @param sub                substring to find
     * @param startIndex        starting index
     * @param endIndex                end index
     * @return last index of founded substring or -1 if substring is not found
     */
    public static int lastIndexOfIgnoreCase(String s, String sub, int startIndex, int endIndex) {
        int sublen = sub.length();
        int srclen = s.length();
        if (sublen == 0) {
            return startIndex > srclen ? srclen : (startIndex < -1 ? -1 : startIndex);
        }
        sub = sub.toLowerCase();
        int total = srclen - sublen;
        if (total < 0) {
            return -1;
        }
        if (startIndex >= total) {
            startIndex = total;
        }
        if (endIndex < 0) {
            endIndex = 0;
        }
        char c = sub.charAt(0);
        mainloop: for (int i = startIndex; i >= endIndex; i--) {
            if (Character.toLowerCase(s.charAt(i)) != c) {
                continue;
            }
            int j = 1;
            int k = i + 1;
            while (j < sublen) {
                char source = Character.toLowerCase(s.charAt(k));
                if (sub.charAt(j) != source) {
                    continue mainloop;
                }
                j++;
                k++;
            }
            return i;
        }
        return -1;
    }

    /**
     * Finds last index of a substring in the given source string in specified range [end, start]
     * See {@link #indexOf(String, String, int, int)}  for details about the speed.
     *
     * @param s                source to examine
     * @param sub                substring to find
     * @param startIndex        starting index
     * @param endIndex                end index
     * @return last index of founded substring or -1 if substring is not found
     */
    public static int lastIndexOf(String s, String sub, int startIndex, int endIndex) {
        int sublen = sub.length();
        int srclen = s.length();
        if (sublen == 0) {
            return startIndex > srclen ? srclen : (startIndex < -1 ? -1 : startIndex);
        }
        int total = srclen - sublen;
        if (total < 0) {
            return -1;
        }
        if (startIndex >= total) {
            startIndex = total;
        }
        if (endIndex < 0) {
            endIndex = 0;
        }
        char c = sub.charAt(0);
        mainloop: for (int i = startIndex; i >= endIndex; i--) {
            if (s.charAt(i) != c) {
                continue;
            }
            int j = 1;
            int k = i + 1;
            while (j < sublen) {
                if (sub.charAt(j) != s.charAt(k)) {
                    continue mainloop;
                }
                j++;
                k++;
            }
            return i;
        }
        return -1;
    }

    /**
     * Finds last index of a character in the given source string in specified range [end, start]
     */
    public static int lastIndexOf(String s, char c, int startIndex, int endIndex) {
        int total = s.length() - 1;
        if (total < 0) {
            return -1;
        }
        if (startIndex >= total) {
            startIndex = total;
        }
        if (endIndex < 0) {
            endIndex = 0;
        }
        for (int i = startIndex; i >= endIndex; i--) {
            if (s.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds last index of a character in the given source string in specified range [end, start]
     */
    public static int lastIndexOfIgnoreCase(String s, char c, int startIndex, int endIndex) {
        int total = s.length() - 1;
        if (total < 0) {
            return -1;
        }
        if (startIndex >= total) {
            startIndex = total;
        }
        if (endIndex < 0) {
            endIndex = 0;
        }
        c = Character.toLowerCase(c);
        for (int i = startIndex; i >= endIndex; i--) {
            if (Character.toLowerCase(s.charAt(i)) == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param arr    string array
     */
    public static int[] lastIndexOf(String s, String arr[]) {
        return lastIndexOf(s, arr, s.length());
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s         source string
     * @param arr       string array
     * @param fromIndex starting position
     */
    public static int[] lastIndexOf(String s, String arr[], int fromIndex) {
        int arrLen = arr.length;
        int index = -1;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = s.lastIndexOf(arr[j], fromIndex);
            if (i != -1) {
                if (i > index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param c      char array
     */
    public static int[] lastIndexOf(String s, char c[]) {
        return lastIndexOf(s, c, s.length());
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s         source string
     * @param c         char array
     * @param fromIndex starting position
     */
    public static int[] lastIndexOf(String s, char c[], int fromIndex) {
        int arrLen = c.length;
        int index = -1;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = s.lastIndexOf(c[j], fromIndex);
            if (i != -1) {
                if (i > index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s      source string
     * @param arr    string array
     *
     * @return int[2]
     */
    public static int[] lastIndexOfIgnoreCase(String s, String arr[]) {
        return lastIndexOfIgnoreCase(s, arr, s.length());
    }

    /**
     * Finds the very last index of a substring from the specified array. It
     * returns an int[2] where int[0] represents the substring index and int[1]
     * represents position where substring was found. Returns <code>null</code>
     * if noting found.
     *
     * @param s         source string
     * @param arr       string array
     * @param fromIndex starting position
     */
    public static int[] lastIndexOfIgnoreCase(String s, String arr[], int fromIndex) {
        int arrLen = arr.length;
        int index = -1;
        int last = -1;
        for (int j = 0; j < arrLen; j++) {
            int i = lastIndexOfIgnoreCase(s, arr[j], fromIndex);
            if (i != -1) {
                if (i > index) {
                    index = i;
                    last = j;
                }
            }
        }
        return last == -1 ? null : new int[] { last, index };
    }

    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * A negative start position can be used to start {@code n} characters from
     * the end of the String.
     * </p>
     *
     * <p>
     * A {@code null} String will return {@code null}. An empty ("") String will
     * return "".
     * </p>
     *
     */
    public static String substring(final String s, int start) {
        if (s == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = s.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > s.length()) {
            return "";
        }

        return s.substring(start);
    }

    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * A negative start position can be used to start/end {@code n} characters
     * from the end of the String.
     * </p>
     *
     * <p>
     * The returned substring starts with the character in the {@code start}
     * position and ends before the {@code end} position. All position counting
     * is zero-based -- i.e., to start at the beginning of the string use
     * {@code start = 0}. Negative start and end positions can be used to
     * specify offsets relative to the end of the String.
     * </p>
     *
     * <p>
     * If {@code start} is not strictly to the left of {@code end}, "" is
     * returned.
     * </p>
     *
     */
    public static String substring(final String s, int start, int end) {
        if (s == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = s.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = s.length() + start; // remember start is negative
        }

        // check length next
        if (end > s.length()) {
            end = s.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return s.substring(start, end);
    }

    /**
     * <p>
     * Gets the substring before the first occurrence of a separator. The
     * separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("")
     * string input will return the empty string. A {@code null} separator will
     * return the input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     */
    public static String substringBefore(final String s, final String separator) {
        if (isEmpty(s) || separator == null) {
            return s;
        }
        if (separator.isEmpty()) {
            return "";
        }
        final int pos = s.indexOf(separator);
        if (pos < 0) {
            return s;
        }
        return s.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the first occurrence of a separator. The
     * separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("")
     * string input will return the empty string. A {@code null} separator will
     * return the empty string if the input string is not {@code null}.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     */
    public static String substringAfter(final String s, final String separator) {
        if (isEmpty(s)) {
            return s;
        }
        if (separator == null) {
            return "";
        }
        final int pos = s.indexOf(separator);
        if (pos < 0) {
            return "";
        }
        return s.substring(pos + separator.length());
    }

    /**
     * <p>
     * Gets the substring before the last occurrence of a separator. The
     * separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("")
     * string input will return the empty string. An empty or {@code null}
     * separator will return the input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     */
    public static String substringBeforeLast(final String s, final String separator) {
        if (isEmpty(s) || isEmpty(separator)) {
            return s;
        }
        final int pos = s.lastIndexOf(separator);
        if (pos < 0) {
            return s;
        }
        return s.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the last occurrence of a separator. The
     * separator is not returned.
     * </p>
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("")
     * string input will return the empty string. An empty or {@code null}
     * separator will return the empty string if the input string is not
     * {@code null}.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     */
    public static String substringAfterLast(final String s, final String separator) {
        if (isEmpty(s)) {
            return s;
        }
        if (isEmpty(separator)) {
            return "";
        }
        final int pos = s.lastIndexOf(separator);
        if (pos < 0 || pos == s.length() - separator.length()) {
            return "";
        }
        return s.substring(pos + separator.length());
    }

    /**
     * <p>
     * Gets the String that is nested in between two instances of the same
     * String.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}. A {@code null} tag
     * returns {@code null}.
     * </p>
     *
     */
    public static String substringBetween(final String s, final String tag) {
        return substringBetween(s, tag, tag);
    }

    /**
     * <p>
     * Gets the String that is nested in between two Strings. Only the first
     * match is returned.
     * </p>
     *
     * <p>
     * A {@code null} input String returns {@code null}. A {@code null}
     * open/close returns {@code null} (no match). An empty ("") open and close
     * returns an empty string.
     * </p>
     *
     */
    public static String substringBetween(final String s, final String open, final String close) {
        if (s == null || open == null || close == null) {
            return null;
        }
        final int start = s.indexOf(open);
        if (start >= 0) {
            final int end = s.indexOf(close, start + open.length());
            if (end >= 0) {
                return s.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * <p>
     * Gets the leftmost {@code len} characters of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, or the String is
     * {@code null}, the String will be returned without an exception. An empty
     * String is returned if len is negative.
     * </p>
     *
     */
    public static String left(final String s, final int len) {
        if (s == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (s.length() <= len) {
            return s;
        }
        return s.substring(0, len);
    }

    /**
     * <p>
     * Gets the rightmost {@code len} characters of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, or the String is
     * {@code null}, the String will be returned without an an exception. An
     * empty String is returned if len is negative.
     * </p>
     *
     */
    public static String right(final String s, final int len) {
        if (s == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (s.length() <= len) {
            return s;
        }
        return s.substring(s.length() - len);
    }

    /**
     * <p>
     * Gets {@code len} characters from the middle of a String.
     * </p>
     *
     * <p>
     * If {@code len} characters are not available, the remainder of the String
     * will be returned without an exception. If the String is {@code null},
     * {@code null} will be returned. An empty String is returned if len is
     * negative or exceeds the length of {@code str}.
     * </p>
     *
     */
    public static String mid(final String s, int pos, final int len) {
        if (s == null) {
            return null;
        }
        if (len < 0 || pos > s.length()) {
            return "";
        }
        if (pos < 0) {
            pos = 0;
        }
        if (s.length() <= pos + len) {
            return s.substring(pos);
        }
        return s.substring(pos, pos + len);
    }

    /**
     * <p>
     * Repeat a String {@code repeat} times to form a new String.
     * </p>
     *
     */
    public static String repeat(final String s, final int repeat) {
        if (s == null) {
            return null;
        }
        if (repeat <= 0) {
            return "";
        }
        final int inputLength = s.length();
        if (repeat == 1 || inputLength == 0) {
            return s;
        }

        final int outputLength = inputLength * repeat;
        switch (inputLength) {
        case 1:
            return repeat(String.valueOf(s.charAt(0)), repeat);
        case 2:
            final char ch0 = s.charAt(0);
            final char ch1 = s.charAt(1);
            final char[] output2 = new char[outputLength];
            for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                output2[i] = ch0;
                output2[i + 1] = ch1;
            }
            return new String(output2);
        default:
            final StringBuilder buf = new StringBuilder(outputLength);
            for (int i = 0; i < repeat; i++) {
                buf.append(s);
            }
            return buf.toString();
        }
    }

    /**
     * <p>
     * Repeat a String {@code repeat} times to form a new String, with a String
     * separator injected each time.
     * </p>
     *
     */
    public static String repeat(final String s, final String separator, final int repeat) {
        if (s == null || separator == null) {
            return repeat(s, repeat);
        }
        // given that repeat(String, int) is quite optimized, better to rely on
        // it than try and splice this into it
        final String result = repeat(s + separator, repeat);
        return removeEnd(result, separator);
    }


    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(String.valueOf(padChar), pads).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        if ((padLen == 1) && (pads <= 8192)) {
            return leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[(i % padLen)];
        }
        return new String(padding).concat(str);
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(repeat(String.valueOf(padChar), pads));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        if ((padLen == 1) && (pads <= 8192)) {
            return rightPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[(i % padLen)];
        }
        return str.concat(new String(padding));
    }

    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar) {
        if ((str == null) || (size <= 0)) {
            return str;
        }
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        str = leftPad(str, strLen + pads / 2, padChar);
        str = rightPad(str, size, padChar);
        return str;
    }

    public static String reverse(String s) {
        StringBuilder result = new StringBuilder(s.length());
        for (int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }
        return result.toString();
    }

    /**
     * <p>
     * Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.
     * </p>
     *
     */
    public static String deleteWhitespace(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        final int sz = s.length();
        final char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                chs[count++] = s.charAt(i);
            }
        }
        if (count == sz) {
            return s;
        }
        return new String(chs, 0, count);
    }

    /**
     * <p>
     * Removes a substring only if it is at the beginning of a source string,
     * otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} search string
     * will return the source string.
     * </p>
     *
     */
    public static String removeStart(final String s, final String remove) {
        if (isEmpty(s) || isEmpty(remove)) {
            return s;
        }
        if (s.startsWith(remove)) {
            return s.substring(remove.length());
        }
        return s;
    }

    /**
     * <p>
     * Case insensitive removal of a substring if it is at the beginning of a
     * source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} search string
     * will return the source string.
     * </p>
     *
     */
    public static String removeStartIgnoreCase(final String s, final String remove) {
        if (isEmpty(s) || isEmpty(remove)) {
            return s;
        }
        if (startsWithIgnoreCase(s, remove)) {
            return s.substring(remove.length());
        }
        return s;
    }

    /**
     * <p>
     * Removes a substring only if it is at the end of a source string,
     * otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} search string
     * will return the source string.
     * </p>
     */
    public static String removeEnd(final String s, final String remove) {
        if (isEmpty(s) || isEmpty(remove)) {
            return s;
        }
        if (s.endsWith(remove)) {
            return s.substring(0, s.length() - remove.length());
        }
        return s;
    }

    /**
     * <p>
     * Case insensitive removal of a substring if it is at the end of a source
     * string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} search string
     * will return the source string.
     * </p>
     *
     */
    public static String removeEndIgnoreCase(final String s, final String remove) {
        if (isEmpty(s) || isEmpty(remove)) {
            return s;
        }
        if (endsWithIgnoreCase(s, remove)) {
            return s.substring(0, s.length() - remove.length());
        }
        return s;
    }

    /**
     * <p>
     * Removes all occurrences of a substring from within the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} remove string
     * will return the source string. An empty ("") remove string will return
     * the source string.
     * </p>
     *
     */
    public static String remove(final String s, final String remove) {
        if (isEmpty(s) || isEmpty(remove)) {
            return s;
        }
        return replace(s, remove, "", -1);
    }

    /**
     * <p>
     * Removes all occurrences of a character from within the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string.
     * </p>
     *
     */
    public static String remove(final String s, final char remove) {
        if (isEmpty(s) || s.indexOf(remove) < 0) {
            return s;
        }
        final char[] chars = s.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * Removes all characters contained in provided string.
     *
     */
    public static String removeChars(String s, String chars) {
        int i = s.length();
        StringBuilder sb = new StringBuilder(i);
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if (chars.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Removes set of characters from string.
     *
     */
    public static String removeChars(String s, char... chars) {
        int i = s.length();
        StringBuilder sb = new StringBuilder(i);
        mainloop: for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            for (char aChar : chars) {
                if (c == aChar) {
                    continue mainloop;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String prefix(String s, String prefix) {
        if (!s.startsWith(prefix)) {
            s = prefix + s;
        }
        return s;
    }

    public static String suffix(String s, String suffix) {
        if (!s.endsWith(suffix)) {
            s = s + suffix;
        }
        return s;
    }

    /**
     * <p>
     * Replaces a String with another String inside a larger String, once.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     */
    public static String replaceOnce(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    /**
     * Replaces each substring of the source String that matches the given
     * regular expression with the given replacement using the
     * {@link Pattern#DOTALL} option. DOTALL is also know as single-line mode in
     * Perl. This call is also equivalent to:
     * <ul>
     * <li>{@code source.replaceAll(&quot;(?s)&quot; + regex, replacement)}</li>
     * <li>
     * {@code Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(replacement)}
     * </li>
     * </ul>
     *
     * @see String#replaceAll(String, String)
     * @see Pattern#DOTALL
     */
    public static String replacePattern(final String source, final String regex, final String replacement) {
        return Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(replacement);
    }

    /**
     * <p>
     * Replaces all occurrences of a String within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     */
    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    /**
     * <p>
     * Replaces a String with another String inside a larger String, for the
     * first {@code max} values of the search String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op.
     * </p>
     *
     * </pre>
     */
    public static String replace(final String text, final String searchString, final String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end >= 0) {
            return text;
        }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end >= 0) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any
     * "search string" or "string to replace" is null, that replace will be
     * ignored. This will not repeat. For repeating replaces, call the
     * overloaded method.
     * </p>
     *
     */
    public static String replaceEach(final String text, final String[] searchList, final String[] replacementList) {
        return replaceEach(text, searchList, replacementList, false, 0);
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any
     * "search string" or "string to replace" is null, that replace will be
     * ignored.
     * </p>
     *
     */
    public static String replaceEachRepeatedly(final String text, final String[] searchList, final String[] replacementList) {
        final int listLength = searchList == null ? 0 : searchList.length;
        return replaceEach(text, searchList, replacementList, true, listLength);
    }

    /**
     * <p>
     * Replaces all occurrences of Strings within another String.
     * </p>
     *
     * <p>
     * A {@code null} reference passed to this method is a no-op, or if any
     * "search string" or "string to replace" is null, that replace will be
     * ignored.
     * </p>
     *
     */
    private static String replaceEach(final String text, final String[] searchList, final String[] replacementList, final boolean repeat, final int listLength) {
        if (text == null || text.isEmpty() || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0) {
            return text;
        }

        // if recursing, this shouldn't be less than 0
        if (listLength < 0) {
            throw new IllegalStateException("Aborting to protect against StackOverflowError - " + "output of one loop is the input of another");
        }

        final int searchLength = searchList.length;
        final int replacementLength = replacementList.length;

        if (searchLength != replacementLength) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
        }

        final boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

        int textIndex = -1;
        int replaceIndex = -1;
        int tempIndex = -1;

        for (int i = 0; i < searchLength; i++) {
            if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].isEmpty() || replacementList[i] == null) {
                continue;
            }
            tempIndex = text.indexOf(searchList[i]);

            if (tempIndex == -1) {
                noMoreMatchesForReplIndex[i] = true;
            } else {
                if (textIndex == -1 || tempIndex < textIndex) {
                    textIndex = tempIndex;
                    replaceIndex = i;
                }
            }
        }

        if (textIndex == -1) {
            return text;
        }

        int start = 0;


        int increase = 0;

        for (int i = 0; i < searchList.length; i++) {
            if (searchList[i] == null || replacementList[i] == null) {
                continue;
            }
            final int greater = replacementList[i].length() - searchList[i].length();
            if (greater > 0) {
                increase += 3 * greater;
            }
        }
        increase = Math.min(increase, text.length() / 5);

        final StringBuilder buf = new StringBuilder(text.length() + increase);

        while (textIndex != -1) {

            for (int i = start; i < textIndex; i++) {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);

            start = textIndex + searchList[replaceIndex].length();

            textIndex = -1;
            replaceIndex = -1;
            tempIndex = -1;

            for (int i = 0; i < searchLength; i++) {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].isEmpty() || replacementList[i] == null) {
                    continue;
                }
                tempIndex = text.indexOf(searchList[i], start);

                if (tempIndex == -1) {
                    noMoreMatchesForReplIndex[i] = true;
                } else {
                    if (textIndex == -1 || tempIndex < textIndex) {
                        textIndex = tempIndex;
                        replaceIndex = i;
                    }
                }
            }
        }
        final int textLength = text.length();
        for (int i = start; i < textLength; i++) {
            buf.append(text.charAt(i));
        }
        final String result = buf.toString();
        if (!repeat) {
            return result;
        }

        return replaceEach(result, searchList, replacementList, repeat, listLength - 1);
    }

    /**
     * <p>
     * Replaces all occurrences of a character in a String with another. This is
     * a null-safe version of {@link String#replace(char, char)}.
     * </p>
     *
     * <p>
     * A {@code null} string input returns {@code null}. An empty ("") string
     * input returns an empty string.
     * </p>
     *
     */
    public static String replaceChars(final String str, final char searchChar, final char replaceChar) {
        if (str == null) {
            return null;
        }
        return str.replace(searchChar, replaceChar);
    }

    /**
     * <p>
     * Replaces multiple characters in a String in one go. This method can also
     * be used to delete characters.
     * </p>
     *
     * <p>
     * For example:<br>
     * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
     * .
     * </p>
     *
     * <p>
     * A {@code null} string input returns {@code null}. An empty ("") string
     * input returns an empty string. A null or empty set of search characters
     * returns the input string.
     * </p>
     *
     * <p>
     * The length of the search characters should normally equal the length of
     * the replace characters. If the search characters is longer, then the
     * extra search characters are deleted. If the search characters is shorter,
     * then the extra replace characters are ignored.
     * </p>
     *
     */
    public static String replaceChars(final String str, final String searchChars, String replaceChars) {
        if (isEmpty(str) || isEmpty(searchChars)) {
            return str;
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        boolean modified = false;
        final int replaceCharsLength = replaceChars.length();
        final int strLength = str.length();
        final StringBuilder buf = new StringBuilder(strLength);
        for (int i = 0; i < strLength; i++) {
            final char ch = str.charAt(i);
            final int index = searchChars.indexOf(ch);
            if (index >= 0) {
                modified = true;
                if (index < replaceCharsLength) {
                    buf.append(replaceChars.charAt(index));
                }
            } else {
                buf.append(ch);
            }
        }
        if (modified) {
            return buf.toString();
        }
        return str;
    }

    public static String[] split(String str, String delimiter) {
        if (str == null) return null;
        List<String> results = new ArrayList<String>();

        int ipos = 0, lastpos = 0;
        while ((ipos = str.indexOf(delimiter, lastpos)) != -1) {
            results.add(str.substring(lastpos, ipos));
            lastpos = ipos + delimiter.length();
        }
        results.add(str.substring(lastpos));
        return results.toArray(new String[results.size()]);
    }

    public static String[] split(String str, char delimiter) {
        if (str == null) return null;
        List<String> results = new ArrayList<String>();

        int ipos = 0, lastpos = 0;
        while ((ipos = str.indexOf(delimiter, lastpos)) != -1) {
            results.add(str.substring(lastpos, ipos));
            lastpos = ipos + 1;
        }
        results.add(str.substring(lastpos));
        return results.toArray(new String[results.size()]);
    }

    public static String[] splitChars(String str, String delimiters) {
        if (str == null) return null;
        List<String> results = new ArrayList<String>();

        int lastpos = 0;
        for (int i = 0, len = str.length(); i < len; i++) {
            char c = str.charAt(i);
            if (delimiters.indexOf(c) != -1) {
                results.add(str.substring(lastpos, i));
                lastpos = i + 1;
            }
        }
        results.add(str.substring(lastpos));
        return results.toArray(new String[results.size()]);
    }

    public static String[] splitCSV(String str) {
        if (str == null) return null;
        String[] parts = StringUtils.split(str, ',');

        List<String> results = new ArrayList<String>();
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            if (s.length() == 0) {
                results.add(s);
            } else {
                char c = s.charAt(0);
                if (c == '"' || c == '\'' || c == '`') {
                    StringBuilder sb = new StringBuilder();
                    sb.append(s);
                    while (i + 1 < parts.length) {
                        if (sb.length() > 1 && s.length() > 0 && s.charAt(s.length() - 1) == c) {
                            break;
                        }
                        s = parts[++i];
                        sb.append(',').append(s);
                    }
                    s = sb.toString().trim();
                    if (s.charAt(s.length() - 1) == c) {
                        s = s.substring(1, s.length() - 1);
                    }
                    results.add(s);
                } else {
                    results.add(s);
                }
            }
        }
        return results.toArray(new String[results.size()]);
    }

    public static String join(String... parts) {
        StringBuilder sb = new StringBuilder(parts.length);
        for (String part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }

    public static String join(Iterable<?> elements, String separator) {
        if (elements == null) {
            return "";
        }
        return join(elements.iterator(), separator);
    }

    public static String join(Iterator<?> elements, String separator) {
        if (elements == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (elements.hasNext()) {
            Object o = elements.next();
            if (sb.length() > 0 && separator != null) {
                sb.append(separator);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static String join(Object[] elements, String separator) {
        if (elements == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : elements) {
            if (sb.length() > 0 && separator != null) {
                sb.append(separator);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static int count(String source, String substr) {
        return count(source, substr, 0);
    }

    public static int count(String source, String substr, int start) {
        if (source == null || source.length() == 0) {
            return 0;
        }

        int count = 0;
        int j = start;
        int sublen = substr.length();
        if (sublen == 0) return 0;
        while (true) {
            int i = source.indexOf(substr, j);
            if (i == -1) {
                break;
            }
            count++;
            j = i + sublen;
        }
        return count;
    }

    public static int count(String source, char c) {
        return count(source, c, 0);
    }

    public static int count(String source, char c, int start) {
        if (source == null || source.length() == 0) {
            return 0;
        }

        int count = 0;
        int j = start;
        while (true) {
            int i = source.indexOf(c, j);
            if (i == -1) {
                break;
            }
            count++;
            j = i + 1;
        }
        return count;
    }
}
