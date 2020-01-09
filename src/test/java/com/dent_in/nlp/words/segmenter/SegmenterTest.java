package com.dent_in.nlp.words.segmenter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SegmenterTest {

    private static final Logger logger = LoggerFactory.getLogger(SegmenterTest.class);

    @Test
    public void segmentTerms() throws URISyntaxException {
        long currentTime = System.currentTimeMillis();
        logger.info("Starting up timing at " + currentTime + "ms...");

        URI uri = ClassLoader.getSystemResource("count_1w.txt").toURI();
        String unigramDataFileName = Paths.get(uri).toString();
        uri = ClassLoader.getSystemResource("count_2w.txt").toURI();
        String bigramDataFileName = Paths.get(uri).toString();
        String totalNumberOfTokens = "1024908267229";
        //no need for corpus lexicon in the splitter (i.e., 2nd parameter)
        Splitter splitter = new SegmenterFactory().createSplitter(Splitter.type.EXHAUSTIVE, null);
        SegmentationScorer scorer = new SegmenterFactory().createSegmentationScorer(unigramDataFileName, bigramDataFileName, totalNumberOfTokens, SegmentationScorer.type.BAYES_UNIGRAM);
        Segmenter segmenter = new SegmenterFactory().createSegmenter(Segmenter.type.NORVIG, splitter, scorer);
        SegmentationScorer scorer2 = new SegmenterFactory().createSegmentationScorer(unigramDataFileName, bigramDataFileName, totalNumberOfTokens, SegmentationScorer.type.BAYES_BIGRAM);
        Segmenter segmenter2 = new SegmenterFactory().createSegmenter(Segmenter.type.NORVIG, splitter, scorer2);

        logger.info("TIME ELAPSED LOADING RESOURCES: " + (System.currentTimeMillis() - currentTime) + "ms.");
        currentTime = System.currentTimeMillis();

                //Examples from Norvig's chapter:
        //choosespain - choose spain
        //thisisatest - this is a test
        //expertsexchange - experts exchange
        //speedofart - speed of art
        //tositdownon
        //    unigram-based: to sitdown on
        //    bigram-based: to sit down on
        //smallunregardedsun - small un regarded sun
        //  this one needs the full 13-million-word google unigram corpus to work (which includes entry: >>> Pw['unregarded'] = 7557)

        //TMs
        //sportswear
        //  unigram-based: sportswear
        //  bigram-based: sports wear
        // PEAKVIEW
        // ROADPACER
        //from my log Nov 8th
        //TRANSWORLD
        //FIREDOG
        //EVERSOFT
        //ZYKADIA
        //BOLOBULU
        //NEBUSAL


        String term = "choosespain"; // choose spain
        Segmentation segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("choose", segmentation.getParts().get(0).getContent().getToken());
//        System.out.println(segmentation.getProbability());
        //Test segment2() / add corresponding assertEquals() for the example below
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("choose", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        term = "thisisatest"; //this is a test
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("test", segmentation.getParts().get(3).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("test", segmentation.getParts().get(3).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        term = "expertsexchange"; // experts exchange
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("experts", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("experts", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        term = "speedofart"; //speed of art
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("art", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("art", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        term = "tositdownon"; //unigram: to sitdown on - bigram: to sit down on
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size()); //wrong segmentation
        assertEquals("sitdown", segmentation.getParts().get(1).getContent().getToken());
        assertEquals("on", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 -  correct segmentation
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("sit", segmentation.getParts().get(1).getContent().getToken());
        assertEquals("down", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        term = "smallunregardedsun"; //wrong segmentation example, adding unregarded to the unigram lexicon solves it
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("un", segmentation.getParts().get(1).getContent().getToken());
        assertEquals("regarded", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("un", segmentation.getParts().get(1).getContent().getToken());
        assertEquals("regarded", segmentation.getParts().get(2).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        //////////////////////////////// TRADEMARKS /////////////////////////////////////////////

        term = "sportswear"; //unigram: sportswear - bigram: sports wear
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(1, segmentation.getParts().size());
        assertEquals("sportswear", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("sports", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        // PEAKVIEW
        term = "peakview"; //peak view
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("view", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("peak", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        // ROADPACER
        term = "roadpacer"; //road pacer
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("pacer", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("road", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //TRANSWORLD
        term = "transworld"; //unigram: transworld - bigram: trans world
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(1, segmentation.getParts().size());
        assertEquals("transworld", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("world", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //FIREDOG
        term = "firedog"; //fire dog
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("dog", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("fire", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //EVERSOFT
        term = "eversoft"; //ever soft
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("soft", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(2, segmentation.getParts().size());
        assertEquals("ever", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //System.out.println("--");

        //////////// Potentially problematic cases //////////////////////
        /////////// examples of wrong segmentation ! Should not segment
        //ZYKADIA
        term = "zykadia";
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("kadi", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(4, segmentation.getParts().size());
        assertEquals("z", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //BOLOBULU
        term = "bolobulu";
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("b", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("bo", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //NEBUSAL
        term = "nebusal";
        segmentation = segmenter.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("bus", segmentation.getParts().get(1).getContent().getToken());
        //System.out.println(segmentation.getProbability());
        //segment2 - bigram model segments (sports)(wear) as expected !
        segmentation = segmenter2.getBestSegmentation(term);
        assertEquals(3, segmentation.getParts().size());
        assertEquals("ne", segmentation.getParts().get(0).getContent().getToken());
        //System.out.println(segmentation.getProbability());

        logger.info("TIME ELAPSED SEGMENTING: " + (System.currentTimeMillis() - currentTime) + " ms.");

    }
}
