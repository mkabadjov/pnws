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

package com.dent_in.nlp.lexicon.pdist;

import com.dent_in.nlp.lexicon.CorpusLexicon;
import com.dent_in.nlp.lexicon.Lexicon;
import com.dent_in.nlp.lexicon.LexiconFactory;
import com.dent_in.nlp.lexicon.NgramCorpusLexicon;
import com.dent_in.nlp.lexicon.utils.LexiconReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to model a probability distribution estimated from counts in datafile.
 * (this stands for class Pdist in the python version of Norvig's segmenter.)
 *
 */
public class ProbabilityDistribution {

    private CorpusLexicon lexicon = null;
    private NgramCorpusLexicon bigramLexicon = null;
    private long numberOfTokens = 0;
    private boolean isLetterNGram = false;
    private int overlap = 1; //only for letter n-grams

    public ProbabilityDistribution(String dataFileName){
        //create unigram lexicon and load from data
        this.lexicon = (CorpusLexicon) new LexiconFactory().createInstance(Lexicon.TYPE.CORPUSLEXICON);
        this.lexicon.load(dataFileName);
        this.numberOfTokens = this.lexicon.getCorpusSize();
    }

    public ProbabilityDistribution(String dataFileName, long numberOfTokens){
        this(dataFileName);
        this.numberOfTokens = numberOfTokens;
    }

    public ProbabilityDistribution(String dataFileName, String bigramDataFileName, long numberOfTokens){
        this(dataFileName);
        this.numberOfTokens = numberOfTokens;
        this.bigramLexicon = (NgramCorpusLexicon) new LexiconFactory().createInstance(Lexicon.TYPE.NGRAMCORPUSLEXICON);
        LexiconReader.loadNgramCorpusLexicon(FileSystems.getDefault().getPath(bigramDataFileName), this.bigramLexicon, StandardCharsets.UTF_8);
    }

    public ProbabilityDistribution(String dataFileName, String bigramDataFileName, long numberOfTokens, boolean isLetterNGram){
        this(dataFileName);
        this.numberOfTokens = numberOfTokens;
        this.bigramLexicon = (NgramCorpusLexicon) new LexiconFactory().createInstance(Lexicon.TYPE.NGRAMCORPUSLEXICON);
        LexiconReader.loadNgramCorpusLexicon(FileSystems.getDefault().getPath(bigramDataFileName), this.bigramLexicon, StandardCharsets.UTF_8);
        this.isLetterNGram = isLetterNGram;
    }

    public ProbabilityDistribution(String dataFileName, String bigramDataFileName, long numberOfTokens, boolean isLetterNGram, int overlap){
        this(dataFileName);
        this.numberOfTokens = numberOfTokens;
        this.bigramLexicon = (NgramCorpusLexicon) new LexiconFactory().createInstance(Lexicon.TYPE.NGRAMCORPUSLEXICON);
        LexiconReader.loadNgramCorpusLexicon(FileSystems.getDefault().getPath(bigramDataFileName), this.bigramLexicon, StandardCharsets.UTF_8);
        this.isLetterNGram = isLetterNGram;
        this.overlap = overlap;
    }

    public Lexicon getLexicon() {
        return this.lexicon;
    }

    public Lexicon getBigramLexicon() {
        return this.bigramLexicon;
    }

    public long getNumberOfTokens() {
        return this.numberOfTokens;
    }

    /**
     * Estimate the probability of an unknown word.
     *
     * @param word The query word
     * @param penalizeLongWords True if long words should not be allowed to score high, false otherwise
     * @return The probability of an unknown word
     */
    public double getProbabilityOfUnknownWord(String word, boolean penalizeLongWords) {
        double result = 0.0;

        if(penalizeLongWords) {
            result = (double)10/(this.numberOfTokens * Math.pow(10,word.length()));
        } else {
            result = (double)1/this.numberOfTokens;
        }

        return result;
    }


    /**
     * The Naive Bayes probability of a sequence of words - based on a unigram model (i.e., for Peter Norvig's first version of segment()).
     *
     * @param words The input sequence of words
     * @return The probability of a sequence of words
     */
    public double getProbability(String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        double product = 1;
        for (String word : words) {
            product *= getProbability(this.lexicon, word);
        }
        return product;
    }

    /**
     * The Naive Bayes log-probability of a sequence of words - based on a unigram model
     * (more efficient and safer - i.e., avoids underflows problems - to do sums of log-probabilities than product of probabilities).
     *
     * @param words The input sequence of words
     * @return The log-probability of a sequence of words
     */
    public double getLogProbability(String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        double sum = 0;
        for (String word : words) {
            sum += Math.log(getProbability(this.lexicon, word));
        }
        return sum;
    }

    /**
     * The Naive Bayes probability of a sequence of words - based on a bigram model (i.e., for Peter Norvig's 2nd version of segment2()).
     *
     * @param words The input sequence of words
     * @return The probability of a sequence of words based ona bigram model
     */
    public double getProbability2(String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        String previousWord = "<S>";
        double product = 1;
        for (String word : words) {
            product *= getConditionalProbability(word, previousWord);
            previousWord = word;
        }
        return product;
    }

    /**
     * The Naive Bayes log-probability of a sequence of words - based on a bigram model
     * (more efficient and safer to do sums of log-probabilities than product of probabilities).
     *
     * @param words The input sequence of words
     * @return The log-probability of a sequence of words based ona bigram model
     */
    public double getLogProbability2(String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        String previousWord = "<S>";
        double sum = 0;
        for (String word : words) {
            sum += Math.log(getConditionalProbability(word, previousWord));
            previousWord = word;
        }
        return sum;
    }

    /**
     * The Naive Bayes probability of a sequence of words.
     *
     * @param lexicon The lexicon to use to compute the probabilities
     * @param words The input sequence of words
     * @return The Naive Bayes probability of a sequence of words
     */
    public double getProbability(CorpusLexicon lexicon, String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        double product = 1;
        for (String word : words) {
            product *= getProbability(lexicon, word);
        }
        return product;
    }

    /**
     * The Naive Bayes probability of a sequence of words.
     *
     * @param lexicon The lexicon to use to compute the probabilities
     * @param words The input sequence of words
     * @return The log-probability of a sequence of words
     */
    public double getLogProbability(CorpusLexicon lexicon, String [] words){
        //safeguard, return 0.0 if words is empty
        if(words.length == 0) return 0.0;
        double sum = 0;
        for (String word : words) {
            sum += Math.log(getProbability(lexicon, word));
        }
        return sum;
    }

    /**
     * The probability estimate of a word based on n-gram count data.
     *
     * @param lexicon The lexicon to use to compute the probabilities
     * @param ngram The query ngram
     * @param penalizeLongWords True if long words should be penalized, false otherwise
     * @return The probability of the ngram
     */
    public double getProbability(CorpusLexicon lexicon, String ngram, boolean penalizeLongWords){
        double result = 0.0;
        if(lexicon.containsWord(ngram)) {
            result = (double)lexicon.getFrequency(ngram)/this.numberOfTokens;
        } else {
            result = getProbabilityOfUnknownWord(ngram, penalizeLongWords);
        }
        return result;
    }

    /**
     * The probability estimate of a word based on unigram count data by penalizing unknown words that are long.
     * @param lexicon The lexicon to use to compute the probabilities
     * @param ngram The query ngram
     * @return The probability of the ngram (long words penalized)
     */
    public double getProbability(CorpusLexicon lexicon, String ngram){
        return getProbability(lexicon, ngram, true);
    }

    /**
     * Conditional probability of word, given previous word.
     *
     * @param word The query word
     * @param previousWord The previous word
     * @return The conditional probability of the word given the previous word
     */
    public double getConditionalProbability(String word, String previousWord) {
        double result = 0.0;
        String bigram = null;
        //bigrams formed differently depending on word-/letter-ngrams
        if(!this.isLetterNGram) {
            bigram = previousWord + ' ' + word;
        } else {
            bigram = previousWord + word.substring(this.overlap);
        }
        if (bigramLexicon.containsWord(bigram)) {
            result = getProbability(bigramLexicon, bigram, false) / getProbability(lexicon, previousWord);
        } else {
            result = getProbability(lexicon, word); //fallback to unigram
        }

        return result;
    }

    ////////////// PERPLEXITY MEASURES /////////////////////////////

    /**
     * Perplexity of a sequence of words (unigram).
     *
     * @param words The words
     * @return The perplexity
     */
    public double getPerplexity(String [] words) {
        double p = getProbability(words);
        int n = words.length;
        //nth root of 1/probability of the sequence
        double pp = Math.pow(1.0/p,1.0/n);

        return pp;
    }

    /**
     * Perplexity of a sequence of words (bigram).
     *
     * @param words The words
     * @return The perplexity
     */
    public double getPerplexity2(String [] words) {
        double p = getProbability2(words);
        int n = words.length;
        //nth root of 1/probability of the sequence
        double pp = Math.pow(1.0/p,1.0/n);

        return pp;
    }


    /**
     * Perplexity of a sequence of words in log space (unigram).
     *
     * @param words The words
     * @return The perplexity
     */
    public double getLogPerplexity(String [] words){
        double p = getLogProbability(words);
        int n = words.length;
        double exponent = (-1.0/n)*p;
        //then e at the power of all-of-the-above (e^[-1/n times the log probability of the word sequence])
        double pp = Math.exp(exponent);

        return pp;
    }

    /**
     * Perplexity of a sequence of words in log space (bigram).
     *
     * @param words The words
     * @return The perplexity
     */
    public double getLogPerplexity2(String [] words){
        double p = getLogProbability2(words);
        int n = words.length;
        double exponent = (-1.0/n)*p;
        //then e at the power of all-of-the-above (e^[-1/n times the log probability of the word sequence])
        double pp = Math.exp(exponent);

        return pp;
    }

    /**
     * A utility method to chunk up a string into letter n-grams of size n.
     * @param term The input string
     * @param n The size of letter chunks
     * @return All (overlapping) letter n-grams
     */
    public static String [] getNgrams(String term, int n) {
        List<String> result = new ArrayList<>();
        for (int i = 0, j = n; i < term.length(); i++, j++){
            int endIdx = j <= term.length() ? j : term.length();
            String ngram = term.substring(i,endIdx);
            result.add(ngram);
        }

        return result.toArray(new String[0]);
    }

}

