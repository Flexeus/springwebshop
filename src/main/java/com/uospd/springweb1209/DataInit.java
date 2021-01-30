package com.uospd.springweb1209;

import com.uospd.springweb1209.entities.Product;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInit{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    @SneakyThrows
    public void init(){
        URL url = ClassLoader.getSystemResource("bd.sql");
        if(url == null) return;
        Path path = Path.of(url.toURI());
        String sql = new String(Files.readAllBytes(path));
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createNativeQuery(sql);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

}
