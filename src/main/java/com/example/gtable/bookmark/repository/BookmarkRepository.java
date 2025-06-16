package com.example.gtable.bookmark.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.bookmark.entity.Bookmark;
import com.example.gtable.store.model.Store;
import com.example.gtable.user.entity.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
	boolean existsByUserAndStore(User user, Store store);

	Collection<Bookmark> findAllByUser(User user);
}
