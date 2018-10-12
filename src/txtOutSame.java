import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by liyangbd on 2018/9/19.
 */
public class txtOutSame {


	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("a");
		list.add("c");
		list.add("c");
		list.add("a");
		list.add("d");


		ArrayList<String> arr = new ArrayList<>();

		// set集合去重从
		// method01(list);

		// 创建新集合，如果不包含就添加进去
		//method02(list, arr);

		testReadFile();

	}

	public static void method02(List<String> list, ArrayList<String> arr) {
		for (int i = 0; i < list.size(); i++) {

			if (!arr.contains(list.get(i))) {
				arr.add(list.get(i));
			}

		}

		for (String string : arr) {
			System.out.println(string);
		}
	}

	public static void method01(List<String> list) {
		HashSet<String> set = new HashSet<>();
		set.addAll(list);
		for (String string : set) {
			System.out.println(string);
		}
	}


	/**
	 * 功能说明：读取文本文件，并去除重复字段
	 * 功能：Java读取txt文件的内容 步骤：
	 * 1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 *
	 * @param filePath
	 */

	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
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
				ArrayList<String> resultList = new ArrayList<String>();
// 去除文件中的重复数据
				for (String item : ayyarsListText) {
					if (!resultList.contains(item)) {
						resultList.add(item);
					}
				}
				System.out.println("结果:" + resultList);
				for (String string : resultList) {
					System.out.println("'DEBT_" + string + "',");
				}
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}


	}

	public static void testReadFile() {
		String filePath = "C:\\Users\\liyangbd\\Desktop\\property.txt";
		readTxtFile(filePath);
	}


}
