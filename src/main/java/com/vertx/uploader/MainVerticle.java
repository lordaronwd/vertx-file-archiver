package com.vertx.uploader;

import com.vertx.uploader.dto.UploadRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.post("/upload").handler(this::uploadFile);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        startFuture.succeeded();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });
    }

    @SneakyThrows
    private void uploadFile(final RoutingContext routingContext) {
        final UploadRequest uploadRequest = Json.decodeValue(routingContext.getBodyAsString(), UploadRequest.class);
        final byte[] decodedContent = Base64.getDecoder().decode(uploadRequest.getFileContent());
        Files.write(Paths.get(uploadRequest.getFileName()), decodedContent);

        routingContext.response().setStatusCode(201).end();
    }
}
