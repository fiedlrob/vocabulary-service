package com.raftec.palabrita.vocabularyservice.application.mapper;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionResponse;
import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionMapper {
    CollectionMapper INSTANCE = Mappers.getMapper( CollectionMapper.class );

    CollectionResponse collectionToResponse(Collection collection);
}
