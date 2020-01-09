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

import com.dent_in.nlp.words.segmenter.*;
import com.dent_in.nlp.words.tokenizer.Token;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of the word segmentation algorithm by Peter Norvig described in chap. 14 of the book 'Beautiful Data'
 * (see python version and data resources needed at: https://norvig.com/ngrams/)
 *
 */
public class SegmenterImpl implements Segmenter {

    //private long NUMBEROFTOKENS = 1024908267229;
    //max int 2147483647
    //max long 9,223,372,036,854,775,807

    Segmenter.type type = null;
    Splitter splitter = null;
    SegmentationScorer scorer = null;



    public SegmenterImpl(Splitter splitter, SegmentationScorer scorer) {
        this.type = Segmenter.type.NORVIG;
        this.splitter = splitter;
        this.scorer = scorer;
    }


    /**
     * Return a list of words that is the best segmentation of text.
     *
     * @param string The input string to be segmented
     * @param cutoff This parameter is completely ignored in this implementation
     * @return The Set of candidate segmentations
     */
    public Set<Segmentation> segment(String string, int cutoff) {
        Set<Segmentation> result = new HashSet<>();
        List<Token> binarySplits = splitter.split(string, cutoff);

        //base case
        if(binarySplits.isEmpty()) {
            return result; //here it is empty
        }

        //recursive step
        for (Token t : binarySplits) {
            Set<Segmentation> remainingSegmentations = segment(string.substring(t.getEndOffset()), cutoff);
            if( remainingSegmentations.isEmpty() ) {
                //get last remainder, put it as last segmentation
                List<AnnotatedTerm> taggedTerms = new ArrayList<>();
                createSegmentation(t, taggedTerms); //first, resulting segmentation is ignored
                Token remainder = new Token(string.substring(t.getEndOffset()), t.getEndOffset(), string.length());
                Segmentation lastSegmentation = createSegmentation(remainder, taggedTerms); //remainder
                result.add(lastSegmentation);
                //and the counterpart (i.e., the no boundary case)
                taggedTerms = new ArrayList<>();
                remainder = new Token(string.substring(t.getStartOffset()), t.getStartOffset(), string.length());
                lastSegmentation = createSegmentation(remainder, taggedTerms);
                result.add(lastSegmentation);
            } else {
                //add first token to each one of those
                for (Segmentation segmentation : remainingSegmentations) {
                    updateSegmentation(segmentation, t);
                }
                result.addAll(remainingSegmentations);
            }
        }

        return result;
    }

    protected Segmentation createSegmentation(Token t, List<AnnotatedTerm> taggedTerms){
        //get token, return it as segmentation
        AnnotatedTerm tTerm = new AnnotatedTerm();
        tTerm.setContent(t);
        taggedTerms.add(tTerm);
        //probability
        double segmentationProbability = scorer.score(taggedTerms);

        return new Segmentation(taggedTerms, segmentationProbability);
    }

    protected void updateSegmentation(Segmentation segmentation, Token t) {
        List<AnnotatedTerm> taggedTerms = segmentation.getParts();
        AnnotatedTerm tTerm = new AnnotatedTerm();
        tTerm.setContent(t);
        taggedTerms.add(0, tTerm); //add it in first position
        //probability
        double segmentationProbability = scorer.score(taggedTerms);
        segmentation.setProbability(segmentationProbability);
    }

    /**
     * Given a compound this method returns the most likely segmentation for it based on either
     * the unigram model or the bigram model which is controled by the flag useUnigram.
     *
     * @param string The input string
     *
     * @return The best segmentation
     */
    public Segmentation getBestSegmentation(String string) {
        Segmentation result = new Segmentation();
        Set<Segmentation> segmentations = segment(string, 0);
        result = Segmenter.getBestSegmentation(segmentations);
        return result;
    }


    public Segmentation getAhocTermsSegmentation(String term) {
        return null; //applicable only for Ahoc Segmenters
    }

    public Splitter getSplitter() {
        return this.splitter;
    }

    public SegmentationScorer getSegmentationScorer() {
        return this.scorer;
    }


}
