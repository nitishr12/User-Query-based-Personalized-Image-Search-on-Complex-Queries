import java.io.*;
public class fil
{
  public static void main(String[]args)
  {
    File f1=new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src");
    File[] matchingFiles = f1.listFiles(new FilenameFilter() {
      public boolean accept(File f1, String name) {
        return name.endsWith("jpg");
      }
    });
    int count=matchingFiles.length;
     for(int i=0;i<count;i++)
     {
       System.out.println("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\"+matchingFiles[i].getName());
     }
  }
}