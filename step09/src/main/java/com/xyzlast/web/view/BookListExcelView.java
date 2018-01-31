package com.xyzlast.web.view;

import com.xyzlast.web.vo.Book;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BookListExcelView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "책리스트" + "-" + fileFormat.format(new Date()) + ".xlsx";
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        Sheet sheet = workbook.createSheet("책목록");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("이름");
        header.createCell(1).setCellValue("저자");
        header.createCell(2).setCellValue("상태");

        @SuppressWarnings("unchecked")
        List<Book> books = (List<Book>) model.get("books");

        int row = 1;
        for (Book book : books) {
            Row rowCell = sheet.createRow(row);
            rowCell.createCell(0).setCellValue(book.getName());
            rowCell.createCell(1).setCellValue(book.getAuthor());
            rowCell.createCell(2).setCellValue(book.getStatus());
            row++;
        }

    }
}
