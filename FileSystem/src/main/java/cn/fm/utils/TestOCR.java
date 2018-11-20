package cn.fm.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
public class TestOCR {
    public static void ocr(String filename) {
        try {
            File tifFile = new File(filename);
            ITesseract instance = new Tesseract();
            //指定放着库文件夹的文件夹
            instance.setDatapath("/usr/local/share");
            instance.setLanguage("chi_sim");
            System.out.println( tifFile.canRead() );
            String result = instance.doOCR(tifFile);
//            result = result.replaceAll("、" ,"");
//            result = result.replaceAll(" ", "");
            String resultSplit[] = result.split("\\n");
           for( int i = 0 ; i < resultSplit.length; i ++ ) {
               if( !resultSplit[i].equals( "" ) ) {
                   System.out.println("-----------------" + i + "---------------------------");
                   System.out.println(resultSplit[i]);
               }
           }
            System.out.println( "````````````````````````华丽的分割线" + "````````````````````````" );

            System.out.println( result );
        } catch (TesseractException e) {
            e.printStackTrace();
        }


    }




        public static void main(String[] args) throws Exception
        {
            /**
             * 如果是文件夹 可以使用 文件夹.listFiles()遍历里面的文件
             * for (File file : testDataDir.listFiles())
             {
             cleanImage(file, destDir);
             }
             */

//            File testData = new File("/home/wxq/test.jpg");//某一张图片
//            //去噪后图片的存储地址
//            final String destDir = testData.getParent()+"/testOCR";
//            cleanImage(testData ,destDir);
            ocr("/home/wxq/test1.jpg");
//             Date date = new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//           String  a = format.format(date);
//             System.out.println(a);
//           System.out.println( OCRHelper.convertFile( "/home/wxq/test.jpg"));
        }

//        /**
//         *
//         * @param sfile
//         *            需要去噪的图像
//         * @param destDir
//         *            去噪后的图像保存地址
//         * @throws IOException
//         */
//        public static void cleanImage(File sfile, String destDir)
//                throws IOException
//        {
//            File destF = new File(destDir);
//            //如果文件夹不存在就新建
//            if (!destF.exists())
//            {
//                destF.mkdirs();
//            }
//            //读取这个图片文件到图片流中
//            BufferedImage bufferedImage = ImageIO.read(sfile);
//            //获取图片高和宽
//            int h = bufferedImage.getHeight();
//            int w = bufferedImage.getWidth();
//
//            // 灰度化
//            int[][] gray = new int[w][h];
//            for (int x = 0; x < w; x++)
//            {
//                for (int y = 0; y < h; y++)
//                {
//                    //获取这个像素点的像素的值
//                    int argb = bufferedImage.getRGB(x, y);
//                    // 图像加亮（调整亮度识别率非常高）
//                    int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
//                    int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
//                    int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
//                    if (r >= 255)
//                    {
//                        r = 255;
//                    }
//                    if (g >= 255)
//                    {
//                        g = 255;
//                    }
//                    if (b >= 255)
//                    {
//                        b = 255;
//                    }
//                    gray[x][y] = (int) Math
//                            .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)
//                                    * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
//                }
//            }
//
//            // 二值化
//            int threshold = ostu(gray, w, h);
//            BufferedImage binaryBufferedImage = new BufferedImage(w, h,
//                    BufferedImage.TYPE_BYTE_BINARY);
//            for (int x = 0; x < w; x++)
//            {
//                for (int y = 0; y < h; y++)
//                {
//                    if (gray[x][y] > threshold)
//                    {
//                        gray[x][y] |= 0x00FFFF;
//                    } else
//                    {
//                        gray[x][y] &= 0xFF0000;
//                    }
//                    binaryBufferedImage.setRGB(x, y, gray[x][y]);
//                }
//            }
//
//            // 矩阵打印
//            for (int y = 0; y < h; y++)
//            {
//                for (int x = 0; x < w; x++)
//                {
//                    if (isBlack(binaryBufferedImage.getRGB(x, y)))
//                    {
//                        System.out.print("*");
//                    } else
//                    {
//                        System.out.print(" ");
//                    }
//                }
//                System.out.println();
//            }
//            String name = sfile.getName();
//            String[] a = name.split("\\.");
//            System.out.println(a[0]);
////            String test = a[0] + ".tif";
//            ImageIO.write(binaryBufferedImage, "tif", new File(destDir, a[0] + ".tif"));
//        }
//
//        public static boolean isBlack(int colorInt)
//        {
//            Color color = new Color(colorInt);
//            if (color.getRed() + color.getGreen() + color.getBlue() <= 300)
//            {
//                return true;
//            }
//            return false;
//        }
//
//        public static boolean isWhite(int colorInt)
//        {
//            Color color = new Color(colorInt);
//            if (color.getRed() + color.getGreen() + color.getBlue() > 300)
//            {
//                return true;
//            }
//            return false;
//        }
//
//        public static int isBlackOrWhite(int colorInt)
//        {
//            if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730)
//            {
//                return 1;
//            }
//            return 0;
//        }
//
//        public static int getColorBright(int colorInt)
//        {
//            Color color = new Color(colorInt);
//            return color.getRed() + color.getGreen() + color.getBlue();
//        }
//
//        public static int ostu(int[][] gray, int w, int h)
//        {
//            int[] histData = new int[w * h];
//            // Calculate histogram
//            for (int x = 0; x < w; x++)
//            {
//                for (int y = 0; y < h; y++)
//                {
//                    int red = 0xFF & gray[x][y];
//                    histData[red]++;
//                }
//            }
//
//            // Total number of pixels
//            int total = w * h;
//
//            float sum = 0;
//            for (int t = 0; t < 256; t++)
//                sum += t * histData[t];
//
//            float sumB = 0;
//            int wB = 0;
//            int wF = 0;
//
//            float varMax = 0;
//            int threshold = 0;
//
//            for (int t = 0; t < 256; t++)
//            {
//                wB += histData[t]; // Weight Background
//                if (wB == 0)
//                    continue;
//
//                wF = total - wB; // Weight Foreground
//                if (wF == 0)
//                    break;
//
//                sumB += (float) (t * histData[t]);
//
//                float mB = sumB / wB; // Mean Background
//                float mF = (sum - sumB) / wF; // Mean Foreground
//
//                // Calculate Between Class Variance
//                float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
//
//                // Check if new maximum found
//                if (varBetween > varMax)
//                {
//                    varMax = varBetween;
//                    threshold = t;
//                }
//            }
//
//            return threshold;
//        }


}
