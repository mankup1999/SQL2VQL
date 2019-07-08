import java.lang.*;
import java.io.*;
import java.util.*;

public class Example
{
	public static void main(String[] xyz)
	{
		Scanner kb=new Scanner(System.in);
		
		String Line,result,query="";
		boolean flag=false;
	    while(true)
	    {
	        Line=(kb.nextLine()).trim();
			
			int index_single_line_comment=Line.indexOf("--");
			if(index_single_line_comment!=-1)
				Line=(Line.substring(0,index_single_line_comment)).trim();
			
			if(flag)
			{
				int i=Line.indexOf("*/");
				if(i==-1)
					Line="";
				else
				{
					Line=Line.substring(i+2);
					flag=false;
				}
			}
			else
			{
				int i=Line.indexOf("/*");
				if(i!=-1)
				{
					flag=true;
					String x=Line.substring(0,i);
					int j=Line.indexOf("*/");
					if(j!=-1)
					{
						x+=Line.substring(j+2);
						flag=false;
					}
					Line=x;
				}
				
			}
			
			//System.out.println(Line);
	        query+=Line;
			int len=Line.length();
	        if(len!=0 && Line.charAt(len-1)==';')
	            break;
			if(len!=0)
				query+=" ";
	    }
		query=query.replaceAll("(?i)SELECT","select")
					.replaceAll("(?i)FROM","from")
					.replaceAll("(?i)WHERE","where")
					.replaceAll("(?i)UNION","union")
					.replaceAll("(?i)INTERSECT","intersect")
					.replaceAll("(?i)MINUS","minus")
					.replaceAll("(?i)AND ","and ")
					.replaceAll("(?i)OR ","or ");
		try
		{
			result=convert(query.trim());
			result=result.trim();
			int len_result=result.length();
			if(len_result!=0 && result.charAt(len_result-1)!=';')
				result+=";";
			
			System.out.println(result);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Oops! Something is wrong with query");
		}
			
	}
	
	public static String convert(String query)
	{
		String q=queryResolver(query,"union");
	    if(!q.equals(query))
	        return q;
	   else
	   {
	       String q1=queryResolver(query,"intersect");
	       if(!q1.equals(query))
	            return q1;
	       else
	       {
	           String q2=queryResolver(query,"minus");
	            if(!q2.equals(query))
	                return q2;
	       }
	   }
		int len_query=query.length();
		if(len_query==0)
			return query;
		int index_from=findBalanced(query,len_query,"from");
		int index_where=findBalanced(query,len_query,"where");
	
		/*if(index_from==-1)
			return query;
		*/
	
		String before_from="";
		String from_to_where="";
		String after_where="";

		if(index_from!=-1)
			before_from=query.substring(0,index_from);
		if(index_where==-1)
		{
			from_to_where=query.substring(index_from+4,len_query);
		}
		if(index_where!=-1)
		{
			from_to_where=query.substring(index_from+4,index_where);
			after_where=query.substring(index_where+5,len_query-1);
		}

		String result=before_from+" FROM ";
		
		String[] tables=tablesFind(from_to_where);
		int len_tables=tables.length;
		String[] symbols=new String[len_tables];
		for(int i=0;i<len_tables;i++)
			symbols[i]=symbolFind(tables[i]);
		
		/*System.out.println("-----------------------------------");
		for(int i=0;i<len_tables;i++)
		    System.out.println(tables[i]+"---------->"+symbols[i]);
		*/
			
			
		for(int i=0;i<len_tables;i++)
		{
			String a=tables[i];
			int len_a=a.length();
			int i1=a.indexOf("(");
			if(i1!=-1)
			{
					
				String b=a.substring(0,i1+1);
				int i2=a.lastIndexOf(")");
				String c=a.substring(i2,len_a);
				String d=a.substring(i1+1,i2);
				String e=convert(d);
				tables[i]=b+e+c;
				}
			}
			
		
		
		if(index_where!=-1)
		{
			String[] conditions=resultsFind(after_where);
			int len_conditions=conditions.length;
			
			/*for(int i=0;i<len_conditions;i++)
				System.out.println(conditions[i]);*/
			// System.out.println(Arrays.toString(conditions);
			

			String[] cond_for_joins=joinnable(conditions,len_conditions,symbols,len_tables);
			int len_con_for_joins=cond_for_joins.length;

			if(len_con_for_joins>0)
			{
				result+=tables[0];
				String already_tables=symbols[0];
				for(int i=1;i<len_tables;i++)
				{
					String s1=symbols[i-1];
					String s2=symbols[i];
					String join_type=joinType(already_tables,s2,cond_for_joins,len_con_for_joins);
					result+=join_type+tables[i];
					
					result+=findJoinCond(s2,already_tables,cond_for_joins,len_con_for_joins);
					already_tables+=" "+s2;
				}
				
			}
			else
			{
				for(int i=0;i<len_tables-1;i++)
					result+=tables[i]+", ";
				result+=tables[len_tables-1];	
			}

			String cond_not_for_joins=notJoinnable(conditions,len_conditions,symbols,len_tables);
			result+=cond_not_for_joins;

			return result;
		}
		
	
			for(int i=0;i<len_tables-1;i++)
				result+=tables[i]+", ";
			result+=tables[len_tables-1];
			return result;
		
	}
	
