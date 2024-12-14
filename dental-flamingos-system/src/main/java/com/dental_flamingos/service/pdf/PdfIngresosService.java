package com.dental_flamingos.service.pdf;
import com.dental_flamingos.model.PagoMovimientos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class PdfIngresosService {
    public byte[] generarReporteIngresosPDF(LocalDate fechaInicio, LocalDate fechaFin, List<PagoMovimientos> ingresos) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Reporte de Ingresos", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Rango de fechas
            Font rangoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph rangoFechas = new Paragraph("Rango de Fechas: " + fechaInicio + " - " + fechaFin, rangoFont);
            rangoFechas.setAlignment(Element.ALIGN_CENTER);
            document.add(rangoFechas);

            // Espaciado
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            // Encabezados
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Fecha de Pago", headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Monto", headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Método", headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Notas", headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Filas
            Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            double totalGanancias = 0; // Variable para sumar las ganancias
            for (PagoMovimientos ingreso : ingresos) {
                table.addCell(new PdfPCell(new Phrase(ingreso.getFechaPago().toString(), rowFont)));
                table.addCell(new PdfPCell(new Phrase("$" + ingreso.getMonto(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(ingreso.getMetodoPago(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(ingreso.getComentarios(), rowFont)));

                // Sumar al total
                totalGanancias += ingreso.getMonto();
            }

            document.add(table);

            // Espaciado antes del total
            document.add(new Paragraph(" "));

            // Total de ganancias
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Paragraph totalGananciasParagraph = new Paragraph("Total Generado: $" + totalGanancias, totalFont);
            totalGananciasParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalGananciasParagraph);

            // Cerrar documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
