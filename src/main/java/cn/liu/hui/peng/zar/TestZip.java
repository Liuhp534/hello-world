package cn.liu.hui.peng.zar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 
 *
 *
 * </p>
 * 
 * @author hz16092620
 * @date 2018年11月17日 下午12:57:23
 * @version
 */
public class TestZip {

    public static void main(String[] args) throws Exception {
	// 生成的ZIP文件名为Demo.zip
	String strZipName = "E:/201811_work/warp.zip";
	ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
	// 需要同时下载的两个文件result.txt ，source.txt
/*	File[] file1 = { new File("E:\\Java_PDF\\Spring Cloud微服务实战 - 副本 - 副本.pdf"), 
		new File("E:\\Java_PDF\\Netty权威指南 第2版 带书签目录 完整版.pdf"), 
		new File("E:\\Java_PDF\\Spring Cloud微服务实战.pdf"), 
		new File("E:\\Java_PDF\\Spring Cloud微服务实战 - 副本.pdf")};*/
	File[] file1 = { new File("E:\\Java_PDF\\Spring Cloud微服务实战 - 副本 - 副本.pdf")};
	for (int i = 0; i < file1.length; i++) {
	    FileInputStream fis = new FileInputStream(file1[i]);
	    out.putNextEntry(new ZipEntry(file1[i].getName()));
	    int len;
	    byte[] buffer = new byte[10240];
	    // 读入需要下载的文件的内容，打包到zip文件
	    while ((len = fis.read(buffer)) > 0) {
		out.write(buffer, 0, len);
	    }
	    out.closeEntry();
	    fis.close();
	}
	out.close();
	System.out.println("生成Demo.zip成功");
    }
}
