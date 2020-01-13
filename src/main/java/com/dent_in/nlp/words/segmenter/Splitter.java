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

package com.dent_in.nlp.words.segmenter;

import com.dent_in.nlp.words.tokenizer.Token;

import java.util.List;

public interface Splitter {

    enum type {
        EXHAUSTIVE, BOUNDARY_DRIVEN, BOUNDARY_OVERLAP
    }

    /**
     * Returns a list of binary splits for an input string.
     * A given split is represented by one Token instance, where the start offset is always 0 and the end offset
     * is where the input string is split, hence, must be in the range [1, string.length()-1].
     *
     * @param string The input string to be splitted
     * @param cutoff The boundary cutoff after which splits are considered (usually starts at 0 and slides to the end of the input string)
     * @return A List of Token where each token's offset is the head of the string and the rest of the string is the tail
     */
    List<Token> split(String string, int cutoff);

    /**
     * Returns all dictionary entries coming out of the Aho-Corasick algorithm.
     *
     * @param string The input string
     * @return All dictionary entries identified by the Aho-Corasick algorithm
     */
    List<Token> ahocIndex(String string);

    /**
     * For boundary driven splitters, these must be set first.
     *
     * @param string The input string on which to identify possible split boundaries
     */
    public void setBoundaries(String string);

    /**
     * For boundary driven splitters, boundaries must be set and they can also be retrieved.
     *
     * @return An Array of ints signalling the boundaries
     */
    public int [] getBoundaries();

}
