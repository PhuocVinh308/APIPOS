package com.example.demo.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
public class PdfUtils {
    private static final Logger log = LoggerFactory.getLogger(PdfUtils.class);

    public static byte[] rotatePdf(byte[] originalPdfFile, int degrees) {
        try {
            PdfReader reader = new PdfReader(originalPdfFile);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfDictionary dictionary = reader.getPageN(i);
                dictionary.put(PdfName.ROTATE, new PdfNumber(degrees));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, baos);
            stamper.close();
            reader.close();
            return baos.toByteArray();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return originalPdfFile;
    }

    public static boolean isLandscapePage(PdfReader pdfReader) {
        try {
            Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
            return !(rectangle.getHeight() >= rectangle.getWidth());
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * Merge n pdf files with the same page size
     * */
    public static byte[] mergePdfFiles(List<byte[]> inputPdfList) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //Create document and pdfReader objects.
            List<PdfReader> readers = new ArrayList<>();
            int totalPages = 0;

            //Create pdf Iterator object using inputPdfList.

            // Create reader list for the input pdf files.
            for (byte[] pdf : inputPdfList) {
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages = totalPages + pdfReader.getNumberOfPages();
            }

            Rectangle rectangle =  new Rectangle(readers.get(0).getPageSize(1));
            boolean landscape = isLandscapePage(readers.get(0));
            Document document = new Document(rectangle);
            // Create writer for the outputStream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            //Open document.
            document.open();

            //Contain the pdf data.
            PdfContentByte pageContentByte = writer.getDirectContent();

            int currentPdfReaderPage = 1;

            // Iterate and process the reader list.
            for (PdfReader pdfReader : readers) {
                //Create page and add content.
                while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                    document.newPage();
                    PdfImportedPage pdfImportedPage = writer.getImportedPage(
                            pdfReader, currentPdfReaderPage);
                    pageContentByte.addTemplate(pdfImportedPage, 0, 0);
                    currentPdfReaderPage++;
                }
                currentPdfReaderPage = 1;
            }

            //Close document and outputStream.
            outputStream.flush();
            document.close();
            outputStream.close();

            log.debug("Pdf files merged successfully.");
            if (landscape) {
                return rotatePdf(outputStream.toByteArray(), 90);
            }
            return outputStream.toByteArray();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new byte[0];
    }
}
