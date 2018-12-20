package cn.liu.hui.peng.io; 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * @author	hz16092620 
 * @date	2018年7月12日 下午4:55:43
 * @version      
 */
public class InputAndOutPutStream {
    
    public static void main(String[] args) {
	//inputSome();
	//outSome();
	//outputWriterSome();
	//inputReaderSome();
	outputWriterSome2();
	/*PrintWriter print = new PrintWriter(System.out);
	print.println("aaa你好");*/
	//testObjectOutputStream();
    }
    
    /**输出对象，需要加入序列化*/
    static void testObjectOutputStream() {
	try (OutputStream out = new FileOutputStream("E:/201807_work/test.txt", false);
		 ObjectOutput objOut = new ObjectOutputStream(out)) {
	    objOut.writeObject(new Dog("john"));
	    /*String s = "aaaaa";
	    out.write(s.getBytes(), 3, 2);//这个字节的位置和长度
*/	} catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        } finally {
        }
    }
    
    static class Dog implements Serializable {

	/**
	 * <p>
	 * 
	 *
	 *
	 * </p>
	 *  
	 * @author	hz16092620 
	 * @date	2018年9月8日 下午4:05:14
	 * @version     
	 */ 
	private static final long serialVersionUID = 2919664442265258816L;
	private String name;

	Dog(String name) {
	    this.name = name;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	@Override
	public String toString() {
	    return this.name;
	}
	
	

    }
    
    /**特殊读取流*/
    public static void inputSome()  {
	try (InputStream in = new FileInputStream("E:/201807_work/test.txt"); Reader reader = new InputStreamReader(in)) {
	    int i = reader.read();
	    while (i != -1) {
		System.out.println((char)i);
		i = reader.read();
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        } 
    }
    
    /**输出流*/
    public static void outSome() {
	try (OutputStream out = new FileOutputStream("E:/201807_work/test.txt", true)) {
	    String s = "aaaaa";
	    out.write(s.getBytes(), 3, 2);//这个字节的位置和长度
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        } 
    }
    
    /**阅读器*/
    public static void inputReaderSome() {
	try (Reader reader = new FileReader("E:/201807_work/test.txt")) {
	    char[] chars = new char[1024];
	    int i = -1;
	    while ((i = reader.read(chars, 0, chars.length)) != -1) {
		System.out.println(i);
		String str = new String(chars, 0, i);
		System.out.println(str);
	    }
	    //reader.read(str);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    /**书写器，字符，采用unicode编码*/
    public static void outputWriterSome() {
	try (Writer writer = new FileWriter("E:/201807_work/test.txt")) {
	    String test = "你好啊world";
	    writer.write(test, 2, 6);
	} catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    /**书写器，字符，采用unicode编码*/
    public static void outputWriterSome2() {
	try (Writer writer = new OutputStreamWriter(new FileOutputStream("E:/201807_work/test.txt"))) {
	    String test = "你好啊world\n";
	    writer.write(test, 0, test.length());
	} catch (IOException e) {
	    e.printStackTrace();
        }
    }

}
 