	public static String queryResolver(String query,String word)
	{
	    int index_union=findBalanced(query,query.length(),word);
	    if(index_union!=-1)
	    {
	        String[] q=query.split(word);
	        int len_q=q.length;
	        String res="";
	        for(int i=0;i<len_q-1;i++)
	        {
	            int index_open=(q[i]).indexOf("(");
	            int index_close=(q[i]).lastIndexOf(")");
	            String first=(q[i]).substring(0,index_open+1);
	            String last=(q[i]).substring(index_close);
	            String middle=((q[i]).substring(index_open+1,index_close)).trim();
	            String mid=convert(middle);
	            q[i]=first+mid+last;
	            res+=(q[i])+" "+word+" ";
	        }
	        int j=len_q-1;
	        int index_open=(q[j]).indexOf("(");
	       int index_close=(q[j]).lastIndexOf(")");
	       String first=(q[j]).substring(0,index_open+1);
	       String last=(q[j]).substring(index_close);
	       String middle=((q[j]).substring(index_open+1,index_close)).trim();
	        String mid=convert(middle);
	         q[j]=first+mid+last;
	       res+=(q[j]);
	       return res; 
	    }
	    
	    return query;
	}
	
	public static int findBalanced(String X,int l,String word)
	{
		int index=X.indexOf(word);
		int len_word=word.length();
		while(index!=-1)
		{
			Boolean bal_left=balance(X,0,index);
			Boolean bal_right=balance(X,index+len_word,l);
			if(bal_left && bal_right)
				return index;
			index=X.indexOf(word,index+1);
		}
		return -1;
	}

	public static Boolean balance(String X,int l,int r)
	{
		int count=0;
		char[] x=(X.substring(l,r)).toCharArray();
		int len_x=x.length;
		for(int i=0;i<len_x;i++)
		{
			if(x[i]=='(')
				count++;
			else if(x[i]==')')
				count--;
		}
		return count==0;
	}
	
	public static String[] tablesFind(String X)
	{
		int len=X.length();
		String[] y=new String[100];
		int len_y=0;
		int prev_index=0;
		int index_comma=X.indexOf(",");
		while(index_comma!=-1)
		{
			Boolean bal_left=balance(X,0,index_comma);
			Boolean bal_right=balance(X,index_comma+1,len);
			if(bal_left && bal_right)
			{
				y[len_y++]=X.substring(prev_index,index_comma);
				prev_index=index_comma+1;
			}
			index_comma=X.indexOf(",",index_comma+1);	
		}
		y[len_y++]=X.substring(prev_index,len);
		String[] res=new String[len_y];
		for(int i=0;i<len_y;i++)
			res[i]=(y[i]).trim();
		return res;
	}
	
	public static String symbolFind(String X)
	{
		int n=X.lastIndexOf(")");
		if(n!=-1)
		    X=(X.substring(n+1)).trim();
		n=X.lastIndexOf("as ");
		if(n!=-1)
		    X=(X.substring(n+1)).trim();
		n=X.lastIndexOf(".");
		if(n!=-1)
		    X=(X.substring(n+1)).trim();
		n=X.lastIndexOf(" ");
		if(n!=-1)
		    X=(X.substring(n+1)).trim();
		return X;
	}
	
