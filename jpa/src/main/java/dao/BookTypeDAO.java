package dao;

import pojo.BookInfo;
import pojo.BookType;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.awt.print.Book;
import java.util.Set;

public class BookTypeDAO {

    public void addType(BookType bookType) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(bookType);
        transaction.commit();
        entityManager.close();
    }

    public BookType findById(Long typeId) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        BookType bookType = entityManager.find(BookType.class,typeId);
        entityManager.close();
        return bookType;
    }

    //一对多情况下的查询
    public void  findByIdA(Long typeID) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        BookType bookType = entityManager.find(BookType.class,typeID);
        //主表
        System.out.println(bookType.getTypeName());
        System.out.println("==============================");
        //关联表
        Set<BookInfo> books = bookType.getBooks();
        for(BookInfo book : books) {
            System.out.println(book.getBookName());
        }
        entityManager.close();
    }

    public static void main(String[] args) {
        BookType bookType = new BookType();
        bookType.setTypeName("好好学习");
        bookType.setTypeDesc("天天向上");
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        bookTypeDAO.addType(bookType);

        bookTypeDAO.findByIdA(3L);
    }
}
