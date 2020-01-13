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

import com.dent_in.nlp.lexicon.exception.UnsupportedLexiconException;
import com.dent_in.nlp.lexicon.impl.CorpusLexiconImpl;
import com.dent_in.nlp.lexicon.impl.NgramCorpusLexiconImpl;

public class LexiconFactory {

    /**
     * Creates a default Lexicon.
     *
     * @return The newly created lexicon
     */
    public Lexicon createInstance() throws UnsupportedLexiconException {
        return createInstance(Lexicon.TYPE.DEFAULT);
    }

    /**
     * Creates specialized Lexicon based on the type given.
     *
     * @param type The type of Lexicon to be created
     * @return The newly created lexicon
     */
    public Lexicon createInstance(Lexicon.TYPE type) throws UnsupportedLexiconException {
        Lexicon lexicon = null;

        switch (type) {
            case CORPUSLEXICON:
                lexicon = new CorpusLexiconImpl();
                break;
            case NGRAMCORPUSLEXICON:
                lexicon = new NgramCorpusLexiconImpl();
                break;
            case DICTIONARYLEXICON:
                throw new UnsupportedLexiconException();
            case THESAURUSLEXICON:
                throw new UnsupportedLexiconException();
            case DEFAULT:
                lexicon = new CorpusLexiconImpl(type);
        }

        return lexicon;
    }
}
