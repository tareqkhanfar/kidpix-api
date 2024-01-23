package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name =  "address_tbl")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "work_address" , length = 512)
    private  String work_address ;
    @Column(name = "home_address" , length = 512)

    private String home_address ;
    @Column(name = "city")

    private String  city ;
    @Column(name = "country")

    private String country ;

    @Column(name = "zip_code")

    private String zipCode ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user ;


    @Column(name = "phone_number")
    private String phone ;

    @OneToMany(mappedBy = "addressEntity")
    private List<PhysicalBookEntity> orderEntityList ;


}
