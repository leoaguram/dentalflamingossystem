package com.dental_flamingos.service.status;

import com.dental_flamingos.exception.StatusNotFoundException;
import com.dental_flamingos.model.CatalogoStatus;
import com.dental_flamingos.repository.CatalogoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    CatalogoStatusRepository statusRepository;

    @Override
    public List<CatalogoStatus> getStatusPageable(int page, int size, String dirSort, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(dirSort), sort);
        Page<CatalogoStatus> pageResult = statusRepository.findAll(pageRequest);
        return pageResult.stream().toList();
    }

    @Override
    public List<CatalogoStatus> listStatus() {
        return statusRepository.findAll();
    }

    @Override
    public CatalogoStatus guardar(CatalogoStatus status) {
        statusRepository.save(status);
        return status;
    }

    @Override
    public boolean deleteStatus(Integer id) {
        Optional<CatalogoStatus> status = statusRepository.findById(id);
        if (status.isPresent()) {
            statusRepository.delete(status.get());
            return true;
        } else
            return false;
    }

    @Override
    public CatalogoStatus updateStatus(CatalogoStatus status) throws StatusNotFoundException {
        Optional<CatalogoStatus> catalogoStatusBD = statusRepository.findById(status.getIdStatus());
        if (catalogoStatusBD.isPresent()) {
            CatalogoStatus modificable = catalogoStatusBD.get();

            if (status.getIdStatus() != null) modificable.setIdStatus(status.getIdStatus());
            if (status.getDescripcion() != null) modificable.setDescripcion(status.getDescripcion());

            return statusRepository.save(modificable);
        } else {
            throw new StatusNotFoundException("El status indicado no se encuentra");
        }
    }

    @Override
    public CatalogoStatus getById(Integer id) {
        CatalogoStatus catalogoStatus = new CatalogoStatus();
        Optional<CatalogoStatus> statusBD = statusRepository.findById(id);
        if (statusBD.isPresent()) {
            catalogoStatus = statusBD.get();
        }
        return catalogoStatus;
    }
}
