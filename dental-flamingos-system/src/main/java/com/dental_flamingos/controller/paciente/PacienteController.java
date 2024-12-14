package com.dental_flamingos.controller.paciente;

import com.dental_flamingos.controller.cita.CitaController;
import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.Dentista;
import com.dental_flamingos.model.Paciente;
import com.dental_flamingos.service.dentista.DentistaService;
import com.dental_flamingos.service.paciente.PacienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @Autowired
    DentistaService dentistaService;

    private static final Logger logger = LoggerFactory.getLogger(Paciente.class);

    @GetMapping("lista-paciente")
    public String listaPaciente(Model model){
        model.addAttribute("paciente", pacienteService.getPacientesPorDentista(dentistaService.getDentistaActivo().getIdDentista()));
        model.addAttribute("contenido","Lista de Pacientes");
        return "paciente/lista-paciente";
    }

    @GetMapping("/{id}/editar")
    public String getFormEditar(@PathVariable Integer id, Model model) {
        Paciente paciente = pacienteService.getById(id);
        model.addAttribute("contenido", "Modificación de un Paciente");
        model.addAttribute("paciente", paciente);
        return "paciente/alta-paciente";
    }

    @GetMapping("/alta")
    public String altaPaciente(Model model) {
        Paciente paciente = new Paciente();
        model.addAttribute("contenido", "Alta de un nuevo Paciente");
        model.addAttribute("paciente", paciente);
        return "paciente/alta-paciente";
    }

    @PostMapping("/guardar")
    public String guardarPaciente(@Valid @ModelAttribute("paciente") Paciente paciente, BindingResult bindingResult,
                                  Model model, RedirectAttributes flash) throws PacienteNotFoundException {
        if(bindingResult.hasErrors()){
            model.addAttribute("contenido", "Alta de un nuevo Paciente");
            return "paciente/alta-paciente";
        }

        Dentista selectDentista = dentistaService.getDentistaActivo();
        paciente.setDentista(selectDentista);

        if (paciente.getIdPaciente() != null) {
            pacienteService.updatePaciente(paciente);
        } else
            pacienteService.guardar(paciente);
        logger.info("Se guarda regitro: "+paciente);

        flash.addFlashAttribute("success", "Se almacenó con éxito");
        return "redirect:/pacientes/lista-paciente";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            pacienteService.deletePaciente(id);
            flash.addFlashAttribute("success", "Paciente eliminado exitosamente.");
            logger.info("Se almacena registro con ID: "+id);
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar el paciente ");
            logger.info("Se genera error al eliminar registro con ID: "+id);
        }
        return "redirect:/pacientes/lista-paciente";
    }
}
