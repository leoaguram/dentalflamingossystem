package com.dental_flamingos.controller.status;

import com.dental_flamingos.utils.MayusculasConverter;
import com.dental_flamingos.exception.StatusNotFoundException;
import com.dental_flamingos.model.CatalogoStatus;
import com.dental_flamingos.service.status.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping("lista-status")
    public String listaDentista(Model model){
        model.addAttribute("status", statusService.listStatus());
        model.addAttribute("contenido","Consulta de los Status");
        return "status/lista-status";
    }

    @GetMapping("/{id}/editar")
    public String getFormEditar(@PathVariable Integer id, Model model) {
        CatalogoStatus status = statusService.getById(id);
        model.addAttribute("contenido", "Modificación de un Status");
        model.addAttribute("status", status);
        return "status/alta-status";
    }

    @GetMapping("/alta")
    public String altaStatus(Model model) {
        CatalogoStatus status = new CatalogoStatus();
        model.addAttribute("contenido", "Alta de un nuevo Status");
        model.addAttribute("status", status);
        return "status/alta-status";
    }

    @PostMapping("/guardar")
    public String guardarStatus(@Valid @ModelAttribute("status") CatalogoStatus status, BindingResult bindingResult,
                                Model model, RedirectAttributes flash) throws StatusNotFoundException {
        if(bindingResult.hasErrors()){
            model.addAttribute("contenido", "Alta de un nuevo Status");
            return "status/alta-status";
        }
        if (status.getIdStatus() != null) {
            statusService.updateStatus(status);
        } else
            statusService.guardar(status);
        flash.addFlashAttribute("success", "Se almacenó con éxito");
        return "redirect:/status/lista-status";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarStatus(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            statusService.deleteStatus(id);
            flash.addFlashAttribute("success", "Status eliminado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar el tratamiento porque puede estar siendo usando en algún registro");
        }
        return "redirect:/status/lista-status";
    }

    @InitBinder("status")
    public void nombreStatus(WebDataBinder binder) {
        binder.registerCustomEditor(String.class,
                "descripcion", new MayusculasConverter());
    }
}
