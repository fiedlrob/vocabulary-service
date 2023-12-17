package com.raftec.palabrita.vocabularyservice.infrastructure.repositories;

import com.raftec.palabrita.vocabularyservice.domain.model.CollectionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionEntryRepository extends JpaRepository<CollectionEntry, Long>, JpaSpecificationExecutor<CollectionEntry> {

}
