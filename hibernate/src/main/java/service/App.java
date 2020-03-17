package service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojo.MyUser;
import pojo.User;
import utils.HibernateUtils;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.addUser();

    }

    public void addUser() {
        //加载配置
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        //得到工厂
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        //得到session
        Session session = sessionFactory.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        //执行动作
        User pojo = new User();
        pojo.setUsername("赵英杰");
        pojo.setPassword("没有密码");
        //提交事务
        transaction.commit();
        //关闭连接
        session.close();
        sessionFactory.close();
    }

    public void findById(Long id) {
        Session session = HibernateUtils.openSission();
        User user = session.get(User.class,id);
        System.out.println(user.getUsername() + "\t" + user.getPassword());
        session.close();
    }

    public void update() {
        Session session = HibernateUtils.openSission();
        Transaction transaction = session.beginTransaction();
        //如果更新部分字段，先得到后更新
        User user = session.get(User.class,2L);
        user.setPassword("123456");
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void delete(Long id) {
        Session session = HibernateUtils.openSission();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public void addMyUser() {
        Session session = HibernateUtils.openSission();
        Transaction transaction = session.beginTransaction();
        MyUser myUser = new MyUser();
        myUser.setUsername("aime");
        myUser.setPassword("9999999");
        session.save(myUser);
        transaction.commit();
        session.close();
    }
}
