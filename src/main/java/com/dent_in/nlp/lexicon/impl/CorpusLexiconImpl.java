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

package com.dent_in.nlp.lexicon.impl;

import com.dent_in.nlp.lexicon.AbstractLexicon;
import com.dent_in.nlp.lexicon.CorpusLexicon;
import com.dent_in.nlp.lexicon.Lexicon;
import com.dent_in.nlp.lexicon.utils.LexiconReader;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import static com.dent_in.nlp.lexicon.Lexicon.TYPE.CORPUSLEXICON;

public class CorpusLexiconImpl extends AbstractLexicon
        implements CorpusLexicon {

    private Map<String, Long> wordFrequencies = null;
    private Long corpusSize = null;
    private Long minFrequency = null;
    private Long maxFrequency = null;


    public CorpusLexiconImpl() {
        this.wordFrequencies = new HashMap<>();
        this.setType( CORPUSLEXICON );
        this.corpusSize = new Long(0);
    }

    public CorpusLexiconImpl(Lexicon.TYPE type) {
        this();
        this.setType(type);
    }

    /**
     * Loads a lexicon file.
     * Called multiple times has a cumulative effect, that is, aggregates frequencies from various lists.
     *
     * @param lexiconFilePath The path to the input file
     */
    public void load(String lexiconFilePath) {
        LexiconReader.loadLexicon(lexiconFilePath, this);
        //This could be done on the fly within the loading,
        //but like this it's neater albeit the need for an extra sweep over the frequency map
        recomputeStats();
    }

    /**
     * Recomputes all stats after a new lexicon has been loaded.
     */
    private void recomputeStats() {
        long min = 1000000;
        long max = 0;
        long total = 0;
        for (Long frequency : this.wordFrequencies.values()) {
            total += frequency;
            if (frequency < min )
                min = frequency;
            if (frequency > max)
                max = frequency;
        }
        //set new stats
        this.corpusSize = new Long(total);
        this.minFrequency = new Long(min);
        this.maxFrequency = new Long(max);
    }


    /**
     * Returns the size of the corpus (i.e., summing up all frequencies into a grand total).
     *
     * @return The size of the corpus
     */
    public Long getCorpusSize() {
        return this.corpusSize;
    }

    /**
     * Returns the frequency of a given word, or min frequency if word not found in lexicon.
     *
     * @param token A word whose frequency is sought
     * @return The frequency of the given word
     */
    public Long getFrequency(String token) {
        Long result = null;
        //normally
        result = this.wordFrequencies.get(token);
        //but if,
        if ( !this.containsWord (token) )
            result = new Long (this.getMinFrequency());

        return result;
    }

    /**
     * Returns a map of the word->frequency pairs.
     *
     * @return A map of the word->frequency pairs
     */
    public Map<String, Long> getFrequencies() {
        return this.wordFrequencies;
    }

    /**
     * Returns the minimum frequency in the list of frequencies.
     *
     * @return The minimum frequency
     */
    public Long getMinFrequency() {
        return this.minFrequency;
    }

    /**
     * Returns the maximum frequency in the list of frequencies.
     *
     * @return The maximum frequency
     */
    public Long getMaxFrequency() {
        return this.maxFrequency;
    }

    @Override
    public Double getLogLikelihood(String token) {
        Double result = null;
        //use doubleValue() to force result to be double
        double logLikelihood = (-1.0)*Math.log(token.length()*this.getFrequency(token).doubleValue()/this.getCorpusSize());
        return new Double(logLikelihood);
    }

    /**
     * Returns the size of the vocabulary (i.e., number of words).
     *
     * @return The size of the vocabulary
     */
    public Long getVocabularySize() {
        Long result = new Long(this.wordFrequencies.keySet().size());
        return result;
    }

    /**
     * Checks if this lexicon contains the given word.
     *
     * @param word The word to check for its existence
     * @return True if this lexicon contains the given word, false otherwise
     */
    public Boolean containsWord(String word) {
        return this.wordFrequencies.containsKey(word);
    }

    /**
     * Returns all the words in this lexicon.
     *
     * @return All the words in this lexicon.
     */
    public Set<String> getWords() {
        return this.wordFrequencies.keySet();
    }

    /**
     * Returns the map of word frequencies.
     *
     * @return The map of word frequencies
     */
    public Object getBackBone() {
        return this.wordFrequencies;
    }
}
