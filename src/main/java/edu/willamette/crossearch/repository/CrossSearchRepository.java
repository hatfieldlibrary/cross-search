package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.CrossSearchDao;
import edu.willamette.crossearch.model.NormalizedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CrossSearchRepository implements RepositoryInterface {

    @Autowired
    CrossSearchDao crossSearchDao;

    @Override
    @Cacheable("search")
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        NormalizedResult crossSearchResult = crossSearchDao.execQuery(terms, offset, mode, collections);
        return crossSearchResult;
    }
}
