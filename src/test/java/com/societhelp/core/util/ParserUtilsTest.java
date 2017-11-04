package com.societhelp.core.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ParserUtilsTest {

	@Test
	public void testHasCharNum() {

		assertFalse(ParserUtils.hasCharNum("Jony"));
		assertTrue(ParserUtils.hasCharNum("Jony123"));
		assertTrue(ParserUtils.hasCharNum("123"));
	}

	@Test
	public void testIsInvalidWord() {
		for (String testStr : ParserUtils.INVALID_STRINGS_IN_DES) {
			assertTrue(ParserUtils.isInvalidWord(testStr));
		}
	}

	@Test
	public void testHasSpecialChar() {
		assertFalse(ParserUtils.hasSpecialChar("Value"));
		assertTrue(ParserUtils.hasSpecialChar("(Value"));
		assertTrue(ParserUtils.hasSpecialChar("(Date:05/08/2011)"));
	}

	@Test
	public void testGetNameFromDescription() {

		/*
		 * "=""NEFT N105110400414065 VINOD KUMAR A"""
		 * "=""TO CLG MANAS KUMAR DASH"""
		 * "=""IB: FUND TRANSFER FROM JACOB  ALEXANDER"""
		 * "=""IB:SI FUND TRANSFER FROM MANENDRA PRASAD SINGH"""
		 * "=""IMPS from BENNY M J   Ref 128622112848"""
		 * "=""IMPS from ADITYA BHAM Ref 124801108116 (Value Date:05/08/2011)"""
		 * "=""115:MICR INWARD 5:TO CLG DANIEL L"""
		 * "=""MB:FUND TRANSFER FROM ABHINAV NIGAM"""
		 */
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader("src/test/resources/kbank-statement-description.csv"));
			Assert.assertEquals("VINOD KUMAR A", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("MANAS KUMAR DASH", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("JACOB ALEXANDER", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("MANENDRA PRASAD SINGH", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("BENNY M J", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("ADITYA BHAM", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("DANIEL L", ParserUtils.getNameFromDescription(r.readLine()));
			Assert.assertEquals("ABHINAV NIGAM", ParserUtils.getNameFromDescription(r.readLine()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
