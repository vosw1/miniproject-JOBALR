package com.example.jobala._user;

import com.example.jobala.jobopen.Jobopen;
import com.example.jobala.jobopen.JobopenResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public List<JobopenResponse.ListDTO> findAll() {
        String q = """
                select jb.id, jb.jobopen_title, jb.comp_location, jb.career, jb.edu, 
               (select img_filename from pic_tb where jobopen_id =  jb.id) img_filename  from jobopen_tb jb order by id desc;              
                """;
        Query query = em.createNativeQuery(q);

        JpaResultMapper rm = new JpaResultMapper();
        List<JobopenResponse.ListDTO> jobopenList = rm.list(query, JobopenResponse.ListDTO.class);
        return jobopenList;
    }

    public User findById(int id) {
        String q = """
                select * from user_tb where id = ?
                """;
        Query query = em.createNativeQuery(q,User.class);
        query.setParameter(1, id);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Transactional
    public void userSave(UserRequst.joinDTO reqDTO) {
        Query query = em.createNativeQuery("insert into  user_tb(name, username, email, password, address, phone, role, created_at) values(?, ?, ?, ?, ?, ?, ?, now()) ");
        query.setParameter(1,reqDTO.getName());
        query.setParameter(2,reqDTO.getUsername());
        query.setParameter(3,reqDTO.getEmail());
        query.setParameter(4,reqDTO.getPassword());
        query.setParameter(5,reqDTO.getAddress());
        query.setParameter(6,reqDTO.getPhone());
        query.setParameter(7,0);
        query.executeUpdate();
    }

    @Transactional
    public void compSave(UserRequst.joinDTO reqDTO) {
        Query query = em.createNativeQuery("insert into  user_tb(comp_num, ceo, compname, address, username, email, password, name, phone, role, created_at) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now()) ");
        query.setParameter(1,reqDTO.getCompNum());
        query.setParameter(2,reqDTO.getCeo());
        query.setParameter(3,reqDTO.getCompname());
        query.setParameter(4,reqDTO.getAddress());
        query.setParameter(5,reqDTO.getUsername());
        query.setParameter(6,reqDTO.getEmail());
        query.setParameter(7,reqDTO.getPassword());
        query.setParameter(8,reqDTO.getName());
        query.setParameter(9,reqDTO.getPhone());
        query.setParameter(10,1);
        query.executeUpdate();
    }

    public User findByUsernameAndPassword(UserRequst.loginDTO reqDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=? and password=?", User.class);
        query.setParameter(1, reqDTO.getUsername());
        query.setParameter(2, reqDTO.getPassword());


        try {
           User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void update() {
        return;
    }

    @Transactional
    public void delete() {
        return;
    }

    public User findByUsername(String username) {
        Query query = em.createNativeQuery("select * from user_tb where username=?", User.class);
        query.setParameter(1, username);
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }


}
