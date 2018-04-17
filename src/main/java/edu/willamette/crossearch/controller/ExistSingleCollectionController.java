package edu.willamette.crossearch.controller;

import edu.willamette.crossearch.repository.ExistDbResponse;
import edu.willamette.crossearch.repository.Pagination;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ExistSingleCollectionController {

    @Autowired
    RepositoryInterface existDbSingleRepository;

    @Autowired
    Pagination pagination;

    @RequestMapping(value = "/exist/search/{collection}", method = GET)
    @ResponseBody
    public HttpEntity<ExistDbResponse> search(@RequestParam(value = "term") String searchTerm,
                                              @RequestParam(value = "offset") String offset,
                                              @RequestParam(value = "mode", defaultValue = "all") String mode,
                                              @PathVariable("collection") String collection) {

        NormalizedResult result = existDbSingleRepository.execQuery(searchTerm, offset, mode, collection);
        ExistDbResponse response = new ExistDbResponse(result);
        response.add(linkTo(methodOn(ExistdbController.class).search(searchTerm, offset, mode, collection)).withSelfRel());
        boolean hasNext = pagination.hasNext(result.getPager(), offset);
        boolean hasPrev = pagination.hasPrev(offset);
        if (hasNext) {
            String nextOffset = pagination.getOffset("next", offset, result.getPager());
            Link ordersLink = linkTo(methodOn(ExistSingleCollectionController.class).search(searchTerm, nextOffset, mode, collection)).withRel("next");
            response.add(ordersLink);
        }
        if (hasPrev) {
            String prevOffset = pagination.getOffset("prev", offset, result.getPager());
            Link ordersLink = linkTo(methodOn(ExistSingleCollectionController.class).search(searchTerm, prevOffset, mode, collection)).withRel("prev");
            response.add(ordersLink);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
