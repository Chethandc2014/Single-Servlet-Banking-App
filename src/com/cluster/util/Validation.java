package com.cluster.util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
	public static boolean nameValidate(String strName) {
		boolean b;
		return b = strName.matches("[a-zA-Z]{3,15}");
	}

	public static boolean emailidValidate(String emailid) {
		boolean b;

		return b = emailid.matches("\\w+@[a-zA-Z]{1,8}\\.[a-zA-Z]{3,8}");
	}
	public static boolean salaryValidate(long sal) {
		boolean b;
		String str = Long.toString(sal);
//	return  b = Pattern.compile("\\d+").matcher("str").matches()||Pattern.compile("\\d.\\d").matcher("str").matches();
		     return b=true;
	}
	public static boolean genderValidate(String gen) {
		boolean b;
	   return b=gen.matches("Active")||gen.matches("Passive");
	}
	public static boolean phoneValidate(long phone) {
		boolean b;
		String strPh=Long.toString(phone);
	  return b=strPh.matches("\\d{12}");
	}
	public static boolean  characterValidate(String dis) {
		boolean b;
		return  b=dis.matches("[a-zA-Z]+");
	}
	public static boolean zipcodeValidate(int zip) {
		boolean b;
		 String strZip = Integer.toString(zip);
		
		return b=strZip.matches("\\d{6,8}");
	}
	public static boolean Number(int n) {
		boolean b ;
		String strNum = Long.toString(n);
		return b=strNum.matches("[0-9]{12}");
	}
     public static boolean passwordValidate(String pass)
     {    boolean b;
    	 return pass.matches("[a-zA-Z0-9]{4,8}");
    	 
     }
     public static boolean branchValidate(String country,String branch) {
    	
    	 Character countryFirstChar = country.charAt(0);
    	 Character branchFirstChar= branch.charAt(0);
    	 
    	 return countryFirstChar.equals(branchFirstChar);
	}
	public static boolean amountValidate(int amt) {
		
		return amt >=5000;
		
	}
	public static boolean addressValidate(String address) {
		int length = address.length();
		return length<=200;
		
	}
	public static boolean dateValidate(String date) {
		
		     boolean b = false;
		          /*1st checking all the date are numbers format and specified no of quantities*/
		     b=date.matches("\\d{2}/\\d{2}/\\d{4}");   
		         if(b)
		         {     
		        	 
		           /**2nd checking the range of day and month */
				   /*converting string day into digits into primitives*/
		        	 
						int dateFirstDigit=Integer.parseInt(String.valueOf(date.charAt(0)));		
					    int dateSecDigit=Integer.parseInt(String.valueOf(date.charAt(1)));
					    	System.out.println(dateFirstDigit+" "+dateSecDigit);
				    if(!(dateFirstDigit>=3&&dateSecDigit>=2))/*checking the date range*/
				    {    
				    	
				    	
				    	/*converting string month into digits primitive*/
				         int monthFirstDigit = Integer.parseInt(String.valueOf(date.charAt(3)));
				         int monthSecDigit=Integer.parseInt(String.valueOf(date.charAt(4)));
				         System.out.println(monthFirstDigit+" "+monthSecDigit);
				    	if(monthFirstDigit==0 || (monthFirstDigit<=1 &&monthSecDigit<=2))
				    	{
				    		 b=true;
				    	}
				    	else
				    	{
				    		 b=false;
				    	}
				    	     
				    }
				    else
				    {
				    	 b=false;
				    }
		        }
		     return b;
				
			}
}
