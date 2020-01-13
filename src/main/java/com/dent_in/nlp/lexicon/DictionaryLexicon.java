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
import java.util.List;

public interface DictionaryLexicon extends Lexicon {


    /**
     * Returns the definition for a given word.
     * @param word The query word
     * @return The definition for the word
     */
    public List<? extends LexicalEntry> getDefinition(String word);

    /**
     * Returns a Map {word, definition} for all words.
     * XXX: But isn't a definition mapping one-to-one with a word sense?
     *
     * @return The Map {word, definition} for all words
     */
    public Map<String, List<? extends LexicalEntry>> getDefinitions();


    /**
     * Returns all the senses for a given word.
     * @param word The query word
     * @return All the senses for a given word
     */
    public List<String> getSenses(String word);

    /**
     * Returns a Map {word, [senses]} for all words.
     * @return A Map {word, [senses]} for all words.
     */
    public Map<String, List<String>> getSenses();


    /**
     * Returns the translations in a specific language for a given word.
     * @param word The query word
     * @param languageID The language of interest
     * @return The translations in a specific language for a given word
     */
    public List<? extends LexicalEntry> getTranslations(String word, String languageID);

    /**
     * Returns the translations in all languages for a given word.
     * The keys in the maps are the language IDs.
     * @param word The query word
     * @return The translations in all languages for a given word
     */
    public Map<String, List<? extends LexicalEntry>> getTranslations(String word);

    /**
     * Returns the translations in all languages for all words.
     * @return The translations in all languages for all words
     */
    public Map<String, Map<String, List<? extends LexicalEntry>>> getTranslations();

    /**
     * Returns phonetic representation of a given word according to the International Phonetics Alphabet (IPA).
     * @param word The query word
     * @return The phonetic representation for the word
     */
    public List<? extends LexicalEntry> getPhoneticSymbol(String word);

    /**
     * Returns phonetic representations of all words
     * @return The phonetic representations of all words
     */
    public Map<String, List<LexicalEntry>> getPhoneticSymbols();
}
