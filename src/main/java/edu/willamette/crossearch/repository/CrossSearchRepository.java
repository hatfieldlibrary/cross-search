package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.StatefulRecordHolder;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrossSearchRepository implements RepositoryInterface {

    private static Logger log = LogManager.getLogger(CrossSearchRepository.class);

    @Autowired
    ContentdmRepository contentdmRepository;

    @Autowired
    ExistDbRepository existDbRepository;

    @Autowired
    DspaceRepository dspaceRepository;

    @Value("${record.count}")
    String setSize;

    /**
     * This is the core cross-search method that queries repositories. Repository
     * queries and data normalization is handled on separate threads. The result
     * is sorted on the servlet request thread.
     * @param terms search term
     * @param offset current offset position in the result list
     * @param mode the query mode (all, any, phrase), default 'all'
     * @param collections the collections to search, default 'all'
     * @return
     */
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        StatefulRecordHolder holder = new StatefulRecordHolder();
        Flowable<RepositoryInterface> repos = getRepositories();
        List<NormalizedResult> result = repos.flatMap(
                repo -> execRepoQuery(repo, terms, offset, mode, collections)
                        .subscribeOn(Schedulers.io())
                        .flatMap(rec -> holder.combineResult(rec, offset, setSize)), 5)
                .toList()
                .blockingGet();
        log.debug("Return cross search result");
        return result.get(0);
    }

    /**
     * Calls a single repository's query method and converts the method call into Flowable.
     * @param repository
     * @param terms
     * @param offset
     * @param mode
     * @param collections
     * @return a Flowable created from the repository query.
     */
    private Flowable<NormalizedResult> execRepoQuery(RepositoryInterface repository,
                                                     String terms,
                                                     String offset,
                                                     String mode,
                                                     String collections) {

        return Flowable.fromCallable(() -> repository.execQuery(terms, offset, mode, collections));
    }

    /**
     * This method creates and returns a Flowable of the Spring injected repository objects.
     * @return
     */
    private Flowable<RepositoryInterface> getRepositories() {

        List<RepositoryInterface> queries = new ArrayList<>();
        queries.add(contentdmRepository);
        queries.add(existDbRepository);
        queries.add(dspaceRepository);
        return Flowable.fromIterable(queries);
    }

}
