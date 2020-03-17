package pojo;

public class BookInfo {
    private Long bookId;
    private String bookName;
    private String bookAuthor;
    private Double bookPrice;
    private Long bookDate;
    private String bookPublish;
    private String bookDesc;
    //多对一
    private BookType bookTypeByTypeId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Long getBookDate() {
        return bookDate;
    }

    public void setBookDate(Long bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookPublish() {
        return bookPublish;
    }

    public void setBookPublish(String bookPublish) {
        this.bookPublish = bookPublish;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInfo bookInfo = (BookInfo) o;

        if (bookId != null ? !bookId.equals(bookInfo.bookId) : bookInfo.bookId != null) return false;
        if (bookName != null ? !bookName.equals(bookInfo.bookName) : bookInfo.bookName != null) return false;
        if (bookAuthor != null ? !bookAuthor.equals(bookInfo.bookAuthor) : bookInfo.bookAuthor != null) return false;
        if (bookPrice != null ? !bookPrice.equals(bookInfo.bookPrice) : bookInfo.bookPrice != null) return false;
        if (bookDate != null ? !bookDate.equals(bookInfo.bookDate) : bookInfo.bookDate != null) return false;
        if (bookPublish != null ? !bookPublish.equals(bookInfo.bookPublish) : bookInfo.bookPublish != null)
            return false;
        if (bookDesc != null ? !bookDesc.equals(bookInfo.bookDesc) : bookInfo.bookDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId != null ? bookId.hashCode() : 0;
        result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
        result = 31 * result + (bookAuthor != null ? bookAuthor.hashCode() : 0);
        result = 31 * result + (bookPrice != null ? bookPrice.hashCode() : 0);
        result = 31 * result + (bookDate != null ? bookDate.hashCode() : 0);
        result = 31 * result + (bookPublish != null ? bookPublish.hashCode() : 0);
        result = 31 * result + (bookDesc != null ? bookDesc.hashCode() : 0);
        return result;
    }

    public BookType getBookTypeByTypeId() {
        return bookTypeByTypeId;
    }

    public void setBookTypeByTypeId(BookType bookTypeByTypeId) {
        this.bookTypeByTypeId = bookTypeByTypeId;
    }
}
