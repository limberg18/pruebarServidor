package com.taller.service;

import com.taller.dto.ProformaDto;
import com.taller.model.Proforma;

import java.util.List;
import java.util.Optional;

public interface IProformaService {

    List<ProformaDto> findAll();

    Optional<ProformaDto> findById(Long id);

    ProformaDto save(ProformaDto proforma);

    ProformaDto update(Long id, ProformaDto dto);
    void deleteById(Long id);
}
