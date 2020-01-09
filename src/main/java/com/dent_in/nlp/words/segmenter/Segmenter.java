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

import java.util.List;
import java.util.Set;

/**
 * This is an interface which defines two key methods of a word segmenter:
 * 1. segment()
 *    returns either a list of plausible segmentations or the best segmentation of such a list
 *    according to a given maximazation function (e.g., Bayesian probability) for a given input string
 *    That is, one segmentation splits the string in one or more places, or even none (e.g., ac+cur+ex).
 * 2. split()
 *    returns a list of plausible binary splits for an input string (i.e., one split splits the string in exactly one place; e.g., a+ccurex).
 *
 *    The two are intended to be used in tandem, where segment() makes calls to split() from which
 *    composes the list of segmentations. This draws on Peter Norvig's algorithm described in chap. 14 of "Beautiful Data" (https://norvig.com/ngrams/).
 */
public interface Segmenter {

    enum type {
        NORVIG, AHOCORASICK
    }

    /**
     * Generally a recursive function for segmenting an input string into a set of possible segmentations,
     * aggregated from various calls to method split(). Draws on Peter Norvig's word segmentation algorithm (see above).
     *
     * The second parameter is specific to the Java reimplementation of the Fancifulness segmentation
     * driven by Aho-Corasick indexing, and hence, can be ignored in more generic implementations.
     *
     * @param string The input string to be segmented
     * @param cutoff The boundary cutoff after which splits are considered (usually starts at 0 and slides to the end of the input string)
     * @return A Set of Segmentation candidates
     */
    Set<Segmentation> segment(String string, int cutoff);

    /**
     * Returns the best segmentation out of a set of candidate segmentations returned by segment().
     * The version of the segmentation is controlled by the boolean parameter useUnigram (true/false for Norvig's segment()/segment2(), respectively).
     *
     * @param term The input string
     * @return The best segmentation for the given string
     */
    Segmentation getBestSegmentation(String term);


    /**
     * Returns a segmentaiton of overlapping dictionary terms returned by the Aho-Corasick algorithm.
     *
     * @param term
     * @return
     */
    Segmentation getAhocTermsSegmentation(String term);

    /**
     * This is a variant of the getBestSegmentation method above, only receives as parameters
     * an already processed set of segmentations.
     *
     * @param segmentations A Set of Segmentation candidates
     * @return The best Segmentation
     */
    static Segmentation getBestSegmentation(Set<Segmentation> segmentations) {
        //In Norvig's implementation the maximization criterion is given by Pwords
        // this is getProbability( CorpusLexicon, String [] )
        Segmentation result = new Segmentation(null,0);
        double maxProbability = -1.0;
        for(Segmentation segmentation : segmentations) {
            double segmentationProbability = segmentation.getProbability();
            //maximization step
            if(segmentationProbability > maxProbability) {
                result = segmentation;
                maxProbability = segmentationProbability;
            }
        }
        return result;
    }

    /**
     * Returns the Splitter component for this segmenter.
     * @return The Splitter component fo this segmenter
     */
    public Splitter getSplitter();

    /**
     * Returns the SegmentationScorer component for this segmenter.
     * @return The SegmentationScorer component for this segmenter
     */
    public SegmentationScorer getSegmentationScorer();

}
