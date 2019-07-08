
import java.lang.*;
import java.io.*;
import java.util.*;

public class COLLECT
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		
		String res=printResult(query);
		
		System.out.println(res);
	}
	
	public static String printResult(String query)
	{
		int isCast=findCast(query);
		query=query.replaceAll("(?i)CAST","")
					.replaceAll("(?i)COLLECT","COLLECT")
					.replaceAll("(?i)FROM","FROM")
					.replaceAll("(?i)DISTINCT","DISTINCT")
					.replaceAll("(?i)UNIQUE","UNIQUE")
					.replaceAll("(?i)ORDER BY","ORDER BY")
					.replaceAll("(?i)WHERE","WHERE")
					.replaceAll("(?i)GROUP BY","GROUP BY");
		//System.out.println(query);
		
		int index_collect=query.indexOf("COLLECT");
		
		String first=query.substring(0,index_collect);
		if(first.charAt(first.length()-1)=='(')
			first=first.substring(0,first.length()-1);
		
		int index_close_collect=0;
		if(isCast!=-1)
			index_close_collect=findBalanced(query,isCast-3);
		else
			index_close_collect=query.indexOf("FROM");
		
		String second=query.substring(index_collect,index_close_collect);
		
		if(isCast!=-1)
			index_close_collect++;
		
		String third=query.substring(index_close_collect);
		
		int index_for_collect=findBalanced(query,index_collect+1);
		
		String second_second=query.substring(index_for_collect+1,index_close_collect-1);
		
		String collect_part=query.substring(query.indexOf("(",index_collect+1)+1,index_for_collect);
		
		int index_distinct=collect_part.indexOf("DISTINCT");
		int len_distinct=8;
		if(index_distinct==-1)
		{index_distinct=collect_part.indexOf("UNIQUE");len_distinct=6;}
		
		int index_order=collect_part.indexOf("ORDER BY");
		
		String column=collect_part;
		String order_clause="";
		String distinct_clause="";
		if(index_distinct!=-1)
		{
			if(len_distinct==8)
				distinct_clause="DISTINCT";
			else
				distinct_clause="UNIQUE";
			column=collect_part.substring(index_distinct+len_distinct);
		}
		if(index_order!=-1)
		{
			order_clause=collect_part.substring(index_order);
			if(index_distinct==-1)
				column=collect_part.substring(0,index_order);
			else
				column=collect_part.substring(index_distinct+len_distinct,index_order);
		}
		column=column.trim();
		
		String result=first+"NEST("+column+") "+second_second+" FROM ";
		
		int index_from=query.indexOf("FROM")+4;
		
		int close_for_tables=query.indexOf("WHERE");
		if(close_for_tables==-1)
		{
			close_for_tables=query.indexOf("GROUP BY");
			if(close_for_tables==-1)
			{
				close_for_tables=query.indexOf(";");
			}
		}
		
		String table=(query.substring(index_from,close_for_tables)).trim();
		String last=query.substring(close_for_tables);
		
		if(index_distinct==-1 && index_order==-1)
			result+=table+last;
		else
			result+=table+" INNER JOIN "+"(SELECT "+distinct_clause+" "+column+" FROM "+table+" "+order_clause+") "+"XYZ"+" ON "+table+"."+column+"="+"XYZ."+column+" "+last;
		
		return result;
		
		
	}
	
	public static int findCast(String query)
	{
		query=query.replaceAll("(?i)CAST","CAST");
		
		return query.indexOf("CAST");
		
	}
	
	public static int findBalanced(String query,int index)
	{
		int bal=0;
		int x=index;
		int l=query.length();
		for(int i=index;i<l;i++)
			if(query.charAt(i)=='(')
			{
				
				bal=1;
				x=i+1;
				break;
			}
		for(int i=x;i<l;i++)
		{
			if(query.charAt(i)=='(')
				bal++;
			else if(query.charAt(i)==')')
				bal--;
			if(bal==0)
				return i;
		}
	
		return l-1;
	}
}



/*
SELECT CAST(COLLECT(phone_numbers) AS phone_book_t) FROM customers;
*/
