package br.com.vital.controle_servico.itens.service;

import br.com.vital.controle_servico.itens.dto.ItemFilterDTO;
import br.com.vital.controle_servico.itens.dto.ItemRequestDTO;
import br.com.vital.controle_servico.itens.dto.ItemResponseDTO;
import br.com.vital.controle_servico.itens.exception.ItemAlreadyExistsException;
import br.com.vital.controle_servico.itens.exception.ItemNotFoundException;
import br.com.vital.controle_servico.itens.mapper.ItemMapper;
import br.com.vital.controle_servico.itens.repository.ItemCriteriaRepository;
import br.com.vital.controle_servico.itens.repository.ItemRepository;
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
public class ItemService {

    private final ItemRepository repository;
    private final ItemCriteriaRepository repositoryItemCriteria;

    @Transactional
    public ItemResponseDTO save(ItemRequestDTO itemRequestDTO) {
        log.info("Save received item to save: {}", itemRequestDTO);
        if (repository.existByCodeAndDescription(itemRequestDTO.code(), itemRequestDTO.description())) {
            log.info("Item already exists with code: {} and description: {}, ignore request", itemRequestDTO.code(), itemRequestDTO.description());
            throw new ItemAlreadyExistsException("O Item cadastrado já existe com código: %s e descrição: %s".formatted(itemRequestDTO.code(), itemRequestDTO.description()));
        }
        var item = repository.saveAndFlush(ItemMapper.toItem(itemRequestDTO));
        log.info("Item saved: {}", item);
        return ItemMapper.toDTO(item);
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO findById(Long id) {
        log.info("Find received item by id: {}", id);
        return ItemMapper.toDTO(repository.findById(id)
                .orElseThrow(ItemNotFoundException::new));
    }

    @Transactional
    public ItemResponseDTO update(Long id, ItemRequestDTO itemRequestDTO) {
        log.info("Update received item to save changes: {}", itemRequestDTO);
        var item = repository.findById(id).orElseThrow(ItemNotFoundException::new);
        item.merger(itemRequestDTO);
        log.info("Item merged: {}", item);
        return ItemMapper.toDTO(repository.saveAndFlush(item));
    }

    @Transactional
    public Boolean delete(Long id) {
        log.info("Delete received item by id: {}", id);
        var item = repository.findById(id).orElseThrow(ItemNotFoundException::new);
        repository.deleteById(item.getId());
        log.info("Item deleted: {}", item);
        return true;
    }

    public Slice<ItemResponseDTO> findAll(ItemFilterDTO filters, PageRequest page) {
        log.info("Find all itens pageable: {} and filters: {}", page, filters);
        var itens = repositoryItemCriteria.findAll(filters, page);
        if (itens.isEmpty()) {
            log.info("Itens list is empty");
            return new SliceImpl<>(List.of(), page, false);
        }
        log.info("Itens list found {}", itens.getContent());
        return itens.map(ItemMapper::toDTO);
    }

}
