/*package whatever //do not write package name here */

import java.util.Scanner;

class TODSINTERVAL {
	public static void main (String[] args) {
		Scanner kb=new Scanner(System.in);
		String query=kb.nextLine();
		
		printNumToDisInterval(query);
		
	}
	
	public static void printNumToDisInterval(String q)
	{
	    int index_function=q.indexOf("to_dsinterval");
	    int index_open_brac=q.indexOf("(",index_function+1);
	    int index_close_brac=q.indexOf(")",index_function+1);
	    String str=q.substring(index_open_brac+1,index_close_brac);
	    String[] z=str.split(",");
	    
	    int value=Integer.parseInt((z[0]).trim());
	    String format=(z[1].substring(1,(z[1]).length()-1)).trim();
	    
	    int d,h,m,s;
	    d=h=m=s=0;
	    
	    if(format.equals("day"))
	     d=value;
	    else if(format.equals("hour"))
	    {
	        d=value/24;
	        h=value%24;
	    }
	    else if(format.equals("minute"))
	    {
	        d=value/1440;
	        value%=1440;
	        h=value/60;
	        m=value%60;
	    }
	    else if(format.equals("second"))
	    {
	        d=value/86400;
	        value%=86400;
	        h=value/3600;
	        value%=3600;
	        m=value/60;
	        s=value%60;
	    }
	    
	    String s_h=""+h;
	    if(h<10)
	        s_h=0+s_h;
	    String s_m=""+m;
	    if(m<10)
	        s_m=0+s_m;
	    String s_s=""+s;
	    if(s<10)
	        s_s=0+s_s;
	        
	        
	    System.out.println(d+" "+s_h+":"+s_m+":"+s_s);
	}
}
