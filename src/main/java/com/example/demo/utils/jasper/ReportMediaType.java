package com.example.demo.utils.jasper;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import java.util.Map;
public class ReportMediaType {

    private static final Map<ReportType, MediaType> mediaTypeMap = ImmutableMap.<ReportType, MediaType>builder()
            .put(ReportType.PDF, MediaType.APPLICATION_PDF)
            .put(ReportType.XLS, MediaType.asMediaType(MimeType.valueOf("application/vnd.ms-excel")))
            .put(ReportType.XLSX, MediaType.asMediaType(MimeType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")))
            .put(ReportType.RTF, MediaType.asMediaType(MimeType.valueOf("application/rtf")))
            .put(ReportType.HTML, MediaType.TEXT_HTML)
            .build();

    private static final Map<ReportType, String> extensionMap = ImmutableMap.<ReportType, String>builder()
            .put(ReportType.PDF, ".pdf")
            .put(ReportType.XLS, ".xls")
            .put(ReportType.XLSX, ".xlsx")
            .put(ReportType.RTF,  ".rtf")
            .put(ReportType.HTML, ".html")
            .build();

    public static MediaType getMediaType(ReportType type) {
        return mediaTypeMap.get(type);
    }

    public static String getExtension(ReportType type) {
        return extensionMap.get(type);
    }

}
