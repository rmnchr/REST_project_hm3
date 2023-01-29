package DAO;

import lombok.Getter;

@Getter
public enum Driver {
    H2_TEST("jdbc:h2:mem:playtest;" +
            "INIT=" +
            "runscript from 'src/test/resources/create_db.sql'\\;" +
            "runscript from 'src/test/resources/init_db.sql'"),
    POSTGRES("jdbc:postgresql://localhost:0202/rest_project_hm3");

    private String url;

    Driver(String url) {
        this.url = url;
    }
}
