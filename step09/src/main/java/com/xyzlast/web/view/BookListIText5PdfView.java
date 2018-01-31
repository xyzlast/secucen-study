package com.xyzlast.web.view;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class BookListIText5PdfView extends AbstractIText5PdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = createFileName();
        setFileNameToResponse(request, response, fileName);
        Chapter chapter = new Chapter(new Paragraph("this is english"), 1);
        chapter.add(new Paragraph("이건 메세지입니다."));
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

    private String createFileName() {
        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return new StringBuilder("pdf").append("-").append(fileFormat.format(new Date())).append(".pdf").toString();
    }
}
