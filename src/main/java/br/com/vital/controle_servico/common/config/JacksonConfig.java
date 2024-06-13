package br.com.vital.controle_servico.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JacksonConfig {


    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(PageImpl.class, new PageImplDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class PageImplDeserializer extends StdDeserializer<PageImpl> {

        public PageImplDeserializer() {
            super(PageImpl.class);
        }

        @Override
        public PageImpl deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                throws IOException {
            ObjectCodec codec = jsonParser.getCodec();
            JsonNode node = codec.readTree(jsonParser);

            JsonNode contentNode = node.get("content");
            List<Object> content = new ArrayList<>();
            for (JsonNode objNode : contentNode) {
                content.add(codec.treeToValue(objNode, Object.class));
            }

            JsonNode pageableNode = node.get("pageable");
            int page = pageableNode.get("pageNumber").intValue();
            int size = pageableNode.get("pageSize").intValue();
            Pageable pageable = PageRequest.of(page, size);

            long total = node.get("totalElements").longValue();

            return new PageImpl<>(content, pageable, total);
        }
    }
}
