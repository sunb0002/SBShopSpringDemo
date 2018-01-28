/**
 * 
 */
package com.madoka.sunb0002.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madoka.sunb0002.repositories.entities.User;

/**
 * @author Sun Bo
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByNric(String nric);

}
