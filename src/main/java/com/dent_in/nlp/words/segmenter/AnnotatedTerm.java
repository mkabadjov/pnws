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

import com.dent_in.nlp.words.tokenizer.Token;

import java.util.List;
import java.util.Set;

public class AnnotatedTerm {

    private Token content;
    private Set<Segmentation> segmentationCandidates; // null if none

    // Examples:
    //  PEAKVIEW  -- PEAK VIEW
    //  SPORTSWEAR -- SPORTS WEAR  (not SPORT SWEAR)

    // Getters
    public Token getContent() {
        return this.content;
    }

    public Set<Segmentation> getSegmentationCandidates(){
        return this.segmentationCandidates;
    }


    // Setters
    public void setContent(Token content) {
        this.content = content;
    }

    public void setSegmentationCandidates(Set<Segmentation> segmentationCandidates) {
        this.segmentationCandidates = segmentationCandidates;
    }


    @Override
    public String toString() {
        //Two parts: term and segmentation
        Set<Segmentation> segmentationSet = getSegmentationCandidates();

        StringBuffer theSegments = new StringBuffer();
        if( segmentationSet != null ) {
            for (Segmentation seg : segmentationSet) {
                //for now there's only one segmentation, the best one (i.e., one cycle here)
                List<AnnotatedTerm> constituentTerms = seg.getParts();
                for (AnnotatedTerm annotatedTerm : constituentTerms) {
                    theSegments.append(annotatedTerm.getContent().getToken() + " ");
                }
            }
        }

        return "{ \"term\" : \"" + getContent().getToken() + "\", \"segmentation\" : \"" + theSegments.toString().trim() + "\" }";
    }

}
