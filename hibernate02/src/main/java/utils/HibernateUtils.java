package utils;
// Hibernate的单例模式
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static SessionFactory sessionFactory;
    static {
        //加载配置
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        //得到工厂
        SessionFactory sessionFactory = configuration.buildSessionFactory();
    }

    public static Session openSission() {
        return sessionFactory.openSession();
    }


}
