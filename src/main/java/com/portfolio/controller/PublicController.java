package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.response.PublicPortfolioResponse;
import com.portfolio.service.PdfService;
import com.portfolio.service.PublicPortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public Portfolio", description = "View public portfolios — no authentication required")
public class PublicController {

    private final PublicPortfolioService publicPortfolioService;
    private final PdfService pdfService;

    @GetMapping("/{username}")
    @Operation(summary = "Get full portfolio by username")
    public ResponseEntity<ApiResponse<PublicPortfolioResponse>> getPortfolio(
            @PathVariable String username) {
        PublicPortfolioResponse portfolio = publicPortfolioService.getPortfolioByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(portfolio));
    }

    @GetMapping("/{username}/pdf")
    @Operation(summary = "Generate and download portfolio as PDF")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String username) {
        byte[] pdfBytes = pdfService.generatePortfolioPdf(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(username + "-portfolio.pdf")
                        .build()
        );
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
