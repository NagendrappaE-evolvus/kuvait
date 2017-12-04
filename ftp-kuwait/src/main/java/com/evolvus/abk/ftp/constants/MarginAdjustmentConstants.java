package com.evolvus.abk.ftp.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MarginAdjustmentConstants {
	Integer MRGN_ADJ_MAX_CELL = 98;

	List<HashMap<String, String>> ADJSTMNT_TEMP_HEADERS = new ArrayList<HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			/* KWD */
			add(0, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(1, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(2, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(3, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(4, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(5, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(6, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(7, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "KWD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*USD*/
			add(8, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(9, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(10, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(11, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(12, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(13, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(14, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(15, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1825D");
					put("desc", "1825D and Above");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/* EUR */
			add(16, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(17, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(18, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(19, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(20, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(21, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(22, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(23, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/* GBP */
			add(24, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(25, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(26, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(27, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(28, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(29, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(30, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(31, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			
			/* GBP */
			add(32, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(33, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(34, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(35, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(36, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(37, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(38, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(39, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			
			/* GBP */
			add(40, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(41, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(42, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(43, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(44, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(45, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(46, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(47, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/* AUD */
			add(48, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(49, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(50, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(51, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(52, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(53, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(54, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(55, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			
			/* CAD */
			add(56, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(57, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(58, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(59, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(60, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(61, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(62, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(63, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			
			/* AED */
			add(64, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "UPTO_31D");
					put("desc", "Upto 31 Days");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "0");
					put("to", "31");
				}
			});
			add(65, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "32_91D");
					put("desc", "32D - 91D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "32");
					put("to", "91");
				}
			});
			add(66, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "92_183D");
					put("desc", "92D - 183D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "92");
					put("to", "183");
				}
			});
			add(67, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "184_365D");
					put("desc", "184D - 365D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "184");
					put("to", "365");
				}
			});
			add(68, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "366_730D");
					put("desc", "366D - 730D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "366");
					put("to", "730");
				}
			});
			add(69, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "731_1095D");
					put("desc", "731D - 1095D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "731");
					put("to", "1095");
				}
			});
			add(70, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "1096_1825D");
					put("desc", "1096D - 1825D");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1096");
					put("to", "1825");
				}
			});
			add(71, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "GT_1826D");
					put("desc", "1826D and Above");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			
			/*DEC 2014 3-5 Yr*/
			
			add(72, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "KWD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(73, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "USD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(74, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "EUR");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(75, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "AED");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(76, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "GBP");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(77, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "CHF");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(78, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "JPY");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(79, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "AUD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(80, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_3_5YR");
					put("desc", "Dec 2014 3-5yr");
					put("ccy", "CAD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			/*Dec 2014 2-3yr*/
			add(81, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "KWD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(82, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "USD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(83, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "EUR");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(84, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "GBP");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(85, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "CHF");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(86, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "JPY");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(87, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "AUD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(88, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "CAD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			add(89, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("ccy", "AED");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "2");
					put("to", "3");
				}
			});
			/*Dec 2014  >5yr*/
			add(90, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "KWD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(91, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "USD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(92, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "EUR");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(93, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "AED");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(94, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "GBP");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(95, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "CHF");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(96, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "JPY");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(97, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "AUD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
			add(98, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_2014_GT_5YR");
					put("desc", "Dec 2014 >5yr");
					put("ccy", "CAD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "5");
					put("to", "999");
				}
			});
		}
	};

}
