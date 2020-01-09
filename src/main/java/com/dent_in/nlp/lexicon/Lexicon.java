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

import java.util.Set;

public interface Lexicon {

    enum TYPE {
        DEFAULT, CORPUSLEXICON, NGRAMCORPUSLEXICON, DICTIONARYLEXICON, THESAURUSLEXICON
    }

    /**
     * This in place of public Integer getSize();
     *
     * Returns all the entries (i.e., token types) in the lexicon.
     * @return
     */
    public Long getVocabularySize();

    /**
     *
     * @param word
     * @return
     */
    public Boolean containsWord(String word);

    /**
     * XXX: It might be better (or in addition to) to provide an Iterator over the Collection of words.
     * @return
     */
    public Set<String> getWords();

    /**
     * Implementing classes need to implement their own internal data structure to represent the lexical resource in question (e.g., a map for word frequencies).
     *
     * @return Returns the internal data structure of this lexicon
     */
    public abstract Object getBackBone();

    /**
     * All implementing classes must provide a loader.
     *
     * @param lexiconFilePath The path to the input file
     */
    public void load(String lexiconFilePath);

    /**
     * All lexicons must have a type.
     *
     * @return Returns the type of this lexicon
     */
    public TYPE getType();
}
