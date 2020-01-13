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

public interface SegmentationScorer {

    enum type {
        BAYES_UNIGRAM, BAYES_BIGRAM, RANDOM_FORREST
    }

    /**
     * Computes a score for a sequence of words (e.g., Bayes unigram or bigram probability, other score by supervised learning, etc.).
     * @param words An Array of String (i.e. sequence of words)
     * @return The Bayes probability for the given sequence of words
     */
    public double score(String[] words);

    /**
     * Computes a score for a sequence of AnnotatedTerms (e.g., Bayes unigram or bigram probability, other score by supervised learning, etc.).
     * @param annotatedTerms A List of AnnotatedTerm (again a sequence of words)
     * @return The Bayes probability for the given sequence of words
     */
    public double score(List<AnnotatedTerm> annotatedTerms);

    /**
     *
     * Here features has been externalised in order to handle properly the morphing feature.
     * Morphing of the input string is external to the segmentation, hence, the need for passing it on explicitly.
     *
     * Computes a score for a sequence of AnnotatedTerms (e.g., Bayes unigram or bigram probability, other score by supervised learning, etc.).
     *
     * @param features The input features (e.g., for a classifier or a regressor)
     * @return The Bayes probability for the given sequence of words
     */
    public double score(double [] features);

}
