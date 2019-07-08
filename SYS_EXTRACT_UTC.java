//SYS_EXTRACT_UTC

import java.util.*;


public class SYS_EXTRACT_UTC
{
	public static void main(String[] args)
	{
		Scanner kb=new Scanner(System.in);
		String query=kb.nextLine();
		
		SYS_EXTRACT_UTC(query);
	}
	
	public static void SYS_EXTRACT_UTC(String query)
	{
		int index_timestamp=query.indexOf("TIMESTAMP");
		int index_open_quotes=query.indexOf("'",index_timestamp+1);
		int index_close_quotes=query.indexOf("'",index_open_quotes+1);
		
		String q=query.substring(index_open_quotes+1,index_close_quotes);
		
		String[] x=q.split(" ");
		String date=(x[0]).trim();
		String time=(x[1]).trim();
		String difference=(x[2]).trim();
		
		String[] a=date.split("-");
		int year=Integer.parseInt((a[0]).trim());
		int month=Integer.parseInt((a[1]).trim());
		int day=Integer.parseInt((a[2]).trim());
		
		String[] b=time.split(":");
		int hour=Integer.parseInt((b[0]).trim());
		int minute=Integer.parseInt((b[1]).trim());
		int second=Integer.parseInt((b[2].substring(0,2)).trim());
		
		String[] c=difference.split(":");
		int hour_diff=Integer.parseInt((c[0]).trim());
		int minute_diff=Integer.parseInt((c[1]).trim());
		String am_pm="";
		
		if(hour_diff<=0)
		{
			minute+=minute_diff;
			if(minute>=60)
			{
				minute-=60;
				hour+=1;
			}
			hour+=Math.abs(hour_diff);
			if(hour<12)
				am_pm="AM";
			else if(hour>=12)
			{
				hour-=12;
				am_pm="PM";
			}
		}
		else
		{
			if(minute>minute_diff)
			{
				minute-=minute_diff;
			}
			else
			{
				minute=60-(minute_diff-minute);
				if(hour==0)
				{
					hour=11;
					am_pm="AM";
				}
			}
			hour-=Math.abs(hour_diff);
			if(hour<0)
			{
				hour=12+hour;
			}
		}
		
		String year_str=year%100+"";
		if(year%100<10)
			year_str="0"+year_str;
		String month_str=month_equi(month);
		String day_str=day+"";
		String hour_str="";
		if(hour<10)
			hour_str="0"+hour;
		else
			hour+=hour;
		String minute_str="";
		if(minute<10)
			minute_str="0"+minute;
		else
			minute_str+=minute;
		String second_str="";
		if(second<10)
			second_str="0"+second;
		else
			second_str+=second;
		
		String result=day_str+"-"+month_str+"-"+year_str+" "+hour_str+"."+minute_str+"."+second_str+" "+am_pm;
		
		System.out.println(result);
	}
	public static Boolean leap_year(int y)
	{
		if(y%100==0)
		{
			if(y%400==0)
				return true;
			return false;
		}
		if(y%4==0)
			return true;
		return false;
	}
	public static String month_equi(int m)
	{
		String month="";
		switch(m)
		{
			case 1:month="JAN";break;
			case 2:month="FEB";break;
			case 3:month="MAR";break;
			case 4:month="APR";break;
			case 5:month="MAY";break;
			case 6:month="JUN";break;
			case 7:month="JUL";break;
			case 8:month="AUG";break;
			case 9:month="SEP";break;
			case 10:month="OCT";break;
			case 11:month="NOV";break;
			case 12:month="DEC";break;
		}
		
		return month;
	}
}

/*
Input- SELECT SYS_EXTRACT_UTC(TIMESTAMP '2015-03-18 11:25:00.00 -05:00') FROM DUAL;

Output- 18-MAR-15 04.25.00 PM

Input- SELECT SYS_EXTRACT_UTC(TIMESTAMP '2000-03-28 11:30:00.00 -08:00') FROM DUAL;
 
Output- 28-MAR-00 07.30.00 PM

*/
