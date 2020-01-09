/*
 * Licensed to Dent-in Tech Solutions under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Dent-in Tech Solutions licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dent_in.nlp.words.segmenter.impl;

import com.dent_in.nlp.words.segmenter.Splitter;
import com.dent_in.nlp.words.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class SplitterImpl implements Splitter {

    private final byte MAX_LENGTH = 20;
    Splitter.type type = null;

    public SplitterImpl() { this.type = Splitter.type.EXHAUSTIVE; }

    /**
     * Return a list of all possible (first, rem) pairs, len(first)&lt;=L.
     *
     * @param string The input string to be splitted
     * @param cutoff This parameter is completely ignored in this implementation
     * @return A List of Token where each token's offset is the head of the string and the rest of the string is the tail
     */
    public List<Token> split(String string, int cutoff) {
        List<Token> result = new ArrayList<>();
        for(int i = 1; i < string.length() && i < MAX_LENGTH; i++) {
            Token token = new Token(string.substring(0,i), 0, i);
            result.add(token);
        }

        return result;
    }

    /**
     * In an exhaustive splitter no need for boundaries.
     * @param string The input string on which to identify possible split boundaries
     */
    public void setBoundaries(String string) {
        //This is an exhaustive splitter.
    }

    public int[] getBoundaries() {
        return null; //exhaustive splitters have no boundaries
    }

    public List<Token> ahocIndex(String string) {
        //again a method only used in Ahoc-related splitters
        return null;
    }

}
