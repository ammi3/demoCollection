package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.BookInfo;
import pojo.BookType;
import utils.HibernateUtils;

import java.util.Collection;

public class BookTypeDAO {
    public BookType findById(Long typeId) {
        Session session = HibernateUtils.openSission();
        BookType bookType = session.get(BookType.class, typeId);
        // 分类信息
        System.out.println(bookType.getTypeName());
        //分类所对应的图书
        Collection<BookInfo> bookInfoCollection = bookType.getBookInfosByTypeId();
        for(BookInfo bookInfo : bookInfoCollection) {
            System.out.println(bookInfo.getBookName());
        }
        session.close();
        return bookType;
    }

    //单表的延迟与立即,只关系主表
    public void findByIdLazy(Long typeId) {
        Session session = HibernateUtils.openSission();
        //立即检索-直接查询数据库，立即向数据库发送SQL语句
        BookType bookType = session.get(BookType.class, typeId);
        //延迟检索-首先查找Session缓存，然后查找二级缓存，若找不到则返回代理对象（查询数据库）
        //BookType bookType1 = session.load(BookType.class,typeId);//此时没有发送SQL语句
        System.out.println(bookType.getTypeName());
        session.close();
    }

    public static void main(String[] args) {
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        BookType bookType = bookTypeDAO.findById(2L);
    }
}
