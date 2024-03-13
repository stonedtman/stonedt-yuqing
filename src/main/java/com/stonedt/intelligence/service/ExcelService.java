package com.stonedt.intelligence.service;

import com.stonedt.intelligence.vo.ArticleData;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelService {


    /**
     * 组装为excel
     */
    byte[] assembleExcel(List<ArticleData> articleDataList) throws IOException;

    void assembleExcel(List<ArticleData> articleDataList, OutputStream outputStream) throws IOException;
}
