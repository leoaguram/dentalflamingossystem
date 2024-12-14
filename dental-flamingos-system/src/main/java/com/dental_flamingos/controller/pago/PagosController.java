package com.dental_flamingos.controller.pago;


import com.dental_flamingos.model.*;
import com.dental_flamingos.service.cita.CitaService;
import com.dental_flamingos.service.dentista.DentistaService;
import com.dental_flamingos.service.paciente.PacienteService;
import com.dental_flamingos.service.pago.PagoMovimientosService;
import com.dental_flamingos.service.pago.PagoResumenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private PagoMovimientosService movimientosService;

    @Autowired
    private PagoResumenService pagosResumenService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private DentistaService dentistaService;
    private static final Logger loggerResumen = LoggerFactory.getLogger(PagoResumen.class);
    private static final Logger loggerMovimientos = LoggerFactory.getLogger(PagoMovimientos.class);


    // Mostrar el formulario para registrar un pago
    @GetMapping("/{idCita}/nuevo")
    public String mostrarFormularioPago(@PathVariable Integer idCita, Model model) {
        PagoResumen pagoResumen = pagosResumenService.obtenerResumenPorCita(idCita);

        model.addAttribute("condicion", pagoResumen.getIdResumen() == null);
        model.addAttribute("idCita", idCita);
        return "pago/alta-pago";
    }

    // Guardar el nuevo pago
    @PostMapping("/guardar")
    public String guardarPago(@ModelAttribute PagoDTO pagoDTO, RedirectAttributes attributes) {
        Cita cita = citaService.getById(pagoDTO.getIdCita());
        if (pagoDTO.getMontoTotal() != null) {
            //Se registra nuevo pago total
            PagoMovimientos movimientos = generatePagoMovimientos(cita, pagoDTO);
            movimientosService.registrarMovimiento(movimientos);
            loggerMovimientos.info("Se almacena nuevo movimiento: "+movimientos);

            //Se registra el pago resumen
            PagoResumen pagoResumen = generatePagoResumen(cita, pagoDTO);
            pagosResumenService.registrarResumen(pagoResumen);
            loggerResumen.info("Se almacena en BD pago resumen: "+pagoResumen);

            //Se da la cita por finalizada
            citaService.finalizarCita(pagoDTO.getIdCita());
        } else {
            //Se registra nuevo movimiento de pago y actualiza el resumen
            movimientosService.registrarMovimiento(generatePagoMovimientos(cita, pagoDTO));

            //Se actualiza el pago resumen
            pagosResumenService.actualizarResumen(generatePagoResumen(cita, pagoDTO));
        }
        attributes.addFlashAttribute("success", "Pago registrado exitosamente.");
        return "redirect:/pagos/filtrar"; //+ movimiento.getCita().getPaciente().getIdPaciente(); // Redirige a la lista de pagos de la cita
    }

    // Mostrar formulario de filtro por paciente
    @GetMapping("/filtrar")
    public String mostrarFiltroPacientes(Model model) {
        model.addAttribute("contenido", "Mostrar el historial de pagos realizados por un paciente");
        model.addAttribute("pacientes", pacienteService.getPacientesPorDentista(dentistaService.getDentistaActivo().getIdDentista()));
        return "pago/filtrar-pagos";
    }

    // Listar movimientos y resumen para una cita específica
    // Mostrar movimientos y resumen de pagos para un paciente
    @GetMapping("/pago-resumen")
    public String listarPagosPorPaciente(@RequestParam Integer idPaciente, @RequestParam Integer idCita, Model model, RedirectAttributes flash) {
        List<PagoMovimientos> movimientos = movimientosService.obtenerMovimientosPorPacienteYCita(idPaciente, idCita);
        PagoResumen resumen = pagosResumenService.obtenerResumenPorPacienteYCita(idPaciente, idCita);
        if (resumen == null) {
            flash.addFlashAttribute("warning", "No se han encontrado pagos asociados al paciente para la cita seleccionada, registra algún pago o selecciona otra cita.");
            return "redirect:/pagos/filtrar";
        }

        model.addAttribute("movimientos", movimientos);
        model.addAttribute("resumen", resumen);
        model.addAttribute("contenido", "Movimientos y Resumen de Pagos");
        model.addAttribute("paciente", pacienteService.getById(idPaciente));
        return "pago/pago-resumen";
    }

    @GetMapping("/citas")
    public String obtenerCitasPorPaciente(@RequestParam Integer idPaciente, Model model) {
        List<Cita> citas = citaService.obtenerCitasPorPaciente(idPaciente);

        model.addAttribute("citas", citas);
        return "fragments/citas-select ::citasOptions";
    }

    @GetMapping("/reporte")
    private String reporteForm() {
        return "pago/ingresos";
    }

    private PagoMovimientos generatePagoMovimientos(Cita cita, PagoDTO pagoDTO) {
        PagoMovimientos pagoMovimientos = new PagoMovimientos();
        pagoMovimientos.setCita(cita);
        pagoMovimientos.setMonto(pagoDTO.getMontoPago());
        pagoMovimientos.setMetodoPago(pagoDTO.getMetodoPago());
        pagoMovimientos.setFechaPago(LocalDate.now());
        pagoMovimientos.setComentarios(pagoDTO.getComentarios());
        return pagoMovimientos;
    }

    private PagoResumen generatePagoResumen(Cita cita, PagoDTO pagoDTO) {
        PagoResumen pagoResumen = new PagoResumen();
        pagoResumen.setCita(cita);
        pagoResumen.setMontoTotal(pagoDTO.getMontoTotal());
        pagoResumen.setMontoPagado(pagoDTO.getMontoPago());
        return pagoResumen;
    }
}
