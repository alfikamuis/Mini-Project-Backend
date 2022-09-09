package com.alfika.backendecommerce.data;

import com.alfika.backendecommerce.model.OrderItems;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExporterOrderItems {

    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private List<OrderItems> orderItems;

    public ExporterOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
        workbook = new XSSFWorkbook();
    }

    private void fileHeader(){
        sheet = workbook.createSheet("Order");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(15);
        style.setFont(font);

        createCell(row,0,"order_id",style);
        createCell(row,1,"user_email",style);
        createCell(row,2,"total_cost",style);
        createCell(row,3,"status_order",style);
        createCell(row,4,"date_order",style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);

        Cell cell = row.createCell(columnCount);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
        DecimalFormat dFormat = new DecimalFormat("####,###,###.00");

        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            String currencyValue = "Rp."+dFormat.format(value);
            cell.setCellValue(currencyValue);
        }else if ( value instanceof Date){
            String valueDate = formatter.format(value);
            cell.setCellValue(valueDate);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeData() {
        int inRow = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (OrderItems items : orderItems) {
            Row row = sheet.createRow(inRow++);
            int columnCount = 0;

            createCell(row, columnCount++, items.getId(), style);
            createCell(row, columnCount++, items.getEmail(), style);
            createCell(row, columnCount++, items.getTotalCost(), style);
            createCell(row, columnCount++, items.getOrderStatus(), style);
            createCell(row, columnCount++, items.getOrderDate(), style);

        }
    }

    public void exports(HttpServletResponse response) throws IOException{
        fileHeader();
        writeData();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

}
