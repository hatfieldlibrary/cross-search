package edu.willamette.crossearch.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.willamette.crossearch.model.NormalizedResult;
import org.springframework.hateoas.ResourceSupport;

public class CrossSearchResponse extends ResourceSupport {

    private final NormalizedResult content;

    @JsonCreator
    public CrossSearchResponse(@JsonProperty("content") NormalizedResult content) {

        this.content = content;
    }

    public NormalizedResult getContent() {
        return content;
    }
}
