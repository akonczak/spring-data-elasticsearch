package org.springframework.data.elasticsearch.client;

import org.springframework.data.elasticsearch.client.model.IndexDescription;
import org.springframework.data.elasticsearch.client.model.Mappings;
import org.springframework.data.elasticsearch.client.model.Settings;

import java.io.IOException;

public interface IndicesAPI {

    /**
     * Since version 6 ES will support single type per index
     */
    public final String DEFAULT_INDEX_TYPE_NAME = "_doc";

    public boolean create(String indexName);

    public boolean create(String indexName, Settings settings, Mappings mappings);

    public boolean create(String indexName, String settings, String mappings);

    public boolean isExists(String indexName);

    public boolean delete(String indexName) ;

    public boolean addMappings(String indexName, Mappings mappings);

    public boolean addMappings(String indexName, String mappings);

    public IndexDescription getIndex(String indexName);

    public boolean refresh(String indexName);

}
