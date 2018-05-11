package org.springframework.data.elasticsearch;

public class TestBase {

    protected String getIndexName(){
        return "unit-test-"+this.getClass().getSimpleName().toLowerCase();
    }

}
