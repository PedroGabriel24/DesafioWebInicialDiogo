package org.example.desafiodiogo.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.service.BoletimPdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class BoletimPdfServiceImpl implements BoletimPdfService {

    @Override
    public byte[] gerarPdfBoletim(BoletimResponse boletim) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PdfFont fontTitle = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontHeader = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // =========================
        // TÍTULO
        // =========================

        Paragraph title = new Paragraph("BOLETIM ESCOLAR")
                .setFont(fontTitle)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);

        document.add(title);

        Paragraph data = new Paragraph("Data: " + LocalDate.now().format(formatter))
                .setFont(fontNormal)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(20);

        document.add(data);

        // =========================
        // DADOS DO ALUNO
        // =========================

        Paragraph infoAluno = new Paragraph()
                .setFont(fontNormal)
                .setFontSize(12)
                .setMarginBottom(15);

        infoAluno.add(new Text("Nome do Aluno: ").setFont(fontHeader));
        infoAluno.add(new Text(boletim.getAlunoNome()).setFont(fontNormal));
        infoAluno.add("\n");
        document.add(infoAluno);

        // =========================
        // TABELA DE NOTAS
        // =========================

        float[] columnWidths = {4f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginTop(10);
        table.setMarginBottom(20);

        // Header
        addHeaderCell(table, "Disciplina", fontHeader);
        addHeaderCell(table, "1º", fontHeader);
        addHeaderCell(table, "2º", fontHeader);
        addHeaderCell(table, "3º", fontHeader);
        addHeaderCell(table, "4º", fontHeader);
        addHeaderCell(table, "Média", fontHeader);

        boolean alternate = false;

        for (BoletimResponse.DisciplinaNotas disciplina : boletim.getDisciplinas()) {

            Color bgColor = alternate
                    ? ColorConstants.WHITE
                    : new DeviceRgb(245, 245, 245);

            alternate = !alternate;

            addCell(table, disciplina.getMateriaNome(), fontNormal, bgColor);

            BigDecimal soma = BigDecimal.ZERO;
            int count = 0;

            for (BoletimResponse.PeriodoNota periodo : disciplina.getNotas()) {

                if (periodo.getNota() != null) {
                    addCell(table, periodo.getNota().toString(), fontNormal, bgColor);
                    soma = soma.add(periodo.getNota());
                    count++;
                } else {
                    addCell(table, "-", fontNormal, bgColor);
                }
            }

            // Completar períodos faltantes até 4
            for (int i = disciplina.getNotas().size(); i < 4; i++) {
                addCell(table, "-", fontNormal, bgColor);
            }

            // Média
            if (count > 0) {
                BigDecimal mediaFinal = soma.divide(
                        BigDecimal.valueOf(count),
                        2,
                        RoundingMode.HALF_UP
                );

                addCell(
                        table,
                        mediaFinal.toString(),
                        fontHeader,
                        new DeviceRgb(220, 235, 255)
                );
            } else {
                addCell(table, "-", fontNormal, bgColor);
            }
        }

        document.add(table);

        // =========================
        // FOOTER
        // =========================

        Paragraph footer = new Paragraph(
                "Documento gerado automaticamente pelo sistema escolar"
        )
                .setFont(fontNormal)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(30);

        document.add(footer);

        document.close();

        return baos.toByteArray();
    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    private void addHeaderCell(Table table, String text, PdfFont font) {

        Cell cell = new Cell()
                .add(new Paragraph(text)
                        .setFont(font)
                        .setFontSize(12))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setPadding(8);

        table.addCell(cell);
    }

    private void addCell(Table table, String text, PdfFont font, Color bgColor) {

        Cell cell = new Cell()
                .add(new Paragraph(text)
                        .setFont(font)
                        .setFontSize(10))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBackgroundColor(bgColor)
                .setPadding(6);

        table.addCell(cell);
    }
}