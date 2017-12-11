package com.user.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.entity.LoginUser;

@Repository
public class AddUserDao {
	@Resource
	private SessionFactory sessionFactory;
	//保存用户
	  public void save(LoginUser loginUser)  {
			Session session = sessionFactory.openSession();
			Transaction tx=session.beginTransaction();
			session.save(loginUser);
			tx.commit();
			session.close();
		}
	  //根据用户名字查找用户
	  public List<LoginUser> findByName(String name){
		  Query query=this.sessionFactory.getCurrentSession().createQuery("from LoginUser where name=?");
		  query.setParameter(0, name);
			return query.list();		  
	  }
	  //查找所有用户
	  public List<LoginUser> users(){
		  Query query=this.sessionFactory.getCurrentSession().createQuery("from LoginUser");
		  List list=query.list();
		  return list;
	  }
	  
}
