package com.dental_flamingos.controller.cita;

import com.dental_flamingos.controller.index.IndexController;
import com.dental_flamingos.exception.CitaNotFoundException;
import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.*;
import com.dental_flamingos.service.cita.CitaService;
import com.dental_flamingos.service.dentista.DentistaService;
import com.dental_flamingos.service.paciente.PacienteService;
import com.dental_flamingos.service.status.StatusService;
import com.dental_flamingos.service.tratamiento.TratamientoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "citas")
public class CitaController {
    @Autowired
    CitaService citaService;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    DentistaService dentistaService;

    @Autowired
    StatusService statusService;

    @Autowired
    TratamientoService tratamientoService;

    private static final Logger logger = LoggerFactory.getLogger(CitaController.class);

    @GetMapping("/filtrar")
    public String filtrarCitas(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Integer tratamiento,
            Model model) {
        
        LocalDateTime fechaInicioSQL = fechaInicio != null
                ? fechaInicio.atStartOfDay()
                : null;

        LocalDateTime fechaFinSQL = fechaFin != null
                ? fechaFin.atTime(LocalTime.MAX)
                : null;

        List<Cita> citasFiltradas = citaService.buscarCitasConFiltros(
                status,
                fechaInicioSQL,
                fechaFinSQL,
                tratamiento
        );

        model.addAttribute("cita", citasFiltradas);
        return "fragments/resultados-tabla :: resultadosTabla";
    }

    @GetMapping("/lista-cita")
    public String listaCita(Model model){
        List<Cita> citaList = citaService.buscarCitasPorDentista(dentistaService.getDentistaActivo().getIdDentista());
        List<CatalogoStatus> statusList = statusService.listStatus();
        List<CatalogoStatus> statusesFinal = statusList.subList(0, 3);
        List<Tratamiento> tratamientos = tratamientoService.listaTratamientos();

        model.addAttribute("cita", citaList);
        model.addAttribute("contenido","Lista de Citas");
        model.addAttribute("statusList", statusesFinal);
        model.addAttribute("tratamientosList", tratamientos);
        return "cita/lista-cita";
    }

    @GetMapping("/{id}/editar")
    public String getFormEditar(@PathVariable Integer id, Model model) {
        Cita cita = citaService.getById(id);
        model.addAttribute("contenido", "Modificación de una Cita");
        model.addAttribute("cita", cita);

        List<CatalogoStatus> statusList = statusService.listStatus();
        List<CatalogoStatus> statusesFinal = statusList.subList(0, 3);

        model.addAttribute("selectPaciente", cita.getPaciente());
        model.addAttribute("selectTratamiento", cita.getTratamiento());
        model.addAttribute("selectStatus", statusesFinal);
        return "cita/alta-cita";
    }

    @GetMapping("/alta")
    public String altaCita(Model model){
        Cita cita = new Cita();

        List<Paciente> selectPaciente = pacienteService.getPacientesPorDentista(dentistaService.getDentistaActivo().getIdDentista());
        List<Tratamiento> selectTratamiento = tratamientoService.listaTratamientos();
        List<CatalogoStatus> selectStatus = statusService.listStatus();
        List<CatalogoStatus> finalStatus = selectStatus.subList(0, 3);

        model.addAttribute("contenido", "Alta de Cita");
        model.addAttribute("cita", cita);
        model.addAttribute("selectPaciente", selectPaciente);
        model.addAttribute("selectTratamiento", selectTratamiento);
        model.addAttribute("selectStatus", finalStatus);
        return "cita/alta-cita";
    }

    @PostMapping("/guardar")
    public String guardarCita(@Valid @ModelAttribute("cita") Cita cita, BindingResult bindingResult,
                              Model model, RedirectAttributes flash) throws PacienteNotFoundException, CitaNotFoundException {

        if(bindingResult.hasErrors()){
            model.addAttribute("contenido", "Alta de una nueva Cita");
            return "cita/alta-cita";
        }
        if (cita.getIdCita() != null) {
            citaService.updateCita(cita);
        } else
            citaService.guardar(cita);

        logger.info("Se generó nuevo registro : "+cita);

        flash.addFlashAttribute("success", "Se almacenó con éxito");
        return "redirect:/citas/lista-cita";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            citaService.deleteCita(id);
            flash.addFlashAttribute("success", "Cita eliminada exitosamente.");
            logger.info("Se elimina registro cita con ID: "+id);
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar la cita: ");
            logger.info("Se generó un error al borrar cita  con ID: "+id+ " debido a: " + e.getMessage());
        }
        return "redirect:/citas/lista-cita";
    }
}
