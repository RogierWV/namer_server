package io.snynx.namegengrpcserver.services;

import io.snynx.namegengrpcinterface.*;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;

import io.snynx.namegengrpcserver.repositories.FavoriteRepository;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class FavoritesServiceImpl extends FavoritesServiceGrpc.FavoritesServiceImplBase {
    private FavoriteRepository repo;
    private ModelMapper modelMapper;

    @Override
    public void storeFavorite(Favorite request, StreamObserver<StoreFavoriteReply> responseObserver) {
//        super.storeFavorite(request, responseObserver);
        log.info(String.format("favorite from %d : %s %s",
                request.getUid(),
                request.getName1(),
                request.getName2()));
        // store the actual thing and respond with status
        Status status = Status.Success;
        // try / catch blocks to store in repo
        try {
            repo.save(modelMapper.map(request, io.snynx.namegengrpcserver.models.Favorite.class));
        } catch(Exception e) {
            log.error(e.getMessage());
            status = Status.Error;
        }
        StoreFavoriteReply reply = StoreFavoriteReply.newBuilder()
                .setStatus(status)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getFavorites(FavoriteRequest request, StreamObserver<Favorite> responseObserver) {
        log.info("starting request...");
        repo.getByUid(request.getUid())
                .map(f -> modelMapper.map(f, io.snynx.namegengrpcinterface.Favorite.class))
                .doOnEach(s -> responseObserver.onNext(s.get()));
        responseObserver.onCompleted();
    }
}
