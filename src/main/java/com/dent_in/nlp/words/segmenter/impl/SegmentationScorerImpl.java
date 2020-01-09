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

package com.dent_in.nlp.words.segmenter.impl;

import com.dent_in.nlp.lexicon.pdist.ProbabilityDistribution;
import com.dent_in.nlp.words.segmenter.AnnotatedTerm;
import com.dent_in.nlp.words.segmenter.Segmentation;
import com.dent_in.nlp.words.segmenter.SegmentationScorer;

import java.util.List;

public class SegmentationScorerImpl implements SegmentationScorer {

    SegmentationScorer.type type = null;
    ProbabilityDistribution pDistribution = null;

    public SegmentationScorerImpl(String unigramDataFileName, String bigramDataFileName, String totalNumberOfTokens, SegmentationScorer.type type) {
        Long googleNGramCorpusSize = new Long (totalNumberOfTokens);
        this.pDistribution = new ProbabilityDistribution(unigramDataFileName, bigramDataFileName, googleNGramCorpusSize.longValue());
        this.type = type;
    }

    public double score(List<AnnotatedTerm> taggedTerms) {
        String [] words = getTokenStrings(taggedTerms);
        return score(words);
    }


    public double score(String[] words) {
        double result = 0.0;
        //get the probability of sequence of words
        if(this.type == SegmentationScorer.type.BAYES_UNIGRAM) {
            //if I use the unigram corpus I get segment
            result = pDistribution.getProbability(words);
        } else {
            //if I use the bigram corpus I should get segment2
            result = pDistribution.getProbability2(words);
        }

        return result;
    }

    /**
     * Gets a segmentation and returns its parts (i.e., a list of TaggedTerms with Tokens inside) as an array of String[].
     *
     * @param segmentation The Segmentation
     * @return The segment Tokens as Strings
     */
    public String[] getTokenStrings(Segmentation segmentation) {
        List<AnnotatedTerm> taggedTerms = segmentation.getParts();
        return getTokenStrings(taggedTerms);
    }

    /**
     * Gets a list of TaggedTerms and returns the tokens as an array of String[].
     *
     * @param taggedTerms The TaggedTerms
     * @return The segment Tokens as Strings
     */
    public String[] getTokenStrings(List<AnnotatedTerm> taggedTerms) {
        String [] result = new String[taggedTerms.size()];
        int i = 0;
        for(AnnotatedTerm tTerm: taggedTerms) {
            result[i++] = tTerm.getContent().getToken();
        }
        return result;
    }

    public double score(double [] features) {
        //isMorphed is only used for random-forest-driven scorers
        return -1.0;
    }

}
