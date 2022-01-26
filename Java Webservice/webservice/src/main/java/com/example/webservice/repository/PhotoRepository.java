package com.example.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.webservice.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query(value="select * from Photo where idSignalement = ?1",nativeQuery= true)
	List<Photo> findPhotoByIdSignalement(Long id);
}
