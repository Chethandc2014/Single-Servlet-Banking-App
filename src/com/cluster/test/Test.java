package com.cluster.test;

import java.util.Locale;
import java.util.TimeZone;

public class Test {

	public static void main(String[] args) {
				/*String[] availableIDs = TimeZone.getAvailableIDs();
				
				for(String str:availableIDs)
				{
					System.out.println(str);
				}*/
						Locale[] availableLocales = Locale.getAvailableLocales();
						for(Locale locale:availableLocales)
						{
System.out.println(locale+" "+locale.getISO3Country()+"  "+locale.getISO3Language()+"country Name: "+locale.getDisplayCountry());
							
						}	
				
						
						System.currentTimeMillis();
						
						
	}
	
	
	
	
	

}
