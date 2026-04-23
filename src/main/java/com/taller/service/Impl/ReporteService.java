package com.taller.service.Impl;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReporteService {

    private final DataSource dataSource;
    public ReporteService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte [] generarReporte(Integer Proforma_id) throws JRException {

        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("reportes/" + "Blank_A4" + ".jasper");

        if(input==null){
            throw new RuntimeException("NO SE ENCONTRÓ EL REPORTE: reportes/" + "Blank_A4" + ".jasper");

        }
        Map<String, Object> param = new HashMap<>();
        param.put("Parameter1",Proforma_id);
        param.put("SUBREPORT_DIR", "reportes/");

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(input,param,dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return    JasperExportManager.exportReportToPdf(jasperPrint);

    }
}
