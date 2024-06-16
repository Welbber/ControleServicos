package br.com.vital.controle_servico.vehicles.service;

import br.com.vital.controle_servico.vehicles.dto.VehicleFilterDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleRequestDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleResponseDTO;
import br.com.vital.controle_servico.vehicles.exception.VehicleAlreadyExistsException;
import br.com.vital.controle_servico.vehicles.exception.VehicleNotFoundException;
import br.com.vital.controle_servico.vehicles.mapper.VehicleMapper;
import br.com.vital.controle_servico.vehicles.repository.VehicleCriteriaRepository;
import br.com.vital.controle_servico.vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleCriteriaRepository vehicleCriteriaRepository;

    @Transactional
    public VehicleResponseDTO save(VehicleRequestDTO vehicleRequestDTO) {
        log.info("Save received vehicle to save: {}", vehicleRequestDTO);
        if (repository.existByLicensePlate(vehicleRequestDTO.plate())) {
            log.info("Vehicle already exists with License Plate: {}, ignore request", vehicleRequestDTO.plate());
            throw new VehicleAlreadyExistsException("Vehicle already exists with License Plate: %s".formatted(vehicleRequestDTO.plate()));
        }
        var vehicle = repository.saveAndFlush(VehicleMapper.toVehicle(vehicleRequestDTO));
        log.info("Vehicle saved: {}", vehicle);
        return VehicleMapper.toVehicleDTO(vehicle);
    }

    @Transactional(readOnly = true)
    public VehicleResponseDTO findById(Long id) {
        log.info("Find vehicle by id: {}", id);
        return repository.findById(id)
                .map(VehicleMapper::toVehicleDTO)
                .orElseThrow(VehicleNotFoundException::new);
    }

    public Slice<VehicleResponseDTO> findAll(VehicleFilterDTO filters, PageRequest pageable) {
        log.info("Find all vehicles pageable: {} and filters: {}", pageable, filters);
        var vehicles = vehicleCriteriaRepository.findAll(filters, pageable);
        if (vehicles.isEmpty()) {
            log.info("Vehicle list is empty");
            return new SliceImpl<>(List.of(), pageable, false);
        }

        log.info("Vehicles list found {}", vehicles.getContent());
        return vehicles.map(VehicleMapper::toVehicleDTO);
    }

    public List<VehicleResponseDTO> findAllByCustomer(Long customerId) {
        log.info("Find all vehicles by customer id: {}", customerId);
        var vehicles = repository.findByCustomerId(customerId);
        if (vehicles.isEmpty()) {
            log.info("Vehicle list is empty for Customer: {}", customerId);
            return List.of();
        }

        log.info("Vehicles list found {} for Customer: {}", vehicles, customerId);
        return vehicles.stream().map(VehicleMapper::toVehicleDTO).toList();
    }

    @Transactional
    public Boolean delete(Long id) {
        log.info("Delete received vehicle by id: {}", id);
        repository.deleteById(id);
        return true;
    }
}