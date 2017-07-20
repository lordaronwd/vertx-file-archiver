package com.vertx.uploader.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author lazar.agatonovic
 */
@NoArgsConstructor
@Getter
public class UploadRequest {

    private String fileName;
    private String fileContent;
}
