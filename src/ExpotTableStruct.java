import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created by liyang on 2018/1/24.
 */
public class ExpotTableStruct {
    public static void main(String[] args) {
        //getTabIndex("IRL_ACCT_INT");
        //getColKeyDesc("AC_SUBJECT");
        //getTableKeyDesc();
        //tranTypeLength(type,length);
        //System.out.println("TIMESTAMP(6)".substring(0,8));
        //getTabIndex("FM_FAKE_COIN_DEF");
        //抽数据表方法：deal
        //deal();
        //getKeyToTable("BASE_ACCT_NO");
        //关键字找描述
		//keyToDesc();
		substring.file("03");
		toExcletest();


    }
    //todo:获取TABLE_NAME--TABLE_DESC
    private static List<Table>  getTableKeyDesc() {
        Connection conn = null;
        List<Table> tabList = new ArrayList<Table>();
        try {
            DataBase db = new DataBase();
            conn = db.getConn();
            final String sql = "SELECT TABLE_NAME,COMMENTS FROM user_tab_comments ORDER BY TABLE_NAME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setTable_name(rs.getString("TABLE_NAME"));
                //处理"/"
                String tableDesc = rs.getString("COMMENTS");
//                System.out.println(tableDesc);
//                System.out.println(null!=tableDesc);
//                System.out.println("".equals(tableDesc));
                if (null!=tableDesc&&(tableDesc.contains("/")||tableDesc.contains("?"))){
                    tableDesc=tableDesc.replace("/","-");
                    tableDesc=tableDesc.replace("?","-");
                    table.setTable_desc(tableDesc);
                }else{
                    table.setTable_desc(tableDesc);
                }
                tabList.add(table);
            }
            //System.out.println(tabList.toString());
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return tabList;
    }
    //todo:获取col_name.data_type.col_desc
    private static List<Table> getColKeyDesc(String table_name) {
        Connection conn = null;
        List<Table> colList = new ArrayList<Table>();
        try {
            DataBase db = new DataBase();
            conn = db.getConn();
                String sql = "SELECT t.table_name,t.column_name,t.data_type,t.data_length ,t.data_precision,t.data_scale,t1.comments,t.nullable FROM user_tab_cols t,user_col_comments t1 WHERE  t.table_name=t1.table_name and t.column_name = t1.column_name AND  t.table_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //System.out.println(sql);
            ps.setString(1,table_name);
//            System.out.println(table_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setCol_name(rs.getString("column_name"));
                String type = rs.getString("data_type");
                String length = rs.getString("data_length");
                if ("TIMESTAMP(6)".equals(type)){
                    table.setData_type(type.substring(0,8));
                    table.setData_length(tranLength(type,length));
                }else if ("NUMBER".equals(type)){
                    table.setData_type(type);
                    if (!("0".equals(rs.getString("data_scale")))){
                        table.setData_length(rs.getString("data_precision")+","+rs.getString("data_scale"));
                    }else {
                        table.setData_length(rs.getString("data_precision"));
                    }
                } else {
                    table.setData_type(type);
                    table.setData_length(tranLength(type,length));
                }
                table.setCol_desc(rs.getString("comments"));
                table.setNull_bale(rs.getString("nullable"));
                table.setData_precision(rs.getString("data_precision"));
                table.setData_scale(rs.getString("data_scale"));
                colList.add(table);
            }
            //System.out.println(colList.toString());
            rs.close();
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return colList;
    }
    //todo:获取主键
    private static List<Table> getTabPK(String table_name) {
        Connection conn = null;
        List<Table> pkList = new ArrayList<Table>();
        try {
            DataBase db = new DataBase();
            conn = db.getConn();
            String sql = "SELECT cu.column_name pkname FROM user_cons_columns cu, user_constraints au  WHERE cu.constraint_name= au.constraint_name and au.constraint_type='P' and au.table_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //System.out.println(sql);
            ps.setString(1,table_name);
            //System.out.println(table_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setPk(rs.getString("pkname"));
                pkList.add(table);
            }
            //System.out.println(pkList.toString());
            rs.close();
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return pkList;
    }
    //todo:获取索引
    private static List<Table> getTabIndex(String table_name) {
        Connection conn = null;
        List<Table> pkList = new ArrayList<Table>();
        try {
            DataBase db = new DataBase();
            conn = db.getConn();
            String sql = "SELECT i.index_name, i.table_name, i.uniqueness,c.column_name,c.column_position from user_indexes i,user_ind_columns c where i.index_name=c.index_name and i.table_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //System.out.println(sql);
            ps.setString(1,table_name);
            System.out.println(table_name);
            ResultSet rs = ps.executeQuery();
            //System.out.println(rs.next());
            while (rs.next()) {
                Table table = new Table();
                table.setIndex(rs.getString("index_name"));
                table.setIndex_col_name(rs.getString("column_name"));
                pkList.add(table);
            }
            System.out.println(pkList.toString());
            rs.close();
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return pkList;
    }
    //TODO:列名查表名
    private static List<Table> getKeyToTable(String col_name) {
        Connection conn = null;
        List<Table> pkList = new ArrayList<Table>();
        try {
            DataBase db = new DataBase();
            conn = db.getConn();
            String sql = "SELECT t.table_name,t.column_name FROM user_tab_cols t WHERE  t.column_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            System.out.println("sql"+sql);
            ps.setString(1,col_name);
            System.out.println("参数"+col_name);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs.next());
            while (rs.next()) {
                Table table = new Table();
                table.setTable_name(rs.getString("table_name"));
                pkList.add(table);
            }
            System.out.println("结果集"+pkList.toString());
            rs.close();
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return pkList;
    }

    //todo:生成excl
    public static void toExcle(String table_name, String table_desc, List<Table> excllist, List<Table> pkList, List<Table> indexList){
        //第一步创建workbook
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步创建sheet
        HSSFSheet sheet = null;
        if (null!=table_desc){
             sheet = wb.createSheet(table_name);
        }else {
            sheet = wb.createSheet(table_name);
        }
        //第三步创建行row:
        // 添加表头0行
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);         //第一个单元格
        cell0.setCellValue("表名:");
        cell0 = row0.createCell(1);                   //第二个单元格
        if (null!=table_desc){
            cell0.setCellValue(table_name+":"+table_desc);
        }else {
            cell0.setCellValue(table_name);
        }
//        cell0 = row0.createCell(2);         //第三个单元格
//        cell0.setCellValue("主键:");
//        cell0 = row0.createCell(3);         //第三个单元格
//        for (int i=0;i<pkList.size();i++){
//            cell0.setCellValue(pkList.get(i).getPk());
//        }
        //一行
        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell1 = row1.createCell(0);         //第一个单元格
        cell1.setCellValue("主键");
        for (int i = 0;i<pkList.size();i++){
            cell1 = row1.createCell(i+1);
            cell1.setCellValue(pkList.get(i).getPk());
        }

        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cell2 = row2.createCell(0);         //第一个单元格
        cell2.setCellValue("索引");
                 //第二个单元格
        for (int i = 0;i<pkList.size();i++){
            cell2 = row2.createCell(i+1);
            cell2.setCellValue(indexList.get(i).getIndex_col_name()+":"+indexList.get(i).getIndex()+";");
        }

        // 添加表头1行
        HSSFRow row = sheet.createRow(3);
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中
        //第四步创建单元格
        HSSFCell cell = row.createCell(0);         //第一个单元格
        cell.setCellValue("字段名");                  //设定值
        //cell.setCellStyle(style);                   //内容居中
        cell = row.createCell(1);                   //第二个单元格
        cell.setCellValue("数据类型");
        //cell.setCellStyle(style);
        cell = row.createCell(2);                   //第三个单元格
        cell.setCellValue("数据长度");
        //cell.setCellStyle(style);
        cell = row.createCell(3);                   //第四个单元格
        cell.setCellValue("字段描述");
        cell = row.createCell(4);                   //第五个单元格
        cell.setCellValue("是否必输");
        cell = row.createCell(5);                   //第五个单元格
        //cell.setCellValue("索引名");
        //cell.setCellStyle(style);
        //第五步插入数据
        //List<Table> list = getErrorCondition();
        List<Table> list = excllist;
        for (int i = 0; i < list.size(); i++) {
            Table Table = list.get(i);
            //创建行
            row = sheet.createRow(i+4);
            //创建单元格并且添加数据
            row.createCell(0).setCellValue(Table.getCol_name());
            row.createCell(1).setCellValue(Table.getData_type());
            row.createCell(2).setCellValue(Table.getData_length());
            row.createCell(3).setCellValue(Table.getCol_desc());
            row.createCell(4).setCellValue(Table.getNull_bale());
            row0.createCell(3).setCellValue(Table.getPk());

        }

        //第六步将生成excel文件保存到指定路径下
        try {
            FileOutputStream fout = new FileOutputStream("D:\\Users\\liyang\\Desktop\\ceshi\\"+table_name+".xls");
            wb.write(fout);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel文件生成成功...");
    }


	public static void toExcletest(){
		//第一步创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		//第二步创建sheet
		HSSFSheet sheet = wb.createSheet("sheet");

		//第三步创建行row:
		// 添加表头0行
		//页数

		int a =1;
		int b = a+1;
		int num = 3/40+1;
		for (int j=0;j<num;j++) {
		//合并单元格
		//标题
			//每也世行
		CellRangeAddress regionTitle = new CellRangeAddress(j*52+0, j*52+0, 0, 7);
		sheet.addMergedRegion(regionTitle);
		//币种
		CellRangeAddress regionCcy = new CellRangeAddress(j*52+1, j*52+1, 4, 7);
		sheet.addMergedRegion(regionCcy);
		//账号
		CellRangeAddress regionBaseAcctNo = new CellRangeAddress(j*52+2, j*52+2, 0, 3);
		sheet.addMergedRegion(regionBaseAcctNo);
		//账户序号
		CellRangeAddress regionAcctSeqNo = new CellRangeAddress(j*52+2, j*52+2, 4, 7);
		sheet.addMergedRegion(regionAcctSeqNo);
		//起始日期
		CellRangeAddress regionStartDate = new CellRangeAddress(j*52+3, j*52+3, 0, 3);
		sheet.addMergedRegion(regionStartDate);
		//结束日期
		CellRangeAddress regionEndDate = new CellRangeAddress(j*52+3, j*52+3, 4, 7);
		sheet.addMergedRegion(regionEndDate);

		//所有单元格边框



		//单元格样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
		//垂直居中
		boderStyle.setAlignment(HorizontalAlignment.CENTER);

//		boderStyle.setBorderBottom(BorderStyle.DOUBLE); //下边框
//		boderStyle.setBorderLeft(BorderStyle.DOUBLE);//左边框
//		boderStyle.setBorderTop(BorderStyle.DOUBLE);//上边框
//		boderStyle.setBorderRight(BorderStyle.DOUBLE);//右边框




			HSSFRow row0 = sheet.createRow(j*52+0);
			HSSFCell cell0 = row0.createCell(0);         //第一个单元格
			cell0.setCellValue("中信百信银行账户明细清单");
			cell0.setCellStyle(boderStyle);

//		cell0 = row0.createCell(1);                   //第二个单元格
//		cell0.setCellValue("这是啥");
//
//        cell0 = row0.createCell(2);         //第三个单元格
//        cell0.setCellValue("主键:");
//        cell0 = row0.createCell(3);         //第三个单元格
//
//		cell0.setCellValue("户名：");

			//一行


			HSSFRow row1 = sheet.createRow(j*52+1);
			HSSFCell cell1 = row1.createCell(0);         //第一个单元格
			cell1.setCellValue("户名：");


			cell1 = row1.createCell(4);                   //第二个单元格
			cell1.setCellValue("币种:CNY  ");

			//第二行
			HSSFRow row2 = sheet.createRow(j*52+2);
			HSSFCell cell2 = row2.createCell(0);         //第一个单元格
			cell2.setCellValue("账号：");


			cell2 = row2.createCell(4);                   //第二个单元格
			cell2.setCellValue("序号:");


			//第三行
			HSSFRow row3 = sheet.createRow(j*52+3);
			HSSFCell cell3 = row3.createCell(0);         //第一个单元格
			cell3.setCellValue("起始日期：");


			cell3 = row3.createCell(4);                   //第二个单元格
			cell3.setCellValue("结束日期:");

		/*
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1 = row1.createCell(0);         //第一个单元格
		cell1.setCellValue("主键");
		for (int i = 0;i<pkList.size();i++){
			cell1 = row1.createCell(i+1);
			cell1.setCellValue(pkList.get(i).getPk());
		}
/*
		HSSFRow row2 = sheet.createRow(2);
		HSSFCell cell2 = row2.createCell(0);         //第一个单元格
		cell2.setCellValue("索引");
		//第二个单元格
		for (int i = 0;i<pkList.size();i++){
			cell2 = row2.createCell(i+1);
			cell2.setCellValue(indexList.get(i).getIndex_col_name()+":"+indexList.get(i).getIndex()+";");
		}
		*/
			// 添加表头1行
			HSSFRow row = sheet.createRow(j*52+4);
			HSSFCellStyle style = wb.createCellStyle();
			//style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中
			//第四步创建单元格
			HSSFCell cell = row.createCell(0);         //第一个单元格
			cell.setCellValue("序号");                  //设定值
			//cell.setCellStyle(style);                   //内容居中
			cell = row.createCell(1);                   //第二个单元格
			cell.setCellValue("交易日期");
			//cell.setCellStyle(style);
			cell = row.createCell(2);                   //第三个单元格
			cell.setCellValue("收入/支出");
			//cell.setCellStyle(style);
			cell = row.createCell(3);                   //第四个单元格
			cell.setCellValue("对方账户");
			cell = row.createCell(4);                   //第五个单元格
			cell.setCellValue("对方户名");
			cell = row.createCell(5);                   //第五个单元格
			cell.setCellValue("摘要");
			cell = row.createCell(6);
			cell.setCellValue("交易金额");
			cell = row.createCell(7);
			cell.setCellValue("账户余额");
			cell = row.createCell(8);
			//cell.setCellValue("索引名");
			//cell.setCellStyle(style);
		}
		//第五步插入数据

//		List<MbTranHist> mbTranHistList = new ArrayList<MbTranHist>();
//		//for(TranHistArray tranHistArray:tranHistArrayList)
//		for (int i=0;i<tranHistArrayList.size();i++){
//			TranHistArray tranHistArray = tranHistArrayList.get(i);
//			//创建行
//			row = sheet.createRow(i+5);
//			//创建单元格并且添加数据
//			row.createCell(0).setCellValue(i+1);
//			row.createCell(1).setCellValue(tranHistArray.getTranDate());
//			String cOrd = null;
//			if (BusiUtil.isEquals("D",tranHistArray.getCrDrMaintInd())){
//				cOrd="支出";
//			}else if ((BusiUtil.isEquals("C",tranHistArray.getCrDrMaintInd()))){
//				cOrd="收入";
//			}else {
//				cOrd="未知";
//			}
//			row.createCell(2).setCellValue(cOrd);
//			row.createCell(3).setCellValue(tranHistArray.getOthBaseAcctNo());
//			row.createCell(4).setCellValue(tranHistArray.getOthAcctDesc());
//
//			row.createCell(5).setCellValue(tranHistArray.getTranDesc());
//
//			row.createCell(6).setCellValue(tranHistArray.getTranAmt().toString());
//
//			row.createCell(7).setCellValue(tranHistArray.getActualBalAmt().toString());
//
//			//row0.createCell(3).setCellValue(Table.getPk());
//
//		}

		//第六步将生成excel文件保存到指定路径下
		try {
			FileOutputStream fout = new FileOutputStream("C:\\Users\\liyangbd\\Desktop\\test.xls");
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Excel文件生成成功...");
	}


	//todo 拼接excl
//    public static List<Table> getErrorCondition(){
//        List<Table> exclList = new ArrayList<Table>();
//
////        Table r1 = new Table("张三", "4306821989021611", "L", "长度错误","");
////        Table r2 = new Table("李四", "430682198902191112","X", "校验错误","");
////        Table r3 = new Table("王五", "", "N", "身份证信息为空","");
//
////        list.add(r1);
////        list.add(r2);
////        list.add(r3);
//
//        return exclList;
//    }

    //todo:处理数据库类型长度
    public static String tranLength(String type,String length){
        if ("VARCHAR2".equals(type)){
            length=Integer.toString((Integer.valueOf(length)/4));
        }
//        if ("NUMBER".equals(type)){
//            length=Integer.toString((Integer.valueOf(length)/2));
//        }
        if ("TIMESTAMP(6)".equals(type)){
            length = "6";
        }
        if ("CHAR".equals(type)){
            if("1".equals(length)){
                length="1";
            }else{
                length=Integer.toString((Integer.valueOf(length)/4));
            }
        }
//        if ("CLOB".equals(type)){
//            length="";
//        }
        //System.out.println(length);
        return length;
    }

    //todo:调用处理
    public static void deal(){
        //substring.file("02");
        List<Table> tabList = getTableKeyDesc();
        for (Table tab:tabList){
            List<Table> excllist = getColKeyDesc(tab.getTable_name());
            List<Table> pkList = getTabPK(tab.getTable_name());
            List<Table> indexList = getTabIndex(tab.getTable_name());
            toExcle(tab.getTable_name(),tab.getTable_desc(),excllist,pkList,indexList);
        }
        substring.file("01");
    }

    //TODO 给字段名对应表名
    public static void keyToDesc(){
        String inputValue = JOptionPane.showInputDialog("请输入字段名");
        System.out.println(inputValue);
        List<Table> keyDesc = getKeyToTable(inputValue);
        System.out.println("结果列表"+keyDesc.toString());
        JOptionPane.showInputDialog(keyDesc);

    }
}
