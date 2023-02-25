package com.example.pojo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate  //只将有变化的字段 去执行update，优化了速度
@DynamicInsert
@Table(name = "app_user")
@EntityListeners(AuditingEntityListener.class)  // 和@EnableJpaAuditing一起用， 使   @CreatedDate，@LastModifiedDate 生效
public class SqlUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Index_Number")
    private Long IndexNumber;

    @Id
    @Column(name = "user_name")
    private String userName;

    @NonNull
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 在get 时不显示password，
    @Column(name = "password")
    private String password;


    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_Name")
    private String lastName;

    @Email(message = "Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @Column(name = "contact_Number")
    private String contactNumber;

    //@Max(100)
    //@Min(10)
    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    //@Enumerated(EnumType.STRING) //用在数据向数据库持久化时用: 。STRING：使用枚举字符串; EnumType.ORDINAL：默认值，字段存储为0，1，
    private String  gender;

    @Column(name = "nationality")
    private String nationality;

//    @Transient //不参加存储
    //@JsonAlias("tags")  //只在反序列化中起作用，可以多个,("tags", "tag"...)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//不被读取,WRITE_ONLY:仅做反序列化操作;READ_ONLY：仅做序列化操作
    //@JsonSerialize(using = tagsSerializer.class)//自定义Json 序列化
   // private ArrayList<String > inputTags;

    @Column(name = "tags")
    private String tags;

    @Column(name = "status")
    //@Enumerated(EnumType.STRING) //用在数据向数据库持久化时用: 。STRING：使用枚举字符串; EnumType.ORDINAL：默认值，字段存储为0，1，2
    private String  status;

    @NotNull
    //@CreatedDate   // 自动生成日期时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化date。 弃用，选择在application.properties 中配置 ISO
    @Column(name = "created")
    private String  created;

    @NotNull
    //@LastModifiedDate
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") //isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    @Column(name = "updated")
    private String updated;
}
