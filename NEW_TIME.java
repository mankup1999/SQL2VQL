//"SELECT NEW_TIME(TO_DATE('11-10-99 01:23:45', 'MM-DD-YY HH24:MI:SS'),'AST', 'PST') ""New Date and Time"" FROM DUAL;"
//"SELECT NEW_TIME(TO_DATE(   '11-10-99 01:23:45', 'MM-DD-YY HH24:MI:SS'),   'AST', 'PST') ""New Date and Time"" FROM DUAL;"

import java.lang.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class NEW_TIME
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		
		printResult(query);
	}
	
	public static void printResult(String query)
	{
		query=query.replaceAll("(?i)new_time","NEW_TIME")
				   .replaceAll("(?i)to_date","TO_DATE");
		int index_new_time=query.indexOf("NEW_TIME");
		int index_to_date=query.indexOf("TO_DATE",index_new_time+1);
		int index_to_date_starts=query.indexOf("(",index_to_date+1);
		int index_to_date_ends=query.indexOf(")",index_to_date+1);
		int index_time_zones=query.indexOf(",",index_to_date_ends+1);
		int index_new_time_ends=query.indexOf(")",index_to_date_ends+1);
		
		String date=query.substring(index_to_date_starts+1,index_to_date_ends).trim();
		String time_zones=query.substring(index_time_zones+1,index_new_time_ends).trim();
		
		String[] x=time_zones.split(",");
		String start_time_zone=(x[0]).replaceAll("'","").trim();
		String last_time_zone=(x[1]).replaceAll("'","").trim();
		
		date=date.replaceAll("'","");
		
		String[] y=date.split(",");
		String time=(y[0]).trim();
		String time_format=(y[1]).trim();
		
		String[] a=time.split(" ");
		String[] b=time_format.split(" ");
		
		String time_to_convert=findTime(a,b);
		
		//System.out.println(time_to_convert);
		
		//System.out.println(start_time_zone+"--"+last_time_zone);
		
		String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat estFormatter = new SimpleDateFormat(format);
        estFormatter.setTimeZone(TimeZone.getTimeZone(start_time_zone));
        try{
        Date date1 = estFormatter.parse(time_to_convert);
    
    
        SimpleDateFormat utcFormatter = new SimpleDateFormat(format);
        utcFormatter.setTimeZone(TimeZone.getTimeZone(last_time_zone));
    
        System.out.println(utcFormatter.format(date1));}
        catch(Exception e)
        {
            System.out.println();
        }
		
	}
	
	public static String findTime(String[] a,String[] b)
	{
		String date=(a[0]).trim();
		String time=(a[1]).trim();
		String date_format=(b[0]).trim();
		String time_format=(b[1]).trim();
		
		String yy="",mm="",dd="";
		
		String[] x=date.split("-");
		String[] y=date_format.split("-");
		int len_y=y.length;
		
		for(int i=0;i<len_y;i++)
		{
			String z=(y[i]).trim();
			String x1=(x[i]).trim();
			if(z.equals("YY"))
				yy=x1;
			else if(z.equals("MM"))
				mm=x1;
			else if(z.equals("DD"))
				dd=x1;
		}
		
		String hh="",mi="",ss="";
		
		String[] x2=time.split(":");
		String[] y2=time_format.split(":");
		int len_y2=y2.length;
		
		for(int i=0;i<len_y2;i++)
		{
			String z=(y2[i]).trim();
			String x1=(x2[i]).trim();
			if(z.equals("HH24"))
				hh=x1;
			else if(z.equals("MI"))
				mi=x1;
			else if(z.equals("SS"))
				ss=x1;
		}
		
		int hour=Integer.parseInt(hh);
		boolean flag=true;
		if(hour>=12)
		{hh=String.valueOf(hour-12);flag=false;}
		else if(hour>=0)
			hh=String.valueOf(hour);
		
		String[] res=new String[6];
		
		res[0]=yy;
		if(yy.length()==2)
			res[0]="19"+yy;
		res[1]=mm;
		res[2]=dd;
		res[3]=hh;
		res[4]=mi;
		res[5]=ss;

		String result=res[0]+"-"+res[1]+"-"+res[2]+" "+res[3]+":"+res[4]+":"+res[5];
		
		return result;
	}
	
}

