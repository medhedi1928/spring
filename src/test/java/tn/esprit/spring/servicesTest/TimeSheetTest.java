package tn.esprit.spring.servicesTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

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
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.TimesheetServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSheetTest {


    @Autowired
	TimesheetServiceImpl controller;
    
	
	@Test
	public void ajouterTimesheetDone() {
		
		 controller.ajouterTimesheet(2,20,new Date(2020-02-05),new Date (2020-02-07));

     }
	//@Test
	//public void ValiderTimesheetDone() {
	//controller.validerTimesheet( 2,1, new Date(2020-03-01), new Date (2020-11-01), 1);
	//}
	@Test
	public void findAllMission() {
	controller.findAllMissionByEmployeJPQL(2);
	}
	
	@Test
	public void getAllEmploye() {
	controller.getAllEmployeByMission(2);
	}
	
}

