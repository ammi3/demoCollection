package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.BookInfo;
import pojo.BookType;
import utils.HibernateUtils;

import java.util.Date;

public class BookInfoDAO {

    public BookInfo findById(Long bookId) {
        Session session = HibernateUtils.openSission();
        BookInfo bookInfo = session.get(BookInfo.class, bookId);
        //主表信息
        System.out.println(bookInfo.getBookName());
        //关联表信息
        System.out.println(bookInfo.getBookTypeByTypeId().getTypeName());
        session.close();
        return bookInfo;
    }

    public void addBook(BookInfo pojo) {
        Session session = HibernateUtils.openSission();
        Transaction transaction = session.beginTransaction();
        session.save(pojo);
        transaction.commit();
        session.close();
    }

    public static void main(String[] args) {
        BookInfoDAO bookInfoDAO = new BookInfoDAO();
        //测试多对一的查询
        BookInfo bookInfo = bookInfoDAO.findById(2L);
        // 多对一的添加
        BookInfo pojo = new BookInfo();
        pojo.setBookName("Java");
        pojo.setBookAuthor("aime");
        pojo.setBookPrice(88.88);
        pojo.setBookDate(new Date().getTime());
        pojo.setBookPublish("Java");
        pojo.setBookDesc("Java");
        //得到关联对象
        BookType bookType = new BookTypeDAO().findById(3L);
        //关联字段是对象
        pojo.setBookTypeByTypeId(bookType);
        bookInfoDAO.addBook(pojo);
    }
}
