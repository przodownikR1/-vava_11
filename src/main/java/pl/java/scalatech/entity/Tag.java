package pl.java.scalatech.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;

@JsonRootName("tag")
@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = { "parent", "name" }) )
@Data
public class Tag extends AbstactId {

    /**
     *
     */
    private static final long serialVersionUID = 6481603128242154904L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Tag parent;

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Tag> children;

    @Column(nullable = false, length = 64)
    private String name;

    @JsonGetter("parent")
    public Long getParentId() {
        if (parent == null || parent.getId() < 1) {
            return null;
        }
        return parent.getId();
    }

    @JsonSetter("parent")
    public void setParentId(Long id) {
        if (id == null || id < 1) {
            setParent(null);
            return;
        }

        Tag parent = new Tag();
        parent.setId(id);
        setParent(parent);
    }

}