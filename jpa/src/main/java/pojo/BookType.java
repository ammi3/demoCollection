package pojo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_type")
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "type_name")
    private String typeName;
    @Column(name = "type_desc")
    private String typeDesc;

    //一对多
    @OneToMany(mappedBy = "bookType")//这个名称和BookInfo中关联对象属性的名称一致
    private Set<BookInfo> books = new HashSet<>();

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

    public Set<BookInfo> getBooks() {
        return books;
    }

    public void setBooks(Set<BookInfo> books) {
        this.books = books;
    }


}
