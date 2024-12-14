package com.dental_flamingos.controller.tratamiento;

import com.dental_flamingos.exception.TratamientoNotFoundException;
import com.dental_flamingos.model.Tratamiento;
import com.dental_flamingos.service.tratamiento.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "tratamientos")
public class TratamientoController {
    @Autowired
    TratamientoService tratamientoService;

    @GetMapping("lista-tratamiento")
    public String listaPaciente(Model model){
        List<Tratamiento> tratamientoList = tratamientoService.listaTratamientos();
        model.addAttribute("tratamiento", tratamientoList);
        model.addAttribute("contenido","Lista de Tratamientos");
        return "tratamiento/lista-tratamiento";
    }

    @GetMapping("/{id}/editar")
    public String getFormEditar(@PathVariable Integer id, Model model) {
        Tratamiento tratamiento = tratamientoService.getById(id);
        model.addAttribute("contenido", "Editar Tratamiento");
        model.addAttribute("tratamiento", tratamiento);
        return "tratamiento/alta-tratamiento";
    }

    @GetMapping("/alta")
    public String nuevoTrat(Model model) {
        Tratamiento tratamiento = new Tratamiento();
        model.addAttribute("contenido", "Alta de un nuevo Tratamiento");
        model.addAttribute("tratamiento", tratamiento);
        return "tratamiento/alta-tratamiento";
    }

    @PostMapping("/guardar")
    public String guardarTratamiento(@Valid @ModelAttribute("tratamiento") Tratamiento tratamiento, BindingResult bindingResult,
                                     Model model, RedirectAttributes flash) throws TratamientoNotFoundException {
        if(bindingResult.hasErrors()){
            model.addAttribute("contenido", "Alta de un nuevo Tratamiento");
            return "tratamiento/alta-tratamiento";
        }
        System.out.println("Formulario: " + tratamiento);
        if (tratamiento.getIdTratamiento() != null) {
            tratamientoService.updateTratamiento(tratamiento);
        }
        else
            tratamientoService.guardar(tratamiento);

        flash.addFlashAttribute("success", "Se almacenó con éxito");
        return "redirect:/tratamientos/lista-tratamiento";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTratamiento(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            tratamientoService.deleteTratamiento(id);
            flash.addFlashAttribute("success", "Tratamiento eliminado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar el tratamiento porque puede estar siendo usando en algún registro");
        }
        return "redirect:/tratamientos/lista-tratamiento";
    }

}
