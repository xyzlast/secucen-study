package com.xyzlast.web.view;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class BookListITextPdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        setFileNameToResponse(request, response, "bookList.pdf");
        BaseFont objBaseFont = BaseFont.createFont("HYGoThic-Medium", "UniKS-UCS2-H", false);
        Font objFont = new Font(objBaseFont, 12);
        Chapter chapter = new Chapter(new Paragraph("this is english"), 1);
        chapter.add(new Paragraph("이건 메세지입니다.", objFont));
        document.add(chapter);
    }

    private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.contains("MSIE 5.5")) {
            response.setContentType("doesn/matter");
            response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }
    }
}
