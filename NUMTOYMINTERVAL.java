//NUMTOYMINTERVAL

import java.util.*;


public class Main
{
	public static void main(String[] args)
	{
		Scanner kb=new Scanner(System.in);
		String query=kb.nextLine();
		
		NUMTOYMINTERVAL(query);
	}
	
	public static void NUMTOYMINTERVAL(String query)
	{
		int index_fun=query.indexOf("NUMTOYMINTERVAL")+14;
		int index_open_brac=query.indexOf("(",index_fun)+1;
		int index_close_brac=query.indexOf(")",index_open_brac);
		
		String q=query.substring(index_open_brac,index_close_brac);
		
		String[] x=q.split(",");
		int value=Integer.parseInt((x[0]).trim());
		String dependency=(x[1]).trim();
		
		int month=0;
		int year=0;
		
		if(dependency.equals("'year'"))
			year=value;
		else if(dependency.equals("'month'"))
		{
			year+=value/12;
			month=value%12;
		}
		
		System.out.println(year+"-"+month);
		
	}
}

/*
Input- select NUMTOYMINTERVAL(3,'year') from dual;
Output- 3-0

Input- select NUMTOYMINTERVAL (11,'month') from dual; 
Output- 0-11

*/
