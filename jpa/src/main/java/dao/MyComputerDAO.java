package dao;

import pojo.MyComputer;
import sun.rmi.runtime.Log;
import utils.JPAUtils;

import javax.persistence.*;
import java.util.List;

public class MyComputerDAO {

    public void initTable() {
        //加载配置,得到工厂
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
        entityManagerFactory.close();
    }

    //添加数据
    public void addCom() {
        //加载配置,得到工厂
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
        //得到实体管理对象
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //操作数据
        MyComputer pojo = new MyComputer();
        pojo.setComName("联想");
        pojo.setComDesc("支持国产");
        entityManager.merge(pojo);
        //提交事务
        transaction.commit();
        //关闭连接
        entityManager.close();
        entityManagerFactory.close();
    }

    //查找数据
    public void findById(Long cid) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        MyComputer myComputer = entityManager.find(MyComputer.class, cid);
        System.out.println(myComputer.getComName()+"\t"+myComputer.getComDesc());
        entityManager.close();
    }

    //修改数据
    public void updateCom() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        MyComputer myComputer = entityManager.find(MyComputer.class,1L);
        myComputer.setComDesc("中国梦");
        entityManager.merge(myComputer);
        entityManager.close();
    }

    public void del(Long comid) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        MyComputer myComputer = entityManager.find(MyComputer.class,comid);
        entityManager.remove(myComputer);
        entityManager.merge(myComputer);
        entityManager.close();
    }

/*    ------------------------------------------------------------------------*/

    //JPQL操作数据库
    //得到总记录数
    public void findCount() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        String jpql = "select count(comId) from MyComputer";
        TypedQuery<Long> query = entityManager.createQuery(jpql,long.class);
        Long singleResult = query.getSingleResult();
        System.out.println(singleResult);
        entityManager.close();
    }

    // 通过JPQL查询
    public void findAll() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        String jpql = "from MyComputer";
        TypedQuery<MyComputer> query = entityManager.createQuery(jpql, MyComputer.class);
        //设置分页
        query.setFirstResult(0);//起始位置
        query.setMaxResults(2);//每页大小
        List<MyComputer> resultList = query.getResultList();
        for(MyComputer myComputer : resultList) {
            System.out.println(myComputer.getComName());
        }
        entityManager.close();
    }

    //通过JPQL单个查询
    public void findByIdJPQL(Long id) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        //命名占位
        String jpql = "select com from MyComputer com where com.comId=:cid";
        //索引占位
        //String jpql = "select com from MyComputer com where com.comId=?1";
        TypedQuery<MyComputer> query = entityManager.createQuery(jpql, MyComputer.class);
        //命名占位
        query.setParameter("cid",id);
        //索引占位
        //query.setParameter(1,id);
        MyComputer singleResult = query.getSingleResult();
        System.out.println(singleResult.getComName());
        entityManager.close();
    }

    //通过JPQL模糊查询
    public void findByLike() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        String jpql = "select com from MyComputer com where com.comName like :cn";
        TypedQuery<MyComputer> query = entityManager.createQuery(jpql,MyComputer.class);
        query.setParameter("cn","%te%");
        List<MyComputer> resultList = query.getResultList();
        for(MyComputer myComputer : resultList) {
            System.out.println(myComputer.getComName());
        }
        entityManager.close();
    }

    //通过JPQL查询部分字段-投影查询
    public void findDepart() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        String jpql = "select com.comName,com.comDesc from MyComputer";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql,Object[].class);
        List<Object[]> resultList = query.getResultList();
        for(Object[] objects : resultList) {
            System.out.println(objects[0]+"\t"+objects[1]);
        }
        entityManager.close();
    }

    //通过JPQL更新
    public void updateComByJPQL() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        String jpql = "update MyComputer set comDesc=:cdesc where comId=:cid";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("cdesc","最后一次");
        query.setParameter("cid","5L");
        entityTransaction.commit();
        entityManager.close();
    }

    //通过JPQL删除
    public void delComByJPQL() {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        String jpql = "delete from MyComputer where comId=:cid";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("cid","4L");
        entityTransaction.commit();
        entityManager.close();
    }

    public static void main(String[] args) {
        MyComputerDAO myComputerDAO = new MyComputerDAO();
        myComputerDAO.initTable();
        // 添加数据
        //myComputerDAO.addCom();
        // 查找数据
        //myComputerDAO.findById(1L);
        //修改数据
        //myComputerDAO.updateCom();
        //删除数据
        //myComputerDAO.del(1L);
        /*--------------------------------------------------------*/
        //通过JPQL查询
        //myComputerDAO.findAll();
        //通过JPQL查询单个
        //myComputerDAO.findByIdJPQL(3L);
        //通过JPQL进行模糊查询
        //myComputerDAO.findByLike();
        //通过JPQL查询部分字段-投影查询
        //myComputerDAO.findDepart();
        //通过JPQL更新
        //myComputerDAO.updateComByJPQL();
        //通过JPQL删除
        //myComputerDAO.delComByJPQL();
        //得到总记录数
        //myComputerDAO.findCount();
    }
}
