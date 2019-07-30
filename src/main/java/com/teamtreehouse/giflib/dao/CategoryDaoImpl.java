package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        // Get all categories with a Hibernate criteria
        //List<Category> categories = session.createCriteria(Category.class).list(); - deprecated
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        criteria.from(Category.class);

        List<Category> categories = session.createQuery(criteria).getResultList();

        // Close session
        session.close();

        return categories;
    }

    @Override
    public Category findById(Long id) {
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class, id);
        Hibernate.initialize(category.getGifs());
        session.close();
        return category;
    }

    @Override
    public void save(Category category) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the category
        session.saveOrUpdate(category);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the connection
        session.close();

    }

    @Override
    public void delete(Category category) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the category
        session.delete(category);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the connection
        session.close();
    }
}
