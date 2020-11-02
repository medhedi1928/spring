package tn.esprit.spring.services;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	private static final Logger l = Logger.getLogger(EntrepriseServiceImpl.class);
	
	
	public int ajouterEntreprise(Entreprise entreprise) {
		
		entrepriseRepoistory.save(entreprise);
		
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entreprise.getId()).orElse(null);
		if (entrepriseManagedEntity != null) {

			l.info("ajouter Entreprise avec succes {}");

			return entrepriseManagedEntity.getId();

		} else {
			l.error("Erreur methode ajouter entreprise");
			return -1;
		}
	}

	public int ajouterDepartement(Departement dep) {
		
		deptRepoistory.save(dep);
		l.info("ajouter departement avec succes {}");
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		try {
			l.info("{affecter departement A Entreprise}");

				Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
				Departement depManagedEntity = deptRepoistory.findById(depId).get();
				
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
		}
		 catch (Exception e) {
				l.error("Erreur methode affecterDepartementAEntreprise {}");

			}

	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		List<String> depNames = new ArrayList<>();
		try {
			l.info("{get All departement By Entreprise}");


		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		}
		catch (Exception e) {
			l.error("Erreur methode getAllDepartementsNamesByEntreprise {}");
			return new ArrayList<>();
		}
		
		return depNames;

		
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		l.info("{delete entreprise ById}");
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
		if (entrepriseManagedEntity != null) {
			entrepriseRepoistory.delete(entrepriseManagedEntity);

		} else {
			l.error("Erreur methode deleteContratById :NullPointerException");

		}
	
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		l.info("{delete departement ById}");
		Departement departementManagedEntity =deptRepoistory.findById(depId).orElse(null);	
		if (departementManagedEntity != null) {
			deptRepoistory.delete(departementManagedEntity);

		} else {
			l.error("Erreur methode deleteDepartementById");

		}

	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("{get All departement By Entreprise}");
		return entrepriseRepoistory.findById(entrepriseId).get();	
	}

}
