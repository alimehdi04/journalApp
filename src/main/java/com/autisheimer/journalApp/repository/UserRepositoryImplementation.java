package com.autisheimer.journalApp.repository;

import com.autisheimer.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImplementation {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSa(){
        Query query = new Query();

        //        query.addCriteria(Criteria.where("email").exists(true));
        //        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        List<User> users = mongoTemplate.find(query, User.class);
        for(User user : users){
            System.out.println(user);
        }
        return users;
    }

}
