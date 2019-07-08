import java.lang.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class TZ_OFFSET
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		
		printResult(query);
	}
	
	public static void printResult(String query)
	{
		query=query.replaceAll("(?i)tz_offset","TZ_OFFSET");
		int index_tz_offset=query.indexOf("TZ_OFFSET");
		int index_open_comma=query.indexOf("'",index_tz_offset+1);
		String result="";
		if(index_open_comma==-1)
			result=findOffsetForCase1();
		else
		{
			int index_close_comma=query.indexOf("'",index_open_comma+1);
			String value=query.substring(index_open_comma+1,index_close_comma).trim();
			int index_colon=value.indexOf(":");
			if(index_colon!=-1)
				result=value;
			else
				result=findOffsetForCase2(value);
		}
		
		System.out.println(result);
	}
	
	public static String findOffsetForCase1()
	{
		TimeZone tz = TimeZone.getDefault();  
		Calendar cal = GregorianCalendar.getInstance(tz);
		int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

		String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
		offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

		return offset;
	}
	
	public static String findOffsetForCase2(String value)
	{
		TimeZone tz = TimeZone.getTimeZone(value);
        int x=tz.getOffset(new Date().getTime())/1000/60;
		boolean flag=true;
		if(x<0)
		{
			x*=-1;
			flag=false;
		}
		
		int hour=x/60;
		x%=60;
		int minutes=x;
		
		String h=String.valueOf(hour);
		String m=String.valueOf(minutes);
		
		if(hour<10)
			h="0"+h;
		if(minutes<10)
			m="0"+m;
		
		if(!flag)
			h="-"+h;
		else
			h="+"+h;
		
		return h+":"+m;
		
	}
}




