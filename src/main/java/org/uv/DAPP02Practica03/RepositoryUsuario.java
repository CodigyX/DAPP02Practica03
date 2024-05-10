
package org.uv.DAPP02Practica03;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Codigy
 */
public interface RepositoryUsuario extends JpaRepository<User, Long> {
     User findByUsername(String username);
}
