package edu.willamette.crossearch.repository;

public enum Domains {

    CONDM("condm.willamette.edu:81",
            "dmwebservices/index.php?q=dmQuery",
            "CISOSEARCHALL^{$query}^{$mode}^AND!",
            "nosort",
            "source!descri!title!creato!date",
            "10"),

    EXIST("exist.willamette.edu:8080",
            "exist/apps/METSALTO/api/SearchQuery.xquery?type=search&desc=add",
            "all^{$query}^{$mode}^and",
            "",
            "",
            "10"),

    DSPACE("dspace.willamette.edu:3000",
            "ds-api/solrQuery",
            "search/{$query}/discover",
            "",
            "",
            "10");

    private final String host;
    private final String rootPath;
    private final String query;
    private final String returnFields;
    private final String setSize;
    private final String sort;

    private Domains(String host, String rootPath, String query, String sort, String returnFields, String setSize) {

        this.host = host;
        this.rootPath = rootPath;
        this.query = query;
        this.returnFields = returnFields;
        this.setSize = setSize;
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

    public final String getSetSize() {
        return this.setSize;
    }

    public final String getSort() {
        return this.sort;
    }
}
