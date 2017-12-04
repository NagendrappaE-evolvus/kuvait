package com.evolvus.abk.ftp.constants;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public interface CcyCurveConstants {
	String EXCEL_DATE_READER_FORMAT="MM/dd/yy";
	Integer ONE = 1;
	Integer CCY_CRV_MAX_CELL = 13;

	List<HashMap<String, String>> CCY_CURVE_HEADERS = new ArrayList<HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2845929251698528642L;
		{
			add(0, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -8321269609531288782L;

				{
					put("code", "31_DAYS");
					put("desc", "31 Days");
					put("column","");
					put("unit", "DAYS");
					put("from", "0");
					put("to", "31");
					put("keyCodeRef","UPTO_31D");
				}

			});
			add(1, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3266156267964037496L;

				{
					put("code", "32_DAYS");
					put("desc", "32 Days");
					put("column","");
					put("unit", "DAYS");
					put("from", "32");
					put("to", "32");
					put("keyCodeRef","1_3MONTHS");
				}

			});
			add(2, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -7777868091648190631L;

				{
					put("code", "91_DAYS");
					put("desc", "91 Days");
					put("column","");
					put("unit", "DAYS");
					put("from", "33");
					put("to", "91");
					put("keyCodeRef","1_3MONTHS");
				}

			});
			add(3, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6368574671251919470L;

				{
					put("code", "92_DAYS");
					put("desc", "92 Days");
					put("column","");
					put("unit", "DAYS");
					put("from", "92");
					put("to", "92");
					put("keyCodeRef","1_3MONTHS");
				}
			});
			add(4, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -1772807873528419865L;
				{
					put("code", "183_DAYS");
					put("desc", "183 Days");
					put("column","");
					put("unit", "DAYS");
					put("from", "93");
					put("to", "183");
					put("keyCodeRef","3_6MONTHS");
				}
			});

			add(5, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 5076266454904689568L;

				{
					put("code", "184_DAYS");
					put("desc", "184 Days");
					put("unit", "DAYS");
					put("from", "184");
					put("to", "184");
					put("keyCodeRef","3_6MONTHS");
				}
			});

			add(6, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2556136305250848966L;

				{
					put("code", "365_DAYS");
					put("desc", "365 Days");
					put("unit", "DAYS");
					put("from", "185");
					put("to", "365");
					put("keyCodeRef","6MN_1YR");
				}
			});

			add(7, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4711848605369248461L;

				{
					put("code", "366_DAYS");
					put("desc", "366 Days");
					put("unit", "DAYS");
					put("from", "366");
					put("to", "366");
					put("keyCodeRef","6MN_1YR");
				}
			});

			add(8, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3049845397586172633L;

				{
					put("code", "24_MONTHS");
					put("desc", "24 Months");
					put("unit", "MONTHS");
					put("from", "12");
					put("to", "24");
					put("keyCodeRef","2_3YEAR");
				}
			});

			add(9, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 588372506435628438L;

				{
					put("code", "25_MONTHS");
					put("desc", "25 Months");
					put("unit", "MONTHS");
					put("from", "25");
					put("to", "25");
					put("keyCodeRef","2_3YEAR");
				}
			});

			add(10, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4240314533021797520L;

				{
					put("code", "36_MONTHS");
					put("desc", "36 Months");
					put("unit", "MONTHS");
					put("from", "26");
					put("to", "36");
					put("keyCodeRef","2_3YEAR");
				}
			});

			add(11, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 7840550053088248934L;

				{
					put("code", "37_MONTHS");
					put("desc", "37 Months");
					put("unit", "MONTHS");
					put("from", "37");
					put("to", "37");
					put("keyCodeRef","3_5YEAR");
				}
			});

			add(12, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 6287642317863595073L;

				{
					put("code", "60_MONTHS");
					put("desc", "60 Months");
					put("unit", "MONTHS");
					put("from", "38");
					put("to", "60");
					put("keyCodeRef","3_5YEAR");
				}
			});

			add(13, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3918513125312746494L;
				{
					put("code", "61_MONTHS");
					put("desc", "61 Months");
					put("unit", "MONTHS");
					put("from", "61");
					put("to", "99999");
					put("keyCodeRef","GT_5_YR");
				}
			});

		}
	};

}
