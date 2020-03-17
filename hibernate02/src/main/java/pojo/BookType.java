package pojo;

import java.util.Collection;

public class BookType {
    private Long typeId;
    private String typeName;
    private String typeDesc;
    //一对多，集合没有初始化
    private Collection<BookInfo> bookInfosByTypeId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookType bookType = (BookType) o;

        if (typeId != null ? !typeId.equals(bookType.typeId) : bookType.typeId != null) return false;
        if (typeName != null ? !typeName.equals(bookType.typeName) : bookType.typeName != null) return false;
        if (typeDesc != null ? !typeDesc.equals(bookType.typeDesc) : bookType.typeDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId != null ? typeId.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (typeDesc != null ? typeDesc.hashCode() : 0);
        return result;
    }

    public Collection<BookInfo> getBookInfosByTypeId() {
        return bookInfosByTypeId;
    }

    public void setBookInfosByTypeId(Collection<BookInfo> bookInfosByTypeId) {
        this.bookInfosByTypeId = bookInfosByTypeId;
    }
}
