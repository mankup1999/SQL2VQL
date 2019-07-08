import java.util.Scanner;

public class TEXT_HIGHLIGHT{

     public static void main(String []args){
        String black="\u001b[30m";
        String red="\u001b[31m";
        String green="\u001b[32m";
        String yellow="\u001b[33m";
        String blue="\u001b[34m";
        String magenta="\u001b[35m";
        String cyan="\u001b[36m";
        String white="\u001b[37m";
        String reset="\u001b[0m";
        
        Scanner kb=new Scanner(System.in);
        
        
        System.out.println("Font Highlighting: ");
        
        System.out.print("Enter your First Name: ");
        String FirstName=kb.next();
        System.out.print(red+FirstName);
        System.out.println(reset+" ");
        
        System.out.print("Enter your Middle Name: ");
        String MiddleName=kb.next();
        System.out.println(green+MiddleName);
        System.out.println(reset+" ");
        
        System.out.print("Enter your Last Name: ");
        String LastName=kb.next();
        System.out.println(yellow+LastName);
        System.out.println(reset+" ");
        
        
        
        System.out.print("Background Highlighting: ");
        int x=65;;
    String characterCode;
    for (int foreground = 30; foreground < 38; foreground++) {
        for (int background = 40; background < 48; background++) {
            characterCode = "\033[" + foreground + ";" + background + ";1m";
            System.out.print(characterCode + (char)x);
        }
        System.out.println(reset+" ");x++;
    }
     }
     
}
