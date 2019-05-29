package com.projects.gallery.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.gallery.Entity.Image;


@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

	Page<Image>findAllByOrderByIdDesc(Pageable pageable);
}
