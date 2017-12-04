package com.evolvus.abk.ftp.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MarginCurveExtConstants {
	Integer MRGN_CURVE_EX_MAX_CELL = 71;

	List<HashMap<String, String>> MRGN_CURVE_EX_HEADERS = new ArrayList<HashMap<String, String>>() {
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
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
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
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "USD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*EUR*/
			add(16, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
					put("ccy", "EUR");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "EUR");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*GBP*/
			add(24, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "GBP");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*CHF*/
			add(32, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "CHF");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*JPY*/
			add(40, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "JPY");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*AUD*/
			add(48, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "AUD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*CAD*/
			add(56, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "CAD");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});
			/*AED*/
			add(64, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "TENOR_BUCKET");
					put("code", "DEC_14_UPTO_31D");
					put("desc", "Dec 2014 Upto 31D");
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
					put("code", "DEC_14_32_91D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_92_183D");
					put("desc", "Dec 2014 32D - 91D");
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
					put("code", "DEC_14_184_365D");
					put("desc", "Dec 2014 184D - 365D");
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
					put("code", "DEC_14_366_730D");
					put("desc", "Dec 2014 366D - 730D");
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
					put("code", "DEC_14_731_1095D");
					put("desc", "Dec 2014 731D - 1095D");
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
					put("code", "DEC_14_1096_1825D");
					put("desc", "Dec 2014 1096D - 1825D");
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
					put("code", "DEC_14_GT_1826D");
					put("desc", "Dec 2014 1825D and Above");
					put("ccy", "AED");
					put("fromUnit", "DAY");
					put("toUnit", "DAY");
					put("from", "1826");
					put("to", "9999");
				}
			});

		}
	};

}
