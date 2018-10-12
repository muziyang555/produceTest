package Excle;

/**
 * Created by liyangbd on 2018/9/28.
 */

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;

public class ExcelTest {
	public static void main(String[] args) {
		//新建excel报表
		HSSFWorkbook excel = new HSSFWorkbook();
		//添加一个sheet，名字叫"我的POI之旅"
		HSSFSheet hssfSheet = excel.createSheet("指令情况统计");

		//这个就是合并单元格
		//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
		//比如我要合并 第二行到第四行的    第六列到第八列     sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));



		CellRangeAddress regionTitle = new CellRangeAddress(0, 0, 0, 7);


		CellRangeAddress region = new CellRangeAddress(0, 0, 2, 3);
		CellRangeAddress region2 = new CellRangeAddress(0, 0, 4, 5);
		CellRangeAddress region3 = new CellRangeAddress(0, 0, 6, 7);
		CellRangeAddress region4 = new CellRangeAddress(0, 1, 0, 0);
		CellRangeAddress region5 = new CellRangeAddress(0, 1, 1, 1);
		//在sheet里增加合并单元格W
		hssfSheet.addMergedRegion(region);
		hssfSheet.addMergedRegion(region2);
		hssfSheet.addMergedRegion(region3);
		hssfSheet.addMergedRegion(region4);
		hssfSheet.addMergedRegion(region5);
		//生成第一行表头
		Row row = hssfSheet.createRow(0);
		Cell first = row.createCell(0);
		first.setCellValue("序号");
		Cell t2 = row.createCell(1);
		t2.setCellValue("单位名称");
		Cell t3 = row.createCell(2, 3);
		t3.setCellValue("宣传指令");
		Cell t4 = row.createCell(4, 5);
		t4.setCellValue("管控指令");
		Cell t5 = row.createCell(6);
		t5.setCellValue("通知");
		//生成第一行表头

		Row row1 = hssfSheet.createRow(1);
		Cell t12 = row1.createCell(2);
		t12.setCellValue("下达数");
		Cell t13 = row1.createCell(3);
		t13.setCellValue("未办理数");
		Cell t14 = row1.createCell(4);
		t14.setCellValue("下达数");
		Cell t15 = row1.createCell(5);
		t15.setCellValue("未办理数");
		Cell t16 = row1.createCell(6);
		t16.setCellValue("下达数");
		Cell t17 = row1.createCell(7);
		t17.setCellValue("未办理数");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("C:\\Users\\liyangbd\\Desktop\\test.xls");
			excel.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
