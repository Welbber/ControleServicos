package br.com.vital.controle_servico.vehicles.service;

import br.com.vital.controle_servico.vehicles.dto.VehicleDTO;
import br.com.vital.controle_servico.vehicles.dto.VehicleFilterDTO;
import br.com.vital.controle_servico.vehicles.exception.VehicleAlreadyExistsException;
import br.com.vital.controle_servico.vehicles.exception.VehicleNotFoundException;
import br.com.vital.controle_servico.vehicles.mapper.VehicleMapper;
import br.com.vital.controle_servico.vehicles.repository.VehicleCriteriaRepository;
import br.com.vital.controle_servico.vehicles.repository.VeiculoRepository;
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

    private final VeiculoRepository repository;
    private final VehicleCriteriaRepository vehicleCriteriaRepository;

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

    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        log.info("Find vehicle by id: {}", id);
        return repository.findById(id)
                .map(VehicleMapper::toVehicleDTO)
                .orElseThrow(VehicleNotFoundException::new);
    }

    public Slice<VehicleDTO> findAll(VehicleFilterDTO filters, PageRequest pageable) {
        log.info("Find all vehicles pageable: {} and filters: {}", pageable, filters);
        var vehicles = vehicleCriteriaRepository.findAll(filters, pageable);
        if (vehicles.isEmpty()) {
            log.info("Vehicle list is empty");
            return new SliceImpl<>(List.of(), pageable, false);
        }

        log.info("Vehicles list found {}", vehicles.getContent());
        return vehicles.map(VehicleMapper::toVehicleDTO);
    }

    public List<VehicleDTO> findAllByCustomer(Long customerId) {
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