package cn.fm.utils;

import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRHelper {
    /**
     * 进行图片的放大和转换,将图片放大到150%
     * @throws Exception
     */
    public static int convertFile (String  filename ) throws  Exception {
        //书写一条命令
        String cmd = "convert " + filename + " -scale 150% " + filename;
        Process process = Runtime.getRuntime().exec( cmd );
        return process.waitFor();
    }
    /**
     * 用来ocr识别之后返回一个getFile
     * @param filename
     * @return
     */
    public static GetFile ocrGetFile(String filename ) throws Exception{
        GetFile getFile = new GetFile();
        try {
            File tifFile = new File(filename);
            ITesseract instance = new Tesseract();
            //指定放着库文件夹的文件夹
            instance.setDatapath("/usr/local/share");
            instance.setLanguage("chi_sim");
            System.out.println( tifFile.canRead() );
            String result = instance.doOCR(tifFile);
            result = result.replaceAll("、" ,"");
            result = result.replaceAll(" ", "");
            String resultSplit[] = result.split("\\n");
            for( int i = 0 ; i < resultSplit.length; i ++ ) {
//                if( !resultSplit[i].equals( "" ) ) {
//                    System.out.println("-----------------" + i + "---------------------------");
//                    System.out.println(resultSplit[i]);
//                }
                if( resultSplit[i].equals( "" ) ) {
                    if(getFile.getGfname() == null ) {
                        getFile.setGfname( resultSplit[i] );
                    }
                    if( getFile.getGfcompany() == null && getFile.getGfname() != null) {
                        getFile.setGfcompany( resultSplit[i] );
                    }

                }
                if( getFile.getGfcompany() != null && getFile.getGfname() != null) {
                    break;
                }
            }

//            System.out.println( "````````````````````````华丽的分割线" + "````````````````````````" );

//            System.out.println( result );
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        RmFileUtils.rmFile();
        return getFile;

    }

    /**
     * 用来ocr识别之后返回一个companyFile
     * @param filename
     * @return
     */
    public static CompanyFile ocrCompanyFile(String filename ) throws Exception {
        CompanyFile companyFile = new CompanyFile();
        try {
            File tifFile = new File(filename);
            ITesseract instance = new Tesseract();
            //指定放着库文件夹的文件夹
            instance.setDatapath("/usr/local/share");
            instance.setLanguage("chi_sim");
            System.out.println( tifFile.canRead() );
            String result = instance.doOCR(tifFile);
            result = result.replaceAll("、" ,"");
            result = result.replaceAll(" ", "");
            String resultSplit[] = result.split("\\n");
            for( int i = 0 ; i < resultSplit.length; i ++ ) {
//                if( !resultSplit[i].equals( "" ) ) {
//                    System.out.println("-----------------" + i + "---------------------------");
//                    System.out.println(resultSplit[i]);
//                }
                if( resultSplit[i].equals( "" ) ) {
                    if(companyFile.getCfname() == null ) {
                        companyFile.setCfname( resultSplit[i] );
                    }
                    if( companyFile.getCflanguage() == null && companyFile.getCfname() != null) {
                        companyFile.setCflanguage( resultSplit[i] );
                    }

                }
                if( companyFile.getCflanguage() != null && companyFile.getCfname() != null) {
                    break;
                }
            }

//            System.out.println( "````````````````````````华丽的分割线" + "````````````````````````" );

//            System.out.println( result );
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        RmFileUtils.rmFile();
        return companyFile;

    }
}
