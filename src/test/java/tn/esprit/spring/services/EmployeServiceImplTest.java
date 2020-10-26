package tn.esprit.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.EmployeServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class EmployeServiceImplTest {


    @Autowired
	EmployeServiceImpl controller;
    
	
	@Test
	public void ajouterEmployeDone() {
		Employe employe = getEmploye();
		int getFromDb = controller.getNombreEmployeJPQL();
		int i=controller.ajouterEmploye(employe);
		System.out.println(getFromDb);
		assertNotNull(employe);

}
	
	private Employe getEmploye() {
		Employe employe = new Employe();
		employe.setNom("Sean Murphy");
		employe.setPrenom("Delhi");
		employe.setEmail("Mumbai");
		employe.setActif(true);
		employe.setRole(Role.ADMINISTRATEUR);
		return employe;
	}
}
