package com.dent_in.nlp.lexicon;

import com.dent_in.nlp.lexicon.pdist.ProbabilityDistribution;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProbabilityDistributionTest {

    /**
     * Computes unigram and bigram probabilites of various sequences of words.
     * Note strictly speaking these are unnormalized probabilities, hence, not really probabilities.
     * However, this follows Peter Norvig's conventions as it works well in practice
     * (and is more efficient to compute as it skips a normalization step).
     */
    @Test
    public void computeProbabilities() throws URISyntaxException {
        //Setup
        URI uri = ClassLoader.getSystemResource("count_1w.txt").toURI();
        String unigramDataFileName = Paths.get(uri).toString();

        uri = ClassLoader.getSystemResource("count_2w.txt").toURI();
        String bigramDataFileName = Paths.get(uri).toString();

        String totalNumberOfTokens = "1024908267229";
        Long googleNGramCorpusSize = new Long(totalNumberOfTokens);
        double delta = 0.001;
        ProbabilityDistribution pDistribution = new ProbabilityDistribution(unigramDataFileName, bigramDataFileName, googleNGramCorpusSize.longValue());

        //Tests
        String[] words = {"sports", "wear"};
        double pUnigram = pDistribution.getProbability(words); //unigram
        double pBigram = pDistribution.getProbability2(words); //bigram
        assertEquals(0.000000006, pUnigram, delta);
        assertEquals(4050.914, pBigram, delta);

        String[] words2 = {"peak", "view"};
        pUnigram = pDistribution.getProbability(words2); //unigram
        pBigram = pDistribution.getProbability2(words2); //bigram
        assertEquals(0.000000014, pUnigram, delta);
        assertEquals(26912.778, pBigram, delta);

        String[] words3 = {"road", "pacer"};
        pUnigram = pDistribution.getProbability(words3); //unigram
        pBigram = pDistribution.getProbability2(words3); //bigram
        assertEquals(0.00000000005, pUnigram, delta);
        assertEquals(29.847, pBigram, delta);

        String[] words4 = {"trans", "world"};
        pUnigram = pDistribution.getProbability(words4); //unigram
        pBigram = pDistribution.getProbability2(words4); //bigram
        assertEquals(0.000000005, pUnigram, delta);
        assertEquals(12044.373, pBigram, delta);

        String[] words5 = {"fire", "dog"};
        pUnigram = pDistribution.getProbability(words5); //unigram
        pBigram = pDistribution.getProbability2(words5); //bigram
        assertEquals(0.000000006, pUnigram, delta);
        assertEquals(6354.482, pBigram, delta);

        String[] words6 = {"ever", "soft"};
        pUnigram = pDistribution.getProbability(words6); //unigram
        pBigram = pDistribution.getProbability2(words6); //bigram
        assertEquals(0.000000003, pUnigram, delta);
        assertEquals(4176.185, pBigram, delta);

    }

    /**
     * Computes log probabilities for letter n-grams.
     * @throws URISyntaxException If any of the resources are not found
     */
    @Test
    public void computeLogProbabilities() throws URISyntaxException {
        //Setup
        URI uri = ClassLoader.getSystemResource("count_2l.txt").toURI();
        String charBigramDataFileName = Paths.get(uri).toString();
        uri = ClassLoader.getSystemResource("count_3l.txt").toURI();
        String charTrigramDataFileName = Paths.get(uri).toString();
        String numberOfTokens = "6670825274245"; //letter bigram count
        ProbabilityDistribution pDistribution = new ProbabilityDistribution(charBigramDataFileName, charTrigramDataFileName, (new Long(numberOfTokens).longValue()), true, 1);
        ProbabilityDistribution pDistribution3gram = new ProbabilityDistribution(charTrigramDataFileName);

        double delta = 0.001;

        //Tests
        String[] ngrams = ProbabilityDistribution.getNgrams("xerox", 2);
        double pUnigram = pDistribution.getLogProbability(ngrams); //2-letter unigrams
        double pBigram = pDistribution.getLogProbability2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("xerox", 3);
        double pTrigram = pDistribution3gram.getLogProbability(ngrams); //3-letter unigrams
        assertEquals(-55.168, pUnigram, delta);
        assertEquals(-48.763, pBigram, delta);
        assertEquals(-89.07, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("hewlett-packard", 2);
        pUnigram = pDistribution.getLogProbability(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogProbability2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("hewlett-packard", 3);
        pTrigram = pDistribution3gram.getLogProbability(ngrams); //3-letter unigrams
        assertEquals(-163.0978, pUnigram, delta);
        assertEquals(-129.157, pBigram, delta);
        assertEquals(-246.38, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("about", 2);
        pUnigram = pDistribution.getLogProbability(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogProbability2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("about", 3);
        pTrigram = pDistribution3gram.getLogProbability(ngrams); //3-letter unigrams
        assertEquals(-52.276, pUnigram, delta);
        assertEquals(-39.985, pBigram, delta);
        assertEquals(-82.287, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("roadpacer", 2);
        pUnigram = pDistribution.getLogProbability(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogProbability2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("roadpacer", 3);
        pTrigram = pDistribution3gram.getLogProbability(ngrams); //3-letter unigrams
        assertEquals(-74.517, pUnigram, delta);
        assertEquals(-53.888, pBigram, delta);
        assertEquals(-120.797, pTrigram, delta);

    }

    /**
     * Computes log perplexity for letter ngrams.
     *
     * @throws URISyntaxException If any of the resources are not found
     */
    @Test
    public void computeLogPerplexities() throws URISyntaxException {
        //Setup
        URI uri = ClassLoader.getSystemResource("count_2l.txt").toURI();
        String charBigramDataFileName = Paths.get(uri).toString();
        uri = ClassLoader.getSystemResource("count_3l.txt").toURI();
        String charTrigramDataFileName = Paths.get(uri).toString();
        String numberOfTokens = "6670825274245"; //letter bigram count
        ProbabilityDistribution pDistribution = new ProbabilityDistribution(charBigramDataFileName, charTrigramDataFileName, (new Long(numberOfTokens).longValue()), true, 1);
        ProbabilityDistribution pDistribution3gram = new ProbabilityDistribution(charTrigramDataFileName);

        double delta = 0.001;

        //Tests
        String[] ngrams = ProbabilityDistribution.getNgrams("xerox", 2);
        double pUnigram = pDistribution.getLogPerplexity(ngrams); //2-letter unigrams
        double pBigram = pDistribution.getLogPerplexity2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("xerox", 3);
        double pTrigram = pDistribution3gram.getLogPerplexity(ngrams); //3-letter unigrams
        assertEquals(61920.623, pUnigram, delta);
        assertEquals(17201.091, pBigram, delta);
        assertEquals(5.45235553729453E7, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("hewlett-packard", 2);
        pUnigram = pDistribution.getLogPerplexity(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogPerplexity2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("hewlett-packard", 3);
        pTrigram = pDistribution3gram.getLogPerplexity(ngrams); //3-letter unigrams
        assertEquals(52743.144, pUnigram, delta);
        assertEquals(5489.173, pBigram, delta);
        assertEquals(1.3596697679759884E7, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("about", 2);
        pUnigram = pDistribution.getLogPerplexity(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogPerplexity2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("about", 3);
        pTrigram = pDistribution3gram.getLogPerplexity(ngrams); //3-letter unigrams
        assertEquals(34726.354, pUnigram, delta);
        assertEquals(2972.254, pBigram, delta);
        assertEquals(1.4041443366378972E7, pTrigram, delta);

        ngrams = ProbabilityDistribution.getNgrams("roadpacer", 2);
        pUnigram = pDistribution.getLogPerplexity(ngrams); //2-letter unigrams
        pBigram = pDistribution.getLogPerplexity2(ngrams); //2-letter bigrams
        ngrams = ProbabilityDistribution.getNgrams("roadpacer", 3);
        pTrigram = pDistribution3gram.getLogPerplexity(ngrams); //3-letter unigrams
        assertEquals(3943.176, pUnigram, delta);
        assertEquals(398.444, pBigram, delta);
        assertEquals(674645.029, pTrigram, delta);
    }

}
