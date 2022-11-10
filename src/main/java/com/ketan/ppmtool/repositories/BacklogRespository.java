package com.ketan.ppmtool.repositories;

import com.ketan.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRespository extends CrudRepository<Backlog,Long> {

    Backlog findByProjectIdentifier(String Identifier);
}
