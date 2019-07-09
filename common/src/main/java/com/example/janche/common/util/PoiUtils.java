package com.example.janche.common.util;

import com.example.janche.common.exception.CustomException;
import com.example.janche.common.restResult.ResultCode;
import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 操作execl
 * -DONE 适配.xls
 * -TODO 适配.xlsx
 */
@Data
public class PoiUtils {

    /**
     * 文件
     */
    private HSSFWorkbook workbook;

    /**
     * 单元表名
     */
    private String sheetName;

    /**
     * 第一行的表头
     */
    private String[] headers;

    /**
     * 标题
     */
    private String title;

    /**
     * 单元表
     */
    private HSSFSheet sheet;

    /**
     * execl基础信息
     */
    public PoiUtils(String sheetName, String[] headers) {
        this.sheetName = sheetName;
        this.headers = headers;
        this.workbook = new HSSFWorkbook();
        //建表
        this.sheet = workbook.createSheet(sheetName);
    }

    /**
     * 根据模板导出excel
     */
    public PoiUtils(String sheetName, String fileName) {
        String path = PoiUtils.class.getResource("/xlsx_template").getPath() + "/"+fileName;
        InputStream is = null;
        try{
            is = new FileInputStream(path);
            this.workbook = new HSSFWorkbook(is);
            // 替换工作表的名称
            this.workbook.setSheetName(0, sheetName);
            this.sheet = this.workbook.getSheetAt(0);
        }catch (IOException e){
            throw new CustomException(10009, e.getMessage());
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title, String[] headers){
        HSSFCellStyle titleStyle = sheet.getRow(0).getCell(0).getCellStyle();
        titleStyle.setHidden(false);
        // 先移除模板的合并样式
        sheet.removeMergedRegion(0);
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setZeroHeight(false);
        titleRow.setHeight((short) 600);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(title);
        // 合并单元格
        // 起始行, 终止行, 起始列, 终止列
        CellRangeAddress cra =new CellRangeAddress(0, 0, 0, headers.length-1);
        sheet.addMergedRegion(cra);
    }
    public String getTitle(){
        return this.title;
    }
    /**
     * 设置表头
     * @param headers
     */
    public void setHeaders(String[] headers, String title){
        this.headers = headers;
        this.title = title;
        // 替换表头的文字
        HSSFRow headRow = sheet.getRow(1);
        HSSFRow newHeadRow = sheet.createRow(1);
        int len = headers.length;
        CellStyle[] arr = new CellStyle[len];
        for (int i=0; i<arr.length; i++) {
            HSSFCell headCell = headRow.getCell(i);
            if (null == headCell){
                headCell = headRow.getCell(0);
            }
            HSSFCellStyle cellStyle = headCell.getCellStyle();
            HSSFCell cell = newHeadRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(cellStyle);
        }
        this.setTitle(title, headers);
    }

    /**
     * 根据数据生成sheet，不加样式
     */
    public <T> void createFileByData(List<T> data) {
        // 添加表头
        HSSFRow headRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = headRow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 装配内容
        int rowNum = 1;
        for (T item : data) {
            HSSFRow contentRow = sheet.createRow(rowNum);
            Class clazz = item.getClass();
            // TODO 这里没有对 对象中的属性是 一个集合 或 一个对象的情况进行处理
            Field[] fields = clazz.getDeclaredFields();

            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                try {
                    Object o = field.get(item);
                    if (null == o){
                        o = "";
                    }
                    if(o instanceof Date){
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        contentRow.createCell(j).setCellValue(sf.format(o));
                        continue;
                    }
                    HSSFCell contentCell = contentRow.createCell(j);
                    contentCell.setCellValue(o.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rowNum++;
        }
    }

    /**
     * 用模板样式填充数据
     */
    public <T> void fillDataAndStyle(List<T> data, int rowNum) {
        // 获取模板第一行的样式,行从0开始
        HSSFRow nRow = sheet.getRow(rowNum);
        if (null == data || data.size() == 0){
            throw new CustomException(ResultCode.DATA_IS_NULL);
        }
        int len = data.get(0).getClass().getDeclaredFields().length;
        CellStyle[] arr = new CellStyle[len];
        for (int i=0; i<arr.length; i++) {
            HSSFCell cell = nRow.getCell(i);
            if (null == cell){
                cell = nRow.getCell(0);
            }
            arr[i] = cell.getCellStyle();
        }
        // 装配内容
        for (T item : data) {
            HSSFRow contentRow = sheet.createRow(rowNum);
            Class clazz = item.getClass();
            // TODO 这里没有对 对象中的属性是 一个集合 或 一个对象的情况进行处理
            Field[] fields = clazz.getDeclaredFields();

            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                try {
                    Object o = field.get(item);
                    if (null == o){
                        o = "";
                    }
                    if(o instanceof Date){
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        HSSFCell contentCell = contentRow.createCell(j);
                        contentCell.setCellValue(sf.format(o));
                        contentCell.setCellStyle(arr[j]);
                        sheet.setColumnWidth(j,32*256);
                        continue;
                    }
                    HSSFCell contentCell = contentRow.createCell(j);
                    contentCell.setCellValue(o.toString());
                    contentCell.setCellStyle(arr[j]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rowNum++;
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 获取excel单元格内容，如果单元格为空则返回null(空和空字符有区别，通常用于判断是不是最后一行)
     * @param row 单元格行的变量，可以是HSSFRow和XSSFRow
     * @param i 单元格的列变量
     * @return cell value
     */
    public static Object getColumnValue(Row row, int i) {
        Cell cell = row.getCell(i);
        if(cell == null)
            return null;
        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            case Cell.CELL_TYPE_NUMERIC:
                String value = df.format(cell.getNumericCellValue());
                if(value.endsWith(".00"))
                    value = value.split("[.]")[0];
                return value;
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                String value1 = df.format(cell.getNumericCellValue());
                if(value1.endsWith(".00"))
                    value1 = value1.split("[.]")[0];
                return value1;
            default:
                return null;
        }
    }

    /**
     * 获取模板工作簿
     * @param fileName
     * @return
     */
    public static HSSFWorkbook createHSSFWB(String fileName) {
        String path = PoiUtils.class.getResource("/xlsx_template").getPath() + "/"+fileName;
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
