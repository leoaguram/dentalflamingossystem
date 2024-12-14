package com.dental_flamingos.controller.dentista;

import com.dental_flamingos.controller.cita.CitaController;
import com.dental_flamingos.exception.DentistaNotFoundException;
import com.dental_flamingos.model.Dentista;
import com.dental_flamingos.service.dentista.DentistaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
@RequestMapping("dentistas")
public class DentistaController {
    @Autowired
    DentistaService dentistaService;

    private static final Logger logger = LoggerFactory.getLogger(Dentista.class);

    @GetMapping("lista-dentista")
    public String listaDentista(Model model){
        model.addAttribute("dentista", dentistaService.buscarDentistas());
        model.addAttribute("contenido","Consulta de Dentistas");
        return "dentista/lista-dentista";
    }

    @GetMapping("/editar")
    public String getFormEditar(Model model) {
        Dentista dentista = dentistaService.getDentistaActivo();
        model.addAttribute("contenido", "Modificación de un Dentista");
        model.addAttribute("dentista", dentista);
        return "dentista/edita-dentista";
    }

    @PostMapping("/guardar")
    public String guardarStatus(@Valid @ModelAttribute("dentista") Dentista dentista, BindingResult bindingResult,
                                Model model, RedirectAttributes flash) throws DentistaNotFoundException {
        if(bindingResult.hasErrors()){
            model.addAttribute("contenido", "Modificación de un Dentista");
            return "dentista/modifica-dentista";
        }
        dentistaService.updateDentista(dentista);
        flash.addFlashAttribute("success", "Se almacenó con éxito");
        logger.info("Se modifica la información del dentista "+dentista);
        logger.info("Se cierra sesión para confirmar los cambios");
        return "redirect:/logout";
    }
}
