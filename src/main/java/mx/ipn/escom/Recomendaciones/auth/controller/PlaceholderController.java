package mx.ipn.escom.Recomendaciones.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class PlaceholderController {    @GetMapping(value = "/api/placeholder/{width}/{height}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getPlaceholderImage(@PathVariable int width, @PathVariable int height) throws IOException {
        // Crear una imagen en blanco
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Establecer el color de fondo
        graphics.setColor(new Color(224, 224, 224)); // Gris claro
        graphics.fillRect(0, 0, width, height);

        // Dibujar un borde
        graphics.setColor(new Color(189, 189, 189)); // Gris más oscuro
        graphics.drawRect(0, 0, width - 1, height - 1);

        // Dibujar el texto
        graphics.setColor(new Color(117, 117, 117)); // Gris oscuro
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        String text = "No imagen";
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (width - metrics.stringWidth(text)) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics.drawString(text, x, y);

        graphics.dispose();

        // Convertir la imagen a bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
