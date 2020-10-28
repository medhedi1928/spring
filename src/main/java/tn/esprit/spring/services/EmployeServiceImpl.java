package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
@Configurable
public class EmployeServiceImpl implements IEmployeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		try {
			LOGGER.info("{Save succes}", employeRepository.save(employe));
			Employe employeManagedEntity = employeRepository.findById(employe.getId()).get();

			return employeManagedEntity.getId();

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Erreur methode ajouterEmploye =>" + " " );
			return -1;
		}

	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		try {
			LOGGER.info("{mettre A jour Email By EmployeId}");

			Employe employe = employeRepository.findById(employeId).get();
			employe.setEmail(email);
			LOGGER.debug("{Email changer}");

		} catch (Exception e) {
			LOGGER.error("Erreur methode mettreAjourEmailByEmployeId=>" + e.getMessage());
		}

	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {
		try {
			LOGGER.info("{affecter Employe A Departement}");

			Departement depManagedEntity = deptRepoistory.findById(depId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			if (depManagedEntity.getEmployes() == null) {

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			} else {

				depManagedEntity.getEmployes().add(employeManagedEntity);
				LOGGER.debug("{Email changer}");

			}
		} catch (Exception e) {
			LOGGER.error("Erreur methode affecterEmployeADepartement=>" + " " + e.getMessage());
		}

	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		try {
			LOGGER.info("{desaffecter Employe Du Departement}");

			Departement dep = deptRepoistory.findById(depId).get();

			int employeNb = dep.getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (dep.getEmployes().get(index).getId() == employeId) {
					dep.getEmployes().remove(index);
					break;// a revoir
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erreur methode desaffecterEmployeDuDepartement =>" + " " + e.getMessage());
		}

	}

	public int ajouterContrat(Contrat contrat) {
		try {
			LOGGER.info("{ajouter Contrat}");

			contratRepoistory.save(contrat);
			return contrat.getReference();
		} catch (Exception e) {
			LOGGER.error("Erreur methode ajouterContrat=>" + " " + e.getMessage());

			return 0;
		}

	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		try {
			LOGGER.info("{affecter Contrat A Employe}");

			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
		} catch (Exception e) {
			LOGGER.error("Erreur methode affecterContratAEmploye =>" + " " + e.getMessage());
			// TODO: handle exception
		}

	}

	public String getEmployePrenomById(int employeId) {
		try {
			LOGGER.info("{get Employe Prenom ById}");

			Employe employeManagedEntity = employeRepository.findById(employeId).get();
			return employeManagedEntity.getPrenom();
		} catch (Exception e) {
			LOGGER.error("Erreur methode getEmployePrenomById =>" + " " + e.getMessage());
			// TODO: handle exception
			return e.getMessage();
		}

	}

	public void deleteEmployeById(int employeId) {
		try {
			LOGGER.info("{delete Employe ById}");

			Employe employe = employeRepository.findById(employeId).get();

			// Desaffecter l'employe de tous les departements
			// c'est le bout master qui permet de mettre a jour
			// la table d'association
			for (Departement dep : employe.getDepartements()) {
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
		} catch (Exception e) {
			LOGGER.error("Erreur methode deleteEmployeById =>" + " " + e.getMessage());
		}

	}

	public void deleteContratById(int contratId) {
		try {
			LOGGER.info("{delete Contrat ById}");

			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			contratRepoistory.delete(contratManagedEntity);
		} catch (Exception e) {
			LOGGER.error("Erreur methode deleteContratById =>" + " " + e.getMessage());
		}

	}

	public int getNombreEmployeJPQL() {
		try {
			LOGGER.info("{get Nombre Employe JPQL}");

			return employeRepository.countemp();

		} catch (Exception e) {
			LOGGER.error("Erreur methode getNombreEmployeJPQL =>" + " " + e.getMessage());
			return -1;
		}
	}

	public List<String> getAllEmployeNamesJPQL() {
		List<String>employes=new ArrayList<String>();
		employes= employeRepository.employeNames();
		LOGGER.info("{get All Employe Names JPQL}");

		if(employes.isEmpty()) {
			LOGGER.error("Erreur methode getAllEmployeNamesJPQL return vide" );
			return null;
		}else
			return employes;



	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		try {
			LOGGER.info("{get All Employe By Entreprise}");

			return employeRepository.getAllEmployeByEntreprisec(entreprise);

		} catch (Exception e) {
			LOGGER.error("Erreur methode getAllEmployeByEntreprise =>" + " " + e.getMessage());
			return null;
		}
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		try {
			LOGGER.info("{mettre A jour Email By EmployeId JPQL}");
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeManagedEntity.getId());

		} catch (Exception e) {
			LOGGER.error("Erreur methode mettreAjourEmailByEmployeIdJPQL =>");
		}

	}

	public void deleteAllContratJPQL() {
		try {
			LOGGER.info("{delete All Contrat JPQL}");

			employeRepository.deleteAllContratJPQL();

		} catch (Exception e) {
			LOGGER.error("Erreur methode deleteAllContratJPQL =>" + " " + e.getMessage());
		}

	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		try {
			LOGGER.info("{get Salaire By EmployeId JPQL}");

			return employeRepository.getSalaireByEmployeIdJPQL(employeId);

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Erreur methode getSalaireByEmployeIdJPQL =>" + " " + e.getMessage());
			return (Float) null;// TODO: handle exception
		}
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		double i=0;
		try {
			LOGGER.info("{get Salaire Moyen By DepartementId}");

			i= employeRepository.getSalaireMoyenByDepartementId(departementId);
			return i;

		} catch (Exception e) {
			LOGGER.error("Erreur methode getSalaireMoyenByDepartementId =>" + " " + e.getMessage());
			return i;// TODO: handle exception
		}
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		try {
			LOGGER.info("{get Timesheets By Mission And Date}");

			return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);

		} catch (Exception e) {
			LOGGER.error("Erreur methode getTimesheetsByMissionAndDate =>" + " " + e.getMessage());
			return null;// TODO: handle exception
		}
	}

	public List<Employe> getAllEmployes() {
		try {
			LOGGER.info("{get All Employes}");

			return (List<Employe>) employeRepository.findAll();

		} catch (Exception e) {
			LOGGER.error("Erreur methode getAllEmployes =>" + " " + e.getMessage());
			return null;// TODO: handle exception
		}
	}

}
