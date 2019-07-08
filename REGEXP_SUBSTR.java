//REGEXP_SUBSTR

import java.util.Scanner;
import java.util.regex.*;

class REGEXP_SUBSTR {
	public static void main (String[] args) {
	    Scanner kb=new Scanner(System.in);
	    String s=kb.nextLine();
	    
	    printResult(s);
	}
	
	
	public static void printResult(String s)
	{
	    int a=s.indexOf('(')+1;
	    int b=s.lastIndexOf(')');
	    s=s.substring(a,b);
	    
	    int w=s.indexOf("'",1);
	    String source_str=s.substring(1,w);
	    
	    s=s.substring(w+1,s.length());
	   String[] x=s.split(",");
	    int len=x.length;
	    for(int i=0;i<len;i++)
	        x[i]=(x[i]).trim();
	 
	   String d=x[1];
	   String reg_ex=d.substring(1,d.length()-1);
	   
	   //default values
	   int start=0;
	   int occur=0;
	   String match_para="c";
	   int sub_expr=0;
	   if(len>=3)
	        start=Integer.parseInt(x[2])-1;
	   if(len>=4)
	        occur=Integer.parseInt(x[3])-1;
	   if(len>=5)
	        match_para=(x[4]).substring(1,2);
	   if(len>=6)
	        sub_expr=Integer.parseInt(x[5])-1;
	        
	   source_str=source_str.substring(start,source_str.length());
	   Pattern p=null;
	   
	   if(match_para.equals("c"))
	    p = Pattern.compile(reg_ex);
	   else if(match_para.equals("i"))
	    p = Pattern.compile(reg_ex,Pattern.CASE_INSENSITIVE);
	   else if(match_para.equals("m"))
	    p = Pattern.compile(reg_ex,Pattern.MULTILINE);
	   else if(match_para.equals("im"))
	    p = Pattern.compile(reg_ex,Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	    
        Matcher m = p.matcher(source_str);
        if(m.find())
            System.out.println(m.group(occur));
	    
	}
}
