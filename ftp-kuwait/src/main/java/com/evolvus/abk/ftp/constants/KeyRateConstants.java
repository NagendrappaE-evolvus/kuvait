package com.evolvus.abk.ftp.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface KeyRateConstants {
	Integer KEY_RATE_MAX_CELL = 51;

	List<HashMap<String, String>> KEY_RATES_HEADERS = new ArrayList<HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			/*MARGIN KWD AND FC*/
			add(0, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column", "0% Rate or Margin");
					put("code", "0_PR_RT_MRGN");
					put("desc", "0% Rate or Margin");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			add(1, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column", "1% Margin");
					put("code", "1_PR_MRGN");
					put("desc", "1% Margin");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(2, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column", ".05 Margin");
					put("code", "_05_PR_MRGN");
					put("desc", "0.05% Margin");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(3, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("column","Margin KWD Term & Others Good");
					put("code", "MRGN_TRM_OS_GD");
					put("desc", "Margin Term & Others Good");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(4, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("column","Margin FC Term & Oth Good");
					put("code", "MRGN_TRM_OS_GD");
					put("desc", "Margin Term & Oth Good");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});

			add(5, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("column","Margin KWD Term & Others Rem");
					put("code", "MRGN_TRM_OS_REM");
					put("desc", " Margin Term & Others Rem");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(6, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column","Margin FC Term & Others Rem");
					put("code", "MRGN_TRM_OS_REM");
					put("desc", " Margin Term & Others Rem");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(7, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column","Margin Loans against FD");
					put("code", "MRGN_LOAN_AG_FD");
					put("desc", "Margin Loans against FD");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});

			add(8, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column","Margin KWD Banks");
					put("code", "MRGN_BNK");
					put("desc", "Margin Banks");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(9, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("column","Margin FC Banks");
					put("code", "MRGN_BNK");
					put("desc", "Margin Banks");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});

			
			add(10, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("column","Margin KWD MNC & Sov");
					put("code", "MRGN_MNC_SOV");
					put("desc", "Margin MNC & Sov");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(11, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_MNC_SOV");
					put("column","Margin FC MNC & Sov");
					put("desc", "Margin MNC & Sov");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(12, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_CBD_GD");
					put("column","Margin KWD Time CBD Good");
					put("desc", "Margin Time CBD Good");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});

			add(13, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_CBD_GD");
					put("column","Margin FC Time CBD Good");
					put("desc", "Margin Time CBD Good");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(14, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("column","Margin KWD Time CBD Rem");
					put("code", "MRGN_TM_CBD_REM");
					
					put("desc", "Margin Time CBD Rem");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(15, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_CBD_REM");
					put("desc", "Margin Time CBD Rem");
					put("column","Margin FC Time CBD Rem");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});

			add(16, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_IBD");
					put("column","Margin KWD Time IBD");
					put("desc", "Margin Time IBD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(17, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_IBD");
					put("desc", "Margin Time IBD");
					put("column","Margin FC Time IBD");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(18, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_RBD");
					put("desc", "Margin Time RBD");
					put("column","Margin KWD Time RBD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(19, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_TM_RBD");
					put("desc", "Margin Time RBD");
					put("column","Margin FC Time RBD");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(20, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "IBB_TRSRY");
					put("desc", "IBB Treasury");
					put("column", "IBB Treasury");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(21, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "INVST_BONDS");
					put("code", "MINV_BND_14");
					put("desc", "Margin Invest Bonds 2014");
					put("column", "Margin Invest Bonds  2014 KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			
			add(22, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "INVST_BONDS");
					put("code", "MINV_BND_14");
					put("desc", "Margin Invest Bonds 2014");
					put("column", "Margin Invest Bonds 2014 FC");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(23, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "INVST_SUB_ASOC");
					put("code", "3PINV_SUB_ASOC");
					put("desc", "3% Invest in Sub and Assoc");
					put("column", "3% Invest in Sub and Assoc");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			
			
			/*YTD 2014 3-5 Yr */
			add(24, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 Yr KWD");
					put("ccy", "KWD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			
			add(25, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5ry USD");
					put("ccy", "USD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(26, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 Yr GBP");
					put("ccy", "GBP");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(27, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 YR EUR");
					put("ccy", "EUR");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(28, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 Yr CHF");
					put("ccy", "CHF");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(29, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 YR AED");
					put("ccy", "AED");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(30, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 YR JPY");
					put("ccy", "JPY");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(31, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 YR AUD");
					put("ccy", "AUD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			add(32, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_3_5_YR");
					put("desc", "YTD 2014 3-5 Yr");
					put("column", "YTD 2014 3-5 YR CAD");
					put("ccy", "CAD");
					put("fromUnit", "YEAR");
					put("toUnit", "YEAR");
					put("from", "3");
					put("to", "5");
				}
			});
			
			/*YTD 2014 1-3 month */
			add(33, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_1_3_MON");
					put("desc", "YTD 2014 1-3 Month");
					put("column", "YTD 2014 1-3 month KWD");
					put("ccy", "KWD");
					put("fromUnit", "MONTH");
					put("toUnit", "MONTH");
					put("from", "1");
					put("to", "3");
				}
			});
			add(34, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "Y14_TENOR_BUCKET");
					put("code", "YTD_2014_1_3_MON");
					put("desc", "YTD 2014 1-3 Month");
					put("column","YTD 2014 1-3 month USD");
					put("ccy", "USD");
					put("fromUnit", "MONTH");
					put("toUnit", "MONTH");
					put("from", "1");
					put("to", "3");
				}
			});
			
			/*Margin Core*/
			add(35, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_CR_OD");
					put("desc", "Margin Core OD");
					put("column", "Margin Core OD KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(36, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_CR_LCD");
					put("desc", "Margin Core LCD");
					put("column", "Margin Core LCD KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			add(37, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_CNSMR_LNS_14");
					put("desc", "Margin Consumer Lns 2014");
					put("column", "Margin Consumer Lns 2014 KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(38, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "MARGIN");
					put("code", "MRGN_INST_LNS_14");
					put("desc", "Margin Install Lns 2014");
					put("column", "Margin Install Lns 2014 KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(39, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "RATE");
					put("code", "IRRG_LNS_BS_RT");
					put("desc", "Irreg Loans Base Rate");
					put("column", "Irreg Loans Base Rate");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(40, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "MI_LIAB");
					put("desc", "MI Liability");
					put("column", "MI Liability");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(41, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "FHD");
					put("desc", "FAHOUDI");
					put("column", "KWD FAHOUDI");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(42, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "PRVSNS");
					put("desc", "Provisions");
					put("column", "KWD Provisions");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(43, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "OTHER");
					put("code", "SVR");
					put("desc", "SVR");
					put("column", "KWD SVR");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(44, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "FHD");
					put("desc", "FAHOUDI");
					put("column", "FC FAHOUDI");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(45, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "OTHER");
					put("code", "PRVSNS");
					put("desc", "Provisions");
					put("column", "FC Provisions");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(46, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				{
					put("type", "OTHER");
					put("code", "SVR");
					put("desc", "SVR");
					put("column", "FC SVR");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(47, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "DEC_14_2_3YR");
					put("desc", "Dec 2014 2-3yr");
					put("column", "KWD Dec 2014 2-3yr");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			
			add(48, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "IRRG_OD");
					put("desc", "Irregular OD");
					put("column", "Irregular OD KWD");
					put("ccy", "KWD");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			add(49, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "OTHER");
					put("code", "IRRG_OD");
					put("desc", "Irregular OD");
					put("column", "Irregular OD FC");
					put("ccy", "FCY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
			/*Default*/
			add(50, new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("type", "DEFAULT");
					put("code", "DEFLT_RT");
					put("desc", "Default Rate");
					put("column", "Default Key Rate");
					put("ccy", "ANY");
					put("fromUnit", "NA");
					put("toUnit", "NA");
					put("from", "0");
					put("to", "0");
				}
			});
		}
		
	};
}
