import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbd on 2018/9/21.
 */
public class readTmpFile {

	//返回数组
	public static List<String> readTmpAsList(){
		String filePath = "D:\\IdeaProjects\\B\\src\\Sources\\tmp.txt";

		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				ArrayList<String> ayyarsListText = new ArrayList<String>();
				// 得到文件中的数据
				while ((lineTxt = bufferedReader.readLine()) != null) {
					ayyarsListText.add(lineTxt);
				}
				read.close();

				System.out.println("结果:" + ayyarsListText);
				for (String string : ayyarsListText) {
					System.out.println(string);
				}
				return ayyarsListText;
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

	//返回字符串
	public static String readTmpAsString(){
		String filePath = "D:\\IdeaProjects\\B\\src\\Sources\\tmp.txt";

		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				ArrayList<String> ayyarsListText = new ArrayList<String>();
				// 得到文件中的数据
				while ((lineTxt = bufferedReader.readLine()) != null) {
					ayyarsListText.add(lineTxt);
				}
				read.close();

				System.out.println("结果:" + ayyarsListText);
				for (String string : ayyarsListText) {
					System.out.println(string);
				}
				return ayyarsListText.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

//	public static void main(String[] args) {
//		String a = readTmpAsString();
//		List list = readTmpAsList();
//		System.out.println(">--"+list);
//	}
}