	public static String[] resultsFind(String X)
	{
		int len=X.length();
		String[] y=new String[100];
		int len_y=0;
		int prev_index=0;
		String check="and";
		int index_comma=X.indexOf(" and ");
		if(index_comma==-1)
		{
			index_comma=X.indexOf(" or ");
			check="or";
		}
		while(index_comma!=-1)
		{
			Boolean bal_left=balance(X,0,index_comma);
			Boolean bal_right=balance(X,index_comma+1,len);
			if(bal_left && bal_right)
			{
				y[len_y++]=X.substring(prev_index,index_comma);
				if(check.equals("and"))
					prev_index=index_comma+5;
				else if(check.equals("or"))
					prev_index=index_comma+4;
			}
			check="and";
			index_comma=X.indexOf(" and ",prev_index);
			if(index_comma==-1)
			{
				index_comma=X.indexOf(" or ",prev_index);
				check="or";
			}	
		}
		y[len_y++]=X.substring(prev_index,len);
		String[] res=new String[len_y];
		for(int i=0;i<len_y;i++)
			res[i]=(y[i]).trim();
		return res;
	}
	
	
	public static String[] joinnable(String[] X,int l,String[] Y,int ly)
	{
		String[] y=new String[100];
		int len_y=0;
		for(int i=0;i<l;i++)
			if((X[i]).indexOf("(+)")!=-1 || dots(X[i],Y,ly)>=2)
				y[len_y++]=X[i];
		String[] res=new String[len_y];
		for(int i=0;i<len_y;i++)
			res[i]=(y[i]).trim();
		return res;
	}

	public static int dots(String X,String[] Y,int l)
	{
		int count=0;
		
		for(int i=0;i<l;i++)
		{
			if(X.indexOf(Y[i]+".")!=-1)
				count++;
		}
		
		return count;
	}
	
	
	public static String joinType(String T,String s2,String[] X,int l)
	{
		String[] s=T.split(" ");
		int len_T=s.length;
		for(int j=0;j<len_T;j++)
			s[j]=(s[j]).trim();
		
		for(int j=0;j<len_T;j++)
		{
			String s1=s[j];
			for(int i=0;i<l;i++)
			{
				int n1=(X[i]).indexOf(s1+".");
				int n2=(X[i]).indexOf(s2+".");
				if(n1!=-1 && n2!=-1)
				{
					//System.out.println(X[i]+"---"+s1+"----"+s2);
					int index=(X[i]).indexOf("+");
					if(index==-1)
						return " INNER JOIN ";
					if(n1>n2)
					{
						if(index>n1)
							return " RIGHT OUTER JOIN ";
						else
							return " LEFT OUTER JOIN ";
					}
					else
					{
						if(index>n2)
							return " LEFT OUTER JOIN ";
						else
							return " RIGHT OUTER JOIN ";
							
					}
				}
			}
		}
		return " LEFT OUTER JOIN ";
	}

	public static String findJoinCond(String x,String Y,String[] X,int l)
	{
		String res=" ON ";
		Boolean flag=false;

		String[] y=Y.split(" ");
		int len_y=y.length;


		for(int i=0;i<l;i++)
		{
			int n1=(X[i]).indexOf(x+".");
			if(n1!=-1)
			{
				for(int j=0;j<len_y;j++)
				{
					int n2=(X[i]).indexOf(y[j]+".");
					if(n2!=-1)
					{
						if(flag)
							res+=" and ";
						int index_plus=(X[i]).indexOf("(+)");
						if(index_plus==-1)
							res+=X[i];
						else
							//res+=(X[i]).substring(0,index_plus)+(X[i]).substring(index_plus+3);
							res+=(X[i]).replace("(+)","");
						flag=true;
					}
				}
			}
		}
		if(!flag)
			return "";
		return res;
	}
	
	public static String notJoinnable(String[] X,int l,String[] Y,int ly)
	{
		String res=" WHERE ";
		Boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if((X[i]).indexOf("(+)")==-1 && dots(X[i],Y,ly)<2)
			{
				if(flag)
					res+=" and ";
				//System.out.println(X[i]);
				res+=X[i];
				flag=true;
			}
		}
		if(!flag)
			return "";
		return res;
	}
		
	
}