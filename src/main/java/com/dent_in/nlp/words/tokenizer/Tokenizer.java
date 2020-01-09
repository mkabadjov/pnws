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

package com.dent_in.nlp.words.tokenizer;

import java.io.File;
import java.util.List;

/**
 * TODO: Consider extending interface Iterator from Java Collections, which brings in the following methods: forEachRemaining, hasNext, next, remove.
 *
 * Like this, this interface would align better with Tokenizers like Stanford's PTBTokenizer for English.
 */
public interface Tokenizer {

    enum TYPE {
        DEFAULT, ENGLISH, FRENCH, DUTCH, SPANISH, CHINESE
    }

    /**
     * Returns all tokens of this Tokenizer as a List for convenience.
     * @return Returns the list of tokens
     */
    public List<Token> tokenize ();

    /**
     * Tokenizes a given string, practical for tokenizing trademarks.
     *
     * @param inputString
     * @return Returns the list of tokens
     */
    public List<Token> tokenize (String inputString);

    /**
     * For large files, this is not a practical method.
     * @param inputFile The input file name
     * @return Returns the list of tokens
     */
    public List<Token> tokenize (File inputFile);

}
