package com.dental_flamingos.service.tratamiento;

import com.dental_flamingos.exception.TratamientoNotFoundException;
import com.dental_flamingos.model.Tratamiento;
import com.dental_flamingos.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TratamientoServiceImpl implements TratamientoService {
    @Autowired
    TratamientoRepository tratamientoRepository;

    @Override
    public List<Tratamiento> getTratamientoPageable(int page, int size, String dirSort, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(dirSort), sort);
        Page<Tratamiento> pageResult = tratamientoRepository.findAll(pageRequest);
        return pageResult.stream().toList();
    }

    @Override
    public Tratamiento guardar(Tratamiento tratamiento) {
        tratamientoRepository.save(tratamiento);
        return tratamiento;
    }

    @Override
    public List<Tratamiento> listaTratamientos() {
        return tratamientoRepository.findAll();
    }

    @Override
    public boolean deleteTratamiento(Integer id) {
        Optional<Tratamiento> tratamiento = tratamientoRepository.findById(id);
        if (tratamiento.isPresent()) {
            tratamientoRepository.delete(tratamiento.get());
            return true;
        } else
            return false;
    }

    @Override
    public Tratamiento updateTratamiento(Tratamiento tratamiento) throws TratamientoNotFoundException {
        Optional<Tratamiento> tratamientoBD = tratamientoRepository.findById(tratamiento.getIdTratamiento());
        if (tratamientoBD.isPresent()) {
            Tratamiento modificable = tratamientoBD.get();

            if (tratamiento.getIdTratamiento() != null) modificable.setIdTratamiento(tratamiento.getIdTratamiento());
            if (tratamiento.getDescripcion() != null) modificable.setDescripcion(tratamiento.getDescripcion());

            return tratamientoRepository.save(modificable);
        } else {
            throw new TratamientoNotFoundException("El tratamiento solicitado no se encontró");
        }
    }

    @Override
    public Tratamiento getById(Integer id) {
        Tratamiento tratamientoReturned = new Tratamiento();
        Optional<Tratamiento> tratamientoBD = tratamientoRepository.findById(id);
        if (tratamientoBD.isPresent())
            tratamientoReturned = tratamientoBD.get();
        return tratamientoReturned;
    }
}
