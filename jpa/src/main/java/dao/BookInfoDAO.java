package dao;

import pojo.BookInfo;
import pojo.BookType;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BookInfoDAO {

    //多对一关联的添加
    public void addBook(BookInfo bookInfo) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(bookInfo);
        transaction.commit();
        entityManager.close();
    }

    //多对一关联的查询--默认是立即检索
    public void findById(Long id) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        BookInfo bookInfo = entityManager.find(BookInfo.class,id);
        //主表‘
        System.out.println(bookInfo.getBookName());
        System.out.println("===============================");
        //关联表
        BookType bookType = bookInfo.getBookType();
        System.out.println(bookType.getTypeName());
        entityManager.close();
    }



    public static void main(String[] args) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookName("Java从入门");
        bookInfo.setBookAuthor("aime");
        bookInfo.setBookPrice("100");
        bookInfo.setBookPublish("回收站");
        bookInfo.setBookDesc("赵大爷");
        bookInfo.setBookData(2019L);
        //关联对象
        BookType byId = new BookTypeDAO().findById(3L);
        bookInfo.setBookType(byId);
        BookInfoDAO bookInfoDAO = new BookInfoDAO();
        bookInfoDAO.addBook(bookInfo);
    }
}
