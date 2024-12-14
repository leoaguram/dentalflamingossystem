package com.dental_flamingos.service.pdf;

import com.dental_flamingos.model.PagoMovimientos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generarReciboPDF(String paciente, String tratamiento, String fechaCita,
                                   Double montoTotal, Double montoPagado,
                                   Double montoRestante, String estatus,
                                   List<PagoMovimientos> movimientos) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Crear el documento
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Recibo de Pagos", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Espaciado
            document.add(new Paragraph(" "));

            // Información del Paciente y Tratamiento
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            document.add(new Paragraph("Paciente: " + paciente, infoFont));
            document.add(new Paragraph("Tratamiento: " + tratamiento, infoFont));
            document.add(new Paragraph("Fecha de la Cita: " + fechaCita, infoFont));
            document.add(new Paragraph("Monto Total: $" + montoTotal, infoFont));
            document.add(new Paragraph("Monto Pagado: $" + montoPagado, infoFont));
            document.add(new Paragraph("Monto Restante: $" + montoRestante, infoFont));
            document.add(new Paragraph("Estatus: " + estatus, infoFont));

            // Espaciado
            document.add(new Paragraph(" "));

            // Tabla de Movimientos
            PdfPTable table = new PdfPTable(4); // 4 columnas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados de la tabla
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

            // Filas de la tabla
            Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            for (PagoMovimientos movimiento : movimientos) {
                table.addCell(new PdfPCell(new Phrase(movimiento.getFechaPago().toString(), rowFont)));
                table.addCell(new PdfPCell(new Phrase("$" + movimiento.getMonto(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(movimiento.getMetodoPago(), rowFont)));
                table.addCell(new PdfPCell(new Phrase(movimiento.getComentarios(), rowFont)));
            }

            document.add(table);

            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}

