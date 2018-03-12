package com.drrufus.lab1server.dao;

import com.drrufus.autogenerated.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    
    private transient Logger logger = LoggerFactory.getLogger(UserDao.class);
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public User getUserById(Integer id) {
        logger.info("Retrieving user for id={}", id);
        String sql = "SELECT * FROM `users` WHERE `id` = ? ;";
        User user = (User)jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        return user;
    }
    
    public int insertUser(String login, String name, String email, String pass) {
        String sql = "insert into `users`(`name`, `email`, `login`, `pass`) values"
                + " (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, email, login, pass);
        
        sql = "select max(id) from `users`;";
        Integer lastId = jdbcTemplate.queryForObject(sql, Integer.class);
        logger.info("Inserted with ID={}", lastId);
        return lastId;
    }

    public List<User> getUserByParams(Integer id, String login, String name, String email, String pass) throws Exception {
        if (id != null && id == 0)
            id = null;
        logger.info("Retrieving users by id={}, login={}, name={}, email={}, pass={}",
                id, login, name, email, pass);
        
        if (id == null && login == null && name == null
                && email == null && pass == null)
            throw new Exception("No parameters for searching found");
        
        String sql = "SELECT * FROM `users` WHERE" +
                (id == null ? "" : (" `id` = " + id)) +
                (login == null ? "" : (" `login` = '" + login + "'")) +
                (name == null ? "" : (" `name` = '" + name + "'")) +
                (email == null ? "" : (" `email` = '" + email + "'")) +
                (pass == null ? "" : (" `pass` = '" + pass + "'"));
        logger.info("Generated SQL: {}", sql);
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }
    
    public void modifyUser(Integer id, String login, String name, String email, String pass) throws Exception {
        logger.info("Modifying user with id={}; new values: login={}, name={}, email={}, pass={}",
                id, login, name, email, pass);
        
        String sql = "UPDATE `users` SET " + 
                "`name` = " + (name == null ? "''," : ("'" + name + "',")) +
                "`email` = " + (email == null ? "''," : ("'" + email + "',")) +
                "`login` = " + (login == null ? "''," : ("'" + login + "',")) +
                "`pass` = " + (pass == null ? "''" : ("'" + pass + "'")) +
                " WHERE `id` = " + id.toString() + ";";
        
        logger.info("Generated SQL: {}", sql);
        jdbcTemplate.update(sql);
    }
    
    public void deleteUser(Integer id) {
        logger.info("Deleting user with ID={}", id);
        
        String sql = "DELETE FROM `users` WHERE `id` = " + id.toString() + ";";
        logger.info("Generated SQL: {}", sql);
        jdbcTemplate.update(sql);
    }
}