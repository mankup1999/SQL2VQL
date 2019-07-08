
import java.lang.*;
import java.io.*;
import java.util.*;

public class ROUND
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		
		printResult(query);
	}
	
	public static void printResult(String query)
	{
		query=query.replaceAll("(?i)round","ROUND")
					.replaceAll("(?i)to_date","TO_DATE");
		int index_round=query.indexOf("ROUND");
		int index_to_date=query.indexOf("TO_DATE",index_round+1);
		int index_open_comma=query.indexOf("'",index_to_date+1);
		int index_close_comma=query.indexOf("'",index_open_comma+1);
		String date=query.substring(index_open_comma+1,index_close_comma);
		
		index_open_comma=query.indexOf("'",index_close_comma+1);
		index_close_comma=query.indexOf("'",index_open_comma+1);
		String format=query.substring(index_open_comma+1,index_close_comma);
		
		//System.out.println(date);
		//System.out.println(format);
		String[] x=date.split("-");
		int day=Integer.parseInt((x[0]).trim());
		int month=monthCalculator((x[1]).trim());
		int year=Integer.parseInt((x[2]).trim());
		
		if(format.equals("SYYYY") || format.equals("YYYY") || format.equals("YEAR") || format.equals("SYEAR") || format.equals("YYY") || format.equals("YY") || format.equals("Y"))
		{
			System.out.print("01-JAN-");
			if(month>=7)
				year++;
			if(year<10)
				System.out.print(0);
			System.out.println(year);
		}
		else if(format.equals("MONTH") || format.equals("MON") || format.equals("MM") || format.equals("RM"))
		{
			System.out.print("01-");
			if(day>=16)
				month++;
			System.out.print(monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
		}
		else if(format.equals("DDD") || format.equals("DD") || format.equals("J"))
		{
			if(day<10)
				System.out.print(0);
			System.out.print(day);
			System.out.print("-"+monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
		}
		else if(format.equals("CC") || format.equals("SCC"))
		{
			if(day<10)
				System.out.print(0);
			System.out.print(day);
			System.out.print("-"+monthConverter(month)+"-");
			System.out.print((year/100+1));
			year=year%100;
			if(year<10)
				System.out.print(0);
			System.out.println(year);
		}
		else if(format.equals("Q"))
		{
			System.out.print("01-");
			if( (month==3 || (month==2 && day>=16)) || (month==6 || (month==5 && day>=16)) || (month==9 || (month==8 && day>=16)) || (month==12 || (month==11 && day>=16)) )
			{
				if(month<=3)
					month=4;
				else if(month<=6)
					month=7;
				else if(month<=9)
					month=10;
				else if(month<=12)
				{
					month=1;
					year++;
				}
			}
			System.out.print(monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
		}
		else if(format.equals("DAY") || format.equals("DY") || format.equals("D"))  
		{
			
			if(year<100)
				year+=2000;
			int y=dayofweek(day,month,year);
			boolean flag=true;
			if(y>7-y)
			{y=7-y;flag=false;}
			if(flag)
				day-=y;
			else
				day+=y;
			if(day<10)
				System.out.print(0);
			System.out.print(day);
			System.out.print("-"+monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
			
		}
		else if(format.equals("W"))
		{
			if(year<100)
				year+=2000;
			int y=dayofweek(1,month,year);
			int y1=dayofweek(day,month,year);
			int diff=y-y1;
			if(diff<0)
				diff*=-1;
			if(y>y1)
				day+=diff;
			else
				day-=diff;
		
			if(day<10)
				System.out.print(0);
			System.out.print(day);
			System.out.print("-"+monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
			
		}
		else if(format.equals("WW"))
		{
			if(year<100)
				year+=2000;
			int y=dayofweek(1,1,year);
			int y1=dayofweek(day,month,year);
			int diff=y-y1;
			if(diff<0)
				diff*=-1;
			if(y>y1)
				day+=diff;
			else
				day-=diff;
		
			if(day<10)
				System.out.print(0);
			System.out.print(day);
			System.out.print("-"+monthConverter(month)+"-");
			if(year<10)
				System.out.print(0);
			System.out.println(year);
			
		}
		else
			System.out.println(date);
		
		   
		   
	}
	
	public static int monthCalculator(String month)
	{
		int res=1;
		switch(month)
		{
			case "JAN":res=1;break;
			case "FEB":res=2;break;
			case "MAR":res=3;break;
			case "APR":res=4;break;
			case "MAY":res=5;break;
			case "JUN":res=6;break;
			case "JUL":res=7;break;
			case "AUG":res=8;break;
			case "SEP":res=9;break;
			case "OCT":res=10;break;
			case "NOV":res=11;break;
			case "DEC":res=12;break;
		}
		return res;
	}
	
	public static String monthConverter(int x)
	{
		String res="JAN";
		switch(x)
		{
			case 1:res="JAN";break;
			case 2:res="FEB";break;
			case 3:res="MAR";break;
			case 4:res="APR";break;
			case 5:res="MAY";break;
			case 6:res="JUN";break;
			case 7:res="JUL";break;
			case 8:res="AUG";break;
			case 9:res="SEP";break;
			case 10:res="OCT";break;
			case 11:res="NOV";break;
			case 12:res="DEC";break;
		}
		return res;
	}
	
	public static int dayofweek(int d, int m, int y) 
	{ 
		int[] t = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 }; 
		y -= m < 3 ? 1 : 0; 
		return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7; 
	}
	
}


