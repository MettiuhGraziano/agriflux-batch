package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.Random;

public class DatiProcessor {
	
	private static final String DELIMITER = "|";
	private static Random random = new Random();
	
	protected static BigDecimal generaRandomBigDecimalFromRange(String range) {

		int delimiterIndex = range.indexOf(DELIMITER);
		String rangeMin = range.substring(0, delimiterIndex);
		String rangeMax = range.substring(delimiterIndex + 1);
		
		double min = 0;
		double max = 0;
		
		if (null != rangeMin && null != rangeMax) {
			min = Double.parseDouble(rangeMin);
			max = Double.parseDouble(rangeMax);
		}
		
		double randomValue = random.nextDouble(min, max);
		
		return BigDecimal.valueOf(randomValue);
	}
	
	protected static BigDecimal generaRandomBigDecimalFromRange(double min, double max) {

		double randomValue = random.nextDouble(min, max);
		
		return BigDecimal.valueOf(randomValue);
	}
	
	protected static int generaRandomIntFromRange(int min, int max) {
		return random.nextInt(min, max);
	}
}
