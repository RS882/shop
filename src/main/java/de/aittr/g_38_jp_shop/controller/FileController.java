package de.aittr.g_38_jp_shop.controller;


import de.aittr.g_38_jp_shop.exception_handler.Response;
import de.aittr.g_38_jp_shop.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public Response uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam String productTitle) {

        return new Response("File saved as"+ fileService.upload(file, productTitle));

    }
}
