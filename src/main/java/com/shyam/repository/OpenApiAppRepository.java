package com.shyam.repository;

import com.shyam.model.OpenApiApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OpenApiAppRepository extends JpaRepository<OpenApiApp, Long> {

    /**
    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.title=:title, m.category=:category, m.rating=:rating WHERE m.movieId = :id")
    Integer updateMovie(@Param("id") Long movieId, @Param("title") String title,
                    @Param("category") String category, @Param("rating") double rating);
     **/
    @Query(value = "select * from open_api_app where app = :app and version in (select coalesce(max(version),0) from open_api_app where app = :app)" , nativeQuery = true)
    OpenApiApp getLatestOpenApiApp(@Param("app") String app);



}
