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

/**
 * A more specialized interface defining n-gram functionality.
 * The unigram case is simply the parent interface CorpusLexicon.
 * Then it covers the more specific cases of bigram, trigram and up to five-gram by generalizing the notion of token
 * in the parent interface, that is, token becomes bigram, trigram, etc. based on the ngramsize.
 */
public interface NgramCorpusLexicon extends CorpusLexicon {

    enum NGRAMSIZE {
        BIGRAM, TRIGRAM, FOURGRAM, FIVEGRAM
    }

    public NGRAMSIZE getNgramSize();
}
