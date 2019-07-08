/*AGGREGATE EXAMPLE-
SELECT department_id,PERCENTILE_DISC(0.5) WITHIN GROUP (ORDER BY salary DESC) "Median cont" FROM employees GROUP BY department_id;
*/

import java.lang.*;
import java.io.*;
import java.util.*;

public class PERCENTILE_DISC
{
	public static void main(String[] args)
	{
		Scanner kb=new Scanner(System.in);
		String query=kb.nextLine();
		
		printResult(query);
	}
	
	public static void printResult(String query)
	{
		query=query.replaceAll("(?i)percentile_disc","PERCENTILE_DISC")
					.replaceAll("(?i)from","FROM")
					.replaceAll("(?i)group by","GROUP BY")
					.replaceAll("(?i)order by","ORDER BY")
					.replaceAll("(?i) asc"," ASC ")
					.replaceAll("(?i) desc"," DESC ")
					.replaceAll("(?i)where","WHERE");
		
		int index_per_cont=query.indexOf("PERCENTILE_DISC");
		int index_from=query.indexOf("FROM");
		
		int perc_cont_ends=findPercContEnds(query,index_per_cont,index_from);
		
		String before_per_cont=query.substring(0,index_per_cont);
		String after_per_cont=query.substring(perc_cont_ends);
		String perc_cont=query.substring(index_per_cont,perc_cont_ends);
		
		String last=";";
		int index_where=query.indexOf("WHERE");
		if(index_where!=-1)
			last=query.substring(index_where);
		
		String result="";
		
		String percentile=perc_cont.substring(perc_cont.indexOf("(")+1,perc_cont.indexOf(")")).trim();
		
		result+="DECLARE P NUMBER := "+percentile+";\n";
		
		String group_attr="";

		group_attr=findGroupByAttr(query,query.indexOf("GROUP BY")+8);
		
		int index_query_ends=query.indexOf(" ",index_from+5);
		if(index_query_ends==-1)
			index_query_ends=query.indexOf(";",index_from+5);
		String table=query.substring(index_from+5,index_query_ends).trim();
			
		int index_order=perc_cont.indexOf("ORDER BY");
		int index_oreder_ends=perc_cont.indexOf(")",index_order+1);
		String order_clause=perc_cont.substring(index_order+8,index_oreder_ends).trim();
		String as_var="";
		
		as_var=perc_cont.substring(index_oreder_ends+1).trim();

		String x="";
		if(as_var.length()>0)
				x=query.substring(query.indexOf(as_var)+as_var.length(),query.indexOf("FROM")).trim();
		String order_attr="";
		int index_asc=order_clause.indexOf(" ASC");
		if(index_asc==-1)
			index_asc=order_clause.indexOf(" DESC");
		if(index_asc==-1)
			order_attr=order_clause;
		if(index_asc!=-1)
			order_attr=order_clause.substring(0,index_asc).trim();
			
		result+="--TEMPORARY TABLE\nCREATE TABLE temporary ("+group_attr+" VARCHAR, "+order_attr+" NUMBER);\n";

		result+="CREATE VIEW v1 AS \n\tSELECT "+group_attr+", COLLECT("+order_attr+")"+" AS "+order_attr+x+" FROM "+table+" GROUP BY "+group_attr+" ORDER BY "+order_clause+last+"\n";
			
		result+="DECLARE rows NUMBER;\n\t SELECT COUNT(*) INTO rows FROM v1;\n";
			
		result+="DECLARE a NUMBER :=1;\nLOOP\n\n\tBEGIN";
		result+="\n\t\tCREATE VIEW v3 AS \n\t\t\tSELECT "+order_attr+" FROM "+"v1 LIMIT a OFFSET 0;\n";
		result+="\t\tCREATE VIEW v2 AS \n\t\t\tSELECT * FROM "+"v3 ORDER BY "+order_attr+";\n";
		result+="\t\tDECLARE N NUMBER;\n\t\tSELECT COUNT(*) INTO N FROM v2;\n";
		result+="\t\tDECLARE RN NUMBER :=(1+P*(N-1));\n";
		result+="\t\tDECLARE FRN INTEGER :=FLOOR(RN);\n";
		result+="\t\tDECLARE CRN INTEGER :=CEIL(RN);\n";
		result+="\t\tIF FRN==CRN THEN\n";
		result+="\t\t\tBEGIN\n";
		result+="\t\t\tDECLARE val NUMBER;\n\t\t\tSELECT "+order_attr+" INTO val FROM v2 LIMIT RN OFFSET 0;\n";
		result+="\t\t\tDECLARE id VARCHAR;\n\t\t\tSELECT "+group_attr+" INTO id FROM v2;\n";
		result+="\t\t\tINSERT INTO temporary VALUES ("+"id,val"+")\n";
		result+="\t\t\tEND\n";
		result+="\t\tELSE\n";
		result+="\t\t\tBEGIN\n";
		result+="\t\t\tDECLARE val1 NUMBER;\n\t\t\tSELECT "+order_attr+" INTO val1 FROM v2 LIMIT FRN OFFSET 0;\n";
		result+="\t\t\tDECLARE val2 NUMBER;\n\t\t\tSELECT "+order_attr+" INTO val2 FROM v2 LIMIT CRN OFFSET 0;\n";
		result+="\t\t\tDECLARE val NUMBER :=val1;\n";
		result+="\t\t\tDECLARE id VARCHAR;\n\t\t\tSELECT "+group_attr+" INTO id FROM v1 LIMIT a OFFSET 0;\n";
		result+="\t\t\tINSERT INTO temporary VALUES ("+"id,val"+")\n";
		result+="\t\t\tEND\n";
		result+="\n\ta := a+1\n\tEXIT WHEN a>rows;\n\tEND\nEND LOOP";
		
		
		result+="\n\n\n"+before_per_cont+order_attr;
			
		if(as_var.length()>0)
			result+=" AS "+as_var+" ";
		result+=after_per_cont.substring(0,after_per_cont.indexOf("FROM")).trim()+"\n";

		result+="FROM "+"v1"+" INNER JOIN temporary ON "+"v1"+"."+group_attr+" = "+"temporary."+group_attr+last+"\n";
			
		System.out.println(result);
			
		
	}
	
	public static int findPercContEnds(String query,int l,int r)
	{
		int bal=0;
		int index=l;
		for(int i=l;i<r;i++)
		{
			char c=query.charAt(i);
			if(c=='(')
				bal++;
			else if(c==')')
				bal--;
			else if(c==',' && bal==0)
			{
				index=i;
				break;
			}
				
		}
		if(query.charAt(index)!=',')
			index=r;
		return index;
	}
	
	public static String findGroupByAttr(String query,int l)
	{
		while(true)
		{
			if(query.charAt(l)!=' ')
				break;
			l++;
		}
		String attr="";
		while(true)
		{
			char c=query.charAt(l);
			if(c==' ' || c==';')
				break;
			attr+=String.valueOf(c);
			l++;
		}
		return attr;
	}
}

