//TO_YMINTERVAL

import java.lang.*;
import java.io.*;
import java.util.*;

public class TOYMINTERVAL
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		
		printResult(query);
	}
	public static void printResult(String q)
	{
		int index_fun=q.indexOf("to_yminterval");
		int index_open_brac=q.indexOf("(",index_fun+1);
		int index_close_brac=q.indexOf(")",index_open_brac+1);
		
		String first=q.substring(0,index_fun-1);
		String last=q.substring(index_close_brac+1);
		String middle="";
		
		String[] x=(q.substring(index_open_brac+2,index_close_brac-1)).split("-");
		int year=Integer.parseInt((x[0]).trim());
		int month=Integer.parseInt((x[1]).trim());

		if(month>=12)
		{
			year=month/12;
			month%=12;
		}		

		if(year>0)
			middle+="+ interval "+"'"+year+"'"+" year";
		if(month>0)
			middle+="+ interval "+"'"+month+"'"+" month";
		
		String result=first+" "+middle+" "+last;
		
		System.out.println(result);
		
	}
}

/*
Input- Select last_name,hire_date+to_yminterval('0-1') as hire_Dt_30days from hr.employees;
Output- Select last_name,hire_date + interval '1' month  as hire_Dt_30days from hr.employees;

*/
