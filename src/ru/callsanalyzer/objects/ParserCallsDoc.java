package ru.callsanalyzer.objects;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by msv on 10.11.2015.
 */
public class ParserCallsDoc {
    private final int DATE_CELL = 1;
    private final int ACCOUNT_CALL_CELL = 2;
    private final int FROM_CALL_CELL = 3;
    private final int TO_CALL_CELL = 4;
    private final int SUM_CALL_CELL = 5;
    private final int DURATION_CALL_CELL = 6;
    private final int FIRST_PARAGRAPH = 0;

    private HWPFDocument doc;
    private FileInputStream finStream;

    private Table table;

    public Table getTable() {
        return table;
    }

    ParserCallsDoc(String fileName) {
        try {
            File docFile = new File(fileName);
            finStream = new FileInputStream(docFile.getAbsolutePath());
            doc = new HWPFDocument(finStream);
        } catch (IOException e) {
            System.out.println("файл " + fileName + " не найден");
        }

        Range range = doc.getRange();
        Paragraph tablePar = range.getParagraph(FIRST_PARAGRAPH);
        if (tablePar.isInTable()) {
            table = range.getTable(tablePar);
        } else {
            throw new IllegalArgumentException("Таблица звонков в файле не найдена!");
        }
    }

    void close() {
        if (finStream != null) {
            try {
                finStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Call getCallByRowId(int rowId) {
        Call call = null;
        if (getTable() != null) {
            if (rowId < table.numRows()) {

                TableRow row = table.getRow(rowId);
                try {
                    String str = getCellText(row, DATE_CELL);
                    if (!str.equals("")) {
                        call = new Call();
                        call.setDate(getDate(str));
                        call.setAccount(getCellText(row, ACCOUNT_CALL_CELL));
                        call.setNumberFrom(getCellText(row, FROM_CALL_CELL));
                        call.setNumberTo(getCellText(row, TO_CALL_CELL));
                        try {
                            call.setSum(Double.parseDouble(getCellText(row, SUM_CALL_CELL)));
                            call.setDuration(Integer.parseInt(getCellText(row, DURATION_CALL_CELL)));
                        } catch (NumberFormatException e) {
                            System.out.println("Не распознаны поля 'SUM_CALL' или 'DURATION_CALL' строки №" + (rowId + 1));
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Неверный формат строки №" + (rowId + 1) + " , не хватает полей");
                }

            }
        }
        return call;
    }

    private String getCellText(TableRow row, int cellIndex) {
        String str = row.getCell(cellIndex).getParagraph(FIRST_PARAGRAPH).text().trim();
        return str;
    }

    private Date getDate(String text) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        try {
            date = (Date) sdf.parseObject(text);
        } catch (ParseException e) {
            throw new IllegalArgumentException(text + " Неверный формат даты в таблице звонков!!!");
        }
        return date;
    }

    public static void main(String[] args) throws IOException {
        ParserCallsDoc parserCallsDoc = new ParserCallsDoc("d://temp/calls.doc");
        Table table = parserCallsDoc.getTable();
        for (int i = 0; i < table.numRows(); i++) {
            Call call = parserCallsDoc.getCallByRowId(i);
            if (call != null) {
                System.out.println(call.getDate() + "  " + call.getAccount());
            }
        }

        //parserCallsDoc.close();
//        File docFile = new File("d://temp/calls.doc");
//        // file input stream with docFile
//        FileInputStream finStream=new FileInputStream(docFile.getAbsolutePath());
//
//        // throws IOException and need to import org.apache.poi.hwpf.HWPFDocument;
//        HWPFDocument doc=new HWPFDocument(finStream);
//
//        // import  org.apache.poi.hwpf.extractor.WordExtractor
//        //WordExtractor wordExtract=new WordExtractor(doc);
//        ///ListTables listTables = doc.getListTables();
//        Range range = doc.getRange();
//        System.out.println(range.numParagraphs());
////        for (int i=0; i<range.numParagraphs(); i++) {
////            Paragraph par = range.getParagraph(i);
////            System.out.println("paragraph "+(i+1));
////            System.out.println("is in table: "+par.isInTable());
////            System.out.println("is table row end: "+par.isTableRowEnd());
////            System.out.println(par.text());
////        }
//
//        Paragraph tablePar = range.getParagraph(0);
//        if (tablePar.isInTable()) {
//            Table table = range.getTable(tablePar);
//            //
//            for (int rowIdx=0; rowIdx<154; rowIdx++) {
//                TableRow row = table.getRow(rowIdx);
//                System.out.println("row "+(rowIdx+1)+", is table header: "+row.isTableHeader());
//                for (int colIdx=0; colIdx<row.numCells(); colIdx++) {
//                    TableCell cell = row.getCell(colIdx);
//                    System.out.println("column "+(colIdx+1)+", text="+cell.getParagraph(0).text());
//                }
//            }
//        }
////        // dataArray stores the each line from the document
////        String [] dataArray =wordExtract.getParagraphText();
////
////
////        for(int i=0;i<dataArray.length;i++) {
////            // printing lines from the array
////            System.out.println("\n–"+dataArray[i]);
////
////        }
////        //closing fileinputstream
//        finStream.close();
    }

}
