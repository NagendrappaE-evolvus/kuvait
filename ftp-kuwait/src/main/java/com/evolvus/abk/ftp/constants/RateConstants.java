package com.evolvus.abk.ftp.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface RateConstants {

	
	List<HashMap<String, String>> RATE_HEADERS = new ArrayList<HashMap<String, String>>() {
		{
			add(0, new HashMap<String, String>() {
				{
					put("code", "31_DAYS");
					put("desc", "31 Days");
					put("unit", "DAYS");
					put("from", "0");
					put("to", "31");
					put("keyCodeRef","UPTO_31D");
				}
			});
			
			add(1, new HashMap<String, String>() {

				{
					put("code", "91_DAYS");
					put("desc", "91 Days");
					put("unit", "DAYS");
					put("from", "32");
					put("to", "91");
					put("keyCodeRef","1_3MONTHS");
				}
				
			});
			
			add(2, new HashMap<String, String>() {

				{
					put("code", "183_DAYS");
					put("desc", "183 Days");
					put("unit", "DAYS");
					put("from", "92");
					put("to", "183");
					put("keyCodeRef","3_6MONTHS");
				}
				
			});


			add(3, new HashMap<String, String>() {

				{
					put("code", "365_DAYS");
					put("desc", "365 Days");
					put("unit", "DAYS");
					put("from", "184");
					put("to", "365");
					put("keyCodeRef","6MN_1YR");
				}
				
			});

			add(4, new HashMap<String, String>() {

				{
					put("code", "790_DAYS");
					put("desc", "790 Days");
					put("unit", "MONTHS");
					put("from", "366");
					put("to", "790");
					put("keyCodeRef","1_2YEAR");
				}
				
			});
			
			add(5, new HashMap<String, String>() {

				{
					put("code", "1095_DAYS");
					put("desc", "1095 Days");
					put("unit", "DAYS");
					put("from", "791");
					put("to", "1095");
					put("keyCodeRef","2_3YEAR");
				}
				
			});
			
			add(6, new HashMap<String, String>() {

				{
					put("code", "1825_DAYS");
					put("desc", "1825 Days");
					put("unit", "DAYS");
					put("from", "1096");
					put("to", "1825");
					put("keyCodeRef","3_5YEAR");
				}
				
			});
			
			add(7, new HashMap<String, String>() {

				{
					put("code", "1825_DAYS");
					put("desc", "1825 Days");
					put("unit", "DAYS");
					put("from", "1825");
					put("to", "1825");
					put("keyCodeRef","above_5");
				}
				
			});
		}
		
	};
}
