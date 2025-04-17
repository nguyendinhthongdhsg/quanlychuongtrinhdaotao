package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    // huy them
    @Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT 1 FROM GiangVien gv WHERE gv.user.id = u.id)")
    List<User> findUsersNotAssignedToGiangVien();
}
