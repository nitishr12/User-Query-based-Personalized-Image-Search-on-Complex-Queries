import java.io.*;
import java.util.*;
import jxl.*;
import jxl.read.biff.BiffException;
//import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.math.*;
public class Demo
{
  static Sample s[] = new Sample[100];
  static int count=0;
  public static void inputData()
  { 
    for(int i=0;i<100;i++)
      s[i] = new Sample();  
    Workbook wb=null;
    Sheet st=null;
    Cell c=null;
    try
    {
      wb=Workbook.getWorkbook(new File("Data_Set.xls"));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    try
    {
     st=wb.getSheet("Sheet1");
    }
    catch(Exception e)
    {
      System.out.println("Exception"+e);
    }   
    int i=0;
    while(true)
    {
      try
      {  
        c=st.getCell(1,i+1);
        s[i].age = Integer.parseInt(c.getContents());
        c=st.getCell(2,i+1); 
        s[i].qua = c.getContents();
        c=st.getCell(3,i+1);
        s[i].occ = c.getContents();
        c=st.getCell(4,i+1);
        s[i].sq=c.getContents();
        c=st.getCell(5,i+1);
        s[i].tag=c.getContents();
        i+=1;
      }
      catch(Exception e)
      {
        System.out.println("Hello"+i);
        count=i;
        break;
      }
    } 
  }
  public static void main(String[]args)throws Exception
  { 
    inputData();    
    bayesian();
  }
  public static void bayesian()
  {
   String tag="",age="";
   int cn=0,num,i,j,a=0,cnt=0,ag=29,greater=0;
   for(i=0;i<count;i++)
   {
     if(s[i].sq.equals("Orange"))
     {
       if(!tag.contains(s[i].tag))
       {
         tag=tag+s[i].tag+' ';
         cn++;
       }
       cnt++;
     }
   }
   num=cn;
   String st[]=new String[num];
   for(i=0,j=0;i<tag.length();i++)
   {
     if(tag.charAt(i)==' ')
     {
       st[a++]=tag.substring(j,i);
       j=i+1;
     }
   }
   double c[]=new double[num]; 
   double ca[]=new double[num];
   double cq[]=new double[num];
   double co[]=new double[num];
   for(i=0;i<num;i++)   
   {
     for(j=0;j<count;j++)     
     {
       if(s[j].sq.equals("Orange")&&st[i].equals(s[j].tag))
       {
            c[i]++;
       }
     }
   }
   double p[]=new double[num];
   double pa[]=new double[num];
   double pq[]=new double[num];
   double po[]=new double[num];
   double result[]=new double[num];
   for(i=0;i<num;i++)
   {
     p[i]=c[i]/cnt;
   }
   if(ag<=25)
     age="youth";
   else if(ag>25&&ag<50)
     age="medium";
   else
     age="old";
   for(i=0;i<num;i++)   
   {
     for(j=0;j<count;j++) 
     {
       if(age.equals("youth"))
       {
         if(s[j].age<=25&&s[j].sq.equals("Orange")&&s[j].tag.equals(st[i]))
           ca[i]++;
       }
       if(age.equals("medium"))
       {
         if(s[j].age>25&&s[j].age<50&&s[j].sq.equals("Orange")&&s[j].tag.equals(st[i]))
           ca[i]++;
       }
       if(age.equals("old"))
       {
         if(s[j].age>=50&&s[j].sq.equals("Orange")&&s[j].tag.equals(st[i]))
           ca[i]++;
       }
     }
   }
   
   for(i=0;i<num;i++)
     pa[i]=ca[i]/c[i];
   for(i=0;i<num;i++)   
   {
     for(j=0;j<count;j++) 
     {
       if(s[j].qua.equals("Computer Engg.")&&s[j].sq.equals("Orange")&&s[j].tag.equals(st[i]))
           cq[i]++;
     }
   }
   for(i=0;i<num;i++)
     pq[i]=cq[i]/c[i];
   for(i=0;i<num;i++)   
   {
     for(j=0;j<count;j++) 
     {
       if(s[j].occ.equals("IT Professional")&&s[j].sq.equals("Orange")&&s[j].tag.equals(st[i]))
           co[i]++;
     }
   }
   for(i=0;i<num;i++)
     po[i]=co[i]/c[i];
   for(i=0;i<num;i++)
     result[i]=pa[i]*pq[i]*po[i]*p[i];  
   for(i=1;i<num;i++)
     if(result[greater]<result[i])
     greater=i;
   System.out.println(st[greater]);  
 }
}

class Sample
{
  int age;
  String occ;
  String qua;
  String sq;
  String tag;
}