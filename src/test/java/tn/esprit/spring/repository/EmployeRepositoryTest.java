package tn.esprit.spring.repository;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import antlr.collections.List;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.EmployeServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository repo;
	@Autowired
	ContratRepository repoC;
	@Autowired
	EntrepriseRepository repoE;
	@Autowired
	DepartementRepository repoD;

	java.util.List<String> eml;
	
	@Test
	@Rollback(false)
	public void ajouterEmployerTest() {
		Employe employe=getEmploye();

		assertNotNull(employe);
	}

	@Test
	@Rollback(false)
	public void countempTest() {
		LOGGER.info("{La methode countemp dans EmployeRepository retourne la valeur :  }" + " " + repo.countemp(),
				+repo.countemp());

		assertNotNull(repo.countemp());
	}

	@Test
	@Rollback(false)
	public void employeNamesTest() {
		Employe employe1 = getEmploye();
		Employe employe2 = getEmploye();

		LOGGER.info("{La methode employeNames dans EmployeRepository contient :  }" + " " + repo.employeNames().size());

		assertNotNull(repo.employeNames());

	}

	@Test
	@Rollback(false)
	public void mettreAjourEmailByEmployeIdJPQLTest() {
		Employe employe = getEmploye();

		repo.mettreAjourEmailByEmployeIdJPQL("mohamedHedi@esprit.com", employe.getId());
		LOGGER.info(
				"{La methode mettreAjourEmailByEmployeIdJPQL dans EmployeRepository  : Succes  }" + employe.getEmail());

	}

	@Test
	@Rollback(false)
	public void getSalaireByEmployeIdJPQLTest() {
		Employe employe = getEmploye();
		Contrat contrat = getContrat(employe);
		float salaire = repo.getSalaireByEmployeIdJPQL(contrat.getEmploye().getId());

		LOGGER.info("{La methode getSalaireByEmployeIdJPQL dans EmployeRepository return :   }" + " "
				+ repo.getSalaireByEmployeIdJPQL(contrat.getEmploye().getId()));
		assertNotNull(salaire);
	}

	@Test
	@Rollback(false)
	public void deleteAllContratJPQLTest() {
		Employe employe = getEmploye();
		Contrat contrat = getContrat(employe);
		repo.deleteAllContratJPQL();
		LOGGER.info("{La methode deleteAllContratJPQL dans EmployeRepository return :  Succes }");

	}
@Test
@Rollback(false)
public void getSalaireMoyenByDepartementIdTest() {
	Departement departemen = new Departement("Informatique");
	java.util.List<Departement> departements = new ArrayList<>();
	departements.add(departemen);
	
	Contrat contrat1=getContrat(getEmploye());
	Contrat contrat2=getContrat2(getEmploye());
	Contrat contrat3=getContrat2(getEmploye());
	


	
	java.util.List<Employe> employes = new ArrayList<>();

	employes.add(contrat1.getEmploye());
	employes.add(contrat2.getEmploye());
	employes.add(contrat3.getEmploye());
	
	departemen.setEmployes(employes);
	repoD.save(departemen);
	
	double d =repo.getSalaireMoyenByDepartementId(1);
	LOGGER.info("{La methode getSalaireMoyenByDepartementId dans EmployeRepository return :  Succes }"
			+ d);
	assertNotNull(d);
}
	@Test
	@Rollback(false)
	public void getAllEmployeByEntreprisecTest() {
		Employe employe1 = getEmploye();
		Employe employe2 = getEmploye();
		Employe employe3 = getEmploye();
		java.util.List<Employe> employes = new ArrayList<>();
		employes.add(employe1);
		employes.add(employe2);
		employes.add(employe3);

		Entreprise entreprise = getEntreprise();

		Departement departement = new Departement("Informatique");
		departement.setEmployes(employes);

		entreprise.addDepartement(departement);

		repo.getAllEmployeByEntreprisec(entreprise);
		LOGGER.info("{La methode getAllEmployeByEntreprisec dans EmployeRepository return :  Succes }"
				+ repo.getAllEmployeByEntreprisec(entreprise).size());
		assertNotNull(repo.getAllEmployeByEntreprisec(entreprise));

	}

	private Employe getEmploye() {
		Employe employe = new Employe("Med", "BEN KHOUDJA", "med@esprit.tn", true, Role.ADMINISTRATEUR);

		repo.save(employe);
		return employe;
	}

	private Entreprise getEntreprise() {
		Entreprise entreprise = new Entreprise("Esprit", "Commercial");

		entreprise.addDepartement(new Departement("Informatique"));
		repoE.save(entreprise);
		return entreprise;
	}

	private Contrat getContrat(Employe employe) {
		Contrat contrat = new Contrat();
		employe = getEmploye();

		contrat.setEmploye(employe);
		contrat.setReference(12);
		contrat.setSalaire(1236);
		contrat.setDateDebut(new Date());
		contrat.setTypeContrat("CDI");
		repoC.save(contrat);

		return contrat;
	}
	private Contrat getContrat2(Employe employe) {
		Contrat contrat = new Contrat();
		employe = getEmploye();

		contrat.setEmploye(employe);
		contrat.setReference(12);
		contrat.setSalaire(1436);
		contrat.setDateDebut(new Date());
		contrat.setTypeContrat("CDI");
		repoC.save(contrat);

		return contrat;
	}
}
