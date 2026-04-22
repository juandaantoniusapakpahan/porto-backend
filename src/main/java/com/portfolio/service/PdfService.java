package com.portfolio.service;

import com.lowagie.text.DocumentException;
import com.portfolio.dto.response.PublicPortfolioResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {

    private final TemplateEngine templateEngine;
    private final PublicPortfolioService publicPortfolioService;

    public byte[] generatePortfolioPdf(String username) {
        PublicPortfolioResponse portfolio = publicPortfolioService.getPortfolioByUsername(username);
        String html = renderHtml(portfolio);
        String xhtml = toXhtml(html);
        return convertToPdf(xhtml);
    }

    private String renderHtml(PublicPortfolioResponse portfolio) {
        Context context = new Context(Locale.ENGLISH);
        context.setVariable("portfolio", portfolio);
        context.setVariable("user", portfolio.user());
        context.setVariable("personalInfo", portfolio.personalInfo());
        context.setVariable("summary", portfolio.summary());
        context.setVariable("experiences", portfolio.experiences());
        context.setVariable("educations", portfolio.educations());
        context.setVariable("skills", portfolio.skills());
        context.setVariable("certifications", portfolio.certifications());
        context.setVariable("projects", portfolio.projects());
        context.setVariable("awards", portfolio.awards());
        context.setVariable("languages", portfolio.languages());
        return templateEngine.process("portfolio-pdf", context);
    }

    /**
     * Flying Saucer (xhtmlrenderer) requires valid XHTML input — its internal parser is XML-based
     * and will throw SAXParseException on HTML5 output (self-closing tags, HTML5 doctype, etc.).
     * Jsoup serialises the parsed DOM back as well-formed XML/XHTML.
     */
    private String toXhtml(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.outputSettings()
                .syntax(OutputSettings.Syntax.xml)
                .charset("UTF-8");
        return doc.html();
    }

    private byte[] convertToPdf(String xhtml) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            renderer.createPDF(out);
            log.debug("PDF generated successfully, size: {} bytes", out.size());
            return out.toByteArray();
        } catch (DocumentException | IOException e) {
            log.error("Failed to generate PDF: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed: " + e.getMessage(), e);
        }
    }
}
