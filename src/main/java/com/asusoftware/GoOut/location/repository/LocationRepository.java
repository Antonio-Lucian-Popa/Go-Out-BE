package com.asusoftware.GoOut.location.repository;

import com.asusoftware.GoOut.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> findByType(String type);
    @Query("SELECT l FROM Location l WHERE l.latitude BETWEEN :south AND :north AND l.longitude BETWEEN :west AND :east")
    List<Location> findWithinBounds(
            @Param("north") double north,
            @Param("south") double south,
            @Param("east") double east,
            @Param("west") double west
    );

}
