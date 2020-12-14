package com.runApplication.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.runApplication.entity.Persons;


public interface queryForJpaRepositoryClient extends JpaRepository<Persons, Long>{
	//and p.address like %?2
	@Query("select p from Persons p where p.lastName like %?1 and p.address like %?2 ")
	List<Persons> getPersonsResult(String name,String address);
	

    @Modifying //操作需要加註解。默認查詢不需要 insert into 对象名(字段名) values (?1)
    @Transactional
	@Query(value ="insert into Persons (id_p,lastName,address,firstName,city) values (?1,?2,?3,?4,?5)",nativeQuery = true)
	int addPersons(long id, String name,String address,String firstName,String city);
    
    
    @Modifying 
    @Transactional
    @Query(value = "delete from Persons where id_p =?1",nativeQuery = true)
    int deleteData(long id);
    
    @Modifying 
    @Transactional
    @Query(value = "update Persons set lastName = ?1 , address = ?2 , firstName = ?3 , city = ?4 where id_p = ?5",nativeQuery = true)
    int updateData(String lastName,String address,String firstName,String city,long id);
}


