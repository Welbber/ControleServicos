package br.com.vital.controle_servico.order_service.util;

import br.com.vital.controle_servico.common.util.BigDecimalFormat;
import br.com.vital.controle_servico.order_service.dto.DetailOrderServiceResponseDTO;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildPDFDetailOrderService {

    public static ByteArrayOutputStream buildPDF(DetailOrderServiceResponseDTO detail) {
        var today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        log.info("Starting builder pdf export in {} for order {}", today, detail);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            builderHeader(document, today);
            builderCustomerInfo(detail, document);
            builderOrderInfo(detail, document);
            builderItemsDetail(detail, document);
            builderAmountTotal(detail, document);
            document.close();

            return byteArrayOutputStream;
        } catch (Exception e) {
            log.error("Error to export PDF with detail order: {}", e.getMessage());
            throw new IllegalStateException("Não foi possível gerar o arquivo PDF da Ordem de Serviço.");
        }
    }

    private static void builderAmountTotal(DetailOrderServiceResponseDTO detail, Document document) {
        var amountTotalFormatted = BigDecimalFormat.getBigDecimalToCurrencyFormatted(detail.amountTotal());
        document.add(getSimpleParagraph("TOTAL: %s".formatted(amountTotalFormatted)).setBold());
    }

    private static void builderItemsDetail(DetailOrderServiceResponseDTO detail, Document document) {
        Table table = new Table(new float[]{280F, 80F, 150F});
        Color headerColor = new DeviceRgb(168, 168, 168);

        // header of table
        table.addCell(new Cell().add(getSimpleParagraph("Descrição")).setBold().setBackgroundColor(headerColor));
        table.addCell(new Cell().add(getSimpleParagraph("Quantidade")).setBold().setBackgroundColor(headerColor));
        table.addCell(new Cell().add(getSimpleParagraph("Valor")).setBold().setBackgroundColor(headerColor));

        // Items of table add
        detail.items().forEach(i -> {
            table.addCell(new Cell().add(getSimpleParagraph(i.description())));
            table.addCell(new Cell().add(getSimpleParagraph(i.quantity().toString())));
            table.addCell(new Cell().add(getSimpleParagraph(BigDecimalFormat.getBigDecimalToCurrencyFormatted(i.saleAmount()))));
        });

        document.add(table);
    }

    private static void builderOrderInfo(DetailOrderServiceResponseDTO detail, Document document) {
        document.add(getSimpleParagraph("___________________________________________________________________________"));
        document.add(getSimpleParagraph("DADOS DA ORDEM DE SERVIÇO").setFontSize(12).setBold());
        document.add(getSimpleParagraph("Cod da Ordem: %s".formatted(detail.id())));
        document.add(getSimpleParagraph("Descrição: %s".formatted(detail.description())));
        document.add(getSimpleParagraph("Quantidade Itens: %s".formatted(detail.quantityItemsTotal())));
    }

    private static void builderCustomerInfo(DetailOrderServiceResponseDTO detail, Document document) {
        document.add(getSimpleParagraph("DADOS DO CLIENTE").setFontSize(12).setBold());
        document.add(getSimpleParagraph("Cod do Cliente: %s".formatted(detail.customerId())));
        document.add(getSimpleParagraph("Nome: %s".formatted(detail.customerName())));
        document.add(getSimpleParagraph("E-mail: %s".formatted(detail.customerEmail())));
        document.add(getSimpleParagraph("Placa do Veículo: %s".formatted(detail.licensePlate())));
    }

    private static void builderHeader(Document document, String today) {
        document.add(new Paragraph("VITAL AUTOPEÇAS E SERVIÇOS").setFontSize(16).setBold().setTextAlignment(TextAlignment.LEFT));
        document.add(getSimpleParagraph("Telefone: (83) 9 8615-7184  -  @vital_auto_pecas_"));
        document.add(getSimpleParagraph("End: Rua 15 de Novembro, 706, Palmeira - Campina Grande, Após o Quartel do Exército"));
        document.add(getSimpleParagraph(today));
        document.add(getSimpleParagraph("___________________________________________________________________________"));
    }

    private static Paragraph getSimpleParagraph(String text) {
        return new Paragraph(text).setFontSize(11);
    }

}
