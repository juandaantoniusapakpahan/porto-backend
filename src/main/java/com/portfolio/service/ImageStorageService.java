package com.portfolio.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class ImageStorageService {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private Path getAvatarDir() throws IOException {
        Path dir = Paths.get(uploadDir, "avatars").toAbsolutePath();
        Files.createDirectories(dir);
        return dir;
    }

    public String storeAvatar(MultipartFile file, UUID userId) throws IOException {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Only JPG, PNG, WebP, and GIF images are allowed");
        }

        String extension = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif" -> ".gif";
            default -> ".jpg";
        };

        String filename = "avatar_" + userId + "_" + UUID.randomUUID() + extension;
        Path dest = getAvatarDir().resolve(filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        log.debug("Stored avatar: {}", dest);
        return "/api/uploads/avatars/" + filename;
    }

    public void deleteAvatar(String avatarUrl) {
        if (avatarUrl == null || !avatarUrl.startsWith("/api/uploads/avatars/")) {
            return;
        }
        try {
            String filename = avatarUrl.substring("/api/uploads/avatars/".length());
            Path file = getAvatarDir().resolve(filename);
            Files.deleteIfExists(file);
            log.debug("Deleted avatar: {}", file);
        } catch (IOException e) {
            log.warn("Failed to delete avatar file: {}", e.getMessage());
        }
    }

    /**
     * Converts a local avatar to a circular PNG data URL for embedding in PDF.
     * Pre-renders the circular crop in Java since Flying Saucer (CSS 2.1) does not support border-radius.
     * Adds a white border ring around the circle.
     */
    public String toCircularDataUrl(String avatarUrl) {
        if (avatarUrl == null || !avatarUrl.startsWith("/api/uploads/avatars/")) {
            return avatarUrl;
        }
        try {
            String filename = avatarUrl.substring("/api/uploads/avatars/".length());
            Path file = getAvatarDir().resolve(filename);
            if (!Files.exists(file)) return null;

            BufferedImage original = ImageIO.read(file.toFile());
            if (original == null) return toDataUrl(avatarUrl);

            int size = Math.min(original.getWidth(), original.getHeight());
            int cropX = (original.getWidth() - size) / 2;
            int cropY = (original.getHeight() - size) / 2;

            int border = Math.max(6, size / 20); // ~5% white ring
            int total = size + border * 2;

            BufferedImage output = new BufferedImage(total, total, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = output.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // White border circle
            g.setColor(Color.WHITE);
            g.fillOval(0, 0, total, total);

            // Clip to inner circle and draw cropped image
            g.setClip(new Ellipse2D.Float(border, border, size, size));
            g.drawImage(original, border, border, border + size, border + size,
                    cropX, cropY, cropX + size, cropY + size, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(output, "PNG", baos);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            log.warn("Failed to create circular avatar for PDF: {}", e.getMessage());
            return toDataUrl(avatarUrl);
        }
    }

    /**
     * Converts a local avatar URL to a base64 data URL for embedding in PDF.
     * Returns null if the file cannot be read.
     */
    public String toDataUrl(String avatarUrl) {
        if (avatarUrl == null || !avatarUrl.startsWith("/api/uploads/avatars/")) {
            return avatarUrl;
        }
        try {
            String filename = avatarUrl.substring("/api/uploads/avatars/".length());
            Path file = getAvatarDir().resolve(filename);
            if (!Files.exists(file)) return null;

            byte[] bytes = Files.readAllBytes(file);
            String mimeType = Files.probeContentType(file);
            if (mimeType == null) mimeType = "image/jpeg";
            return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            log.warn("Failed to read avatar for PDF: {}", e.getMessage());
            return null;
        }
    }
}
