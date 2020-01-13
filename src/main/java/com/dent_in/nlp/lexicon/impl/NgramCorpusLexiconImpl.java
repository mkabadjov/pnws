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

import com.dent_in.nlp.lexicon.NgramCorpusLexicon;

import static com.dent_in.nlp.lexicon.Lexicon.TYPE.NGRAMCORPUSLEXICON;

public class NgramCorpusLexiconImpl extends CorpusLexiconImpl
        implements NgramCorpusLexicon {

    NGRAMSIZE nsize = NGRAMSIZE.BIGRAM;

    public NgramCorpusLexiconImpl () {
        super();
        nsize = NGRAMSIZE.BIGRAM; // default
        this.setType(NGRAMCORPUSLEXICON);
    }

    public NgramCorpusLexiconImpl (NGRAMSIZE nsize) {
        super();
        this.nsize = nsize;
        this.setType(NGRAMCORPUSLEXICON);
    }

    /**
     * Returns the value of n of this n-gram corpus lexicon.
     * @return The value of n of this n-gram corpus lexicon
     */
    public NGRAMSIZE getNgramSize() {
        return this.nsize;
    }
}
