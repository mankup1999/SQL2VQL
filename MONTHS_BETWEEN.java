
//Pass your query as an argument of the function 'printResult'

import java.lang.*;
import java.io.*;
import java.util.*;

public class MONTHS_BETWEEN
{
		public static void main(String[] args)
		{
			Scanner sc=new Scanner(System.in);
			String query=sc.nextLine();
			
			printResult(query);
			
		}
		
		public static void printResult(String query)
		{
			query=query.replaceAll("(?)months_between","MONTHS_BETWEEN")
						.replaceAll("(?)to_date","TO_DATE")
						.replaceAll("-","/")
						.replaceAll("(?)YYYY","yyyy")
						.replaceAll("(?)MM","mm")
						.replaceAll("(?)DD","dd");
			int index_of_function=query.indexOf("MONTHS_BETWEEN");
			
			int index_start=query.indexOf("TO_DATE",index_of_function+1);
			String start_date=findDate(query,index_start);
			int index_last=query.indexOf("TO_DATE",index_start+1);
			String last_date=findDate(query,index_last);
			
			String[] dates1=start_date.split("-");
			String[] dates2=last_date.split("-");
			
			int start_year=Integer.parseInt(dates1[0]);
			int start_month=Integer.parseInt(dates1[1]);
			int start_day=Integer.parseInt(dates1[2]);
			
			int last_year=Integer.parseInt(dates2[0]);
			int last_month=Integer.parseInt(dates2[1]);
			int last_day=Integer.parseInt(dates2[2]);
			
			boolean result_type=resultType(start_year,start_month,start_day,last_year,last_month,last_day);
			
			int yr_diff=0,mon_diff=0,day_diff=0;

			
			if(start_year<last_year)
			{
				int t=start_year;start_year=last_year;last_year=t;
				t=start_month;start_month=last_month;last_month=t;
				t=start_day;start_day=last_day;last_day=t;
			}
			else if(start_year==last_year)
			{
				if(start_month<last_month)
				{
					int t=start_year;start_year=last_year;last_year=t;
					t=start_month;start_month=last_month;last_month=t;
					t=start_day;start_day=last_day;last_day=t;
				}
				else if(start_month==last_month)
				{
					if(start_day<last_day)
					{
						int t=start_year;start_year=last_year;last_year=t;
						t=start_month;start_month=last_month;last_month=t;
						t=start_day;start_day=last_day;last_day=t;
					}
				}
			}
			
			yr_diff=start_year-last_year;

			if(start_month>=last_month)
				{
					mon_diff=start_month-last_month;
					if(start_day>=last_day)
					{
						day_diff=start_day-last_day;
						if(isLeapYear(start_year) && start_month>2)
							day_diff+=1;
						if(isLeapYear(last_year) && last_month>2)
							day_diff-=1;
					}
					else
					{
						mon_diff-=1;
						day_diff=daysCount(start_month-1)+start_day-last_day;
						if(isLeapYear(start_year) && (start_month-1)>2)
							day_diff+=1;
						if(isLeapYear(last_year) && last_month>2)
							day_diff-=1;
					}
				}
			else
				{
						yr_diff-=1;
						mon_diff=start_month+12-last_month;
					if(start_day>=last_day)
					{
						day_diff=start_day-last_day;
						if(isLeapYear(start_year) && start_month>2)
							day_diff+=1;
						if(isLeapYear(last_year) && last_month>2)
							day_diff-=1;
					}
					else
					{
						mon_diff-=1;
						day_diff=daysCount(start_month-1)+start_day-last_day;
						if(isLeapYear(start_year) && (start_month-1)>2)
							day_diff+=1;
						if(isLeapYear(last_year) && last_month>2)
							day_diff-=1;
					}
				}
			
			double result=12*yr_diff+mon_diff+(double)day_diff/31;
			
			if(!result_type)
					result*=-1;
			System.out.println(result);
			
			
			
		}
		
		public static String findDate(String query,int a)
		{
			int b=query.indexOf("(",a+1);
			int c=query.indexOf("\'",b+1);
			int d=query.indexOf("\'",c+1);
			int e=query.indexOf(",",d+1);
			int f=query.indexOf("\'",e+1);
			int g=query.indexOf("\'",f+1);
			
			String date=(query.substring(c+1,d)).trim();
			String format=(query.substring(f+1,g)).trim();
			
			String[] x=date.split("/");
			String[] y=format.split("/");
			int l=y.length;
			
			String year="",month="",day="";
			
			for(int i=0;i<l;i++)
			{
				String z=(y[i]).trim();
				if(z.equals("yyyy"))
					year=(x[i]).trim();
				else if(z.equals("mm"))
					month=(x[i]).trim();
				else if(z.equals("dd"))
					day=(x[i]).trim();
			}
			
			
			return year+"-"+month+"-"+day;
			
		}
		
		public static boolean resultType(int a,int b,int c,int d,int e,int f)
		{
			if(a<d)
				return false;
			if(a==d && b<e)
				return false;
			if(a==d && b==e && c<f)
				return false;
			return true;
		}
		
		public static int daysCount(int n)
		{
			if(n==1 || n==3 || n==5 || n==7 || n==8 || n==10 || n==12)
				return 31;
			else if(n==2)
				return 28;
			return 30;
		}
		
		public static boolean isLeapYear(int year)
		{
			if(year%100==0)
			{
				if(year%400==0)
					return true;
				return false;
			}
			if(year%4==0)
				return true;
			return false;
		}
		
}

