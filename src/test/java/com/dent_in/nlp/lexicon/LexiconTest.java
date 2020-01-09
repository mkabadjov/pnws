package com.dent_in.nlp.lexicon;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LexiconTest {

    @Test
    public void testCorpusLexicon() {

        CorpusLexicon lexicon = (CorpusLexicon) new LexiconFactory().createInstance(Lexicon.TYPE.CORPUSLEXICON);
        String testFileName = ClassLoader.getSystemResource("frequencyDictionaryExcerpt.txt").getFile();
        //lexicon.load("src/test/resources/frequencyDictionaryExcerpt.txt");
        lexicon.load(testFileName);

        String word = "abbot";
        Long count = new Long(213200);
        Long zeroCount = new Long(0);
        String nonWord = "crrrsssstttt";
        Long vocabularySize = new Long(1010);
        Long corpusSize = new Long("6433959798");
        Long min = new Long(2);
        Long max = new Long("1153305349");
        Double wordLL = new Double("8.705432133667752");

        //check word existence
        assertEquals(true, lexicon.containsWord(word));
        assertNotEquals(true, lexicon.containsWord(nonWord));
        //check word counts
        assertEquals(count, lexicon.getFrequency(word));
        assertEquals(min, lexicon.getFrequency(nonWord));
        //check word log-likelihood
        assertEquals(wordLL, lexicon.getLogLikelihood(word));
        //check vocabulary size
        assertEquals(vocabularySize, lexicon.getVocabularySize());
        //check corpus size
        assertEquals(corpusSize, lexicon.getCorpusSize());
        //check range of frequencies
        assertEquals(min, lexicon.getMinFrequency());
        assertEquals(max, lexicon.getMaxFrequency());


    }

}