package edu.willamette.crossearch.dao;

import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.repository.ContentdmRepository;
import edu.willamette.crossearch.repository.ExistDbRepository;
import edu.willamette.crossearch.repository.RepositoryInterface;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrossSearchDao {

    @Autowired
    ContentdmRepository contentdmRepository;

    @Autowired
    ExistDbRepository existDbRepository;

    private static Logger log = LogManager.getLogger(CrossSearchDao.class);

    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        StatefulRecordHolder holder = new StatefulRecordHolder();
        Flowable<RepositoryInterface> urls = getUrls(terms, offset, mode, collections);
        List<NormalizedResult> result = urls.flatMap(
                url -> execRepoQuery(url, terms, offset, mode, collections)
                        .subscribeOn(Schedulers.io())
                        .flatMap(rec -> holder.combineResult(rec, offset, "10")), 5)
                .toList()
                .blockingGet();
        return result.get(0);
    }

    private Flowable<NormalizedResult> execRepoQuery(RepositoryInterface repository, String terms, String offset, String mode, String collections) {

        return Flowable.fromCallable(() -> repository.execQuery(terms, offset, mode, collections));
    }

    private Flowable<RepositoryInterface> getUrls(String terms, String offset, String mode, String collections) {

        List<RepositoryInterface> queries = new ArrayList<>();
        queries.add(contentdmRepository);
        queries.add(existDbRepository);
        return Flowable.fromIterable(queries);
    }

}
