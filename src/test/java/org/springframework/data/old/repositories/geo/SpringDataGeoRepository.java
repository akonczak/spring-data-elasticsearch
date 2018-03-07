package org.springframework.data.old.repositories.geo;

import org.springframework.data.old.entities.GeoEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by akonczak on 22/11/2015.
 */
public interface SpringDataGeoRepository extends ElasticsearchRepository<GeoEntity, String> {

}
