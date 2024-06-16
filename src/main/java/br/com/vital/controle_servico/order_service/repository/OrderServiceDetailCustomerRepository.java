package br.com.vital.controle_servico.order_service.repository;

import br.com.vital.controle_servico.order_service.domain.OrderServiceStatus;
import br.com.vital.controle_servico.order_service.domain.OrderServiceType;
import br.com.vital.controle_servico.order_service.dto.DetailOrderServiceResponseDTO;
import br.com.vital.controle_servico.order_service.dto.OrderServiceDetailItemResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderServiceDetailCustomerRepository {

    private final EntityManager manager;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public DetailOrderServiceResponseDTO findByOrderServiceId(Long orderServiceId) {
        Session session = manager.unwrap(Session.class);
        String sql = """
                              SELECT os.id,
                                   os.description        AS description,
                                   os.amount             AS amountTotal,
                                   os.km_vehicle_at      AS kmVehicleAt,
                                   os.quantity_itens     AS quantityItemsTotal,
                                   os.order_service_type AS type,
                                   os.status,
                                   c.id AS customerId, 
                                   c.email AS customerEmail,
                                   c.name  AS customerName,
                                   v.license_plate  AS licensePlate,
                                   jsonb_agg(json_build_object(
                                   'code', i.code,
                                   'description', i.description,
                                   'quantity',osd.quantity_item,
                                   'saleAmount', osd.sale_amount_at)) AS items
                            FROM order_service os
                                     LEFT JOIN customers c ON c.id = os.customer_id
                                     LEFT JOIN  vehicles v ON c.id = v.customer_id AND os.vehicle_id = v.id
                                     INNER JOIN order_service_detail_itens osd ON os.id = osd.order_service_id
                                      LEFT JOIN  itens i ON osd.item_id = i.id
                            WHERE os.id = ?1
                            GROUP BY os.id, os.description, os.amount, os.km_vehicle_at, os.quantity_itens,
                                     os.order_service_type, os.status,c.id, c.email,c.name, v.license_plate
                """;

        var q = session.createNativeQuery(sql);
        q.setParameter(1, orderServiceId);
        Object[] result = (Object[]) q.getSingleResult();
        return mapper(result);
    }

    private DetailOrderServiceResponseDTO mapper(Object[] result) {
        return new DetailOrderServiceResponseDTO(
                Long.parseLong(result[0].toString()),
                result[1].toString(),
                BigDecimal.valueOf(Double.parseDouble(result[2].toString())),
                Integer.parseInt(result[3].toString()),
                Integer.parseInt(result[4].toString()),
                OrderServiceType.valueOf(result[5].toString()),
                OrderServiceStatus.valueOf(result[6].toString()),
                Long.parseLong(result[7].toString()),
                result[8].toString(),
                result[9].toString(),
                result[10].toString(),
                convertToObject(result[11].toString())
        );
    }

    private List<OrderServiceDetailItemResponseDTO> convertToObject(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error to convert string to object: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
