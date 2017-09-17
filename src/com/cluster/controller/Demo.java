package com.cluster.controller;

public class Demo {
	
     void m1()
     {
    	 String backHome = "<div "
 				+ "style='border-bottom: #000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 30px; margin-bottom: 10px; background-color: aqua;'>"
 				+" <form action='./'>"
 				+"<table style='margin-left:30px;margin-right:30px;'>"
 				+ "<tr>"
 				+ "<td style='width:50px;'> "
 				+ "<a href='./'><input type='button' value='home'/></a> </td>"
 				+ "<td style='width: 1300px;'></td>"
 				+ "<td>"
 				+ "<input type='submit' value='Back'></td>"
 				+ "</tr></table>" +"</form>" +"</div>";
     }
     void change()
     {
    	 String backHome = "<div "
  				+ "style='border-bottom: #000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 30px; margin-bottom: 10px; background-color: aqua;'>"
  				+" <form action='./'>"
  				+"<table style='margin-left:30px;margin-right:30px;'>"
  				+ "<tr>"
  				+ "<td style='width:50px;'> "
  				+ "<a href='./'><input type='button' value='home'/></a> </td>"
  				+ "<td style='width: 1300px;'></td>"
  				+ "<td>"
  				+ "<input type='submit' value='Back'></td>"
  				+ "</tr></table>" +"</form>" +"</div>";
     }
     
     public static void main(String args[])
     {
          /*begin*/
    	 System.out.println("begin");
    	 String str="IND-BLR-20141101";
    	    
    	     String strTemp1 = str.substring(0,3)+"_";
    	     String strTemp2=str.substring(4,7);
    	     String strSeq= strTemp1.concat(strTemp2);
    	     System.out.println(strTemp1+strTemp2);
    	     /*System.out.println(split.length);
    	     for(int i=0;i<split.length;i++)
    	     {
    	    	 System.out.println(split[i]);
    	    	 split[i].
    	     }*/
     }
}
