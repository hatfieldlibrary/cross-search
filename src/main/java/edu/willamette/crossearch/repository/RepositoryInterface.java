package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.model.NormalizedResult;

public interface RepositoryInterface {

    NormalizedResult execQuery(String terms, String offset, String mode, String collections);
}
