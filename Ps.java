import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Image;
import java.awt.List;
import java.applet.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import org.python.util.PythonInterpreter;
import org.python.core.*;
import java.util.*;
import jxl.*;
import jxl.read.biff.BiffException;
import java.net.*;
//import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.math.*;
/*
 <HTML>
 <BODY>
 <applet code="Ps" name="applet" width=400 height=1000>
 <PARAM NAME="bgImage"  VALUE="bg.GIF">
 </applet>
 </Body>
 </HTML>
 */


public class Ps extends Applet implements ActionListener,ItemListener
{
  
  class Sample
  {
    int age;
    String occ;
    String qua;
    String sq;
    String tag;
  }
  Boolean flag=false;
  TextField text,a1;
  List occ,qua;
  Image[]im=new Image[20];
  Image bg=null;
  int count1,x=110,y=300,z=0;
  String sq;
  String final_tag="";
  Sample s[] = new Sample[100];
  int count=0;
  CheckboxGroup cbg;
  public void inputData()
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
        count=i;
        break;
      }
    } 
  }
  public void bayesian()
  {
   String tag="",age="";
   int cn=0,num,i,j,a=0,cnt=0,ag=Integer.parseInt(a1.getText()),greater=0;
   for(i=0;i<count;i++)
   {
     if(text.getText().contains(s[i].sq))
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
       if(text.getText().contains(s[j].sq)&&st[i].equals(s[j].tag))
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
         if(s[j].age<=25&&text.getText().contains(s[j].sq)&&s[j].tag.equals(st[i]))
           ca[i]++;
       }
       if(age.equals("medium"))
       {
         if(s[j].age>25&&s[j].age<50&&text.getText().contains(s[j].sq)&&s[j].tag.equals(st[i]))
           ca[i]++;
       }
       if(age.equals("old"))
       {
         if(s[j].age>=50&&text.getText().contains(s[j].sq)&&s[j].tag.equals(st[i]))
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
       if(s[j].qua.equals(qua.getSelectedItem())&&text.getText().contains(s[j].sq)&&s[j].tag.equals(st[i]))
           cq[i]++;
     }
   }
   for(i=0;i<num;i++)
     pq[i]=cq[i]/c[i];
   for(i=0;i<num;i++)   
   {
     for(j=0;j<count;j++) 
     {
       if(s[j].occ.equals(occ.getSelectedItem())&&text.getText().contains(s[j].sq)&&s[j].tag.equals(st[i]))
           co[i]++;
     }
   }
   for(i=0;i<num;i++)
     po[i]=co[i]/c[i];
   for(i=0;i<num;i++)
   {
     result[i]=pa[i]*pq[i]*po[i]*p[i];
     System.out.println(st[i]+": "+result[i]);
   }
     for(i=1;i<num;i++)
     if(result[greater]<result[i])
     greater=i;
   final_tag=st[greater]; 
 }
  public void init()
  {
    setBackground(Color.LIGHT_GRAY);
    bg= getImage(getDocumentBase(),"bb.GIF");
    cbg=new CheckboxGroup();
    Checkbox on=new Checkbox("Personalized Search",cbg,true);
    Checkbox off=new Checkbox("Non-Personalized Search",cbg,true);
    Label header=new Label("Personalized Image Search on Queries based on user input", Label.RIGHT);
    Label query=new Label("Query: ", Label.LEFT);
    Label n=new Label("Name: ",Label.LEFT);
    Label q=new Label("Qualification: ",Label.RIGHT);
    TextField n1=new TextField(40);
    Label a=new Label("Age: ",Label.LEFT);
    Label o=new Label("Occupation: ",Label.LEFT);
    a1=new TextField(12);
    occ=new List(7,false);
    occ.add("IT Professional");
    occ.add("Professor");
    occ.add("Researcher");
    occ.add("Engineer");
    occ.add("Student");
    occ.add("Government Servent");
    occ.add("Photographer");
    qua=new List(6,false);
    qua.add("Computer Engg.");
    qua.add("Mechanical Engg.");
    qua.add("Biology");
    qua.add("Chemical Engg.");
    qua.add("Bio Technology");
    qua.add("ITI");
    qua.add("Political Science");
    qua.add("Chemistry");
    qua.add("Electrical Engg.");
    Button submit=new Button("Submit");
    text=new TextField(12);
    add(header);
    add(n);
    add(n1);
    add(a);
    add(a1);
    add(o);
    add(occ);
    add(q);
    add(qua);
    add(on);
    add(off);
    add(query);
    add(text);
    add(submit);
    on.addItemListener(this);
    off.addItemListener(this);
    n1.addActionListener(this);
    a1.addActionListener(this);
    occ.addActionListener(this);
    text.addActionListener(this);
    submit.addActionListener(this);
  }
  /*public static void main(String args[]) 
  {  
    Ps f = new Ps();
    f.setSize(new Dimension(500, 500));
    f.setTitle("Personalised Search");
    f.setVisible(true);
    
  }*/
  public void itemStateChanged(ItemEvent ie)
  {
    if(cbg.getSelectedCheckbox().getLabel().equals("Personalized Search"))
      flag=true;
    else
      flag=false;
  }
  public void actionPerformed(ActionEvent ae)
  {
    count1=0;

    try
    {
      File file = new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\data.txt");
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write("");
      bw.close();
      final_tag="";
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    //setBackground(Color.RED);
    if(flag==true)
    {
      inputData();
      bayesian();
    }
    File f1=new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src");
    File[] match = f1.listFiles(new FilenameFilter() {
       public boolean accept(File f1, String name) {
         return name.endsWith("jpg");
       }
     });
    for(int i=0;i<match.length;i++)
    {
      match[i].delete();
   //   start();
    }
    x=x++;
    y=y++;
   if(!text.getText().equals(""))
   {
     try
     {
       File file = new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\data.txt");
       FileWriter fw = new FileWriter(file.getAbsoluteFile());
       BufferedWriter bw = new BufferedWriter(fw);
       bw.write(text.getText()+" "+final_tag);
       bw.close();
       Process p = Runtime.getRuntime().exec("python searchpy.py");
       BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
       BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));  
       String s = stdInput.readLine();
       /*PythonInterpreter interpreter = new PythonInterpreter();
       File src = new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\searchpy.py");
       FileInputStream is =new FileInputStream(src);
       interpreter.execfile(is);
       is.close();*/
      /*interpreter.exec("from searchpy import srcq");
       PyObject someFunc = interpreter.get("srcq");
       //System.out.println(someFunc);
       PyObject result = someFunc.__call__();
       String realResult = (String) result.__tojava__(String.class);
       //System.out.println(realResult);*/
       //repaint();
     }
     catch(Exception e)
     {
       e.printStackTrace();
     }
     
     f1=new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src");
     File[] matchingFiles = f1.listFiles(new FilenameFilter() {
       public boolean accept(File f1, String name) {
         return name.endsWith("jpg");
       }
     });
     count1=matchingFiles.length;
     /*for(int i=0;i<count1;i++)
     {
       matchingFiles[i]=null;
     }
     matchingFiles = f1.listFiles(new FilenameFilter() {
       public boolean accept(File f1, String name) {
         return name.endsWith("jpg");
       }
     });*/
     //System.out.println(count1);
     /*for(int i=0;i<count1;i++)
     {
     System.out.println(matchingFiles[i].getName());
     }*/
     im=new Image[count1];
     for(int i=0;i<count1;i++)
     {
       try{
         BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\"+matchingFiles[i].getName()));
         int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
         BufferedImage resizeImage = new BufferedImage(250, 450, type);
         Graphics2D g = resizeImage.createGraphics();
         g.drawImage(originalImage, 0, 0, 250, 450, null);
         g.dispose();
         ImageIO.write(resizeImage, "jpg", new File("C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\"+matchingFiles[i].getName()));
       }
       catch(Exception e)
       {
         e.printStackTrace();
       }
       /*String path = "C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\"+matchingFiles[i].getName();
       f1=new File(path);
       try
       {
       im[i]=ImageIO.read(f1);
       Label label = new Label(new ImageIcon(im[i]));
       Frame f = new Frame();
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.getContentPane().add(label);
       f.pack();
       f.setLocation(i*x,y);
       }
       catch(Exception e)
       {}*/
       //im[i]=getImage(getDocumentBase(),matchingFiles[i].getName()); 
     //  System.out.println(matchingFiles[i].getName());
       if(z%3==0)
       {
         try
         {
           Runtime r=Runtime.getRuntime();
           String url="C:\\Users\\sony\\Documents\\NetBeansProjects\\Searchpy\\src\\src.html";
           String browser="C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe ";
           Process pc=r.exec(browser + url);
           //pc.waitFor();
         }
         catch(Exception e)
         {}
         z++;
       }
     }
   }
   //update();
   repaint();
  }
  public void update( Graphics g) { 
   paint(g); 
  }

  public void paint(Graphics g)
  {
    
    g.drawImage(bg,0,0,this); 
    for(int i=0;i<count1;i++)
    {
     //super.paint(g);
     
      //System.out.println("output is "+i);
     //g.drawImage(im[i] ,i*x,y,this); 
    }
   
  }
 
}
