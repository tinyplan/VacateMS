package com.finalwork.utils.excel;

import com.finalwork.po.Tutor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ExcelUtils {
    /* 读取Excel获取辅导员list */
    public static List<Tutor> readExcel(String path) throws IOException {
        FileInputStream in = new FileInputStream(new File(path));
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        // 读取工作表，从0开始
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Tutor> tutorList = new LinkedList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            // 读取行
            XSSFRow row = sheet.getRow(i);
            // 读取单元格
            XSSFCell cell = row.getCell(0);
            cell.setCellType(CellType.STRING);
            String tutor_id = cell.getStringCellValue();
            cell = row.getCell(1);
            String tutor_name = cell.getStringCellValue();
            cell = row.getCell(2);
            cell.setCellType(CellType.STRING);
            String pwd = cell.getStringCellValue();

            Tutor tutor = new Tutor();
            tutor.setId(tutor_id);
            tutor.setName(tutor_name);
            tutor.setPassword(pwd);
            tutor.setEdu_id("edu01");

            tutorList.add(tutor);
        }
        workbook.close();
        in.close();
        return tutorList;
    }

    public static void writeExcel() throws IOException {
        FileOutputStream out = new FileOutputStream(new File("D:\\poiTest.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("HelloWorld");
        workbook.write(out);
        workbook.close();
    }

    public static String getFullDir(MultipartFile file, String userId, HttpServletRequest request, String dir){
        String originalFileName = file.getOriginalFilename();
        /* 在项目目录下创建dir目录 */
        String dirPath = request.getServletContext().getRealPath(dir);
        File filePath = new File(dirPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String newFileName = UUID.randomUUID() + "_" + userId + "_" + originalFileName;
        return dirPath + File.separator + newFileName;
    }
}
