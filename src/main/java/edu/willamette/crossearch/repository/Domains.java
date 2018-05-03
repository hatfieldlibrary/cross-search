package edu.willamette.crossearch.repository;

import org.springframework.beans.factory.annotation.Value;

public enum Domains {

    CONDM("condm.willamette.edu:81",
            "dmwebservices/index.php?q=dmQuery",
            "CISOSEARCHALL^{$query}^{$mode}^AND!",
            "nosort",
            "source!descri!title!creato!date"),

    EXIST("exist.willamette.edu:8080",
            "exist/apps/METSALTO/api/SearchQuery.xquery?type=search&desc=add",
            "all^{$query}^{$mode}^and",
            "",
            ""),

    DSPACE("dspace.willamette.edu:3000",
            "ds-api/solrQuery",
            "search/{$query}/discover",
            "",
            "");

    private final String host;
    private final String rootPath;
    private final String query;
    private final String returnFields;
    private final String sort;

    private Domains(String host, String rootPath, String query, String sort, String returnFields) {

        this.host = host;
        this.rootPath = rootPath;
        this.query = query;
        this.returnFields = returnFields;
        this.sort = sort;
    }

    public final String getHost() {

        return this.host;
    }

    public final String getRootPath() {
        return this.rootPath;
    }

    public final String  getQuery() {
        return this.query;
    }

    public final String getReturnFields() {
        return this.returnFields;
    }

    public final String getSort() {
        return this.sort;
    }
}
