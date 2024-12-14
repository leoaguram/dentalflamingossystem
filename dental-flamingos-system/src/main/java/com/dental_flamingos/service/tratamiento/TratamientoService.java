package com.dental_flamingos.service.tratamiento;

import com.dental_flamingos.exception.TratamientoNotFoundException;
import com.dental_flamingos.model.Tratamiento;

import java.util.List;
import java.util.Optional;

public interface TratamientoService {
    List<Tratamiento> getTratamientoPageable(int page, int size, String dirSort, String sort);
    Tratamiento guardar(Tratamiento tratamiento);
    List<Tratamiento> listaTratamientos();
    boolean deleteTratamiento(Integer id);
    Tratamiento updateTratamiento(Tratamiento tratamiento) throws TratamientoNotFoundException;
    Tratamiento getById(Integer id);
}
