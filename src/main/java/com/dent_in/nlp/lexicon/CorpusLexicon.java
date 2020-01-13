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

package com.dent_in.nlp.lexicon;

import java.util.Map;

public interface CorpusLexicon extends Lexicon {

    /**
     * Returns the total of all frequencies in the lexicon.
     * @return The size of the corpus (i.e., total number of tokens)
     */
    public Long getCorpusSize();

    /**
     * Returns the frequency of a token in the corpus.
     * @param token The query token
     * @return The frequency for the token in the corpus
     */
    public Long getFrequency(String token);

    /**
     * Returns a Map of {word, frequency} representing the whole corpus.
     * @return The Map of {word, frequency} representing the whole corpus.
     */
    public Map<String, Long> getFrequencies();

    /**
     * Returns the minimum frequency.
     *
     * @return the minimum frequency
     */
    public Long getMinFrequency();

    /**
     * Returns The maximum frequency.
     *
     * @return The maximum frequency
     */
    public Long getMaxFrequency();

    /**
     * Returns the token log-likelihood penalizing longer words (-log(wordLength*wordFreq/total))
     *
     * @param token The token of interest
     * @return The token's log-likelihood
     */
    public Double getLogLikelihood(String token);

}
