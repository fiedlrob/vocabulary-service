package com.raftec.palabrita.vocabularyservice.application.mapper;

import com.raftec.palabrita.vocabularyservice.application.dto.CollectionEntryResponse;
import com.raftec.palabrita.vocabularyservice.domain.model.CollectionEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionEntryMapper {
    CollectionEntryMapper INSTANCE = Mappers.getMapper( CollectionEntryMapper.class );

    @Mapping(target = "translations", expression = "java(collectionEntity.getTranslations())")
    CollectionEntryResponse collectionEntryToResponse(CollectionEntry collectionEntity);
}
