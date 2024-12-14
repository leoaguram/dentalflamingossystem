package com.dental_flamingos.controller.pdf;

import com.dental_flamingos.model.Dentista;
import com.dental_flamingos.model.PagoMovimientos;
import com.dental_flamingos.model.PagoResumen;
import com.dental_flamingos.service.dentista.DentistaService;
import com.dental_flamingos.service.pago.PagoMovimientosService;
import com.dental_flamingos.service.pago.PagoResumenService;
import com.dental_flamingos.service.pdf.PdfIngresosService;
import com.dental_flamingos.service.pdf.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PagosPDFController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private PdfIngresosService pdfIngresosService;

    @Autowired
    private PagoMovimientosService pagoMovimientosService;

    @Autowired
    private PagoResumenService pagoResumenService;

    @Autowired
    private DentistaService dentistaService;

    @GetMapping("/recibo")
    public ResponseEntity<byte[]> generarRecibo(@RequestParam Integer idCita) {
        // Obtener datos para el PDF
        PagoResumen resumen = pagoResumenService.obtenerResumenPorCita(idCita);
        List<PagoMovimientos> movimientos = pagoMovimientosService.obtenerMovimientosPorCita(idCita);

        // Generar el PDF
        byte[] pdfBytes = pdfService.generarReciboPDF(
                resumen.getCita().getPaciente().getNombre(),
                resumen.getCita().getTratamiento().getDescripcion(),
                resumen.getCita().getFechaHoraCita().toString(),
                resumen.getMontoTotal(),
                resumen.getMontoPagado(),
                resumen.getMontoRestante(),
                resumen.getStatus().getDescripcion(),
                movimientos
        );

        // Devolver el PDF como respuesta
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recibo-pago.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/reporte-ingresos")
    public ResponseEntity<byte[]> generarReporteIngresos(@RequestParam("fechaInicio") String fechaInicio,
                                                         @RequestParam("fechaFin") String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        // Obtener datos de ingresos
        List<PagoMovimientos> ingresos = pagoMovimientosService.obtenerIngresosPorFechas(inicio, fin, dentistaService.getDentistaActivo().getIdDentista());

        // Generar el PDF
        byte[] pdfBytes = pdfIngresosService.generarReporteIngresosPDF(inicio, fin, ingresos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-ingresos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
        
    }
}

