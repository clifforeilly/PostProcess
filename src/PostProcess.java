/**
 * Created by cliff on 04/02/14.
 */

//args
// 0=input folder
// 1=output folder
// 2=type
//  =1 post process the semantic similarity ms csv file onto one line
//  =2 post process the multi-line single sentence file onto one line
//  =3 rename csv files from LDA output
//  =4 split csv files by a column value, e.g. break out documents to individual files
// 3=binary for delete content of output folder before starting parse

import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PostProcess
{
    static String InputFileFolder = "";
    static String OutputFileFolder = "";
    static int type = 0;
    static List<String> nm = null;

    public static void findFiles(File[] files) {

        for (File file : files) {
            if (file.isDirectory()) {
                findFiles(file.listFiles()); // Calls same method again.
            } else {
                if(file.getName().equals("document-topic-distributions.csv"))
                {
                    nm.add(file.getAbsolutePath());
                }
            }
        }

    }

    public static void main(String[] args)
    {
        try
        {
            if(args.length>0)
            {
                System.out.println(args[0]);
                InputFileFolder=args[0];
                System.out.println(args[1]);
                OutputFileFolder=args[1];
                System.out.println(args[2]);
                type=Integer.parseInt(args[2]);

                if(type==4)
                {
                    File f = new File(InputFileFolder);
                    File[] matchingFiles = f.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".csv");
                        }
                    });
                    System.out.println("Scanning ... " + InputFileFolder);

                    for(File tf : matchingFiles)
                    {
                        CSVReader csvinput = new CSVReader(new FileReader(tf.getAbsolutePath()));
                        System.out.println("Reading csv file ...");
                        List csvinputdata = csvinput.readAll();
                        csvinput.close();
                        String[] row;
                        int SplitterCol = 0;
                        String ID = "bananalongboatharvey";
                        int OutFileCount = 1;
                        CSVWriter csvout = new CSVWriter(new FileWriter(OutputFileFolder + File.separator + "pp2-" + OutFileCount + "-" + tf.getName()));
                        boolean first = true;

                        for(int q=0 ; q<csvinputdata.size() ; q++)
                        {
                            Object ob = csvinputdata.get(q);
                            row=(String[]) ob;

                            if(!ID.equals(row[SplitterCol]))
                            {
                                if(!first)
                                {
                                    OutFileCount++;
                                    csvout.close();
                                    csvout = new CSVWriter(new FileWriter(OutputFileFolder + File.separator + "pp2-" + OutFileCount + "-" + tf.getName()));
                                }
                            }

                            csvout.writeNext(row);

                            ID = row[SplitterCol];
                            first=false;
                        }

                    }
                }


                if(type==3)
                {
                    File[] files = new File(InputFileFolder).listFiles();
                    CSVWriter csvout = new CSVWriter(new FileWriter(OutputFileFolder + File.separator + "main-output.csv"));

                    nm = new ArrayList<String>();
                    findFiles(files);

                    for(String tf : nm)
                    {
                        System.out.println(tf);

                        CSVReader csvinput = new CSVReader(new FileReader(tf));
                        System.out.println("Reading csv file ...");
                        List csvinputdata = csvinput.readAll();
                        csvinput.close();
                        String[] row;

                        for(int q=0 ; q<csvinputdata.size() ; q++)
                        {
                            Object ob = csvinputdata.get(q);
                            row=(String[]) ob;
                            String[] rowout = new String[row.length+1];
                            rowout[0] = tf;
                            int c = 1;

                            for(String s : row)
                            {
                                rowout[c] = row[c-1];
                                c++;
                            }

                            csvout.writeNext(rowout);
                        }

                    }

                    csvout.close();
                }

                if(type==1 || type==2)
                {

                    if(Integer.parseInt(args[3])==1)      //delete the output before parsing
                    {
                        File f = new File(OutputFileFolder);
                        File[] matchingFiles = f.listFiles();

                            if(matchingFiles!=null)
                        {
                            for(File tf : matchingFiles)
                            {
                                tf.delete();
                            }
                        }
                    }

                    File f = new File(InputFileFolder);
                    File[] matchingFiles = f.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".csv");
                        }
                    });
                    System.out.println("Scanning ... " + InputFileFolder);
                    int filecount=0;

                    for(File tf : matchingFiles)
                    {
                        filecount++;
                        CSVReader csvinput = new CSVReader(new FileReader(tf.getAbsolutePath()));
                        System.out.println("Reading csv file ...");
                        List csvinputdata = csvinput.readAll();
                        csvinput.close();
                        String[] row;
                        String[] rowout;
                        String prevRow = "1";
                        String prevRow2 = "23890098";

                        String ssPOS = "";
                        String ssPOSGroup = "";
                        String ssLemma = "";
                        String ssNoun = "";
                        String ssNamedEntity = "";
                        String ssFrame = "";
                        String ssFrameElements = "";
                        String ssFrameLUs = "";
                        String ssIsInheritedBy = "";
                        String ssPerspectivized = "";
                        String ssUses = "";
                        String ssUsedBy = "";
                        String ssHasSubFrame = "";
                        String ssInchoative = "";
                        String ssInchoativeStative = "";
                        String ssCausative = "";
                        String ssCausativeStatice = "";
                        String ssAllInheritedFrames = "";
                        String ssAllInheritingFrames = "";
                        String ssEarlier = "";
                        String ssInheritsFrom = "";
                        String ssLater = "";
                        String ssNeutral = "";
                        String ssReferred = "";
                        String ssReferring = "";
                        String ssSubFrameOf = "";
                        String ssEvokingLU = "";

                        int rowcount=0;
                        String trow = "";
                        String[] trowx = new String[5];

                        CSVWriter csvout = new CSVWriter(new FileWriter(OutputFileFolder + File.separator + "pp-" + tf.getName()));
                        for(int q=0 ; q<csvinputdata.size() ; q++)
                        {
                            Object ob = csvinputdata.get(q);
                            row=(String[]) ob;


                            if(type==1)
                            {

                                int a=0;

                                int DifferentFormatFactor = 5; // where in relation to the default start of 10 do the columns start!

                                String initial="";
                                int includedconfidence = 0;
                                int takeconfidence = 1 - DifferentFormatFactor;
                                int colstokeep=5 + includedconfidence;
                                int extrasnotneeded=6;
                                int[] datacols = new int[]{10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37};
                                String[] data = new String[datacols.length*2];

                                int r=0;
                                rowout = new String[colstokeep + (datacols.length*7)];

                                for(int c=0; c<colstokeep ; c++)
                                {
                                    rowout[c] = row[c];
                                    r++;
                                }

                                List<List<String>> Fdata = new ArrayList<List<String>>();
                                List<List<String>> Sdata = new ArrayList<List<String>>();

                                /*

                                POS
                                POSGroup
                                Lemma
                                Noun
                                NamedEntity
                                Frame
                                FrameElements
                                FrameLUs
                                IsInheritedBy
                                Perspectivized
                                Uses
                                UsedBy
                                HasSubFrame
                                Inchoative
                                InchoativeStative
                                Causative
                                CausativeStatice
                                AllInheritedFrames
                                AllInheritingFrames
                                Earlier
                                InheritsFrom
                                Later
                                Neutral
                                Referred
                                Referring
                                SubFrameOf
                                EvokingLU

                                 */

                                List<String> POS = new ArrayList<String>();
                                List<String> POSGroup = new ArrayList<String>();
                                List<String> Lemma = new ArrayList<String>();
                                List<String> Noun = new ArrayList<String>();
                                List<String> NamedEntity = new ArrayList<String>();
                                List<String> Frame = new ArrayList<String>();
                                List<String> FrameElements = new ArrayList<String>();
                                List<String> FrameLUs = new ArrayList<String>();
                                List<String> IsInheritedBy = new ArrayList<String>();
                                List<String> Perspectivized = new ArrayList<String>();
                                List<String> Uses = new ArrayList<String>();
                                List<String> UsedBy = new ArrayList<String>();
                                List<String> HasSubFrame = new ArrayList<String>();
                                List<String> Inchoative = new ArrayList<String>();
                                List<String> InchoativeStative = new ArrayList<String>();
                                List<String> Causative = new ArrayList<String>();
                                List<String> CausativeStatice = new ArrayList<String>();
                                List<String> AllInheritedFrames = new ArrayList<String>();
                                List<String> AllInheritingFrames = new ArrayList<String>();
                                List<String> Earlier = new ArrayList<String>();
                                List<String> InheritsFrom = new ArrayList<String>();
                                List<String> Later = new ArrayList<String>();
                                List<String> Neutral = new ArrayList<String>();
                                List<String> Referred = new ArrayList<String>();
                                List<String> Referring = new ArrayList<String>();
                                List<String> SubFrameOf = new ArrayList<String>();
                                List<String> EvokingLUs = new ArrayList<String>();

                                Fdata.add(POS);
                                Fdata.add(POSGroup);
                                Fdata.add(Lemma);
                                Fdata.add(Noun);
                                Fdata.add(NamedEntity);
                                Fdata.add(Frame);
                                Fdata.add(FrameElements);
                                Fdata.add(FrameLUs);
                                Fdata.add(IsInheritedBy);
                                Fdata.add(Perspectivized);
                                Fdata.add(Uses);
                                Fdata.add(UsedBy);
                                Fdata.add(HasSubFrame);
                                Fdata.add(Inchoative);
                                Fdata.add(InchoativeStative);
                                Fdata.add(Causative);
                                Fdata.add(CausativeStatice);
                                Fdata.add(AllInheritedFrames);
                                Fdata.add(AllInheritingFrames);
                                Fdata.add(Earlier);
                                Fdata.add(InheritsFrom);
                                Fdata.add(Later);
                                Fdata.add(Neutral);
                                Fdata.add(Referred);
                                Fdata.add(Referring);
                                Fdata.add(SubFrameOf);
                                Fdata.add(EvokingLUs);

                                List<String> sPOS = new ArrayList<String>();
                                List<String> sPOSGroup = new ArrayList<String>();
                                List<String> sLemma = new ArrayList<String>();
                                List<String> sNoun = new ArrayList<String>();
                                List<String> sNamedEntity = new ArrayList<String>();
                                List<String> sFrame = new ArrayList<String>();
                                List<String> sFrameElements = new ArrayList<String>();
                                List<String> sFrameLUs = new ArrayList<String>();
                                List<String> sIsInheritedBy = new ArrayList<String>();
                                List<String> sPerspectivized = new ArrayList<String>();
                                List<String> sUses = new ArrayList<String>();
                                List<String> sUsedBy = new ArrayList<String>();
                                List<String> sHasSubFrame = new ArrayList<String>();
                                List<String> sInchoative = new ArrayList<String>();
                                List<String> sInchoativeStative = new ArrayList<String>();
                                List<String> sCausative = new ArrayList<String>();
                                List<String> sCausativeStatice = new ArrayList<String>();
                                List<String> sAllInheritedFrames = new ArrayList<String>();
                                List<String> sAllInheritingFrames = new ArrayList<String>();
                                List<String> sEarlier = new ArrayList<String>();
                                List<String> sInheritsFrom = new ArrayList<String>();
                                List<String> sLater = new ArrayList<String>();
                                List<String> sNeutral = new ArrayList<String>();
                                List<String> sReferred = new ArrayList<String>();
                                List<String> sReferring = new ArrayList<String>();
                                List<String> sSubFrameOf = new ArrayList<String>();
                                List<String> sEvokingLUs = new ArrayList<String>();

                                Sdata.add(sPOS);
                                Sdata.add(sPOSGroup);
                                Sdata.add(sLemma);
                                Sdata.add(sNoun);
                                Sdata.add(sNamedEntity);
                                Sdata.add(sFrame);
                                Sdata.add(sFrameElements);
                                Sdata.add(sFrameLUs);
                                Sdata.add(sIsInheritedBy);
                                Sdata.add(sPerspectivized);
                                Sdata.add(sUses);
                                Sdata.add(sUsedBy);
                                Sdata.add(sHasSubFrame);
                                Sdata.add(sInchoative);
                                Sdata.add(sInchoativeStative);
                                Sdata.add(sCausative);
                                Sdata.add(sCausativeStatice);
                                Sdata.add(sAllInheritedFrames);
                                Sdata.add(sAllInheritingFrames);
                                Sdata.add(sEarlier);
                                Sdata.add(sInheritsFrom);
                                Sdata.add(sLater);
                                Sdata.add(sNeutral);
                                Sdata.add(sReferred);
                                Sdata.add(sReferring);
                                Sdata.add(sSubFrameOf);
                                Sdata.add(sEvokingLUs);

                                while(row[r].toLowerCase().equals("true"))
                                {
                                    for(int k=0 ; k<row.length+1-extrasnotneeded-colstokeep ; k++)
                                    {
                                        //if(data[k]==null)
                                        //{
                                          //  data[k]=row[datacols[k]];
                                            String tmp = row[datacols[k]-takeconfidence].trim();
                                            if(tmp.contains(" "))
                                            {
                                                String[] tmpsp = tmp.split(" ");
                                                for(String s : tmpsp)
                                                {
                                                    Fdata.get(k).add(s);
                                                }
                                            }
                                            else
                                            {
                                                Fdata.get(k).add(tmp);
                                            }

                                        //}
                                        //else
                                        //{
                                          //  data[k]=data[k] + " " + row[datacols[k]];
                                            //Fdata.get(k).add(row[datacols[k]]);
                                        //}
                                    }
                                    q++;
                                    ob = csvinputdata.get(q);
                                    row=(String[]) ob;

                                }

                                while(row[r].toLowerCase().equals("false"))
                                {
                                    for(int k=0 ; k<row.length+1-extrasnotneeded-colstokeep ; k++)
                                    {
                                        //if(data[datacols.length+k]==null)
                                        //{
                                          //  data[datacols.length+k]=row[datacols[k]];
                                        String tmp = row[datacols[k]-takeconfidence].trim();
                                        if(tmp.contains(" "))
                                        {
                                            String[] tmpsp = tmp.split(" ");
                                            for(String s : tmpsp)
                                            {
                                                Sdata.get(k).add(s);
                                            }
                                        }
                                        else
                                        {
                                            Sdata.get(k).add(tmp);
                                        }
                                        //}
                                        //else
                                        //{
                                          //  data[datacols.length+k]=data[datacols.length+k] + " " + row[datacols[k]];
                                            //Sdata.get(k).add(row[datacols[k]]);
                                        //}
                                    }


                                    //if this is not the last row
                                    if(q!=csvinputdata.size()-1)
                                    {
                                        q++;
                                        ob = csvinputdata.get(q);
                                        row=(String[]) ob;

                                        //if next row is true
                                        if(row[r].toLowerCase().equals("true"))
                                        {
                                           q--;
                                        }


                                    }
                                    else
                                    {
                                        break;
                                    }


                                }

                                /*
                                for(int c=0; c<data.length ; c++)
                                {
                                    System.out.println(c);
                                    rowout[r] = data[c].trim();
                                    r++;
                                }
                                */

                                int[] cnt = new int[Fdata.size() + Sdata.size()];
                                int[] cnty = new int[Fdata.size() * 3];

                                //count first set
                                for(int fl=0 ; fl<Fdata.size() ; fl++)
                                {
                                    int tm=0;
                                    for(int ll=0 ; ll<Fdata.get(fl).size() ; ll++)
                                    {
                                        if(!Fdata.get(fl).get(ll).replace(" ","").equals(""))
                                        {
                                            tm++;
                                        }
                                    }
                                    cnt[fl]=tm;
                                }

                                //count second set
                                for(int fl=0 ; fl<Sdata.size() ; fl++)
                                {
                                    int tm=0;
                                    for(int ll=0 ; ll<Sdata.get(fl).size() ; ll++)
                                    {
                                        if(!Sdata.get(fl).get(ll).replace(" ","").equals(""))
                                        {
                                            tm++;
                                        }
                                    }
                                    cnt[Fdata.size() + fl]=tm;
                                }


                                //count occurances
                                int cx=0;
                                for(List l : Fdata)
                                {
                                    List<String> ls = l;
                                    int xtm=0;
                                    for(String s : ls)
                                    {
                                        if(!s.replace(" ","").equals(""))
                                        {
                                            for(String s2 : Sdata.get(cx))
                                            {
                                                if(s2.equals(s))
                                                {
                                                    xtm++;
                                                }
                                            }
                                        }
                                    }
                                    cnty[cx]=xtm;
                                    cx++;
                                }

                                //count matches from first to second
                                int cy=0;
                                for(List l : Fdata)
                                {
                                    List<String> ls = l;
                                    int xtm=0;
                                    for(String s : ls)
                                    {
                                        boolean in2=false;
                                        if(!s.replace(" ","").equals(""))
                                        {
                                            for(String s2 : Sdata.get(cy))
                                            {
                                                if(s2.equals(s))
                                                {
                                                    in2=true;
                                                }
                                            }
                                        }
                                        if(in2)
                                        {
                                            xtm++;
                                        }
                                    }
                                    cnty[cx]=xtm;
                                    cx++;
                                    cy++;
                                }

                                //count matches from second to first
                                int cz=0;
                                for(List l : Sdata)
                                {
                                    List<String> ls = l;
                                    int xtm=0;
                                    for(String s : ls)
                                    {
                                        boolean in1=false;
                                        if(!s.replace(" ","").equals(""))
                                        {
                                            for(String s2 : Fdata.get(cz))
                                            {
                                                if(s2.equals(s))
                                                {
                                                    in1=true;
                                                }
                                            }
                                        }
                                        if(in1)
                                        {
                                            xtm++;
                                        }
                                    }
                                    cnty[cx]=xtm;
                                    cx++;
                                    cz++;
                                }

    /*
                                for(int fl=0 ; fl<Fdata.size() ; fl++)
                                {
                                    for(int ll=0 ; ll<Fdata.get(fl).size() ; ll++)
                                    {
                                        if(!Fdata.get(fl).get(ll).replace(" ","").equals(""))
                                        {
                                            for(int xfl=0 ; xfl<Sdata.size() ; xfl++)
                                            {
                                                int xtm=0;
                                                for(int xll=0 ; xll<Sdata.get(xfl).size() ; xll++)
                                                {
                                                    if(!Sdata.get(xfl).get(xll).replace(" ","").equals(""))
                                                    {
                                                        if(Fdata.get(fl).get(ll).trim().equals(Sdata.get(xfl).get(xll).trim()))
                                                        {
                                                            xtm++;
                                                        }
                                                    }
                                                }
                                                cnty[xfl]=xtm;
                                            }
                                        }
                                    }
                                }
    */

                                for(List<String> ls : Fdata)
                                {
                                    String tmp = "";

                                    for(String s : ls)
                                    {
                                        if(!s.replace(" ","").equals(""))
                                        {
                                            tmp = tmp + " " + s;
                                        }
                                    }

                                    rowout[r]=tmp.trim();
                                    r++;
                                }

                                for(List<String> ls : Sdata)
                                {
                                    String tmp = "";

                                    for(String s : ls)
                                    {
                                        if(!s.replace(" ","").equals(""))
                                        {
                                            tmp = tmp + " " + s;
                                        }
                                    }

                                    rowout[r]=tmp.trim();
                                    r++;
                                }

                                for(int c=0; c<cnt.length; c++)
                                {
                                    rowout[r] = String.valueOf(cnt[c]);
                                    r++;
                                }

                                for(int c=0; c<cnty.length; c++)
                                {
                                    rowout[r] = String.valueOf(cnty[c]);
                                    r++;
                                }

                                csvout.writeNext(rowout);
                                rowcount++;

                                System.out.println("Written a row: " + rowcount);
                            }     //type==1


                            if(type==2)
                            {
                                int startCol = 6;
                                int SentenceColumn = 3; //Change this when the column containing the sentence count changes
                                int SentenceColumn2 = 0;

                                boolean lastRow = false;
                                //if this is not the last row
                                if(q!=csvinputdata.size()-1)
                                {
                                     if(!prevRow.equals(row[SentenceColumn]) || !prevRow2.equals(row[SentenceColumn2]))
                                     {
                                         lastRow=true;
                                     }
                                }
                                else
                                {
                                    lastRow=true;
                                }


                                if(lastRow)
                                {
                                    rowout = new String[startCol + 26];

                                    for (int k = 0 ; k<trowx.length ; k++)
                                    {
                                        rowout[k]=trowx[k];
                                    }

                                    rowout[startCol + 0]=ssPOS.trim();
                                    rowout[startCol + 1]=ssPOSGroup.trim();
                                    rowout[startCol + 3]=ssLemma.trim();
                                    //rowout[startCol + 3]=ssNoun;
                                    rowout[startCol + 2]=ssNamedEntity.trim();
                                    rowout[startCol + 4]=ssFrame.trim();
                                    rowout[startCol + 5]=ssFrameElements.trim();
                                    rowout[startCol + 6]=ssFrameLUs.trim();
                                    rowout[startCol + 7]=ssIsInheritedBy.trim();
                                    rowout[startCol + 8]=ssPerspectivized.trim();
                                    rowout[startCol + 9]=ssUses.trim();
                                    rowout[startCol + 10]=ssUsedBy.trim();
                                    rowout[startCol + 11]=ssHasSubFrame.trim();
                                    rowout[startCol + 12]=ssInchoative.trim();
                                    rowout[startCol + 13]=ssInchoativeStative.trim();
                                    rowout[startCol + 14]=ssCausative.trim();
                                    rowout[startCol + 15]=ssCausativeStatice.trim();
                                    rowout[startCol + 16]=ssAllInheritedFrames.trim();
                                    rowout[startCol + 17]=ssAllInheritingFrames.trim();
                                    rowout[startCol + 18]=ssEarlier.trim();
                                    rowout[startCol + 19]=ssInheritsFrom.trim();
                                    rowout[startCol + 20]=ssLater.trim();
                                    rowout[startCol + 21]=ssNeutral.trim();
                                    rowout[startCol + 22]=ssReferred.trim();
                                    rowout[startCol + 23]=ssReferring.trim();
                                    rowout[startCol + 24]=ssSubFrameOf.trim();
                                    rowout[startCol + 25]=ssEvokingLU.trim();

                                    csvout.writeNext(rowout);
                                    rowcount++;

                                    System.out.println("Written a row: " + rowcount);

                                    ssPOS = "";
                                    ssPOSGroup = "";
                                    ssLemma = "";
                                    //ssNoun = "";
                                    ssNamedEntity = "";
                                    ssFrame = "";
                                    ssFrameElements = "";
                                    ssFrameLUs = "";
                                    ssIsInheritedBy = "";
                                    ssPerspectivized = "";
                                    ssUses = "";
                                    ssUsedBy = "";
                                    ssHasSubFrame = "";
                                    ssInchoative = "";
                                    ssInchoativeStative = "";
                                    ssCausative = "";
                                    ssCausativeStatice = "";
                                    ssAllInheritedFrames = "";
                                    ssAllInheritingFrames = "";
                                    ssEarlier = "";
                                    ssInheritsFrom = "";
                                    ssLater = "";
                                    ssNeutral = "";
                                    ssReferred = "";
                                    ssReferring = "";
                                    ssSubFrameOf = "";
                                    ssEvokingLU = "";

                                }


                                ssPOS = ssPOS.trim() + " " + row[startCol + 0].trim();
                                ssPOSGroup = ssPOSGroup.trim()  + " " + row[startCol + 1].trim();
                                ssLemma = ssLemma.trim()  + " " + row[startCol + 3].trim();
                                //ssNoun = ssNoun + " " + row[startCol + 3];
                                ssNamedEntity = ssNamedEntity.replace("O","").trim()  + " " + row[startCol + 2].trim();
                                ssFrame = ssFrame.trim()  + " " + row[startCol + 4].trim();
                                ssFrameElements = ssFrameElements.trim()  + " " + row[startCol + 5].trim();
                                ssFrameLUs = ssFrameLUs.trim()  + " " + row[startCol + 6].trim() ;
                                ssIsInheritedBy = ssIsInheritedBy.trim()  + " " + row[startCol + 7].trim();
                                ssPerspectivized = ssPerspectivized.trim()  + " " + row[startCol + 8].trim();
                                ssUses = ssUses.trim()  + " " + row[startCol + 9].trim();
                                ssUsedBy = ssUsedBy.trim()  + " " + row[startCol + 10].trim();
                                ssHasSubFrame = ssHasSubFrame.trim()  + " " + row[startCol + 11].trim();
                                ssInchoative = ssInchoative.trim()  + " " + row[startCol + 12].trim();
                                ssInchoativeStative = ssInchoativeStative.trim()  + " " + row[startCol + 13].trim();
                                ssCausative = ssCausative.trim()  + " " + row[startCol + 14].trim();
                                ssCausativeStatice = ssCausativeStatice.trim()  + " " + row[startCol + 15].trim();
                                ssAllInheritedFrames = ssAllInheritedFrames.trim()  + " " + row[startCol + 16].trim();
                                ssAllInheritingFrames = ssAllInheritingFrames.trim()  + " " + row[startCol + 17].trim();
                                ssEarlier = ssEarlier.trim()  + " " + row[startCol + 18].trim();
                                ssInheritsFrom = ssInheritsFrom.trim()  + " " + row[startCol + 19].trim();
                                ssLater = ssLater.trim()  + " " + row[startCol + 20].trim();
                                ssNeutral = ssNeutral.trim()  + " " + row[startCol + 21].trim();
                                ssReferred = ssReferred.trim()  + " " + row[startCol + 22].trim();
                                ssReferring = ssReferring.trim()  + " " + row[startCol + 23].trim();
                                ssSubFrameOf = ssSubFrameOf.trim()  + " " + row[startCol + 24].trim();
                                ssEvokingLU = ssEvokingLU.trim()  + " " + row[startCol + 25].trim();


                                trow = row[SentenceColumn];
                                prevRow = trow;
                                prevRow2 = row[SentenceColumn2];

                                trowx[0]=row[0];
                                trowx[1]=row[1];
                                trowx[2]=row[2];
                                trowx[3]=row[3];
                                trowx[4]=row[4];
                            }

                        }

                        csvout.close();
                    }
                }
                }
                else
                {
                    System.out.println("Error - no parameters supplied");
                }

        }
        catch (Exception ex)
        {
            System.out.println("Error:-" + ex.toString() + ", " + ex.getMessage() + ", " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
}