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

import com.dent_in.nlp.lexicon.CorpusLexicon;
import com.dent_in.nlp.words.segmenter.impl.SegmentationScorerImpl;
import com.dent_in.nlp.words.segmenter.impl.SegmenterImpl;
import com.dent_in.nlp.words.segmenter.impl.SplitterImpl;

public class SegmenterFactory {
    public Segmenter createSegmenter(Segmenter.type type, Splitter splitter, SegmentationScorer scorer) {
        switch ( type ) {
            case AHOCORASICK:
            case NORVIG:
            default:
                return new SegmenterImpl(splitter, scorer);
        }
    }

    public Splitter createSplitter(Splitter.type type, CorpusLexicon corpusLexicon) {
        switch ( type ) {
            case BOUNDARY_DRIVEN:
            case EXHAUSTIVE:
            default:
                return new SplitterImpl();

        }

    }

    public SegmentationScorer createSegmentationScorer(String unigramDataFileName, String bigramDataFileName, String totalNumberOfTokens, SegmentationScorer.type type) {
        switch ( type ) {
            case RANDOM_FORREST:
                throw new UnsupportedOperationException();
            case BAYES_BIGRAM:
            case BAYES_UNIGRAM:
            default:
                return new SegmentationScorerImpl(unigramDataFileName, bigramDataFileName, totalNumberOfTokens, type);

        }
    }


}
