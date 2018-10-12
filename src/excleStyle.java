import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import java.io.FileOutputStream;

/**
 * Created by liyangbd on 2018/10/10.
 */
public class excleStyle {
	public static void main( String[] args ){

		//新建excel报表
		HSSFWorkbook excel = new HSSFWorkbook();
		//添加一个sheet，名字叫"我的POI之旅"
		HSSFSheet hssfSheet = excel.createSheet("我的POI之旅");
		//往excel表格创建一行，excel的行号是从0开始的
		HSSFRow hssfRow = hssfSheet.createRow(0);
		//第一行创建第一个单元格
		HSSFCell hssfCell = hssfRow.createCell(0);
		//设置第一个单元格的值
		hssfCell.setCellValue("poi");


		//生成单元格样式
		HSSFCellStyle cellStyle = excel.createCellStyle();
		//新建font实体
		HSSFFont hssfFont = excel.createFont();
		//设置字体颜色
		hssfFont.setColor(HSSFColor.BLUE.index);
		//设置删除线   strikeout（删除线）
		hssfFont.setStrikeout(true);
		//设置是否斜体
		hssfFont.setItalic(true);
		//字体大小
		hssfFont.setFontHeightInPoints((short)24);
		hssfFont.setFontName("楷体");
		//粗体
		//hssfFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		//设置下滑线   1:有下滑线 0：没有
		hssfFont.setUnderline((byte)0);
		cellStyle.setFont(hssfFont);
		hssfCell.setCellStyle(cellStyle);

		FileOutputStream fout = null;
		try{
			fout = new FileOutputStream("D:/students.xls");
			excel.write(fout);
			fout.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
