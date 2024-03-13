package com.stonedt.intelligence.service.impl;

import com.stonedt.intelligence.service.ExcelService;
import com.stonedt.intelligence.vo.ArticleData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {


    /**
     * 组装为excel
     *
     * @param articleDataList
     */
    @Override
    public byte[] assembleExcel(List<ArticleData> articleDataList) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            assembleExcel(articleDataList, outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * 组装为excel
     * 格式如下
     * |序号|标题|摘要|情感|涉及词|来源|作者|发布时间|发布地|原文地址|相似文章数|
     * @param articleDataList
     */
    @Override
    public void assembleExcel(List<ArticleData> articleDataList, OutputStream outputStream) throws IOException {
        // 创建一个excel
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("舆情数据");
            // 创建表头
            sheet.createRow(0).createCell(0).setCellValue("序号");
            sheet.getRow(0).createCell(1).setCellValue("标题");
            sheet.getRow(0).createCell(2).setCellValue("摘要");
            sheet.getRow(0).createCell(3).setCellValue("情感");
            sheet.getRow(0).createCell(4).setCellValue("涉及词");
            sheet.getRow(0).createCell(5).setCellValue("来源");
            sheet.getRow(0).createCell(6).setCellValue("作者");
            sheet.getRow(0).createCell(7).setCellValue("发布时间");
            sheet.getRow(0).createCell(8).setCellValue("原文地址");
//            sheet.getRow(0).createCell(9).setCellValue("相似文章数");
            // 创建数据行
            for (int i = 0; i < articleDataList.size(); i++) {
                ArticleData articleData = articleDataList.get(i);
                Integer emotionalIndex = articleData.getEmotionalIndex();
                String emotional = emotionalIndex == 1 ? "正面" : emotionalIndex == 2 ? "中性" : "负面";

                String content = articleData.getContent();
                if (content != null && content.length() > 500) {
                    content = content.substring(0, 500);
                }
                String relatedWord;

                List<String> relatedWordList = articleData.getRelatedWord();
                if (relatedWordList != null && !relatedWordList.isEmpty()) {
                    relatedWord = String.join(",", relatedWordList);
                } else {
                    relatedWord = "";
                }


                sheet.createRow(i + 1).createCell(0).setCellValue(i + 1);
                sheet.getRow(i + 1).createCell(1).setCellValue(articleData.getTitle());
                sheet.getRow(i + 1).createCell(2).setCellValue(content);
                sheet.getRow(i + 1).createCell(3).setCellValue(emotional);
                sheet.getRow(i + 1).createCell(4).setCellValue(relatedWord);
                sheet.getRow(i + 1).createCell(5).setCellValue(articleData.getSource_name());
                sheet.getRow(i + 1).createCell(6).setCellValue(articleData.getAuthor());
                sheet.getRow(i + 1).createCell(7).setCellValue(articleData.getPublish_time());
                sheet.getRow(i + 1).createCell(8).setCellValue(articleData.getSource_url());
//                sheet.getRow(i + 1).createCell(9).setCellValue(articleData.getSimilarvolume());
            }

            // 将excel写入到输出流中
            workbook.write(outputStream);

        }
    }
}
