package br.com.vital.controleServico.vehicles.service;

import br.com.vital.controleServico.vehicles.dto.VehicleDTO;
import br.com.vital.controleServico.vehicles.dto.VehicleFilterDTO;
import br.com.vital.controleServico.vehicles.exception.VehicleAlreadyExistsException;
import br.com.vital.controleServico.vehicles.exception.VehicleNotFoundException;
import br.com.vital.controleServico.vehicles.mapper.VehicleMapper;
import br.com.vital.controleServico.vehicles.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VeiculoRepository repository;

    @Transactional
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.info("Save received vehicle to save: {}", vehicleDTO);
        if (repository.existByLicensePlate(vehicleDTO.plate())) {
            log.info("Vehicle already exists with License Plate: {}, ignore request", vehicleDTO.plate());
            throw new VehicleAlreadyExistsException("Vehicle already exists with License Plate: %s".formatted(vehicleDTO.plate()));
        }
        var vehicle = repository.saveAndFlush(VehicleMapper.toVehicle(vehicleDTO));
        log.info("Vehicle saved: {}", vehicle);
        return VehicleMapper.toVehicleDTO(vehicle);
    }

//    public Page<VehicleDTO> findAll(Pageable pageable) {
//        Page<Vehicle> result = repository.findAll(pageable);
//        return null;//result.map(v -> model.map(v, VeiculoDTO.class));
//    }

    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        log.info("Find vehicle by id: {}", id);
        return repository.findById(id)
                .map(VehicleMapper::toVehicleDTO)
                .orElseThrow(VehicleNotFoundException::new);
    }

    public Slice<VehicleDTO> findAll(VehicleFilterDTO filters, PageRequest name) {
        return null;
    }
}