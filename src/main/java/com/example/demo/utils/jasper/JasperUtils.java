package com.example.demo.utils.jasper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.utils.PdfUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
public class JasperUtils {

    private static final JasperUtilsException FORMAT_NOT_SUPPORT_EXCEPTION = new JasperUtilsException("Report format is not supported");
    private static final List<ReportType> OUTPUT_STREAM_EXPORTER_TYPES = Arrays.asList(ReportType.PDF, ReportType.XLS, ReportType.XLSX);
    private static final List<ReportType> WRITER_EXPORTER_TYPES = Collections.singletonList(ReportType.RTF);
    private static final List<ReportType> HTML_EXPORTER_TYPES = Collections.singletonList(ReportType.HTML);
    private static final Map<ReportType, JRAbstractExporter> JREXPORTER_MAP = new HashMap<>();
    static {
        JREXPORTER_MAP.put(ReportType.PDF, new JRPdfExporter());
        JREXPORTER_MAP.put(ReportType.XLS, new JRXlsExporter());
        JREXPORTER_MAP.put(ReportType.XLSX, new JRXlsxExporter());
        JREXPORTER_MAP.put(ReportType.RTF, new JRRtfExporter());
        JREXPORTER_MAP.put(ReportType.HTML, new HtmlExporter());
    }

    private static ExporterOutput getExporterOutput(ByteArrayOutputStream baos, ReportType type) {
        ExporterOutput exporterOutput = null;
        if (OUTPUT_STREAM_EXPORTER_TYPES.contains(type)) {
            exporterOutput = new SimpleOutputStreamExporterOutput(baos);
        }
        if (WRITER_EXPORTER_TYPES.contains(type)) {
            exporterOutput = new SimpleWriterExporterOutput(baos);
        }
        if (HTML_EXPORTER_TYPES.contains(type)) {
            exporterOutput = new SimpleHtmlExporterOutput(baos);
        }
        if (exporterOutput == null) {
            throw FORMAT_NOT_SUPPORT_EXCEPTION;
        }
        return exporterOutput;
    }

    public static PrintResult printReport(String resourcePath, Map<String, Object> parameters, List<?> dataSource, ReportType type) {
        try {
            long init = System.currentTimeMillis();

            JRDataSource jrDataSource = getJRDataSource(dataSource);
            ClassPathResource cr = new ClassPathResource(resourcePath);
            InputStream is = cr.getInputStream();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
            String filename = generateFileName(FilenameUtils.removeExtension(cr.getFilename()), type);
            parameters.forEach((key, value) -> log.info("Param [" + key + "]: " + value));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRAbstractExporter exporter = JREXPORTER_MAP.get(type);
            ExporterOutput exporterOutput = getExporterOutput(baos, type);
            ExporterInput exporterInput = new SimpleExporterInput(jp);

            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(exporterOutput);
            exporter.exportReport();

            log.info("Print report take: {} ms", (System.currentTimeMillis() - init));
            byte[] data = baos.toByteArray();
            baos.close();
            return new PrintResult(filename, data, type);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JasperUtilsException(ex);
        }
    }

    private static String generateFileName(String jasperName, ReportType type) {
        String extension = ReportMediaType.getExtension(type);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String posfix = LocalDateTime.now().format(formatter);
        String name = (StringUtils.isNotBlank(jasperName) ? jasperName : "") + "_" + posfix + extension;
        log.info("exportType=" + extension);
        log.info("fileName=" + name);
        return name;
    }

    private static JRDataSource getJRDataSource(List dataSource) {
        if (dataSource == null || dataSource.isEmpty()) {
            return new JRBeanCollectionDataSource(Collections.emptyList());
        }
        else if (dataSource.get(0) instanceof Map) {
            return new JRMapCollectionDataSource(dataSource);
        }
        else {
            return new JRBeanCollectionDataSource(dataSource, false);
        }
    }

    public static ResponseEntity<ByteArrayResource> getReportResponseEntity(PrintResult printResult) {
        try {
            byte[] data = printResult.getData();
            return ResponseEntity
                    .ok()
                    .headers(headers -> {
                        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                                .filename(printResult.getFileName())
                                .build();
                        headers.setContentLength(data.length);
                        headers.setContentDisposition(contentDisposition);
                        headers.setContentType(ReportMediaType.getMediaType(printResult.getType()));
                    })
                    .body(new ByteArrayResource(data));
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    public static ResponseEntity<ByteArrayResource> getReportResponseEntity(String path, Map<String, Object> parameters, List<?> dataSource, ReportType type) {
        PrintResult printResult = printReport(path, parameters, dataSource, type);
        return JasperUtils.getReportResponseEntity(printResult);
    }

    public static ResponseEntity<ByteArrayResource> getListReportResponseEntity(List<PrintResult> listResult) {
        try {
            List<byte[]> listByte = listResult.stream().map(e -> e.getData()).collect(Collectors.toList());

            byte[] data = PdfUtils.mergePdfFiles(listByte);

            return ResponseEntity
                    .ok()
                    .headers(headers -> {
                        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                                .filename(listResult.get(0).getFileName())
                                .build();
                        headers.setContentLength(data.length);
                        headers.setContentDisposition(contentDisposition);
                        headers.setContentType(ReportMediaType.getMediaType(listResult.get(0).getType()));
                    })
                    .body(new ByteArrayResource(data));
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    public static JasperPrint getJasperPrint(String templatePath, Map<String, Object> parameters, JRDataSource dataSource) {
        try {
            // Sử dụng ClassPathResource để lấy InputStream của tệp .jasper
            InputStream inputStream = new ClassPathResource(templatePath).getInputStream();

            // Tạo bản in (JasperPrint) từ mẫu, tham số và nguồn dữ liệu
            return JasperFillManager.fillReport(inputStream, parameters, dataSource);
        } catch (JRException | IOException e) {
            // Xử lý exception nếu có
            e.printStackTrace();
            return null;
        }
    }
}
