
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class UNISTR {
    
    public static String[] splitPath (String path) {
    String backslash = ((char)92) + "";
    if (path.contains(backslash)) {
        ArrayList<String> parts = new ArrayList<>();
        int start = 0;
        int end = 0;
        for ( int c : path.toCharArray() ) {
            if (c == 92) {
                parts.add(path.substring(start, end));
                start = end + 1;
            }
            end++;
        }
        parts.add(path.substring(start));
        return parts.toArray( new String[parts.size()] );
        }
        return path.split("/");
    }

    public static int conv(String s)
    {
        int sum1=0;
        int j=0;
        char[] x=s.toCharArray();
        for(int i=x.length-1;i>=0;i--)
        {
            int tmp=(int)x[i];
            if(tmp>=48 && tmp<=57)
                tmp-=48;
            else if(tmp>=97 && tmp<=122)
                {tmp-=87;}
            int tmp1=(int)Math.pow(16,j);
            int sum=tmp1*tmp;
            sum1+=sum;
            j++;
        }
        
        return sum1;
    }
    
    public static void final_conv(String s)
    {
        int i=s.indexOf('(');
		int j=s.indexOf(')');
		String x=s.substring(i+2,j-1);
		
        String[] y=splitPath(x);
        
        int len=y.length;
        System.out.print(y[0]);
        for(int a=1;a<len;a++)
        {
            System.out.print((char)conv(y[a]));

        }
    }
    
	public static void main (String[] args) {
		Scanner kb=new Scanner(System.in);
		String s=kb.nextLine();
		
		final_conv(s);
	}
}
