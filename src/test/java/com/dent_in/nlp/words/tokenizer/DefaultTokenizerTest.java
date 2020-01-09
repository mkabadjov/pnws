package com.dent_in.nlp.words.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultTokenizerTest {

    @Test
    public void tokenizeEnglish() {
        Tokenizer tokenizer = new TokenizerFactory().createInstance(Tokenizer.TYPE.DEFAULT);

        //Tests on number of tokens
        assertEquals(0, tokenizer.tokenize("").size());
        assertEquals(5, tokenizer.tokenize("REGISTRY OF ANTIQUITY LTD.").size());
        assertEquals(3, tokenizer.tokenize("RUG GALLERY ORIGINALS").size());
        assertEquals(1, tokenizer.tokenize("RENOVAC").size());
        assertEquals(2, tokenizer.tokenize("MONTE CARLO").size());
        assertEquals(3, tokenizer.tokenize("SWING A WAY").size());
        assertEquals(6, tokenizer.tokenize("THE MASTER'S INN INCORPORATED").size());
        assertEquals(1, tokenizer.tokenize("FI").size());
        assertEquals(3, tokenizer.tokenize("FLOSS-WAND").size());
        assertEquals(7, tokenizer.tokenize("ORIGINAL NEW YORK CORNED BEEF CO.").size());
        assertEquals(10, tokenizer.tokenize("C'MON, DO SOMETHING NICE FOR YOU TODAY").size());
        assertEquals(3, tokenizer.tokenize("BLO-KAP").size());
        assertEquals(5, tokenizer.tokenize("BUCKS COUNTY 1776-1976").size());
        assertEquals(2, tokenizer.tokenize("OZON II").size());
        assertEquals(7, tokenizer.tokenize("MARKETPLACE SERVICES, INC. HODIE PORRO").size());
        assertEquals(5, tokenizer.tokenize("SPIN-A-METER").size());
        assertEquals(5, tokenizer.tokenize("DONUTS \"N\" CREAM").size());
        assertEquals(2, tokenizer.tokenize("SYSTEM 88").size());
        assertEquals(2, tokenizer.tokenize("RALLY!").size());
        assertEquals(3, tokenizer.tokenize("\"K\"").size());
        assertEquals(4, tokenizer.tokenize("FRESH 'N FLUFFY").size());
        assertEquals(4, tokenizer.tokenize("E-Z KARE").size());
        assertEquals(3, tokenizer.tokenize("INSTA PAY $").size());
        assertEquals(3, tokenizer.tokenize("SMILIN' PEACH").size());
        assertEquals(3, tokenizer.tokenize("MR. PEPPER").size());
        assertEquals(3, tokenizer.tokenize("12/12").size());
        assertEquals(3, tokenizer.tokenize("HABEEBA'S").size());
        assertEquals(5, tokenizer.tokenize("TREMOR-BLEN-D").size());
        assertEquals(4, tokenizer.tokenize("PLASTI CO2 COLD").size());
        assertEquals(2, tokenizer.tokenize("NH36").size());

        //Tests on specific tokens
        assertEquals("INN", tokenizer.tokenize("THE MASTER'S INN INCORPORATED").get(4).getToken());
        assertEquals("WAND", tokenizer.tokenize("FLOSS-WAND").get(2).getToken());
        assertEquals("DO", tokenizer.tokenize("C'MON, DO SOMETHING NICE FOR YOU TODAY").get(4).getToken());
        assertEquals("METER", tokenizer.tokenize("SPIN-A-METER").get(4).getToken());
        assertEquals("CREAM", tokenizer.tokenize("DONUTS \"N\" CREAM").get(4).getToken());
        assertEquals("MR", tokenizer.tokenize("MR. PEPPER").get(0).getToken());
        assertEquals("12", tokenizer.tokenize("12/12").get(0).getToken());
        assertEquals("12", tokenizer.tokenize("12/12").get(2).getToken());
        assertEquals("CO", tokenizer.tokenize("PLASTI CO2 COLD").get(1).getToken());
        assertEquals("36", tokenizer.tokenize("NH36").get(1).getToken());


        //Tests on token offsets
        assertEquals(13, tokenizer.tokenize("THE MASTER'S INN INCORPORATED").get(4).getStartOffset());
        assertEquals(6, tokenizer.tokenize("FLOSS-WAND").get(2).getStartOffset());
        assertEquals(9, tokenizer.tokenize("C'MON, DO SOMETHING NICE FOR YOU TODAY").get(4).getEndOffset());
        assertEquals(12, tokenizer.tokenize("SPIN-A-METER").get(4).getEndOffset());
        assertEquals(11, tokenizer.tokenize("DONUTS \"N\" CREAM").get(4).getStartOffset());
        assertEquals(0, tokenizer.tokenize("MR. PEPPER").get(0).getStartOffset());
        assertEquals(2, tokenizer.tokenize("12/12").get(0).getEndOffset());
        assertEquals(5, tokenizer.tokenize("12/12").get(2).getEndOffset());
        assertEquals(7, tokenizer.tokenize("PLASTI CO2 COLD").get(1).getStartOffset());
        assertEquals(2, tokenizer.tokenize("NH36").get(1).getStartOffset());

        //Tokenise a File
        String testFileName = ClassLoader.getSystemResource("TrademarkDataTest.txt").getFile();
        File file = new File(testFileName);
        assertEquals(true, file.exists());
        assertEquals(1603, tokenizer.tokenize(file).size());
    }
}
