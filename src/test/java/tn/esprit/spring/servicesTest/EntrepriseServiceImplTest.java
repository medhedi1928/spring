package tn.esprit.spring.servicesTest;



import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
/*
 * @SpringBootTest Annotation qui peut être spécifiée sur une classe de test qui
 * exécute des tests basés sur Spring Boot.
 */
public class EntrepriseServiceImplTest {

	@Autowired
	EntrepriseServiceImpl controller;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	EntrepriseRepository entrepriseRepository;
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Test
	public void ajouterEntrepriseTest() {
		Entreprise entreprise = new Entreprise("Salma", "Commercial");

		int i = controller.ajouterEntreprise(entreprise);

		assertThat(i).isNotNegative();
	}
     
	@Test
	public void ajouterDepartementTest() {
		Departement dep = new Departement("informatique");

		int i = controller.ajouterDepartement(dep);
		assertThat(i).isNotNegative();
	}
	
	@Test
	public void affecterEntrepriseADepartementTest() {

		Entreprise entreprise = new Entreprise("mon entreprise", "Commercial");
		controller.ajouterEntreprise(entreprise);
		Departement departement = new Departement("Mécanique");
		deptRepoistory.save(departement);
		controller.affecterDepartementAEntreprise(1, 2);
		assertNotNull(deptRepoistory.findById(departement.getId()));

	}

	/*@Test
	public void getEntrepriseByIdTest() {
		Entreprise entreprise = new Entreprise("Entreprise Salma","commercial");
		controller.ajouterEntreprise(entreprise);

		Entreprise name = controller.getEntrepriseById(2);

	}*/
	
	@Test
	public void deleteEntrepriseByIdTest() {
		Entreprise entreprise = new Entreprise("entreprise nayrouz", "Commercial");
		controller.ajouterEntreprise(entreprise);
		assertNotNull(entreprise);
		controller.deleteEntrepriseById(entreprise.getId());

	}
	
	@Test
	public void deleteDepartementByIdTest() {
		Departement departement = new Departement("salma");
		deptRepoistory.save(departement);
		assertNotNull(departement);
		//controller.deleteDepartementById(departement.getId());

	}
	/*	@Test
	public List<String> getAllDepartementsNamesByEntreprise(){
		List<String> list = new ArrayList<String>();
		int entrepriseId=2;
		
		list=controller.getAllDepartementsNamesByEntreprise(entrepriseId);
		return list;
		
	}*/

	





	

	
	
}
