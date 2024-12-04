package com.vinay.radiant.repository;

import com.vinay.radiant.model.Chat;
import com.vinay.radiant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("select c from Chat c where :reqUser member of c.users and :targetUser member of c.users")
    public Chat findChatByUsersId(@Param("reqUser") User reqUser,@Param("targetUser") User targetUser);

    public List<Chat> findByUsersId(Integer userId);

}
